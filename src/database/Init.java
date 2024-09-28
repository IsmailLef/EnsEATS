package database;

public class Init {

    private static final String tablesNames[]
        = {"Restaurant", "Plat", "Commande", "Commande_livraison",
           "Commande_sur_place", "TypeCmd", "Statut", "Eval", "Categorie",
           "Client", "Allergenes", "Contient", "Propose_type",
           "Specialise_dans", "Parent_de", "P_a_allergenes", "Associer"};




    public static void creerTables(DB db) {
        /* Créer la table des restaurants */
        db.sendUpdate("CREATE TABLE Restaurant(email_resto varchar(50) PRIMARY KEY," +
                                             "nom_resto varchar(50) NOT NULL," +
                                             "tel varchar(20) NOT NULL," +
                                             "adresse_resto varchar(100) NOT NULL," +
                                             "horaires varchar(50) NOT NULL," +
                                             "capacite int CHECK(capacite > 0) NOT NULL," +
                                             "texte_resto varchar(200) NOT NULL," +
                                             "note_resto float)");

        /* Créer la table des clients */
        db.sendUpdate("CREATE TABLE Client(id_client int PRIMARY KEY,"+
                                         "email_client varchar(50),"+
                                         "mdp varchar(20),"+
                                         "nom_client varchar(20),"+
                                         "prenom_client varchar(20),"+
                                         "adresse_client varchar(100))");

        /* Créer la table des allergènes */
        db.sendUpdate("CREATE TABLE Allergenes(allergenes varchar(20) PRIMARY KEY CHECK (allergenes IN ('gluten','crustaces', 'oeufs', 'arachides', 'soja', 'lait', 'fruit_a_coques', 'celeri', 'moutarde','sesame', 'anhydride_sulfureux', 'lupin', 'mollusques')))");

        /* Créer la table des catégories de restaurants */
        db.sendUpdate("CREATE TABLE Categorie(categorie varchar(20) PRIMARY KEY)");

        /* Créer la table liant les catégories pères aux catégories fils */
        db.sendUpdate("CREATE TABLE Parent_de(categorie_pere varchar(20),"+
                                            "categorie_fils varchar(20),"+
                                            "PRIMARY KEY(categorie_pere, categorie_fils),"+
                                            "CONSTRAINT pere_parent_de FOREIGN KEY (categorie_pere) REFERENCES Categorie(categorie) ON DELETE CASCADE,"+
                                            "CONSTRAINT fils_parent_de FOREIGN KEY (categorie_fils) REFERENCES Categorie(categorie) ON DELETE CASCADE)");

        /* Créer la table des types de commandes */
        db.sendUpdate("CREATE TABLE TypeCmd(type_cmd varchar(10) PRIMARY KEY CHECK (type_cmd IN ('sur_place', 'a_emporter', 'livraison')))");

        /* Créer la table des statuts des commandes */
        db.sendUpdate("CREATE TABLE Statut(statut varchar(30) PRIMARY KEY CHECK (statut IN ('attente_de_confirmation','validee', 'disponible', 'en_livraison', 'annulee_par_le_client', 'annulee_par_le_restaurant', 'livree')))");

        /* Créer la table des plats */
        db.sendUpdate("CREATE TABLE Plat(id_plat int," +
                                       "email_resto varchar(50)," +
                                       "nom_plat varchar(50) NOT NULL," +
                                       "description_plat varchar(200) NOT NULL," +
                                       "prix float CHECK(prix >= 0) NOT NULL," +
                                       "PRIMARY KEY(id_plat, email_resto)," +
                                       "CONSTRAINT resto_plat FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE)");

        /* Créer la table des commandes */
        db.sendUpdate("CREATE TABLE Commande(id_cmd int PRIMARY KEY," +
                                           "date_cmd date NOT NULL," +
                                           "heure varchar(10) NOT NULL," +
                                           "type_cmd varchar(10) NOT NULL," +
                                           "montant float CHECK(montant >= 0) NOT NULL," +
                                           "statut varchar(30) NOT NULL," +
                                           "CONSTRAINT type_cmd_commande FOREIGN KEY (type_cmd) REFERENCES TypeCmd(type_cmd)," +
                                           "CONSTRAINT statut_commande FOREIGN KEY (statut) REFERENCES Statut(statut))");

        /* Créer la table des commandes en livraison */
        db.sendUpdate("CREATE TABLE Commande_livraison(id_cmd int PRIMARY KEY," +
                                                     "adresse_livraison varchar(100)  NOT NULL," +
                                                     "texte_livraison varchar(200)," +
                                                     "heure_livraison varchar(10) NOT NULL," +
                                                     "CONSTRAINT cmd_livraison FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE)");

        /* Créer la table des commandes sur place */
        db.sendUpdate("CREATE TABLE Commande_sur_place(id_cmd int PRIMARY KEY," +
                                                     "nbr_clients int NOT NULL," +
                                                     "heure_arrivee varchar(10) NOT NULL," +
                                                     "CONSTRAINT cmd_sur_place FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE)");

        /* Créer la table des évaluations */
        db.sendUpdate("CREATE TABLE Eval(id_cmd int PRIMARY KEY,"+
                                       "date_eval date NOT NULL,"+
                                       "heure_eval varchar(10) NOT NULL,"+
                                       "avis varchar(200),"+
                                       "note int CHECK(0 <= note AND note <= 5) NOT NULL,"+
                                       "CONSTRAINT cmd_eval FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE)");

        /* Créer la table des plats contenus dans une commande */
        db.sendUpdate("CREATE TABLE Contient(id_cmd int,"+
                                           "id_plat int,"+
                                           "email_resto varchar(50),"+
                                           "PRIMARY KEY(id_cmd, id_plat, email_resto),"+
                                           "CONSTRAINT cmd_contient FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE,"+
                                           "CONSTRAINT plat_contient FOREIGN KEY (id_plat, email_resto) REFERENCES Plat(id_plat, email_resto) ON DELETE CASCADE)");

        /* Créer la table des types de commandes proposés par un restaurant */
        db.sendUpdate("CREATE TABLE Propose_type(email_resto varchar(50),"+
                                               "type_cmd varchar(20),"+
                                               "PRIMARY KEY(email_resto, type_cmd),"+
                                               "CONSTRAINT resto_propose_type FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE,"+
                                               "CONSTRAINT type_cmd_propose_type FOREIGN KEY (type_cmd) REFERENCES TypeCmd(type_cmd) ON DELETE CASCADE)");

        /* Créer la table des catégories associées aux restaurants */
        db.sendUpdate("CREATE TABLE Specialise_dans(email_resto varchar(50),"+
                                                  "categorie varchar(20),"+
                                                  "PRIMARY KEY(email_resto, categorie),"+
                                                  "CONSTRAINT resto_specialise_dans FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE,"+
                                                  "CONSTRAINT categorie_specialise_dans FOREIGN KEY (categorie) REFERENCES Categorie(categorie) ON DELETE CASCADE)");
        

        /* Créer la table des associations entre un client, une commande et un restaurant */
        db.sendUpdate("CREATE TABLE Associer(id_client int," +
                                           "id_cmd int," +
                                           "email_resto varchar(50)," +
                                           "PRIMARY KEY (id_client, id_cmd, email_resto)," +
                                           "CONSTRAINT client_associer FOREIGN KEY (id_client) REFERENCES Client(id_client) ON DELETE CASCADE," +
                                           "CONSTRAINT cmd_associer FOREIGN KEY (id_cmd) REFERENCES Commande(id_cmd) ON DELETE CASCADE," +
                                           "CONSTRAINT resto_associer FOREIGN KEY (email_resto) REFERENCES Restaurant(email_resto) ON DELETE CASCADE)");

        /* Créer la table des associations entre un plat et les allergènes */
        db.sendUpdate("CREATE TABLE P_a_allergenes(id_plat int," +
                                                 "email_resto varchar(50)," +
                                                 "allergenes varchar(20)," +
                                                 "PRIMARY KEY(id_plat, email_resto, allergenes)," +
                                                 "CONSTRAINT plat_p_a_allergenes FOREIGN KEY (id_plat, email_resto) REFERENCES Plat(id_plat, email_resto) ON DELETE CASCADE," +
                                                 "CONSTRAINT allergenes_p_a_allergenes FOREIGN KEY (allergenes) REFERENCES Allergenes(allergenes) ON DELETE CASCADE)");
    }

