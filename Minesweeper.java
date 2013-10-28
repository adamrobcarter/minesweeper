package minesweeper;

import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class Minesweeper extends MIDlet implements CommandListener {
	public static int height = 10;
	public static int length = 10;
	public Canvas canvas;
	public Space[][] spaces;
	public Space selected;
	public static Command exitCommand = new Command("Exit", Command.EXIT, 0);
	public Command okCommand = new Command("Uncover", Command.OK, 1);
	public Command newCommand = new Command("New", Command.HELP, 2);
	public static Command flagCommand = new Command("Flag", Command.CANCEL, 2);
	public static String error = "";
	public String message = "";
	public static CommandListener thi = null;

	public void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	public void pauseApp() {
	}

	public void startApp() throws MIDletStateChangeException {
		this.newGame();
	}

	public void commandAction(Command c, Displayable d) {
		if(c == exitCommand){
			exitMIDlet();
		} else if(c == okCommand){
			this.selected.uncover();
		} else if(c == newCommand){
			newGame();
		} else if(c == flagCommand){
			this.selected.flag();
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
		for(int col=0; col<spaces.length; col++){
			for(int row=0; row<spaces[col].length; row++){
				spaces[col][row].show();
			}
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

		canvas.removeCommand(flagCommand);
		canvas.addCommand(exitCommand);
		canvas.setCommandListener(thi);

		canvas.repaint();
	}

	public void win(){
		message = "You Win!";
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
		p("start");

		Display display = Display.getDisplay(this);

		this.spaces = this.generate();
		spaces[0][0].gohere();

		this.message = "";

		this.canvas = new MCanvas(this);

		//canvas.addCommand(exitCommand);
		canvas.addCommand(okCommand);
		canvas.addCommand(newCommand);
		canvas.addCommand(flagCommand);

		canvas.setCommandListener(this);

		this.thi = this;

		display.setCurrent(canvas);

		canvas.repaint();
	}



	public Space[][] generate(){

		System.out.println("generate()");

		int[][] mines = generateMines();

		Space[][] data2 = new Space[Minesweeper.length][Minesweeper.height];

		for(int r=0; r<Minesweeper.height; r++){
			for(int c=0; c<Minesweeper.length; c++){

				int value;

				if(mines[r][c]==1){
					value = 9;
				} else {

					int surrounding = 0;

					if(c>0){
						surrounding += mines[r][c-1];
					}
					if(c<Minesweeper.length-1){
						surrounding += mines[r][c+1];
					}
					if(r>0){
						surrounding += mines[r-1][c];
					}
					if(r<Minesweeper.height-1){
						surrounding += mines[r+1][c];
					}

					if(c>0 && r>0){
						surrounding += mines[r-1][c-1];
					}
					if(c>0 && r<Minesweeper.height-1){
						surrounding += mines[r+1][c-1];
					}
					if(c<Minesweeper.length-1 && r>0){
						surrounding += mines[r-1][c+1];
					}
					if(c<Minesweeper.length-1 && r<Minesweeper.height-1){
						surrounding += mines[r+1][c+1];
					}
					value = surrounding;
				}
				data2[c][r] = new Space(c, r, value, this);
			}
		}
		return data2;
	}

	/**
	 * Generates a grid of mines
	 * @return int[] array of int[] arrays. 1=mine, 0=no mine. (int for purposes of adding up)
	 */
	public static int[][] generateMines(){
		System.out.println("generateMines()");

		int[][] data = new int[Minesweeper.height][Minesweeper.length];

		Random rand = new Random();

		for(int i=0; i<12; i++){
			int c = (int) (rand.nextFloat()*10);
			int r = (int) (rand.nextFloat()*10);
			data[c][r] = 1;
		}

		return data;
	}
}
