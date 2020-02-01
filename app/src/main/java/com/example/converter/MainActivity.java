package com.example.converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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
        history.setEnabled(false);
        inputHeading.setTextColor(Color.parseColor("#FF000000"));
        outputHeading.setTextColor(Color.parseColor("#FF000000"));
        history.setMovementMethod(new ScrollingMovementMethod());
        history.setTextColor(Color.parseColor("#FF000000"));
    }

    public void onClickingRadioButton(View view){
         radioInput = ((RadioButton) view).getText().toString();
        String textViewField = ((TextView) view).getText().toString();
        if(radioInput.equals("Kilometers to Miles")){
            inputValue.setText("");
            finalResult.setText("");
            inputHeading.setText("Kilometers: ");
            outputHeading.setText("Miles:");
            buttonText.setText("Convert to Miles");
        }else {
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
        Double inputValueInNumber = Double.parseDouble(inputValue.getText().toString());
        Double output;
        if(radioInput.equals("Kilometers to Miles")){
            Log.d(TAG, "Input in Kilometers: "+inputValueInNumber);
            output = (inputValueInNumber)*0.621371;
            output = Math.round(output*10.0)/10.0;
            conversionHistory = System.getProperty("line.separator")+"     "+inputValueInNumber.toString()+"  Km ===>  "+output.toString()+"  Mi"+conversionHistory;
            history.setText(conversionHistory);
            finalResult.setText("  "+output.toString()+"   miles");
        }else {
            Log.d(TAG, "Input in Miles: "+inputValueInNumber);
            output = (inputValueInNumber)*1.60934;
            output = Math.round(output*10.0)/10.0;
            finalResult.setText("  "+output.toString()+"   kilometers");
            conversionHistory =System.getProperty("line.separator")+"     "+inputValueInNumber.toString()+"  Mi  ===>  "+output.toString()+"  Km"+conversionHistory;
            history.setText(conversionHistory);
        }
    }

    public void onClickingClearHistory(View view){
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("ConversionHistory", history.getText().toString());
        outState.putString("ConvertedResult", finalResult.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        history.setText(savedInstanceState.getString("ConversionHistory"));
        finalResult.setText(savedInstanceState.getString("ConvertedResult"));
    }
}