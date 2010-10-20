/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmi.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 *
 * @author yxdeng
 */
public class Setting extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        this.setContentView(org.bmi.R.layout.settingview);
//        this.addPreferencesFromResource(org.bmi.R.layout.settingview);

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        menu.add(0, Menu.FIRST, 0, "Save");
//        menu.add(0, Menu.FIRST + 1, 0, "Back");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(org.bmi.R.menu.setmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
//            case Menu.FIRST:
//                save();
//            case Menu.FIRST + 1:
//                openMainView();
//                break;
            case org.bmi.R.id.menu_save_id:
                save();
            case org.bmi.R.id.menu_back_id:
                openMainView();
                break;
//            case Menu.FIRST + 2:
//                finish();
//                break;
        }
        return true;
    }

    private void init(){
        settings = this.getSharedPreferences("config", MODE_PRIVATE);
        usernameinput = (EditText) this.findViewById(org.bmi.R.id.usernametext);
        usernameinput.setText(settings.getString("name", ""));

        lang = settings.getString("lang", "");
        standard = settings.getString("standard", "");
        String temp = langitems[0];
        for(int i=0;i<langitems.length;i++){
            if(langitems[i].equals(lang)){
                langitems[0] = lang;
                langitems[i] = temp;
                break;
            }
        }

        temp = standitems[0];
        for(int i=0;i<standitems.length;i++){
            if(standitems[i].equals(standard)){
                standitems[0] = standard;
                standitems[i] = temp;
                break;
            }
        }

//        System.out.println("lang:"+lang  +" standard:"+standard);
        standardspin = (Spinner) findViewById(org.bmi.R.id.standardtext);
        ArrayAdapter<String> standaa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                standitems);
        standaa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
//        standaa.s
        standardspin.setAdapter(standaa);

//        OnItemSelectedListener osl;
        langspin = (Spinner) findViewById(org.bmi.R.id.languagetext);
        ArrayAdapter<String> langaa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                langitems);
        langaa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        langspin.setAdapter(langaa);
    }

    private void save() {
//        usernameinput = (EditText) this.findViewById(org.bmi.R.id.usernametext);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", usernameinput.getText().toString()).commit();
        editor.putString("lang", langspin.getSelectedItem().toString()).commit();
        editor.putString("standard", standardspin.getSelectedItem().toString()).commit();
    }

    private void openMainView() {
        Intent intent = new Intent(this, org.bmi.Bmi.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("lang", lang);
        bundle.putCharSequence("standard", standard);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private EditText usernameinput;
    private SharedPreferences settings;
    private Spinner langspin;
    private Spinner standardspin;
    String[] langitems = {"English", "中文"};
    String[] standitems = {"who","asia","china"};

    private String lang;
    private String standard;
}
