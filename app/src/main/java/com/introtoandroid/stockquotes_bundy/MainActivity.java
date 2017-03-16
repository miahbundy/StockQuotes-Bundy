package com.introtoandroid.stockquotes_bundy;

import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import android.util.Log;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public String input;
    String symbol;
    String name;
    String ltp;
    String ltt;
    String change;
    String week;
    boolean bad =false;

    EditText editText;

    TextView tvSym;
    TextView tvName;
    TextView tvLTP;
    TextView tvLTT;
    TextView tvChange;
    TextView tvWeek;

    Stock stock1;

    Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        tvSym = (TextView) findViewById(R.id.textView);
        tvName = (TextView) findViewById(R.id.textView2);
        tvLTP = (TextView) findViewById(R.id.textView3);
        tvLTT = (TextView) findViewById(R.id.textView4);
        tvChange = (TextView) findViewById(R.id.textView5);
        tvWeek = (TextView) findViewById(R.id.textView6);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input = editText.getText().toString();
                if (!input.contains(" ") && input.length()>0) {
                    stock1 = new Stock(input);

                    new Thread() {
                        public void run() {
                            try {
                                stock1.load();
                            } catch (IOException e) {
                            }

                            symbol = stock1.getSymbol();
                            name = stock1.getName();
                            ltp = stock1.getLastTradePrice();
                            ltt = stock1.getLastTradeTime();
                            change = stock1.getChange();
                            week = stock1.getRange();


                            if (stock1.getName().equals("/")) {
                                bad = true;
                                symbol = "";
                                name = "";
                                ltp = "";
                                ltt = "";
                                change = "";
                                week = "";

                            }

                        }
                    }.start();


                    }

                    tvSym.setText(symbol);
                    tvName.setText(name);
                    tvLTP.setText(ltp);
                    tvLTT.setText(ltt);
                    tvChange.setText(change);
                    tvWeek.setText(week);

                    if(bad) {
                        Toast.makeText(MainActivity.this, "Unable to find stock", Toast.LENGTH_LONG).show();
                    }

                }



        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("input",editText.getText().toString());
        outState.putString("symbol",tvSym.getText().toString());
        outState.putString("name",tvName.getText().toString());
        outState.putString("ltp",tvLTP.getText().toString());
        outState.putString("ltt",tvLTT.getText().toString());
        outState.putString("change",tvChange.getText().toString());
        outState.putString("week",tvWeek.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getString("input"));
        tvSym.setText(savedInstanceState.getString("symbol"));
        tvName.setText(savedInstanceState.getString("name"));
        tvLTP.setText(savedInstanceState.getString("ltp"));
        tvLTT.setText(savedInstanceState.getString("ltt"));
        tvChange.setText(savedInstanceState.getString("change"));
        tvWeek.setText(savedInstanceState.getString("week"));
    }

}

