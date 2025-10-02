package configDatabase;

import java.sql.DriverManager;
import java.sql.Connection;


public class ConnexionDatabase {

    private static final String url = "jdbc:mysql://localhost:3306/gestion_cartes_bancaires";
    private static final String user = "root";
    private static final String password = "12345";

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connexion reussie");
        }catch (Exception e){
            System.out.println("Error connecting to database"+e.getMessage());
        }
        return conn;
    }

}
