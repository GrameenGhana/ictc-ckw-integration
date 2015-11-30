package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.model.wrapper.ItemWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skwakwa on 11/27/15.
 */
public class ParentListAdapter  extends BaseAdapter {

    private Context mContext;
    public LayoutInflater minflater;

    List<ItemWrapper> items= null;
    public int lastExpandedGroupPosition;

    public ParentListAdapter(Context mContext,
                           List<ItemWrapper> items
    ) {
        this.mContext = mContext;
       this.items = items;
        minflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = minflater.inflate(R.layout.lst_table, viewGroup, false);
        }
        ItemWrapper farmer = items.get(i);
        TextView key = (TextView) view.findViewById(R.id.txt_tab_key);
        TextView val = (TextView) view.findViewById(R.id.txt_tab_value);


        if(farmer.getKey().equalsIgnoreCase("")){


            key.setText(String.valueOf(farmer.getValue()));
            key.setTextColor(Color.parseColor("#ff0e4918"));
            key.setTypeface(null, Typeface.BOLD);
            val.setText("");
        }else {
            key.setText(farmer.getKey());

            val.setText(String.valueOf(farmer.getValue()));
        }return view;
    }

}


