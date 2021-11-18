import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public BrickGenerator(int row, int col) {

        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length-2; j++) {
                if(i ==0 || i ==1) {
                    map[i][j] = 2;
                }else
                    map[i][j] = 1;


            }
        }

        brickWidth = 600 / col;   
        brickHeight = 100 / row;  
    }

 

    public void draw(Graphics2D g) {

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                if (map[i][j] == 1) {

                    g.setColor(Color.darkGray);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    // separate bricks
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                }
                if (map[i][j] == 2) {

                    g.setColor(new Color(0,102,51));
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
         map[row][col] = value;
    }

    public int getBrickValue(int row ,int col){
        return map[row][col];
    }
}
