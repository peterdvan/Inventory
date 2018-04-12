package com.example.android.inventory.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Peter on 3/21/2018.
 */

public final class InventoryContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INVENTORY = "inventory";
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InventoryContract() {
    }

    public static final class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        public static final String TABLE_NAME = "inventory";

        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_ITEM_NAME = "name";


        public final static String COLUMN_PRICE = "price";

        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_IMAGE = "image";
    }
}
