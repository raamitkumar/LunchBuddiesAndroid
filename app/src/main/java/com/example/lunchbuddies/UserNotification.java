package com.example.lunchbuddies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserNotification extends AppCompatActivity {


    String firstname,lastname,place,cusinetype,startdate,enddate,user_status;
    int numberofperson;
    double budget;
    Button accept,reject,sendMessage;
    TextView sender_place,fn,ln,sender_cusine,sender_numberofperson,sender_startdate,sender_enddate,sender_budget,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
        accept=findViewById(R.id.statusaccept);
        reject=findViewById(R.id.statusreject);
        status=findViewById(R.id.messagedetail);
        sender_place=findViewById(R.id.place_editText);
        fn=findViewById(R.id.firstname);
        ln=findViewById(R.id.lastname);
        sender_cusine=findViewById(R.id.cusinetype3);
        sender_numberofperson=findViewById(R.id.numberofperson_editText);
        sender_enddate=findViewById(R.id.endtime2);
        sender_startdate=findViewById(R.id.starttime_editText);
        sender_budget=findViewById(R.id.budget);
        sendMessage=findViewById(R.id.sendmessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent bridge=new Intent(getApplicationContext(),History.class);
               startActivity(bridge);

            }
        });

        Bundle bundle=getIntent().getExtras();
        firstname=bundle.getString("FIRSTNAME");

        lastname=bundle.getString("LASTNAME");
         place=bundle.getString("PLACE");
         cusinetype=bundle.getString("CUSINE");
         startdate=bundle.getString("STARTDATE");
         enddate=bundle.getString("ENDDATE");
         numberofperson=bundle.getInt("NUMBEROFPERSON");
         budget=bundle.getDouble("BUDGET");

                sender_budget.setText(budget+"");
                sender_numberofperson.setText(numberofperson+"");
                fn.setText(firstname);
                ln.setText(lastname);
                sender_place.setText(place);
                sender_startdate.setText(startdate);
                sender_enddate.setText(enddate);
                sender_cusine.setText(cusinetype);

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        user_status="ACCEPT";
                        new MyTask().execute();
                    }
                });

                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        user_status="REJECT";
                        new MyTask().execute();
                    }
                });
        Button user_signout=findViewById(R.id.signout);
        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
    }


private class MyTask extends AsyncTask<Void, Void, Void> {
    int sender_id,reciever_id,post_id;

    @Override

    protected Void doInBackground(Void... params) {


        URL url = null;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        System.out.println(datetime);
        DataInfo datainfo=DataInfo.getInstance();
        sender_id=datainfo.getUser_id();
        reciever_id=datainfo.getReciever_user_id();
        post_id=datainfo.getPost_id();
        try {

            url = new URL("http://172.24.13.33:8080/lunchbuddies/mobile/application/recieveinvitation&"
                    +  user_status+ "&" + datetime + "&" +sender_id  + "&" + reciever_id + "&" +post_id);

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
        Intent bridge=new Intent(getApplicationContext(),SentandRecivePost.class);
        startActivity(bridge);
    }

}

    private class MyTask2 extends AsyncTask<Void, Void, Void> {
        int sender_id,reciever_id,post_id;

        @Override

        protected Void doInBackground(Void... params) {


            URL url = null;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String datetime = dateformat.format(c.getTime());
            System.out.println(datetime);
            DataInfo datainfo=DataInfo.getInstance();
            sender_id=datainfo.getUser_id();
            reciever_id=datainfo.getReciever_user_id();
            post_id=datainfo.getPost_id();
            try {

                url = new URL("http://172.24.13.33:8080/lunchbuddies/mobile/application/sendmessage&"
                        +  user_status+ "&" + datetime + "&" +sender_id  + "&" + reciever_id + "&" +post_id);

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
            Intent bridge=new Intent(getApplicationContext(),SentandRecivePost.class);
            startActivity(bridge);
        }

    }
}
