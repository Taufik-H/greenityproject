package project.greetiny.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import project.greetiny.fragment.FragmentList;

public class    AdapterViewPager extends FragmentStateAdapter {
    ArrayList<Fragment> arr;
    FragmentList context;
    private String currentUserUid;
    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> arr) {
        super(fragmentActivity);
        this.currentUserUid = currentUserUid;
        this.arr = arr;
        this.context = context;
        listener = (dataListener) context;
    }

    public interface dataListener{
        void onDeleteData(DataClass data, int position);
    }
    //Deklarasi Objek Interface
    dataListener listener;

    @Override
    protected void onBindViewHolder(@NonNull myadapter.myviewholder holder, int i, @NonNull model model) {
        if (model.getUserId().equals(currentUserUid)){
            holder.nametext.setText(model.getSubject());
            holder.ListItem.setOnLongClickListener(v -> {
                final String[] action = {"Update","Delete?"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Apa yang akan anda pilih?");
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0 :
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent(v.getContext(), FragmentList.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1 :
                                AlertDialog.Builder alertt = new AlertDialog.Builder(v.getContext());
                                alertt.setTitle("Apakah anda akan menghapus kartu ini?");
                                alertt.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                listener.onDeleteData((DataClass) FragmentList.get(i),i);
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

            });
        }else {
            holder.nametext.setText("Data terfilter");
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arr.get(position);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }


}
