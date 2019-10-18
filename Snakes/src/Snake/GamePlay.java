package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private ImageIcon titleImage;

	private ImageIcon rightMouth;
	private ImageIcon leftMouth;
	private ImageIcon upMouth;
	private ImageIcon downMouth;

	private ImageIcon snakeBody;

	private int[] snakePosX = new int[750];
	private int[] snakePosY = new int[750];

	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;

	private Timer timer;
	private int delay = 100;

	private int lengthOfSnake = 3;

	private int moves = 0;

	private int[] enemyPosX = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450,
			475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850 };

	private int[] enemyPosY = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450,
			475, 500, 525, 550, 575, 600, 625};

	private ImageIcon enemyImage;

	private Random random = new Random();

	private int xPos = random.nextInt(34);
	private int yPos = random.nextInt(23);

	private int score = 0;
	private int length = 3;

	public GamePlay() {

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

	}

	public void paint(Graphics g) {

		// only when game has just started(default pos of snake)
		if (moves == 0) {

			snakePosX[0] = 100;
			snakePosX[1] = 75;
			snakePosX[2] = 50;

			snakePosY[0] = 100;
			snakePosY[1] = 100;
			snakePosY[2] = 100;

		}

		// drawing titleImage border
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 853, 56);

		// drawing titleImage
		titleImage = new ImageIcon("src/resourcesSnake/snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);

		// drawing border for gameplay area
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);

		// drawing game play area
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 575);
		// I took width and height as multiples of 25 since thats the width and height
		// of the images

		// draw score board
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Score: " + score, 780, 30);

		// draw length
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length: " + lengthOfSnake, 780, 50);

		// initial snake
		rightMouth = new ImageIcon("src/resourcesSnake/rightmouth.png");
		rightMouth.paintIcon(this, g, snakePosX[0], snakePosY[0]);

		for (int i = 0; i < lengthOfSnake; i++) {
			// draw face (i = 0)
			if (i == 0 && right) {
				rightMouth = new ImageIcon("src/resourcesSnake/rightmouth.png");
				rightMouth.paintIcon(this, g, snakePosX[i], snakePosY[i]);
			}
			if (i == 0 && left) {
				leftMouth = new ImageIcon("src/resourcesSnake/leftmouth.png");
				leftMouth.paintIcon(this, g, snakePosX[i], snakePosY[i]);
			}
			if (i == 0 && up) {
				upMouth = new ImageIcon("src/resourcesSnake/upmouth.png");
				upMouth.paintIcon(this, g, snakePosX[i], snakePosY[i]);
			}
			if (i == 0 && down) {
				downMouth = new ImageIcon("src/resourcesSnake/downmouth.png");
				downMouth.paintIcon(this, g, snakePosX[i], snakePosY[i]);
			}

			// draw body
			if (i != 0) {
				snakeBody = new ImageIcon("src/resourcesSnake/snakeimage.png");
				snakeBody.paintIcon(this, g, snakePosX[i], snakePosY[i]);
			}

		}

		//collision with enemy
		enemyImage = new ImageIcon("src/resourcesSnake/enemy.png");
		enemyImage.paintIcon(this, g, enemyPosX[xPos], enemyPosY[yPos]);

		if (enemyPosX[xPos] == snakePosX[0] && enemyPosY[yPos] == snakePosY[0]) {
			lengthOfSnake++;
			score++;
			xPos = random.nextInt(34);
			yPos = random.nextInt(23);
		}
		
		//collision with tail
		for(int i = lengthOfSnake - 1; i >= 1; i--) {
			if(snakePosX[0] == snakePosX[i] && snakePosY[0] == snakePosY[i]) {
				right = false;
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("GAME OVER! :(", 300, 300);
				
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Press SPACE to RESTART", 350, 340);
			}
		}

		g.dispose();
		/*
		 * self note: When you ask for a graphical object, Windows will allocate a bit
		 * of memory for you. Calling dispose will tidy up that memory for you. If you
		 * do not call dispose, all of these handles to memory will remain open and
		 * eventually your system will run out of resources, become slower and
		 * eventually stop
		 */
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (right) {
			for (int y = lengthOfSnake - 1; y >= 0; y--) {
				snakePosY[y + 1] = snakePosY[y];
			}
			for (int x = lengthOfSnake; x >= 0; x--) {
				if (x == 0) {
					snakePosX[x] += 25;
				} else {
					snakePosX[x] = snakePosX[x - 1];
				}
				// right border -> come out from the left
				if (snakePosX[x] > 850) {
					snakePosX[x] = 25;
				}
			}
			repaint();
		}

		if (left) {
			for (int y = lengthOfSnake - 1; y >= 0; y--) {
				snakePosY[y + 1] = snakePosY[y];
			}
			for (int x = lengthOfSnake; x >= 0; x--) {
				if (x == 0) {
					snakePosX[x] -= 25;
				} else {
					snakePosX[x] = snakePosX[x - 1];
				}
				// left border -> come out from the right
				if (snakePosX[x] < 25) {
					snakePosX[x] = 850;
				}
			}
			repaint();
		}

		if (up) {
			for (int x = lengthOfSnake - 1; x >= 0; x--) {
				snakePosX[x + 1] = snakePosX[x];
			}
			for (int y = lengthOfSnake; y >= 0; y--) {
				if (y == 0) {
					snakePosY[y] -= 25;
				} else {
					snakePosY[y] = snakePosY[y - 1];
				}
				// top border -> come out from the bottom
				if (snakePosY[y] < 75) {
					snakePosY[y] = 625;
				}
			}
			repaint();
		}

		if (down) {
			for (int x = lengthOfSnake - 1; x >= 0; x--) {
				snakePosX[x + 1] = snakePosX[x];
			}
			for (int y = lengthOfSnake; y >= 0; y--) {
				if (y == 0) {
					snakePosY[y] += 25;
				} else {
					snakePosY[y] = snakePosY[y - 1];
				}
				// bottom border -> come out from the top
				if (snakePosY[y] > 625) {
					snakePosY[y] = 75;
				}
			}
			repaint();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// cannot move right if already moving in left direction
			moves++;
			if (left) {
				right = false;
			} else {
				right = true;
			}
			up = false;
			down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// cannot move left if already moving in right direction
			moves++;
			if (right) {
				left = false;
			} else {
				left = true;
			}
			up = false;
			down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			// cannot move up if already moving in down direction
			moves++;
			if (down) {
				up = false;
			} else {
				up = true;
			}
			right = false;
			left = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			// cannot move down if already moving in up direction
			moves++;
			if (up) {
				down = false;
			} else {
				down = true;
			}
			right = false;
			left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			moves = 0;
			lengthOfSnake = 3;
			score = 3;
			repaint();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
