package com.example.qldathangsanpham.ui.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qldathangsanpham.DatabaseHelper;
import com.example.qldathangsanpham.R;

public class CustomerActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor customersCursor;
    public static  final String EXTRA_MAKH = "maKH";

    public void onClickAdd(View view){
        Intent intent = new Intent(this, CustomerFormActivity.class);
        intent.putExtra(EXTRA_MAKH, -1);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setCustomersListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor newCursor = db.query("HoSoKhachHang", new String[]{"_id", "hoTen"},
                null, null, null, null, null);

        ListView listFavorites = findViewById(R.id.list);
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
        adapter.changeCursor(newCursor);
        customersCursor = newCursor;
    }


    private void setCustomersListView(){
        ListView listCustomers = findViewById(R.id.list);
        try{
            SQLiteOpenHelper angDoDatabaseHelper = new DatabaseHelper(this);
            db = angDoDatabaseHelper.getReadableDatabase();
            customersCursor = db.query("HoSoKhachHang", new String[]{"_id", "hoTen"},
                    null, null, null, null, null);
            CursorAdapter customerAdapter =
                    new SimpleCursorAdapter(CustomerActivity.this, android.R.layout.simple_list_item_1, customersCursor, new String[]{"hoTen"}, new int[]{android.R.id.text1},0);
            listCustomers.setAdapter(customerAdapter);

//            String tableName = "KhachHang";
//            Log.d("LogCursor", "getTableAsString called");
//            String tableString = String.format("Table %s:\n", tableName);
//            Cursor allRows  = customersCursor;
//            if (allRows.moveToFirst() ){
//                String[] columnNames = allRows.getColumnNames();
//                do {
//                    for (String name: columnNames) {
//                        tableString += String.format("%s: %s\n", name,
//                                allRows.getString(allRows.getColumnIndex(name)));
//                    }
//                    tableString += "\n";
//
//                } while (allRows.moveToNext());
//                Log.d("LogCursor", tableString);
//            }
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
            e.printStackTrace();
        }
        listCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CustomerActivity.this, CustomerFormActivity.class);
                intent.putExtra(EXTRA_MAKH, (int) id);
                startActivity(intent);
            }
        });
    }


}