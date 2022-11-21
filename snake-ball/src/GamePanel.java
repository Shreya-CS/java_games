import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH=500;
	static final int SCREEN_HEIGHT=500;
	static final int UNIT_SIZE=20;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=75;
	private String highScore = "";
	
	final int x[]=new int[GAME_UNITS];
	final int y[]=new int[GAME_UNITS];
	int bodyParts=6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		StartGame();
		
	}
	public void StartGame() {
		newApple();
		running = true;
		timer=new Timer(DELAY,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			draw(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void draw(Graphics g) throws IOException {
		//just a matrix for easier understanding
		if(running) {
			/*
			for(int i =0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
			}
			for(int i =0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
			}*/
			//drawings for apple
			g.setColor(Color.blue);
			g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
			
			//drawings for snake
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45,180,0));
//					generate random snake colors
//					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Cambria",Font.BOLD,15));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
			
			g.setFont(new Font("Cambria",Font.BOLD,15));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("High Score: "+highScore,(SCREEN_WIDTH-metrics1.stringWidth("High Score: "+applesEaten))/17,g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		if(highScore.equals("")) {
			highScore=this.GetHighScore();
		}
		
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1]; //shifting the coordinates to one spot
			y[i]=y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
		break;
		case 'D':
		y[0] = y[0] + UNIT_SIZE;
		break;
		case 'L':
		x[0] = x[0] - UNIT_SIZE;
		break;
		case 'R':
		x[0] = x[0] + UNIT_SIZE;
		break;
		}
}
	public void checkApple() {
		if((x[0] == appleX) && y[0]== appleY) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		//checks if head collides with body
		for(int i=bodyParts;i>0;i--) {
			if((x[0]==x[i]) && y[0]==y[i]) {
				running=false;
			}
		}
		//checks if head touches left border
		if(x[0]<0) {
			checkScore();
			running=false;
		}
		//checks if head touches right border
		if(x[0]>SCREEN_WIDTH) {
			checkScore();
			running=false;
		}
		//checks if head touches top border
		if(y[0]<0) {
			checkScore();
			running=false;
		}
		//checks if head touches bottom border
		if(y[0]>SCREEN_HEIGHT) {
			checkScore();
			running=false;
		}
		if(!running) {
			checkScore();
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont(new Font("Cambria",Font.BOLD,25));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		//here string width parameter helps us to locate our word in center
		g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
		//highscore
		g.setColor(Color.red);
		g.setFont(new Font("Cambria",Font.BOLD,20));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		//here string width parameter helps us to locate our word in center
		g.drawString("High Score: \n"+highScore,(int) ((SCREEN_WIDTH-metrics2.stringWidth("High Score: "+applesEaten))/2.4),SCREEN_HEIGHT/4);
		//GameOver Screen
		g.setColor(Color.red);
		g.setFont(new Font("Cambria",Font.BOLD,50));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		//here string width parameter helps us to locate our word in center
		g.drawString("Game Over",(SCREEN_WIDTH-metrics3.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
//			checkScore();
		}
		repaint();
	}
	public String GetHighScore() throws IOException {
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("highscore.txt");
			reader = new BufferedReader(readFile);
			return reader.readLine();
		}
		 catch(Exception e) {
			 return "Nobody:0";
		 }
		finally {
			try {
				if(reader!=null) {
					reader.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		 
	}
	public void checkScore() {
		if(applesEaten > Integer.parseInt(highScore.split(":")[1])) { 
			String name = JOptionPane.showInputDialog("You set a new high score!!!Enter your name:");  
			highScore = name+":"+ applesEaten;
			
			File scoreFile = new File("highscore.txt");
			if(!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore);
			}
			catch(Exception e){
				
			}
			finally {
				if(writer!=null) {
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				}
			break;
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction='R';
				}
			break;
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction='U';
				}
			break;
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction='D';
				}
			break;
			}
		}
	}

}
