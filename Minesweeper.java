package minesweeper;

import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class Minesweeper extends MIDlet implements CommandListener {
	public int height = 10;
	public int length = 10;
	public Canvas canvas;
	public Space[][] spaces;
	public Space selected;
	public static Command exitCommand = new Command("Exit", Command.EXIT, 0);
	public Command okCommand = new Command("Uncover", Command.OK, 1);
	public Command newCommand = new Command("New", Command.HELP, 2);
	public static Command flagCommand = new Command("Flag", Command.CANCEL, 2);
	public String error = "";
	public String message = "";
	public CommandListener thi = null;
	/** state of game: true: playing, false: game ended */
	public boolean playing;
	public int[][] mines;
	public int numMines;
	public Display display;

	public void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	public void pauseApp() {
	}

	public void startApp() throws MIDletStateChangeException {
		try {
			this.newGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commandAction(Command c, Displayable d) {
		try {
			if(c == exitCommand){
				this.exitMIDlet();
			} else if(c == okCommand){
				this.selected.uncover();
			} else if(c == newCommand){
				newGame();
			} else if(c == flagCommand){
				this.selected.flag();
			}
		} catch (Exception e) {
			this.p("hi");
			e.printStackTrace();
		}
	}

	/** Get space based on row and col
	 * @param col col to get
	 * @param row row to get
	 * @return Space instace of result */
	public Space getSpace(int col, int row){
		return spaces[col][row];
	}

	public void uncoverMines(){
		this.display.vibrate(10000);
		this.p("3.0");
		Space[] one = {this.selected};
		
		Space[][] toExplode = new Space[this.height+1][this.height*2];
		toExplode[1] = one;
		int tElen = 0;

		this.p("3.1");
		for(int i=2; i<this.height+1; i++){
			p("her");
			int range[][] = generateRanges(i, this.selected.col, this.selected.row);
			p(i);
			p("3.1."+Integer.toString(i));
			p(range.length);
			p("swq");
			int tE = 0;
			for(int t=0; t<range.length; t++){
				if(spaceExists(range[t][0], range[t][1])){
					Space s = getSpace(range[t][0], range[t][1]);
					toExplode[i][tE] = s;
					tE++;
				}
			}
		}
		this.p("3.2");
		
		for(int i=0; i<toExplode.length; i++){
			for(int s=0; s<toExplode[i].length; s++){
				if(toExplode[i][s] != null){
					toExplode[i][s].explode();
				}
			}
			try {
				this.canvas.repaint();
				this.canvas.serviceRepaints();
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.p("3.3");
		
		for(int i=0; i<toExplode.length; i++){
			for(int s=0; s<toExplode[i].length; s++){
				if(toExplode[i][s] != null){
					toExplode[i][s].stopExplode();
				}
			}
			try {
				this.canvas.repaint();
				this.canvas.serviceRepaints();
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.p("3.4");
		this.display.vibrate(0);
	}
	
	public int[][] generateRanges(int distance, int col, int row){
		p("r");
		int len = 8*(distance-1)+4;
		int[][] range = new int[len][2];
		int[][] relRange = new int[len][2];
		
		int dist = (distance*2)-1;
		int nstart = (distance-1)*-1;
		int pstart = distance-1;
		
		int c=0;
		
		for(int i=0; i<dist; i++){ //top
			range[c][0] = i+nstart;
			range[c][1] = pstart;
			c++;
		}
		for(int i=0; i<dist; i++){ //bottom
			range[c][0] = i+nstart;
			range[c][1] = nstart;
			c++;
		}
		for(int i=0; i<dist; i++){ //left
			range[c][0] = nstart;
			range[c][1] = i+nstart;
			c++;
		}
		for(int i=0; i<dist; i++){ //right
			range[c][0] = pstart;
			range[c][1] = i+nstart;
			c++;
		}
		
		for(int i=0; i<c; i++){
			relRange[i][0] = range[i][0] + col;
			relRange[i][1] = range[i][1] + row;
		}
		
		p("t");
		p(c);
		p(relRange.length);
		
		return relRange;
	}
	
	public boolean spaceExists(int col, int row){
		if(col >= 0 && col<this.length && row >= 0 && row<this.height){
			return true;
		} else {
			return false;
		}
	}

	public boolean uncoveredRest(){
		for(int col=0; col<spaces.length; col++){
			for(int row=0; row<spaces[col].length; row++){
				if(spaces[col][row].value != 9 && spaces[col][row].open == false){
					return false;
				}
			}
		}
		return true;
	}

	public void lose(){
		this.uncoverMines();
		message = "You Lose!";
		this.playing = false;

		canvas.removeCommand(flagCommand);
		canvas.addCommand(exitCommand);
		canvas.setCommandListener(thi);

		canvas.repaint();
	}

	public void win(){
		message = "You Win!";
		this.playing = true;
		
		canvas.removeCommand(flagCommand);
		canvas.addCommand(exitCommand);
		canvas.setCommandListener(thi);
		
		canvas.repaint();
	}

	public void exitMIDlet() {
		notifyDestroyed();
	}

	public static void p(int i){
		System.out.println(i);
	}

	public void p(String s){
		System.out.println(s);
	}

	public void newGame(){
		this.display = Display.getDisplay(this);

		this.spaces = this.generate();
		spaces[0][0].gohere();

		this.message = "";
		this.playing = true;

		this.canvas = new MCanvas(this);
		
		this.p("letso");

		this.canvas.addCommand(okCommand);
		this.canvas.addCommand(newCommand);
		this.canvas.addCommand(flagCommand);

		this.canvas.setCommandListener(this);

		this.thi = this;

		this.display.setCurrent(this.canvas);
/*
		canvas.repaint();
		*/
		this.p("endNEW");
	}

	public Space[][] generate(){
		p("generate");
		int[][] mines = this.generateMines();
		p("here");
		Space[][] data2 = new Space[this.length][this.height];

		for(int c=0; c<this.length; c++){
			for(int r=0; r<this.height; r++){
				int value;

				if(mines[c][r] == 1){
					value = 9;
				} else {
					int surrounding = 0;

					if(c>0){
						surrounding += mines[c-1][r];
					}
					if(c<this.length-1){
						surrounding += mines[c+1][r];
					}
					if(r>0){
						surrounding += mines[c][r-1];
					}
					if(r<this.height-1){
						surrounding += mines[c][r+1];
					}

					if(c>0 && r>0){
						surrounding += mines[c-1][r-1];
					}
					if(c>0 && r<this.height-1){
						surrounding += mines[c-1][r+1];
					}
					if(c<this.length-1 && r>0){
						surrounding += mines[c+1][r-1];
					}
					if(c<this.length-1 && r<this.height-1){
						surrounding += mines[c+1][r+1];
					}
					value = surrounding;
				}
				data2[c][r] = new Space(c, r, value, this);
			}
		}
		this.p("returning");
		return data2;
	}

	/**
	 * Generates a grid of mines
	 * @return int[] array of int[] arrays. 1=mine, 0=no mine. (int for purposes of adding up)
	 */
	public int[][] generateMines(){
		System.out.println("generateMines()");
		
		this.numMines = (int) ((this.height+this.length)/2*1.2);
		
		this.mines = new int[this.numMines][2];

		int[][] data = new int[this.length][this.height];

		Random rand = new Random();

		for(int i=0; i<this.numMines; i++){
			this.p("1.0");
			int c = (int) (rand.nextFloat()*this.length);
			int r = (int) (rand.nextFloat()*this.height);
			data[c][r] = 1;
			this.p("1.1");
			this.mines[i][0] = c;
			this.mines[i][1] = r;
			this.p("1.2");
		}
		this.p("out");

		return data;
	}
}
