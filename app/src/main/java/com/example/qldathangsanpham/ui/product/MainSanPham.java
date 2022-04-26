package com.example.qldathangsanpham.ui.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.qldathangsanpham.ui.product.chart.SanPhamChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
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

        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Nhập tên/xuất xứ sản phẩm");
        searchView.setMaxWidth(Integer.MAX_VALUE);

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
                Intent intent = new Intent(MainSanPham.this, SanPhamChart.class);
                intent.putExtra("LIST", (Serializable) sanPhamList);
                startActivity(intent);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return true;
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

    private void setupDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
    }

    private void initComponents() {
        db = new DatabaseHelper(this);
        sanPhamList = db.getAllSanPham();

        drawer = findViewById(R.id.drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_hamburger_menu);
        setSupportActionBar(toolbar);

        NavigationView nvDrawer = findViewById(R.id.nv_nav_view);
        setupDrawer(nvDrawer);

        list = findViewById(R.id.list);
        add = findViewById(R.id.btnInsert);

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