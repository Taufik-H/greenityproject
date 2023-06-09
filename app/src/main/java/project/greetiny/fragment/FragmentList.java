package project.greetiny.fragment;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import project.greetiny.MainActivity;
import project.greetiny.R;
import project.greetiny.adapter.OnFragmentScrollListener;
import project.greetiny.adapter.model;
import project.greetiny.adapter.myadapter;


public class FragmentList extends Fragment  {

    String subject;
    ShimmerFrameLayout shimmerFrameLayout;
    myadapter adapter;
    private TextView emptyTextView;

    boolean isDataLoaded = false;

    public FragmentList() {

    }

    public FragmentList(String subject) {
        this.subject = subject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycleview);
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        emptyTextView = view.findViewById(R.id.emptyText);
        // Set FragmentList sebagai listener scroll

        if (currentUser != null) {

            String currentUserUid = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kartu");
            Query query = databaseReference.orderByChild("userId").equalTo(currentUserUid);
            FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                    .setQuery(query, model.class)
                    .build();

            adapter = new myadapter(options, currentUserUid,this);
            recyclerView.setAdapter(adapter);

            // Listener untuk memeriksa apakah data telah dimuat
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        adapter.startListening();
                        recyclerView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        emptyTextView.setVisibility(View.GONE);
                    }else {
                        recyclerView.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        emptyTextView.setVisibility(View.VISIBLE);
                        emptyTextView.setText("Tidak ada kartu tersedia");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                }
            };

            query.addValueEventListener(valueEventListener);
        } else {
            recyclerView.setVisibility(View.GONE);
            // Tampilkan pesan atau tindakan lain jika user tidak login
        }

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isDataLoaded) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isDataLoaded) {
            adapter.stopListening();
        }
    }

    public void onBackPressed()
    {

        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().popBackStack();
    }
    }
