package applab.client.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import applab.client.search.R;

import java.util.List;

/**
 * Created by skwakwa on 9/2/15.
 */
public class ListWithThumbnailAdapter extends BaseAdapter {

    private Context mContext;
    public LayoutInflater minflater;


   String[] contents = null;
    int[] thumbs=null;

    public int lastExpandedGroupPosition;

    public ListWithThumbnailAdapter(Context mContext,
                             String[] meetingActivities,int[] thumbnails
    ) {
        this.mContext = mContext;
        this.contents = meetingActivities;
        this.thumbs=thumbnails;
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
            view = minflater.inflate(R.layout.list_item_with_thumbnail, viewGroup, false);
        }
        TextView title = (TextView) view.findViewById(R.id.text);
        ImageView thumb=(ImageView) view.findViewById(R.id.thumbnail);
        title.setText(contents[i]);
        thumb.setImageResource(thumbs[i]);
        return view;
    }
}

