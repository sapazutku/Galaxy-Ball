import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayGame extends JPanel implements KeyListener, ActionListener {

    
    Ball ball = new Ball();
    Paddle paddle = new Paddle();
    BrickGenerator generator;

    Font f = new Font("serif", Font.BOLD, 30);
    ImageIcon background = new ImageIcon(getClass().getResource("images/galaxybackground.jpg"));

    private boolean playable = false;
    public int score = 0;
    private int totalBricks = 65;       //normally its 75 but changed for arrange window (5*15)

    private Timer timer;
    private int delay = 8;

    public PlayGame() {

        generator = new BrickGenerator(5, 15);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics g) {

        // background
        background.paintIcon(this, g, 0, 0);

        // map
        generator.draw((Graphics2D) g);

        // the scores
        g.setColor(Color.black);
        g.setFont(f);
        g.drawString("" + score, 590, 30);

        // the paddle
        paddle.player.paintIcon(this, g, paddle.x, 550);

        // the ball
        ball.imgBall.paintIcon(this, g, ball.x, ball.y);

        // starting the game
        if (totalBricks == 0) {

            playable = false;
            ball.dirX = 0;
            ball.dirY = 0;

            g.setColor(Color.black);
            g.drawString("Level-1   Earth", 300, 300);

            g.setColor(Color.black);
            g.drawString("Press Space to Start", 250, 300);
        }

        // when you win the game
        if (totalBricks == 0) {
            win(g);
        }

        // when you lose the game
        if (ball.y > 570) {
            lose(g);
        }

        g.dispose();
    }

    public void win(Graphics g) {
        
            score += 100;
            playable = false;
            ball.dirX = 0;
            ball.dirY = 0;

            g.setColor(Color.black);
            g.setFont(f);

            g.drawString("Level Completed   Score:" + score, 150, 250);
            g.drawString("Press Space to Play", 150, 350);
        
    }

    public void lose(Graphics g) {

            playable = false;
            ball.dirX = 0;
            ball.dirY = 0;
            g.setColor(Color.orange);
            g.setFont(f);

            g.drawString("Game Over  Score: " + score, 150, 300);
            g.drawString("Press Space to Restart", 150, 350);

    }

    public void moveRight() {
        playable = true;
        paddle.x += 10;
    }

    public void moveLeft() {
        playable = true;
        paddle.x -= 10;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddle.x >= 600) {
                paddle.x = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddle.x < 0) {
                paddle.x = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!playable) {

                  playable = true;
                  ball.x = 100;
                  ball.y = 300;

                  ball.dirX = -1;
                  ball.dirY = -2;
                  paddle.x = 300;
                  paddle.y = 550;
                  score = 0;
                  totalBricks = 65;
                  generator = new BrickGenerator(5, 15);

                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (playable) {
            if (new Rectangle(ball.x, ball.y, 20, 20).intersects(new Rectangle(paddle.x, paddle.y, 30, 8))) {

                ball.dirY = -ball.dirY;
                ball.dirX = -2;

            } else if (new Rectangle(ball.x, ball.y, 20, 20).intersects(new Rectangle(paddle.x + 70, paddle.y, 30, 8))) {

                ball.dirY = -ball.dirY;
                ball.dirX = ball.dirX + 1;

            } else if (new Rectangle(ball.x, ball.y, 20, 20).intersects(new Rectangle(paddle.x + 30, paddle.y, 40, 8))) {

                ball.dirY = -ball.dirY;

            }


            A: for (int i = 0; i < generator.map.length; i++) {
                for (int j = 0; j < generator.map[0].length; j++) {
                    if (generator.map[i][j] > 0) {

                        //field
                        int brickX = j * generator.brickWidth + 80;
                        int brickY = i * generator.brickHeight + 50;
                        int brickWidth = generator.brickWidth;
                        int brickHeight = generator.brickHeight;

                        //edges
                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ball.x, ball.y, 20, 20);
                        Rectangle brickRect = rectangle;


                        if (ballRect.intersects(brickRect)) {

                               // level 2 bricks
                            if (generator.getBrickValue(i, j) == 2) {
                                generator.setBrickValue(1, i, j);
                                score += 20;
                            } // level 1 bricks
                            else {
                                generator.setBrickValue(0, i, j); // deleting bricks
                                totalBricks--;
                                score += 15;
                            }

                            // right or left brick
                            if (ball.x + 19 <= brickRect.x || ball.x + 1 >= brickRect.x + brickRect.width) {
                                ball.dirX = -ball.dirX;
                            }
                            // top or bottom brick
                            else {
                                ball.dirY = -ball.dirY;
                            }

                            break A;
                        }
                    }
                }
            }

            ball.x += ball.dirX;
            ball.y += ball.dirY;

            if (ball.x < 8) {
                ball.dirX = -ball.dirX;
            }
            if (ball.y < 0) {
                ball.dirY = -ball.dirY;
            }
            if (ball.x > 660) {
                ball.dirX = -ball.dirX;
            }

            repaint();
        }
    }
}