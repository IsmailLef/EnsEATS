package tests;
import database.DB;
import database.Init;

public class testInit {
    
    public static void main(String[] args) {
        DB db = new DB();

        Init.emptyAllTables(db);
        // Init.creerTables(db);
        Init.insererExemples(db);

        db.closeConnection();
    }
}
