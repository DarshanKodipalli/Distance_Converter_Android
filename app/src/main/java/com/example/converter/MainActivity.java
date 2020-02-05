package com.example.converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private static final String TAG = "MainActivity";
    private TextView inputHeading;
    private TextView outputHeading;
    private Button buttonText;
    private TextView finalResult;
    private TextView inputValue;
    private String radioInput = "Miles to Kilometers";
    private RadioButton radioInp;
    private RadioGroup radioGroup;
    private TextView history;
    private String conversionHistory;
    private TextView conversionHistoryHeadingText;
    private TextView applicationHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.radioGroup);
        inputHeading = findViewById(R.id.conversionInput);
        outputHeading = findViewById(R.id.convertedResult);
        buttonText = findViewById(R.id.convert);
        finalResult = findViewById(R.id.result);
        inputValue = findViewById(R.id.inputValue);
        radioInp = findViewById(R.id.mitokms);
        history = findViewById(R.id.conversionHistory);
        conversionHistory = "";
        finalResult.setEnabled(false);
        history.setEnabled(true);
        inputValue.setHint("Enter distance in Miles...");
        inputHeading.setTextColor(Color.parseColor("#FF000000"));
        outputHeading.setTextColor(Color.parseColor("#FF000000"));
        history.setMovementMethod(new ScrollingMovementMethod());
        history.setTextColor(Color.parseColor("#FF000000"));
        conversionHistoryHeadingText = findViewById(R.id.conversionHistoryHeading);
        conversionHistoryHeadingText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        conversionHistoryHeadingText.setTextColor(Color.parseColor("#FF000000"));
        applicationHeading = findViewById(R.id.applicationHeading);
        applicationHeading.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        inputValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    finalResult.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClickingRadioButton(View view){
        radioInput = ((RadioButton) view).getText().toString();
        String textViewField = ((TextView) view).getText().toString();
        if(radioInput.equals("Kilometers to Miles")){
            inputValue.setHint("Enter distance in Kilometers...");
            inputValue.setText("");
            finalResult.setText("");
            inputHeading.setText("Kilometers: ");
            outputHeading.setText("Miles:");
            buttonText.setText("Convert to Miles");
        }else {
            inputValue.setHint("Enter distance in Miles...");
            inputValue.setText("");
            finalResult.setText("");
            inputHeading.setText("Miles: ");
            outputHeading.setText("Kilometers : ");
            buttonText.setText("Convert to Kilometers");
        }
    }

    public void onConvertOption(View view){
        int id = radioGroup.getCheckedRadioButtonId();
        Log.d(TAG, "Radio Button ID: "+id);
        Log.d(TAG, "onConvertOption: "+R.id.mitokms);
        Log.d(TAG, "Input Value: "+inputValue.getText());
        if(inputValue.getText().toString().equals("")){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Invalid Input");
            alert.setMessage("Invalid input entered. Please enter a valid input");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputValue.setText("");
                    finalResult.setText("");
                }
            });
            alert.create().show();
        }else {
            Double inputValueInNumber = Double.parseDouble(inputValue.getText().toString());
            Double output;
            if(radioInput.equals("Kilometers to Miles")){
                Log.d(TAG, "Input in Kilometers: "+inputValueInNumber);
                output = (inputValueInNumber)*0.621371;
                output = Math.round(output*10.0)/10.0;
                conversionHistory = System.getProperty("line.separator")+"     "+inputValueInNumber.toString()+"  Km ==>  "+output.toString()+"  Mi"+conversionHistory;
                history.setText(conversionHistory);
                finalResult.setText("  "+output.toString()+"   miles");
                inputValue.setText("");
            }else {
                Log.d(TAG, "Input in Miles: "+inputValueInNumber);
                output = (inputValueInNumber)*1.60934;
                output = Math.round(output*10.0)/10.0;
                finalResult.setText("  "+output.toString()+"   kilometers");
                conversionHistory =System.getProperty("line.separator")+"     "+inputValueInNumber.toString()+"  Mi  ==>  "+output.toString()+"  Km"+conversionHistory;
                history.setText(conversionHistory);
                inputValue.setText("");
            }
        }
    }

    public void onClickingClearHistory(View view){
        if(conversionHistory.equals("")){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("History Status");
            alert.setMessage("Conversion history is already cleared!");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.create().show();
        }else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Clear History");
            alert.setMessage("Are you sure? It cannot be undone!!");
            alert.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    conversionHistory = "";
                    history.setText(conversionHistory);
                    inputValue.setText("");
                    finalResult.setText("");
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    history.setText(conversionHistory);
                }
            });
            alert.create().show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("ConversionHistory", history.getText().toString());
        outState.putString("ConvertedResult", finalResult.getText().toString());
        outState.putString("InputValue",inputValue.getText().toString());
        outState.putString("InputValueHeading", inputHeading.getText().toString());
        outState.putString("OutputHeading", outputHeading.getText().toString());
        outState.putString("ButtonText",buttonText.getText().toString());
        outState.putString("InputValueHint",inputValue.getHint().toString());
        outState.putString("CurrentState",radioInput);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        inputValue.setText(savedInstanceState.getString("InputValue"));
        conversionHistory = savedInstanceState.getString("ConversionHistory");
        history.setText(savedInstanceState.getString("ConversionHistory"));
        finalResult.setText(savedInstanceState.getString("ConvertedResult"));
        inputHeading.setText(savedInstanceState.getString("InputValueHeading"));
        outputHeading.setText(savedInstanceState.getString("OutputHeading"));
        buttonText.setText(savedInstanceState.getString("ButtonText"));
        inputValue.setHint(savedInstanceState.getString("InputValueHint"));
        radioInput = savedInstanceState.getString("CurrentState");
    }
}