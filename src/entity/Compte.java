package entity;

import java.time.LocalDateTime;

public record Compte(
    int idCompte,
    String numeroCompte,
    int idClient,
    TypeCompte typeCompte,
    double solde,
    LocalDateTime dateCreation,
    StatutCompte statut
) {
    public Compte {
        if (numeroCompte == null || numeroCompte.trim().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de compte ne peut pas être vide");
        }
        if (typeCompte == null) {
            throw new IllegalArgumentException("Le type de compte est obligatoire");
        }
        if (solde < 0) {
            throw new IllegalArgumentException("Le solde ne peut pas être négatif");
        }
    }

    public Compte(String numeroCompte, int idClient, TypeCompte typeCompte, double solde) {
        this(0, numeroCompte, idClient, typeCompte, solde, LocalDateTime.now(), StatutCompte.ACTIF);
    }
}
