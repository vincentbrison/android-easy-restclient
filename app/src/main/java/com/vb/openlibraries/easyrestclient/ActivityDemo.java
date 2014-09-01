package com.vb.openlibraries.easyrestclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.vb.openlibraries.easyrestclient.lib.interfaces.WebServiceCallbacks;
import com.vb.openlibraries.easyrestclient.lib.services.WebService;
import com.vb.openlibraries.easyrestclient.omwcityweather.result.OWMCityWeatherResult;

public class ActivityDemo extends ActionBarActivity {

    private Button mButtonGet;
    private Button mButtonPost;
    private Button mButtonDelete;
    private ListView mListViewDemoObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mButtonGet = (Button) findViewById(R.id.button_get);
        mButtonPost = (Button) findViewById(R.id.button_post);
        mButtonDelete = (Button) findViewById(R.id.button_delete);
        mListViewDemoObjects = (ListView) findViewById(R.id.listView_demo_objects);

        mButtonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OWMCityWeatherService<OWMCityWeatherResult> service = new OWMCityWeatherService<OWMCityWeatherResult>(new WebServiceCallbacks() {
                    @Override
                    public void onWebServiceFinishWithSuccess(WebService ws) {

                    }
                }, "London");
                service.addCallback(new WebServiceCallbacks() {
                    @Override
                    public void onWebServiceFinishWithSuccess(WebService ws) {
                        OWMCityWeatherResult result = service.getResult();
                        Toast.makeText(ActivityDemo.this, "ok", Toast.LENGTH_SHORT).show();
                    }
                });
                service.execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_demo, menu);
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
}
