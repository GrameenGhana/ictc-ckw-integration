package org.grameenfoundation.search;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import org.grameenfoundation.search.model.ListObject;
import org.grameenfoundation.search.settings.SettingsConstants;
import org.grameenfoundation.search.settings.SettingsManager;
import org.grameenfoundation.search.ui.MainListViewAdapter;
import org.grameenfoundation.search.ui.OnSwipeTouchListener;
import org.grameenfoundation.search.ui.SearchMenuItemActivity;
import org.grameenfoundation.search.ui.SingleInputPromptDialog;

import java.util.Stack;

/**
 * This is a fragment that handles the display of the default view.
 */
public class DefaultViewFragment extends Fragment implements ActionMode.Callback {
    public static final String FRAGMENT_TAG = "org.grameenfoundation.search.ui.DefaultViewFragment";
    private static final String NAVIGATION_STACK_SAVED_STATE_KEY = "navigation_stack_state_key";
    private Stack<ListObject> listObjectNavigationStack = null;
    private ListView mainListView;
    private MenuItem backNavigationMenuItem = null;
    private ActionMode actionMode;

    public DefaultViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(NAVIGATION_STACK_SAVED_STATE_KEY)) {
            listObjectNavigationStack =
                    (Stack<ListObject>) savedInstanceState.get(NAVIGATION_STACK_SAVED_STATE_KEY);
        } else {
            listObjectNavigationStack = new Stack<ListObject>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_view_fragment, container, false);
        initMainListView(view);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //save state of
        if (listObjectNavigationStack != null && !listObjectNavigationStack.isEmpty()) {
            outState.putSerializable(NAVIGATION_STACK_SAVED_STATE_KEY, listObjectNavigationStack);
        }
    }

    /**
     *
     */
    private void resetDisplayMenus() {
        if (listObjectNavigationStack != null) {
            listObjectNavigationStack.clear();

            listViewBackNavigation();
        }
    }

    private void initMainListView(View container) {
        mainListView = (ListView) container.findViewById(R.id.main_list);

        final MainListViewAdapter listViewAdapter = new MainListViewAdapter(getActivity());
        mainListView.setAdapter(listViewAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListObject itemToSelect = (ListObject) listViewAdapter.getItem(position);
                selectListElement(itemToSelect, listViewAdapter);

                if (actionMode == null)
                    actionMode = getActivity().startActionMode(DefaultViewFragment.this);
            }
        });

        mainListView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
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

        if (!listObjectNavigationStack.isEmpty()) {
            selectListElement(listObjectNavigationStack.pop(), listViewAdapter);
        }
    }

    private void selectListElement(final ListObject itemToSelect, MainListViewAdapter listViewAdapter) {
        if (listViewAdapter.hasChildren(itemToSelect)) {
            listViewAdapter.setSelectedObject(itemToSelect);
            listObjectNavigationStack.push(listViewAdapter.getSelectedObject());

            if (backNavigationMenuItem != null) {
                backNavigationMenuItem.setVisible(true);
            }
        } else {
            if (SettingsManager.getInstance().
                    getBooleanValue(SettingsConstants.KEY_CLIENT_IDENTIFIER_PROMPTING_ENABLED, false)) {

                SingleInputPromptDialog dialog = new SingleInputPromptDialog(getActivity(), R.string.clientid_dialog_title,
                        R.string.clientid_dialog_message) {
                    @Override
                    protected boolean onOkClicked(String input) {
                        Intent intent = new Intent().setClass(getActivity(), SearchMenuItemActivity.class);
                        intent.putExtra(SearchMenuItemActivity.EXTRA_LIST_OBJECT_IDENTIFIER, itemToSelect);
                        intent.putExtra(SearchMenuItemActivity.CLIENT_IDENTIFIER, input);
                        startActivityForResult(intent, 0);

                        return true;
                    }
                };
                dialog.show();
            } else {
                Intent intent = new Intent().setClass(getActivity(), SearchMenuItemActivity.class);
                intent.putExtra(SearchMenuItemActivity.EXTRA_LIST_OBJECT_IDENTIFIER, itemToSelect);
                this.startActivityForResult(intent, 0);
            }
        }
    }

    private void listViewBackNavigation() {
        MainListViewAdapter listViewAdapter = (MainListViewAdapter) mainListView.getAdapter();

        //we pop the stack twice to get the right navigation element.
        if (!listObjectNavigationStack.isEmpty())
            listObjectNavigationStack.pop();

        if (!listObjectNavigationStack.isEmpty()) {
            listViewAdapter.setSelectedObject(listObjectNavigationStack.peek());
            return;
        }

        if (listObjectNavigationStack.isEmpty()) {
            listViewAdapter.setSelectedObject(null);
            backNavigationMenuItem.setVisible(false);

            //finish the action mode
            if (actionMode != null)
                actionMode.finish();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.default_view_fragment, menu);

        backNavigationMenuItem = menu.findItem(R.id.action_nav_back);
        if (listObjectNavigationStack != null
                && !listObjectNavigationStack.isEmpty()) {
            backNavigationMenuItem.setVisible(true);
        }


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
                if (listObjectNavigationStack.isEmpty()) {
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
}
