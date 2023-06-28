package project.greetiny.adapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import project.greetiny.R;


public class adapter extends FirebaseRecyclerAdapter<model, adapter.myviewholder> {
    private String currentUserUid;
    public adapter(@NonNull FirebaseRecyclerOptions<model> options, String currentUserUid) {
        super(options);
        this.currentUserUid = currentUserUid;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {
        if (model.getUserId().equals(currentUserUid)){

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

    public class myviewhozlder extends RecyclerView.ViewHolder {
        TextView nametext, type,copylink;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.recType);
            copylink = itemView.findViewById(R.id.copyLink);
        }
    }
}





