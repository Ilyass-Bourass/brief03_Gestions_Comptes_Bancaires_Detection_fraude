package entity;

import java.time.LocalDate;

public final class CarteCredit extends Carte {
    private double plafondMensuel;
    private double tauxInteret;

    public CarteCredit(int idCarte, String numeroCarte, LocalDate dateExpiration, StatutCarte statut, int idClient, double plafondMensuel, double tauxInteret) {
        super(idCarte, numeroCarte, dateExpiration, statut, idClient);
        this.plafondMensuel = plafondMensuel;
        this.tauxInteret = tauxInteret;
    }

    public CarteCredit(String numeroCarte, LocalDate dateExpiration, int idClient, double plafondMensuel, double tauxInteret) {
        super(numeroCarte, dateExpiration, idClient);
        this.plafondMensuel = plafondMensuel;
        this.tauxInteret = tauxInteret;
    }

    public double getPlafondMensuel() {
        return plafondMensuel;
    }

    public void setPlafondMensuel(double plafondMensuel) {
        this.plafondMensuel = plafondMensuel;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    @Override
    public String getTypeCarte() {
        return "CREDIT";
    }
}

