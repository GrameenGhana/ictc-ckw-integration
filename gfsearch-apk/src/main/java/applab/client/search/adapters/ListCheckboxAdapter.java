package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Farmer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by skwakwa on 8/30/15.
 */
public class ListCheckboxAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Farmer> farmers;
    private final boolean [] selected;
    String  [] colors = null;


    public ListCheckboxAdapter(Context c, List<Farmer> farmers,String  [] colors) {
        mContext = c;
        this.farmers = farmers;
        this.selected = new  boolean[farmers.size()];
        Arrays.fill(selected,false);
        this.colors = colors;

    } public ListCheckboxAdapter(Context c, List<Farmer> farmers) {
        mContext = c;
        this.farmers = farmers;
        this.selected = new  boolean[farmers.size()];
        Arrays.fill(selected,false);


    }

    public int getCount() {
        return farmers.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.list_checkbox_activity, null);

        } else {
            grid = (View) view;
        }
        CheckBox chk = (CheckBox) grid.findViewById(R.id.lst_chk);
        TextView farmer = (TextView) grid.findViewById(R.id.lst_farmer);
        ImageView tr = (ImageView) grid.findViewById(R.id.lst_farmer_initial);
        tr.setBackgroundColor(Color.parseColor(getColor()));
        //tr.setText(farmers.get(i).getFullname().substring(0,1));


        farmer.setText(farmers.get(i).getFullname());
        chk.setTag(i);

        chk.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        int pos = (Integer) cb.getTag();
                        System.out.println("List Item : "+pos);
                        Farmer fm = farmers.get(pos);
                        Toast.makeText(mContext,
                                "Clicked on Checkbox: " + fm.getFullname() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();

                        selected[pos] =cb.isChecked();
                    }
                });


//        farmer.setText(farmers.get(i).getFirstName());
        return grid;
    }


    public boolean[] getSelectedIndex(){
        return selected;

    }
    public String getColor(){
        return colors[(new Double(Math.floor(Math.random()*colors.length)).intValue())];
    }
}

