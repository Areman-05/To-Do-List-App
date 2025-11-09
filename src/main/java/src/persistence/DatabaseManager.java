package src.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class DatabaseManager {

    private static final String DEFAULT_URL = "jdbc:sqlite:data/todolist.db";
    private static final String SCHEMA_RESOURCE = "/sql/schema.sql";
    private static final String SEED_RESOURCE = "/sql/seed.sql";
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
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            ejecutarScript(connection, SCHEMA_RESOURCE);
            ejecutarScript(connection, SEED_RESOURCE);
        }
        initialized = true;
    }

    private static void ejecutarScript(Connection connection, String recurso) {
        List<String> sentencias = cargarSentenciasDesdeRecurso(recurso);
        if (sentencias.isEmpty()) {
            return;
        }
        try (Statement statement = connection.createStatement()) {
            for (String sql : sentencias) {
                if (!sql.isBlank()) {
                    statement.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar script SQL (" + recurso + "): " + e.getMessage());
        }
    }

    private static List<String> cargarSentenciasDesdeRecurso(String recurso) {
        InputStream inputStream = DatabaseManager.class.getResourceAsStream(recurso);
        if (inputStream == null) {
            return List.of();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String contenido = reader.lines()
                    .map(String::trim)
                    .filter(linea -> !linea.isEmpty() && !linea.startsWith("--"))
                    .collect(Collectors.joining(" "));
            return Arrays.stream(contenido.split(";"))
                    .map(String::trim)
                    .filter(sql -> !sql.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error al leer el recurso SQL (" + recurso + "): " + e.getMessage());
            return List.of();
        }
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

