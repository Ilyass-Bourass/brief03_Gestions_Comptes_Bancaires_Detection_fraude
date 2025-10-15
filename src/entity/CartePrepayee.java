package entity;

import java.time.LocalDate;

public final class CartePrepayee extends Carte {
    private double soldeDisponible;

    public CartePrepayee(int idCarte, String numeroCarte, LocalDate dateExpiration, StatutCarte statut, int idClient, double soldeDisponible) {
        super(idCarte, numeroCarte, dateExpiration, statut, idClient);
        this.soldeDisponible = soldeDisponible;
    }

    public CartePrepayee(String numeroCarte, LocalDate dateExpiration, int idClient, double soldeDisponible) {
        super(numeroCarte, dateExpiration, idClient);
        this.soldeDisponible = soldeDisponible;
    }

    public double getSoldeDisponible() {
        return soldeDisponible;
    }

    public void setSoldeDisponible(double soldeDisponible) {
        this.soldeDisponible = soldeDisponible;
    }

    public void crediter(double montant) {
        if (montant > 0) {
            this.soldeDisponible += montant;
        }
    }

    public boolean debiter(double montant) {
        if (montant > 0 && soldeDisponible >= montant) {
            this.soldeDisponible -= montant;
            return true;
        }
        return false;
    }

    @Override
    public String getTypeCarte() {
        return "PREPAYEE";
    }
}

