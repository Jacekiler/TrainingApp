package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

import android.content.Context;
import android.content.Intent;
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

public class DailyCaloriesAdapter extends RecyclerView.Adapter<DailyCaloriesAdapter.DailyCaloriesViewHolder>
{
    private Context context;
    private ArrayList<DailyCalories> dailyCalories;
    private ProductsAndCaloriesData productsAndCaloriesData;

    // DONE
    public DailyCaloriesAdapter(Context context, ProductsAndCaloriesData productsAndCaloriesData)
    {
        this.context = context;
        this.productsAndCaloriesData = productsAndCaloriesData;
        dailyCalories = productsAndCaloriesData.getAllDays();
    }

    @NonNull
    @Override
    public DailyCaloriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_daily_calories, parent, false);
        return new DailyCaloriesViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        return dailyCalories.size();
    }

    public void update()
    {
        dailyCalories = productsAndCaloriesData.getAllDays();
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull DailyCaloriesViewHolder holder, int position)
    {
        holder.calories.setText(String.valueOf(dailyCalories.get(position).getCalories()));
        holder.date.setText(dailyCalories.get(position).getDate());
    }

    public boolean hasDay(String date)
    {
        for(DailyCalories d:dailyCalories)
        {
            if(d.getDate().equals(date))
            {
                return true;
            }
        }
        return false;

//        return dailyCalories.get(0).getDate().equals(date);
    }




    // DONE
    public void delete(int position)
    {
//        productsAndCaloriesData.deleteDay(dailyCalories.get(position));

        productsAndCaloriesData.deleteDetails(dailyCalories.get(position).getDate());
        if(!productsAndCaloriesData.hasDate(dailyCalories.get(position).getDate()))
        {
            productsAndCaloriesData.deleteDate(dailyCalories.get(position).getDate());
        }


        dailyCalories.remove(position);
        notifyItemRemoved(position);
    }

    public class DailyCaloriesViewHolder extends RecyclerView.ViewHolder
    {
        TextView calories;
        TextView date;


        public DailyCaloriesViewHolder(View itemView)
        {
            super(itemView);
            calories = itemView.findViewById(R.id.caloriesDailyCaloriesRow);
            date = itemView.findViewById(R.id.dateDailyCaloriesRow);

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    showPopup(v);
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openDetails();
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
                        case R.id.editDay:
                        {
                            openDetails();
                            return true;
                        }
                        case R.id.deleteDay:
                        {
                            Utils.openDeleteConfirmDialog(context,getAdapterPosition());
                            return true;
                        }

                        default:
                        {
                            return false;
                        }
                    }
                }
            });

            popupMenu.inflate(R.menu.daily_calories_popup_menu);
            popupMenu.show();

        }

        public void openDetails()
        {
            Intent intent = new Intent(context, DailyCaloriesDetailsActivity.class);
            intent.putExtra("date",date.getText().toString());
            ((AppCompatActivity)context).startActivityForResult(intent, 2);
        }

    }
}