    public static void emptyAllTables(DB db) {
        String keyword = "TRUNCATE TABLE ";
        for (String tableName : tablesNames)
            db.sendUpdate(keyword + tableName);
    }

    public static void insererExemples(DB db) {
        ajouterExemplesRestaurants(db);
        ajouterExemplesPlats(db);

        ajouterExemplesTypeCmd(db);

        ajouterExemplesCategories(db);
        ajouterExemplesClients(db);

        ajouterExemplesProposeType(db);
        ajouterExemplesSpecialiseDans(db);

        ajouterExemplesParentDe(db);
    }

    private static void insertionRestaurant(DB db, String emailResto, String nomResto,
                                            String tel, String adresseResto,
                                            String horaireString, int capacite,
                                            String texteResto, double noteResto) {
        String q = String.format("INSERT INTO Restaurant VALUES "
                                +"('%s', '%s', '%s', '%s', '%s', %d, '%s', %f) ",
                                emailResto, nomResto, tel, adresseResto,
                                horaireString, capacite, texteResto, noteResto);
        db.sendUpdate(q);
    }

    private static void insertionPlat(DB db, int idPlat, String emailResto,
                                      String nomPlat, String descriptionPlat,
                                      double prix) {
        String q = String.format("INSERT INTO Plat VALUES "
                            +"(%d, '%s', '%s', '%s', %f) ",
                            idPlat, emailResto, nomPlat, descriptionPlat, prix);
        db.sendUpdate(q);
    }

