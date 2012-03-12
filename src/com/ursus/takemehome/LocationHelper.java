package com.ursus.takemehome;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class LocationHelper implements LocationListener {
	
	private static final String PROX_ALERT_INTENT = "com.ursus.takemehome.ProximityAlert";
	private LocationManager manager;
	private double latitude;
	private double longitude;
	private Context context;
	private int index;
	private TextView textView;

	//WORKS HAHAHA !! yea madafaka, ale udrziavam iba jednu hodnotu...
	public LocationHelper(LocationManager manager, Context context, TextView textView) {
		
		this.textView = textView;
		this.index = 0;
		
		this.context = context;
		this.manager = manager;
	}
	 
	public void checkLocation() {
		
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		//manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		//neni dobre lebo to nestihne ako keby, onClick prida sa, onPause bude odobraty listener
		//manager.removeUpdates(this);
	}
	
	//ulozi current poziciu ako proximity alert
	public void addProximityAlert() {
		
		Intent intent = new Intent(PROX_ALERT_INTENT);
		intent.putExtra("areaIndex", index++);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(context, 0, intent, Intent.FILL_IN_DATA);

		//manager.addProximityAlert(latitude, longitude, 500, -1, proximityIntent);
		manager.addProximityAlert(latitude, longitude, 2, -1, proximityIntent);
		
		IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT); 
		context.registerReceiver(new ProximityIntentReceiver(), filter);
		
		Log.i("COORDINATES","Area " + String.valueOf(index - 1) + " " + String.valueOf(latitude) + "-" + String.valueOf(longitude) + "saved");
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i("gps", "method gets called");
		if (location != null) {
			this.latitude = location.getLatitude();
			this.longitude = location.getLongitude();
			
			//textView.setText(String.valueOf(latitude) + " " + String.valueOf(longitude));
			Toast.makeText(context, "PICA" , Toast.LENGTH_SHORT).show();
			//textView.setText("PICA");
			
			Log.i("COORDINATES", String.valueOf(latitude) + " " + String.valueOf(longitude));
		} else {
			
			textView.setText("null");
			Log.i("LOCATION","IS NULL");
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
			
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
