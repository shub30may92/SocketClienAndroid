package com.example.shubgupta.socketclienandroid;

/**
 * Created by shubgupta on 1/27/18.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends AsyncTask<Void, Void, String> {

    String dstAddress;
    int dstPort;
    int available;
    String response = "";
    TextView textResponse;
    ArrayList<String> list;
    ArrayAdapter adapter;

    public interface DataChanged {
        void onDataChanged();
    }

    public DataChanged delegate = null;

    Client(DataChanged delegate, String addr, int port, ArrayList<String> list, ArrayAdapter adapter) {
        dstAddress = addr;
        dstPort = port;
        this.list = list;
        this.adapter = adapter;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

            while(true) {
                response = "";
                byte[] buffer = new byte[1024];
                InputStream inputStream = socket.getInputStream();

                available = inputStream.available();
                System.out.print("Characters printed:   ");
                char c = (char) inputStream.read();
                while (c != '\n') {
                    response += c;
                    c = (char) inputStream.read();
                    System.out.print(c);
                }
                System.out.println("RESPONSE:  " + response);
                list.add(response);
                publishProgress();
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }
        return response;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        delegate.onDataChanged();
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("RESULT:  " +result);
        list.add(result);
        adapter.notifyDataSetChanged();
        adapter.setNotifyOnChange(true);
        super.onPostExecute(result);
    }

}