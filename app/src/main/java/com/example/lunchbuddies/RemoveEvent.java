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

public class RemoveEvent extends AppCompatActivity {
    EditText event_name,event_place,event_date;
    Button removeevent;
    String ename,eplace,edate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_event);

        event_name=findViewById(R.id.eventname);
        event_place=findViewById(R.id.eventplace);
        event_date=findViewById(R.id.startdate);

        removeevent=findViewById(R.id.removeevent);
        removeevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ename=event_name.getText().toString();
                eplace=event_place.getText().toString();
                edate=event_date.getText().toString();
                new MyTask().execute();
            }
        });
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


    @Override

    protected Void doInBackground(Void... params) {


        DataInfo datainfo=DataInfo.getInstance();


        URL url = null;

        try {

            url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/RemoveEvent&" + ename  + "&" + eplace+"&"+edate );

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




