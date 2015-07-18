package za.co.itlab.cashdispense.za.co.itlab.cashdispense.handlers;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by poovanpillay on 2015/07/16.
 */
public class HttpErrorTaskHandler extends AsyncTask<Void,Void,Boolean> {
    private Context context;
    private int errorMsg;

    public HttpErrorTaskHandler(Context context, int errorMsg) {
        this.context = context;
        this.errorMsg = errorMsg;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        AlertDialogHandler.display(context,errorMsg);
    }
}
