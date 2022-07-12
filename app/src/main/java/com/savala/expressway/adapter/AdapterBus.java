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

import java.util.ArrayList;

public class AdapterBus extends RecyclerView.Adapter<AdapterBus.PackHolder> {

    private Context context;
    private ArrayList<ModelMyPacks> myPacksList;
    private int memberCount = 0;

    //constructor
    public AdapterBus(Context context, ArrayList<ModelBus> myPacksList) {
        this.context = context;
        this.myPacksList = myPacksList;
    }

    @NonNull
    @Override
    public PackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (row_user)

        View view = LayoutInflater.from(context).inflate(R.layout.row_pack, parent, false);

        return new PackHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PackHolder holder, int position) {
        // get data
        final ModelMyPacks myPacks = myPacksList.get(position);
        String pack_id = myPacks.getPack_id();
        String user_id = myPacks.getUser_id();
        String alpha = myPacks.getAlpha();

        //click potential to open to user
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPack.class);
                intent.putExtra("packId", pack_id);
                context.startActivity(intent);
            }
        });

        setMyPacksFromPacks(myPacks, holder);
        setMyPacksFromUser(myPacks, holder);
        getMemberCount(myPacks, holder);
    }

    private void getMemberCount(ModelMyPacks myPacks, PackHolder holder) {

        FirebaseDatabase.getInstance().getReference("my_packs_members_count")
                .child(myPacks.getPack_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            memberCount++;
                        }
                        holder.mRowPackMemberCount.setText(String.valueOf(memberCount));

                        memberCount = 0;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setMyPacksFromPacks(ModelMyPacks myPacks, PackHolder holder) {
        FirebaseDatabase.getInstance().getReference("packs")
                .orderByChild("pack_id").equalTo(myPacks.getPack_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){

                            String packName = "" + ds.child("pack_name").getValue();

                            holder.mRowPackName.setText(packName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setMyPacksFromUser(ModelMyPacks myPacks, PackHolder holder) {
        //get user info from uid in model
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_account_settings");
        ref.orderByChild("user_id").equalTo(myPacks.getAlpha())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String alphaName = "" + ds.child("display_name").getValue();
                            String alphaUid = "" + ds.child("user_id").getValue();

                            if(!alphaUid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                holder.mRowPackAlphaName.setText(alphaName);
                            }else{
                                holder.mRowPackAlphaName.setText("Me");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return myPacksList.size();
    }

    class PackHolder extends RecyclerView.ViewHolder{
        private TextView mRowPackName, mRowPackAlphaName, mRowPackMemberCount;

        public PackHolder(@NonNull View itemView) {
            super(itemView);

            mRowPackAlphaName = itemView.findViewById(R.id.row_pack_display_name_alpha);
            mRowPackName = itemView.findViewById(R.id.row_pack_display_name);
            mRowPackMemberCount = itemView.findViewById(R.id.row_pack_member_count);
        }
    }
}

