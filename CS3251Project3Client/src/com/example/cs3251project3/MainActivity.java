package com.example.cs3251project3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	private static final String SERVERIP = "130.207.114.21";
	private static final int SERVERPORT = 1111;

	private Socket socket;
	private String message = "Hello World";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Thread(new ClientThread()).start();
	}
	
	/** Called when the user clicks the Send button */
	public void sendMessage(View view){
		try{
			//outgoing stream redirect to socket
			PrintWriter output = new PrintWriter(socket.getOutputStream());
			//receive message server is sending back
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Send message from client to server
			output.write(message);
			output.flush();
			Log.i("TCPClient", "sent: " + message);
			
			//read line(s) from server
			String st = input.readLine();
			Log.i("TCPClient", "received:  " + st);
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
//	public void sendMessage(View view) {
//	    
//		Intent intent = new Intent(this, DisplayMessageActivity.class);
//	    EditText editText = (EditText) findViewById(R.id.edit_message);
//	    String message = editText.getText().toString();
//	    
//		try {
//			Socket s = new Socket(SERVERIP, 1111);
//			
//			//outgoing stream redirect to socket
//			PrintWriter output = new PrintWriter(s.getOutputStream());
//			//receive message server is sending back
//			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
//			
//			//Send message from client to server
//			output.write(message);
//			output.flush();
//			Log.i("TCPClient", "sent: " + message);
//			
//			//read line(s) from server
//			String st = input.readLine();
//			Log.i("TCPClient", "received:  " + st);
//			
//			//Close connection
//			s.close();
//		} catch (UnknownHostException e){
//			e.printStackTrace();
//		} catch (IOException e){
//			e.printStackTrace();
//		}
//		
//
////	    intent.putExtra(EXTRA_MESSAGE, message);
////	    startActivity(intent);
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class ClientThread implements Runnable{
		
		@Override
		public void run() {
			try{
				InetAddress serverAddress = InetAddress.getByName(SERVERIP);
				socket = new Socket(serverAddress, SERVERPORT);
			} catch (UnknownHostException e1){
				e1.printStackTrace();
			} catch (IOException e1){
				e1.printStackTrace();
			}
		}
	}

}
