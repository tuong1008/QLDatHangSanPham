package com.example.qldathangsanpham.ui.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;

public class MainActivity extends AppCompatActivity {

    ListView l;
    String menu[]
            = {"Quản lý Đơn đặt hàng", "Quản lý Thông tin khách hàng",
            "Quản lý Sản phẩm"};
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

//        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getLifecycle());
//        ViewPager2 pager = findViewById(R.id.pager);
//        pager.setAdapter(pagerAdapter);
//
//        TabLayout tabLayout = findViewById(R.id.tabs);
//        new TabLayoutMediator(tabLayout, pager,
//                (tab, position) -> tab.setText("OBJECT " + (position + 1))
//        ).attach();

        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                menu);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        l.setAdapter(arr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}