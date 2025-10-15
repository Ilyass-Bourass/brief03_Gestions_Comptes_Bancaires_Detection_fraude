package dao;

import entity.*;
import configDatabase.ConnexionDatabase;

import java.sql.*;
import java.time.LocalDate;

public class CarteDao {

    public boolean creer(Carte carte) {
        String sql = "INSERT INTO cartes (numero_carte, date_expiration, statut, type_carte, id_client, plafond_journalier, plafond_mensuel, taux_interet, solde_disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = ConnexionDatabase.getConnection();

        if (connection == null) {
            System.err.println("❌ Impossible de se connecter à la base de données");
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, carte.getNumeroCarte());
            ps.setDate(2, Date.valueOf(carte.getDateExpiration()));
            ps.setString(3, carte.getStatut().name());
            ps.setString(4, carte.getTypeCarte());
            ps.setInt(5, carte.getIdClient());

            if (carte instanceof CarteDebit carteDebit) {
                ps.setDouble(6, carteDebit.getPlafondJournalier());
                ps.setNull(7, Types.DOUBLE);
                ps.setNull(8, Types.DOUBLE);
                ps.setNull(9, Types.DOUBLE);
            } else if (carte instanceof CarteCredit carteCredit) {
                ps.setNull(6, Types.DOUBLE);
                ps.setDouble(7, carteCredit.getPlafondMensuel());
                ps.setDouble(8, carteCredit.getTauxInteret());
                ps.setNull(9, Types.DOUBLE);
            } else if (carte instanceof CartePrepayee cartePrepayee) {
                ps.setNull(6, Types.DOUBLE);
                ps.setNull(7, Types.DOUBLE);
                ps.setNull(8, Types.DOUBLE);
                ps.setDouble(9, cartePrepayee.getSoldeDisponible());
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    carte.setIdCarte(rs.getInt(1));
                }
                return true;
            }

            return false;

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la création de la carte : " + e.getMessage());
            return false;
        }
    }
}
