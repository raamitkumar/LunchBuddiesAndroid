package com.example.lunchbuddies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class Editpost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpost);
        Toast.makeText(getApplicationContext(), "PLEASE SELECT OF THE ITEM OF LIST VIEW TO EDIT YOUR POST", Toast.LENGTH_LONG).show();

        new MyTask().execute();
    }
   @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class MyTask extends AsyncTask<Void, Void, Void> {
       TextView thePlace, cusineType, startTime,tx1;
        int fulluid;
        TableLayout tbLayout=findViewById(R.id.tblayoutpost);
        TableRow tr1,tr2;
        String firstname;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startdatetime = dateformat.format(c.getTime());
        String enddatetime = dateformat.format(c.getTime());


        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());


            URL url = null;

            try {

                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/view_post&"+datainfo.getUser_id());

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

                final JSONArray postarray = obj.getJSONArray("POSTDATA");
                runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {


                        tr1 = new TableRow(getApplicationContext());

                        tr2=new TableRow(getApplicationContext());
                        thePlace = new TextView(getApplicationContext());
                        thePlace.setText(" Place ");
                        thePlace.setTextSize(20);
                        thePlace.setAlpha(1);
                        thePlace.setTextColor(Color.BLACK);
                        //email.setTextColor(R.color.colorPrimaryDark);
                        tr1.addView(thePlace);


                        cusineType = new TextView(getApplicationContext());
                        cusineType.setText(" CusineType ");
                        cusineType.setAlpha(1);
                        cusineType.setTextSize(20);

                        cusineType.setTextColor(Color.BLACK);
                        //  firstName.setTextColor(Color.Black);

                        tr1.addView(cusineType);

                        tx1=new TextView(getApplicationContext());


                        startTime = new TextView(getApplicationContext());
                        startTime.setText(" StartTime ");
                        startTime.setTextSize(20);
                        startTime.setAlpha(1);
                        startTime.setTextColor(Color.BLACK
                        );
                        cusineType.setTypeface(null, Typeface.BOLD_ITALIC);

                        thePlace.setTypeface(null,Typeface.BOLD_ITALIC);
                        startTime.setTypeface(null, Typeface.BOLD_ITALIC);


                        tr1.addView(startTime);

                        //tr2.addView(tx1);
                        tbLayout.addView(tr1);

                      //  tbLayout.addView(tr2);
                        String place = null;
                        String cusinetype = null;
                        String startdate = null;
                        String enddate =null;
                        int post_id = 0;
                        int user_id;
                         int numberofperson =0;
                         double budget = 0;


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
                                place = arrayobj.getString("PLACE");


                                thePlace.setTextSize(20);
                                thePlace.setAlpha(1);
                                cusineType.setTextSize(20);
                                cusineType.setAlpha(1);
                                startTime.setTextSize(20);
                                startTime.setAlpha(0.99f);

                                thePlace.setText(place + "  ");
                                tr1.addView(thePlace);
                                cusinetype = arrayobj.getString("CUSINETYPE");
                                cusineType.setText(cusinetype + "  ");
                                tr1.addView(cusineType);
                                startdate = arrayobj.getString("STARTTIME");
                                startTime.setText(startdate + "  ");
                                tr1.addView(startTime);
                                enddate = arrayobj.getString("ENDTIME");
                                numberofperson = arrayobj.getInt("NUMBEROFPERSON");
                                user_id = arrayobj.getInt("USER_ID");
                                post_id = arrayobj.getInt("POST_ID");
                                budget = arrayobj.getDouble("BUDGET");

                                tr2.addView(tx1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            tr1.setAlpha(1);
                            tr2.setAlpha(1);

                            tbLayout.addView(tr1);
                            tbLayout.addView(tr2);
                            //tablelayout.addView(tr2);

                            final int finalPost_id = post_id;
                            final String finalPlace = place;
                            final String finalCusinetype = cusinetype;
                            final int finalNumberofperson = numberofperson;
                            final double finalBudget = budget;
                            final String finalStartdate = startdate;
                            final String finalEnddate = enddate;
                            final int finalPost_id1 = post_id;
                            tr1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent bridge = new Intent(getApplicationContext(), SingleeditPost.class);
                                    bridge.putExtra("postid", finalPost_id1);
                                    System.out.println(finalPost_id1);
                                    bridge.putExtra("PLACE", finalPlace);
                                    bridge.putExtra("CUSINETYPE", finalCusinetype);
                                    bridge.putExtra("NUMBEROFPERSON", finalNumberofperson);
                                    bridge.putExtra("BUDGET", finalBudget);
                                    bridge.putExtra("STARTDATE", finalStartdate);
                                    bridge.putExtra("ENDDATE", finalEnddate);

                                    bridge.putExtra("POSTID", finalPost_id1);
                                    startActivity(bridge);
                                }
                            });




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
}
