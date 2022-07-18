package com.savala.expressway.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.expressway.passenger.BoardingPoint;
import com.savala.expressway.R;
import com.savala.expressway.model.ModelBus;

import java.util.ArrayList;

public class AdapterBus extends RecyclerView.Adapter<AdapterBus.BusHolder> {

    private Context context;
    private ArrayList<ModelBus> busList;

    //constructor
    public AdapterBus(Context context, ArrayList<ModelBus> busList) {
        this.context = context;
        this.busList = busList;
    }

    @NonNull
    @Override
    public BusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (row_user)

        View view = LayoutInflater.from(context).inflate(R.layout.row_bus, parent, false);

        return new BusHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BusHolder holder, int position) {
        // get data
        final ModelBus bus = busList.get(position);
        String number_plate = bus.getNumber_plate();

        //click potential to open to user
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference("DoneBooking")
                        .orderByChild("number_plate").equalTo(number_plate)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds: snapshot.getChildren()){
                                    String number_plate2 = "" + ds.child("number_plate").getValue();

                                    if(number_plate2.equals(number_plate)){
                                        Toast.makeText(context, "Already booked this bus", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Intent intent = new Intent(context, BoardingPoint.class);
                                        intent.putExtra("number_plate", number_plate);
                                        context.startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });

        setBusDetails(bus, holder);
    }

    private void setBusDetails(ModelBus bus, BusHolder holder) {
        //get user info from uid in model
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Bus");
        ref.orderByChild("number_plate").equalTo(bus.getNumber_plate())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String number_plate = "" + ds.child("number_plate").getValue();
                            String time = "" + ds.child("time").getValue();
                            String price = "" + ds.child("price").getValue();
                            String rating = "" + ds.child("rating").getValue();
                            String seats = "" + ds.child("seats").getValue();
                            String bus_manufacturer = "" + ds.child("bus_manufacturer").getValue();

                            holder.mRating.setText(rating);
                            holder.mBusManufacturer.setText(bus_manufacturer);
                            holder.mTime.setText(time);
                            holder.mNumberPlate.setText(number_plate);
                            holder.mPrice.setText(price);
                            holder.mSeats.setText(seats + " seats available");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    class BusHolder extends RecyclerView.ViewHolder{
        private TextView mNumberPlate, mBusManufacturer, mTime, mPrice, mRating, mSeats;

        public BusHolder(@NonNull View itemView) {
            super(itemView);

            mNumberPlate = itemView.findViewById(R.id.number_plate);
            mBusManufacturer = itemView.findViewById(R.id.bus_manufacturer);
            mTime= itemView.findViewById(R.id.time);
            mPrice = itemView.findViewById(R.id.price);
            mRating = itemView.findViewById(R.id.rating);
            mSeats = itemView.findViewById(R.id.seats);
        }
    }
}

