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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RemoveList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_list);
        new MyTask().execute();
        Button user_signout=findViewById(R.id.signout);
        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
    }
int eventId;

    private class MyTask extends AsyncTask<Void, Void, Void> {
        int event_id;
        String firstname;
        TableLayout tblayout;
        TableRow tr1, tr2;
        TextView thePlace, cusineType, startTime,tx1;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startdatetime = dateformat.format(c.getTime());
        String enddatetime = dateformat.format(c.getTime());

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());

            tblayout=findViewById(R.id.tbLayout);

            URL url = null;

            try {

                url = new URL("http://172.24.208.170:8888/lunchbuddies/mobile/application/viewEvent");

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();

                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);

                InputStreamReader myInput = new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                final StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
                final JSONObject obj = new JSONObject(response.toString());

                final JSONArray postarray = obj.getJSONArray("EVENTDATA");
                runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {

                        tblayout.removeAllViews();

                        tr1 = new TableRow(getApplicationContext());

                        tr2=new TableRow(getApplicationContext());
                        thePlace = new TextView(getApplicationContext());
                        thePlace.setText(" Place ");
                        thePlace.setTextSize(20);
                       // email.setAlpha(1);
                        thePlace.setTextColor(Color.BLACK);
                        //email.setTextColor(R.color.colorPrimaryDark);
                        tr1.addView(thePlace);


                        cusineType = new TextView(getApplicationContext());
                        cusineType.setText(" Event Name ");
                        //firstName.setAlpha(1);
                        cusineType.setTextSize(20);

                        cusineType.setTextColor(Color.BLACK);
                        //  firstName.setTextColor(Color.Black);

                        tr1.addView(cusineType);

                        tx1=new TextView(getApplicationContext());


                        startTime = new TextView(getApplicationContext());
                        startTime.setText(" StartTime ");
                        startTime.setTextSize(20);
                        //lastName.setAlpha(1);
                        startTime.setTextColor(Color.BLACK
                        );
                        cusineType.setTypeface(null, Typeface.BOLD_ITALIC);

                        thePlace.setTypeface(null,Typeface.BOLD_ITALIC);
                        startTime.setTypeface(null, Typeface.BOLD_ITALIC);


                        tr1.addView(startTime);

                        tr2.addView(tx1);
                        tblayout.addView(tr1);

                        tblayout.addView(tr2);
                        String place = null, event_name = null, startdate = null, enddate;
                        int post_id = 0;
                        int user_id;
                        int numberofperson;
                        double budget;


                        JSONObject arrayobj = null;

                        for (int i = 0; i < postarray.length(); i++) {
                            tr1 = new TableRow(getApplicationContext());
                            tr2 = new TableRow(getApplicationContext());
                            thePlace = new TextView(getApplicationContext());
                            cusineType = new TextView(getApplicationContext());
                            startTime = new TextView(getApplicationContext());
                            tx1=new TextView(getApplicationContext());
                            startTime.setTextColor(Color.BLACK);

                            cusineType.setTextColor(Color.BLACK);

                            thePlace.setTextColor(Color.BLACK);
                            try {
                                arrayobj = postarray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                place = arrayobj.getString("EVENTPLACE");


                                thePlace.setTextSize(20);
                                thePlace.setAlpha(1);
                                cusineType.setTextSize(20);
                                cusineType.setAlpha(1);
                                startTime.setTextSize(20);
                                startTime.setAlpha(0.99f);

                                thePlace.setText(place + "  ");
                                tr1.addView(thePlace);
                                event_name = arrayobj.getString("EVENTNAME");
                                cusineType.setText(event_name + "  ");
                                tr1.addView(cusineType);
                                startdate = arrayobj.getString("STARTTIME");
                                startTime.setText(startdate + "  ");
                                tr1.addView(startTime);

                                event_id=arrayobj.getInt("EVENTID");
                                tr2.addView(tx1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            tr1.setAlpha(1);
                            tr2.setAlpha(1);

                            tblayout.addView(tr1);
                            tblayout.addView(tr2);
                            //tablelayout.addView(tr2);

                            final int finalPost_id = post_id;
                            tr1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent bridge = new Intent(getApplicationContext(), RemoveList.class);

                                    eventId=event_id;
                                    new MyTask2().execute();
                                    startActivity(bridge);
                                }
                            });

//


                        }


                    }
                });
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
        int fulluid;
        String user_status;

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public int getFulluid() {
            return fulluid;
        }

        public void setFulluid(int fulluid) {
            this.fulluid = fulluid;
        }


        @Override

        protected Void doInBackground(Void... params) {


            DataInfo datainfo=DataInfo.getInstance();


            URL url = null;

            try {

                url = new URL("http://172.24.208.170:8888/lunchbuddies/mobile/application/RemoveEvent&" +eventId );

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

                user_status=obj.getString("STATUS");

                //setFulluid(uid);
                System.out.println(user_status);




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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(user_status.equals("STATUS")){

                Intent brdige=new Intent(getApplicationContext(),Adminpanel.class);
                Toast.makeText(getApplicationContext(),"Event is Successfully remove",Toast.LENGTH_SHORT).show();
                startActivity(brdige);
            }else{
                Toast.makeText(getApplicationContext(),"Please enter correct details",Toast.LENGTH_SHORT).show();


            }

        }

    }



}
