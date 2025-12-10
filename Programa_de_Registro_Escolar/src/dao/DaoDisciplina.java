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

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(DaoDisciplina.class.getName());

	public boolean salvar(Disciplina disciplina) {
		LOG.info("Salvando disciplina: " + disciplina.getNome_disciplina());
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
		LOG.info("Listando todas as disciplinas na base de dados");
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

	public boolean alterar(Disciplina disciplina) {
		LOG.info("Alterando disciplina ID: " + disciplina.getId());
		String sql = "UPDATE tdisciplina SET nome_disciplina = ?, id_professor = ? WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setString(1, disciplina.getNome_disciplina());
			ps.setInt(2, disciplina.getId_professor());
			ps.setInt(3, disciplina.getId());

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar disciplina: " + e.getMessage());
		}
		return false;
	}

	public boolean excluir(int id) {
		LOG.info("Excluindo disciplina ID: " + id);
		String sql = "DELETE FROM tdisciplina WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, id);

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao excluir disciplina: " + e.getMessage());
		}
		return false;
	}

	public Disciplina pesquisar(String nome) {
		LOG.info("Pesquisando disciplina com nome: " + nome);
		String sql = "SELECT id, nome_disciplina, id_professor FROM tdisciplina WHERE nome_disciplina = ? LIMIT 1";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return null;
			}

			ps.setString(1, nome);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Disciplina disciplina = new Disciplina(
					rs.getString("nome_disciplina"),
					rs.getInt("id_professor")
				);
				disciplina.setId(rs.getInt("id"));
				return disciplina;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar disciplina por nome: " + e.getMessage());
		}
		return null;
	}
}
