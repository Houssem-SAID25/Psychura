package entities;

/**
 * Représente un utilisateur du système.
 * Cette classe est une entité métier mappée sur la table "user" de la base de données.
 */
public class User {

    // Identifiant unique de l'utilisateur (clé primaire)
    private int id;

    // Informations personnelles
    private String firstname;
    private String lastname;
    private String email;

    // Mot de passe de l'utilisateur (stocké sous forme hachée en base)
    private String password;

    // Rôle de l'utilisateur : USER, ADMIN ou SPECIALIST
    private String role;

    // Indique si l'utilisateur agit de manière anonyme
    private boolean isAnonymous;

    /**
     * Constructeur par défaut
     */
    public User() {
    }

    /**
     * Constructeur avec paramètres
     *
     * @param firstname Prénom
     * @param lastname Nom
     * @param email Adresse email
     * @param password Mot de passe (en clair avant hachage)
     * @param role Rôle de l'utilisateur
     */
    public User(String firstname, String lastname, String email, String password, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe (haché en base)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur
     * @param password Mot de passe en clair (sera haché avant insertion)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retourne le rôle de l'utilisateur
     */
    public String getRole() {
        return role;
    }

    /**
     * Définit le rôle de l'utilisateur
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Indique si l'utilisateur est anonyme
     */
    public boolean isAnonymous() {
        return isAnonymous;
    }

    /**
     * Définit le mode anonyme de l'utilisateur
     */
    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}