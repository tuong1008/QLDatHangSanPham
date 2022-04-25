package com.example.qldathangsanpham.ui.authentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.data.model.EmployeeModel;
import com.example.qldathangsanpham.databinding.ActivityFullscreenBinding;
import com.example.qldathangsanpham.ui.login.EmployeeViewModel;
import com.example.qldathangsanpham.ui.login.EmployeeViewModelFactory;
import com.example.qldathangsanpham.ui.login.LoginActivity;
import com.example.qldathangsanpham.ui.login.SaveSharedPreference;
import com.google.android.material.navigation.NavigationView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private View mControlsView;
    private EmployeeViewModel mViewModel;
    private boolean mVisible;

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    private ActivityFullscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mViewModel = new ViewModelProvider(this, new EmployeeViewModelFactory(this)).get(EmployeeViewModel.class);

        mVisible = true;
        mControlsView = binding.fullscreenContentControls;

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_menu_24);
       /* Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap,25,  25, true));*/

        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);


        // toolbar.setLogo(R.drawable.logo);

        /*ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");
        int id = intent.getIntExtra("id", -1);

        Fragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", userName);
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fullscreen_content_controls, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        ImageView imageView = binding.navView.getHeaderView(0).findViewById(R.id.imageView_nav);
        TextView textView = binding.navView.getHeaderView(0).findViewById(R.id.textViewHoTen_nav);

        MenuItem menuItem = navigationView.getMenu().getItem(3);
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.purple_700)), 0,
                spanString.length(), 0);
        menuItem.setTitle(spanString);

        //     binding.navView.getMenu().g
        EmployeeModel employeeModel = mViewModel.findByTaiKhoanID(id);
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(employeeModel.getImage(), 0, employeeModel.getImage().length);
        imageView.setImageBitmap(bitmapImage);
        textView.setText(employeeModel.getHoVaTen());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            case 2:
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = getIntent();

        removeColor(findViewById(R.id.nav_view));
        item.setChecked(true);
        switch (id) {
            case R.id.nav_profile:
                String userName = intent.getStringExtra("username");

                int idTaiKhoan = intent.getIntExtra("id", -1);
                Bundle bundle = new Bundle();
                bundle.putString("username", userName);
                bundle.putInt("id", idTaiKhoan);
                fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_security:
                String userName2 = intent.getStringExtra("username");
                int idTaiKhoan2 = intent.getIntExtra("id", -1);
                Bundle bundle2 = new Bundle();
                bundle2.putString("username", userName2);
                bundle2.putInt("id", idTaiKhoan2);
                fragment = new SecurityFragment();
                fragment.setArguments(bundle2);
                break;
            case R.id.nav_logout:
                SaveSharedPreference.logout(this);
                Intent intent3 = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent3);
                finish();
                break;

            default:
                fragment = new ProfileFragment();
        }
        if (fragment != null) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fullscreen_content_controls, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void removeColor(NavigationView view) {
        for (int i = 0; i < view.getMenu().size(); i++) {
            MenuItem item = view.getMenu().getItem(i);
            item.setChecked(false);
        }
    }

}