package com.example.shubgupta.socketclienandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements Client.DataChanged{

    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    ListView lv;
    ArrayList<String> list;
    ArrayAdapter adapter;
    Client myClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);
        list  = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        buttonConnect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    myClient = new Client(MainActivity.this, editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()),
                            list, adapter);
                    myClient.execute();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }

    @Override
    public void onDataChanged() {
        adapter.notifyDataSetChanged();
        adapter.setNotifyOnChange(true);
    }
}
