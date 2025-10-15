package dao;

import entity.Client;
import configDatabase.ConnexionDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO {
    
    public boolean creer(Client client) {
        String sql = "INSERT INTO clients (nom, prenom, email, telephone, adresse, date_creation) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            System.err.println("❌ Impossible de se connecter à la base de données");
            return false;
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.nom());
            ps.setString(2, client.prenom());
            ps.setString(3, client.email());
            ps.setString(4, client.telephone());
            ps.setString(5, client.adresse());
            ps.setTimestamp(6, Timestamp.valueOf(client.dateCreation()));
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la création du client : " + e.getMessage());
            return false;
        }
    }
    
    public Optional<Client> trouverParId(int id) {
        String sql = "SELECT * FROM clients WHERE id_client = ?";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            return Optional.empty();
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapperClient(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la recherche du client : " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    public Optional<Client> trouverParEmail(String email) {
        String sql = "SELECT * FROM clients WHERE email = ?";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            return Optional.empty();
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapperClient(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la recherche du client par email : " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    public List<Client> listerTous() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients ORDER BY nom, prenom";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            return clients;
        }
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clients.add(mapperClient(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des clients : " + e.getMessage());
        }
        
        return clients;
    }
    
    public boolean mettreAJour(Client client) {
        String sql = "UPDATE clients SET nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ? WHERE id_client = ?";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            return false;
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.nom());
            ps.setString(2, client.prenom());
            ps.setString(3, client.email());
            ps.setString(4, client.telephone());
            ps.setString(5, client.adresse());
            ps.setInt(6, client.idClient());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour du client : " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) {
        String sql = "DELETE FROM clients WHERE id_client = ?";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            return false;
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du client : " + e.getMessage());
            return false;
        }
    }
    
    public List<Client> rechercherParNom(String recherche) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            return clients;
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String pattern = "%" + recherche + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(mapperClient(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la recherche des clients : " + e.getMessage());
        }
        
        return clients;
    }
    
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM clients WHERE email = ?";
        Connection connection = ConnexionDatabase.getConnection();
        
        if (connection == null) {
            return false;
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la vérification de l'email : " + e.getMessage());
        }
        
        return false;
    }
    
    private Client mapperClient(ResultSet rs) throws SQLException {
        return new Client(
            rs.getInt("id_client"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("email"),
            rs.getString("telephone"),
            rs.getString("adresse"),
            rs.getTimestamp("date_creation").toLocalDateTime()
        );
    }
}