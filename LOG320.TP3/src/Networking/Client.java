package Networking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import Board.Board;

//http://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html

public class Client {
	private static final Client instance = new Client();
    private final String host = "localhost";
    private final int socket = 8888;
	private Socket client;
	private PrintWriter out = null;
    private BufferedInputStream in = null;
	
    /**
     * Open a connection to the server
     */
	public final void openConnection() {
		try {
			client = new Socket(host, socket);
		   	out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedInputStream(client.getInputStream());
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host + ":" + socket);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host + ":" + socket);
            System.exit(1);
        }
	}
	
	/**
	 * Close the connection to the server
	 */
	public final void closeConnection() {
		try {
			client.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send a move to the server
	 * @param move Example D6D5
	 */
	public final void sendToServer(String move) {
		out.println(move);
	}
	
	/**
	 * Receive a response from the server
	 */
	public final void receiveFromServer() {
		byte[] buffer = new byte[1024];
		char cmd = 0;
		
		try {
			cmd = (char)in.read(buffer, 0, in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (cmd == '1' || cmd == '2') {
			String s = new String(buffer).trim();
	        String[] boardValues;
	        boardValues = s.split(" ");
	        int x=0,y=0;
	        for(int i=0; i<boardValues.length;i++){
	            Board.board[x][y] = Integer.parseInt(boardValues[i]);
	            x++;
	            if(x == 8){
	                x = 0;
	                y++;
	            }
	        }
		} else if (cmd == '3') {
			byte[] aBuffer = new byte[16];
			
			try {
				in.read(aBuffer, 0, in.available());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String s = new String(aBuffer);
			System.out.println("Dernier coup : "+ s);
	       	System.out.println("Entrez votre coup : ");
		} else if (cmd == '4') {
			System.out.println("Coup invalide, entrez un nouveau coup : ");
		} else {
			System.out.println("Code serveur invalide.");
		}
	}
	
	/**
	 * Get the instance of the client
	 * @return Client
	 */
	public static Client getInstance() {
		return instance;
	}
}
