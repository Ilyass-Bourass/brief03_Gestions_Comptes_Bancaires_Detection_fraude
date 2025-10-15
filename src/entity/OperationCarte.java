package entity;

import java.time.LocalDateTime;

public record OperationCarte(
    int idOperation,
    LocalDateTime date,
    double montant,
    TypeOperation type,
    String lieu,
    int idCarte
) {
    public OperationCarte {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        if (type == null) {
            throw new IllegalArgumentException("Le type d'opération est obligatoire");
        }
    }

    public OperationCarte(double montant, TypeOperation type, String lieu, int idCarte) {
        this(0, LocalDateTime.now(), montant, type, lieu, idCarte);
    }
}

