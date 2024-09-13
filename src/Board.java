import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//panel is besically a component;
public class Board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;

    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;

    private int apple_x;
    private int apple_y;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;



    private int dots;// this is for dots initialize ;

    Board() {
        addKeyListener(new TAdapter());

        setBackground(Color.BLACK);// this is use for background;
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);// this is use for frame focus;
        loadImages();// this is for creating image
        initGame();// initialize the game;
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("D:/SnakeGame/src/icons/apple.PNG"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("D:/SnakeGame/src/icons/dot.PNG"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("D:/SnakeGame/src/icons/head.PNG"));
        head = i3.getImage();
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
        }
        locateApple();

        Timer timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple() {
        int RANDOM_POSITION = 29;
        int r = (int) (Math.random() * RANDOM_POSITION);
        apple_x = r * DOT_SIZE;

        r = (int) (Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE;

    }


    public void PaintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g) {
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }

            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game over!";
        Font font = new Font("SAN SERIF",Font.BOLD,14);
        FontMetrics metrices =getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);

        g.drawString(msg,(300 -metrices.stringWidth(msg)) /2,300/2);
    }
     public void move(){
        for(int i =dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0] = x[0] - DOT_SIZE;
        }
        if(rightDirection){
            x[0] = x[0] + DOT_SIZE;
        }
        if(upDirection){
            y[0] = y[0] - DOT_SIZE;
        }
        if(downDirection){
            y[0] = y[0] + DOT_SIZE;
        }

//        x[0] +=DOT_SIZE;
//        y[0] +=DOT_SIZE;
    }
     public void checkApple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateApple();
        }
     }
     public void checkCollision(){
        for(int i = dots;i>0;i--){
           if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
               inGame = false;
           }
        }
        if(y[0] >=300){
            inGame = true;
        }
         if(x[0] >=300){
             inGame = true;
         }
         if(y[0] < 0){
             inGame = true;
         }
         if(x[0] < 0){
             inGame = true;
         }
         if(!inGame){
             timer.stop();
         }
     }


    public void actionPerformed(ActionEvent ae) {
        if(inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public class TAdapter extends KeyAdepter{
        //@Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection =false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection =false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_LEFT && (!downDirection)){
                upDirection = true;
                leftDirection =false;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_LEFT && (!upDirection)){
                downDirection = true;
                leftDirection =false;
                rightDirection = false;
            }
        }

    }
}




