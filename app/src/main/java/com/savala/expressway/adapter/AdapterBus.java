package com.savala.expressway.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
                Intent intent = new Intent(context, ViewPack.class);
                intent.putExtra("packId", pack_id);
                context.startActivity(intent);
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
                            String route = "" + ds.child("route").getValue();

                            holder.mRating.setText(rating);
                            holder.mRoute.setText(route);
                            holder.mTime.setText(time);
                            holder.mNumberPlate.setText(number_plate);
                            holder.mPrice.setText(price);
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
        private TextView mNumberPlate, mRoute, mTime, mPrice, mRating;

        public BusHolder(@NonNull View itemView) {
            super(itemView);

            mNumberPlate = itemView.findViewById(R.id.number_plate);
            mRoute = itemView.findViewById(R.id.route);
            mTime= itemView.findViewById(R.id.time);
            mPrice = itemView.findViewById(R.id.price);
            mRating = itemView.findViewById(R.id.rating);
        }
    }
}

