package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import applab.client.search.R;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class FarmersAdapter extends BaseAdapter {
    private final LayoutInflater minflater;
    private Context mContext;
    String[] name;
    String[] location;
    String[] mainCrop;
    String[] groups;

    public FarmersAdapter(Context mContext,
                          String[] name,
                          String[] location,
                          String[] mainCrop,
                          String[] groups) {

        this.name = name;
        this.location = location;
        this.mainCrop = mainCrop;
        this.groups = groups;
        minflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return name.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.cluster_child_single, viewGroup, false);
        }
        TextView names = (TextView) view.findViewById(R.id.textView_name);
        TextView locations = (TextView) view.findViewById(R.id.textView_location);
//        TextView mainCrops = (TextView) view.findViewById(R.id.textView_mainCrop);
        TextView group = (TextView) view.findViewById(R.id.textView_groups);
        names.setText(name[i]);
        locations.setText(location[i]);
      //  mainCrops.setText(mainCrop[i]);
        group.setText(groups[i]);
        return view;
    }
}
