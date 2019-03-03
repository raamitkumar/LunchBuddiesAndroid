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

public class Myprofile extends AppCompatActivity {

    TextView user_fn,user_ln,user_email,user_password,user_contactnumber;
    Button user_edit,user_notification,user_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        user_fn=findViewById(R.id.firstname);
        user_ln=findViewById(R.id.lastname);
        user_email=findViewById(R.id.email);
        user_password=findViewById(R.id.send_message);
        user_contactnumber=findViewById(R.id.contactnumber);
        user_edit=findViewById(R.id.editprofile);
        user_notification=findViewById(R.id.notification);
        user_history=findViewById(R.id.history);
        Button user_signout=findViewById(R.id.signout);

        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
        user_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),Editprofile.class);
                startActivity(bridge);
            }
        });

        user_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),Recievemessage.class);
                startActivity(bridge);
            }
        });

        user_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),SentandRecivePost.class);
                startActivity(bridge);
            }
        });
        new MyTask().execute();
    }



    private class MyTask extends AsyncTask<Void, Void, Void> {
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
        String fn,ln,emailid,cnumber,pass;

        @Override

        protected Void doInBackground(Void... params) {



            DataInfo datainfo=DataInfo.getInstance();


            fulluid=datainfo.getUser_id();

            URL url = null;

            try {

                url = new URL("http://172.24.208.170:8888/lunchbuddies/mobile/application/myprofile&"+ fulluid);

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

                fn=obj.getString("firstname");
                ln=obj.getString("lastname");
                emailid=obj.getString("email");
                cnumber=obj.getString("contactnumber");
                pass=obj.getString("Password");

                datainfo.setFirst_name(fn);
                datainfo.setLast_name(ln);
                datainfo.setContact_number(cnumber);
                datainfo.setEmail_id(emailid);
                datainfo.setPassword(pass);



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
            System.out.println(fn+ln+emailid+cnumber+pass);
            user_fn.setText(fn);
            user_ln.setText(ln);
            user_email.setText(emailid);
            user_contactnumber.setText(cnumber);
            user_password.setText(pass);

        }

    }
}
