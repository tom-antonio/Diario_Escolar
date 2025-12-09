package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
