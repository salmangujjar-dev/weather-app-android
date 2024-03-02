package com.weather_app_android.data.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weather_app_android.R;
import com.weather_app_android.data.entities.CityHistory;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;

    private ArrayList<CityHistory> cityHistoryList;
    public ContactAdapter(Context context, ArrayList<CityHistory> cityHistoryList){
        this.context = context;
        this.cityHistoryList = cityHistoryList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.row_content, null, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.contactName.setText(""+cityHistoryList.get(position).getCityId());
        holder.contactNumber.setText(cityHistoryList.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return cityHistoryList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder
    {
        TextView contactName, contactNumber;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

           contactName = itemView.findViewById(R.id.city_id);
           contactNumber = itemView.findViewById(R.id.city_name);


        }
    }
}
