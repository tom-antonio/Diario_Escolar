package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;

public class DaoAluno {

	public boolean salvar(Aluno aluno) {
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
			ps.setString(7, aluno.getNome_mae());

			ResultSet rs = ps.executeQuery();
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
}
