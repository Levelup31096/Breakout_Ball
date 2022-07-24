package brick;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 32;
	
	private Timer timer;
	private int delay = 2;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private Bricks brk;
	
	
	public Gameplay() {
		brk = new Bricks(4, 8);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		
		//Background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//Draw Bricks
		brk.draw((Graphics2D)g);;
		
		//Borders
		g.setColor(Color.white);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//Scores
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 22));
		g.drawString(""+score, 592, 30);
		
		//The Paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//The Ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY,  20, 20);
		
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.drawString("You Won: "+score, 190, 300);
			
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.drawString("Press Enter to Restart", 190, 350);
		}
		
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.drawString("Game Over!! Scores: "+score, 190, 300);
			
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.drawString("Press Enter to Restart", 190, 350);
		}
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = - ballYdir;
			}
			
		A:	for(int i=0; i<brk.brk.length; i++) {
				
				for(int j=0; j<brk.brk[0].length; j++) {
					
					if(brk.brk[i][j] > 0) {
						int brickX = j*brk.bw + 80;
						int brickY = i*brk.bh + 50;
						int bwidth = brk.bw;
						int bheight = brk.bh;
						
						Rectangle rect = new Rectangle(brickX, brickY, bwidth, bheight);
						Rectangle ballrct = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brkrct = rect;
						
						if(ballrct.intersects(brkrct)) {
							brk.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brkrct.x || ballposX + i >= brkrct.x + brkrct.width) {
								ballXdir = -ballXdir;
							}
							else {
								ballYdir = - ballYdir;
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();	
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if(playerX >= 600 ) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX < 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballYdir = -2;
				ballXdir = -1;
				playerX = 310;
				score = 0;
				totalBricks = 32;
				brk = new Bricks(4, 8);
				
				repaint();
			}
		}
	}
	public void moveRight() {
		play = true;
		playerX+=20;
	}
	public void moveLeft() {
		play = true;
		playerX-=20;
	}
}

