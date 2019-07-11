package Visualiazation;

import DataClasses.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    private Graph graph;
    public boolean isAlgorithm = false;
    public static final int BIGRADIUS = 30;
    private static final int LITTLERADIUS = 24;
    private static final int ARROWANGLE = 50;
    private static final int ARROWLENGTH = 30;
    private static final int OFFSETFORNAME = 7;
    private static final int OFFSETFORWEIGHT = 15;


    DrawingPanel(Graph graph, JTextField txtfNode){
        this.graph = graph;
        listenerSettings(txtfNode);
    }

    @Override
    protected void paintComponent ( Graphics g ) {
        super.paintComponent ( g );
        repaint();
        Graphics2D g2 = (Graphics2D) g;
        drawEdges(g2);
        drawNodes(g2);
    }

    private void drawNodes(Graphics2D g2){
        for(int i = 0; i < graph.nodeCount(); ++i){
            drawOneNode(g2, String.valueOf(graph.getNodeByIndex(i).getName()), graph.getNodeByIndex(i).getDistance(),
                    graph.getNodeByIndex(i).getLocation(), graph.getNodeByIndex(i).getColor());
        }
    }

    private void drawArrows(Graphics2D g2, Point nodeFromLocation, Point nodeToLocation){
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(2.0f));
        double  edgeAngle = Math.atan2(nodeFromLocation.y - nodeToLocation.y, nodeFromLocation.x - nodeToLocation.x);
        g2.drawLine((int)(nodeToLocation.x + BIGRADIUS / 2 * Math.cos(edgeAngle)), (int)(nodeToLocation.y + BIGRADIUS / 2 * Math.sin(edgeAngle)),
                (int)(nodeToLocation.x + ARROWLENGTH * Math.cos(edgeAngle + ARROWANGLE)),
                (int)(nodeToLocation.y + ARROWLENGTH * Math.sin(edgeAngle + ARROWANGLE)));

        g2.drawLine((int)(nodeToLocation.x + BIGRADIUS / 2 * Math.cos(edgeAngle)), (int)(nodeToLocation.y + BIGRADIUS / 2 * Math.sin(edgeAngle)),
                (int)(nodeToLocation.x + ARROWLENGTH * Math.cos(edgeAngle - ARROWANGLE)),
                (int)(nodeToLocation.y + ARROWLENGTH * Math.sin(edgeAngle - ARROWANGLE)));
    }

    private void drawEdges(Graphics2D g2){
        for(int i = 0; i < graph.nodeCount(); ++i){
            Point nodeFromLocation = new Point(graph.getNodeByIndex(i).getLocation());
            ArrayList <Edge> currentAdjacencyList = graph.getNodeByIndex(i).getAdjacencyList();
            for(int j = 0; j < currentAdjacencyList.size(); ++j){
                Point nodeToLocation = new Point();
                nodeToLocation = new Point(graph.getNodeByName(currentAdjacencyList.get(j).getEndNodeName()).getLocation());
                drawLine(g2, nodeFromLocation, nodeToLocation, currentAdjacencyList.get(j).getColor());
                drawArrows(g2, nodeFromLocation, nodeToLocation);
                printEdgeWeightInPoint(g2, String.valueOf(currentAdjacencyList.get(j).getWeight()), nodeFromLocation, nodeToLocation);
            }
        }
    }

    private void drawMainLine(Graphics2D g2, Point from, Point to, Color color){
        g2.setColor(color);
        g2.setStroke(new BasicStroke(3.0f));
        g2.drawLine(from.x, from.y, to.x, to.y);
    }

    private void drawLine(Graphics2D g2, Point from, Point to, Color color){
        drawMainLine(g2, from, to, color);
    }

    private void printStringInPoint(Graphics2D g2, String string, Point point){
        g2.setColor(Color.black);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString(string, point.x - OFFSETFORNAME, point.y + OFFSETFORNAME);
    }

    private void printEdgeWeightInPoint(Graphics2D g2, String weight, Point nodeFromLocation, Point nodeToLocation){
        double edgeAngle = Math.atan2(nodeFromLocation.y - nodeToLocation.y, nodeFromLocation.x - nodeToLocation.x);
        int length  = (int)Math.sqrt(Math.pow(nodeFromLocation.x - nodeToLocation.x, 2) + Math.pow(nodeFromLocation.y - nodeToLocation.y, 2)) / 2;
        printStringInPoint(g2, weight, new Point((int)(nodeToLocation.x + length * Math.cos(edgeAngle) + OFFSETFORWEIGHT * Math.cos(edgeAngle + 90)),
                                                 (int)(nodeToLocation.y + length * Math.sin(edgeAngle) + OFFSETFORWEIGHT * Math.sin(edgeAngle + 90))));

    }

    private void drawOneNode(Graphics2D g2, String string, int distance, Point point, Color color){
        if(isAlgorithm) {
            if(distance == Integer.MAX_VALUE)
                printStringInPoint(g2, String.valueOf('\u221E'), new Point(point.x, point.y - BIGRADIUS));
            else
                printStringInPoint(g2, String.valueOf(distance), new Point(point.x, point.y - BIGRADIUS));
        }

        g2.setColor(color);
        g2.fillOval(point.x - BIGRADIUS / 2, point.y - BIGRADIUS /2, BIGRADIUS, BIGRADIUS);
        g2.setColor(Color.WHITE);
        g2.fillOval(point.x - LITTLERADIUS / 2, point.y - LITTLERADIUS /2, LITTLERADIUS, LITTLERADIUS);
        printStringInPoint(g2, string, point);
    }

    private void listenerSettings(JTextField txtfNode) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!txtfNode.getText().isEmpty()) {
                        graph.getNodeByIndex(graph.addNode(txtfNode.getText().charAt(0))).setLocation(e.getPoint());
                        txtfNode.setText("");
                        return;
                    }
                    else
                        for (int i = 0; i < graph.nodeCount(); ++i) {
                            if (graph.getNodeByIndex(i).getLocation().x <= e.getPoint().x + BIGRADIUS / 2 &&
                                    graph.getNodeByIndex(i).getLocation().x >= e.getPoint().x - BIGRADIUS / 2 &&
                                    graph.getNodeByIndex(i).getLocation().y <= e.getPoint().y + BIGRADIUS / 2 &&
                                    graph.getNodeByIndex(i).getLocation().y >= e.getPoint().y - BIGRADIUS / 2) {
                                graph.removeNode(graph.getNodeByIndex(i).getName());
                                return;
                            }
                        }
                    JOptionPane.showMessageDialog(null, "Node`s name empty");
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    for (int i = 0; i < graph.nodeCount(); ++i) {
                        if (graph.getNodeByIndex(i).getLocation().x <= e.getPoint().x + BIGRADIUS / 2 &&
                                graph.getNodeByIndex(i).getLocation().x >= e.getPoint().x - BIGRADIUS / 2 &&
                                graph.getNodeByIndex(i).getLocation().y <= e.getPoint().y + BIGRADIUS / 2 &&
                                graph.getNodeByIndex(i).getLocation().y >= e.getPoint().y - BIGRADIUS / 2)
                            if (graph.getNodeByIndex(i).getColor() == Color.black)
                                graph.getNodeByIndex(i).setColor(Color.BLUE);
                            else if (graph.getNodeByIndex(i).getColor() == Color.BLUE)
                                graph.getNodeByIndex(i).setColor(Color.YELLOW);
                            else
                                graph.getNodeByIndex(i).setColor(Color.black);
                    }
                }
            }
        });
    }

    public void updateGraph(Graph newGraph){
        this.graph = newGraph;
    }

    public void setTrueIsAlgorithmValue(){
        isAlgorithm = true;
    }
    public void setFalseIsAlgorithmValue() {
        isAlgorithm = false;
    }
}
