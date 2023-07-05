package project.greetiny.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import project.greetiny.R;

import project.greetiny.Update.UpdateHariRaya;
import project.greetiny.Update.UpdateKelulusan;
import project.greetiny.Update.UpdateNikahan;
import project.greetiny.Update.UpdateTahunBaru;
import project.greetiny.Update.UpdateUlangTahun;
import project.greetiny.Update.UpdateValentine;
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
            Log.d("MyAdapter", "Image URL: " + model.getGambar());

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
                    final String[] action = {"Update","Delete"};
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Delete Kartu Ini?");
                    alert.setItems(action, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            switch (position) {
                                case 0:
                                    String cardId = getRef(holder.getAdapterPosition()).getKey();

                                    FirebaseDatabase.getInstance().getReference("kartu").child(cardId).child("type")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String cardType = dataSnapshot.getValue(String.class);
                                                    if (cardType != null) {
                                                        Intent intent;
                                                        switch (cardType) {
                                                            case "Ulang tahun":
                                                                intent = new Intent(v.getContext(), UpdateUlangTahun.class);
                                                                // Pass the necessary data fields for the Ulang Tahun card
                                                                intent.putExtra("cardId", cardId);
                                                                intent.putExtra("subject", model.getSubject());
                                                                intent.putExtra("tanggal", model.getTanggal());
                                                                intent.putExtra("image", model.getGambar());
                                                                intent.putExtra("ucapan", model.getUcapan());
                                                                v.getContext().startActivity(intent);


                                                                break;
                                                            case "Hari Raya":
                                                                intent = new Intent(v.getContext(), UpdateHariRaya.class);
                                                                // Pass the necessary data fields for the Hari Raya card
                                                                intent.putExtra("cardId", cardId);
                                                                intent.putExtra("subject", model.getSubject());
                                                                intent.putExtra("tanggal", model.getTanggal());
                                                                intent.putExtra("image", model.getGambar());
                                                                intent.putExtra("ucapan", model.getUcapan());
                                                                // Add more data fields specific to the Hari Raya card if needed
                                                                v.getContext().startActivity(intent);
                                                                break;
                                                            case "Kelulusan":
                                                                intent = new Intent(v.getContext(), UpdateKelulusan.class);
                                                                // Pass the necessary data fields for the Kelulusan card
                                                                intent.putExtra("cardId", cardId);
                                                                intent.putExtra("subject", model.getSubject());
                                                                intent.putExtra("tanggal", model.getTanggal());
                                                                intent.putExtra("image", model.getGambar());
                                                                intent.putExtra("ucapan", model.getUcapan());
                                                                // Add more data fields specific to the Kelulusan card if needed
                                                                v.getContext().startActivity(intent);
                                                                break;
                                                            case "Pernikahan":
                                                                intent = new Intent(v.getContext(), UpdateNikahan.class);
                                                                // Pass the necessary data fields for the Pernikahan card
                                                                intent.putExtra("cardId", cardId);
                                                                intent.putExtra("subject", model.getSubject());
                                                                intent.putExtra("tanggal", model.getTanggal());
                                                                intent.putExtra("image", model.getGambar());
                                                                intent.putExtra("ucapan", model.getUcapan());
                                                                // Add more data fields specific to the Pernikahan card if needed
                                                                v.getContext().startActivity(intent);
                                                                break;
                                                            case "Tahun Baru":
                                                                intent = new Intent(v.getContext(), UpdateTahunBaru.class);
                                                                // Pass the necessary data fields for the Tahun Baru card
                                                                intent.putExtra("cardId", cardId);
                                                                intent.putExtra("subject", model.getSubject());
                                                                intent.putExtra("tanggal", model.getTanggal());
                                                                intent.putExtra("image", model.getGambar());
                                                                intent.putExtra("ucapan", model.getUcapan());
                                                                // Add more data fields specific to the Tahun Baru card if needed
                                                                v.getContext().startActivity(intent);
                                                                break;
                                                            case "Valentine":
                                                                intent = new Intent(v.getContext(), UpdateValentine.class);
                                                                // Pass the necessary data fields for the Valentine card
                                                                intent.putExtra("cardId", cardId);
                                                                intent.putExtra("subject", model.getSubject());
                                                                intent.putExtra("tanggal", model.getTanggal());
                                                                intent.putExtra("image", model.getGambar());
                                                                intent.putExtra("ucapan", model.getUcapan());
                                                                // Add more data fields specific to the Valentine card if needed
                                                                v.getContext().startActivity(intent);
                                                                break;
                                                            default:
                                                                Toast.makeText(v.getContext(), "Pilih kartu ucapan dengan benar", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Handle database error
                                                }
                                            });
                                    break;
                                case 1:
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                                    alertDialogBuilder.setTitle("Apakah anda yakin akan menghapus Data ini?");
                                    alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
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

                                    alertDialogBuilder.create().show();
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
