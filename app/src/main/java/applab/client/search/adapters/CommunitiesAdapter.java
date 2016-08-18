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
import applab.client.search.model.Community;
import applab.client.search.model.Farmer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class CommunitiesAdapter extends BaseAdapter implements Filterable {

    private final Context mContext;
    private List<Community> names;
    private List<Community> originalData;
   // private final List<Community> farmers;
    List<Farmer> farmerList= new ArrayList<Farmer>();

    public CommunitiesAdapter(Context c, List<Community> names) {
        mContext = c;
        this.names = names;
        this.originalData=names;
        //this.farmers = farmers;
    }
    public CommunitiesAdapter(Context c, List<Community> names,List<Farmer> fs) {
        mContext = c;
        this.names = names;
        //this.farmers = farmers;
        this.farmerList =fs;
        this.originalData=names;
    }

    public int getCount() {
        return names.size();
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
        name.setText(names.get(i).getName());
        farmer.setText(names.get(i).getMemberCount());
        return grid;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                names = (ArrayList<Community>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Community> FilteredArrList = new ArrayList<Community>();

                if (originalData == null) {
                    originalData = new ArrayList<Community>(names); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = originalData.size();
                    results.values = originalData;
                } else {
                    originalData = new ArrayList<Community>(names);
                    constraint = constraint.toString().toLowerCase();

                    for (int i = 0; i < originalData.size(); i++) {
                        String data = originalData.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Community(originalData.get(i).getName(),originalData.get(i).getMemberCount()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}
