package entity;

import java.time.LocalDateTime;

public record AlerteFraude(
    int idAlerte,
    String description,
    NiveauAlerte niveau,
    int idCarte,
    LocalDateTime dateAlerte
) {
    public AlerteFraude {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La description ne peut pas être vide");
        }
        if (niveau == null) {
            throw new IllegalArgumentException("Le niveau d'alerte est obligatoire");
        }
    }

    public AlerteFraude(String description, NiveauAlerte niveau, int idCarte) {
        this(0, description, niveau, idCarte, LocalDateTime.now());
    }
}




