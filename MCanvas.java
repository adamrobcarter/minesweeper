package minesweeper;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class MCanvas extends Canvas {
	
	protected void paint(Graphics g){
		//Minesweeper.p("repaint");
		
		//Space[] data = Minesweeper.topaint;///////////////////////////////////////////////////////
		
		if(Minesweeper.topaint == null){
			g.setColor(0, 0, 0);
			g.drawString("null", 30, 70, Graphics.HCENTER | Graphics.BASELINE);
		} else {
			g.setColor(0, 0, 0);
			g.drawString("non-null", 30, 70, Graphics.HCENTER | Graphics.BASELINE);
		}
		
		for(int x=0; x<Minesweeper.topaint.length; x++){//////////////////////////////////////////////////////////
		
			//Minesweeper.p("startloop");
			//Minesweeper.p(x);
			//Minesweeper.p(Minesweeper.topaint.length);
			//Minesweeper.p(Minesweeper.topaint[x].getClass().getName());
			Minesweeper.topaint[x].print();
			
			int r = Minesweeper.topaint[x].row * 10;
			int c = Minesweeper.topaint[x].col * 10;
			int v = Minesweeper.topaint[x].value;
			String s = "";
			
			//System.out.println("r:"+Integer.toString(r)+" c:"+Integer.toString(c)+" s:"+Integer.toString(v));
			
			g.setColor(250, 0, 0);
			
			//Minesweeper.p("if");
			
			if(Minesweeper.topaint[x].open){
				switch(v){
				case 0:
					g.setColor(50, 50, 50);
					break;
				case 1:
					g.setColor(100, 50, 50);
					s = "1";
					break;
				case 2:
					g.setColor(150, 50, 50);
					s = "2";
					break;
				case 3:
					g.setColor(200, 50, 50);
					s = "3";
					break;
				case 4:
					g.setColor(250, 50, 50);
					s = "4";
					break;
				case 5:
					g.setColor(250, 100, 100);
					s = "5";
				break;
				case 6:
					g.setColor(250, 125, 125);
					s = "6";
					break;
				case 7:
					g.setColor(250, 150, 150);
					s = "7";
					break;
				case 8:
					g.setColor(250, 175, 175);
					s = "8";
					break;
				case 9:
					g.setColor(250, 200, 200);
					break;
				default:
					g.setColor(250, 100, 100);
				}
			} else {
				g.setColor(0,0,0);
			}
			
			//Minesweeper.p("endif");
			
			g.fillRect(c, r, 10, 10);
			
			g.setColor(250, 250, 250);
			Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);  
			g.setFont(font);
			
			if(Minesweeper.topaint[x].flagged){
				g.drawString(">", c+5, r+8, Graphics.HCENTER | Graphics.BASELINE);
			}
			
			g.drawString(s, c+5, r+8, Graphics.HCENTER | Graphics.BASELINE);
			
			if(Minesweeper.topaint[x].hover){
				g.setColor(250, 250, 250);
				g.drawLine(c, r, c, r+9);
				g.drawLine(c, r, c+9, r);
				g.drawLine(c+9, r, c+9, r+9);
				g.drawLine(c, r+9, c+9, r+9);
			}
			
			//Minesweeper.p("here?");
		}

		//}//////////////////////////////////////////////////////////////////////////////////////
		//try {
		
		//Minesweeper.p("here");
		
		//Minesweeper.p(Minesweeper.error);
		
		if(Minesweeper.error != null){
			//g.drawString(Minesweeper.error, 50, 50, Graphics.HCENTER | Graphics.BASELINE);
		}
		if(Minesweeper.message.length() > 0){
			Minesweeper.p("msg:"+Minesweeper.message);
		}
		g.setColor(0, 0, 0);
		Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);  
		g.setFont(font);
		g.drawString(Minesweeper.message, this.getWidth()/2, this.getHeight()-3, Graphics.HCENTER | Graphics.BASELINE);

	}
	
	protected void keyPressed(int keyCode) {
	    int gameaction = getGameAction(keyCode);

		int c = Minesweeper.selected.col;
		int r = Minesweeper.selected.row;
		
	    switch (gameaction) {
	    case UP:
	    	Minesweeper.p("UP");
	    	
	    	if(r>0){
	    		Minesweeper.selected.leavehere();
	    		Minesweeper.getSpace(Minesweeper.selected.row - 1, Minesweeper.selected.col).gohere();
	    	}
	    	break;
	    case DOWN:
	    	Minesweeper.p("DOWN");
	    	
	    	if(r<Minesweeper.height-1){
	    		Minesweeper.selected.leavehere();
	    		Minesweeper.getSpace(Minesweeper.selected.row + 1, Minesweeper.selected.col).gohere();
	    	}
	    	break;
	    case LEFT:
	    	Minesweeper.p("LEFT");
	    	
	    	if(c>0){
	    		Minesweeper.selected.leavehere();
	    		Minesweeper.getSpace(Minesweeper.selected.row, Minesweeper.selected.col - 1).gohere();
	    	}
	    	break;
	    case RIGHT:
	    	Minesweeper.p("RIGHT");
	    	
	    	if(c<Minesweeper.length-1){
	    		Minesweeper.selected.leavehere();
	    		Minesweeper.getSpace(Minesweeper.selected.row, Minesweeper.selected.col + 1).gohere();
	    	}
			break;
	    }
	    repaint();
	  }
}
