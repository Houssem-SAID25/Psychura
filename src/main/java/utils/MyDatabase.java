package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de gestion de la connexion à la base de données.
 * Implémente le pattern Singleton afin d'assurer une seule connexion.
 */
public class MyDatabase {

    // Paramètres de connexion à la base de données
    private final String URL = "jdbc:mysql://127.0.0.1:3306/psycura_db";
    private final String USER = "root";
    private final String PASS = "";

    // Connexion JDBC
    private Connection connection;

    // Instance unique du Singleton
    private static MyDatabase instance;

    /**
     * Constructeur privé pour empêcher l'instanciation directe
     */
    private MyDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connexion établie !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }

    /**
     * Retourne l'instance unique de la classe
     */
    public static MyDatabase getInstance() {
        if (instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }

    /**
     * Retourne la connexion à la base de données
     */
    public Connection getConnection() {
        return connection;
    }
}