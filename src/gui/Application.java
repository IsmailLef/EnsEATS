package gui;

import java.awt.CardLayout;
import javax.swing.*;
import database.DB;
import database.Init;


public class Application extends JFrame {

    public static String CONNEXION_PAGE = "Connexion";
    public static String ACCUEIL_PAGE = "Accueil";
    public static String COMMANDE_PAGE = "Commander";

    private int width;
    private int height;

    private ConnexionPage connexionPage;
    private AccueilPage accueilPage;
    private CommandePage commandePage;

    private CardLayout selecteurPage;
    private JPanel pageConteneur;

    private DB db;

    private String emailClient;
    
    public Application() {

        super("GrenobleEat");

        this.width = 1200;
        this.height = 800;
        this.setSize(this.width, this.height);

        this.db = new DB();
        this.emailClient = "";
/*
        Init.creerTables(this.db);*/
        // Init.emptyAllTables(db);
        // Init.insererExemples(this.db);
        this.db.commit();

        this.selecteurPage = new CardLayout();
        this.pageConteneur = new JPanel();
        this.pageConteneur.setLayout(this.selecteurPage);
        this.setContentPane(this.pageConteneur);
        
        this.connexionPage = new ConnexionPage(this);
        this.pageConteneur.add(connexionPage, Application.CONNEXION_PAGE);

        this.accueilPage = new AccueilPage(this);
        this.pageConteneur.add(accueilPage, Application.ACCUEIL_PAGE);

        this.commandePage = new CommandePage(this);
        this.pageConteneur.add(commandePage, Application.COMMANDE_PAGE);
        
        this.selecteurPage.show(this.pageConteneur, Application.CONNEXION_PAGE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getEmailClient() {
        return this.emailClient;
    }

    public void setEmailClient(String email) {
        this.emailClient = email;
    }

    public void changePage(String pageName) {
        if (pageName == Application.CONNEXION_PAGE) {
            this.selecteurPage.show(this.pageConteneur, Application.CONNEXION_PAGE);
        } else if (pageName == Application.ACCUEIL_PAGE) {
            this.selecteurPage.show(this.pageConteneur, Application.ACCUEIL_PAGE);
        } else if (pageName == Application.COMMANDE_PAGE) {
            this.selecteurPage.show(this.pageConteneur, Application.COMMANDE_PAGE);
        }
    }

    public DB getDB() {
        return this.db;
    }

    public void deconnexion() {
        this.selecteurPage.show(this.pageConteneur, Application.CONNEXION_PAGE);
        this.emailClient = "";
    }

    

}