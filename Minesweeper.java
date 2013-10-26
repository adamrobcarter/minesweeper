package minesweeper;

import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class Minesweeper extends MIDlet implements CommandListener {
	public static int height = 10;
	public static int length = 10;
	public static Canvas canvas;
	public static Space[] topaint;
	public static Space selected;
	public static Command exitCommand = new Command("Exit", Command.EXIT, 0);
	public Command okCommand = new Command("Uncover", Command.OK, 1);
	public Command newCommand = new Command("New", Command.HELP, 2);
	public static Command flagCommand = new Command("Flag", Command.CANCEL, 2);
	public static String error = "";
	public static String message = "";
	public static CommandListener thi = null;

	public void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	public void pauseApp() {
	}

	public void startApp() throws MIDletStateChangeException {
		newGame();
	}
	
	public void commandAction(Command c, Displayable d) {
		if(c == exitCommand){
			exitMIDlet();
		} else if(c == okCommand){
			selected.uncover();
		} else if(c == newCommand){
			newGame();
		} else if(c == flagCommand){
			flag();
		}
	}

	/** Get space based on row and col
	 * @param col col to get
	 * @param row row to get
	 * @return Space instace of result */
	public static Space getSpace(int row, int col){
		//System.out.println("getS");
		for(int x=0; x<Minesweeper.topaint.length; x++){
			if(Minesweeper.topaint[x].col==col && Minesweeper.topaint[x].row==row){
				return Minesweeper.topaint[x];
			}
		}
		System.out.println("getS nonexistant");
		return null;
	}
	
	public static void flag(){
		if(!selected.flagged){
			selected.flagged = true;
		} else {
			selected.flagged = false;
		}
		canvas.repaint();
	}
	
	public static void uncoverMines(){
		for(int x=0; x<Minesweeper.topaint.length; x++){
			if(Minesweeper.topaint[x].value == 9){
				Minesweeper.topaint[x].show();
			}
		}
	}
	
	public static boolean uncoveredRest(){
		p("uR");
		for(int x=0; x<Minesweeper.topaint.length; x++){
			if(Minesweeper.topaint[x].value != 9 && Minesweeper.topaint[x].open == false){
				return false;
			}
		}
		return true;
	}
	
	public static void lost(){
		p("lose");
		uncoverMines();
		message = "You Lose!";

		canvas.addCommand(exitCommand);
		canvas.removeCommand(flagCommand);
		canvas.setCommandListener(thi);
		
		canvas.repaint();
	}
	
	public static void win(){
		p("win");
		message = "You Win!";
		canvas.repaint();
	}
	
    public void exitMIDlet() {
        notifyDestroyed();
    }
    
	public static void p(int i){
		System.out.println(i);
	}
	
	public static void p(String s){
		System.out.println(s);
	}

	public void newGame(){
		p("start");
		
		Display display = Display.getDisplay(this);
		
		Minesweeper.topaint = Minesweeper.generate();
		topaint[0].gohere();
		
		message = "";
		
		canvas = new MCanvas();

		//canvas.addCommand(exitCommand);
		canvas.addCommand(okCommand);
		canvas.addCommand(newCommand);
		canvas.addCommand(flagCommand);
		
		canvas.setCommandListener(this);
		
		thi = this;
		
		p("help");
		
		display.setCurrent(canvas);
		
		p("fine");
		
		canvas.repaint();
		
		p("nofine");
	}
	
	public static void uncoverSurrounding(Space s){
		Space[] toscan = new Space[length*height];
		
		toscan[0] = s;
		int tfull = 1;
		int x = 0;
		
		while (x<tfull){
			Space t = toscan[x];
			Space[] nowscan = new Space[4];
			
			p("^");
			
			int full = 0;
			
			p("l");
			if(t.col > 0){
				nowscan[full] = getSpace(t.row, t.col-1);
				full ++;
			}
			p("r");
			if(t.col < length){
				p(full);
				nowscan[full] = getSpace(t.row, t.col+1);
				full ++;
			}
			p("u");
			if(t.row > 0){
				nowscan[full] = getSpace(t.row, t.row-1);
				full ++;
			}
			p("d");
			if(t.row < height){
				nowscan[full] = getSpace(t.row, t.row+1);
				full ++;
			}
			
			p("'");
			
			for(int w=0; w<full; w++){
				p("foring");
				if(nowscan[w].value == 0 && nowscan[w].open == false){
					//p("iffing");
					nowscan[w].show();
					//p("uncovered?");
					p(tfull);
					p("here2");
					toscan[tfull] = topaint[0];
					p("here?");
					toscan[tfull] = nowscan[w];
					tfull += 1;
					p("dididie?");
				}
			}

			x++;
		}
	}
	
	private static void p(Space space) {
		System.out.println(space);
	}

	public static Space[] generate(){
		
		System.out.println("generate()");
		
		int[][] mines = generateMines();
		Space[] data = new Space[(Minesweeper.height*Minesweeper.length)+0];
		
		p("here");
		
		int i = 0;
		
		for(int r=0; r<Minesweeper.height; r++){
			p("1");
			for(int c=0; c<Minesweeper.length; c++){
				
				//p("2");
				
				//p(data.length);
				
				//p(r+"r");
				//p(c+"c");
				//p(i+"i");
				
				int value;
				
				if(mines[r][c]==1){
					//p("3.1");
					value = 9;
					//p("3.3");
				} else {
					
					//p("3.7");
					int surrounding = 0;
					//p("3.9");

					if(c>0){
						surrounding += mines[r][c-1];
					}
					if(c<Minesweeper.length-1){
						surrounding += mines[r][c+1];
					}
					//p("3.10");
					if(r>0){
						surrounding += mines[r-1][c];
					}
					//p("3.11");
					if(r<Minesweeper.height-1){
						surrounding += mines[r+1][c];
					}
					//p("3.12");
					
					if(c>0 && r>0){
						surrounding += mines[r-1][c-1];
					}
					//p("3.13");
					if(c>0 && r<Minesweeper.height-1){
						surrounding += mines[r+1][c-1];
					}
					//p("3.14");
					if(c<Minesweeper.length-1 && r>0){
						surrounding += mines[r-1][c+1];
					}
					//p("3.15");
					if(c<Minesweeper.length-1 && r<Minesweeper.height-1){
						surrounding += mines[r+1][c+1];
					}
					//p("3.16");
					
					value = surrounding;
				}
				data[i] = new Space(c, r, value);
				
				data[i].print();
				//p(i);
				
				//p("5");
				
				i += 1;
				
				//p("endloop");
			}
		}
		
		//p("endendloop");
		
		p("esc");
		
		return data;
		
	}
	
	/**
	 * Generates a grid of mines
	 * @return int[] array of int[] arrays. 1=mine, 0=no mine. (int for purposes of adding up)
	 */
	public static int[][] generateMines(){
		System.out.println("generateMines()");
		
		int[][] data = new int[Minesweeper.height][Minesweeper.length];
		
		//p("ptin");
		Random rand = new Random();

		//p("2");
		try{
		for(int r=0; r<Minesweeper.height; r++){
			//p("3");
			for(int c=0; c<Minesweeper.length; c++){
				//p("4");
				if(rand.nextFloat()>1.00){
					//p("5");
					data[r][c] = 1;
				}
			}
		}
		} catch (Exception e) {
			error = e.toString();
		}
		//p("6");
		
		return data;
		
	}

}
