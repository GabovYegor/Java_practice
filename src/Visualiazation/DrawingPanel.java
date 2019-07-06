package Visualiazation;

import DataClasses.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private Graph graph;
    private static final int BIGRADIUS = 30;
    private static final int LITTLERADIUS = 24;
    private static final int OFFSETFORNAME = 7;
    private static final int BORDER = 100;

    DrawingPanel(Graph graph){
        this.graph = graph;
        calculateNodesLocation(this.graph);
    }

    @Override
    protected void paintComponent ( Graphics g ) {
        repaint();
        super.paintComponent ( g );
        Graphics2D g2 = (Graphics2D) g;
        printNodes(g2);
        printEdges(g2);
    }

    private void printNodes(Graphics2D g2){
        for(int i = 0; i < graph.nodeCount(); ++i){
            Point tempLocation = graph.getNodeByIndex(i).getLocation();
            g2.setColor(Color.black);
            g2.fillOval(tempLocation.x - BIGRADIUS / 2, tempLocation.y - BIGRADIUS /2, BIGRADIUS, BIGRADIUS);
            g2.setColor(Color.WHITE);
            g2.fillOval(tempLocation.x - LITTLERADIUS / 2, tempLocation.y - LITTLERADIUS /2, LITTLERADIUS, LITTLERADIUS);
            g2.setColor(Color.black);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2.drawString(String.valueOf(graph.nodeName(i)), tempLocation.x - OFFSETFORNAME, tempLocation.y + OFFSETFORNAME);
        }
    }

    private void printEdges(Graphics2D g2){
        for(int i = 0; i < graph.nodeCount(); ++i){
            Point nodeFromLocation = new Point(graph.getNodeByIndex(i).getLocation());
            ArrayList <Edge> currentAdjacencyList = graph.getNodeByIndex(i).getAdjacencyList();
            for(int j = 0; j < currentAdjacencyList.size(); ++j){
                Point nodeToLocation = new Point(graph.getNodeByName(currentAdjacencyList.get(j).getEndNodeName()).getLocation());
                Point vector2D = new Point(nodeToLocation.x - nodeFromLocation.x, nodeToLocation.y - nodeFromLocation.y);
                drawLine(g2, new Point(nodeFromLocation.x, nodeFromLocation.y), new Point(nodeToLocation.x, nodeToLocation.y));
            }
        }
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

    public static void calculateNodesLocation(Graph graph){
        Random random = new Random();
        for(int i = 0; i < graph.nodeCount(); ++i){
            graph.getNodeByIndex(i).setLocation(new Point(random.nextInt(500 + BORDER), random.nextInt(500 + BORDER)));
        }
    }
}
