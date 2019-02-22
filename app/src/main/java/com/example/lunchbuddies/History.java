package com.example.lunchbuddies;

import android.annotation.SuppressLint;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class History extends AppCompatActivity {
    Button sendMessage;
    EditText message;
    int recieveruser_id;
    int sender_id;
    String sender_messsage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DataInfo datainfo=DataInfo.getInstance();
        sender_id=datainfo.getUser_id();
        recieveruser_id=datainfo.getReciever_user_id();
        System.out.println(datainfo.getUser_id());
        System.out.println(datainfo.getReciever_user_id());
        message=findViewById(R.id.send_message);
        sendMessage=findViewById(R.id.sendmessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sender_messsage=message.getText().toString();
                new MyTask2().execute();

            }
        });
    }



    private class MyTask2 extends AsyncTask<Void, Void, Void> {


        String user_status;
        @SuppressLint("WrongThread")
        @Override

        protected Void doInBackground(Void... params) {



            URL url = null;

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String datetime = dateformat.format(c.getTime());
            System.out.println(datetime);
            try {

                url = new URL("http://192.168.0.107:8888/lunchbuddies/mobile/application/sendmessage&" +sender_messsage+"&"+sender_id+
                        "&"+recieveruser_id+"&"+datetime);

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
//
//


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

            if(user_status.equals("OK")){
                Intent bridge=new Intent(getApplicationContext(),Listofpost.class);
                Toast.makeText(getApplicationContext(), "Your Message is Succesfully Send", Toast.LENGTH_SHORT).show();

                startActivity(bridge);

            }else{

                Toast.makeText(getApplicationContext(),"Some information went wrong",Toast.LENGTH_SHORT).show();
            }

    }

    }
}

