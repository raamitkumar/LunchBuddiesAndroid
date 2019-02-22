package com.example.lunchbuddies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Postadd extends AppCompatActivity {

    EditText place,cusine,startdate,enddate,numberofperson,budget,start_time,end_time;
    Button post;
    String post_place,post_cusine,post_startdate,post_enddate;
    int post_numberofperson,stTime,eTime;
    double post_budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd);

        place=findViewById(R.id.place);
        cusine=findViewById(R.id.cusine);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        numberofperson=findViewById(R.id.numberofperson);
        budget=findViewById(R.id.budget);
        post=findViewById(R.id.post);
        start_time=findViewById(R.id.starttime);
        end_time=findViewById(R.id.endtime);

        Toast.makeText(getApplicationContext(),"PLEASE ENTER thE FOLlowing DETAILSTO POST ADD",Toast.LENGTH_LONG).show();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                post_place=place.getText().toString().toUpperCase().toUpperCase();
                post_cusine=cusine.getText().toString().toUpperCase().toUpperCase();
                post_startdate=startdate.getText().toString();
                post_enddate=enddate.getText().toString();
                post_numberofperson=Integer.parseInt(numberofperson.getText().toString());
                post_budget=Double.parseDouble(budget.getText().toString());
                stTime=Integer.parseInt(start_time.getText().toString());

                stTime=Integer.parseInt(start_time.getText().toString());
                eTime=Integer.parseInt(end_time.getText().toString());
                post_startdate=post_startdate+stTime;
                post_enddate=post_enddate+eTime;
                new MyTask().execute();

            }
        });
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
        int uid;

        String post_status;
        @Override

        protected Void doInBackground(Void... params) {


            URL url = null;

            DataInfo datainfo=DataInfo.getInstance();
            try {
                uid=datainfo.getUser_id();

                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/postinfo&"
                        + post_place + "&" + post_numberofperson +
                        "&" + post_budget + "&"+post_cusine +"&" + post_startdate+"&"+post_enddate+"&"+uid);

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();

                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);

                InputStreamReader myInput = new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                JSONObject obj = new JSONObject(response.toString());
                post_status=obj.getString("STATUS");





            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(post_status.equals("OK")){
            Intent bridge=new Intent(getApplicationContext(),Listofpost.class);
                Toast.makeText(getApplicationContext(),"Your post is succesfully added",Toast.LENGTH_LONG).show();
            startActivity(bridge);
        }else{

                place.setText("");
                cusine.setText("");
                startdate.setText("");
                enddate.setText("");
                numberofperson.setText("");
                budget.setText("");
                post.setText("");
                start_time.setText("");
                end_time.setText("");


                Toast.makeText(getApplicationContext(),"You are adding some information wrong please RE-Enter",Toast.LENGTH_LONG).show();
            }}

    }
}
