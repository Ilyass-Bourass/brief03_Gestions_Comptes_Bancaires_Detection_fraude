package entity;

import java.time.LocalDate;

public sealed abstract class Carte permits CarteDebit, CarteCredit, CartePrepayee {
    private int idCarte;
    private String numeroCarte;
    private LocalDate dateExpiration;
    private StatutCarte statut;
    private int idClient;

    public Carte(int idCarte, String numeroCarte, LocalDate dateExpiration, StatutCarte statut, int idClient) {
        this.idCarte = idCarte;
        this.numeroCarte = numeroCarte;
        this.dateExpiration = dateExpiration;
        this.statut = statut;
        this.idClient = idClient;
    }

    public Carte(String numeroCarte, LocalDate dateExpiration, int idClient) {
        this(0, numeroCarte, dateExpiration, StatutCarte.ACTIVE, idClient);
    }

    // Getters
    public int getIdCarte() {
        return idCarte;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public StatutCarte getStatut() {
        return statut;
    }

    public int getIdClient() {
        return idClient;
    }

    // Setters
    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }

    public void setStatut(StatutCarte statut) {
        this.statut = statut;
    }

    public abstract String getTypeCarte();

    public boolean estActive() {
        return statut == StatutCarte.ACTIVE && dateExpiration.isAfter(LocalDate.now());
    }

    public void activer() {
        this.statut = StatutCarte.ACTIVE;
    }

    public void bloquer() {
        this.statut = StatutCarte.BLOQUEE;
    }

    public void suspendre() {
        this.statut = StatutCarte.SUSPENDUE;
    }
}

