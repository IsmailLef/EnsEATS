package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CommandePage extends JPanel {

    private int width;
    private int height;

    private Application appli;

    private SpringLayout layout;

    private SelectorPanel platSelector;
    private JScrollPane platScroll;
    private String platSelected;

    private SelectorPanel panierSelector;
    private JScrollPane panierScroll;
    private String panierSelected;

    private JPanel descriptionZone;

    private JButton ajouterPlatButton;

    private JButton enleverPlatButton;
    private JButton viderPanierButton;
    private JButton validerButton;

    private JButton retourButton;

    public CommandePage(Application appli) {

        this.width = (int) appli.getSize().getWidth();
        this.height = (int) appli.getSize().getHeight() - 30;

        this.appli = appli;

        this.setBackground(Color.ORANGE);

        this.layout = new SpringLayout();
        this.setLayout(this.layout);

        // Plat
        this.platSelector = new SelectorPanel(this.width*5/20, this.height*13/20, new SelectorPanelListener() {
            @Override
            public void apply(String s) {
                pressPlat(s);
            }
        });
        this.platScroll = new JScrollPane(this.platSelector);
        this.platScroll.setPreferredSize(new Dimension(this.width*5/20,this.height*14/20));
        this.platScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(platScroll);
        this.layout.putConstraint(SpringLayout.WEST, platScroll, this.width*1/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, platScroll, this.height*2/20, SpringLayout.NORTH, this);
        this.platSelector.addButton("Premier plat");
        this.platSelector.addButton("Deuxieme plat");
        this.platSelector.refreshPosition();

        // Panier
        this.panierSelector = new SelectorPanel(this.width*5/20, this.height*10/20, new SelectorPanelListener() {
            @Override
            public void apply(String s) {
                pressPanier(s);
            }
        });
        this.panierScroll = new JScrollPane(this.panierSelector);
        this.panierScroll.setPreferredSize(new Dimension(this.width*5/20,this.height*12/20));
        this.panierScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(panierScroll);
        this.layout.putConstraint(SpringLayout.WEST, panierScroll, this.width*7/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, panierScroll, this.height*2/20, SpringLayout.NORTH, this);
        this.panierSelector.addButton("Premier plat du panier");
        this.panierSelector.addButton("Deuxieme plat du panier");
        this.panierSelector.refreshPosition();

        // Description
        this.descriptionZone = new JPanel();
        this.descriptionZone.setPreferredSize(new Dimension(this.width*6/20, this.height*13/20));
        this.add(this.descriptionZone);
        this.layout.putConstraint(SpringLayout.WEST, this.descriptionZone, this.width*13/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.descriptionZone, this.height/10, SpringLayout.NORTH, this);
        this.descriptionZone.setBackground(Color.WHITE);

        // Bouton Plat
        this.ajouterPlatButton = new JButton("Ajouter plat");
        this.ajouterPlatButton.setPreferredSize(new Dimension(this.width*3/20, this.height/20));
        this.add(this.ajouterPlatButton);
        this.layout.putConstraint(SpringLayout.WEST, this.ajouterPlatButton, this.width*2/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.ajouterPlatButton, this.height*17/20, SpringLayout.NORTH, this);
        this.ajouterPlatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        this.ajouterPlatButton.setEnabled(false);

        // Boutons Panier
        this.viderPanierButton = new JButton("Vider panier");
        this.viderPanierButton.setPreferredSize(new Dimension(this.width*2/20, this.height/20));
        this.add(this.viderPanierButton);
        this.layout.putConstraint(SpringLayout.WEST, this.viderPanierButton, this.width*7/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.viderPanierButton, this.height*15/20, SpringLayout.NORTH, this);
        this.viderPanierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        this.viderPanierButton.setEnabled(false);

        this.enleverPlatButton = new JButton("Enlever plat du panier");
        this.enleverPlatButton.setPreferredSize(new Dimension(this.width*2/20, this.height/20));
        this.add(this.enleverPlatButton);
        this.layout.putConstraint(SpringLayout.WEST, this.enleverPlatButton, this.width*10/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.enleverPlatButton, this.height*15/20, SpringLayout.NORTH, this);
        this.enleverPlatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        this.enleverPlatButton.setEnabled(false);

        this.validerButton = new JButton("Valider");
        this.validerButton.setPreferredSize(new Dimension(this.width*3/20, this.height/20));
        this.add(this.validerButton);
        this.layout.putConstraint(SpringLayout.WEST, this.validerButton, this.width*8/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.validerButton, this.height*17/20, SpringLayout.NORTH, this);
        this.validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        this.validerButton.setEnabled(false);

        // Bouton retour
        this.retourButton = new JButton("Retour");
        this.retourButton.setPreferredSize(new Dimension(width*2/20, this.height/20));
        this.add(this.retourButton);
        this.layout.putConstraint(SpringLayout.WEST, this.retourButton, this.width*17/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.retourButton, this.height*18/20, SpringLayout.NORTH, this);
        this.retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appli.changePage(Application.ACCUEIL_PAGE);
            }
        });
    }

    private void pressPlat(String s) {
        this.platSelected = s;
        this.platSelector.selectButton(s);
        this.ajouterPlatButton.setEnabled(true);
    }

    private void pressPanier(String s) {
        this.panierSelected = s;
        this.panierSelector.selectButton(s);
    }

    private void ajouterPlatButtonAction() {
        //TODO
    }
}
