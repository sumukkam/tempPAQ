package com.example.k3vn19.paq.screen.more.screens.bluetoothFinal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.k3vn19.paq.R;
import com.example.k3vn19.paq.screen.main.controller.MainActivity;

public class MainActivityBluetooth extends Activity  {
	private Button buttonScan;
	private Button buttonSerialSend;
	private Button buttonNewActivity;
	private EditText serialSendText;
	private TextView serialReceivedText;

	BlunoLibrary bluno;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("*%^&%^*onCreate");



		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_bluetooth);

		bluno = new BlunoLibrary(MainActivityBluetooth.this) {
			@Override
			public void onConectionStateChange(connectionStateEnum theConnectionState) {
				System.out.println("*%^&%^*onConnectionStateChange");

				switch (theConnectionState) {											//Four connection state
					case isConnected:
						buttonScan.setText("Connected");
						break;
					case isConnecting:
						buttonScan.setText("Connecting");
						break;
					case isToScan:
						buttonScan.setText("Scan");
						break;
					case isScanning:
						buttonScan.setText("Scanning");
						break;
					case isDisconnecting:
						buttonScan.setText("isDisconnecting");
						break;
					default:
						break;
				}
			}

			@Override
			public void onSerialReceived(String theString) {
				serialReceivedText.append(theString);
				((ScrollView)serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
			}
		};

		bluno.onCreateProcess();
		bluno.serialBegin(115200);

		serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);
		serialSendText=(EditText) findViewById(R.id.serialSendText);

		buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend);		//initial the button for sending the data
		buttonSerialSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bluno.serialSend(serialSendText.getText().toString());				//send the data to the BLUNO
			}
		});

		buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
		buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bluno.buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});

		buttonNewActivity = (Button) findViewById(R.id.newActivity);					//initial the button for scanning the BLE device
		buttonNewActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newActivity = new Intent(getApplicationContext(), MainActivityBluetooth.class);
				startActivity(newActivity);
			}
		});

		/*
        onCreateProcess();														//onCreate Process by BlunoLibrary
        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data
        serialSendText=(EditText) findViewById(R.id.serialSendText);			//initial the EditText of the sending data

        buttonSerialSend = (Button) findViewById(R.id.buttonSerialSend);		//initial the button for sending the data
        buttonSerialSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				serialSend(serialSendText.getText().toString());				//send the data to the BLUNO
			}
		});

        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});

		buttonNewActivity = (Button) findViewById(R.id.newActivity);					//initial the button for scanning the BLE device
		buttonNewActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newActivity = new Intent(getApplicationContext(), NewActivity.class);
				startActivity(newActivity);
			}
		});
		*/
	}

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("*%^&%^*onActivityResult");

		onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}
	*/


	/*
    @Override
    protected void onPause() {
		System.out.println("*%^&%^*onPause");

		super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }
	
	protected void onStop() {
		System.out.println("*%^&%^*onStop");

		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
	}
	*/


	@Override
	public void onBackPressed() {
		System.out.println("*%^&%^*onResume");
		super.moveTaskToBack(true);
	}



	// TODO: keep these
	protected void onResume() {
		System.out.println("*%^&%^*onResume");

		super.onResume();
		System.out.println("BlUNOActivity onResume");
		bluno.onResumeProcess();														//onResume Process by BlunoLibrary
	}

	@Override
    protected void onDestroy() {
		System.out.println("*%^&%^*onDestroy");

		super.onDestroy();
        bluno.onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

	@Override
	protected void onPause() {
		System.out.println("*%^&%^*onPause");

		super.onPause();
		//bluno.onDestroyProcess();														//onDestroy Process by BlunoLibrary
	}



    /*
	@Override
	public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		System.out.println("*%^&%^*onConnectionStateChange");

		switch (theConnectionState) {											//Four connection state
		case isConnected:
			buttonScan.setText("Connected");
			break;
		case isConnecting:
			buttonScan.setText("Connecting");
			break;
		case isToScan:
			buttonScan.setText("Scan");
			break;
		case isScanning:
			buttonScan.setText("Scanning");
			break;
		case isDisconnecting:
			buttonScan.setText("isDisconnecting");
			break;
		default:
			break;
		}
	}

	// TODO Auto-generated method stub
	@Override
	public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
		serialReceivedText.append(theString);							//append the text into the EditText
		//The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
		((ScrollView)serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
	}
	*/

}
