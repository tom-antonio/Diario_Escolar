package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Disciplina;

public class DaoDisciplina {

	public boolean salvar(Disciplina disciplina) {
		String sql = "INSERT INTO tdisciplina (nome_disciplina, id_professor) VALUES (?, ?)";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : null) {

			if (ps == null) {
				return false;
			}

			ps.setString(1, disciplina.getNome_disciplina());
			ps.setInt(2, disciplina.getId_professor());

			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					disciplina.setId(generatedKeys.getInt(1));
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar disciplina: " + e.getMessage());
		}
		return false;
	}

	public List<Disciplina> listarTodos() {
		List<Disciplina> disciplinas = new ArrayList<>();
		String sql = "SELECT id, nome_disciplina, id_professor FROM tdisciplina ORDER BY nome_disciplina";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return disciplinas;
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Disciplina disciplina = new Disciplina(
					rs.getString("nome_disciplina"),
					rs.getInt("id_professor")
				);
				disciplina.setId(rs.getInt("id"));
				disciplinas.add(disciplina);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar disciplinas: " + e.getMessage());
		}
		return disciplinas;
	}
}
