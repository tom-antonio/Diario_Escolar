package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Nota;

public class DaoNota {

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(DaoNota.class.getName());

	public boolean salvar(Nota nota) {
		LOG.info("Salvando nota: " + nota.getNota());
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
		LOG.info("Listando todas as notas na base de dados");
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

	public Nota buscarPorId(int id) {
		LOG.info("Buscando nota por ID: " + id);
		String sql = "SELECT id, nota FROM tnota WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return null;
			}

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Nota(rs.getInt("id"), rs.getDouble("nota"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar nota por ID: " + e.getMessage());
		}
		return null;
	}

	public boolean salvarNotasPorDiario(int idDiario, List<Nota> notas) {
		LOG.info("Salvando notas para o diário ID: " + idDiario);
		String sql = "INSERT INTO tnota (id_diario, nota) VALUES (?, ?)";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null || notas == null || notas.isEmpty()) {
				return true; // Sem notas para salvar
			}

			for (Nota nota : notas) {
				ps.setInt(1, idDiario);
				ps.setDouble(2, nota.getNota());
				ps.addBatch();
			}

			ps.executeBatch();
			return true;
		} catch (SQLException e) {
			System.err.println("Erro ao salvar notas: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean excluirNotasPorDiario(int idDiario) {
		LOG.info("Excluindo notas para o diário ID: " + idDiario);
		String sql = "DELETE FROM tnota WHERE id_diario = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, idDiario);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Erro ao excluir notas: " + e.getMessage());
		}
		return false;
	}
}
