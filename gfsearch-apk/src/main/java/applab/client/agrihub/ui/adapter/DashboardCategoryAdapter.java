package applab.client.agrihub.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

import applab.client.agsmo.activity.TrainingActivity;
import applab.client.search.activity.CKWSearchActivity;
import applab.client.search.model.DashboardListItem;
import applab.client.search.R;

public class DashboardCategoryAdapter extends BaseAdapter implements OnClickListener {
	 
    private static final int TYPE_ONE_COLUMN = 0;
    private static final int TYPE_TWO_COLUMNS = 1;
    private static final int TYPE_MAX_COUNT = TYPE_TWO_COLUMNS + 1;
	
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<DashboardListItem> mDashboardCategories;
	private boolean mIsLayoutOnTop;
	
	public DashboardCategoryAdapter(Context context, ArrayList<DashboardListItem> categories, boolean isLayoutOnTop) {
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDashboardCategories = categories;
		mIsLayoutOnTop = isLayoutOnTop;
	}
	
    @Override
    public int getItemViewType(int position) {
    	if ((position == mDashboardCategories.size() / 2) && (mDashboardCategories.size() % 2 == 1)) {
    		return TYPE_ONE_COLUMN;
    	} else {
    		return TYPE_TWO_COLUMNS;
    	}
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

	public int getCount() {
		return (mDashboardCategories.size() / 2) + (mDashboardCategories.size() % 2);
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder.OneColumnViewHolder oneColumnViewHolder;
		final ViewHolder.TwoColumnsViewHolder twoColumnsViewHolder;

		int type = getItemViewType(position);

		if (type == TYPE_ONE_COLUMN) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_categories_one_column, parent, false);
				oneColumnViewHolder = new ViewHolder.OneColumnViewHolder();
				oneColumnViewHolder.image1 = (TextView) convertView.findViewById(R.id.list_item_icon);
				oneColumnViewHolder.title1 = (TextView) convertView.findViewById(R.id.list_item_title_1);
				oneColumnViewHolder.subtitle1 = (TextView) convertView.findViewById(R.id.list_item_number_of_images_1);
				oneColumnViewHolder.layoutTopBottom1 = (ViewGroup) convertView.findViewById(R.id.layout_top_bottom_1);
				oneColumnViewHolder.image1.setOnClickListener(this);
				convertView.setTag(oneColumnViewHolder);
			} else {
				oneColumnViewHolder = (ViewHolder.OneColumnViewHolder) convertView.getTag();
			}

			DashboardListItem model1 = mDashboardCategories.get(position * 2);
			oneColumnViewHolder.title1.setText(model1.getTitle());
			oneColumnViewHolder.subtitle1.setText(model1.getSubTitle());
			oneColumnViewHolder.image1.setText(model1.getImage());
			oneColumnViewHolder.image1.setTag(model1.getTag());

			LayoutParams lp1 = (LayoutParams) oneColumnViewHolder.layoutTopBottom1.getLayoutParams();

			if (!mIsLayoutOnTop) {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			} else {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
			}

		} else if (type == TYPE_TWO_COLUMNS) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_categories_two_columns, parent, false);
				twoColumnsViewHolder = new ViewHolder.TwoColumnsViewHolder();
				twoColumnsViewHolder.image1 = (TextView) convertView.findViewById(R.id.list_item_icon);
				twoColumnsViewHolder.title1 = (TextView) convertView.findViewById(R.id.list_item_title_1);
				twoColumnsViewHolder.subtitle1 = (TextView) convertView.findViewById(R.id.list_item_number_of_images_1);
				twoColumnsViewHolder.layoutTopBottom1 = (ViewGroup) convertView.findViewById(R.id.layout_top_bottom_1);

				twoColumnsViewHolder.image2 = (TextView) convertView.findViewById(R.id.list_item_icon_2);
				twoColumnsViewHolder.title2 = (TextView) convertView.findViewById(R.id.list_item_title_2);
				twoColumnsViewHolder.subtitle2 = (TextView) convertView.findViewById(R.id.list_item_number_of_images_2);
				twoColumnsViewHolder.layoutTopBottom2 = (ViewGroup) convertView.findViewById(R.id.layout_top_bottom_2);
				twoColumnsViewHolder.image1.setOnClickListener(this);
				twoColumnsViewHolder.image2.setOnClickListener(this);
				convertView.setTag(twoColumnsViewHolder);
			} else {
				twoColumnsViewHolder = (ViewHolder.TwoColumnsViewHolder) convertView.getTag();
			}

            DashboardListItem model1 = mDashboardCategories.get(position * 2);
            twoColumnsViewHolder.title1.setText(model1.getTitle());
            twoColumnsViewHolder.subtitle1.setText(model1.getSubTitle());
            twoColumnsViewHolder.image1.setText(model1.getImage());

            DashboardListItem model2 = mDashboardCategories.get(position * 2 + 1);
            twoColumnsViewHolder.title2.setText(model2.getTitle());
            twoColumnsViewHolder.subtitle2.setText(model2.getSubTitle());
            twoColumnsViewHolder.image2.setText(model2.getImage());

			twoColumnsViewHolder.image1.setTag(model1.getTag());
			twoColumnsViewHolder.image2.setTag(model2.getTag());

			LayoutParams lp1 = (LayoutParams) twoColumnsViewHolder.layoutTopBottom1.getLayoutParams();
			LayoutParams lp2 = (LayoutParams) twoColumnsViewHolder.layoutTopBottom2.getLayoutParams();

			if (!mIsLayoutOnTop) {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
				lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			} else {
				lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
				lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
			}
		}
		return convertView;
	}
	
	private static class ViewHolder {
		
		public static class OneColumnViewHolder {
			public TextView image1;
			public TextView title1;
			public TextView subtitle1;
			public ViewGroup layoutTopBottom1;
		}
		
		private static class TwoColumnsViewHolder {
			public TextView image1;
			public TextView title1;
			public TextView subtitle1;
			public ViewGroup layoutTopBottom1;

			public TextView image2;
			public TextView title2;
			public TextView subtitle2;
			public ViewGroup layoutTopBottom2;
		}
	}

	public void onClick(View v) {
		String action = (String) v.getTag();
        Intent intent = null;

        if (action.equalsIgnoreCase("ckwsearch")) {
            intent = new Intent(mContext, CKWSearchActivity.class);
        } else if (action.equalsIgnoreCase("register")) {
                //intent = new Intent(mContext, RegisterActivity.class);
        } else if (action.equalsIgnoreCase("stats")) {
            //intent = new Intent(mContext, StatsActivity.class);
        } else if (action.equalsIgnoreCase("training")) {
            intent = new Intent(mContext, TrainingActivity.class);
        } else {
            Toast.makeText(mContext,"Unknown action "+action,Toast.LENGTH_LONG).show();
        }

		if (intent != null) {
			mContext.startActivity(intent);
		}
	}
}
