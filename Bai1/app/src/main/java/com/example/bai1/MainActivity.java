package com.example.bai1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadWebpageAsyncTask extends Activity {
    private TextView textView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.TextView01);
    }
    public void readWebpage(View view){
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://www.mobipro.vn"});
    }

    class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "", s = "";
            for (String url : urls){
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    InputStream content = client.excute(httpGet).GetEntity().getContent();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    while ((s= buffer.readLine()) != null) response += s;
                } catch (Exception e) {}
            }
            return response;
        }

        protected void onPostExecute(String result){
            textView.setText(result);
        }
    }
}
