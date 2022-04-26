package com.example.qldathangsanpham.ui.order;

import androidx.annotation.NonNull;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.ui.authentication.FullscreenActivity;
import com.example.qldathangsanpham.ui.customer.CustomerActivity;
import com.example.qldathangsanpham.ui.product.MainSanPham;
import com.google.android.material.navigation.NavigationView;

public class OrderActivity extends AppCompatActivity {


    private SQLiteDatabase db;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        NavigationView navView = findViewById(R.id.nv_nav_view);
        Toolbar toolbar = findViewById(R.id.t_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_hamburger_menu);
        setSupportActionBar(toolbar);
        mDrawer = findViewById(R.id.dl_drawer);


        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(mDrawer).build();

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);


        //drawer
        NavigationView nvDrawer = findViewById(R.id.nv_nav_view);
        mDrawer = findViewById(R.id.dl_drawer);
        setupDrawerContent(nvDrawer);

        //default
//        changeFragment(new OrderHomeFragment());

        navController.navigate(R.id.fragment_order_home);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_order_activity:
                                Intent intent = new Intent(OrderActivity.this, OrderActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_customer_activity:
                                Intent intent1 = new Intent(OrderActivity.this, CustomerActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.nav_product_activity:
                                Intent intent2 = new Intent(OrderActivity.this, MainSanPham.class);
                                startActivity(intent2);
                                break;
                        }
                        mDrawer.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_edit_profile:
                Intent intent = new Intent(OrderActivity.this, FullscreenActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

}