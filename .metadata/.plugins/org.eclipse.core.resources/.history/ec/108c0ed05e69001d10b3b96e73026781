import javax.swing.JPanel;
import java.awt.Graphics2D;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class gamePlay extends JPanel implements KeyListener,ActionListener{

	private boolean play = false;
	private int inital_score=0;
	private int totalBricks=21;
	
	private Timer timer;
	private int delay=0;
	
	private int playerX=310;
	private int ballPositionX=120;
	private int ballPositionY=350;
	
	private int ballXDirection = -1;
	private int ballYDirection = -2;
	private MapGenerator map;

	public gamePlay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paint(Graphics g) {
		
		 g.setColor(Color.BLACK);
		 g.fillRect(1,1,692,592);
		 
		 //bricks
		 map.draw((Graphics2D)g);
		 
		 //paddle
		 g.setColor(Color.GREEN);
		 g.fillRect(playerX,550,100,8);
		 
		 //score
		 g.setColor(Color.RED);
		 g.setFont(new Font("Cambria",Font.PLAIN,25));
		 g.drawString("Score:"+inital_score,590,30);
		 
		 //creating ball
		 g.setColor(Color.YELLOW);
		 g.fillOval(ballPositionX,ballPositionY,20,20);
		 
		 if(totalBricks <= 0) {
			 play = false;
			 ballXDirection=0;
			 ballYDirection=0;
			 g.setColor(Color.RED);
			 g.setFont(new Font("Cambria",Font.PLAIN,30));
			 g.drawString("You Won!!.Score:"+inital_score,260,300);
			 
			 g.setFont(new Font("Cambria",Font.PLAIN,30));
			 g.drawString("Press Enter To Restart",230,350);
		 }
		 
		 if(ballPositionY>570) {
			 play = false;
			 ballXDirection=0;
			 ballYDirection=0;
			 g.setColor(Color.RED);
			 g.setFont(new Font("Cambria",Font.PLAIN,30));
			 g.drawString("Game Over.Scores: "+inital_score,190,300);
			 
			 g.setFont(new Font("Cambria",Font.PLAIN,30));
			 g.drawString("Press Enter To Restart",230,350);

		 }
		 g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			//detecting the paddle
			if(new Rectangle(ballPositionX,ballPositionY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYDirection= -ballYDirection;
			}
			A:for(int i=0;i<map.map.length;i++){
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX = j*map.brickWidth+80;
						int brickY = i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle(ballPositionX,ballPositionY,20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0,i,j);
							totalBricks--;
							inital_score+=5;
							if(ballPositionX + 19 <=brickRect.x || ballPositionX+1>=brickRect.x+brickRect.width) {
								ballXDirection = -ballXDirection;
							}
							else {
								ballYDirection = -ballYDirection;
							}
							break A;
						}
					}
				}
			}
			
			ballPositionX += ballXDirection;
			ballPositionY += ballYDirection;
			if(ballPositionX<0) {
				ballXDirection = -ballXDirection;
			}
			if(ballPositionY<0) {
				ballYDirection = -ballYDirection;
			}
			if(ballPositionX>670) {
				ballXDirection = -ballXDirection;
			}
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=600) {
				playerX=600;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				if(playerX<10) {
					playerX=10;
				}
				else {
					moveLeft();
				}
		}
		if(e.getKeyCode()== KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPositionX=120;
				ballPositionY=350;
				ballXDirection = -1;
				ballYDirection = -2;
				playerX = 310;
				inital_score=0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
	}
	public void moveRight() {
		play =true;
		playerX+=40;
	}
	public void moveLeft() {
		play =true;
		playerX-=40;
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	
}
