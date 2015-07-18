package za.co.itlab.cashdispense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import za.co.itlab.cashdispense.za.co.itlab.cashdispense.handlers.HttpErrorTaskHandler;


public class CoinDispenseActivity extends AppCompatActivity {

    private final static Locale LOCALE = new Locale("en", "ZA");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_dispense);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coin_dispense, menu);
        return true;
    }

    public void submit(View view) {
        Number amountTendered = null;
        Number amountDue = null;

        EditText amountDueEditText = (EditText) findViewById(R.id.amountDue);
        EditText amountTenderedEditText = (EditText) findViewById(R.id.amountTender);

        try {
            amountDue = NumberFormat.getCurrencyInstance(LOCALE).parse("R"+amountDueEditText.getText().toString());
            amountTendered = NumberFormat.getCurrencyInstance(LOCALE).parse("R"+amountTenderedEditText.getText().toString());
        } catch (ParseException e) {
            Log.e("Coin Dispense Activity", e.getMessage(), e);
        }

        if (amountTendered.doubleValue() < amountDue.doubleValue()) {
            new HttpErrorTaskHandler(this,R.string.error_invalid_tender_amount).execute();
        } else {
            HttpSubmitTask task = new HttpSubmitTask(this, amountDue.doubleValue(), amountTendered.doubleValue());
            task.execute();

        }
    }

    private boolean isValidInput(String amountDue, String amountTendered) {
        if ((amountDue == null) || (amountTendered==null) ||
                (("").equals(amountDue.trim()) ||  (("").equals(amountTendered)))) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class HttpSubmitTask extends AsyncTask<Void, Void, List<String>> {

        private Context context;
        private Double amountDue;
        private Double amountTendered;
        private boolean networkError;


        public HttpSubmitTask(Context context,Double amountDue, Double amountTendered) {
            this.context = context;
            this.amountDue = amountDue;
            this.amountTendered = amountTendered;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            ArrayList<String> denominations = new ArrayList<String>();
            networkError = false;
            denominations = getDenominations();
            return denominations;
        }

        private ArrayList<String> getDenominations() {
            ArrayList<String> result = new ArrayList<String>();
            try {
                 String url = getHost() + "/coin/dispense";
                 RestTemplate restTemplate = new RestTemplate();
                 restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                 result = restTemplate.postForObject(url, this.amountTendered-this.amountDue, ArrayList.class);
             } catch (Exception e) {
                 Log.e("Coin Dispense Activity", e.getMessage(), e);
                 networkError = true;
             }
            return result;
        }

        private String getHost() {
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            return SP.getString("restUrl", "http://localhost:8080");
        }

        @Override
        protected void onPostExecute(List<String> denominations) {
            super.onPostExecute(denominations);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("DENOMINATIONS", (ArrayList<String>) denominations);
            bundle.putString("TOTAL", NumberFormat.getCurrencyInstance(LOCALE).format(this.amountTendered - this.amountDue));
            Intent intent = new Intent(this.context,CoinDispenseBreakDownActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}