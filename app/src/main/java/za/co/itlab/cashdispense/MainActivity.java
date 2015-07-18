package za.co.itlab.cashdispense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import za.co.itlab.cashdispense.za.co.itlab.cashdispense.handlers.AlertDialogHandler;
import za.co.itlab.cashdispense.za.co.itlab.cashdispense.domain.User;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        HttpRequestTask httpRequestTask = new HttpRequestTask(this);
        httpRequestTask.execute();

    }

    public class HttpRequestTask extends AsyncTask<Void,Void,Boolean> {

        private Context context;

        private boolean networkError;

        public HttpRequestTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean isValidLogin = false;
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            User user = new User(username.getText().toString(),password.getText().toString());
            isValidLogin = login(user);
            return isValidLogin;
        }

        private Boolean login(User user) {
            Boolean result = false;
            try {
                String url = getHost() + "/user/login";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                result = restTemplate.postForObject(url, user, Boolean.class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                this.networkError = true;
            }
            return result;
        }

        private String getHost() {
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            return SP.getString("restUrl", "http://localhost:8080");
        }

        @Override
        protected void onPostExecute(Boolean validLogin) {
            if (this.networkError) {
                AlertDialogHandler.display(this.context, R.string.network_error_message);
            }
            else if (validLogin) {
                Intent intent = new Intent(this.context,CoinDispenseActivity.class);
                startActivity(intent);
            } else {
                AlertDialogHandler.display(this.context, R.string.login_error_message);
            }
        }

    }


}
