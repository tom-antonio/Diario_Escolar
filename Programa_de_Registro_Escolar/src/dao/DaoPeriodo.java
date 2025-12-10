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
}
