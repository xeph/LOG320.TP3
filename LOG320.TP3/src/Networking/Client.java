package Networking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Send a move to the server
	 * @param move Example D6D5
	 */
	public final void sendToServer(String move) {
		out.println(move);
		out.flush();
	}
	
	/**
	 * Receive a response from the server
	 */
	public final void receiveFromServer() {
		byte[] buffer = new byte[1024];
		char cmd = 0;
		
		try {
			in.read(buffer, 0, in.available()); // read from the socket
			cmd = (char) buffer[0]; // extract the command
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		//System.out.println("Server code : " + cmd);
		
		if (cmd == '1' || cmd == '2') { // 1 = black players, 2 = white players
			
			if (cmd == '1')
				System.out.println("Black player turn");
			if (cmd == '2')
				System.out.println("White player turn.");
			
			String s = ""; // convert ascii to string
			try {
				s = new String(buffer, "ASCII");
			} catch (UnsupportedEncodingException e) {
				System.err.println(e.getMessage());
			}
			
			s = s.substring(2, s.length()); // remove command and space from string
	        String[] boardValues = s.split(" "); // construct board values
	        int x = 0;
	        int y = 0;
	        for(int i = 0; i<boardValues.length;i++){ // get the new board in memory
	        	Board.getInstance().board[x][y] = Integer.parseInt(boardValues[i]);
	            x++;
	            if(x == 8){
	                x = 0;
	                y++;
	                if (y == 8)
	                	break;
	            }
	        }
		} else if (cmd == '3') { // last movement by opponent
			byte[] aBuffer = new byte[16];
			
			try {
				in.read(aBuffer, 0, in.available());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			
			String s = "";
			try {
				s = new String(buffer, "ASCII");
			} catch (UnsupportedEncodingException e) {
				System.err.println(e.getMessage());
			}
			System.out.println("Dernier coup : "+ s);
	       	System.out.println("Entrez votre coup : ");
		} else if (cmd == '4') { // invalid move
			System.out.println("Coup invalide, entrez un nouveau coup : ");
		} else { // invalid command
			System.err.println("Invalid server code.");
		}
		out.flush();
	}
	
	/**
	 * Get the instance of the client
	 * @return Client
	 */
	public static Client getInstance() {
		return instance;
	}
}
