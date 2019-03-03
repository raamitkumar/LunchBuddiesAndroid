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

public class Editprofile extends AppCompatActivity {

    EditText firstName, lastName, emailID, userpassword, userconfirmpassword, usercontactnumber;
    Button useredit;
    String fName, lName, useremail, uPassword, cPassword, contactNumber;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        emailID = findViewById(R.id.email);
        userpassword = findViewById(R.id.send_message);
        userconfirmpassword = findViewById(R.id.confirmpassword);
        usercontactnumber = findViewById(R.id.contactnumber);
        cPassword = userconfirmpassword.getText().toString();
        Toast.makeText(getApplicationContext(),"PLEASE ENTER THE INFORMATION FOR EDIT YOUR PROFILE",Toast.LENGTH_LONG).show();

        useredit = findViewById(R.id.edit);
        Button user_signout=findViewById(R.id.signout);

        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });

        final DataInfo datainfo=DataInfo.getInstance();
        useredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = firstName.getText().toString();
                lName = lastName.getText().toString();
                useremail = emailID.getText().toString();
                uPassword = userpassword.getText().toString();
                cPassword=userconfirmpassword.getText().toString();

                contactNumber = usercontactnumber.getText().toString();


                if(fName.isEmpty()) {
                    fName=datainfo.getFirst_name();
                }
                if(lName.isEmpty()){
                    lName = datainfo.getLast_name();
                }
                if(useremail.isEmpty()) {
                    useremail =datainfo.getEmail_id();
                }
                if(uPassword.isEmpty()) {
                    uPassword = datainfo.getPassword();
                }
                if(contactNumber.isEmpty()) {
                    contactNumber = datainfo.getContact_number();
                }
                if (uPassword.equals(cPassword)) {

                    new MyTask().execute();

                } else {
                    userpassword.setText("");
                    userpassword.setHint("PASSWORD");
                    userconfirmpassword.setText("");
                    userconfirmpassword.setHint("CONFIRMPASSWORD");
                    Toast.makeText(getApplicationContext(), "YOUR PASSWORD DOESNOT MATCH ", Toast.LENGTH_SHORT).show();

                }

            }
        });
        user_id=datainfo.getUser_id();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        int fulluid;
        String user_status;

        public int getFulluid() {
            return fulluid;
        }

        public void setFulluid(int fulluid) {
            this.fulluid = fulluid;
        }

        @Override

        protected Void doInBackground(Void... params) {


            URL url = null;

            try {

                url = new URL("http://172.24.208.170:8888/lunchbuddies/mobile/application/editprofile&"+user_id+"&"
                        + fName + "&" + lName + "&" + useremail+ "&" + uPassword + "&" + contactNumber );

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

                user_status=obj.getString("Status");
               // int userID = obj.getInt("User_id");

                System.out.println(user_status);
                //DataInfo datainfo = DataInfo.getInstance();
              //  datainfo.setUser_id(userID);


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
            if(user_status.equals("OK")) {
                Intent bridge = new Intent(getApplicationContext(), Listofpost.class);
                Toast.makeText(getApplicationContext(),"YOUR PROFILE IS EEDITED",Toast.LENGTH_LONG).show();
                startActivity(bridge);
            }else {


                Toast.makeText(getApplicationContext(),"YOU ENTERED WRONG INFORMATION",Toast.LENGTH_LONG).show();
            }}

    }
}