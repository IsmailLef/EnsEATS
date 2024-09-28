package deprecated;
import database.DB;

public class insertDB {

    public static void insertionRestaurant(DB db, String emailResto, String nomResto,
                                        String tel, String adresseResto, String horaireString,
                                        String capacite, String texteResto, double noteResto) {

            String query = "INSERT INTO Restaurant VALUES ( '" + emailResto + "', '" + nomResto + "', '" + 
                                                tel + "', '" + adresseResto + "', '" + horaireString + "', '" + 
                                                capacite + "', '" + texteResto + "', '" + noteResto + "')";
            
            db.sendUpdate(query);
    }


    public static void insertionPlat(DB db, int idPlat, String emailResto,
                                        String nomPlat, String descriptionPlat, double prix) {

            String query = "INSERT INTO Plat VALUES ( '" + idPlat + "', '" + emailResto + "', '" + 
                                                nomPlat + "', '" + descriptionPlat + "', '" + prix + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionCommande(DB db, int idCmd, String date,
                                        String heure, String typeCmd, double montant,
                                        String statut) {

            String query = "INSERT INTO Commande VALUES ( '" + idCmd + "', " + "TO_DATE('" + date + "', 'DD/MM/YYYY')" + ", '" + 
                                                heure + "', '" + typeCmd + "', '" + montant + "', '" + 
                                                statut + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionCommandeLivraison(DB db, int idCmd, String adresseLivraison,
                                        String texteLivraison, String heure) {

            String query = "INSERT INTO Commande_livraison VALUES ( '" + idCmd + "', '" + adresseLivraison + "', '" + 
                                                texteLivraison + "', '" + heure + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionCommandeSurPlace(DB db, int idCmd, int nbrClients,
                                        String heureArrivee) {

            String query = "INSERT INTO Commande_sur_place VALUES ( '" + idCmd + "', '" + nbrClients + "', '" + 
                                                heureArrivee + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionTypeCmd(DB db, String type) {

            String query = "INSERT INTO TypeCmd VALUES ( '" + type + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionStatut(DB db, String statut) {

        String query = "INSERT INTO Statut VALUES ( '" + statut + "')";
        
        db.sendUpdate(query);
    }

    public static void insertionEval(DB db, int idCmd, String date,
                                        String heure, String avis, double note) {

            String query = "INSERT INTO Eval VALUES ( '" + idCmd + "', " + "TO_DATE('" + date + "', 'DD/MM/YYYY')" + ", '" + 
                                                heure + "', '" + avis + "', '" + note + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionCategorie(DB db, String categorie) {

        String query = "INSERT INTO Categorie VALUES ( '" + categorie + "')";
        
        db.sendUpdate(query);
    }

    public static void insertionClient(DB db, int idClient, String emailClient,
                                        String mdp, String nomClient, String prenomClient,
                                        String adresseClient) {

            String query = "INSERT INTO Client VALUES ( '" + idClient + "', '" + emailClient + "', '" + 
                                                mdp + "', '" + nomClient + "', '" + prenomClient
                                                + "', '" + adresseClient + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionAllergenes(DB db, String allergene) {

        String query = "INSERT INTO Allergenes VALUES ( '" + allergene + "')";
        
        db.sendUpdate(query);
    }

    public static void insertionContient(DB db, int idCmd, int idPlat, String emailResto) {

            String query = "INSERT INTO Contient VALUES ( '" + idCmd + "', '" + idPlat + "', '" + 
                                                emailResto + "')";
            
            db.sendUpdate(query);
    }

    public static void insertionProposeType(DB db, String emailResto, String typeCmd) {

        String query = "INSERT INTO Propose_type VALUES ( '" + emailResto + "', '" + typeCmd + "')";
        
        db.sendUpdate(query);
    }

    public static void insertionSpecialiseDans(DB db, String emailResto, String categorie) {

        String query = "INSERT INTO Specialise_dans VALUES ( '" + emailResto + "', '" + categorie + "')";
        
        db.sendUpdate(query);
    }

    public static void insertionParentDe(DB db, String catPere, String catFils) {

        String query = "INSERT INTO Parent_de VALUES ( '" + catPere + "', '" + catFils + "')";
        
        db.sendUpdate(query);
    }

    public static void insertionPlatAllergenes(DB db, int idPlat, String emailResto, String allergene) {

        String query = "INSERT INTO P_a_allergenes VALUES ( '" + idPlat + "', '" + emailResto + "', '" + 
                                            allergene + "')";
        
        db.sendUpdate(query);
    }

    public static void insertionAssocier(DB db, int idClient, int idCmd, String emailResto) {

        String query = "INSERT INTO Associer VALUES ( '" + idClient + "', '" + idCmd + "', '" + 
                                            emailResto + "')";
        
        db.sendUpdate(query);
    }
}
