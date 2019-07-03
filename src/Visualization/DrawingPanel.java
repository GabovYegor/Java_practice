/** TODO
 *  1) Рассмотреть repaint
 *  2) Рисовать граф - принимать положения вершин + ребра + цвета ребер
 */


package Visualization;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {
    private static final int RADIUS = 50;
    private static final int X = 50;
    private static final int Y = 50;
    private int moveXBy;

    @Override
    protected void paintComponent ( Graphics g ) {
        super.paintComponent ( g );
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRect ( 50, RADIUS, X, Y );
        g2.drawRect ( 100, RADIUS, X, Y );
        g2.drawRect ( 150, RADIUS, X, Y );
        g2.drawRect ( 200, RADIUS, X, Y );
        g2.drawRect ( 250, RADIUS, X, Y );
        g2.setColor ( Color.RED );
        g2.fillOval ( moveXBy, RADIUS, X, Y ) ;

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