    // private static void insertionCommande(DB db, int idCmd, String date,
    //                                       String heure, String typeCmd, double montant,
    //                                       String statut) {
    //     String formattedDate = String.format("TO_DATE('%s', 'DD/MM/YYYY')", date);
    //     String q = String.format("INSERT INTO Commande VALUES "
    //                             +"(%d, '%s', %s, '%s', '%s', '%s', %f, '%s') ",
    //                             idCmd, formattedDate, heure, typeCmd, montant, statut);
    //     db.sendUpdate(q);
    // }

    // private static void insertionCommandeLivraison(DB db, int idCmd, String adresseLivraison,
    //                                                String texteLivraison, String heure) {
    //     String q = String.format("INSERT INTO Commande_livraison VALUES "
    //                             +"(%d, '%s', '%s', '%s') ",
    //                             idCmd, adresseLivraison, texteLivraison, heure);
    //     db.sendUpdate(q);
    // }

    // private static void insertionCommandeSurPlace(DB db, int idCmd, int nbrClients,
    //                                              String heureArrivee) {
    //     String q = String.format("INSERT INTO Commande_sur_place VALUES "
    //                             +"(%d,%d, '%s') ",
    //                             idCmd, nbrClients, heureArrivee);
    //     db.sendUpdate(q);
    // }

    private static void insertionTypeCmd(DB db, String type) {
        String q = String.format("INSERT INTO TypeCmd VALUES ('%s') ", type);   
        db.sendUpdate(q);
    }

    // private static void insertionStatut(DB db, String statut) {
    //     String q = String.format("INSERT INTO Statut VALUES ('%s') ", statut);   
    //     db.sendUpdate(q);
    // }

    // private static void insertionEval(DB db, int idCmd, String date,
    //                                     String heure, String avis, double note) {
    //     String formattedDate = String.format("TO_DATE('%s', 'DD/MM/YYYY')", date);
    //     String q = String.format("INSERT INTO Client VALUES "
    //                             +"(%d, '%s', '%s', '%s', %f) ",
    //                             idCmd, formattedDate, heure, avis, note);
    //     db.sendUpdate(q);
    // }

    private static void insertionCategorie(DB db, String categorie) {
        String q = String.format("INSERT INTO Categorie VALUES ('%s') ", categorie);   
        db.sendUpdate(q);
    }

    private static void insertionClient(DB db, int idClient, String emailClient,
                                        String mdp, String nomClient, String prenomClient,
                                        String adresseClient) {
        String q = String.format("INSERT INTO Client VALUES "
                                +"(%d, '%s', '%s', '%s', '%s', '%s') ",
                                idClient, emailClient, mdp, nomClient, prenomClient, adresseClient);            
        db.sendUpdate(q);
    }

    // private static void insertionAllergenes(DB db, String allergene) {
    //     String q = String.format("INSERT INTO Allergenes VALUES ('%s') ", allergene);   
    //     db.sendUpdate(q);
    // }

    // private static void insertionContient(DB db, int idCmd, int idPlat, String emailResto) {
    //     String q = String.format("INSERT INTO Contient VALUES "
    //                             +"(%d, %d, '%s') ",
    //                             idCmd, idPlat, emailResto);
    //     db.sendUpdate(q);
    // }

    private static void insertionProposeType(DB db, String emailResto, String typeCmd) {
        String q = String.format("INSERT INTO Propose_type VALUES ('%s', '%s') ",
                                emailResto, typeCmd);
        db.sendUpdate(q);
    }

    private static void insertionSpecialiseDans(DB db, String emailResto, String categorie) {
        String q = String.format("INSERT INTO Specialise_dans VALUES "
                                +"('%s', '%s') ",
                                emailResto, categorie);
        db.sendUpdate(q);
    }

