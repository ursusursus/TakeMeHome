package com.ursus.takemehome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ProximityIntentReceiver extends BroadcastReceiver {

	//prijma to ale nevie to rozoznat z ktorej onej to bolo volane..
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Toast.makeText(context, "You are in the location", Toast.LENGTH_SHORT).show();
		
	/*	int areaIndex = intent.getIntExtra("areaIndex", -1);
		if(areaIndex == 0) {
			Log.i("WELCOME", "RECOGNIZED AREA 0");
		}
		if(areaIndex == 1) {
			Log.i("WELCOME", "RECOGNIZED AREA 1");
		}
		if(areaIndex == 2) {
			Log.i("WELCOME", "RECOGNIZED AREA 2");
		}*/
	}

}
