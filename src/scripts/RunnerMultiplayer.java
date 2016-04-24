package scripts;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

public class RunnerMultiplayer implements Runnable{

	private String ip = "localhost";
	private int port = 22222;
	private Scanner sc = new Scanner(System.in);
	private JFrame frame;
	
	private Thread thread;
	
	private Socket socket;
	private DataOutputStream dout;
	private DataInputStream din;
	
	private ServerSocket serverSocket;
	
	private boolean accepted = false;
	private boolean unableToConnect = false;
	
	private String waitingString = "Waiting for another player";
	private String unableToConnectMsg = "Unable to connect";
	private String gameOver = "Game over";
	
	private RunnerMultiplayer(){
		if(!connect()) initializeServer();
		thread = new Thread(this, "RunnerMultiplayer");
		thread.start();
	}
	

	private boolean connect() {
		try{
			socket = new Socket(ip, port);
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			accepted = true;
		}catch(IOException e){
			System.out.println("Unable to connect");
			return false;
		}
		
		System.out.println("Successfull connection to server!");
		return true;
	}
	
	private void initializeServer() {
		try{
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void run(){
		while(true){
			if(!accepted) listenForServerRequest();
		}
	}
	
	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	public static void main(String[] args) {
		RunnerMultiplayer rm = new RunnerMultiplayer();
	}

}
