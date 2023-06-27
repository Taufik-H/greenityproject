package project.greetiny.adapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.card.MaterialCardView;

import project.greetiny.R;
import project.greetiny.fragment.FragmentList;


public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder> {
    private FragmentList context;
    private String currentUserUid;
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options, String currentUserUid) {
        super(options);
        this.currentUserUid = currentUserUid;
    }
    public interface dataListener{
        void onDeleteData(DataClass data, int position);
    }
    //Deklarasi Objek Interface
    dataListener listener;

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        if (model.getUserId().equals(currentUserUid)){
            holder.nametext.setText(model.getSubject());
            holder.listCard.setOnLongClickListener(v -> {
                final String[] action = {"Update","Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle(("Apa yang anda pilih?"));
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position){
                            case 0:
                                Bundle bundle = new Bundle();
                                Toast.makeText(v.getContext(), "Update", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(v.getContext(), FragmentList.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            case 1:
                                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                                alert.setTitle("Apakah anda yakin akan menghapus Data ni?");
                                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.onDeleteData(DataClass.get(position), position);
                                    }
                                })
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                return;
                                            }
                                        });
                                alert.create();
                                alert.show();
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            });

        }else {
            holder.nametext.setText("Data terfilter");
        }
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView nametext;
        MaterialCardView listCard;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.name);
            listCard = itemView.findViewById(R.id.reCard);
        }
    }
}





