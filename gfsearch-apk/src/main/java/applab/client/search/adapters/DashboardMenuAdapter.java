package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class DashboardMenuAdapter extends BaseAdapter {

    private Context mContext;
    private final int[] Imageid;
    private final String[] titles;

    public DashboardMenuAdapter(Context c, int[] Imageid, String[] titles) {
        mContext = c;
        this.Imageid = Imageid;
        this.titles = titles;
    }

    @Override
    public int getCount() {

        return Imageid.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        System.out.println("pos : " + position);
        System.out.println("Image Length " + Imageid[position]);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);

        } else {
            grid = (View) convertView;
        }
       /* ImageView imageView = (ImageView) grid.findViewById(R.id.imageView_icon);
        TextView title = (TextView) grid.findViewById(R.id.textView_title);
        title.setText(titles[position]);
        imageView.setImageResource(Imageid[position]);
        imageView.setMaxHeight(250); */

        return grid;
    }

}