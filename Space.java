package minesweeper;

/** Class for all spaces in the playing area
 * @author Adam */
public class Space {

	/** Status code none=0, 9=mine, else=no. surrounding mines */
	public int value;
	public boolean open = false;
	public boolean hover = false;
	public int col;
	public int row;
	public boolean flagged = false;
	
	public String toString(){
		return "c:" + Integer.toString(this.col) + " r:" + Integer.toString(this.row) + " v:" + Integer.toString(this.value);
	}

	/** @param x the col
	 * @param y the row
	 * @param status the status of the space */
	Space(int x, int y, int value){
		this.value = value;
		this.col = x;
		this.row = y;
	}
	
	void uncover(){
		this.open = true;
		Minesweeper.canvas.repaint();
		if(this.value == 9){
			Minesweeper.lost();
			Minesweeper.p("ho");
		}
		if(Minesweeper.uncoveredRest()){
			Minesweeper.win();
		}
		if(this.value == 0){
			Minesweeper.uncoverSurrounding(this);
		}
	}
	
	void show(){
		this.open = true;
		Minesweeper.canvas.repaint();
	}
	
	void gohere(){
		this.hover = true;
		Minesweeper.selected = this;
		//Minesweeper.canvas.repaint();
	}
	
	void leavehere(){
		this.hover = false;
		Minesweeper.canvas.repaint();
	}
	
	void print(){
		//System.out.println("val:"+Integer.toString(this.value)+" r:"+Integer.toString(this.row)+" c:"+Integer.toString(this.col));
	}
}
