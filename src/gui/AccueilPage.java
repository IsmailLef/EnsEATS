package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Query;


public class AccueilPage extends JPanel {

    private int width;
    private int height;

    private Application appli;

    private SpringLayout layout;

    private SelectorPanel categorieSelector;
    private JScrollPane categorieScroll;
    private String categorieSelected;

    private SelectorPanel restoSelector;
    private JScrollPane restoScroll;
    private String restoSelected;

    private JPanel descriptionZone;

    private JLabel horaireLabel;
    private JSlider horaireSliderDebut;
    private JSlider horaireSliderFin;
    private int previousSliderValue1;
    
    private JButton commanderButton;
    private JButton supprimerButton;
    private JButton deconnexionButton;

    public AccueilPage(Application appli) {

        this.width = (int) appli.getSize().getWidth();
        this.height = (int) appli.getSize().getHeight() - 30;

        this.appli = appli;

        this.setBackground(Color.LIGHT_GRAY);

        this.layout = new SpringLayout();
        this.setLayout(this.layout);

        // Categorie
        this.categorieSelector = new SelectorPanel(this.width*5/20, this.height*10/20, new SelectorPanelListener() {
            @Override
            public void apply(String s) {
                pressCategorie(s);
            }
        });
        this.categorieScroll = new JScrollPane(this.categorieSelector);
        this.categorieScroll.setPreferredSize(new Dimension(this.width*5/20,this.height*10/20));
        this.categorieScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(categorieScroll);
        this.layout.putConstraint(SpringLayout.WEST, categorieScroll, this.width*1/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, categorieScroll, this.height*2/20, SpringLayout.NORTH, this);
        // this.categorieSelector.addButton("Premiere categorie");
        // this.categorieSelector.addButton("Deuxieme categorie");
        this.afficheMainCategory();
        this.categorieSelector.refreshPosition();

        // Resto
        this.restoSelector = new SelectorPanel(this.width*5/20, this.height*10/20, new SelectorPanelListener() {
            @Override
            public void apply(String s) {
                pressResto(s);
            }
        });
        this.restoScroll = new JScrollPane(this.restoSelector);
        this.restoScroll.setPreferredSize(new Dimension(this.width*5/20,this.height*10/20));
        this.restoScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(restoScroll);
        this.layout.putConstraint(SpringLayout.WEST, restoScroll, this.width*7/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, restoScroll, this.height*2/20, SpringLayout.NORTH, this);
        // this.restoSelector.addButton("Premier resto");
        // this.restoSelector.addButton("Deuxieme resto");
        // this.restoSelector.addButton("Troisieme resto");
        // this.restoSelector.addButton("Quatrieme resto");
        this.afficheResto(null);
        this.restoSelector.refreshPosition();
        
        // Description
        this.descriptionZone = new JPanel();
        this.descriptionZone.setPreferredSize(new Dimension(this.width*6/20, this.height/2));
        this.add(this.descriptionZone);
        this.layout.putConstraint(SpringLayout.WEST, this.descriptionZone, this.width*13/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.descriptionZone, this.height/10, SpringLayout.NORTH, this);
        this.descriptionZone.setBackground(Color.WHITE);

        // Horaire
        this.horaireLabel = new JLabel("Horaire sélectionnée");
        this.add(this.horaireLabel);
        this.layout.putConstraint(SpringLayout.WEST, this.horaireLabel, this.width*8/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.horaireLabel, this.height*13/20, SpringLayout.NORTH, this);

        this.horaireSliderDebut = new JSlider(0, 1439);
        this.add(this.horaireSliderDebut);
        this.horaireSliderDebut.setPreferredSize(new Dimension(this.width*7/20, this.height*1/20));
        this.horaireSliderDebut.setValue(0);
        this.layout.putConstraint(SpringLayout.WEST, this.horaireSliderDebut, this.width*6/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.horaireSliderDebut, this.height*14/20, SpringLayout.NORTH, this);
        this.horaireSliderDebut.setOpaque(false);
        this.horaireSliderDebut.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateHoraire();
            }
        });

        this.horaireSliderFin = new JSlider(0, 1439);
        this.add(this.horaireSliderFin);
        this.horaireSliderFin.setPreferredSize(new Dimension(this.width*7/20, this.height*1/20));
        this.horaireSliderFin.setValue(1439);
        this.layout.putConstraint(SpringLayout.WEST, this.horaireSliderFin, this.width*6/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.horaireSliderFin, this.height*15/20, SpringLayout.NORTH, this);
        this.horaireSliderFin.setOpaque(false);
        this.horaireSliderFin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateHoraire();
            }
        });

        this.previousSliderValue1 = 0;

        this.updateHoraire();

        // Commander
        this.commanderButton = new JButton("Commander");
        this.commanderButton.setPreferredSize(new Dimension(3*this.width/20, this.height/20));
        this.add(this.commanderButton);
        this.layout.putConstraint(SpringLayout.WEST, this.commanderButton, 15*this.width/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.commanderButton, 7*this.height/10, SpringLayout.NORTH, this);
        this.commanderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appli.changePage(Application.COMMANDE_PAGE);
            }
        });
        this.commanderButton.setEnabled(false);

        // Supprimer
        this.supprimerButton = new JButton("Supprimer les données");
        this.supprimerButton.setPreferredSize(new Dimension(2*this.width/20, this.height/20));
        this.add(this.supprimerButton);
        this.layout.putConstraint(SpringLayout.WEST, this.supprimerButton, 14*this.width/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.supprimerButton, 8*this.height/10, SpringLayout.NORTH, this);
        this.supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Suppression des données effectuée.");
                supprimerClient();
            }
        });

        // Deconnecter
        this.deconnexionButton = new JButton("Déconnexion");
        this.deconnexionButton.setPreferredSize(new Dimension(2*this.width/20, this.height/20));
        this.add(this.deconnexionButton);
        this.layout.putConstraint(SpringLayout.WEST, this.deconnexionButton, 17*this.width/20, SpringLayout.WEST, this);
        this.layout.putConstraint(SpringLayout.NORTH, this.deconnexionButton, 8*this.height/10, SpringLayout.NORTH, this);
        this.deconnexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clean();
                appli.deconnexion();
            }
        });
    }

    public void clean() {
        this.categorieSelected = "";
        this.categorieSelector.selectButton("");
        this.restoSelected = "";
        this.restoSelector.selectButton("");
        this.horaireSliderDebut.setValue(0);
        this.horaireSliderFin.setValue(1439);
        updateHoraire();
        desafficheResto();
        this.commanderButton.setEnabled(false);
    } 

    private void pressCategorie(String s) {
        this.categorieSelected = s;
        this.categorieSelector.selectButton(s);
        this.desafficheCategory();  // Update category
        this.afficheCategory(s);
        this.desafficheResto(); // Update resto
        this.afficheResto(s);
    }

    private void pressResto(String s) {
        this.restoSelected = s;
        this.restoSelector.selectButton(s);
        afficheDescription();
        this.commanderButton.setEnabled(true);
    }


    private void afficheMainCategory() {
        try {
            ResultSet sqlReply = Query.getMainCategory(this.appli.getDB());
            while (sqlReply.next()) {
                String name = sqlReply.getString(1);
                restoSelector.addButton(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficheCategory(String category) {
        try {
            ResultSet sqlReply;
            sqlReply = Query.getSubCategory(category, this.appli.getDB());
            while (sqlReply.next()) {
                String name = sqlReply.getString(1);
                restoSelector.addButton(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void desafficheCategory() {
        categorieSelector.removeAll();
    }

    private void afficheResto(String category) {
        try {
            ResultSet sqlReply = Query.getRestaurants(category, this.appli.getDB());
            while (sqlReply.next()) {
                String name = sqlReply.getString(1);
                restoSelector.addButton(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void desafficheResto() {
        restoSelector.removeAll();
    }

    private void afficheDescription() {
        descriptionZone.removeAll();
        //TODO
    }


    private void supprimerClient() {
        try {
            Query.supprimerClient(this.appli.getEmailClient(), this.appli.getDB());
            this.appli.deconnexion();
            this.appli.getDB().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateHoraire() {
        int vs1 = this.horaireSliderDebut.getValue();
        int vs2 = this.horaireSliderFin.getValue();
        if (vs1 > vs2) {
            if (this.previousSliderValue1 == vs1) {
                this.horaireSliderDebut.setValue(vs2);
            } else {
                this.horaireSliderFin.setValue(vs1);
            }
        }
        vs1 = this.horaireSliderDebut.getValue();
        vs2 = this.horaireSliderFin.getValue();
        this.previousSliderValue1 = vs1;
        String horaire = horaireText(vs1, vs2);
        this.horaireLabel.setText(horaire);
    }

    private String horaireText(int a, int b) {
        String heuresA = String.valueOf(a/60);
        String minutesA = String.valueOf(a%60);
        if (minutesA.length() == 1) {
            minutesA = "0" + minutesA;
        }
        String heuresB = String.valueOf(b/60);
        String minutesB = String.valueOf(b%60);
        if (minutesB.length() == 1) {
            minutesB = "0" + minutesA;
        }
        return String.format("%sh%s  -  %sh%s", heuresA, minutesA, heuresB, minutesB);
    }

    private void test(String s) {
        System.out.println(s);
        if (s == "Deuxieme categorie") {
            //this.categorieSelector.removeButton(0);
            this.categorieSelector.refreshPosition();
            this.categorieScroll.updateUI();
        }
    }
}