package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Postgres {

    private static final String URL = "jdbc:postgresql://localhost:5432/DiarioAtividades";
    private static final String USER = "postgres";
    private static final String PASSWORD = "201706279";

    public static Connection conectar(){
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver do PostgreSQL não encontrado: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}