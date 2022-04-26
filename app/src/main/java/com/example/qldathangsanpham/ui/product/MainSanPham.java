package com.example.qldathangsanpham.ui.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;
import com.example.qldathangsanpham.model.SanPham;
import com.example.qldathangsanpham.ui.customer.CustomerActivity;
import com.example.qldathangsanpham.ui.order.OrderActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainSanPham extends AppCompatActivity {
    private static final String TAG = MainSanPham.class.getName();

    List<SanPham> sanPhamList;
    DatabaseHelper db;

    SanPhamAdapter adapter;
    ListView list;
    FloatingActionButton add;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_main);

        NavigationView navView = findViewById(R.id.nv_nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_hamburger_menu);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer);

        NavigationView nvDrawer = findViewById(R.id.nv_nav_view);

        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Log.d(TAG, "Drawer clicked");

                        Intent intent;
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.nav_order_activity:
                                intent = new Intent(MainSanPham.this, OrderActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_customer_activity:
                                intent = new Intent(MainSanPham.this, CustomerActivity.class);
                                startActivity(intent);
                                break;
                        }
                        drawer.closeDrawers();
                        return true;
                    }
                });

        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setList(sanPhamList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem search = menu.findItem(R.id.search);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) search.getActionView();

//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Nhập tên/xuất xứ sản phẩm");
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.action_view_chart:
                Log.d(TAG, "Clicking view chart");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    ActivityResultLauncher<Intent> sanPhamFormLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        sanPhamList = db.getAllSanPham();
                        adapter.setList(sanPhamList);
                    }
                }
            });

    private void initComponents() {
        db = new DatabaseHelper(this);
        sanPhamList = db.getAllSanPham();

        list = findViewById(R.id.list);
        add = findViewById(R.id.btnInsert);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        adapter = new SanPhamAdapter(this, sanPhamList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainSanPham.this, FormSanPham.class);
                intent.putExtra("SAN_PHAM", sanPhamList.get(position));

                sanPhamFormLauncher.launch(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FormSanPham.class);
                sanPhamFormLauncher.launch(intent);
            }
        });
    }
}