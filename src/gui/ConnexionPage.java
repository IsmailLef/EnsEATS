package gui;
import javax.swing.*;

import database.Query;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnexionPage extends JPanel {

    //private Application appli;
    private int width;
    private int height;

    private Application appli;

    private SpringLayout layout;

    private JLabel titre;
    private JLabel descriptif;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel mdpLabel;
    private JTextField mdpField;
    private JButton connexionButton;
    private JLabel messageErreur;

    private JButton boutonCache;


    public ConnexionPage(Application appli) {

        this.appli = appli;
        this.width = appli.getWidth();
        this.height = appli.getHeight() - 24;

        this.setBackground(Color.GREEN);

        this.layout = new SpringLayout();
        this.setLayout(this.layout);

        // Titre
        this.titre = new JLabel("CONNEXION");
        this.add(this.titre);
        this.layout.putConstraint(SpringLayout.NORTH, this.titre, this.height*3/20, SpringLayout.NORTH, this);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.titre, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // Descriptif
        this.descriptif = new JLabel("Connectez-vous pour profiter des services de Grenoble Eat");
        this.add(this.descriptif);
        this.layout.putConstraint(SpringLayout.NORTH, this.descriptif, this.height*5/20, SpringLayout.NORTH, this);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.descriptif, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // Email label
        this.emailLabel = new JLabel("Identifiant (e-mail)");
        this.add(this.emailLabel);
        this.layout.putConstraint(SpringLayout.NORTH, this.emailLabel, this.height*8/20, SpringLayout.NORTH, this);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.emailLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // Email field
        this.emailField = new JTextField(30);
        this.add(this.emailField);
        this.layout.putConstraint(SpringLayout.NORTH, this.emailField, 20, SpringLayout.NORTH, this.emailLabel);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.emailField, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // MDP label
        this.mdpLabel = new JLabel("Mot de passe");
        this.add(this.mdpLabel);
        this.layout.putConstraint(SpringLayout.NORTH, this.mdpLabel, this.height*12/20, SpringLayout.NORTH, this);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.mdpLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // MDP field
        this.mdpField = new JTextField(30);
        this.add(this.mdpField);
        this.layout.putConstraint(SpringLayout.NORTH, this.mdpField, 20, SpringLayout.NORTH, this.mdpLabel);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.mdpField, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // Connexion button
        this.connexionButton = new JButton("Connexion");
        this.add(this.connexionButton);
        this.layout.putConstraint(SpringLayout.NORTH, this.connexionButton, this.height*16/20, SpringLayout.NORTH, this);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.connexionButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
        this.connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressConnexionButton();
            }
        });

        // Message erreur
        this.messageErreur = new JLabel("");
        this.add(this.messageErreur);
        this.layout.putConstraint(SpringLayout.NORTH, this.messageErreur, this.height*17/20, SpringLayout.NORTH, this);
        this.layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.messageErreur, 0, SpringLayout.HORIZONTAL_CENTER, this);

        this.boutonCache = new JButton();
        this.add(this.boutonCache);
        this.boutonCache.setPreferredSize(new Dimension(this.width*2/20,this.height*2/20));
        this.layout.putConstraint(SpringLayout.NORTH, this.boutonCache, this.height*18/20, SpringLayout.NORTH, this);
        this.layout.putConstraint(SpringLayout.WEST, this.boutonCache, 0, SpringLayout.WEST, this);
        this.boutonCache.setContentAreaFilled(false);
        this.boutonCache.setBorderPainted(false);
        this.boutonCache.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boutonTropBienCache();
            }
        });
    }

    public void clean() {
        this.emailField.setText("");
        this.mdpField.setText("");
        this.messageErreur.setText("");
    }

    private void pressConnexionButton() {

        String emailConnexion = this.emailField.getText();
        String mdpConnexion = this.mdpField.getText();

        if (emailConnexion.equals("") || mdpConnexion.equals("")) {
            this.messageErreur.setText("Vous devez saisir votre email de connexion et votre mot de passe.");
        } else {
            if (emailConnexion.length() >= 30) {
                this.messageErreur.setText("Il n'y a aucun compte avec cet e-mail.");
            } else if (mdpConnexion.length()>= 30) {
                this.messageErreur.setText("Votre mot de passe est incorrect");
            } else {
                String mdpClient = "";
                ResultSet result = Query.getPassword(emailConnexion, this.appli.getDB());
                try {
                    if (! result.next()) {
                        this.messageErreur.setText("Il n'y a aucun compte avec cet e-mail.");
                    } else  {
                        mdpClient = result.getString("mdp");
                        if (! mdpConnexion.equals(mdpClient)) {
                            this.messageErreur.setText("Votre mot de passe est incorrect");
                        } else {
                            appli.changePage(Application.ACCUEIL_PAGE);
                            appli.setEmailClient(emailConnexion);
                            this.clean();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void boutonTropBienCache() {
        appli.changePage(Application.ACCUEIL_PAGE);
        appli.setEmailClient("jean.michel@gmail.com");
        this.clean();
    }
}