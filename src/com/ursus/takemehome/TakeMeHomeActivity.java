package com.ursus.takemehome;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TakeMeHomeActivity extends Activity {
	private TextView tv;
	private File file;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
		 * SQLiteDatabase db = helper.getReadableDatabase(); //String output =
		 * helper.select(db);
		 */
		
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		final LocationHelper locationHelper = new LocationHelper(locationManager, this, tv);

		tv = (TextView) findViewById(R.id.textView1);
		tv.setText("lalala");
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				locationHelper.checkLocation();
			}
		});
		
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				locationHelper.addProximityAlert();
			}
		});
		
		
		// exportToSDCard();
		downloadDatabase();
		
		//File path = getExternalFilesDir(null);
		//File dbFile = new File(path, "data.db");

		//Log.i("PATH 1", dbFile.toString());
		//Log.i("PATH 1", file.toString());

		// SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile,
		// null);
		SQLiteDatabase db = SQLiteDatabase.openDatabase(file.toString(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);

		Log.i("PATH 2", db.getPath());

		if (db != null && db.isOpen()) {
			Log.i("DB", "SUCCESS");
		} else {
			Log.i("DB", "FAIL");
		}
		
		//Cursor c = db.query("stops", new String[] {"bus","stop"}, null, null, null, null, null, "5");
		//Cursor c = db.rawQuery("select bus,stop,hour,minute from times where bus like '8' and stop like 'Sibírska' and hour like '7'",null);
		Cursor c = db.rawQuery("select question_id from questions",null);
		c.moveToFirst();
		StringBuilder sb = new StringBuilder();
		if (!(c.isAfterLast())) {
			do {
				sb.append(c.getString(0));				
				sb.append("\n");
			} while (c.moveToNext());
		}
		c.close();
		
		
		//String currentDateTimeString = new SimpleDateFormat("HH").format(new Date());
		//tv.setText(sb.toString());
		//tv.setText(currentDateTimeString);
		//downloadDatabase();
	}

	private void exportToSDCard() {

		if (isSDCardAvailable()) {

			File path = getExternalFilesDir(null);
			File dbFile = new File(path, "database");

			// tv.setText(path.toString());
			try {
				// Open your local db as the input stream
				InputStream myInput = getApplicationContext().getAssets().open("data.po.db");

				// FileOutputStream myOutput =
				// openOutputStream(dbFile.toString();
				FileOutputStream myOutput = new FileOutputStream(dbFile);

				// transfer bytes from the inputfile to the outputfile
				byte[] buffer = new byte[1024];
				int length;
				while ((length = myInput.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}

				// Close the streams
				myOutput.flush();
				myOutput.close();
				myInput.close();
			} catch (Exception e) {
				Log.e("TROLOLOLO", e.getMessage());
			}
			Log.i("DATABASE", "SUCCESS");

			// SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile,
			// null);
		}
	}

	private boolean isSDCardAvailable() {
		boolean isAvailable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Log.i("MEDIA", "MOUNTED!");

			isAvailable = true;

		} else {
			Log.i("MEDIA", "FAIL");
			Toast.makeText(this, "SD-Card not available", Toast.LENGTH_SHORT).show();
			isAvailable = false;

		}
		return isAvailable;

	}

	private void downloadDatabase() {
		try {
			// set the download URL, a url that points to a file on the internet
			// this is the file to be downloaded
			//URL url = new URL("http://vlastimil.brecka.student.cnl.sk/uploads/data.db");
			URL url = new URL("http://vlastimil.brecka.student.cnl.sk/upload/uploads/pixel_master_database.sql");

			// create the new connection
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			// set up some things on the connection
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			// and connect!
			urlConnection.connect();

			// set the path where we want to save the file
			// in this case, going to save it on the root directory of the
			// sd card.
			//File SDCardRoot = Environment.getExternalStorageDirectory();
			File SDCardRoot = getExternalFilesDir(null);
			// create a new file, specifying the path, and the filename
			// which we want to save the file as.
			//file = new File(SDCardRoot, "data.db");
			file = new File(SDCardRoot, "pixel_master_database.sql");

			// this will be used to write the downloaded data into the file we
			// created
			FileOutputStream fileOutput = new FileOutputStream(file);

			// this will be used in reading the data from the internet
			InputStream inputStream = urlConnection.getInputStream();

			// this is the total size of the file
			int totalSize = urlConnection.getContentLength();
			// variable to store total downloaded bytes
			int downloadedSize = 0;

			// create a buffer...
			byte[] buffer = new byte[1024];
			int bufferLength = 0; // used to store a temporary size of the
									// buffer

			// now, read through the input buffer and write the contents to the
			// file

			while ((bufferLength = inputStream.read(buffer)) > 0) {
				// add the data in the buffer to the file in the file output
				// stream (the file on the sd card
				fileOutput.write(buffer, 0, bufferLength);
				// add up the size so we know how much is downloaded
				downloadedSize += bufferLength;
				// this is where you would do something to report the prgress,
				// like this maybe
				// updateProgress(downloadedSize, totalSize);

			}

			// close the output stream when done
			Log.i("DOWNLOAD","FINISHED!");
			fileOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}