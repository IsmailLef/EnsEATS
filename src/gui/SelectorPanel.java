package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


public class SelectorPanel extends JPanel {

    private int width;
    private int height;

    private final int BUTTON_HEIGHT = 30;

    private SpringLayout layout;

    private ArrayList<JButton> buttonList;

    private SelectorPanelListener listener;

    public SelectorPanel(int width, int height, SelectorPanelListener listener) {
        this.width = width - 18;
        this.height = height - 4;
        this.listener = listener;

        this.layout = new SpringLayout();
        this.setLayout(this.layout);

        this.buttonList = new ArrayList<JButton>();

        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setBackground(Color.WHITE);
    }

    public void addButton(String name) {
        JButton b = new JButton(name);
        b.setPreferredSize(new Dimension(this.width, this.BUTTON_HEIGHT));
        b.setBackground(Color.WHITE);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionButton(name);
            }
        });
        this.buttonList.add(b);
        this.add(b);
    }

    public void removeButton(String name) {
        for (int i = 0; i < this.buttonList.size(); i++) {
            if (this.buttonList.get(i).getText() == name) {
                this.remove(this.buttonList.get(i));
                buttonList.remove(i);
                break;
            }
        }
    }

    public void removeAll() {
        for (int i = 0; i < this.buttonList.size(); i++) {
            this.remove(this.buttonList.get(i));
            buttonList.remove(i);
        }
    }

    public void actionButton(String name) {
        this.listener.apply(name);
    }

    public void refreshPosition() {
        for (int i = 0; i < this.buttonList.size(); i++) {
            this.layout.putConstraint(SpringLayout.NORTH, this.buttonList.get(i), i*this.BUTTON_HEIGHT, SpringLayout.NORTH, this);
            this.layout.putConstraint(SpringLayout.WEST, this.buttonList.get(i), 0, SpringLayout.WEST, this);
        }
    }

    public void selectButton(String name) {
        for (int i = 0; i < this.buttonList.size(); i++) {
            if (this.buttonList.get(i).getText() == name) {
                //this.buttonList.get(i).setEnabled(false);
                this.buttonList.get(i).setBackground(Color.GRAY);
            } else {
                //this.buttonList.get(i).setEnabled(true);
                this.buttonList.get(i).setBackground(Color.WHITE);
            }
        }
    }
}
