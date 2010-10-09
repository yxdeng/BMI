/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DecimalFormat;

/**
 *
 * @author yxdeng
 */
public class Bmi extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        this.setContentView(R.layout.main);
        findViews();
        setListeners();
    }

    private void findViews() {
        button = (Button) findViewById(R.id.submit);
        fieldheight = (EditText) findViewById(R.id.height);
        fieldweight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        fieldsuggest = (TextView) findViewById(R.id.suggest);
        yourResult = this.getString(R.string.bmi_result);
    }

    private void setListeners() {
        button.setOnClickListener(calcBMI);
    }

    private OnClickListener calcBMI = new OnClickListener() {

        public void onClick(View v) {
            try {
                DecimalFormat nf = new DecimalFormat("0.00");

                double height = Double.parseDouble(fieldheight.getText().toString()) / 100;
                double weight = Double.parseDouble(fieldweight.getText().toString());

                double BMI = weight / (height * height);

                result.setText(yourResult + nf.format(BMI));

                if (BMI > 25) {
                    fieldsuggest.setText(R.string.advice_heavy);
                } else if (BMI < 20) {
                    fieldsuggest.setText(R.string.advice_light);
                } else {
                    fieldsuggest.setText(R.string.advice_average);
                }
            } catch (Exception ex) {
                fieldsuggest.setText(R.string.bmi_error);
            }
        }
    };
    private Button button;
    private EditText fieldheight;
    private EditText fieldweight;
    private TextView result;
    private TextView fieldsuggest;
    private String yourResult;
}
