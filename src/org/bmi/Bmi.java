/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import db.DbHelper;
import db.Person;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author yxdeng
 */
public class Bmi extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.main);
        findViews();
        setListeners();
        db = new DbHelper(this);
        settings = this.getSharedPreferences("config", MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bmimenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_set_id:
                openSettingView();
                break;
            case R.id.menu_quit_id:
                finish();
                break;
            case R.id.menu_history_id:
                showHistoryView();
                break;
//                openMyTest();
//                break;
        }
        return true;
    }

    private void openSettingView() {
        Intent intent = new Intent(this, org.bmi.view.Setting.class);
        startActivity(intent);
    }

    private void showHistoryView() {
        this.setContentView(R.layout.historyview);
        his_username = (TextView) this.findViewById(R.id.history_username);
        his_content = (TextView) this.findViewById(R.id.history_content);

        String username = settings.getString("name", "");
//        Person p = db.getResult(username);
        List pl = db.getAllUserResult(username);
        his_username.setText(his_username.getText() + username);
        DecimalFormat nf = new DecimalFormat("0.00");
        if (pl.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0 ; i<pl.size();i++){
                Person p = (Person)pl.get(i);
                sb.append(p.getId() + " ");
                sb.append(p.getUsername() + " ");
                sb.append(p.getTime()+ " ");
                sb.append(nf.format(p.getResult())+"\n");
            }
            his_content.setText(sb);
        }
    }

    private void findViews() {
        button = (Button) findViewById(R.id.submit);
        fieldheight = (EditText) findViewById(R.id.height);
        fieldweight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        fieldsuggest = (TextView) findViewById(R.id.suggest);
        yourResult = this.getString(R.string.bmi_result);

    }

    private Person uiToPerson(double res) {
        Person p = new Person();
//        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        p.setTime(cal.get(Calendar.YEAR) 
                + "-" + (cal.get(Calendar.MONTH)+1)
                + "-" + cal.get(Calendar.DATE)
                + " " + cal.getTime().getHours()
                + ":" + cal.getTime().getMinutes());
        p.setUsername(settings.getString("name", ""));
        p.setResult(res);
        return p;
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

                db.addResult(uiToPerson(BMI));
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
    private String standard;
    private DbHelper db;
    private TextView his_username;
    private TextView his_content;
    private SharedPreferences settings;
//    private Bundle bundle;
}
