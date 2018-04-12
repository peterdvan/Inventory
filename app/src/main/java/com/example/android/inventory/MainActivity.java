package com.example.android.inventory;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.android.inventory.data.InventoryContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    InventoryCursorAdapter cursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cursorAdapter = new InventoryCursorAdapter(this,null);
        ListView listView = (ListView) findViewById(R.id.list);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        listView.setAdapter(cursorAdapter);
        getLoaderManager().initLoader(0,null,this);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.shop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.insert_dummy:
                insertDummyData();
                break;
            case R.id.insert_data:
                addItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertDummyData() {
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_ITEM_NAME, "Toto");
        values.put(InventoryContract.InventoryEntry.COLUMN_IMAGE, "Terrier");
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE,1);
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY,1);
        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);
    }
    private void addItem() {
        Intent intent = new Intent(MainActivity.this,EditInventory.class);
        startActivity(intent);
    }

    private void displayDatabaseInfo() {
        String[] Projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_ITEM_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRICE,
                InventoryContract.InventoryEntry.COLUMN_IMAGE,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY
        };
        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = getContentResolver().query(
                InventoryContract.InventoryEntry.CONTENT_URI,
                Projection,null,null,null);
        ListView listView = (ListView) findViewById(R.id.list);
        InventoryCursorAdapter cursorAdapter = new InventoryCursorAdapter(this,cursor);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_ITEM_NAME,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_IMAGE,
                InventoryContract.InventoryEntry.COLUMN_PRICE
        };
        return new CursorLoader(this, InventoryContract.InventoryEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
