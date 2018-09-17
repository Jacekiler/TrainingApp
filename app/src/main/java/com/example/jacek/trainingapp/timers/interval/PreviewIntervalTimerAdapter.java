package com.example.jacek.trainingapp.timers.interval;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;

import java.util.ArrayList;

/**
 * Created by Jacek on 16.04.2018.
 */

public class PreviewIntervalTimerAdapter extends RecyclerView.Adapter<PreviewIntervalTimerAdapter.PreviewITViewHolder>
{
    private Context context;
    private ArrayList<String> recyclerArrayList;

    public PreviewIntervalTimerAdapter(Context context, ArrayList<String> recyclerArrayList)
    {
        this.context = context;
        this.recyclerArrayList = recyclerArrayList;
    }

    @Override
    public PreviewITViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_preview_interval_timer, parent, false);
        return new PreviewITViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreviewITViewHolder holder, int position)
    {
        holder.text.setText(recyclerArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return recyclerArrayList.size();
    }

    public class PreviewITViewHolder extends RecyclerView.ViewHolder
    {
        private TextView text;

        public PreviewITViewHolder(View itemView)
        {
            super(itemView);
            text = itemView.findViewById(R.id.rowPreviewIT);
        }
    }
}
