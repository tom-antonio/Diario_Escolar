package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;

public class DaoAluno {

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(DaoAluno.class.getName());

	public boolean salvar(Aluno aluno) {
		LOG.info("Salvando aluno: " + aluno.getNome());
		String sql = "INSERT INTO taluno (nome, endereco, telefone, email, matricula, nome_pai, nome_mae) "
				   + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

		ps.setString(1, aluno.getNome());
		ps.setString(2, aluno.getEndereco());
		ps.setString(3, aluno.getTelefone());
		ps.setString(4, aluno.getEmail());
		ps.setLong(5, aluno.getMatricula());
		ps.setString(6, aluno.getNome_pai());
		ps.setString(7, aluno.getNome_mae());			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				aluno.setId(rs.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar aluno: " + e.getMessage());
		}
		return false;
	}

	public List<Aluno> listarTodos() {
		LOG.info("Listando todos os alunos na base de dados");
		List<Aluno> alunos = new ArrayList<>();
		String sql = "SELECT id, nome, endereco, telefone, email, matricula, nome_pai, nome_mae FROM taluno ORDER BY nome";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return alunos;
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			Aluno aluno = new Aluno(
				rs.getInt("id"),
				rs.getString("nome"),
				rs.getString("endereco"),
				rs.getString("telefone"),
				rs.getString("email"),
				rs.getLong("matricula"),
				rs.getString("nome_pai"),
				rs.getString("nome_mae")
			);
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar alunos: " + e.getMessage());
		}
		return alunos;
	}

	public boolean alterar(Aluno aluno) {
		LOG.info("Alterando aluno ID: " + aluno.getId());
		String sql = "UPDATE taluno SET nome = ?, endereco = ?, telefone = ?, email = ?, matricula = ?, nome_pai = ?, nome_mae = ? WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setString(1, aluno.getNome());
			ps.setString(2, aluno.getEndereco());
			ps.setString(3, aluno.getTelefone());
			ps.setString(4, aluno.getEmail());
			ps.setLong(5, aluno.getMatricula());
			ps.setString(6, aluno.getNome_pai());
			ps.setString(7, aluno.getNome_mae());
			ps.setInt(8, aluno.getId());

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar aluno: " + e.getMessage());
		}
		return false;
	}

	public boolean excluir(int id) {
		LOG.info("Excluindo aluno ID: " + id);
		String sql = "DELETE FROM taluno WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, id);

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao excluir aluno: " + e.getMessage());
		}
		return false;
	}

	public Aluno buscarPorId(int id) {
		LOG.info("Buscando aluno por ID: " + id);
		String sql = "SELECT id, nome, endereco, telefone, email, matricula, nome_pai, nome_mae FROM taluno WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return null;
			}

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Aluno(
					rs.getInt("id"),
					rs.getString("nome"),
					rs.getString("endereco"),
					rs.getString("telefone"),
					rs.getString("email"),
					rs.getLong("matricula"),
					rs.getString("nome_pai"),
					rs.getString("nome_mae")
				);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar aluno: " + e.getMessage());
		}
		return null;
	}

	public Aluno buscarPorNome(String nome) {
		LOG.info("Buscando aluno por nome: " + nome);
		String sql = "SELECT id, nome, endereco, telefone, email, matricula, nome_pai, nome_mae FROM taluno WHERE nome = ? LIMIT 1";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return null;
			}

			ps.setString(1, nome);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Aluno(
					rs.getInt("id"),
					rs.getString("nome"),
					rs.getString("endereco"),
					rs.getString("telefone"),
					rs.getString("email"),
					rs.getLong("matricula"),
					rs.getString("nome_pai"),
					rs.getString("nome_mae")
				);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar aluno por nome: " + e.getMessage());
		}
		return null;
	}
}
