package Visualiazation;

import DataClasses.Graph;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {
    private Graph graph;

    DrawingPanel(Graph graph){
        calculateNodesLocation(graph);
    }

    @Override
    protected void paintComponent ( Graphics g ) {
        repaint();
        super.paintComponent ( g );
        Graphics2D g2 = (Graphics2D) g;

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

    private void calculateNodesLocation(Graph graph){

    }
}
