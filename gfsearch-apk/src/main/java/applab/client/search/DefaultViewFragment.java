package applab.client.search;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import applab.client.search.activity.FarmerDetailActivity;
import applab.client.search.model.ListObject;
import applab.client.search.model.SearchMenuItem;
import applab.client.search.services.MenuItemService;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.ui.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.List;
import java.util.Stack;

/**
 * This is a fragment that handles the display of the default view.
 */
public class DefaultViewFragment extends Fragment implements ActionMode.Callback {
    public static final String FRAGMENT_TAG = "applab.client.search.ui.DefaultViewFragment";
    private static final String NAVIGATION_STACK_SAVED_STATE_KEY = "navigation_stack_state_key";
    private Stack<ListObject> listObjectNavigationStack = null;
    private ListView mainListView;
    private MenuItem backNavigationMenuItem = null;
    private ActionMode actionMode;

    DatabaseHelper h;

    String farmerId="";
    public DefaultViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(NAVIGATION_STACK_SAVED_STATE_KEY)) {

            System.out.println("NAVIGATION_STACK_SAVED_STATE_KEY");
            setListObjectNavigationStack((Stack<ListObject>) savedInstanceState.get(NAVIGATION_STACK_SAVED_STATE_KEY));
        } else {
            System.out.println("Done No Item");
            setListObjectNavigationStack(new Stack<ListObject>());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_view_fragment, container, false);

        String crop ="";String detail="";farmerId="";String farmerName="";
        System.out.println("DefaultViewFragment.onCreateView");
        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        try {
                  crop = mBundle.getString("SELECTED_CROP");
            detail = mBundle.getString("SELECTED_LABEL");
            farmerId= mBundle.getString("SELECTED_FARMER");
            farmerName= mBundle.getString("SELECTED_FARMER_NAME");
        }   catch (Exception e) {

        }


        System.out.println("Selected CropNoted : "+crop);
        initMainListView(view,crop,detail,farmerName);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //save state of
        if (getListObjectNavigationStack() != null && !getListObjectNavigationStack().isEmpty()) {
            outState.putSerializable(NAVIGATION_STACK_SAVED_STATE_KEY, getListObjectNavigationStack());
        }
    }

    /**
     *
     */
    private void resetDisplayMenus() {
        if (getListObjectNavigationStack() != null) {
            getListObjectNavigationStack().clear();

            listViewBackNavigation();
        }
    }

    private void initMainListView(View container,String selectedCrop,String label,String farmerName) {
        setMainListView((ListView) container.findViewById(R.id.main_list));

        final MainListViewAdapter listViewAdapter = new MainListViewAdapter(getActivity());
        getMainListView().setAdapter(listViewAdapter);
        TextView tv= (TextView)container.findViewById(R.id.txt_farmer_ckw);
        if(!farmerName.isEmpty()){
            tv.setText(farmerName);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("Clicjed Txt");
                    if(null == farmerId || farmerId.isEmpty()){

                    }else{
                        //view actionget
                        Intent i = new Intent(getActivity(),FarmerDetailActivity.class);

                        i.putExtra("farmerId",farmerId);
                        startActivity(i);
                    }
                }
            });
