/**
 * Author: Quoc Duy Nguye
 * NetID: qnguy12
 * Assigment: Homework 4
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.*;

public class EventLabCanvas extends JFrame {

    public EventLabCanvas() {
	setSize(600,480);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	Canvas canvas = new Canvas();
	addMouseListener(canvas);
    addMouseMotionListener(canvas);
    addKeyListener(canvas);
	canvas.setVisible(true);
	add(canvas);

    }

    public static void main(String[] args) {
        EventLabCanvas theFrame = new EventLabCanvas();
        theFrame.setVisible(true);
    }
}

class Canvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    protected List<Integer[]> circles = new LinkedList<>();
    protected List<Color> colors = new LinkedList<>();
    protected int radius = 10;
    protected Color nextColor = Color.BLACK;
    protected boolean focused = false;
    protected int index = 0;
    protected Color chosenColor = Color.BLACK;

    public Canvas() {
        setBackground(Color.WHITE);
        // setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (circles.size() > 0){
            for (int i = 1; i < circles.size(); i++){
                g.setColor(colors.get(i));
                g.drawLine(circles.get(i)[0],circles.get(i)[1],
                circles.get(i-1)[0],circles.get(i-1)[1]);
            }
            for (int i = 0; i < circles.size(); i++){
                g.setColor(colors.get(i));
                drawCircle(g,circles.get(i)[0],circles.get(i)[1],radius);
            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e){
        Integer[] newCircle = new Integer[]{e.getX(),e.getY()};
        circles.add(newCircle);
        colors.add(nextColor);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        focused = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int X = e.getX(), Y = e.getY();
        boolean circleChosen = false;
        // int index = 0;
        if(circleChosen == false){
            for (int i = 0; i < circles.size(); i++){
                double distance = Math.sqrt(Math.pow((double) (X-circles.get(i)[0]), 2)
                                    + Math.pow((double) (Y-circles.get(i)[1]), 2));
                if ((int) distance < radius){
                    if (focused == false){
                        index = i;
                        chosenColor = colors.get(i);
                    }
                    circleChosen = true;
                    focused = true;
                    break;
                }
            }
        }
        if(circles.size() > 0 && circleChosen == true){
            circles.remove(index);
            colors.remove(index);
            Integer[] newCircle = new Integer[]{X,Y};
            circles.add(index,newCircle);
            colors.add(index,chosenColor);
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'r':
                nextColor = Color.RED;
                break;
            case 'b':
                nextColor = Color.BLUE;
                break;
            case 'g':
                nextColor = Color.GREEN;
                break;
            case 'l':
                nextColor = Color.BLACK;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public void drawCircle(Graphics g, int centerX, int centerY, int radius) {
        int topLeftX = centerX - radius;
        int topLeftY = centerY - radius;
        g.fillOval(topLeftX,topLeftY,radius*2,radius*2);
    }

}
