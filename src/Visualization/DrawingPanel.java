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
        g.drawRect ( 50, RADIUS, X, Y );
        g.drawRect ( 100, RADIUS, X, Y );
        g.drawRect ( 150, RADIUS, X, Y );
        g.drawRect ( 200, RADIUS, X, Y );
        g.drawRect ( 250, RADIUS, X, Y );
        g.setColor ( Color.RED );
        g.fillOval ( moveXBy, RADIUS, X, Y ) ;
        g.setColor(Color.green);
        g.drawLine(0, 0, 1000, 1000);
    }
}
