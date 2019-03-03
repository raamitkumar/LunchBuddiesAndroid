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

public class Removeuser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removeuser);

        Button user_signout=findViewById(R.id.signout);

        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
        new MyTask().execute();
    }
int userId=0;
    private class MyTask extends AsyncTask<Void, Void, Void> {
        int fulluid;
        String firstname;
        TableLayout tablelayout;
        TableRow tr1, tr2;
        TextView email, firstName, lastName,tx1;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startdatetime = dateformat.format(c.getTime());
        String enddatetime = dateformat.format(c.getTime());

        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {
            DataInfo datainfo = DataInfo.getInstance();
            System.out.println(datainfo.getUser_id());


            tablelayout= findViewById(R.id.tb);
            URL url = null;

            try {

                url = new URL("http://172.24.13.33:8080/lunchbuddies/mobile/application/profile");

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

                final JSONArray postarray = obj.getJSONArray("USERDATA");
                runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {

                        tablelayout.removeAllViews();

                        tr1 = new TableRow(getApplicationContext());

                        tr2=new TableRow(getApplicationContext());
                        email = new TextView(getApplicationContext());
                        email.setText(" Email ");
                        email.setTextSize(15);
                        email.setAlpha(1);
                        email.setTextColor(Color.BLACK);
                        //email.setTextColor(R.color.colorPrimaryDark);
                        tr1.addView(email);


                        firstName = new TextView(getApplicationContext());
                        firstName.setText(" FirstName ");
                        firstName.setAlpha(1);
                        firstName.setTextSize(15);

                        firstName.setTextColor(Color.BLACK);
                        //  firstName.setTextColor(Color.Black);

                        tr1.addView(firstName);

                        tx1=new TextView(getApplicationContext());


                        lastName = new TextView(getApplicationContext());
                        lastName.setText(" LastName ");
                        lastName.setTextSize(15);
                        lastName.setAlpha(1);
                        lastName.setTextColor(Color.BLACK
                        );
                        firstName.setTypeface(null, Typeface.BOLD_ITALIC);

                        email.setTypeface(null,Typeface.BOLD_ITALIC);
                        lastName.setTypeface(null, Typeface.BOLD_ITALIC);


                        tr1.addView(lastName);

                        tr2.addView(tx1);
                        tablelayout.addView(tr1);

                        tablelayout.addView(tr2);
                        String email_id = null, fn = null, ln = null, enddate;
                        int post_id = 0;
                        int user_id = 0;
                        int numberofperson;
                        double budget;


                        JSONObject arrayobj = null;

                        for (int i = 0; i < postarray.length(); i++) {
                            tr1 = new TableRow(getApplicationContext());
                            tr2 = new TableRow(getApplicationContext());
                            email = new TextView(getApplicationContext());
                            firstName = new TextView(getApplicationContext());
                            lastName = new TextView(getApplicationContext());
                            tx1=new TextView(getApplicationContext());
                            lastName.setTextColor(Color.BLACK);

                            firstName.setTextColor(Color.BLACK);

                            email.setTextColor(Color.BLACK);
                            try {
                                arrayobj = postarray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                email_id = arrayobj.getString("EMAIL");


                                email.setTextSize(15);
                                email.setAlpha(1);
                                firstName.setTextSize(15);
                                firstName.setAlpha(1);
                                lastName.setTextSize(15);
                                lastName.setAlpha(0.99f);

                                email.setText(email_id + "  ");
                                tr1.addView(email);
                                fn = arrayobj.getString("FIRSTNAME");
                                firstName.setText(fn + "  ");
                                tr1.addView(firstName);
                                ln = arrayobj.getString("LASTNAME");
                                lastName.setText(ln + "  ");
                                tr1.addView(lastName);
                                user_id = arrayobj.getInt("USER_ID");
                                tr2.addView(tx1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            tr1.setAlpha(1);
                            tr2.setAlpha(1);

                            tablelayout.addView(tr1);
                            tablelayout.addView(tr2);
                            //tablelayout.addView(tr2);


                            final int finalUser_id = user_id;
                            tr1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    new MyTask2().execute();
                                    userId= finalUser_id;
                                    Intent bridge = new Intent(getApplicationContext(), Removepost.class);
                                    Toast.makeText(getApplicationContext(),"USER IS SUCCESSFULLY REMOVED",Toast.LENGTH_LONG).show();
                                    bridge.putExtra("user_id", finalUser_id);
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

                url = new URL("http://172.24.208.170:8888/lunchbuddies/mobile/application/removeuser&" +userId );

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

            if(user_status.equals("OK")){

                Intent brdige=new Intent(getApplicationContext(),Adminpanel.class);
                Toast.makeText(getApplicationContext(),"POST is Successfully remove",Toast.LENGTH_SHORT).show();
                startActivity(brdige);
            }else{
                Toast.makeText(getApplicationContext(),"Please select  correct Item From List",Toast.LENGTH_SHORT).show();


            }

        }

    }



}


