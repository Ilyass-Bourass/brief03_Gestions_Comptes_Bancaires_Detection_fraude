
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
    id_compte INT NOT NULL,
    type_carte ENUM('DEBIT', 'CREDIT') NOT NULL,
    date_expiration DATE NOT NULL,
    code_cvv VARCHAR(3) NOT NULL,
    limite_journaliere DECIMAL(10,2) DEFAULT 1000.00,
    statut ENUM('ACTIVE', 'BLOQUEE', 'EXPIREE') DEFAULT 'ACTIVE',
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_compte) REFERENCES comptes(id_compte)
);

CREATE TABLE transactions (
    id_transaction INT PRIMARY KEY AUTO_INCREMENT,
    id_carte INT NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    type_transaction ENUM('RETRAIT', 'ACHAT', 'VIREMENT') NOT NULL,
    lieu_transaction VARCHAR(200),
    date_transaction DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('REUSSIE', 'ECHOUEE', 'SUSPECTE') DEFAULT 'REUSSIE',
    FOREIGN KEY (id_carte) REFERENCES cartes(id_carte)
);

CREATE TABLE alertes_fraude (
    id_alerte INT PRIMARY KEY AUTO_INCREMENT,
    id_transaction INT NOT NULL,
    type_alerte ENUM('MONTANT_SUSPECT', 'LOCALISATION_INHABITUELLE', 'FREQUENCE_ELEVEE') NOT NULL,
    niveau_risque ENUM('FAIBLE', 'MOYEN', 'ELEVE') NOT NULL,
    description TEXT,
    date_alerte DATETIME DEFAULT CURRENT_TIMESTAMP,
    traite BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_transaction) REFERENCES transactions(id_transaction)
);

INSERT INTO clients (nom, prenom, email, telephone, adresse) VALUES
('Alami', 'Mohammed', 'mohammed.alami@email.com', '0612345678', 'Casablanca, Maroc'),
('Bennani', 'Fatima', 'fatima.bennani@email.com', '0687654321', 'Rabat, Maroc');

INSERT INTO comptes (numero_compte, id_client, type_compte, solde) VALUES
('ACC001234567890', 1, 'COURANT', 5000.00),
('ACC987654321098', 2, 'EPARGNE', 12000.00);

INSERT INTO cartes (numero_carte, id_compte, type_carte, date_expiration, code_cvv, limite_journaliere) VALUES
('1234567890123456', 1, 'DEBIT', '2027-12-31', '123', 2000.00),
('6543210987654321', 2, 'CREDIT', '2026-08-31', '456', 3000.00);