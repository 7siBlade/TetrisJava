package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {
    JButton button1 = new JButton("Почати гру");
    JPanel top = new JPanel();

    public MenuWindow() {
        ButtonAction();
    }

    public void initWindow() {
        top.setLayout(null);
        setSize(Config.WIDTH * Config.SIZE + 15, Config.HEIGHT * Config.SIZE + 39);
        setLocationRelativeTo(null);
        setTitle("Menu");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(top);
        top.setBackground(Color.BLACK);
        setVisible(true);
    }

    public void initComponent(){
        setLayout(null);
        button1.setBounds(Config.WIDTH*10/2, Config.HEIGHT*10, 200, 50);
        button1.setVisible(true);
        top.add(button1);
    }

    private void ButtonAction() {
        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = new Window();
                javax.swing.SwingUtilities.invokeLater(window);
                window.addFigure();

                setVisible(false);
                //dispose();
            }
        };
        button1.addActionListener(actionListener1);
    }
}
