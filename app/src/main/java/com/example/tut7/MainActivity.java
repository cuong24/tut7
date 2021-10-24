package com.example.tut7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVisible(R.id.addLayout, false);
        showCurrencyList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dbManager.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

  private void setVisible(int id, boolean isVisible) {
        View view = findViewById(id);
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void showCurrencyList() {
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        setVisible(R.id.addLayout, false);
        if (cursor.getCount() == 0) {
            setVisible(R.id.noRecordText, true);
            setVisible(R.id.list, false);
        } else {
            ListView listView = (ListView) findViewById(R.id.list);
            setVisible(R.id.noRecordText, false);

            setVisible(R.id.list, true);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this, R.layout.activity_view_record, cursor,
                    new String[]{
                            DatabaseHelper.ID,
                            DatabaseHelper.COUNTRY,
                            DatabaseHelper.CURRENCY
                    },
                    new int[]{R.id.lId, R.id.lCountry, R.id.lCurrency}, 0);
            adapter.notifyDataSetInvalidated();
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Are you sure you want to delete this item?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onDeleteItem((int) id);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
            });
        }
    }

    public void onAddClick(View view) {
        setVisible(R.id.list, false);
        setVisible(R.id.noRecordText, false);
        setVisible(R.id.addLayout, true);
    }

    public void onSubmitCurrency(View view) {
        EditText country = findViewById(R.id.tCountry);
        EditText currency = findViewById(R.id.tCurrency);
        String countryText = country.getText().toString();
        String currencyText = currency.getText().toString();

        if(!countryText.equals("") && !currencyText.equals("")){
            dbManager.insert(country.getText().toString(), currency.getText().toString());
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Please input both country and currency")
                    .create().show();
        }

        country.setText("");
        currency.setText("");
        setVisible(R.id.addLayout, false);
        showCurrencyList();
    }

    public void onSubmitFormCancel(View view) {
        EditText country = findViewById(R.id.tCountry);
        EditText currency = findViewById(R.id.tCurrency);

        country.setText("");
        currency.setText("");
        setVisible(R.id.addLayout, false);
        showCurrencyList();
    }

    public void onDeleteItem(int id) {
        dbManager.delete(id);
        showCurrencyList();
    }
}