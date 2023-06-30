package com.example.realstateexamapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.realstateexamapp.R;
import com.example.realstateexamapp.activity.app_bar.buy.BuyFragment;
import com.example.realstateexamapp.activity.app_bar.favourites.FavouritesFragment;
import com.example.realstateexamapp.activity.app_bar.home.HomeFragment;
import com.example.realstateexamapp.activity.app_bar.profile.ProfileFragment;
import com.example.realstateexamapp.activity.app_bar.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Fragment> fragments = new ArrayList<>(5);

    private static final String TAG_FRAGMENT_HOME = "HOME";
    private static final String TAG_FRAGMENT_SETTINGS = "SETTINGS";
    private static final String TAG_FRAGMENT_PROFILE = "PROFILE";
    private static final String TAG_FRAGMENT_FAVOURITES = "FAVOURITES";
    private static final String TAG_FRAGMENT_BUY = "BUY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.navigation_home) {
                        switchFragment(TAG_FRAGMENT_HOME);
                        return true;
                    } else if (item.getItemId() == R.id.navigation_profile) {
                        switchFragment(TAG_FRAGMENT_PROFILE);
                        return true;
                    } else if (item.getItemId() == R.id.navigation_favourites) {
                        switchFragment(TAG_FRAGMENT_FAVOURITES);
                        return true;
                    } else if (item.getItemId() == R.id.navigation_settings) {
                        switchFragment(TAG_FRAGMENT_SETTINGS);
                        return true;
                    }
                    return false;
                });

        buildFragmentsList();

        // Set the 0th Fragment to be displayed by default.
        switchFragment(TAG_FRAGMENT_HOME);


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view -> {
            switchFragment(TAG_FRAGMENT_BUY);
        });

    }

    private void buildFragmentsList() {
        fragments.add(buildFragment(TAG_FRAGMENT_HOME));
        fragments.add(buildFragment(TAG_FRAGMENT_PROFILE));
        fragments.add(buildFragment(TAG_FRAGMENT_BUY));
        fragments.add(buildFragment(TAG_FRAGMENT_FAVOURITES));
        fragments.add(buildFragment(TAG_FRAGMENT_SETTINGS));
    }

    private Fragment buildFragment(String tag) {
        Fragment fragment = null;

        switch (tag) {
            case TAG_FRAGMENT_HOME:
                fragment = new HomeFragment();
                break;
            case TAG_FRAGMENT_FAVOURITES:
                fragment = new FavouritesFragment();
                break;
            case TAG_FRAGMENT_BUY:
                fragment = new BuyFragment();
                break;
            case TAG_FRAGMENT_PROFILE:
                fragment = new ProfileFragment();
                break;
            case TAG_FRAGMENT_SETTINGS:
                fragment = new SettingsFragment();
                break;
            default:
                fragment = new HomeFragment();
                Log.e("Invalid tag", tag);
        }

        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int getPos(String tag) {
        switch (tag) {
            case TAG_FRAGMENT_HOME:
                return 0;
            case TAG_FRAGMENT_PROFILE:
                return 1;
            case TAG_FRAGMENT_BUY:
                return 2;
            case TAG_FRAGMENT_FAVOURITES:
                return 3;
            case TAG_FRAGMENT_SETTINGS:
                return 4;
            default:
                Log.e("Invalid tag", tag);
                return 0;
        }
    }

    private void switchFragment(String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_fragmentholder, fragments.get(getPos(tag)), tag)
                .commit();
    }
}
