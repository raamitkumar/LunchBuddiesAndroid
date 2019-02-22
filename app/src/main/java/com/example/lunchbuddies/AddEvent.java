package com.example.lunchbuddies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AddEvent extends AppCompatActivity {

    EditText name,place,startdate,enddate;
    Button addevent,imageupload;


    String event_name,event_place,event_startdate,event_end_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        imageupload=findViewById(R.id.imageupload);
        name=findViewById(R.id.eventname);
        place=findViewById(R.id.eventplace);
        startdate=findViewById(R.id.eventstartdate);
        enddate=findViewById(R.id.eventenddate);
        addevent=findViewById(R.id.event);


        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event_name=name.getText().toString();
                event_place=place.getText().toString();
                event_startdate=startdate.getText().toString();
                event_end_date=enddate.getText().toString();
                new MyTask().execute();
            }
        });

        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),Imageupload.class);
                startActivity(bridge);
            }
        });
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
        int fulluid;
        String user_status;

        public int getFulluid() {
            return fulluid;
        }

        public void setFulluid(int fulluid) {
            this.fulluid = fulluid;
        }

        @Override

        protected Void doInBackground(Void... params) {


            URL url = null;

            DataInfo datainfo=DataInfo.getInstance();
            try {
                fulluid=datainfo.getUser_id();

                System.out.println(fulluid);
                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/addevent&"
                        + event_name + "&" + event_place + "&" + event_startdate + "&" + event_end_date+"&"+fulluid);

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
                user_status=obj.getString("Status");
                System.out.println(user_status);

                //datainfo.setUser_id(userID);

                //System.out.println(userID);

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
            if(user_status.equals("OK")){

                Intent brdige=new Intent(getApplicationContext(),Adminpanel.class);
                Toast.makeText(getApplicationContext(),"Event is Successfully added",Toast.LENGTH_SHORT).show();
                startActivity(brdige);
            }else{
                Toast.makeText(getApplicationContext(),"Please enter correct details",Toast.LENGTH_SHORT).show();


            }

    }
}}
