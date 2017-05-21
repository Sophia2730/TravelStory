package com.example.sophia.travelstory.Detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.sophia.travelstory.R;

public class DetailActivity extends AppCompatActivity {

    RecodeFragment recodeFragment;
    AlbumFragment albumFragment;
    Fragment fragment;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recodeFragment = new RecodeFragment();
        albumFragment = new AlbumFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, recodeFragment).commit();

        bottomNavigation = (BottomNavigationView)findViewById(R.id.navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.navigation_recode:
                        fragment = new RecodeFragment();
                        break;
                    case R.id.navigation_album:
                        fragment = new AlbumFragment();
                        break;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment).commit();
                return true;
            }
        });
    }
}
