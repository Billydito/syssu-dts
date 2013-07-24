package br.ufc.great.syssu.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

public class TCPNetworkClient {

	private String address;
	private int port;

	public TCPNetworkClient(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public String sendMessage(String message) throws IOException {
		String result;
		try {
			// The method execute returns the AynscTask itself, so we need to call get(). 
			// This turned async task into a sync one, as get() waits if needed for the result to be avilable.
			result = new Send().execute(message, this.address, String.valueOf(this.port)).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			result = "Can't connect";
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			result = "Can't connect";
			e.printStackTrace();
		}
		return result;
	}

	// Applications targeting the Honeycomb SDK or higher throws NetworkOnMainThreadException 
	// when an application attempts to perform a networking operation on its main thread.
	private class Send extends AsyncTask<String, Integer, String> {
		
		private static final String CHAR_SET = "UTF8";
		private static final int END_OF_TRANSMITION = 4;

		@Override
		protected String doInBackground(String... message) {
			String result;
			Socket socket = null;
			Writer writer = null;
			InputStream is = null;
			try {
				socket = new Socket();
				SocketAddress sockaddr = new InetSocketAddress(message[1], Integer.parseInt(message[2]));
				socket.connect(sockaddr, 3000);

				is = socket.getInputStream();
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHAR_SET));
				writer.write(message[0]);
				writer.write(END_OF_TRANSMITION);
				writer.flush();
				result = convertStreamToString(is);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				result = "Can't connect";
				e.printStackTrace();
			} finally {
				try {
					socket.close();	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		private String convertStreamToString(InputStream is) throws IOException {
			String result = null;
			if (is != null) {
				Writer writer = new StringWriter();
				Reader reader = new InputStreamReader(is, "UTF8");
				int c;
				while ((c = reader.read()) != END_OF_TRANSMITION) {
					writer.append((char) c);
				}
				result = writer.toString();
			}
			return result;
		}
	}
}


