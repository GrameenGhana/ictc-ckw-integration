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
public class CommunitiesAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] names;
    private final String[] farmers;

    public CommunitiesAdapter(Context c, String[] names, String[] farmers) {
        mContext = c;
        this.names = names;
        this.farmers = farmers;
    }

    public int getCount() {
        return names.length;
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
            grid = inflater.inflate(R.layout.community_list_single, null);

        } else {
            grid = (View) view;
        }
        TextView name = (TextView) grid.findViewById(R.id.textView_community);
        TextView farmer = (TextView) grid.findViewById(R.id.textView_farmers);
        name.setText(names[i]);
        farmer.setText(farmers[i]);
        return grid;
    }
}
