package service;

import dao.ClientDAO;
import entity.Client;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ClientService {
    
    private ClientDAO clientDAO;
    
    public ClientService() {
        this.clientDAO = new ClientDAO();
    }
    
    public boolean creerClient(String nom, String prenom, String email, String telephone, String adresse) {
        try {
            if (clientDAO.emailExiste(email)) {
                System.err.println("❌ Un client avec cet email existe déjà : " + email);
                return false;
            }
            
            Client nouveauClient = new Client(nom, prenom, email, telephone, adresse);
            
            boolean resultat = clientDAO.creer(nouveauClient);
            
            if (resultat) {
                System.out.println("✅ Client créé avec succès : " + nouveauClient.nomComplet());
            }
            
            return resultat;
            
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Données invalides : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création : " + e.getMessage());
            return false;
        }
    }
    

    
    public List<Client> obtenirTousLesClients() {
        return clientDAO.listerTous();
    }
    
    public Optional<Client> obtenirClientParId(int id) {
        return clientDAO.trouverParId(id);
    }
    
    public Optional<Client> obtenirClientParEmail(String email) {
        return clientDAO.trouverParEmail(email);
    }
    
    public List<Client> rechercherClients(String recherche) {
        return clientDAO.rechercherParNom(recherche);
    }
    
    public void afficherTousLesClients() {
        List<Client> clients = clientDAO.listerTous();
        
        if (clients.isEmpty()) {
            System.out.println("📋 Aucun client trouvé.");
            return;
        }
        
        System.out.println("\n📋 Liste des clients (" + clients.size() + ") :");
        System.out.println("=" .repeat(60));
        
        for (Client client : clients) {
            System.out.printf("ID: %d | %s | %s | %s%n", 
                client.idClient(),
                client.nomComplet(),
                client.email(),
                client.telephone()
            );
        }
        System.out.println("=" .repeat(60));
    }
    
    public int compterClients() {
        return obtenirTousLesClients().size();
    }
    
    
}