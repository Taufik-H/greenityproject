package project.greetiny.adapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import project.greetiny.R;
import project.greetiny.fragment.FragmentList;


public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder> {
    private String currentUserUid;
    private DataClass   context;
    private static DatabaseReference reference;
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options, String currentUserUid) {
        super(options);
        this.currentUserUid = currentUserUid;
    }

    public interface dataListener{
        default void onDeleteData(DataClass data, int position) {
            /*
            Kode ini akan dipanggil ketika method OndeleteData dipanggil dari adapter
            pada recycleview melalui interface
             */
            if (reference != null) {
                reference.child("Admin")
                        .child("Mahasiswa")
                        .child(data.getKey())
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
    }
    //Deklarasi Objek Interface
    dataListener listener;

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int i, @NonNull model model) {

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView nametext;
        RelativeLayout ListItem;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.name);
            ListItem = itemView.findViewById(R.id.reCard);
        }
    }
}





