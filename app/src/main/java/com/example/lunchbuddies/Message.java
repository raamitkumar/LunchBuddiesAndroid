package com.example.lunchbuddies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Message extends AppCompatActivity {
    String firstname, lastname, place, cusinetype, startdate, enddate, user_status;
    int numberofperson;
    double budget;

    TextView reciver_place, fn, ln, reciever_cusine, sender_numberofperson, reciever_startdate, reciever_enddate, reciever_budget, invitation_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        invitation_status = findViewById(R.id.messagedetail);
        reciver_place = findViewById(R.id.place_editText);
        fn = findViewById(R.id.firstname);
        ln = findViewById(R.id.lastname);
        reciever_cusine = findViewById(R.id.cusinetype_editText);
        sender_numberofperson = findViewById(R.id.numberofperson_editText);
        reciever_enddate = findViewById(R.id.endtime2);
        reciever_startdate = findViewById(R.id.starttime_editText);
        reciever_budget = findViewById(R.id.budget);

        Bundle bundle = getIntent().getExtras();
        firstname = bundle.getString("FIRSTNAME");

        lastname = bundle.getString("LASTNAME");
        place = bundle.getString("PLACE");
        cusinetype = bundle.getString("CUSINE");
        startdate = bundle.getString("STARTDATE");
        enddate = bundle.getString("ENDDATE");
        numberofperson = bundle.getInt("NUMBEROFPERSON");
        budget = bundle.getDouble("BUDGET");
        user_status = bundle.getString("MESSAGE");
    }
}
