package Visualization;

import Visualization.DrawingPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private JLabel lbl_1;
    private JLabel lbl_2;
    private JTextField txtf_1;
    private JTextField txtf_2;
    private JButton btn_1;
    private JButton btn_2;
    private JButton btn_3;

    public MainWindow(String title){
        super(title);
        WindowSettings();
        LayoutSettins();
    }

    private void WindowSettings(){
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(new Point(400, 400));
        setResizable(true);
    }

    private void LayoutSettins(){
        lbl_1 = new JLabel("some text");
        lbl_2 = new JLabel("some text");
        txtf_1 = new JTextField(10);
        txtf_2 = new JTextField(10);
        btn_1 = new JButton("button_1");
        btn_2 = new JButton("button_2");
        btn_3 = new JButton("button_3");

        DrawingPanel pnl_paint = new DrawingPanel();
        Dimension d = new Dimension(1000, 1000);
        pnl_paint.setPreferredSize(d);

        //JPanel pnl_paint = new JPanel();
        JScrollPane scrollPane = new JScrollPane(pnl_paint);
        Box box_text = Box.createHorizontalBox();
        Box box_buttons = Box.createHorizontalBox();
        //JPanel pnl_main = new JPanel(new GridBagLayout());
        Box box_main = Box.createVerticalBox();

        pnl_paint.setBackground(new Color(20, 180, 250));

        box_text.add(lbl_1);
        box_text.add(Box.createHorizontalStrut(10));
        box_text.add(txtf_1);
        box_text.add(Box.createHorizontalStrut(10));
        box_text.add(lbl_2);
        box_text.add(Box.createHorizontalStrut(10));
        box_text.add(txtf_2);

        btn_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "hello");
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
        box_buttons.add(btn_1);
        box_buttons.add(btn_2);
        box_buttons.add(btn_3);

        box_main.add(scrollPane);
        box_main.add(box_text);
        box_main.add(box_buttons);
//
//        pnl_main.add(scrollPane, new GridBagConstraints(0,0,1,1,1.0,1.0,18, GridBagConstraints.BOTH, new Insets(1,1,0,0),0,400));
//        pnl_main.add(box_text, new GridBagConstraints(0,1,1,1,1.0,1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0),100,0));
//        pnl_main.add(box_buttons, new GridBagConstraints(0,2,1,1,1.0,1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,10,0,0),0,0));
        setContentPane(box_main);


    }

}
