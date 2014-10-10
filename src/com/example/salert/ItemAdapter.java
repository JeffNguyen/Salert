package com.example.salert;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item>{
	
	Context context;
	int layoutResourceId;    
	ArrayList<Item> data;
	DatabaseHandler db;
    
    public ItemAdapter(Context context, int layoutResourceId, ArrayList<Item> data) 
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        db = new DatabaseHandler(context);
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) 
    {
        Log.d("calling getView","");
    	View row = convertView;
        ItemHolder holder = null;
        
        if(row == null)
        {
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ItemHolder();
            
            if(layoutResourceId == R.layout.list_view)
            {
            	holder.imgUrl = (ImageView)row.findViewById(R.id.img);
            	holder.description = (TextView)row.findViewById(R.id.label);
            	holder.button = (Button)row.findViewById(R.id.addButton);
            }
            
            else
            {
            	holder.imgUrl = (ImageView)row.findViewById(R.id.img2);
            	holder.description = (TextView)row.findViewById(R.id.track_label);
            	holder.button = (Button)row.findViewById(R.id.deleteButton);

            }
            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }
      
        Item item = data.get(position);
        holder.description.setText(item.getDescription());
        Glide.load(item.getImgUrl()).placeholder(R.drawable.ic_launcher).into(holder.imgUrl);

        if(layoutResourceId != R.layout.list_view)
        {
            holder.button.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v) 
                {
                    Item temp = data.remove(position);
                    db.deleteItem(temp);
                    notifyDataSetChanged(); 
                }   
 
            });
        }
        return row;
    }
    
    public void updateData(ArrayList<Item> data)
    {
    	this.data = data;
    	notifyDataSetChanged();
    }
    static class ItemHolder
    {
		ImageView imgUrl;
        TextView description;
        Button button;
    }

    public void remove(int position)
    {
    	data.remove(position);
    	notifyDataSetChanged();
    }
    
}