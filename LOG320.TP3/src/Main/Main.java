package Main;

import Board.Board;
import Networking.Client;
import Utilities.commandEnum;

public class Main {

	public static void main(String[] args) {
		Client.getInstance().openConnection();
		Client.getInstance().sendToServer("A2C2");
		Client.getInstance().receiveFromServer();
		Board.getInstance().printBoard();
		Client.getInstance().closeConnection();
	}
}
