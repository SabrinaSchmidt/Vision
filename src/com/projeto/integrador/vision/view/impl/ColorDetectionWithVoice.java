package com.projeto.integrador.vision.view.impl;

import com.projeto.integrador.vision.R;

import android.content.Context;
import android.media.MediaPlayer;

public class ColorDetectionWithVoice {
	
	MediaPlayer mpAudio;

	public void identifyColors(double red, double green, double blue , Context context){
		
		if(red <= 100 && green <= 120 && blue >= 160){
			mpAudio = MediaPlayer.create(context, R.raw.color_blue);
			mpAudio.start();
		}
		
		if(red <= 32 && green <= 32 && blue <= 32 ){
			mpAudio = MediaPlayer.create(context, R.raw.color_black);
			mpAudio.start();
		}	
		if(red >= 220 && green >= 220 && blue >= 220 ){
			mpAudio = MediaPlayer.create(context, R.raw.color_white);
			mpAudio.start();
		}	
		if(red >= 120 && green >= 120 && blue <=50){
			mpAudio = MediaPlayer.create(context, R.raw.color_yellow);
			mpAudio.start();
		}
		if(red <= 100 && green <= 120 && blue >= 160){
			mpAudio = MediaPlayer.create(context, R.raw.color_blue);
			mpAudio.start();
		}
			
		
	}
	
}
