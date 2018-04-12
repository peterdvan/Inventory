package com.example.android.inventory;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract;

public class EditInventory extends AppCompatActivity {
    private EditText productName;
    private EditText quantity;
    private EditText price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);
        productName = (EditText) findViewById(R.id.product_name);
        quantity = (EditText) findViewById(R.id.quantity);
        price = (EditText) findViewById(R.id.price);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.insert_data:
                insertItem();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    public void insertItem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = productName.getText().toString().trim();
        String quantityString = quantity.getText().toString().trim();
        String priceString = price.getText().toString().trim();

        if(nameString == null || quantityString == null || priceString == null) {
            throw new IllegalArgumentException();
        }
        int priceNumber = Integer.parseInt(priceString);
// Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_ITEM_NAME, nameString);
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantityString);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, priceNumber);
        values.put(InventoryContract.InventoryEntry.COLUMN_IMAGE, (String) null);
// Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getContentResolver().insert(InventoryContract.InventoryEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(getApplicationContext(), "Failed insertion",
                    Toast.LENGTH_LONG).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(getApplicationContext(), "Successfully Inserted",
                    Toast.LENGTH_LONG).show();
        }
    }
}
