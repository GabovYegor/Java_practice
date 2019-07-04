/** TODO
 *  1) Рассмотреть repaint
 *  2) Рисовать граф - принимать положения вершин + ребра + цвета ребер
 */
package Visualization;

import DataClasses.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

class DrawingPanel extends JPanel {

    private static final int RADIUS = 30;

    Vector points = new Vector();
    DrawingPanel(Vector new_odj){
        points = new_odj;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(SwingUtilities.isLeftMouseButton(e))
                    points.addElement(new Example(e.getX() - RADIUS/2, e.getY()-RADIUS/2, RADIUS, RADIUS));
                else
                    JOptionPane.showMessageDialog(null, "it_s right mouse button");
            }
        });
    }

    @Override
    protected void paintComponent ( Graphics g ) {
        repaint();
        super.paintComponent ( g );
        Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < points.size(); ++i) {
            Example point = (Example) points.get(i);
            g2.fillOval(point.x, point.y, point.width, point.height);
        }

        drawLine(g2, new Point(0, 0), new Point(1000, 1000));
        drawLine(g2, new Point(0, 500), new Point(500, 0));
    }

    private void drawBlackLine(Graphics2D g2, Point from, Point to){
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(8.0f));  // толщина равна 10
        g2.drawLine(from.x, from.y, to.x, to.y);
    }

    private void drawWhiteLine(Graphics2D g2, Point from, Point to){
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2.0f));  // толщина равна 10
        g2.drawLine(from.x, from.y, to.x, to.y);
    }

    private void drawLine(Graphics2D g2, Point from, Point to){
        drawBlackLine(g2, from, to);
        drawWhiteLine(g2, from, to);
    }
}