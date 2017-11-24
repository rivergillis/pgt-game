import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private class MyPanel extends JPanel {
        Controller controller;

        MyPanel(Controller c) {
            controller = c;
            addMouseListener(c);
        }

        public void paintComponent(Graphics g) {
            controller.update(g);
            revalidate();
        }
    }


    public View(Controller c) throws Exception{
        setTitle("Pretty Good Things");
        setSize(400, 800);
        getContentPane().add(new MyPanel(c));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(c);
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }
}

