package com.example.jacek.trainingapp.productsAndCalories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.jacek.trainingapp.common.DBHelper;
import com.example.jacek.trainingapp.common.DataAccess;
import com.example.jacek.trainingapp.productsAndCalories.calories.Product;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCalories;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesDetails;

import java.util.ArrayList;

public class ProductsAndCaloriesData extends DataAccess
{
    public ProductsAndCaloriesData(Context context)
    {
        super(context);
    }

    public void addProduct(Product product)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getProductNameColumn(), product.getName());
        values.put(DBHelper.getProductCaloriesColumn(), product.getCalories());
        values.put(DBHelper.getProductCategoryColumn(), product.getCategory());
        db.insert(DBHelper.getProductTable(), null, values);
    }

    public void deleteProduct(Product product)
    {
        db.delete(DBHelper.getProductTable(), DBHelper.getProductNameColumn() + "='" + product.getName() + "'", null);
    }

    public void editProduct(Product currentProduct, Product updatedProduct)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getProductNameColumn(), updatedProduct.getName());
        values.put(DBHelper.getProductCaloriesColumn(), updatedProduct.getCalories());
        values.put(DBHelper.getProductCategoryColumn(), updatedProduct.getCategory());
        db.update(DBHelper.getProductTable(), values, DBHelper.getProductNameColumn() + " = '" + currentProduct.getName() + "'", null);
    }

    public void updateDailyDetails(Product product)
    {
        String sql = "UPDATE " + DBHelper.getDailyCaloriesDetailsTable() + " " +
                "SET " + DBHelper.getDailyCaloriesDetailsCaloriesColumn() + " = " + DBHelper.getDailyCaloriesDetailsAmountColumn() + " / 100.0" + " * " + product.getCalories() + " " +
                "WHERE " + DBHelper.getDailyCaloriesDetailsNameColumn() + " = '" + product.getName() + "'";
        db.execSQL(sql);
    }

    public Product getProduct(String name)
    {
        Product product = null;
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getProductTable() + " WHERE " + DBHelper.getProductNameColumn() + " = '" + name + "'", null);

            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                product = new Product(
                        cursor.getString(cursor.getColumnIndex(DBHelper.getProductNameColumn())),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.getProductCaloriesColumn())),
                        cursor.getString(cursor.getColumnIndex(DBHelper.getProductCategoryColumn())));
            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }


        return product;
    }

    public ArrayList<Product> getAllProducts()
    {
        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getProductTable() + " ORDER BY " + DBHelper.getProductNameColumn() + " COLLATE NOCASE", null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Product product = new Product(
                            cursor.getString(cursor.getColumnIndex(DBHelper.getProductNameColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getProductCaloriesColumn())),
                            cursor.getString(cursor.getColumnIndex(DBHelper.getProductCategoryColumn())));
                    products.add(product);
                }
            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }


        return products;
    }

    public ArrayList<Product> getAllFromCategory(String category)
    {
        ArrayList<Product> products = new ArrayList<>();

        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getProductTable()
                    + " WHERE " + DBHelper.getProductCategoryColumn() + " = '" + category + "' ORDER BY " + DBHelper.getProductNameColumn() + " COLLATE NOCASE", null);
            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Product product = new Product(
                            cursor.getString(cursor.getColumnIndex(DBHelper.getProductNameColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getProductCaloriesColumn())),
                            cursor.getString(cursor.getColumnIndex(DBHelper.getProductCategoryColumn())));
                    products.add(product);
                }
            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }

        return products;
    }

    public ArrayList<Product> getSearchedProducts(String name)
    {
        ArrayList<Product> products = new ArrayList<>();

        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getProductTable()
                    + " WHERE " + DBHelper.getProductNameColumn() + " LIKE " + "'%" + name + "%' ORDER BY " + DBHelper.getProductNameColumn() + " COLLATE NOCASE", null);
            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Product product = new Product(
                            cursor.getString(cursor.getColumnIndex(DBHelper.getProductNameColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getProductCaloriesColumn())),
                            cursor.getString(cursor.getColumnIndex(DBHelper.getProductCategoryColumn())));
                    products.add(product);
                }
            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }

        return products;
    }



    public ArrayList<DailyCalories> getAllDays()
    {
        ArrayList<DailyCalories> dailyCalories = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            String sql = "SELECT T1." + DBHelper.getDatesDate() + ", SUM(T2." + DBHelper.getDailyCaloriesDetailsCaloriesColumn() + ") AS 'CALORIES' " +
                    "FROM " + DBHelper.getDatesTable() + " T1 " +
                    "JOIN " + DBHelper.getDailyCaloriesDetailsTable() + " T2 " +
                    "ON T1." + DBHelper.getDatesDate() + " = T2." + DBHelper.getDailyCaloriesDetailsDateColumn() + " " +
                    "GROUP BY T1." + DBHelper.getDatesDate() + " " +
                    "ORDER BY T1." + DBHelper.getDatesDate() + " DESC";
            cursor = db.rawQuery(sql, null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    DailyCalories dailyCal = new DailyCalories(cursor.getString(cursor.getColumnIndex(DBHelper.getDatesDate())), cursor.getInt(cursor.getColumnIndex("CALORIES")));
                    dailyCalories.add(dailyCal);
                }
            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }


        return dailyCalories;
    }


    public void addProductToDay(DailyCaloriesDetails dailyCaloriesDetails)
    {
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getDatesTable() +
                    " WHERE " + DBHelper.getDatesDate() + " ='" + dailyCaloriesDetails.getDate() + "'", null);

            if (cursor.getCount() > 0)
            {
                addDetail(dailyCaloriesDetails);
            }
            else
            {
                addDate(dailyCaloriesDetails.getDate());
                addDetail(dailyCaloriesDetails);
            }

        }
        catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }



    public void addDetail(DailyCaloriesDetails dailyCaloriesDetails)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getDailyCaloriesDetailsDateColumn(), dailyCaloriesDetails.getDate());
        values.put(DBHelper.getDailyCaloriesDetailsNameColumn(), dailyCaloriesDetails.getName());
        values.put(DBHelper.getDailyCaloriesDetailsAmountColumn(), dailyCaloriesDetails.getAmount());
        values.put(DBHelper.getDailyCaloriesDetailsCaloriesColumn(),dailyCaloriesDetails.getCalories());
        db.insert(DBHelper.getDailyCaloriesDetailsTable(), null, values);
    }

    public void deleteDetail(DailyCaloriesDetails dailyCaloriesDetails)
    {
        db.delete(DBHelper.getDailyCaloriesDetailsTable(), DBHelper.getDailyCaloriesDetailsIdColumn() + "= " + dailyCaloriesDetails.getId(), null);
    }

    public void editDetail(DailyCaloriesDetails currentDaily, DailyCaloriesDetails updatedDaily)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getDailyCaloriesDetailsDateColumn(), updatedDaily.getDate());
        values.put(DBHelper.getDailyCaloriesDetailsNameColumn(), updatedDaily.getName());
        values.put(DBHelper.getDailyCaloriesDetailsAmountColumn(), updatedDaily.getAmount());
        values.put(DBHelper.getDailyCaloriesDetailsCaloriesColumn(),updatedDaily.getCalories());
        db.update(DBHelper.getDailyCaloriesDetailsTable(), values, DBHelper.getDailyCaloriesDetailsIdColumn() + " = '" + currentDaily.getId() + "'", null);
    }

    public DailyCaloriesDetails getDetail(int id)
    {
        DailyCaloriesDetails detail = null;

        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getDailyCaloriesDetailsTable() + " WHERE " + DBHelper.getDailyCaloriesDetailsIdColumn() + " = " + id, null);

            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                detail = new DailyCaloriesDetails(
                        cursor.getInt(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsIdColumn())),
                        cursor.getString(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsDateColumn())),
                        cursor.getString(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsNameColumn())),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsAmountColumn())),
                        cursor.getInt(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsCaloriesColumn()))
                );


            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }


        return detail;
    }

    public ArrayList<DailyCaloriesDetails> getAllDetails(String date)
    {
        ArrayList<DailyCaloriesDetails> dailyCaloriesDetails = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getDailyCaloriesDetailsTable() + " WHERE " + DBHelper.getDailyCaloriesDetailsDateColumn() + " ='" + date +"' ORDER BY " + DBHelper.getDailyCaloriesDetailsIdColumn(), null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    DailyCaloriesDetails dailyCalDetails = new DailyCaloriesDetails(
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsIdColumn())),
                            cursor.getString(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsDateColumn())),
                            cursor.getString(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsNameColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsAmountColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getDailyCaloriesDetailsCaloriesColumn()))
                    );
                    dailyCaloriesDetails.add(dailyCalDetails);
                }
            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }


        return dailyCaloriesDetails;
    }

    public void deleteDetails(String date)
    {
        db.delete(DBHelper.getDailyCaloriesDetailsTable(),DBHelper.getDailyCaloriesDetailsDateColumn() + " ='" + date + "'", null);
    }

}
