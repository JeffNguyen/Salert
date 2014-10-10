package com.example.salert;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "itemsManager";
	private static final String TABLE_ITEMS = "items";
	
	private static final String KEY_ID = "id";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_IMG_URL = "imgURL";

	
	public DatabaseHandler(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		onCreate(db);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "(" + 
				KEY_ID + " INTEGER PRIMARY KEY, " + 
				KEY_DESCRIPTION + " TEXT, " + 
				KEY_IMG_URL + " TEXT " + ")";
		db.execSQL(CREATE_PRODUCTS_TABLE);
	}
	
	// add new product
	public void addItem(Item item)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_DESCRIPTION, item.getDescription());
		values.put(KEY_IMG_URL, item.getImgUrl());
		
		db.insert(TABLE_ITEMS, null, values);
	}

	// reading rows
	public Item getItem(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_ITEMS, 
				new String[] {KEY_DESCRIPTION, KEY_IMG_URL},
				KEY_ID + "=?", 
				new String[] {String.valueOf(id)},
				null, null, null, null);
		
		if(cursor != null)
		{
			cursor.moveToFirst();
		}
		
		Item item = new Item(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		return item;
	}
	
	public ArrayList<Item> getAllItems()
	{
		ArrayList<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) 
        {
            do
            {
                Item item = new Item();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setDescription(cursor.getString(1));
                item.setImgUrl(cursor.getString(2));
                itemList.add(item);
            }while(cursor.moveToNext());

        }
 
        return itemList;
	}
	
	
	public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION, item.getDescription());
        values.put(KEY_IMG_URL, item.getImgUrl());
 
        // updating row
        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
    }
 
    // Deleting single contact
    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getID()) });
    }
 
 
    // Getting contacts Count
    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
 
        // return count
        return count;
    }
    
    public void dropTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

    	db.execSQL("DROP TABLE " + DATABASE_NAME);
    }
}
