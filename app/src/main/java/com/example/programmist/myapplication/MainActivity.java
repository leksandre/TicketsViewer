package com.example.programmist.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.List;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    URL url;
    HttpURLConnection urlConnection;
    InputStream in;
    String res11;
    EditText Edtext2;
    MyTask mt;
    TextView tvInfo, tvInfo2;
    ProgressBar progressBar;




    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = (TextView) findViewById(R.id.textView);
        tvInfo2 = (TextView) findViewById(R.id.textView2);
        Edtext2 = (EditText) findViewById(R.id.editText2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);







    }
    public void onclick(View v) {
        mt = new MyTask();
        mt.execute();

    }

    public void clear(View view) {
        Edtext2.setText("");
    }


    class MyTask extends AsyncTask<Void, Void, String> {


        ArrayAdapter<String> adapter;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Начало");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            try{
                url = new URL("http://sd.atm72.ru/ac/ap.php?id="+(Edtext2.getText()));
            }catch(MalformedURLException ex){

            }
            try{
                urlConnection = (HttpURLConnection) url.openConnection();
            }catch(IOException ex){

            }

            try {
                 in = new BufferedInputStream(urlConnection.getInputStream());
                res11=readStream(in);
            } catch(IOException ex) {
                urlConnection.disconnect();
            }



         //   return(urlConnection.getRequestMethod());
/*
            try {

                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
                BufferedReader reader=null;
                reader= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder buf=new StringBuilder();
                String line=null;
                while ((line=reader.readLine()) != null) {
                    buf.append(line + "\n");
                }
                return(buf.toString());
            } catch(IOException ex) {
                urlConnection.disconnect();
            }
*/






/*
            try {
                URL url = new URL("http://developer.alexanderklimov.ru/android/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
             readStream(in);
        }    catch(MalformedURLException ex) {
                     urlConnection.disconnect();
                } catch(IOException ex) {
                urlConnection.disconnect();
            }
*/



            urlConnection.disconnect();
            return res11;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);
            tvInfo.setText("получены данные");
          //  tvInfo2.setText(result);

            String[] names = result.split("___");
            ListView lvMain = (ListView) findViewById(R.id.lvMain);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, names);


            lvMain.setAdapter(adapter);

        }


    }


}
