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
    TableLayout tb;
    TableRow tr,tr2,tr3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recievemessage);

        tb = findViewById(R.id.tblayout);

        recieve_Message = findViewById(R.id.recieve);
        send_Message = findViewById(R.id.send);

        recieve_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask2().execute();
            }
        });

    }

    private class MyTask2 extends AsyncTask<Void, Void, Void> {
        int sender_id, post_id, reciever_id;
        String firstname, lastname, message, cusinetype, startdate, enddate, invitationStatus;
        int numberofperson;


        double budget;

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            final DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());


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
                    final JSONArray senderarray = obj.getJSONArray("Data");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            tr2 = new TableRow(getApplicationContext());
                            tr3 = new TableRow(getApplicationContext());

                            tr = new TableRow(getApplicationContext());
                            thefirstname = new TextView(getApplicationContext());
                            thefirstname.setText("RECIEVERFIRSTNAME  ");
                            thefirstname.setTextSize(20);
                            thefirstname.setTypeface(null, Typeface.BOLD_ITALIC);
                            tr2.addView(thefirstname);

                            thelastname = new TextView(getApplicationContext());
                            thelastname.setText("RECIEVERLASTNAME");
                            thelastname.setTextSize(20);
                            thelastname.setTypeface(null, Typeface.BOLD_ITALIC);
                            tr2.addView(thelastname);

                            reciever_Message = new TextView(getApplicationContext());
                            reciever_Message.setText("RECIEVER MESSAGE  ");
                            reciever_Message.setTextSize(20);
                            reciever_Message.setTypeface(null, Typeface.BOLD_ITALIC);
                            reciever_Message.setTextColor(Color.DKGRAY);
                            tr3.addView(recieve_Message);


                            //  tr2=new TableRow(getApplicationContext());


                            tb.addView(tr);
                            tb.addView(tr2);
                            tb.addView(tr3);


                            JSONObject senderobject = new JSONObject();
                            for (int i = 0; i < senderarray.length(); i++) {
                                try {
                                    senderobject = senderarray.getJSONObject(i);

                                    reciever_id = senderobject.getInt("RECIEVERUSER_ID");

                                    tr3 = new TableRow(getApplicationContext());

                                    tr = new TableRow(getApplicationContext());
                                    tr2 = new TableRow(getApplicationContext());

                                    thefirstname = new TextView(getApplicationContext());
                                    firstname = senderobject.getString("RECIEVERFIRSTNAME");
                                    thefirstname.setTextSize(20);
                                    //    thefirstname.setTextColor(ff000000);
                                    thefirstname.setText(firstname + " ");
                                    tr2.addView(thefirstname);
                                    thelastname = new TextView(getApplicationContext());

                                    thelastname.setTextSize(20);
                                    lastname = senderobject.getString("RECIEVERLASTNAME");
                                    thelastname.setText(lastname + " ");
                                    tr2.addView(thelastname);
                                    reciever_Message = new TextView(getApplicationContext());
                                    message = senderobject.getString("MESSAGE");
                                    reciever_Message.setText(message + " ");
                                    reciever_Message.setTextSize(20);
                                    tr3.addView(reciever_Message);

                                    cusinetype = senderobject.getString("CUSINETYPE");
                                    startdate = senderobject.getString("STARTTIME");
                                    enddate = senderobject.getString("ENDTIME");
                                    budget = senderobject.getDouble("BUDGET");
                                    numberofperson = senderobject.getInt("NUMBEROFPERSON");
                                    sender_id = senderobject.getInt("SenderUSER_ID");
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
