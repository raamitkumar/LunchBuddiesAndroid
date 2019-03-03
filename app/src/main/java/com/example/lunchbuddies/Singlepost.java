package com.example.lunchbuddies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Singlepost extends AppCompatActivity {
    int post_id = 0;

    TextView theplace, thecusine, starttime, endtime, thenumberofperson, thebudget;
    Button sendinvitation,sendmessage,home;

    DataInfo datainfo=DataInfo.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepost);
        Bundle bundle = getIntent().getExtras();
        post_id = bundle.getInt("postid");
        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),Listofpost.class);
                startActivity(bridge);
            }
        });
        final DataInfo info=DataInfo.getInstance();
        theplace = findViewById(R.id.place_editText);
        thecusine = findViewById(R.id.cusinetype3);
        starttime = findViewById(R.id.starttime_editText);
        endtime = findViewById(R.id.endtime2);
        thenumberofperson = findViewById(R.id.numberofperson_editText);
        thebudget = findViewById(R.id.budget3_editText);
        sendmessage=findViewById(R.id.sendmessage);
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info.getUser_id() != 0) {
                    Intent bridge = new Intent(getApplicationContext(), History.class);

                    startActivity(bridge);
                } else {
                    Intent bridge = new Intent(getApplicationContext(), CustomDialogbox.class);

                    startActivity(bridge);

                }

            }});

        sendinvitation = findViewById(R.id.sendinvitation);

        Toast.makeText(getApplicationContext(),"PLEASE CLICK ON THE SEND INVITTAION BUTTON FOR SENDING THE INVITATION"
        ,Toast.LENGTH_LONG).show();
        sendinvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int user_id = datainfo.getUser_id();
                int reciever_user_id = datainfo.getReciever_user_id();

                if(user_id==reciever_user_id) {
                    Intent bridge=new Intent(getApplicationContext(),Listofpost.class);
                    Toast.makeText(getApplicationContext(), "You cannot send invitation to youself", Toast.LENGTH_SHORT).show();

                    startActivity(bridge);
                                    }else{
                    new MyTask2().execute();
                }
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
        System.out.println(post_id);
        setPost_id(post_id);
        new MyTask().execute();
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
        int fulluid;
        String firstname;
        String place, cusinetype, startdate, enddate;
        int post_id;
        int user_id;
        int numberofperson;
        double budget;

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {


            URL url = null;

            try {

                url = new URL("http://172.24.208.170:8888/lunchbuddies/mobile/application/viewpost&" + getPost_id());

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

//
//


                place = obj.getString("PLACE");
                cusinetype = obj.getString("CUSINETYPE");
                startdate = obj.getString("STARTTIME");
                enddate = obj.getString("ENDTIME");
                numberofperson = obj.getInt("NUMBEROFPERSON");
                user_id = obj.getInt("USER_ID");
                post_id = obj.getInt("POST_ID");
                budget = obj.getDouble("BUDGET");

                datainfo.setPost_id(post_id);
                datainfo.setReciever_user_id(user_id);

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
            theplace.setText(place);
            thecusine.setText(cusinetype);
            starttime.setText(startdate);
            endtime.setText(enddate);
            thebudget.setText(budget + "");
            thenumberofperson.setText(numberofperson + "");

//            Intent bridge=new Intent(getApplicationContext(),Listofpost.class);
//            startActivity(bridge);


        }


    }


    private class MyTask2 extends AsyncTask<Void, Void, Void> {


        String user_status;

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {

            int user_id = datainfo.getUser_id();
            int reciever_user_id = datainfo.getReciever_user_id();

            URL url = null;

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String datetime = dateformat.format(c.getTime());
            System.out.println(datetime);
            try {

                url = new URL("http://172.24.208.170:8888/lunchbuddies/mobile/application/sendinvitation&" + datetime + "&" + user_id +
                        "&" + reciever_user_id + "&" + datainfo.getPost_id());

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
                if (datainfo.getUser_id() != 0) {
                    user_status = obj.getString("Status");
                }
                //
//


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

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (datainfo.getUser_id() != 0) {
                        if (user_status.equals("OK")) {
                            Intent bridge = new Intent(getApplicationContext(), Listofpost.class);
                            Toast.makeText(getApplicationContext(), "Your invitation is successfully send", Toast.LENGTH_SHORT).show();

                            startActivity(bridge);

                        } else {

                            Toast.makeText(getApplicationContext(), "Some information went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent bridge = new Intent(getApplicationContext(), CustomDialogbox.class);

                        startActivity(bridge);
                    }

                }
            });

        }
    }}
