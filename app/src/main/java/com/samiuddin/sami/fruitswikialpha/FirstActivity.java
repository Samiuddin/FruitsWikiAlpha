// this is the splash screen activity

package com.samiuddin.sami.fruitswikialpha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //this thread will sleep splash screen for 2 secs

        Thread background = new Thread() {
            public void run () {
                try {
                    sleep(2 * 1000);

                    Intent i = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(i);

                    finish();
                } catch (Exception e) {

                }
            }
        };
        background.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDestroy(){
        super.onDestroy();
    }
}
