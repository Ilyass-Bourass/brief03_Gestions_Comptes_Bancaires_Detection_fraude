package dao;

import entity.*;
import configDatabase.ConnexionDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<Carte> trouverParClient(int idClient) {
        List<Carte> cartes = new ArrayList<>();
        String sql = "SELECT * FROM cartes WHERE id_client = ?";
        Connection connection = ConnexionDatabase.getConnection();

        if (connection == null) {
            return cartes;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cartes.add(mapResultSetToCarte(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la recherche : " + e.getMessage());
        }

        return cartes;
    }

    private Carte mapResultSetToCarte(ResultSet rs) throws SQLException {
        int idCarte = rs.getInt("id_carte");
        String numeroCarte = rs.getString("numero_carte");
        LocalDate dateExpiration = rs.getDate("date_expiration").toLocalDate();
        StatutCarte statut = StatutCarte.valueOf(rs.getString("statut"));
        int idClient = rs.getInt("id_client");
        String typeCarte = rs.getString("type_carte");

        return switch (typeCarte) {
            case "DEBIT" -> new CarteDebit(idCarte, numeroCarte, dateExpiration, statut, idClient,
                    rs.getDouble("plafond_journalier"));
            case "CREDIT" -> new CarteCredit(idCarte, numeroCarte, dateExpiration, statut, idClient,
                    rs.getDouble("plafond_mensuel"), rs.getDouble("taux_interet"));
            case "PREPAYEE" -> new CartePrepayee(idCarte, numeroCarte, dateExpiration, statut, idClient,
                    rs.getDouble("solde_disponible"));
            default -> throw new SQLException("Type de carte inconnu : " + typeCarte);
        };
    }
}
