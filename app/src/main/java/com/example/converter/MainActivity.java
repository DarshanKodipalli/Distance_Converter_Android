package com.example.converter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private TextView history;
    private String conversionHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputHeading = findViewById(R.id.conversionInput);
        outputHeading = findViewById(R.id.convertedResult);
        buttonText = findViewById(R.id.convert);
        finalResult = findViewById(R.id.result);
        inputValue = findViewById(R.id.inputValue);
        radioInp = findViewById(R.id.mitokms);
        history = findViewById(R.id.conversionHistory);
        conversionHistory = "";
    }

    public void onClickingRadioButton(View view){
        radioInput = ((RadioButton) view).getText().toString();
        String textViewField = ((TextView) view).getText().toString();
        if(radioInput.equals("Kilometers to Miles")){
            inputValue.setText("");
            finalResult.setText("");
            inputHeading.setText("Kilometers: ");
            outputHeading.setText("~ Miles:");
            buttonText.setText("Convert to Miles");
        }else {
            inputValue.setText("");
            finalResult.setText("");
            inputHeading.setText("Miles: ");
            outputHeading.setText("~ Kilometers : ");
            buttonText.setText("Convert to Kilometers");
        }
    }

    public void onConvertOption(View view){
        Log.d(TAG, "Input Value: "+inputValue.getText());
        Double inputValueInNumber = Double.parseDouble(inputValue.getText().toString());
        Double output;
        if(radioInput.equals("Kilometers to Miles")){
            Log.d(TAG, "Input in Kilometers: "+inputValueInNumber);
            output = (inputValueInNumber)*0.621371;
            output = Math.round(output*10.0)/10.0;
            conversionHistory = conversionHistory+ System.getProperty("line.separator")+"     "+inputValueInNumber.toString()+"  kms ===>  "+output.toString()+"  mis";
            history.setText(conversionHistory);
            finalResult.setText("  "+output.toString()+"   miles");
        }else {
            Log.d(TAG, "Input in Miles: "+inputValueInNumber);
            output = (inputValueInNumber)*1.60934;
            output = Math.round(output*10.0)/10.0;
            finalResult.setText("  "+output.toString()+"   kilometers");
            conversionHistory =conversionHistory+ System.getProperty("line.separator")+"     "+inputValueInNumber.toString()+"  mis  ===>  "+output.toString()+"  kms";
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
