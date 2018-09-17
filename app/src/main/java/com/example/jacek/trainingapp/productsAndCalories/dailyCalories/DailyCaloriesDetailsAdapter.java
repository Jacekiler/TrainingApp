package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.productsAndCalories.ProductsAndCaloriesData;

import java.util.ArrayList;

public class DailyCaloriesDetailsAdapter extends RecyclerView.Adapter<DailyCaloriesDetailsAdapter.DailyCaloriesDetailsViewHolder>
{
    private Context context;
    private ArrayList<DailyCaloriesDetails> dailyCaloriesDetails;
    private ProductsAndCaloriesData productsAndCaloriesData;
    private String date;

    public DailyCaloriesDetailsAdapter(Context context, ProductsAndCaloriesData productsAndCaloriesData, String date)
    {
        this.context = context;
        this.productsAndCaloriesData = productsAndCaloriesData;
        dailyCaloriesDetails = productsAndCaloriesData.getAllDetails(date);
        this.date = date;
    }

    @NonNull
    @Override
    public DailyCaloriesDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_daily_calories_details, parent, false);
        return new DailyCaloriesDetailsViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        return dailyCaloriesDetails.size();
    }

    public void update()
    {
        dailyCaloriesDetails = productsAndCaloriesData.getAllDetails(date);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DailyCaloriesDetailsViewHolder holder, int position)
    {
        holder.name.setText(dailyCaloriesDetails.get(position).getName());
        if(dailyCaloriesDetails.get(position).getAmount()!=0)
        {
            holder.amount.setText(String.valueOf(dailyCaloriesDetails.get(position).getAmount()) + "g");
        }
        holder.calories.setText(String.valueOf(dailyCaloriesDetails.get(position).getCalories()) + "kcal");
    }

    // DONE
    public void add(DailyCaloriesDetails dailyCaloriesDetails)
    {
        if(!productsAndCaloriesData.hasDate(dailyCaloriesDetails.getDate()))
        {
            productsAndCaloriesData.addDate(dailyCaloriesDetails.getDate());
        }

        productsAndCaloriesData.addDetail(dailyCaloriesDetails);
        this.dailyCaloriesDetails.add(dailyCaloriesDetails);
        notifyItemInserted(getItemCount());
    }

    // DONE
    public void delete(int position)
    {
        productsAndCaloriesData.deleteDetail(dailyCaloriesDetails.get(position));
        dailyCaloriesDetails.remove(position);
        notifyItemRemoved(position);
    }

    // DONE
    public void edit(int position, DailyCaloriesDetails dailyCaloriesDetails)
    {
        productsAndCaloriesData.editDetail(this.dailyCaloriesDetails.get(position), dailyCaloriesDetails);
        this.dailyCaloriesDetails.set(position,dailyCaloriesDetails);
        notifyItemChanged(position);
    }

    public class DailyCaloriesDetailsViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView amount;
        TextView calories;


        public DailyCaloriesDetailsViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.nameDailyCaloriesDetailsRow);
            amount = itemView.findViewById(R.id.amountDailyCaloriesDetailsRow);
            calories = itemView.findViewById(R.id.caloriesDailyCaloriesDetailsRow);

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    showPopup(v);
                    return false;
                }
            });
        }

        public void showPopup(View view)
        {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    switch (item.getItemId())
                    {

                        case R.id.editDetail:
                        {
                            openEditDetailDialog(getAdapterPosition());
                            return true;
                        }
                        case R.id.deleteDetail:
                        {
                            Utils.openDeleteConfirmDialog(context, getAdapterPosition());
                            return true;
                        }
                        default:
                        {
                            return false;
                        }
                    }
                }
            });

            popupMenu.inflate(R.menu.daily_calories_details_popup_menu);
            popupMenu.show();
        }


        public void openEditDetailDialog(int position)
        {
            EditDetailDialog editDetailDialog = new EditDetailDialog();
            Bundle args = new Bundle();
            args.putInt(Utils.POSITION, position);
            args.putInt("id",dailyCaloriesDetails.get(position).getId());
            editDetailDialog.setArguments(args);
            editDetailDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"edit detail dialog");
        }




    }
}
