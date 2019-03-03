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

public class SentandRecivePost extends AppCompatActivity {
    Button notification, myprofile, sent, recieve;

    TableLayout tbLayout,tbLayout2;
    TableRow tr1, tr2, tr3;
    TextView thePlace, cusineType, thefirstname, thelastname;
    Button message,notificationuser,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentand_recive_post);

        notification = findViewById(R.id.notification);
        myprofile = findViewById(R.id.myprofile);
        sent = findViewById(R.id.sentpost);
        recieve = findViewById(R.id.recievepost);
        Button user_signout=findViewById(R.id.signout);
        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),Listofpost.class);
                startActivity(bridge);
            }
        });

        message=findViewById(R.id.message);
    message.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent bridge=new Intent(getApplicationContext(),Recievemessage.class);
            startActivity(bridge);
        }
    });
        tbLayout = findViewById(R.id.tblayout);

        tbLayout2=findViewById(R.id.tblayout2);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge = new Intent(getApplicationContext(), Myprofile.class);
                startActivity(bridge);
            }
        });

        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
        recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tbLayout2.removeAllViews();
                new MyTask().execute();
            }


        });
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbLayout.removeAllViews();

                new MyTask2().execute();
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        int sender_id, post_id, reciever_id;
        String firstname, lastname, place, cusinetype, startdate, enddate;
        int numberofperson;
        double budget;

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            final DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());


            URL url = null;

            try {

                url = new URL("http://172.24.13.33:8080/lunchbuddies/mobile/application/viewrecieverinvitation&" + datainfo.getUser_id());

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
                    final JSONArray senderarray = obj.getJSONArray("SENDERDATA");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            tr1=new TableRow(getApplicationContext());
                            thefirstname = new TextView(getApplicationContext());
                            thefirstname.setText("FIRSTNAME");
                            tr1.addView(thefirstname);

                            thelastname = new TextView(getApplicationContext());
                            thelastname.setText("LASTNAME");
                            tr1.addView(thelastname);

                            thePlace = new TextView(getApplicationContext());
                            thePlace.setText("PLACE");
                            tr1.addView(thePlace);

                            cusineType = new TextView(getApplicationContext());
                            cusineType.setText("CUSINE TYPE");
                            tr1.addView(cusineType);

                            tr2=new TableRow(getApplicationContext());

                            tbLayout.addView(tr1);
                            tbLayout.addView(tr2);


                            JSONObject senderobject = new JSONObject();
                            for (int i = 0; i < senderarray.length(); i++) {
                                try {
                                    senderobject = senderarray.getJSONObject(i);

                                    tr1 = new TableRow(getApplicationContext());
                                    tr2 = new TableRow(getApplicationContext());
                                    thefirstname = new TextView(getApplicationContext());
                                    firstname = senderobject.getString("SENDERFIRSTNAME");
                                    thefirstname.setTextSize(20);
                                    //    thefirstname.setTextColor(ff000000);
                                    thefirstname.setText(firstname + " ");
                                    tr1.addView(thefirstname);
                                    thelastname = new TextView(getApplicationContext());

                                    thelastname.setTextSize(20);
                                    lastname = senderobject.getString("SENDERLASTNAME");
                                    thelastname.setText(lastname + " ");
                                    tr1.addView(thelastname);
                                    thePlace = new TextView(getApplicationContext());
                                    place = senderobject.getString("PLACE");
                                    thePlace.setText(place + " ");
                                    thePlace.setTextSize(20);
                                    tr2.addView(thePlace);
                                    cusineType = new TextView(getApplicationContext());
                                    cusinetype = senderobject.getString("CUISINETYPE");
                                    startdate = senderobject.getString("STARTTIME");
                                    enddate = senderobject.getString("ENDTIME");
                                    budget = senderobject.getDouble("BUDGET");
                                    numberofperson = senderobject.getInt("NUMBEROFPERSON");
                                    cusineType.setTextSize(20);
                                    cusineType.setText(cusinetype + " ");
                                    tr2.addView(cusineType);
                                    sender_id = senderobject.getInt("SENDERUSER_ID");

                                    post_id = senderobject.getInt("POST_ID");
                                    reciever_id = senderobject.getInt("RECIEVERUSER_ID");


                                    tbLayout.addView(tr1);
                                    tbLayout.addView(tr2);


                                    datainfo.setPost_id(post_id);
                                    datainfo.setUser_id(sender_id);
                                    datainfo.setReciever_user_id(reciever_id);
                                    tr1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent bridge = new Intent(getApplicationContext(), UserNotification.class);

                                            bridge.putExtra("FIRSTNAME", firstname);
                                            bridge.putExtra("LASTNAME", lastname);
                                            bridge.putExtra("PLACE", place);
                                            bridge.putExtra("CUSINE", cusinetype);
                                            bridge.putExtra("BUDGET", budget);
                                            bridge.putExtra("NUMBEROFPERSON", numberofperson);
                                            bridge.putExtra("STARTDATE", startdate);
                                            bridge.putExtra("ENDDATE", enddate);

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

        }
    }

    private class MyTask2 extends AsyncTask<Void, Void, Void> {
        int sender_id, post_id, reciever_id;
        String firstname, lastname, place, cusinetype, startdate, enddate,invitationStatus;
        int numberofperson;
        double budget;

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            final DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());


            URL url = null;

            try {

                url = new URL("http://172.24.13.33:8080/lunchbuddies/mobile/application/viewsendedinvitation&" + datainfo.getUser_id());

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


                            tr2=new TableRow(getApplicationContext());
                            tr3=new TableRow(getApplicationContext());

                            tr1=new TableRow(getApplicationContext());
                            thefirstname = new TextView(getApplicationContext());
                            thefirstname.setText("RECIEVERFIRSTNAME  ");
                            thefirstname.setTextSize(20);
                            thefirstname.setTypeface(null, Typeface.BOLD_ITALIC);
                            tr2.addView(thefirstname);

                            thelastname = new TextView(getApplicationContext());
                            thelastname.setText("RECIEVERLASTNAME");
                            thelastname.setTextSize(20);
                            thelastname.setTypeface(null,Typeface.BOLD_ITALIC);
                            tr2.addView(thelastname);

                            thePlace = new TextView(getApplicationContext());
                            thePlace.setText("PLACE  ");
                            thePlace.setTextSize(20);
                            thePlace.setTypeface(null,Typeface.BOLD_ITALIC);
                            thePlace.setTextColor(Color.DKGRAY);
                            tr3.addView(thePlace);

                            cusineType = new TextView(getApplicationContext());
                            cusineType.setText("CUSINE TYPE");
                            cusineType.setTextSize(20);
                            cusineType.setTypeface(null,Typeface.BOLD_ITALIC);
                            tr3.addView(cusineType);

                          //  tr2=new TableRow(getApplicationContext());


                            tbLayout.addView(tr1);
                            tbLayout.addView(tr2);
                            tbLayout.addView(tr3);


                            JSONObject senderobject = new JSONObject();
                            for (int i = 0; i < senderarray.length(); i++) {
                                try {
                                    senderobject = senderarray.getJSONObject(i);

                                    tr3 = new TableRow(getApplicationContext());

                                    tr1 = new TableRow(getApplicationContext());
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
                                    thePlace = new TextView(getApplicationContext());
                                    place = senderobject.getString("PLACE");
                                    thePlace.setText(place + " ");
                                    thePlace.setTextSize(20);
                                    tr3.addView(thePlace);
                                    cusineType = new TextView(getApplicationContext());
                                    cusinetype = senderobject.getString("CUSINETYPE");
                                    startdate = senderobject.getString("STARTTIME");
                                    enddate = senderobject.getString("ENDTIME");
                                    budget = senderobject.getDouble("BUDGET");
                                    numberofperson = senderobject.getInt("NUMBEROFPERSON");
                                    cusineType.setTextSize(20);
                                    cusineType.setText(cusinetype + " ");
                                    tr3.addView(cusineType);
                                    sender_id = senderobject.getInt("SenderUSER_ID");

                                    post_id = senderobject.getInt("POST_ID");
                                    reciever_id = senderobject.getInt("RECIEVERUSER_ID");
                                    invitationStatus=senderobject.getString("INVITATIONSTATUS");


                                  //  tr3 = new TableRow(getApplicationContext());

                                    tbLayout.addView(tr1);
                                    tbLayout.addView(tr2);
                                    tbLayout.addView(tr3);

                                    datainfo.setPost_id(post_id);
                                    datainfo.setUser_id(sender_id);
                                    datainfo.setReciever_user_id(reciever_id);
                                    tr2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent bridge = new Intent(getApplicationContext(), sendernotification.class);

                                            bridge.putExtra("FIRSTNAME", firstname);
                                            bridge.putExtra("LASTNAME", lastname);
                                            bridge.putExtra("PLACE", place);
                                            bridge.putExtra("CUSINE", cusinetype);
                                            bridge.putExtra("BUDGET", budget);
                                            bridge.putExtra("NUMBEROFPERSON", numberofperson);
                                            bridge.putExtra("STARTDATE", startdate);
                                            bridge.putExtra("ENDDATE", enddate);
                                            bridge.putExtra("INVITATIONSTATUS",invitationStatus);

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

        }
    }
}