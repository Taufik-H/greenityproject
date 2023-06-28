package project.greetiny.adapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import project.greetiny.R;
import project.greetiny.fragment.FragmentList;


public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder> {
    private String currentUserUid;
    private FragmentList context;
    private ArrayList<DataClass> listData;
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options, String currentUserUid) {
        super(options);
        this.currentUserUid = currentUserUid;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        if (model.getUserId().equals(currentUserUid)){

            holder.nametext.setText(model.getSubject());
            holder.type.setText(model.getType());
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    final String[] action = {"Update", "Delete"};
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Apa yang akan anda pilih?");
                    alert.setItems(action, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            switch (position) {
                                case 0:
                                /*
                                Berpindah Activity pada halaman layout UpdateData dan
                                mengambil data pada ListMahasiswa
                                 */

                                    Bundle bundle = new Bundle();

                                    Intent intent = new Intent(v.getContext(), UpdateActivity.class);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    break;

                                case 1:
                                    AlertDialog.Builder alertt = new AlertDialog.Builder(v.getContext());
                                    alertt.setTitle("Apakah anda yakin akan menghapus Data ini?");
                                    alertt.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    listener.onDeleteData(listData.get(position), position);
                                                }
                                            })
                                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    return;
                                                }
                                            });

                                    alertt.create();
                                    alertt.show();

                            }
                        }
                    });
                    alert.create();
                    alert.show();
                    return true;
                }
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

    public interface dataListener{
        void onDeleteData(DataClass data, int position);
    }
    //Deklarasi Objek Interface
    dataListener listener;

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView nametext, type;
        CardView cardView;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.recType);
            cardView = itemView.findViewById(R.id.reCard);
        }
    }
}





