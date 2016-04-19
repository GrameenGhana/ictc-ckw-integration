package applab.client.agrihub.util;

import java.util.ArrayList;

import applab.client.search.R;
import applab.client.search.model.DashboardListItem;

public class DashboardContent {
	
	public static ArrayList<DashboardListItem> getContent(String category) {
		ArrayList<DashboardListItem> content = new ArrayList<DashboardListItem>();

		if (category.equals("Super Admin")) {
			DashboardListItem item = new DashboardListItem();
            item.setTag("register");
			item.setTitle("Register User");
			item.setSubTitle("20 Users");
            item.setImage(R.string.material_icon_account_multiple);
			content.add(item);

            item = new DashboardListItem();
            item.setTag("stats");
            item.setTitle("Stats");
            item.setSubTitle("Performance Monitor");
            item.setImage(R.string.material_icon_statistics);
            content.add(item);

            item = new DashboardListItem();
            item.setTag("ckwsearch");
            item.setTitle("Technical Assistance");
            item.setSubTitle("Crop Information");
            item.setImage(R.string.material_icon_info);
            content.add(item);

            item = new DashboardListItem();
            item.setTag("training");
            item.setTitle("Courses");
            item.setSubTitle("Business Management");
            item.setImage(R.string.material_icon_bookmark);
            content.add(item);
		}
		
		return content;
	}
}

