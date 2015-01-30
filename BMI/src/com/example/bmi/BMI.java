package com.example.bmi;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMI extends ActionBarActivity {
	private static final String TAG = "BMI";
	public static final String PREF = "BMI_PREF";
	public static final String PREF_HEIGHT = "BMI_Height";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG,"onCreate");
		setContentView(R.layout.activity_bmi);
		findViews();
		restorePrefs();
		setListeners();
	}
	
	// Restore preferences
	private void restorePrefs()
	{
	  SharedPreferences settings = getSharedPreferences(PREF,0);
	  String pref_height = settings.getString(PREF_HEIGHT, "");
	  if(! "".equals(pref_height))
	  {
		  field_height.setText(pref_height);
		  field_weight.requestFocus();
	  }
	}
	
	public void onStart()
    {
        super.onStart();
        Log.v(TAG,"onStart");
    }
	
	public void onResume()
    {
        super.onResume();
        Log.v(TAG,"onResume");
    }
    
	@Override
    public void onPause()
    {
        super.onPause();
     // Save user preferences. use Editor object to make changes.
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        settings.edit()
          .putString(PREF_HEIGHT, field_height.getText().toString())
          .commit();
        Log.v(TAG,"onPause");
    }
    
    public void onStop()
    {
        super.onStop();
        Log.v(TAG,"onStop");
    }    
    
    public void onRestart()
    {
        super.onRestart();
        Log.v(TAG,"onReStart");
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG,"onDestroy");
    }

	private Button calcbutton;
	private Button clear;
	private EditText field_height;
	private EditText field_weight;
	private TextView view_result;
	private TextView view_suggest;


	private void findViews() {
		calcbutton = (Button) findViewById(R.id.submit);
		clear = (Button) findViewById(R.id.clear);
		field_height = (EditText) findViewById(R.id.height);
		field_weight = (EditText) findViewById(R.id.weight);
		view_result = (TextView) findViewById(R.id.result);
		view_suggest = (TextView) findViewById(R.id.suggest);
	}

	// Listen for button clicks
	private void setListeners() {
		calcbutton.setOnClickListener(calcBMI);
		clear.setOnClickListener(claerBMI);
	}

	private Button.OnClickListener calcBMI = new Button.OnClickListener() {
		public void onClick(View v) {
			
			if ("".equals(field_height.getText().toString()) || "".equals(field_weight.getText().toString())) {
				Toast popup = Toast.makeText(BMI.this, R.string.input_null, Toast.LENGTH_SHORT);
				popup.show();
			}else {
				//Switch to report page
				Intent intent = new Intent();
				intent.setClass(BMI.this, Report.class);
				Bundle bundle = new Bundle();
				bundle.putString("KEY_HEIGHT", field_height.getText().toString());
				bundle.putString("KEY_WEIGHT", field_weight.getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
			
			/*
			DecimalFormat nf = new DecimalFormat("0.00");
			
			if ("".equals(field_height.getText().toString()) || "".equals(field_weight.getText().toString())) {
				Toast popup = Toast.makeText(BMI.this, R.string.input_null, Toast.LENGTH_SHORT);
				popup.show();
			} else {
				double height = Double.parseDouble(field_height.getText().toString()) / 100;
				double weight = Double.parseDouble(field_weight.getText().toString());
				double BMI = weight / (height * height);

				view_result.setText(getText(R.string.bmi_result) + nf.format(BMI));

				// Give health advice
				if (BMI > 25) {
					view_suggest.setText(R.string.advice_heavy);
				} else if (BMI < 20) {
					view_suggest.setText(R.string.advice_light);
				} else {
					view_suggest.setText(R.string.advice_average);
				}
				openOptionsDialog();
			}
			*/
		}
	};
	
	
	private Button.OnClickListener claerBMI = new Button.OnClickListener(){
		public void onClick(View v) {
			field_height.setText("");
			field_weight.setText("");
			view_result.setText("");
			view_suggest.setText("");
		}
	};


	protected static final int MENU_ABOUT = Menu.FIRST;
	protected static final int MENU_Quit = Menu.FIRST+1;
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ABOUT, 0, "關於....");
		menu.add(0, MENU_Quit, 0, "結束");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		   case MENU_ABOUT:
			   openOptionsDialog();
			   break;
		   case MENU_Quit:
			   finish();
			   break;
		}
			return true;
	}
	
	private void openOptionsDialog() {

		//Toast popup = Toast.makeText(BMI.this, "BMI 計算器", Toast.LENGTH_SHORT);
		//popup.show();
		
		 AlertDialog.Builder dialog = new AlertDialog.Builder(BMI.this);
		 dialog.setTitle(R.string.about_title);
		 dialog.setMessage(R.string.about_msg);
		 //按鈕
		 dialog.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() { 
	        public void onClick(DialogInterface dialog, int which) { } }); 
		 //上網按鈕
		 dialog.setNegativeButton(R.string.homepage_label, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//go to urI
				Uri uri = Uri.parse(getString(R.string.homepage_uri));
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		 dialog.show();
		 
	}
	
	

}
