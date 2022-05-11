package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savala.fom.R;
import com.savala.fom.ViewMyself;
import com.savala.fom.ViewUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.ModelPotentials;

public class AdapterDepartureStation extends RecyclerView.Adapter<AdapterDepartureStation.DepartureStationHolder> {

    private Context context;
    private ArrayList<ModelPotentials> potentialList;

    //constructor


    public AdapterPotentials(Context context, ArrayList<ModelPotentials> potentialList) {
        this.context = context;
        this.potentialList = potentialList;
    }

    @NonNull
    @Override
    public PotentialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout (row_user)

        View view = LayoutInflater.from(context).inflate(R.layout.row_potential_request, parent, false);

        return new PotentialHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PotentialHolder holder, int position) {
        // get data
        final ModelPotentials potentials = potentialList.get(position);
        String senderUid = potentials.getUser_id();

        //click potential to open to user
        if(!senderUid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.potentialLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewUser.class);
                    intent.putExtra("uid", senderUid);
                    context.startActivity(intent);
                }
            });
        }else{
            holder.potentialLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewMyself.class);
                    context.startActivity(intent);
                }
            });
        }

        setPotentialDetails(potentials, holder);
    }

    private void setPotentialDetails(ModelPotentials potentials, PotentialHolder holder) {
        //get user info from uid in model
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_account_settings");
        ref.orderByChild("user_id").equalTo(potentials.getUser_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String potentialName = "" + ds.child("display_name").getValue();
                            String potentialImageOne = ""+ds.child("image_one").getValue();
                            String potentialImageTwo = ""+ds.child("image_two").getValue();
                            String potentialImageThree = ""+ds.child("image_three").getValue();

                            holder.mRowPotentialDisplayName.setText(potentialName);

                            try{
                                Picasso.get().load(potentialImageOne)
                                        .placeholder(R.drawable.ic_baseline_person_24)
                                        .into(holder.mRowPotentialImageOne);
                            }catch(Exception e){
                                holder.mRowPotentialImageOne.setImageResource(R.drawable.ic_baseline_person_24);
                            }
                            try{
                                Picasso.get().load(potentialImageTwo)
                                        .placeholder(R.drawable.ic_baseline_person_24)
                                        .into(holder.mRowPotentialImageTwo);
                            }catch(Exception e){
                                holder.mRowPotentialImageTwo.setImageResource(R.drawable.ic_baseline_person_24);
                            }
                            try{
                                Picasso.get().load(potentialImageThree)
                                        .placeholder(R.drawable.ic_baseline_person_24)
                                        .into(holder.mRowPotentialImageThree);
                            }catch(Exception e){
                                holder.mRowPotentialImageThree.setImageResource(R.drawable.ic_baseline_person_24);
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
        return potentialList.size();
    }

    class DepartureStationHolder extends RecyclerView.ViewHolder{
        private ImageView mRowPotentialImageOne, mRowPotentialImageTwo, mRowPotentialImageThree;
        private TextView mRowPotentialDisplayName, mRowPotentialDefaultText;
        private LinearLayout potentialLayout;

        public DepartureStationHolder(@NonNull View itemView) {
            super(itemView);

            mRowPotentialImageOne = itemView.findViewById(R.id.row_potential_image_one);
            mRowPotentialImageTwo = itemView.findViewById(R.id.row_potential_image_two);
            mRowPotentialImageThree = itemView.findViewById(R.id.row_potential_image_three);

            mRowPotentialDisplayName = itemView.findViewById(R.id.row_potential_display_name);
            mRowPotentialDefaultText = itemView.findViewById(R.id.default_text_potentials);

            potentialLayout = itemView.findViewById(R.id.potential_layout);
        }
    }
}
