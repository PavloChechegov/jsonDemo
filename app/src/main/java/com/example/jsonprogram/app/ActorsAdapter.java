package com.example.jsonprogram.app;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pasha on 1/20/16.
 */
public class ActorsAdapter extends ArrayAdapter {

    private ArrayList<Actors> mArrayList;
    //    private int resource;
    private int mResources;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ActorsAdapter(Context context, int textViewResourceId, ArrayList arrayList) {
        super(context, textViewResourceId, arrayList);

        mArrayList = arrayList;
        mResources = textViewResourceId;
        mContext = context;

        // що це дає, тобто що означає context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = mLayoutInflater.inflate(mResources, null);
            holder = new ViewHolder();

            holder.imageview = (ImageView) convertView.findViewById(R.id.ivImage);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescriptionn);
            holder.tvDOB = (TextView) convertView.findViewById(R.id.tvDateOfBirth);
            holder.tvCountry = (TextView) convertView.findViewById(R.id.tvCountry);
            holder.tvHeight = (TextView) convertView.findViewById(R.id.tvHeight);
            holder.tvSpouse = (TextView) convertView.findViewById(R.id.tvSpouse);
            holder.tvChildren = (TextView) convertView.findViewById(R.id.tvChildren);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageview.setImageResource(R.mipmap.ic_launcher);
        //new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.tvName.setText(mArrayList.get(position).getName());
        holder.tvDescription.setText(mArrayList.get(position).getDescription());
        holder.tvDOB.setText("B'day: " + mArrayList.get(position).getDob());
        holder.tvCountry.setText(mArrayList.get(position).getCountry());
        holder.tvHeight.setText("Height: " + mArrayList.get(position).getHeight());
        holder.tvSpouse.setText("Spouse: " + mArrayList.get(position).getSpouse());
        holder.tvChildren.setText("Children: " + mArrayList.get(position).getChildren());
        return convertView;
    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvName;
        public TextView tvDescription;
        public TextView tvDOB;
        public TextView tvCountry;
        public TextView tvHeight;
        public TextView tvSpouse;
        public TextView tvChildren;

    }
}
