package org.usfirst.frc.team2791.robot.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;

/**
 * Socket Switch keys:
 * </br> 0 -- camerStuff
 * </br> 2 -- lambda test
 * </br> 3 -- grayscale
 * </br> 4 -- Image in Memory
 * </br> 5 -- shuts down pi
 * </br> 8 -- image target
 * </br> 9 -- Angle offset from Target
 * </br> 10 -- Height of Target
 * </br> 11 -- Required RPM
 */
public class VisionUtil {

	public double getCameraAngle()
	{
		String  angleDistance = "";
		try {
			System.out.println("*******Trying to turn angle");
			angleDistance = readSocket("10.27.91.9", 9999, "009");
			System.out.println("*******Turned angle  ********** = " + angleDistance );
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Double.parseDouble(angleDistance);
	}	

	public double getRPM()
	{
		String  requiredRPM = "";
		try {
			System.out.println("*******Trying to turn angle");
			requiredRPM = readSocket("10.27.91.9", 9999, "011");
			System.out.println("*******Turned angle  ********** = " + requiredRPM );
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Double.parseDouble(requiredRPM);
	}	
	
	public String readSocket(String ipAddress, int Port, String sentence ) throws IOException
	{
		int timeOut =1500;
		String socketString = "-1";
		//	BufferedReader inFromUser =
		//      new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket;
		
		try {
			clientSocket = new DatagramSocket();
			  clientSocket.setSoTimeout(timeOut);
			
			InetAddress IPAddress = InetAddress.getByName(ipAddress);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			
			//String sentence = inFromUser.readLine();
			System.out.println("sentence = " + sentence);
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Port);

			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("FROM SERVER:" + modifiedSentence);
			socketString = modifiedSentence;
			clientSocket.close();
			System.out.println("done");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket error: " + e.toString());
		}

		return socketString;
	}

}


