package minesweeper;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class MCanvas extends Canvas {

	public Minesweeper game;

	MCanvas(Minesweeper ms){
		this.game = ms;
	}

	protected void paint(Graphics g){
		//Minesweeper.p("repaint");

		//Space[] data = Minesweeper.topaint;///////////////////////////////////////////////////////

		if(this.game.spaces == null){
			g.setColor(0, 0, 0);
			g.drawString("null", 30, 70, Graphics.HCENTER | Graphics.BASELINE);
		} else {
			g.setColor(0, 0, 0);
			g.drawString("non-null", 30, 70, Graphics.HCENTER | Graphics.BASELINE);
		}


		for(int col=0; col<this.game.spaces.length; col++){
			for(int row=0; row<this.game.spaces[col].length; row++){

				int r = this.game.spaces[col][row].row * 14;
				int c = this.game.spaces[col][row].col * 14;
				int v = this.game.spaces[col][row].value;
				String s = "";
				g.setColor(250, 0, 0);

				if(this.game.spaces[col][row].open){
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

				g.fillRect(c, r, 14, 14);

				g.setColor(250, 250, 250);
				Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);  
				g.setFont(font);
				int size = font.getBaselinePosition();

				if(this.game.spaces[col][row].flagged){
					//g.drawString(">", c+7, r+14, Graphics.HCENTER | Graphics.BASELINE);
					s = ">";
				}

				g.drawString(s, c+6, r-3, Graphics.HCENTER | Graphics.TOP);

				if(this.game.spaces[col][row].hover){
					g.setColor(250, 250, 250);
					g.drawLine(c, r, c, r+13);
					g.drawLine(c, r, c+13, r);
					g.drawLine(c+13, r, c+13, r+13);
					g.drawLine(c, r+13, c+13, r+13);
				}

				//Minesweeper.p("here?");
			}

			if(Minesweeper.error != null){
				//g.drawString(Minesweeper.error, 50, 50, Graphics.HCENTER | Graphics.BASELINE);
			}
			if(this.game.message.length() > 0){
				this.game.p("msg:"+this.game.message);
			}
			g.setColor(0, 0, 0);
			Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE);  
			g.setFont(font);
			g.drawString(this.game.message, this.getWidth()/2, this.getHeight()-3, Graphics.HCENTER | Graphics.BASELINE);

		}
	}

	protected void keyPressed(int keyCode) {
		int gameaction = getGameAction(keyCode);

		int c = this.game.selected.col;
		int r = this.game.selected.row;
		
		this.game.p("at"+Integer.toString(c)+":"+Integer.toString(r));

		switch (gameaction) {
		case UP:
			this.game.p("UP");

			if(r>0){
				this.game.selected.leavehere();
				this.game.getSpace(c, r-1).gohere();
			}
			break;
		case DOWN:
			this.game.p("DOWN");

			if(r<Minesweeper.height-1){
				this.game.selected.leavehere();
				this.game.getSpace(c, r+1).gohere();
			}
			break;
		case LEFT:
			this.game.p("LEFT");

			if(c>0){
				this.game.selected.leavehere();
				this.game.getSpace(c-1, r).gohere();
			}
			break;
		case RIGHT:
			this.game.p("RIGHT");

			if(c<Minesweeper.length-1){
				this.game.selected.leavehere();
				this.game.getSpace(c+1, r).gohere();
			}
			break;
		}
		repaint();
	}
}