//            tv.setOnClickListener();
        }else
        {
            tv.setVisibility(TextView.INVISIBLE);

        }

        if(!selectedCrop.isEmpty()) {
            List<SearchMenuItem> itemSelected = new MenuItemService().getSearchMenuItemByLabel(selectedCrop);

            if(!label.isEmpty() && label.toLowerCase().contains("harvest")){
                if(!itemSelected.isEmpty()){
                    itemSelected = new MenuItemService().getSearchMenuItemByLabelAndParent(itemSelected.get(0).getId(),"Harvest");
                }

            }
            if(!itemSelected.isEmpty()){
                selectListElement(itemSelected.get(0),listViewAdapter);
            }
        }
        getMainListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListObject itemToSelect = (ListObject) listViewAdapter.getItem(position);
                selectListElement(itemToSelect, listViewAdapter);

                if (actionMode == null)
                    actionMode = getActivity().startActionMode(DefaultViewFragment.this);
            }
        });

        getMainListView().setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return super.onTouch(v, event);
            }

            @Override
            public boolean onSwipeRight() {
                super.onSwipeRight();
                listViewBackNavigation();
                return false;
            }
        });

        if (!getListObjectNavigationStack().isEmpty()) {
            selectListElement(getListObjectNavigationStack().pop(), listViewAdapter);
        }

        if (backNavigationMenuItem != null) {
            backNavigationMenuItem.setVisible(true);
        }
    }

    private void selectListElement(final ListObject itemToSelect, MainListViewAdapter listViewAdapter) {
        if (listViewAdapter.hasChildren(itemToSelect)) {
            listViewAdapter.setSelectedObject(itemToSelect);
            getListObjectNavigationStack().push(listViewAdapter.getSelectedObject());

            if (backNavigationMenuItem != null) {
                //backNavigationMenuItem.setVisible(true);
                getActivity().startActionMode(this);
            }
        } else {

            //if (SettingsManager.getInstance().
            //        getBooleanValue(SettingsConstants.KEY_CLIENT_IDENTIFIER_PROMPTING_ENABLED, rlse)) {
            //option overridden in gf-search ckw

            boolean showClientIdentifierPrompt = false;
            if (showClientIdentifierPrompt) {
                SingleInputPromptDialog dialog = new SingleInputPromptDialog(getActivity(), R.string.clientid_dialog_title,
                        R.string.clientid_dialog_message) {
                    @Override
                    protected boolean onOkClicked(String input) {
                        Intent intent = new Intent().setClass(getActivity(), SearchMenuItemActivity.class);
                        intent.putExtra(SearchMenuItemActivity.EXTRA_LIST_OBJECT_IDENTIFIER, itemToSelect);
                        intent.putExtra(SearchMenuItemActivity.CLIENT_IDENTIFIER, input);
                        intent.putExtra(SearchMenuItemActivity.BREAD_CRUMB, createBreadCrumb(itemToSelect));
                        startActivityForResult(intent, 0);

                        return true;
                    }
                };
                dialog.show();
            } else {

                Intent intent = new Intent().setClass(getActivity(), SearchMenuItemActivity.class);
                intent.putExtra(SearchMenuItemActivity.EXTRA_LIST_OBJECT_IDENTIFIER, itemToSelect);
                intent.putExtra(SearchMenuItemActivity.BREAD_CRUMB, createBreadCrumb(itemToSelect));
                this.startActivityForResult(intent, 0);
            }
        }
    }

    private String createBreadCrumb(ListObject currentItem) {
        StringBuilder breadCrumb = new StringBuilder();
        boolean isCategory = true;
        for (int i = 1; i < getListObjectNavigationStack().size(); i++) {
            ListObject menuItem = getListObjectNavigationStack().get(i);
            if (isCategory) {
                breadCrumb.append(menuItem.getLabel() + "|");
                isCategory = false;
            } else {
                breadCrumb.append(menuItem.getLabel() + " ");
            }
        }
        return breadCrumb.toString() + currentItem.getLabel();
    }


    public boolean listViewBackNavigation() {
        MainListViewAdapter listViewAdapter = (MainListViewAdapter) getMainListView().getAdapter();

        //we pop the stack twice to get the right navigation element.
        if (!getListObjectNavigationStack().isEmpty())
            getListObjectNavigationStack().pop();

        if (!getListObjectNavigationStack().isEmpty()) {
            listViewAdapter.setSelectedObject(getListObjectNavigationStack().peek());
            return false;
        }

        if (getListObjectNavigationStack().isEmpty()) {
            listViewAdapter.setSelectedObject(null);
            if (backNavigationMenuItem != null) {
                backNavigationMenuItem.setVisible(false);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.default_view_fragment, menu);

        backNavigationMenuItem = menu.findItem(R.id.action_nav_back);
        if (getListObjectNavigationStack() != null
                && !getListObjectNavigationStack().isEmpty()) {
            backNavigationMenuItem.setVisible(true);
        }
        actionMode = mode;

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nav_back:
                listViewBackNavigation();
                if (getListObjectNavigationStack().isEmpty()) {
                    mode.finish();
                }

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
    }

    public Stack<ListObject> getListObjectNavigationStack() {
        return listObjectNavigationStack;
    }

    public void setListObjectNavigationStack(Stack<ListObject> listObjectNavigationStack) {
        this.listObjectNavigationStack = listObjectNavigationStack;
    }

    public ListView getMainListView() {
        return mainListView;
    }

    public void setMainListView(ListView mainListView) {
        this.mainListView = mainListView;
    }

    public void viewFarmer(View view){

        System.out.println("Farmer View"+ farmerId);



    }
}