    private static void insertionParentDe(DB db, String catPere, String catFils) {
        String q = String.format("INSERT INTO Parent_de VALUES ('%s', '%s') ",
                                catPere, catFils);        
        db.sendUpdate(q);
    }

    // private static void insertionPlatAllergenes(DB db, int idPlat, String emailResto, String allergene) {
    //     String q = String.format("INSERT INTO P_a_allergenes VALUES (%d, '%s', '%s') ",
    //                             idPlat, emailResto, allergene);
    //     db.sendUpdate(q);
    // }

    // private static void insertionAssocier(DB db, int idClient, int idCmd, String emailResto) {
    //     String q = String.format("INSERT INTO Associer VALUES (%d, %d, '%s') ",
    //                              idClient, idCmd, emailResto);
    //     db.sendUpdate(q);
    // }



    

    private static void ajouterExemplesRestaurants(DB db) {
        insertionRestaurant(db, "cafet@imag.fr", "Cafet Ensimag",
                            "0438475869", "1 Rue de la Passerelle 38100 SMH",
                            "13-15", 100,
                            "Le meilleur endroit de SMH pour manger le midi.",
                            5);
        insertionRestaurant(db, "intermezzo@crous.fr", "L'Intermezzo",
                            "0414253679", "3 Rue Barnave 38100 SMH",
                            "11:30-14 et 18-19", 120,
                            "Un RU avec un choix incroyable ! (surtout à 11:31)",
                            3);
        insertionRestaurant(db, "chezgino@hotmail.com", "Chez Gino",
                            "0438468219", "6 Rue des Lilas 38000 GRENOBLE",
                            "12-15 et 19-23", 12,
                            "Pizzeria traditionnelle au feu de bois.",
                            4.5);
    }

    private static void ajouterExemplesPlats(DB db) {
        insertionPlat(db, 1, "cafet@imag.fr", "Sandwich SM",
                      "La meilleure inspiration des CafetMasters", 1.50);
        insertionPlat(db, 1, "intermezzo@crous.fr", "Pâtes au pesto",
                      "Des pâtes, de la crème et des petits points verts.", 3.30);
        insertionPlat(db, 1, "chezgino@hotmail.com", "4 Fromages (à fondue)",
                      "Gruyère, Vacherin suisse, Abondance, Beaufort", 13.50);
    }



    private static void ajouterExemplesTypeCmd(DB db) {
        insertionTypeCmd(db, "sur_place");
        insertionTypeCmd(db, "a_emporter");
        insertionTypeCmd(db, "livraison");
    }


    private static void ajouterExemplesCategories(DB db) {
        insertionCategorie(db, "all");
        insertionCategorie(db, "Internationale");
        insertionCategorie(db, "Régionale");
        insertionCategorie(db, "Italienne");
        insertionCategorie(db, "Pizzeria");
        insertionCategorie(db, "Etudiante");
    }

    private static void ajouterExemplesClients(DB db) {
        insertionClient(db, 1, "jean.michel@gmail.com", "michemich@",
                        "JARRE", "Jean-Michel",
                        "3 Rue du Pastaga 38000 GRENOBLE");
        insertionClient(db, 2, "m.mouse@disney.com", "pluto2001",
                        "MOUSE", "Mickey",
                        "59 Boulevard des Airs 38100 GRENOBLE");
        insertionClient(db, 3, "empereur-contact@rome.it", "cleo<3",
                        "CESAR", "Jules",
                        "13 Impasse Maxima 38000 GRENOBLE");
    }
    
    private static void ajouterExemplesProposeType(DB db) {
        insertionProposeType(db, "cafet@imag.fr", "a_emporter");
        insertionProposeType(db, "intermezzo@crous.fr", "sur_place");
        insertionProposeType(db, "intermezzo@crous.fr", "a_emporter");
        insertionProposeType(db, "chezgino@hotmail.com", "a_emporter");
        insertionProposeType(db, "chezgino@hotmail.com", "livraison");
    }

    private static void ajouterExemplesSpecialiseDans(DB db) {
        insertionSpecialiseDans(db, "chezgino@hotmail.com", "Pizzeria");
        insertionSpecialiseDans(db, "cafet@imag.fr", "Etudiante");
        insertionSpecialiseDans(db, "intermezzo@crous.fr", "Etudiante");
    }

    private static void ajouterExemplesParentDe(DB db) {
        insertionParentDe(db, "all", "Internationale");
        insertionParentDe(db, "all", "Régionale");
        insertionParentDe(db, "Internationale", "Italienne");
        insertionParentDe(db, "Italienne", "Pizzeria");
        insertionParentDe(db, "Régionale", "Etudiante");
    }

}