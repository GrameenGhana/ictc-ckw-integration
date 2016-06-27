package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import applab.client.search.R;

import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class SimpleListAdapter extends BaseAdapter {

    private Context mContext;
    public LayoutInflater minflater;


    List<String> contents = null;

    public int lastExpandedGroupPosition;

    public SimpleListAdapter(Context mContext,
                                  List<String> meetingActivities
    ) {
        this.mContext = mContext;
        this.contents = meetingActivities;
        minflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return contents.size();
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
        TextView title = (TextView) view.findViewById(R.id.gen_list_title);

        title.setText(contents.get(i));
        return view;
    }
}

