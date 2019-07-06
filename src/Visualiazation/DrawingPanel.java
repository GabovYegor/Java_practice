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

    DrawingPanel(Graph graph){
        this.graph = graph;
        calculateNodesLocation(this.graph);
    }

    public static void calculateNodesLocation(Graph graph){
        Random random = new Random();
        for(int i = 0; i < graph.nodeCount(); ++i){
            graph.getNodeByIndex(i).setLocation(new Point(random.nextInt(500) + BIGRADIUS, random.nextInt(500) + BIGRADIUS));
        }
    }

    @Override
    protected void paintComponent ( Graphics g ) {
        repaint();
        super.paintComponent ( g );
        Graphics2D g2 = (Graphics2D) g;
        drawNodes(g2);
        drawEdges(g2);
    }

    private void drawNodes(Graphics2D g2){
        for(int i = 0; i < graph.nodeCount(); ++i){
            drawOneNode(g2, String.valueOf(graph.getNodeByIndex(i).getName()),  graph.getNodeByIndex(i).getLocation());
        }
    }

    private void drawEdges(Graphics2D g2){
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

    private void printStringInPoint(Graphics2D g2, String string, Point point){
        g2.setColor(Color.black);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString(string, point.x - OFFSETFORNAME, point.y + OFFSETFORNAME);
    }

    private void drawOneNode(Graphics2D g2, String string, Point point){
        g2.setColor(Color.black);
        g2.fillOval(point.x - BIGRADIUS / 2, point.y - BIGRADIUS /2, BIGRADIUS, BIGRADIUS);
        g2.setColor(Color.WHITE);
        g2.fillOval(point.x - LITTLERADIUS / 2, point.y - LITTLERADIUS /2, LITTLERADIUS, LITTLERADIUS);
        printStringInPoint(g2, string, point);
    }
}
