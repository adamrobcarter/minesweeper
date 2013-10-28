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
	public Minesweeper game;
	
	public String toString(){
		return "c:" + Integer.toString(this.col) + " r:" + Integer.toString(this.row) + " v:" + Integer.toString(this.value);
	}

	/** @param x the col
	 * @param y the row
	 * @param status the status of the space */
	Space(int x, int y, int value, Minesweeper ms){
		this.value = value;
		this.col = x;
		this.row = y;
		this.game = ms;
	}
	
	void flag(){
		this.flagged = !this.flagged;
		this.game.canvas.repaint();
	}
	
	void uncover(){
		if(!this.open){
			this.game.p("uncovering");
			this.open = true;
			this.game.canvas.repaint();
			this.game.p("uncovering2");
			if(this.value == 9){
				game.lose();
			}
			this.game.p("uncovering3");
			if(this.game.uncoveredRest()){
				this.game.win();
			}
			this.game.p("uncovering4");
			if(this.value == 0){
				this.uncoverSurrounding();
			}
			this.game.p("uncovering5");
		}
	}
	
	public void uncoverSurrounding(){
		try {
			this.game.p("sleeping");
			this.game.canvas.repaint();
			this.game.canvas.serviceRepaints();
			Thread.sleep(7);
			this.game.p("slept");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(this.col > 0){
			this.game.getSpace(this.col-1, this.row).uncover();
		}
		if(this.col > 0 && this.row > 0){
			this.game.getSpace(this.col-1, this.row-1).uncover();
		}
		if(this.col < this.game.length-1){
			this.game.getSpace(this.col+1, this.row).uncover();
		}
		if(this.col < this.game.length-1 && this.row > 0){
			this.game.getSpace(this.col+1, this.row-1).uncover();
		}
		if(this.row > 0){
			this.game.getSpace(this.col, this.row-1).uncover();
		}
		if(this.col > 0 && this.row < this.game.height-1){
			this.game.getSpace(this.col-1, this.row+1).uncover();
		}
		if(this.row < this.game.height-1){
			this.game.getSpace(this.col, this.row+1).uncover();
		}
		if(this.col < this.game.length-1 && this.row < this.game.height-1){
			this.game.getSpace(this.col+1, this.row+1).uncover();
		}
	}
	
	void show(){
		this.open = true;
		this.game.canvas.repaint();
	}
	
	void gohere(){
		this.hover = true;
		this.game.selected = this;
		//Minesweeper.canvas.repaint();
	}
	
	void leavehere(){
		this.hover = false;
		this.game.canvas.repaint();
	}
}
