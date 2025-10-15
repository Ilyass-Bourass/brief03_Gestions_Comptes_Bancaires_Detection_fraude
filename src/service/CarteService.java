package service;

import dao.CarteDao;
import dao.ClientDAO;
import entity.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

public class CarteService {

    private CarteDao carteDao;
    private ClientDAO clientDAO;
    private Random random;

    public CarteService() {
        this.carteDao = new CarteDao();
        this.clientDAO = new ClientDAO();
        this.random = new Random();
    }

    public boolean creerCarteDebit(int idClient, double plafondJournalier) {
        try {
            Optional<Client> clientOpt = clientDAO.trouverParId(idClient);
            if (clientOpt.isEmpty()) {
                System.err.println("❌ Client introuvable avec l'ID : " + idClient);
                return false;
            }

            String numeroCarte = genererNumeroCarte();
            LocalDate dateExpiration = LocalDate.now().plusYears(3);

            CarteDebit carte = new CarteDebit(numeroCarte, dateExpiration, idClient, plafondJournalier);

            boolean resultat = carteDao.creer(carte);

            if (resultat) {
                System.out.println("✅ Carte débit créée avec succès !");
                System.out.println("   Numéro : " + numeroCarte);
                System.out.println("   Plafond journalier : " + plafondJournalier + " MAD");
            }

            return resultat;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création : " + e.getMessage());
            return false;
        }
    }

    public boolean creerCarteCredit(int idClient, double plafondMensuel, double tauxInteret) {
        try {
            Optional<Client> clientOpt = clientDAO.trouverParId(idClient);
            if (clientOpt.isEmpty()) {
                System.err.println("❌ Client introuvable avec l'ID : " + idClient);
                return false;
            }

            String numeroCarte = genererNumeroCarte();
            LocalDate dateExpiration = LocalDate.now().plusYears(3);

            CarteCredit carte = new CarteCredit(numeroCarte, dateExpiration, idClient, plafondMensuel, tauxInteret);

            boolean resultat = carteDao.creer(carte);

            if (resultat) {
                System.out.println("✅ Carte crédit créée avec succès !");
                System.out.println("   Numéro : " + numeroCarte);
                System.out.println("   Plafond mensuel : " + plafondMensuel + " MAD");
                System.out.println("   Taux d'intérêt : " + tauxInteret + "%");
            }

            return resultat;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création : " + e.getMessage());
            return false;
        }
    }

    public boolean creerCartePrepayee(int idClient, double soldeInitial) {
        try {
            Optional<Client> clientOpt = clientDAO.trouverParId(idClient);
            if (clientOpt.isEmpty()) {
                System.err.println("❌ Client introuvable avec l'ID : " + idClient);
                return false;
            }

            String numeroCarte = genererNumeroCarte();
            LocalDate dateExpiration = LocalDate.now().plusYears(2);

            CartePrepayee carte = new CartePrepayee(numeroCarte, dateExpiration, idClient, soldeInitial);

            boolean resultat = carteDao.creer(carte);

            if (resultat) {
                System.out.println("✅ Carte prépayée créée avec succès !");
                System.out.println("   Numéro : " + numeroCarte);
                System.out.println("   Solde initial : " + soldeInitial + " MAD");
            }

            return resultat;

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création : " + e.getMessage());
            return false;
        }
    }

    private String genererNumeroCarte() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
