package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.project1.Fragment.BookFragment;
import com.example.project1.Fragment.CartFragment;
import com.example.project1.Fragment.HomeFragment;
import com.example.project1.Fragment.ProfileFragment;


public class UserActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        bottomNavigation = findViewById(R.id.bottom_nav);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_book_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_profile_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        fragment = new BookFragment();
                        break;
                    case 3:
                        fragment = new CartFragment();
                        break;
                    case 4:
                        fragment = new ProfileFragment() ;
                        break;
                }
                loadFragment(fragment);
            }
        });
        //count cart
        bottomNavigation.setCount(3, "10");
        //home selected
        bottomNavigation.show(1, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(),
//                        "You Click "+item.getId(),Toast.LENGTH_SHORT).show();

            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });


    }

    private void loadFragment(Fragment fragment) {
        //replace
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment, "main_fragment")
                .commit();
    }


}
