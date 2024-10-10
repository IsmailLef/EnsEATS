package database;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.ResultSet;


public class DB {
    private Connection connection;

    public DB() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connection = establishConnection();
    }

    /**
     * Send a query to database and get answer.
     * @param query
     * @return Answer to the query
     */
    public ResultSet sendQuery(String query) {
        try {
            Statement stmt = this.connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Send an update to database and get answer.
     * @param query
     * @return Number of modified tuple by the update
     */
    public int sendUpdate(String query) {
        try {
            Statement stmt = this.connection.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Commit the current transaction.
     */
    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback to the last commit and cancel current transaction.
     */
    public void rollback() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rollback to the specified SavePoint and cancel current transaction.
     * @param sp SavePoint to rollback to
     */
    public void rollback(Savepoint sp) {
        try {
            this.connection.rollback(sp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a SavePoint with specified name.
     * @param name Name of SavePoint
     * @return Requested SavePoint
     */
    public Savepoint savepoint(String name) {
        try {
            return this.connection.setSavepoint(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Establish a connection to Oracle1 DataBase at Ensimag.
     * <p><i>Please note that this code is not secure, as it uses
     * a student's ID and password in plain.</i>
     * @return Connection object to the DataBase with souteyrj ID.
     */
    private Connection establishConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
            String user = "souteyrj";
            String passwd = user;
            connection = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connected to DataBase.");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close the connection previously established.
     */
    public void closeConnection() {
        try {
            this.connection.close ();
            System.out.println("Connection to DataBase closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
