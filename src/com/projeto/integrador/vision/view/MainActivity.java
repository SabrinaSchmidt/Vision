package com.projeto.integrador.vision.view;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

import com.projeto.integrador.vision.R;

public class MainActivity extends Activity {
	private Button buttonIdentifyMonetaryBallot;
	private Button buttonIdentifyColor;
	MediaPlayer mpRecognitionOfBallots;
	MediaPlayer mpRecognitionOfColors;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initialize(){
		initializeComponenents();
		addEventComponents();
	}
	
	private void initializeComponenents(){
		buttonIdentifyMonetaryBallot = (Button) findViewById(R.id.buttonIdentifyMonetaryBallot);
		buttonIdentifyColor = (Button) findViewById(R.id.buttonIdentifyColor);
		mpRecognitionOfBallots = MediaPlayer.create(MainActivity.this, R.raw.recognition_function_cedulas);
		mpRecognitionOfColors =  MediaPlayer.create(MainActivity.this, R.raw.recognition_function_colors);
		
	}
	
	private void addEventComponents(){
		eventButtonEnterTheBallotsRecognitionFunction();
		eventButtonEnterTheColorsRecognitionFunction();
	}
	
	private void eventButtonEnterTheBallotsRecognitionFunction(){
		buttonIdentifyMonetaryBallot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mpRecognitionOfBallots.start();
			}
		});
		
		buttonIdentifyMonetaryBallot.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				 
				Intent intentShowActivityBallotsRecognition = new Intent(getApplicationContext(), BallotDetectionActivity.class);
				startActivity(intentShowActivityBallotsRecognition);
				return false;
			}
		});
	}
	
	private void eventButtonEnterTheColorsRecognitionFunction(){
		buttonIdentifyColor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mpRecognitionOfColors.start();
				
			}
		});
		
		buttonIdentifyColor.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Intent intentShowActivityColorsRecognition = new Intent(getApplicationContext(), ColorBlobDetectionActivity.class);
				startActivity(intentShowActivityColorsRecognition);
				return false;
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
