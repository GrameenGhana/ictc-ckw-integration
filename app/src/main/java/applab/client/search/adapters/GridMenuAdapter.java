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
public class GridMenuAdapter extends BaseAdapter {

    private Context mContext;
    private final int[] Imageid;
    private final String[] titles;

    public GridMenuAdapter(Context c, int[] Imageid, String[] titles) {
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.meeting_grid_single, null);

        } else {
            grid = (View) convertView;
        }
        ImageView imageView = (ImageView) grid.findViewById(R.id.thumb);
        TextView title = (TextView) grid.findViewById(R.id.text);
        title.setText(titles[position]);
        imageView.setImageResource(Imageid[position]);
        //imageView.setMaxHeight(130);

        return grid;
    }

}