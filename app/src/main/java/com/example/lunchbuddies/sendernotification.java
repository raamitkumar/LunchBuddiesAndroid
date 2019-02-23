package com.example.lunchbuddies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class sendernotification extends AppCompatActivity {
    String firstname, lastname, place, cusinetype, startdate, enddate, user_status;
    int numberofperson;
    double budget;

    TextView reciver_place, fn, ln, reciever_cusine, sender_numberofperson, reciever_startdate, reciever_enddate, reciever_budget, invitation_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendernotification);

        invitation_status = findViewById(R.id.user_status);
        reciver_place = findViewById(R.id.place);
        fn = findViewById(R.id.firstname);
        ln = findViewById(R.id.lastname);
        reciever_cusine = findViewById(R.id.cusinetype);
        sender_numberofperson = findViewById(R.id.numberofperson);
        reciever_enddate = findViewById(R.id.endtime2);
        reciever_startdate = findViewById(R.id.starttime);
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
        user_status = bundle.getString("INVITATIONSTATUS");

        invitation_status.setText(firstname+" "+lastname +" "+ user_status + "ED THE INVITATION");
        reciever_budget.setText(budget + "");
        sender_numberofperson.setText(numberofperson + "");
        fn.setText(firstname);
        ln.setText(lastname);
        reciver_place.setText(place);
        reciever_startdate.setText(startdate);
        reciever_enddate.setText(enddate);
        reciever_cusine.setText(cusinetype);
    }

}
