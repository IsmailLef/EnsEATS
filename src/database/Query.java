package database;
import java.sql.SQLException;
import java.sql.ResultSet;


public class Query {

    // Queries for 'ConnexionPage' -------------------------------------------

    public static ResultSet getPassword(String email, DB db) {
        String q = String.format("SELECT DISTINCT mdp "
                                +"FROM Client "
                                +"WHERE email_client='%s'",
                                  email);
        return db.sendQuery(q);
    }

    // Queries for 'AccueilPage' ---------------------------------------------

    public static ResultSet getMainCategory(DB db) throws SQLException {
        String q = "(SELECT * FROM Categorie)" 
                   + "minus" 
                   + "(SELECT categorie_fils FROM Parent_de)";

        return db.sendQuery(q);
    }

    public static ResultSet getSubCategory(String parentCategory, DB db) throws SQLException {
        String q = String.format("SELECT DISTINCT categorie_fils "
                                +"FROM Parent_de "
                                +"WHERE categorie_pere='%s'",
                                parentCategory);
        return db.sendQuery(q);
    }

    public static ResultSet getParentCategory(String subCategory, DB db) throws SQLException {
        String q = String.format("SELECT categorie_pere "
                                +"FROM Parent_de "
                                +"WHERE categorie_fils='%s'",
                                subCategory);
        return db.sendQuery(q);
    }

    public static ResultSet getRestaurants(String category, DB db) throws SQLException {
        String q;
        if (category != null) {
            q = String.format("SELECT nom_resto "
                                    +"FROM Restaurant "
                                    +"WHERE email_resto IN "
                                    +   "(SELECT email_resto "
                                    +    "FROM Specialise_dans "
                                    +    "WHERE categorie='%s')",
                                    category);
        }
        else {  // No constraint is set on category : Retrieving all restaurants.
            q = "SELECT nom_resto FROM Restaurant";
        }
        return db.sendQuery(q);
    }

    public static ResultSet getDescription(String nomResto, DB db) throws SQLException {
        String q = String.format("SELECT texte_resto FROM Restaurant "
                                +"WHERE nom_resto ='%s')",
                                  nomResto);
        return db.sendQuery(q);
    }

    /*
     * Supprimer les données personnelles d'un client.
     */
    public static int supprimerClient(String email, DB db) throws SQLException {
        String q = String.format("UPDATE Client "
                                +"SET email_client='', "
                                +"mdp='', "
                                +"nom_client='', "
                                +"prenom_client='', "
                                +"adresse_client='' "
                                +"WHERE email_client='%s'",
                                email);
        return db.sendUpdate(q);
    }

    /*
     * Requête pour récupérer les anciennes commandes d'un client.
     */
    public static ResultSet getCommandeClient(String email, DB db) throws SQLException {
        String q_id = String.format("SELECT id_client "
                                    +"FROM Client "
                                    +"WHERE email_client='%s'",
                                    email);
        int id_client = db.sendQuery(q_id).getInt(0);
        String q = String.format("SELECT * "
                                +"FROM Commande "
                                +"WHERE id_cmd IN "
                                +   "(SELECT id_cmd "
                                +    "FROM Associer "
                                +    "WHERE id_client='%s')",
                                id_client);
        return db.sendQuery(q);
    }

    // Queries for 'CommandePage' ---------------------------------------------
    
    public static int getCapacity(String email_resto, DB db) throws SQLException {
        String q = String.format("SELECT DISTINCT capacite "
                                +"FROM Restaurants "
                                +"WHERE email_resto='%s')",
                                  email_resto);
        try {
            return db.sendQuery(q).getInt(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ResultSet getTypesCmdResto(String email_resto, DB db) throws SQLException {
        String q = String.format("SELECT type_cmd "
                                +"FROM Propose_type "
                                +"WHERE email_resto='%s')",
                                  email_resto);
        return db.sendQuery(q);
    }

    public static ResultSet getPlatsResto(String email_resto, DB db) throws SQLException {
        String q = String.format("SELECT * "
                                +"FROM Plat "
                                +"WHERE email_resto='%s')",
                                email_resto);
        return db.sendQuery(q);
    }
}
