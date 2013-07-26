package client;

import game.Board;
import game.Move;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import searchalgorithm.AlphaBetaAlgorithm;

public class Client {
	
	private static Client instance;
	
	private Client() {
		
	}
	
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		
		return instance;
	}
	
	public void run() {
		Socket MyClient;
		BufferedInputStream input;
		BufferedOutputStream output;
		try {
			MyClient = new Socket("10.196.113.199", 8888);
		   	input    = new BufferedInputStream(MyClient.getInputStream());
			output   = new BufferedOutputStream(MyClient.getOutputStream());
		   	while (true) {
				char cmd = 0;
			   	
	            cmd = (char)input.read();
	            		
	            // Début de la partie en joueur blanc
	            if(cmd == '1'){
	            	Board.getInstance().setIsPlayerWhite(true);
	                byte[] aBuffer = new byte[1024];
					
					int size = input.available();
					//System.out.println("size " + size);
					input.read(aBuffer,0,size);
	                String s = new String(aBuffer).trim();
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                for(int i=0; i<boardValues.length;i++){
	                    Board.getInstance().setTile(x, y, Integer.parseInt(boardValues[i]));
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	                
	                System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : ");
	                Move move = AlphaBetaAlgorithm.getBestMove();
	                String moveString = move.toString();
	                Board.getInstance().makeMove(move);
	                System.out.println(moveString);
					output.write(moveString.getBytes(), 0, moveString.length());
					output.flush();
	            }
	            // Début de la partie en joueur Noir
	            if(cmd == '2'){
	            	Board.getInstance().setIsPlayerWhite(false);
	                System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des blancs");
	                byte[] aBuffer = new byte[1024];
					
					int size = input.available();
					//System.out.println("size " + size);
					input.read(aBuffer,0,size);
	                String s = new String(aBuffer).trim();
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                for(int i=0; i<boardValues.length;i++){
	                	Board.getInstance().setTile(x, y, Integer.parseInt(boardValues[i]));
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	            }


				// Le serveur demande le prochain coup
				// Le message contient aussi le dernier coup joué.
				if(cmd == '3'){
					byte[] aBuffer = new byte[16];
					
					int size = input.available();
					input.read(aBuffer,0,size);
					
					String s = new String(aBuffer);
					System.out.println("Dernier coup : "+ s);
					Board.getInstance().makeMove(new Move(s, !Board.getInstance().isPlayerWhite()));
					
			       	System.out.println("Entrez votre coup : ");
					Move move = AlphaBetaAlgorithm.getBestMove();
					String moveString = move.toString();
	                Board.getInstance().makeMove(move);
	                System.out.println(moveString);
					output.write(moveString.getBytes(), 0, moveString.length());
					output.flush();
					
				}
				// Le dernier coup est invalide
				if(cmd == '4'){
					System.out.println("Coup invalide, entrez un nouveau coup : ");
					Board.getInstance().undoLastMove();
					Move move = AlphaBetaAlgorithm.getBestMove();
					String moveString = move.toString();
	                Board.getInstance().makeMove(move);
	                System.out.println(moveString);
					output.write(moveString.getBytes(), 0, moveString.length());
					output.flush();
					
				}
	        }
		}
		catch (IOException e) {
	   		System.out.println(e);
		}
	}
}
