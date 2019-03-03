package com.example.lunchbuddies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Adminpanel extends AppCompatActivity {

    Button add,removeuser,removeevent,adduser,postRemove;

    String removeUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        add=findViewById(R.id.addevent);
        removeuser=findViewById(R.id.removeuser2);
        removeevent=findViewById(R.id.removeevent);
        adduser=findViewById(R.id.adduser);

        postRemove=findViewById(R.id.removepost);
        Button user_signout=findViewById(R.id.signout);

        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
        postRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),Removepost.class);
                startActivity(bridge);
            }
        });
        Toast.makeText(getApplicationContext(),"Please Select one of the following mentioned button",Toast.LENGTH_LONG).show();
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),Registration.class);
                startActivity(bridge);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),AddEvent.class);
                startActivity(bridge);
            }
        });
        removeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent bridge=new Intent(getApplicationContext(),Removeuser.class);
               startActivity(bridge);
            }
        });

        removeevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),RemoveList.class);
                startActivity(bridge);
            }
        });

    }


    private class MyTask extends AsyncTask<Void, Void, Void> {
        int fulluid;
        String firstname;

        public int getFulluid() {
            return fulluid;
        }

        public void setFulluid(int fulluid) {
            this.fulluid = fulluid;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        @Override

        protected Void doInBackground(Void... params) {


            URL url = null;

            try {

                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/RemoveUser&"+removeUser
                        );

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

                //int userID = obj.getInt("User_id");

                firstname=obj.getString("Message");
                System.out.println(firstname);
//                DataInfo datainfo=DataInfo.getInstance();
//                datainfo.setUser_id(userID);


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
            Intent bridge=new Intent(getApplicationContext(),Listofpost.class);
            startActivity(bridge);
        }

    }
}
