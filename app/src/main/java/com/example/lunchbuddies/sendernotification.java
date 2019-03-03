package com.example.lunchbuddies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sendernotification extends AppCompatActivity {
    String firstname, lastname, place, cusinetype, startdate, enddate, user_status;
    int numberofperson;
    double budget;

    TextView reciver_place, fn, ln, reciever_cusine, sender_numberofperson, reciever_startdate, reciever_enddate, reciever_budget, invitation_status;
Button sendmessage;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendernotification);

        invitation_status = findViewById(R.id.messagedetail);
        reciver_place = findViewById(R.id.place_editText);
        fn = findViewById(R.id.firstname);
        ln = findViewById(R.id.lastname);
        reciever_cusine = findViewById(R.id.cusinetype3);
        sender_numberofperson = findViewById(R.id.numberofperson_editText);
        reciever_enddate = findViewById(R.id.endtime2);
        reciever_startdate = findViewById(R.id.starttime_editText);
        reciever_budget = findViewById(R.id.budget);
        Button user_signout=findViewById(R.id.signout);

        user_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(bridge);
            }
        });
        sendmessage=findViewById(R.id.button_message);

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

        invitation_status.setText(user_status);
        reciever_budget.setText(budget + "");
        sender_numberofperson.setText(numberofperson + "");
        fn.setText(firstname);
        ln.setText(lastname);
        reciver_place.setText(place);
        reciever_startdate.setText(startdate);
        reciever_enddate.setText(enddate);
        reciever_cusine.setText(cusinetype);
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bridge=new Intent(getApplicationContext(),History.class);
                startActivity(bridge);
            }
        });
    }

}
