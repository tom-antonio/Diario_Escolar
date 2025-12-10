package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Nota;

public class DaoNota {

	public boolean salvar(Nota nota) {
		String sql = "INSERT INTO tnota (nota) "
				   + "VALUES (?) RETURNING id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setDouble(1, nota.getNota());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nota.setId(rs.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nota: " + e.getMessage());
		}
		return false;
	}

	public List<Nota> listarTodos() {
		List<Nota> notas = new ArrayList<>();
		String sql = "SELECT id, nota FROM tnota ORDER BY nota";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return notas;
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Nota nota = new Nota(
					rs.getInt("id"),
					rs.getDouble("nota")
				);
				notas.add(nota);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar notas: " + e.getMessage());
		}
		return notas;
	}
}
