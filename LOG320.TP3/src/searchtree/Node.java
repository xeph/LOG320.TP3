package searchtree;

import java.util.ArrayList;
import java.util.Iterator;

public class Node {
	private int atX;
	private int atY;
	private int toX;
	private int toY;
	private int value;
	private ArrayList<Node> children;
	
	public Node(int atX, int atY, int toX, int toY){
		this.atX = atX;
		this.atY = atY;
		this.toX = toX;
		this.toY = toY;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getAtX() {
		return this.atX;
	}
	
	public int getAtY() {
		return this.atY;
	}
	
	public int getToX() {
		return this.toX;
	}
	
	public int getToY() {
		return this.toY;
	}
	
	public Iterator<Node> getChildren() {
		return this.children.iterator();
	}
	
	public void addChild(Node child) {
		this.children.add(child);
	}
}
