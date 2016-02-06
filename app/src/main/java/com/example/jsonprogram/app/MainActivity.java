package com.example.jsonprogram.app;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ActorsAdapter mAdapter;
    private ArrayList<Actors> mActorsArrayList;
    private HttpURLConnection httpURLConnection;
    private BufferedReader reader;
    private Actors mActors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list);
        mActorsArrayList = new ArrayList<Actors>();
        mActors = new Actors();
        new ActorsAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");


    }

    public class ActorsAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream stream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String stringJSON = buffer.toString();

                JSONObject jsonObject = new JSONObject(stringJSON);
                JSONArray jsonArray = jsonObject.getJSONArray("actors");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalObject = jsonArray.getJSONObject(i);


                    mActors.setName(finalObject.getString("name"));
                    mActors.setDescription(finalObject.getString("description"));
                    mActors.setDob(finalObject.getString("dob"));
                    mActors.setCountry(finalObject.getString("country"));
                    mActors.setHeight(finalObject.getString("height"));
                    mActors.setSpouse(finalObject.getString("spouse"));
                    mActors.setChildren(finalObject.getString("children"));
                    mActors.setImage(finalObject.getString("image"));

                    mActorsArrayList.add(mActors);
                    Log.i("For loop", mActorsArrayList.get(i).getName());

                }

                return stringJSON;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

                try {
                    if (reader != null) reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                ActorsAdapter adapter = new ActorsAdapter(getApplicationContext(), R.layout.row, mActorsArrayList);
                mListView.setAdapter(adapter);
//                for (Actors actor : mActorsArrayList) {
//                    actor.getChildren();
//                }
            } else {
                Log.i("NO RESULT", "No result");
            }
        }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
