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
    Vector points = new Vector();
    DrawingPanel(Vector new_odj){
        points = new_odj;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                points.addElement(new Example(e.getX(), e.getY(), 50, 50));
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

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(8.0f));  // толщина равна 10
        g2.drawLine(0, 0, 1000, 1000);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2.0f));  // толщина равна 10
        g2.drawLine(0, 0, 1000, 1000);

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(8.0f));  // толщина равна 10
        g2.drawLine(0, 500, 500, 0);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2.0f));  // толщина равна 10
        g2.drawLine(0, 500, 500, 0);
    }
}