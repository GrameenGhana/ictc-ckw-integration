package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import applab.client.search.R;

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
    public int lastExpandedGroupPosition;

    public SimpleTextTextListAdapter(Context mContext,
                                    String  []  contents,
                                     String []  firstLetter,

                                    String [] colors
    ) {
        this.mContext = mContext;
        this.contents = contents;
        this.colors = colors;
        this.firstLetter = firstLetter;
        minflater = LayoutInflater.from(mContext);
    }
    public SimpleTextTextListAdapter(Context mContext,
                                     String  []  contents,
                                     String []  firstLette,String color


    ) {
        this.mContext = mContext;
        this.contents = contents;;
        colors[0] =(color);
        this.firstLetter = firstLetter;
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
        TextView title = (TextView) view.findViewById(R.id.lst_text_item_title);
        TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon);

        icon.setBackgroundColor(Color.parseColor(getColor()));
        icon.setText(firstLetter[i]);
        title.setText(contents[(i)]);
        return view;
    }

    public String getColor(){
        return colors[(new Double(Math.floor(Math.random()*colors.length)).intValue())];
    }
}

