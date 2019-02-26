package com.example.lunchbuddies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SingleeditPost extends AppCompatActivity {

    String place, cusinetype, startdate, enddate;
    String post_place, post_cusinetype, post_startdate, post_enddate;
    int post_id;
    int user_id;
    int numberofperson,post_numberofperson;
    double budget,post_budget=0.0;


    EditText theplace, thecusine, starttime, endtime, thenumberofperson, thebudget;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleedit_post);
        Bundle bundle=getIntent().getExtras();


        theplace = findViewById(R.id.place_editText);
        thecusine = findViewById(R.id.cusinetype_editText);
        starttime = findViewById(R.id.starttime_editText);
        endtime = findViewById(R.id.endtime_editText);
        thenumberofperson = findViewById(R.id.numberofperson_editText);
        thebudget = findViewById(R.id.budget3_editText);
        edit=findViewById(R.id.edit);

        place=bundle.getString("PLACE");
        cusinetype=bundle.getString("CUSINETYPE");
        startdate=bundle.getString("STARTDATE");
        enddate=bundle.getString("ENDDATE");
        numberofperson=bundle.getInt("NUMBEROFPERSON");
        budget=bundle.getDouble("BUDGET");
        post_id=bundle.getInt("postid");

        theplace.setHint(place);
        thecusine.setHint(cusinetype);
        starttime.setHint(startdate);
        endtime.setHint(enddate);
        thenumberofperson.setHint(numberofperson+"");
        thebudget.setHint(budget+"");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //place=theplace.getText().toString();
                //cusinetype=thecusine.getText().toString();
                //startdate=starttime.getText().toString();
                //enddate=endtime.getText().toString();
                //budget=Double.parseDouble(thebudget.getText().toString());
                //numberofperson=Integer.parseInt(thenumberofperson.getText().toString());
                //System.out.println(budget);

                if(thebudget.getText().toString().isEmpty()){

                    post_budget=budget;
                }
                else{
                    post_budget=Double.parseDouble(thebudget.getText().toString());
                }
                if(theplace.getText().toString().isEmpty()){

                    post_place=place;

                }else
                {
                    post_place=theplace.getText().toString();
                }
                if(thecusine.getText().toString().isEmpty()){
                    post_cusinetype=cusinetype;
                }else{
                    post_cusinetype=thecusine.getText().toString();

                }

                if(starttime.getText().toString().isEmpty()){
                    post_startdate=startdate;
                }else{
                    post_startdate=starttime.getText().toString();

                }


                if(endtime.getText().toString().isEmpty()){
                    post_enddate=enddate;
                }else{
                    post_enddate=endtime.getText().toString();

                }

                new MyTask().execute();

            }
        });

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

                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/editpost&"+post_id+"&"
                        + place + "&" + numberofperson + "&" + post_budget+ "&" + post_cusinetype + "&" + startdate
                        +"&"+enddate);

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
                Toast.makeText(getApplicationContext(),"YOUR Post IS EDITED",Toast.LENGTH_LONG).show();
                startActivity(bridge);
            }else {


                Toast.makeText(getApplicationContext(),"YOU ENTERED WRONG INFORMATION",Toast.LENGTH_LONG).show();
            }}

    }
}
