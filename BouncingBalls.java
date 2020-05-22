import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class BouncingBalls extends JPanel implements Runnable {

    Color color;
    int diameter;
    long delay;
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int g = 1;
    boolean run=true;

    public BouncingBalls(String ballcolor, int xvelocity, int yvelocity, int xcoordinate, int ycoordinate) {
        if(ballcolor == "red") {
            color = Color.red;
        } else if(ballcolor == "random"){
            Random random = new Random();
            color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
        diameter = 15;
        delay = 18;
        x = xcoordinate;
        y = ycoordinate;
        vx = xvelocity;
        vy = yvelocity;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(x,y,diameter,diameter); //adds color to circle
        g.setColor(Color.black);
        g2.drawOval(x,y,diameter,diameter); //draws circle
    }

    public void run() {
        while(isVisible()) {
            try {
                Thread.sleep(delay);
            } catch(InterruptedException e) {
                System.out.println("interrupted");
            }
            move();
            repaint();
        }
    }

    public void move(){

        if(x + vx < 0 || x + diameter + vx > getWidth()) { //this is where the ball has hit the wa
            if (vx<0){
                vx += 1;
            } else if(vx>0){
                vx -= 1;
            }
            vx *= -1;
        }
        if(run) {
            if (y + vy < 0 || y + diameter + vy > getHeight()) {
                vy -= 1;
                vy *= -1;
            } else {
                vy += g;

            }
        } else {
                if (vx < 0) {
                    vx = vx +1;
                } else if (vx>0){
                    vx = vx -1;
                }

        }
        x += vx;
        y += vy;
        if (y>getHeight()-diameter+1){
            y=getHeight()-diameter;
            vy = 0;
            run=false;
        }

    }

    private void start() {
        while(!isVisible()) {
            try {
                Thread.sleep(25);
            } catch(InterruptedException e) {
                System.exit(1);
            }
        }
        Thread thread = new Thread(this);
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    public static void main(String[] args) {
        Random random = new Random();
        BouncingBalls ball1 = new BouncingBalls("red",random.nextInt(20),random.nextInt(20),10,10);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(ball1);
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
        new Thread(ball1).start();
    }
}
