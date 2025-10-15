package entity;

import java.time.LocalDate;

public final class CarteDebit extends Carte {
    private double plafondJournalier;

    public CarteDebit(int idCarte, String numeroCarte, LocalDate dateExpiration, StatutCarte statut, int idClient, double plafondJournalier) {
        super(idCarte, numeroCarte, dateExpiration, statut, idClient);
        this.plafondJournalier = plafondJournalier;
    }

    public CarteDebit(String numeroCarte, LocalDate dateExpiration, int idClient, double plafondJournalier) {
        super(numeroCarte, dateExpiration, idClient);
        this.plafondJournalier = plafondJournalier;
    }

    public double getPlafondJournalier() {
        return plafondJournalier;
    }

    public void setPlafondJournalier(double plafondJournalier) {
        this.plafondJournalier = plafondJournalier;
    }

    @Override
    public String getTypeCarte() {
        return "DEBIT";
    }
}

