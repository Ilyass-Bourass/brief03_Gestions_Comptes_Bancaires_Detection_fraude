import configDatabase.ConnexionDatabase;
import service.ClientService;
import entity.Client;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("🏦 === Application Gestion Cartes Bancaires ===");
        
        Connection conn = ConnexionDatabase.getConnection();
        if (conn == null) {
            System.err.println("❌ Impossible de se connecter à la base de données !");
            return;
        }
        System.out.println("✅ Connexion à la base de données réussie !");
        
        ClientService clientService = new ClientService();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            afficherMenu();
            System.out.print("Choisissez une option : ");
            
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            switch (choix) {
                case 1:
                    creerNouveauClient(clientService, scanner);
                    break;
                case 2:
                    clientService.afficherTousLesClients();
                    break;
                case 3:
                    System.out.println("Créer exepmle de client");
                    break;
                case 4:
                    rechercherClient(clientService, scanner);
                    break;
                case 0:
                    System.out.println("👋 Au revoir !");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Option invalide !");
            }
        }
    }
    
    private static void afficherMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📋 MENU PRINCIPAL");
        System.out.println("=".repeat(40));
        System.out.println("1. Créer un nouveau client");
        System.out.println("2. Afficher tous les clients");
        System.out.println("3. Créer des clients d'exemple");
        System.out.println("4. Rechercher un client");
        System.out.println("0. Quitter");
        System.out.println("=".repeat(40));
    }
    
    private static void creerNouveauClient(ClientService service, Scanner scanner) {
        System.out.println("\n📝 Création d'un nouveau client");
        System.out.println("-".repeat(30));
        
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        
        System.out.print("Email : ");
        String email = scanner.nextLine();
        
        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();
        
        System.out.print("Adresse : ");
        String adresse = scanner.nextLine();
        
        boolean resultat = service.creerClient(nom, prenom, email, telephone, adresse);
        
        if (resultat) {
            System.out.println("✅ Client créé avec succès !");
        } else {
            System.out.println("❌ Échec de la création du client.");
        }
    }
    
    private static void rechercherClient(ClientService service, Scanner scanner) {
        System.out.println("\n🔍 Recherche de client");
        System.out.println("-".repeat(20));
        System.out.print("Entrez un nom ou prénom : ");
        String recherche = scanner.nextLine();
        
        var clients = service.rechercherClients(recherche);
        
        if (clients.isEmpty()) {
            System.out.println("❌ Aucun client trouvé.");
        } else {
            System.out.println("✅ Clients trouvés :");
            for (Client client : clients) {
                System.out.printf("- %s (%s)%n", client.nomComplet(), client.email());
            }
        }
    }
}