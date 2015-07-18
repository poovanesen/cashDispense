package za.co.itlab.cashdispense;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import za.co.itlab.cashdispense.R;

public class CoinDispenseBreakDownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_dispense_break_down);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StringBuilder builder = formatDenominations(extras.getStringArrayList("DENOMINATIONS"));
            TextView denominationsTextView = (TextView) findViewById(R.id.denominations);
            denominationsTextView.setText(builder.toString());

            String totalValueAsString = extras.getString("TOTAL");
            TextView total = (TextView) findViewById(R.id.total);
            total.setText(totalValueAsString);
        }

    }

    private StringBuilder formatDenominations(ArrayList<String> denominations) {
        StringBuilder builder = new StringBuilder();
        for (String details : denominations) {
            builder.append(details + "\n");
        }
        return builder;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coin_dispense_break_down, menu);
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
}
