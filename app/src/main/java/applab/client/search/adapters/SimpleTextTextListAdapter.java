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
import applab.client.search.utils.IctcCKwUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class SimpleTextTextListAdapter  extends BaseAdapter {

    private Context mContext;
    public LayoutInflater minflater;


    String  [] contents = null;
    String  [] firstLetter =null;
    String  [] colors = null;
    boolean [] enabled = null;
List<Farmer> farmers = new ArrayList<Farmer>();
    public int lastExpandedGroupPosition;

    public SimpleTextTextListAdapter(Context mContext,
                                    String  []  contents,
                                     String []  firstLetter,

                                    String [] colors
    ) {
        this.mContext = mContext;
        this.contents = contents;
        this.colors = colors;
        System.out.println("Contents  : "+contents.length);
        this.firstLetter = firstLetter;
        enabled = new boolean[firstLetter.length];
        Arrays.fill(enabled,true);
        minflater = LayoutInflater.from(mContext);
    }

    public SimpleTextTextListAdapter(Context mContext,
                                     String  []  contents,
                                     String []  firstLetter,
                                     boolean [] enabled,


                                     String [] colors
    ) {
        this.mContext = mContext;
        this.contents = contents;
        this.colors = colors;
        System.out.println("Contents  : "+contents.length);
        this.firstLetter = firstLetter;
        this.enabled = enabled;
        minflater = LayoutInflater.from(mContext);
    }
    public SimpleTextTextListAdapter(Context mContext,
                                       String  []  contents,
                                       String []  firstLetter,
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
    }
    public SimpleTextTextListAdapter(Context mContext,
                                     String  []  contents,
                                     String []  firstLetter,String color


    ) {
        this.mContext = mContext;
        this.contents = contents;;
        colors[0] =(color);
        this.firstLetter = firstLetter;
        Arrays.fill(enabled,true);
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
            view = minflater.inflate(R.layout.list_simple_text_text_, viewGroup, false);
        }
        System.out.println("getView");


            TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon);


            icon.setText(contents[(i)].substring(0, 1));


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
        return colors[(Double.valueOf(Math.floor(Math.random()*colors.length)).intValue())];
    }
}

