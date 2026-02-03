import entities.User;
import services.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        // Test création d'un user
        User newUser = new User("Ahmed", "Ben Salah", "ahmed@esprit.tn", "password123", "USER");
        userService.add(newUser);

        // Lecture table User
        List<User> list = userService.getAll();
        for (User u : list) {
            System.out.println("Utilisateur trouvé : " + u.getFirstname() + " | Email : " + u.getEmail() + " | Rôle : " + u.getRole());
        }

        // Test connection avec bons identifiants
        User loggedUser = userService.login("ahmed@esprit.tn", "password123");

        if (loggedUser != null) {
            System.out.println("Succès ! Bienvenue " + loggedUser.getFirstname() + " (Rôle: " + loggedUser.getRole() + ")");
        } else {
            System.out.println("Échec de connexion : Email ou mot de passe incorrect.");
        }

        // Test connection avec mauvais identifiants
        User failedUser = userService.login("ahmed@esprit.tn", "mauvais_pass");
        if (failedUser == null) {
            System.out.println("✅ Le système rejette bien les mauvais mots de passe.");
        }
    }
}
