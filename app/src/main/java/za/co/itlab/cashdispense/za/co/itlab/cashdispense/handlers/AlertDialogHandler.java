package za.co.itlab.cashdispense.za.co.itlab.cashdispense.handlers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import za.co.itlab.cashdispense.R;

/**
 * Created by poovanpillay on 2015/07/16.
 */
public class AlertDialogHandler {

    public static void display(final Context context, final int msgId) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setMessage(msgId);
        dlgAlert.setTitle(R.string.error_message_title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }

    public static void display(final Context context, final String message) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(R.string.error_message_title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }
}
