package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Periodo;

public class DaoPeriodo {

	public boolean salvar(Periodo periodo) {
		String sql = "INSERT INTO tperiodo (nome_periodo) "
				   + "VALUES (?) RETURNING id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setString(1, periodo.getNome_periodo());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				periodo.setId(rs.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar período: " + e.getMessage());
		}
		return false;
	}

	public List<Periodo> listarTodos() {
		List<Periodo> periodos = new ArrayList<>();
		String sql = "SELECT id, nome_periodo FROM tperiodo ORDER BY nome_periodo";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return periodos;
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Periodo periodo = new Periodo(
					rs.getInt("id"),
					rs.getString("nome_periodo")
				);
				periodos.add(periodo);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar periodos: " + e.getMessage());
		}
		return periodos;
	}

	public boolean alterar(Periodo periodo) {
		String sql = "UPDATE tperiodo SET nome_periodo = ? WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setString(1, periodo.getNome_periodo());
			ps.setInt(2, periodo.getId());

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar período: " + e.getMessage());
		}
		return false;
	}

	public boolean excluir(int id) {
		String sql = "DELETE FROM tperiodo WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, id);

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao excluir período: " + e.getMessage());
		}
		return false;
	}

	public Periodo buscarPorId(int id) {
		String sql = "SELECT id, nome_periodo FROM tperiodo WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return null;
			}

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Periodo(
					rs.getInt("id"),
					rs.getString("nome_periodo")
				);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar período: " + e.getMessage());
		}
		return null;
	}
}
