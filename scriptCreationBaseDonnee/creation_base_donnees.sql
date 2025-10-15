CREATE DATABASE IF NOT EXISTS gestion_cartes_bancaires;
USE gestion_cartes_bancaires;

CREATE TABLE clients (
    id_client INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    adresse TEXT,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comptes (
    id_compte INT PRIMARY KEY AUTO_INCREMENT,
    numero_compte VARCHAR(20) UNIQUE NOT NULL,
    id_client INT NOT NULL,
    type_compte ENUM('COURANT', 'EPARGNE') NOT NULL,
    solde DECIMAL(15,2) DEFAULT 0.00,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('ACTIF', 'SUSPENDU', 'FERME') DEFAULT 'ACTIF',
    FOREIGN KEY (id_client) REFERENCES clients(id_client)
);

CREATE TABLE cartes (
    id_carte INT PRIMARY KEY AUTO_INCREMENT,
    numero_carte VARCHAR(16) UNIQUE NOT NULL,
    date_expiration DATE NOT NULL,
    statut ENUM('ACTIVE', 'SUSPENDUE', 'BLOQUEE', 'EXPIREE') DEFAULT 'ACTIVE',
    type_carte ENUM('DEBIT', 'CREDIT', 'PREPAYEE') NOT NULL,
    id_client INT NOT NULL,
    plafond_journalier DECIMAL(10,2) NULL,
    plafond_mensuel DECIMAL(10,2) NULL,
    taux_interet DECIMAL(5,2) NULL,
    solde_disponible DECIMAL(10,2) NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_client) REFERENCES clients(id_client)
);

CREATE TABLE operations_carte (
    id_operation INT PRIMARY KEY AUTO_INCREMENT,
    date DATETIME NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    type ENUM('ACHAT', 'RETRAIT', 'PAIEMENT_EN_LIGNE') NOT NULL,
    lieu VARCHAR(200),
    id_carte INT NOT NULL,
    FOREIGN KEY (id_carte) REFERENCES cartes(id_carte)
);

CREATE TABLE alertes_fraude (
    id_alerte INT PRIMARY KEY AUTO_INCREMENT,
    description TEXT NOT NULL,
    niveau ENUM('INFO', 'AVERTISSEMENT', 'CRITIQUE') NOT NULL,
    id_carte INT NOT NULL,
    date_alerte DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_carte) REFERENCES cartes(id_carte)
);

-- Données de test
INSERT INTO clients (nom, prenom, email, telephone, adresse) VALUES
('Alami', 'Mohammed', 'mohammed.alami@email.com', '0612345678', 'Casablanca, Maroc'),
('Bennani', 'Fatima', 'fatima.bennani@email.com', '0687654321', 'Rabat, Maroc');

INSERT INTO comptes (numero_compte, id_client, type_compte, solde) VALUES
('ACC001234567890', 1, 'COURANT', 5000.00),
('ACC987654321098', 2, 'EPARGNE', 12000.00);
