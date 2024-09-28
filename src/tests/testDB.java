package tests;
import database.DB;

public class testDB {
    
    public static void main(String[] args) {
        DB db = new DB();
        db.closeConnection();
    }
}
