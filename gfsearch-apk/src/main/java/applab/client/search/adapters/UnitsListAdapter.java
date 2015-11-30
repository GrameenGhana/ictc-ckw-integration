package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skwakwa on 11/24/15.
 */
public class UnitsListAdapter extends BaseAdapter {

    private Context mContext;
    public LayoutInflater minflater;


    String  [] contents = null;
    String  [] firstLetter =null;
    String  [] units =null;
    String  [] colors = null;
    boolean [] enabled = null;
    List<Farmer> farmers = new ArrayList<Farmer>();
    public int lastExpandedGroupPosition;

    public UnitsListAdapter(Context mContext,
                                     String  []  contents,
                                     String []  firstLetter,
                                     String []  units,


                                     String [] colors
    ) {
        this.mContext = mContext;
        this.contents = contents;
        this.colors = colors;
        System.out.println("Contents  : "+contents.length);
        this.firstLetter = firstLetter;
        this.units = units;
        enabled = new boolean[firstLetter.length];
        Arrays.fill(enabled, true);
        minflater = LayoutInflater.from(mContext);
    }

    public UnitsListAdapter(Context mContext,
                                     String  []  contents,
                                     String []  firstLetter,
                                     String  []  units ,
                                     boolean [] enabled,


                                     String [] colors
    ) {
        this.mContext = mContext;
        this.contents = contents;
        this.colors = colors;
        System.out.println("Contents  : "+contents.length);
        this.firstLetter = firstLetter;
        this.enabled = enabled;
        this.units = units;
        minflater = LayoutInflater.from(mContext);
    }
    public UnitsListAdapter(Context mContext,
                                     String  []  contents,
                                     String []  firstLetter,
                                     String  []  units ,
                                     boolean [] enabled,


                                     String [] colors, List<Farmer> farmers
    ) {
        this.mContext = mContext;
        this.contents = contents;
        this.colors = colors;
        System.out.println("Contents  : "+contents.length);
        this.firstLetter = firstLetter;
        this.enabled = enabled;
        this.farmers =farmers;
        minflater = LayoutInflater.from(mContext);
        this.units = units;
    }
    public UnitsListAdapter(Context mContext,
                                     String  []  contents,
                                     String []  firstLetter, String  []  units ,String color


    ) {
        this.mContext = mContext;
        this.contents = contents;;
        colors[0] =(color);
        this.firstLetter = firstLetter;
        Arrays.fill(enabled,true);
        this.units = units;
        minflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return contents.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.list_simple_text_text_with_sub, viewGroup, false);
        }
        System.out.println("getView");


        TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon);

        TextView unit = (TextView) view.findViewById(R.id.lst_txt_view_units);
        unit.setText(units[i]);
        icon.setText(firstLetter[i]);


        TextView title = (TextView) view.findViewById(R.id.lst_text_item_titles);

        if (!enabled[i]) {
            title.setTextColor(Color.parseColor("#cccccc"));
            icon.setBackgroundColor(Color.parseColor("#cccccc"));
        } else {
            icon.setBackgroundColor(Color.parseColor(getColor()));
            title.setTextColor(Color.parseColor("#666666"));
        }
        if (null == title) {
            System.out.println("Title is null");
        } else
            title.setText(contents[(i)]);
        return view;
    }

    public String getColor(){
        return colors[(new Double(Math.floor(Math.random()*colors.length)).intValue())];
    }
}



