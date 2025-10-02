package configDatabase;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnexionDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_cartes_bancaires";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    
    // Instance unique de la connexion (Singleton)
    private static Connection instance = null;

    // Constructeur privé pour empêcher l'instanciation
    private ConnexionDatabase() {}

    public static Connection getConnection() {
        // Si la connexion n'existe pas ou est fermée, on en crée une nouvelle
        try {
            if (instance == null || instance.isClosed()) {
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Nouvelle connexion à la base de données créée");
            } else {
                System.out.println("♻️ Connexion existante réutilisée");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la connexion à la base de données :");
            System.err.println("   - Message: " + e.getMessage());
            System.err.println("   - Code erreur: " + e.getErrorCode());
            System.err.println("   - Vérifiez que MySQL est démarré et que la base existe");
            return null;
        }
        
        return instance;
    }
    
    // Méthode pour fermer la connexion proprement
    public static void fermerConnexion() {
        try {
            if (instance != null && !instance.isClosed()) {
                instance.close();
                instance = null;
                System.out.println("🔒 Connexion fermée");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la fermeture : " + e.getMessage());
        }
    }
    
    // Méthode pour vérifier l'état de la connexion
    public static boolean estConnecte() {
        try {
            return instance != null && !instance.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
