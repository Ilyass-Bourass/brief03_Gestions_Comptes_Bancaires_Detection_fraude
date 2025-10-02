package entity;

import java.time.LocalDateTime;

public record Client(
    int idClient,
    String nom,
    String prenom,
    String email,
    String telephone,
    String adresse,
    LocalDateTime dateCreation
) {
    
    // Constructeur compact simple
    public Client {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide");
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }
    }
    
    // Constructeur de convenance sans ID
    public Client(String nom, String prenom, String email, String telephone, String adresse) {
        this(0, nom, prenom, email, telephone, adresse, LocalDateTime.now());
    }
    
    // Méthode utilitaire
    public String nomComplet() {
        return prenom + " " + nom;
    }
}