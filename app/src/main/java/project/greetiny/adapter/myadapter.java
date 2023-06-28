package project.greetiny.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.FirebaseDatabase;

import project.greetiny.R;

import project.greetiny.fragment.FragmentList;
import project.greetiny.adapter.model;

public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder> {
    private String currentUserUid;
    private FragmentList context;

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options, String currentUserUid, FragmentList context) {
        super(options);
        this.currentUserUid = currentUserUid;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        if (model.getUserId().equals(currentUserUid)) {
            holder.nametext.setText(model.getSubject());
            holder.type.setText(model.getType());
            String websiteUrl = model.getWebsiteUrl();
            holder.copylink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboardManager = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("TextView", websiteUrl);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(view.getContext(), "Copied", Toast.LENGTH_SHORT).show();
                }
            });
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
                                    Bundle bundle = new Bundle();
                                    // Kirim data yang diperlukan ke UpdateActivity
                                    bundle.putString("dataId", getRef(holder.getAdapterPosition()).getKey());
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
                                                    // Hapus data dari Firebase berdasarkan kunci referensi yang disimpan di myviewholder
                                                    FirebaseDatabase.getInstance().getReference().child("kartu")
                                                            .child(getRef(holder.getAdapterPosition()).getKey())
                                                            .removeValue()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            })
                                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Tidak melakukan apa-apa jika tombol "Tidak" ditekan
                                                }
                                            });

                                    alertt.create().show();
                                    break;
                            }
                        }
                    });
                    alert.create().show();
                    return true;
                }
            });
        } else {
            holder.nametext.setText("Data terfilter");
        }
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new myviewholder(view);
    }

    public interface dataListener {
        void onDeleteData(DataClass data, int position);
    }

    public static class myviewholder extends RecyclerView.ViewHolder {
        TextView nametext, type;
        CardView cardView;
        MaterialCardView copylink;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.recType);
            cardView = itemView.findViewById(R.id.reCard);
            copylink = itemView.findViewById(R.id.copyLink);
        }
    }
}
