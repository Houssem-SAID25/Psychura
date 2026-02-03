package services;

import entities.User;
import interfaces.Services;
import org.mindrot.jbcrypt.BCrypt;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des utilisateurs.
 * Implémente les opérations CRUD et l'authentification.
 */
public class UserService implements Services<User> {

    // Connexion unique à la base de données
    private final Connection connection = MyDatabase.getInstance().getConnection();

    /**
     * Ajoute un nouvel utilisateur dans la base de données.
     * Le mot de passe est haché avant l'insertion pour des raisons de sécurité.
     */
    @Override
    public void add(User user) {
        String sql = "INSERT INTO user (firstname, lastname, email, password, role, is_anonymous) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());

            // Hachage sécurisé du mot de passe avec BCrypt
            ps.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            ps.setString(5, user.getRole());
            ps.setBoolean(6, user.isAnonymous());

            ps.executeUpdate();
            System.out.println("Utilisateur " + user.getEmail() + " ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur insertion : " + e.getMessage());
        }
    }

    /**
     * Met à jour les informations d'un utilisateur existant
     */
    @Override
    public void update(User user) {
        String sql = "UPDATE user SET firstname=?, lastname=?, email=?, role=?, is_anonymous=? WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            ps.setBoolean(5, user.isAnonymous());
            ps.setInt(6, user.getId());

            ps.executeUpdate();
            System.out.println("Utilisateur ID " + user.getId() + " mis à jour !");
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour : " + e.getMessage());
        }
    }

    /**
     * Supprime un utilisateur à partir de son identifiant
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Utilisateur ID " + id + " supprimé !");
        } catch (SQLException e) {
            System.err.println("Erreur suppression : " + e.getMessage());
        }
    }

    /**
     * Récupère la liste de tous les utilisateurs
     */
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFirstname(rs.getString("firstname"));
                u.setLastname(rs.getString("lastname"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setAnonymous(rs.getBoolean("is_anonymous"));

                users.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Erreur sélection : " + e.getMessage());
        }
        return users;
    }

    /**
     * Récupère un utilisateur par son identifiant
     */
    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFirstname(rs.getString("firstname"));
                u.setLastname(rs.getString("lastname"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Authentifie un utilisateur à partir de son email et mot de passe
     *
     * @return User si authentification réussie, null sinon
     */
    public User login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashed = rs.getString("password");

                // Vérification du mot de passe avec BCrypt
                if (BCrypt.checkpw(password, hashed)) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFirstname(rs.getString("firstname"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}