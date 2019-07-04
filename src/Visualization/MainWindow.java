package Visualization;

/** TODO
 *  1) Отдельный метод для рисования линии
 *  2) Создать интерфейс - разобраться с Layout
 *  3) Получить интерфейс и реализовать интерфейс к кнопкам
 */

import DataClasses.Example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;

public class MainWindow extends JFrame {
    private JLabel lbl_1;
    private JLabel lbl_2;
    private JTextField txtf_1;
    private JTextField txtf_2;
    private JButton btn_1;
    private JButton btn_2;
    private JButton btn_3;
    private DrawingPanel drawingPanel;

    public MainWindow(String title){
        super(title);
        lbl_1 = new JLabel("some text");
        lbl_2 = new JLabel("some text");
        txtf_1 = new JTextField(10);
        txtf_2 = new JTextField(10);
        btn_1 = new JButton("button_1");
        btn_2 = new JButton("button_2");
        btn_3 = new JButton("button_3");
        Vector points = new Vector();
        points.addElement(new Example(100, 100, 50, 50));
        points.addElement(new Example(200, 100, 50, 50));
        drawingPanel = new DrawingPanel(points);
        WindowSettings();
        LayoutSettins();
        buttonsSettings();
    }

    private void WindowSettings(){
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(new Point(400, 400));
        setResizable(true);
    }

    private void LayoutSettins(){

        Dimension d = new Dimension(1000, 1000);
        drawingPanel.setPreferredSize(d);

        JScrollPane scrollPane = new JScrollPane(drawingPanel);
        Box box_text = Box.createHorizontalBox();
        Box box_buttons = Box.createHorizontalBox();
        Box box_main = Box.createVerticalBox();

        drawingPanel.setBackground(new Color(20, 180, 250));
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                drawingPanel.setBackground(Color.white);
            }
        });

        box_text.add(lbl_1);
        box_text.add(Box.createHorizontalStrut(10));
        box_text.add(txtf_1);
        box_text.add(Box.createHorizontalStrut(10));
        box_text.add(lbl_2);
        box_text.add(Box.createHorizontalStrut(10));
        box_text.add(txtf_2);

        box_buttons.add(btn_1);
        box_buttons.add(Box.createHorizontalStrut(10));
        box_buttons.add(btn_2);
        box_buttons.add(Box.createHorizontalStrut(10));
        box_buttons.add(btn_3);
        box_buttons.add(Box.createHorizontalGlue());

        box_main.add(scrollPane);
        box_main.add(Box.createVerticalStrut(10));
        box_main.add(box_text);
        box_main.add(box_buttons);
        setContentPane(box_main);
    }

    private void buttonsSettings(){
        btn_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Vector vec = new Vector();
                vec.addElement(new Example(100, 300, 50, 50));
                vec.addElement(new Example(200, 300, 50, 50));
                vec.addElement(new Example(300, 300, 50, 50));
                drawingPanel.points = vec;
                repaint();
            }

        });
        btn_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!txtf_1.getText().isEmpty())
                    JOptionPane.showMessageDialog(null, txtf_1.getText());
                else
                    JOptionPane.showMessageDialog(null, "message empty");
            }
        });
        btn_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                Color[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.CYAN };
                drawingPanel.setBackground(colors[rand.nextInt(colors.length)]);
                repaint();
            }
        });
    }

}
