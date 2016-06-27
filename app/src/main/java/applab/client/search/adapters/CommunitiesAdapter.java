package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;
import applab.client.search.model.Farmer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class CommunitiesAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private String[] names;
    private final String[] originalData;
    private final String[] farmers;
    List<Farmer> farmerList= new ArrayList<Farmer>();
    private ItemFilter mFilter = new ItemFilter();

    public CommunitiesAdapter(Context c, String[] names, String[] farmers) {
        mContext = c;
        this.names = names;
        this.originalData=names;
        this.farmers = farmers;
    }
    public CommunitiesAdapter(Context c, String[] names, String[] farmers,List<Farmer> fs) {
        mContext = c;
        this.names = names;
        this.farmers = farmers;
        this.farmerList =fs;
        this.originalData=names;
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

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final String[] list = originalData;

            int count = list.length;
            String[] nlist = new String[count];

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list[i];
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist=new String[]{filterableString};
                }
            }

            results.values = nlist;
            results.count = nlist.length;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            names = (String[]) results.values;
            notifyDataSetChanged();
        }

    }

}
