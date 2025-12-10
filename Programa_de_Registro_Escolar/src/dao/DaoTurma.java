package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Turma;

public class DaoTurma {

	public boolean salvar(Turma turma) {
		String sql = "INSERT INTO tturma (nome_turma) "
				   + "VALUES (?) RETURNING id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setString(1, turma.getNome_turma());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				turma.setId(rs.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar turma: " + e.getMessage());
		}
		return false;
	}

	public List<Turma> listarTodos() {
		List<Turma> turmas = new ArrayList<>();
		String sql = "SELECT id, nome_turma FROM tturma ORDER BY nome_turma";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return turmas;
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Turma turma = new Turma(
					rs.getInt("id"),
					rs.getString("nome_turma")
				);
				turmas.add(turma);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar turmas: " + e.getMessage());
		}
		return turmas;
	}
}
