package org.usfirst.frc.team2791.robot.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.net.SocketException;


/**
 * Camera automatically starts on power on; 
 * Socket Switch keys:
 * </br> 0 -- server testing
 * </br> 1 -- vision targeting 
 * </br> 2 -- pishutdown
 * </br> 3 -- stop program
 */
public class ShakerVisionServer {

	private int serverPort = 5800;
	
	/**
	 * data[0] = Camera Success/Failure</br> data[1] = Camera Angle</br> data[2] = Camera Distance
	 */
	protected String data[];
	
	/**
	 * Initializing this server will start the camera and collect all the data
	 */
	public ShakerVisionServer(){
		data = getDataFromSocket().split(":",3);
	}
	
	public void startCamera(){
		String  result = "";
		try {
			System.out.println("*******Starting Camera*******");
			result = readSocket("10.27.91.9", serverPort, "000");
			System.out.println("*******Starting Camera Received********** = " + result );
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * Format of the String that we get from the camera:</br> <blockquote>#:Angle:Distance</blockquote>
	 * # = 1 means camera connection success / # = 0 means camera connection failure
	 */
	public String getDataFromSocket(){
		String  result = "";
		try {
			System.out.println("*******Getting Data*******");
			result = readSocket("10.27.91.9", serverPort, "001");
			System.out.println("*******Getting Data Result********** = " + result );
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}	
	
	public double getCameraAngle(){
		return (double)(Double.parseDouble(data[1]));
	}	
	
	public double getCameraDistance(){
		return (double)(Double.parseDouble(data[2]));
	}	

	/**
	 * @return 1 == success / 2 == failure
	 */
	public double getCameraSuccessFailure(){
		return (double)(Double.parseDouble(data[0]));
	}
	
	public String getTest(){
		String  result = "";
		try {
			System.out.println("*******Testing*******");
			result = readSocket("10.27.91.9", serverPort, "003");
			System.out.println("*******Testing Result********** = " + result );
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}	

	/**
	 * @param ipAddress '10.te.am.9', 9 being the port we selected for the Pi's ip
	 * @param port a 4 digit port for the Pi socket
	 * @param sentence the data to send, see class description for more
	 * @return the data received from the socket, returns -1 if there is a socket failure
	 * @throws IOException likley to be due to a socket timeout
	 */
	public String readSocket(String ipAddress, int port, String sentence) throws IOException
	{
		int timeOut = 15000; //client socket times out after 15 seconds
		String socketString = "-1";

		DatagramSocket clientSocket;

		try {
			clientSocket = new DatagramSocket();
			clientSocket.setSoTimeout(timeOut);

			
			InetAddress IPAddress = InetAddress.getByName(ipAddress);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];

			System.out.println("TO SERVER:" + sentence); 
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);

			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("FROM SERVER:" + modifiedSentence);
			socketString = modifiedSentence;
			clientSocket.close();
			
			if(socketString.substring(0,1).equals("0")){
				System.out.println("Socket Returned '0', throwing Exception");
				throw new IOException();
			}
			
			System.out.println("Socket is done");
		} catch (IOException e) {
			System.out.println("Socket error: " + e.toString());
		}
		
		return socketString;
	}

}
