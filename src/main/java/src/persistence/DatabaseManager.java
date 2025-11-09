package src.persistence;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseManager {

    private static final String DEFAULT_URL = "jdbc:sqlite:data/todolist.db";
    private static String dbUrl = System.getProperty("todolist.db.url", DEFAULT_URL);
    private static boolean initialized = false;

    private DatabaseManager() {
    }

    public static synchronized void configure(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("La URL de base de datos no puede estar vacía.");
        }
        dbUrl = url;
        initialized = false;
    }

    public static Connection getConnection() throws SQLException {
        ensureInitialized();
        return DriverManager.getConnection(dbUrl);
    }

    private static synchronized void ensureInitialized() throws SQLException {
        if (initialized) {
            return;
        }
        prepareFileSystemForDatabase();
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS tareas (
                        id INTEGER PRIMARY KEY,
                        titulo TEXT NOT NULL,
                        descripcion TEXT,
                        completada INTEGER NOT NULL DEFAULT 0
                    )
                    """);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS empleados (
                        id INTEGER PRIMARY KEY,
                        rol TEXT NOT NULL
                    )
                    """);
        }
        initialized = true;
    }

    private static void prepareFileSystemForDatabase() {
        if (!dbUrl.startsWith("jdbc:sqlite:")) {
            return;
        }
        String path = dbUrl.substring("jdbc:sqlite:".length());
        if (path.isBlank() || ":memory:".equalsIgnoreCase(path) || path.startsWith(":memory:")) {
            return;
        }
        try {
            Path dbPath = Path.of(path).toAbsolutePath();
            Path parentPath = dbPath.getParent();
            if (parentPath == null) {
                return;
            }
            File parent = parentPath.toFile();
            if (!parent.exists() && !parent.mkdirs()) {
                System.out.println("No se pudo crear el directorio de la base de datos: " + parent);
            }
        } catch (Exception ignored) {
        }
    }
}

