package org.grameenfoundation.search.ui;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 */
public abstract class SingleInputPromptDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener {

    private final EditText input;
    private Context context;

    public SingleInputPromptDialog(Context context, int title, int message) {
        super(context);
        this.context = context;
        setTitle(title);
        setMessage(message);

        input = new EditText(context);
        input.setSingleLine(true);
        setView(input);
        setPositiveButton(R.string.ok, this);
        setNegativeButton(R.string.cancel, this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (input.getText() == null || input.getText().length() <= 0) {
                Toast.makeText(this.context,
                        org.grameenfoundation.search.R.string.missing_client_id, Toast.LENGTH_LONG).show();
                return;
            }

            if (onOkClicked(input.getText().toString())) {
                dialog.dismiss();
            }
        } else {
            onCancelClicked(dialog);
        }
    }

    /**
     * called when the "ok" button is pressed
     *
     * @param input
     * @return true or false, if the dialog should be closed. false if not.
     */
    protected abstract boolean onOkClicked(String input);

    /**
     * called when the "cancel" button is pressed and closes the dialog.
     *
     * @param dialog
     */
    protected void onCancelClicked(DialogInterface dialog) {
        dialog.dismiss();
    }
}
