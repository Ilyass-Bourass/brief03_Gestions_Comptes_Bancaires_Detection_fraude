package service;

import dao.CompteDao;
import dao.ClientDAO;
import entity.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CompteService {

    private CompteDao compteDao;
    private ClientDAO clientDAO;
    private Random random;

    public CompteService() {
        this.compteDao = new CompteDao();
        this.clientDAO = new ClientDAO();
        this.random = new Random();
    }

    public boolean creerCompte(int idClient, TypeCompte typeCompte, double soldeInitial) {
        try {
            // Vérifier que le client existe
            Optional<Client> clientOpt = clientDAO.trouverParId(idClient);
            if (clientOpt.isEmpty()) {
                System.err.println("❌ Client introuvable avec l'ID : " + idClient);
                return false;
            }

            String numeroCompte = genererNumeroCompte();

            Compte compte = new Compte(numeroCompte, idClient, typeCompte, soldeInitial);

            boolean resultat = compteDao.creer(compte);

            if (resultat) {
                System.out.println("✅ Compte créé avec succès !");
                System.out.println("   Numéro : " + numeroCompte);
                System.out.println("   Type : " + typeCompte);
                System.out.println("   Solde initial : " + soldeInitial + " MAD");
            }

            return resultat;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création : " + e.getMessage());
            return false;
        }
    }

    public void afficherComptesClient(int idClient) {
        // Vérifier que le client existe
        Optional<Client> clientOpt = clientDAO.trouverParId(idClient);
        if (clientOpt.isEmpty()) {
            System.err.println("❌ Client introuvable avec l'ID : " + idClient);
            return;
        }

        Client client = clientOpt.get();
        List<Compte> comptes = compteDao.trouverParClient(idClient);

        if (comptes.isEmpty()) {
            System.out.println("\n❌ Aucun compte trouvé pour " + client.nomComplet());
            return;
        }

        System.out.println("\n🏦 Comptes de " + client.nomComplet() + " :");
        System.out.println("=".repeat(80));

        for (Compte compte : comptes) {
            System.out.printf("ID: %d | Numéro: %s | Type: %s | Solde: %.2f MAD | Statut: %s%n",
                compte.idCompte(),
                compte.numeroCompte(),
                compte.typeCompte(),
                compte.solde(),
                compte.statut());
            System.out.println("-".repeat(80));
        }
    }

    private String genererNumeroCompte() {
        StringBuilder sb = new StringBuilder("ACC");
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
