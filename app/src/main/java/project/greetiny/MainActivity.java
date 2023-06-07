package project.greetiny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import project.greetiny.adapter.AdapterViewPager;
import project.greetiny.fragment.FragmentHome;
import project.greetiny.fragment.FragmentList;
import project.greetiny.fragment.fragmentSetting;

public class MainActivity extends AppCompatActivity {
    ViewPager2 pagerMain;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    BottomNavigationView buttonNav;
    FirebaseAuth mAuth;
    com.google.android.material.card.MaterialCardView card1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerMain = findViewById(R.id.pagerMain);
        buttonNav = findViewById(R.id.buttonNav);
        fragmentArrayList.add(new FragmentHome());
        fragmentArrayList.add(new FragmentList());
        fragmentArrayList.add(new fragmentSetting());

        AdapterViewPager adapterViewPager = new AdapterViewPager(this,fragmentArrayList);
        pagerMain.setAdapter(adapterViewPager);
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){

                    case 0:
                        buttonNav.setSelectedItemId(R.id.ithome);
                        break;
                    case 1:
                        buttonNav.setSelectedItemId(R.id.itlist);
                        break;
                    case 2:
                        buttonNav.setSelectedItemId(R.id.itsetting);
                        break;
                }
                super.onPageSelected(position);
            }
        });
        buttonNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ithome:
                        pagerMain.setCurrentItem(0);
                        break;
                    case R.id.itlist:
                        pagerMain.setCurrentItem(1);
                        break;
                    case R.id.itsetting:
                        pagerMain.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });


    }
}