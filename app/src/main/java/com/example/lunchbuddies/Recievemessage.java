package com.example.lunchbuddies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Recievemessage extends AppCompatActivity {


    Button recieve_Message, send_Message;
    TextView thefirstname,thelastname,reciever_Message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recievemessage);



        recieve_Message = findViewById(R.id.recieve);
        send_Message = findViewById(R.id.send);

        recieve_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask2().execute();
            }
        });

        send_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute();
            }
        });

    }

    private class MyTask2 extends AsyncTask<Void, Void, Void> {
        int sender_id, post_id, reciever_id;
        String firstname, lastname, message, cusinetype, startdate, enddate, invitationStatus;
        int numberofperson;


        double budget;
        TableLayout tb2;
        TableRow tr,tr2,tr3;

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            final DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());

            tb2 = findViewById(R.id.tblayout);

            URL url = null;

            try {

                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/recievemessage&" + datainfo.getUser_id());

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

                try {

                    //JSONObject senderobject = new JSONObject();
                    final JSONArray senderarray = obj.getJSONArray("DATA");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tb2.removeAllViews();


                            tr2 = new TableRow(getApplicationContext());
                            tr3 = new TableRow(getApplicationContext());
                            tr = new TableRow(getApplicationContext());

                            thefirstname = new TextView(getApplicationContext());
                            thefirstname.setText("RECIEVERFIRSTNAME  ");
                            thefirstname.setTextSize(20);
                            thefirstname.setTextColor(Color.BLACK);
                            thefirstname.setTypeface(null, Typeface.BOLD_ITALIC);
                            tr2.addView(thefirstname);

                            thelastname = new TextView(getApplicationContext());
                            thelastname.setText("RECIEVERLASTNAME");
                            thelastname.setTextSize(20);
                            thelastname.setTextColor(Color.BLACK);
                            thelastname.setTypeface(null, Typeface.BOLD_ITALIC);
                            tr2.addView(thelastname);

                            reciever_Message = new TextView(getApplicationContext());
                            reciever_Message.setText("RECIEVER MESSAGE  ");
                            reciever_Message.setTextSize(20);
                            reciever_Message.setTypeface(null, Typeface.BOLD_ITALIC);
                            reciever_Message.setTextColor(Color.BLACK);
                            tr3.addView(reciever_Message);



                            tb2.addView(tr);
                            tb2.addView(tr2);
                            tb2.addView(tr3);


                            JSONObject senderobject = new JSONObject();
                            for (int i = 0; i < senderarray.length(); i++) {
                                try {
                                    senderobject = senderarray.getJSONObject(i);

                                    reciever_id = senderobject.getInt("RECIEVER_ID");
                                    sender_id = senderobject.getInt("Send_id");

                                    if(reciever_id==sender_id){
                                    tr3 = new TableRow(getApplicationContext());

                                    tr = new TableRow(getApplicationContext());
                                    tr2 = new TableRow(getApplicationContext());

                                    thefirstname = new TextView(getApplicationContext());
                                    firstname = senderobject.getString("FIRSTNAME");
                                    thefirstname.setTextSize(20);
                                    //    thefirstname.setTextColor(ff000000);
                                    thefirstname.setText(firstname + " ");
                                    tr2.addView(thefirstname);
                                    thelastname = new TextView(getApplicationContext());

                                    thelastname.setTextSize(20);
                                    lastname = senderobject.getString("LASTNAME");
                                    thelastname.setText(lastname + " ");
                                    tr2.addView(thelastname);
                                    reciever_Message = new TextView(getApplicationContext());
                                    message = senderobject.getString("Message");
                                    reciever_Message.setText(message + " ");
                                    reciever_Message.setTextSize(20);
                                    tr3.addView(reciever_Message);

                                    cusinetype = senderobject.getString("CUSINETYPE");
                                    startdate = senderobject.getString("STARTTIME");
                                    enddate = senderobject.getString("ENDTIME");
                                    budget = senderobject.getDouble("BUDGET");
                                    numberofperson = senderobject.getInt("NUMBEROFPERSON");

                                    post_id = senderobject.getInt("POST_ID");
                                    //  tr3 = new TableRow(getApplicationContext());

                                    tb2.addView(tr);
                                    tb2.addView(tr2);
                                    tb2.addView(tr3);

                                    datainfo.setPost_id(post_id);
                                    datainfo.setUser_id(sender_id);
                                    datainfo.setReciever_user_id(reciever_id);
                                    tr2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent bridge = new Intent(getApplicationContext(), sendernotification.class);

                                            bridge.putExtra("FIRSTNAME", firstname);
                                            bridge.putExtra("LASTNAME", lastname);
                                            bridge.putExtra("PLACE", message);
                                            bridge.putExtra("CUSINE", cusinetype);
                                            bridge.putExtra("BUDGET", budget);
                                            bridge.putExtra("NUMBEROFPERSON", numberofperson);
                                            bridge.putExtra("STARTDATE", startdate);
                                            bridge.putExtra("ENDDATE", enddate);
                                            bridge.putExtra("MESSAGE", message);

                                            startActivity(bridge);
                                        }
                                    });
                                    }else{

                                        Toast.makeText(getApplicationContext(),"YOU DIDNOT RECIEVE ANY MESSAGE",Toast.LENGTH_LONG).
                                                show();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                } catch (Exception e) {
                    e.getMessage();
                }

                return null;

            } catch (MalformedURLException e) {
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

        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        int sender_id, post_id, reciever_id;
        String firstname, lastname, message, cusinetype, startdate, enddate, invitationStatus;
        int numberofperson;


        double budget;
        TableLayout tb;
        TableRow tr,tr2,tr3;

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            final DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());
            tb = findViewById(R.id.tblayout2);

            URL url = null;

            try {

                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/recievemessage&" + datainfo.getUser_id());

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

                try {

                    //JSONObject senderobject = new JSONObject();
                    final JSONArray senderarray = obj.getJSONArray("DATA");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tb.removeAllViews();


                            tr2 = new TableRow(getApplicationContext());
                            tr3 = new TableRow(getApplicationContext());
                            tr = new TableRow(getApplicationContext());

                            thefirstname = new TextView(getApplicationContext());
                            thefirstname.setText("FIRSTNAME  ");
                            thefirstname.setTextSize(20);
                            thefirstname.setTextColor(Color.BLACK);
                            thefirstname.setTypeface(null, Typeface.BOLD_ITALIC);
                            tr2.addView(thefirstname);

                            thelastname = new TextView(getApplicationContext());
                            thelastname.setText("LASTNAME");
                            thelastname.setTextSize(20);
                            thelastname.setTextColor(Color.BLACK);
                            thelastname.setTypeface(null, Typeface.BOLD_ITALIC);
                            tr2.addView(thelastname);

                            reciever_Message = new TextView(getApplicationContext());
                            reciever_Message.setText("MESSAGE  ");
                            reciever_Message.setTextSize(20);
                            reciever_Message.setTypeface(null, Typeface.BOLD_ITALIC);
                            reciever_Message.setTextColor(Color.BLACK);
                            tr3.addView(reciever_Message);



                            tb.addView(tr);
                            tb.addView(tr2);
                            tb.addView(tr3);


                            JSONObject senderobject = new JSONObject();
                            for (int i = 0; i < senderarray.length(); i++) {
                                try {
                                    senderobject = senderarray.getJSONObject(i);

                                    reciever_id = senderobject.getInt("RECIEVER_ID");
                                    sender_id = senderobject.getInt("Send_id");

                                    if(datainfo.getUser_id()==sender_id){
                                        tr3 = new TableRow(getApplicationContext());

                                        tr = new TableRow(getApplicationContext());
                                        tr2 = new TableRow(getApplicationContext());

                                        thefirstname = new TextView(getApplicationContext());
                                        firstname = senderobject.getString("FIRSTNAME");
                                        thefirstname.setTextSize(20);
                                        //    thefirstname.setTextColor(ff000000);
                                        thefirstname.setText(firstname + " ");
                                        tr2.addView(thefirstname);
                                        thelastname = new TextView(getApplicationContext());

                                        thelastname.setTextSize(20);
                                        lastname = senderobject.getString("LASTNAME");
                                        thelastname.setText(lastname + " ");
                                        tr2.addView(thelastname);
                                        reciever_Message = new TextView(getApplicationContext());
                                        message = senderobject.getString("Message");
                                        reciever_Message.setText(message + " ");
                                        reciever_Message.setTextSize(20);
                                        tr3.addView(reciever_Message);

                                        cusinetype = senderobject.getString("CUSINETYPE");
                                        startdate = senderobject.getString("STARTTIME");
                                        enddate = senderobject.getString("ENDTIME");
                                        budget = senderobject.getDouble("BUDGET");
                                        numberofperson = senderobject.getInt("NUMBEROFPERSON");

                                        post_id = senderobject.getInt("POST_ID");
                                        //  tr3 = new TableRow(getApplicationContext());

                                        tb.addView(tr);
                                        tb.addView(tr2);
                                        tb.addView(tr3);

                                        datainfo.setPost_id(post_id);
                                        datainfo.setUser_id(sender_id);
                                        datainfo.setReciever_user_id(reciever_id);
                                        tr2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent bridge = new Intent(getApplicationContext(), sendernotification.class);

                                                bridge.putExtra("FIRSTNAME", firstname);
                                                bridge.putExtra("LASTNAME", lastname);
                                                bridge.putExtra("PLACE", message);
                                                bridge.putExtra("CUSINE", cusinetype);
                                                bridge.putExtra("BUDGET", budget);
                                                bridge.putExtra("NUMBEROFPERSON", numberofperson);
                                                bridge.putExtra("STARTDATE", startdate);
                                                bridge.putExtra("ENDDATE", enddate);
                                                bridge.putExtra("MESSAGE", message);

                                                startActivity(bridge);
                                            }
                                        });
                                    }else{

                                        Toast.makeText(getApplicationContext(),"YOU DIDNOT SEND ANY MESSAGE",Toast.LENGTH_LONG).
                                                show();
                                        tb.removeAllViews();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                } catch (Exception e) {
                    e.getMessage();
                }

                return null;

            } catch (MalformedURLException e) {
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

        }
    }
}
