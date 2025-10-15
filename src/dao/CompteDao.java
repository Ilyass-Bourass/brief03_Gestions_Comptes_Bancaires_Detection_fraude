package dao;

import entity.Compte;
import entity.TypeCompte;
import entity.StatutCompte;
import configDatabase.ConnexionDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDao {

    public boolean creer(Compte compte) {
        String sql = "INSERT INTO comptes (numero_compte, id_client, type_compte, solde, date_creation, statut) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = ConnexionDatabase.getConnection();

        if (connection == null) {
            System.err.println("❌ Impossible de se connecter à la base de données");
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, compte.numeroCompte());
            ps.setInt(2, compte.idClient());
            ps.setString(3, compte.typeCompte().name());
            ps.setDouble(4, compte.solde());
            ps.setTimestamp(5, Timestamp.valueOf(compte.dateCreation()));
            ps.setString(6, compte.statut().name());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la création du compte : " + e.getMessage());
            return false;
        }
    }

    public List<Compte> trouverParClient(int idClient) {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM comptes WHERE id_client = ?";
        Connection connection = ConnexionDatabase.getConnection();

        if (connection == null) {
            return comptes;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comptes.add(mapResultSetToCompte(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la recherche : " + e.getMessage());
        }

        return comptes;
    }

    private Compte mapResultSetToCompte(ResultSet rs) throws SQLException {
        return new Compte(
            rs.getInt("id_compte"),
            rs.getString("numero_compte"),
            rs.getInt("id_client"),
            TypeCompte.valueOf(rs.getString("type_compte")),
            rs.getDouble("solde"),
            rs.getTimestamp("date_creation").toLocalDateTime(),
            StatutCompte.valueOf(rs.getString("statut"))
        );
    }
}
