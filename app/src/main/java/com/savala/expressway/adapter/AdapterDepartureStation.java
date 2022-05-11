package com.savala.expressway.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.DepartureStation;
import com.savala.expressway.R;
import com.savala.expressway.model.ModelTollStations;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterDepartureStation extends RecyclerView.Adapter<AdapterDepartureStation.DepartureStationHolder> {

    private Context context;
    private List<ModelTollStations> stationList;

    //constructor


    public AdapterDepartureStation(Context context, List<ModelTollStations> tollList) {
        this.context = context;
        this.stationList = tollList;
    }

    @NonNull
    @Override
    public DepartureStationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (row_user)

        View view = LayoutInflater.from(context).inflate(R.layout.row_departure_station, parent, false);

        return new DepartureStationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DepartureStationHolder holder, int position) {
        // get data
        final ModelTollStations station = stationList.get(position);
        String stationUid = station.getStation_id();

        holder.stationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DepartureStation.class);
                intent.putExtra("station_uid", stationUid);
                context.startActivity(intent);
            }
        });

        setStationDetails(station, holder);
    }

    private void setStationDetails(ModelTollStations station, DepartureStationHolder holder) {
        //get user info from uid in model
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TollStation");
        ref.orderByChild("station_id").equalTo(station.getStation_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String stationName = "" + ds.child("name").getValue();

                            holder.mRowDepartureStationName.setText(stationName);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    class DepartureStationHolder extends RecyclerView.ViewHolder{
        private TextView mRowDepartureStationName;
        private RelativeLayout stationLayout;

        public DepartureStationHolder(@NonNull View itemView) {
            super(itemView);

            mRowDepartureStationName = itemView.findViewById(R.id.station_name);

            stationLayout = itemView.findViewById(R.id.station_layout);
        }
    }
}
