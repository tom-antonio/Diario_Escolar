package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Professor;

public class DaoProfessor {

	public boolean salvar(Professor professor) {
		String sql = "INSERT INTO tprofessor (nome, endereco, telefone, email, matricula) "
				   + "VALUES (?, ?, ?, ?, ?) RETURNING id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

		ps.setString(1, professor.getNome());
		ps.setString(2, professor.getEndereco());
		ps.setString(3, professor.getTelefone());
		ps.setString(4, professor.getEmail());
		ps.setLong(5, professor.getMatricula());			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				professor.setId(rs.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar professor: " + e.getMessage());
		}
		return false;
	}

	public List<Professor> listarTodos() {
		List<Professor> professores = new ArrayList<>();
		String sql = "SELECT id, nome, endereco, telefone, email, matricula FROM tprofessor ORDER BY nome";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return professores;
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Professor professor = new Professor(
					rs.getInt("id"),
					rs.getString("nome"),
					rs.getString("endereco"),
					rs.getString("telefone"),
					rs.getString("email"),
					rs.getLong("matricula")
				);
				professores.add(professor);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar professores: " + e.getMessage());
		}
		return professores;
	}

	public boolean alterar(Professor professor) {
		String sql = "UPDATE tprofessor SET nome = ?, endereco = ?, telefone = ?, email = ?, matricula = ? WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setString(1, professor.getNome());
			ps.setString(2, professor.getEndereco());
			ps.setString(3, professor.getTelefone());
			ps.setString(4, professor.getEmail());
			ps.setLong(5, professor.getMatricula());
			ps.setInt(6, professor.getId());

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar professor: " + e.getMessage());
		}
		return false;
	}

	public boolean excluir(int id) {
		String sql = "DELETE FROM tprofessor WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, id);

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao excluir professor: " + e.getMessage());
		}
		return false;
	}

	public Professor buscarPorNome(String nome) {
		String sql = "SELECT id, nome, endereco, telefone, email, matricula FROM tprofessor WHERE nome = ? LIMIT 1";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return null;
			}

			ps.setString(1, nome);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Professor(
					rs.getInt("id"),
					rs.getString("nome"),
					rs.getString("endereco"),
					rs.getString("telefone"),
					rs.getString("email"),
					rs.getLong("matricula")
				);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar professor por nome: " + e.getMessage());
		}
		return null;
	}
}
