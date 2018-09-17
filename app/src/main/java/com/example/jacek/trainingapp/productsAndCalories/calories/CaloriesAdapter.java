package com.example.jacek.trainingapp.productsAndCalories.calories;

import android.content.Context;
import android.os.Bundle;
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
import java.util.Collections;

/**
 * Created by Jacek on 18.04.2018.
 */

public class CaloriesAdapter extends RecyclerView.Adapter<CaloriesAdapter.CaloriesViewHolder>
{
    private Context context;
    private ArrayList<Product> products;
    private ProductsAndCaloriesData productsAndCaloriesData;
    private String type;
    private String date;

    public CaloriesAdapter(Context context, ProductsAndCaloriesData productsAndCaloriesData, String type)
    {
        this.context = context;
        this.productsAndCaloriesData = productsAndCaloriesData;
        products = productsAndCaloriesData.getAllProducts();
        this.type = type;
    }

    public CaloriesAdapter(Context context, ProductsAndCaloriesData productsAndCaloriesData, String type, String date)
    {
        this.context = context;
        this.productsAndCaloriesData = productsAndCaloriesData;
        products = productsAndCaloriesData.getAllProducts();
        this.type = type;
        this.date = date;
    }


    @Override
    public CaloriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_calories, parent, false);
        CaloriesViewHolder holder = new CaloriesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CaloriesViewHolder holder, int position)
    {
        holder.name.setText(products.get(position).getName());
        holder.calories.setText(String.valueOf(products.get(position).getCalories()));
    }


    @Override
    public int getItemCount()
    {
        return products.size();
    }


    public void add(Product product)
    {

        product.capitalizeFirstLetter();
        products.add(product);
        Collections.sort(products);
        productsAndCaloriesData.addProduct(product);
        notifyDataSetChanged();
    }

    public void edit(int position, Product updated)
    {
        updated.capitalizeFirstLetter();
        productsAndCaloriesData.editProduct(products.get(position),updated);
        products.set(position, updated);
        Collections.sort(products);
        notifyDataSetChanged();
    }

    public void delete(int position)
    {
        productsAndCaloriesData.deleteProduct(products.get(position));
        products.remove(position);
        notifyItemRemoved(position);
    }

    public void filterByCategory(String category)
    {
        if(category.equalsIgnoreCase("wszystkie"))
        {
            products = productsAndCaloriesData.getAllProducts();
        }
        else
        {
            products = productsAndCaloriesData.getAllFromCategory(category);
        }

        notifyDataSetChanged();
    }

    public void search(String name)
    {
        products = productsAndCaloriesData.getSearchedProducts(name);
        notifyDataSetChanged();
    }


    public class CaloriesViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView calories;

        public CaloriesViewHolder(final View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.productNameTextViewRow);
            calories = itemView.findViewById(R.id.productCaloriesTextViewRow);


            if(type.equals("normal"))
            {
                itemView.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {

                        showPopup(v);
                        return true;
                    }
                });
            }
            else
            {
                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        AddProductToDayOnClickDialog(getAdapterPosition());
                    }
                });
            }
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
                        case R.id.editItem:
                        {
                            openEditDialog(getAdapterPosition());
                            return true;
                        }
                        case R.id.deleteItem:
                        {
                            Utils.openDeleteConfirmDialog(context, getAdapterPosition());
                            return true;
                        }
                        case R.id.addToDayItem:
                        {
                            openAddToDayDialog(getAdapterPosition());
                            return true;
                        }
                        default:
                        {
                            return false;
                        }
                    }
                }
            });

            popupMenu.inflate(R.menu.calories_popup_menu);
            popupMenu.show();

        }

        public void openEditDialog(int position)
        {
            EditProductDialog editDialog = new EditProductDialog();
            Bundle args = new Bundle();
            args.putString("productName",products.get(position).getName());
            args.putInt("productCalories",products.get(position).getCalories());
            args.putString("productCategory", products.get(position).getCategory());
            args.putInt(Utils.POSITION,position);
            editDialog.setArguments(args);
            editDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"edit dialog");
        }


        public void AddProductToDayOnClickDialog(int position)
        {
            AddProductToDayOnClickDialog addProductToDayOnClickDialog = new AddProductToDayOnClickDialog();
            Bundle args = new Bundle();
            args.putString("name", products.get(position).getName());
            args.putString("date", date);
            args.putInt("productCalories",products.get(position).getCalories());
            addProductToDayOnClickDialog.setArguments(args);
            addProductToDayOnClickDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"add product to day on click dialog");
        }

        public void openAddToDayDialog(int position)
        {
            AddProductToDayDialog addProductToDayDialog = new AddProductToDayDialog();
            Bundle args = new Bundle();
            args.putString("productName",products.get(position).getName());
            args.putInt("productCalories",products.get(position).getCalories());
            addProductToDayDialog.setArguments(args);
            addProductToDayDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"addToDay prooduct to day dialog");
        }


    }






}
