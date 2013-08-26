package org.grameenfoundation.search.ui;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.grameenfoundation.search.R;
import org.grameenfoundation.search.model.ListObject;
import org.grameenfoundation.search.model.SearchMenu;
import org.grameenfoundation.search.model.SearchMenuItem;
import org.grameenfoundation.search.services.MenuItemService;

import java.util.List;

/**
 * Custom Adapter that is the backing object of the Main ListView of the application.
 */
public class MainListViewAdapter extends BaseAdapter {
    private ListObject selectedObject;
    private MenuItemService menuItemService = new MenuItemService();
    private Object items = null;
    private Context context;
    private LayoutInflater layoutInflater;
    private Handler handler = null;

    public MainListViewAdapter(Context context) {
        super();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        handler = new Handler();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (selectedObject != null) {
            if (selectedObject instanceof SearchMenuItem) {
                return menuItemService.countSearchMenuItems((SearchMenuItem) selectedObject);
            } else if (selectedObject instanceof SearchMenu) {
                return menuItemService.countTopLevelSearchMenuItems((SearchMenu) selectedObject);
            }

        } else {
            items = menuItemService.getAllSearchMenus();
            count = menuItemService.countSearchMenus();
        }

        return count;
    }

    @Override
    public Object getItem(int position) {
        if (selectedObject != null) {
            if (selectedObject instanceof SearchMenuItem) {
                List<SearchMenuItem> searchMenuItems = (List<SearchMenuItem>) items;
                return searchMenuItems.get(position);
            } else if (selectedObject instanceof SearchMenu) {
                List<SearchMenu> searchMenus = (List<SearchMenu>) items;
                return searchMenus.get(position);
            }
        } else {
            //we assume it's the first time to access the adapter
            List<SearchMenu> menus = (List<SearchMenu>) items;
            if (menus != null) {
                return menus.get(position);
            }
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            rowView = layoutInflater.inflate(R.layout.listviewobject, parent, false);
        }
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.description);

        ListObject listObject = (ListObject) getItem(position);
        if (listObject != null) {
            titleView.setText(listObject.getLabel());
            descriptionView.setText(listObject.getDescription());
            rowView.setTag(listObject);
        }

        return rowView;
    }

    public ListObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(ListObject selectedObject) {
        this.selectedObject = selectedObject;

        if (this.selectedObject instanceof SearchMenu) {
            items = menuItemService.getTopLevelSearchMenuItems((SearchMenu) this.selectedObject);
            //notifyDataChanges();
            notifyDataSetChanged();
        } else if (this.selectedObject instanceof SearchMenuItem) {
            items = menuItemService.getSearchMenuItems((SearchMenuItem) this.selectedObject);
            //notifyDataChanges();
            notifyDataSetChanged();
        } else {
            items = menuItemService.getAllSearchMenus();
            //notifyDataChanges();
            notifyDataSetChanged();
        }
    }

    private void notifyDataChanges() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //notifyDataSetInvalidated();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
