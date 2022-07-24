package brick;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bricks {

	public int brk[][];
	public int bw;
	public int bh;
	public Bricks(int row, int col) {
		brk = new int [row][col];
		for(int i=0; i<brk.length; i++) {
			for(int j=0; j<brk[0].length; j++) {
				brk[i][j] = 1;
			} 
		}
		
		bw = 540/col;
		bh = 150/row;
	}
	
	public void draw(Graphics2D g) {
		for(int i=0; i<brk.length; i++) {
			for(int j=0; j<brk[0].length; j++) {
				if(brk[i][j] > 0) {
					g.setColor(Color.white);
					g.fillRect(j * bw + 80, i*bh + 50, bw, bh);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * bw + 80, i*bh + 50, bw, bh);
					
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		brk[row][col] = value;
	}
}

