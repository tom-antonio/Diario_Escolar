package dao;

import controller.NotaController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;
import model.Diario;
import model.Disciplina;

public class DaoDiario {

	private final DaoAluno daoAluno;
	private final DaoDisciplina daoDisciplina;
	private final NotaController notaController;

	public DaoDiario() {
		this.daoAluno = new DaoAluno();
		this.daoDisciplina = new DaoDisciplina();
		this.notaController = new NotaController();
	}

	public boolean salvar(Diario diario, int idAluno, int idDisciplina, int idPeriodo, int idTurma) {
		String sql = "INSERT INTO tdiario (id_aluno, id_disciplina, id_periodo, id_turma, status) "
				   + "VALUES (?, ?, ?, ?, ?) RETURNING id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, idAluno);
			ps.setInt(2, idDisciplina);
			ps.setInt(3, idPeriodo);
			ps.setInt(4, idTurma);
			ps.setBoolean(5, diario.isStatus());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int idDiario = rs.getInt(1);
				diario.setId(idDiario);
				
				// Salvar notas associadas via NotaController
				return notaController.salvarNotasPorDiario(idDiario, diario.getNotas());
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar diário: " + e.getMessage());
		}
		return false;
	}

	public List<Diario> listarTodos() {
		List<Diario> diarios = new ArrayList<>();
		String sql = "SELECT id, id_aluno, id_disciplina, id_periodo, id_turma, status FROM tdiario ORDER BY id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return diarios;
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Diario diario = new Diario();
				diario.setId(rs.getInt("id"));
				diario.setStatus(rs.getBoolean("status"));
				
				// Carregar notas
				List<model.Nota> notas = buscarNotasPorDiario(rs.getInt("id"));
				diario.setNotas(notas);
				
				diarios.add(diario);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar diários: " + e.getMessage());
		}
		return diarios;
	}

	private List<model.Nota> buscarNotasPorDiario(int idDiario) {
		List<model.Nota> notas = new ArrayList<>();
		String sql = "SELECT id, nota FROM tnota WHERE id_diario = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return notas;
			}

			ps.setInt(1, idDiario);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				model.Nota nota = new model.Nota(rs.getInt("id"), rs.getDouble("nota"));
				notas.add(nota);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar notas: " + e.getMessage());
		}
		return notas;
	}

	public boolean alterar(Diario diario, int idAluno, int idDisciplina, int idPeriodo, int idTurma) {
		String sql = "UPDATE tdiario SET id_aluno = ?, id_disciplina = ?, id_periodo = ?, id_turma = ?, status = ? WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, idAluno);
			ps.setInt(2, idDisciplina);
			ps.setInt(3, idPeriodo);
			ps.setInt(4, idTurma);
			ps.setBoolean(5, diario.isStatus());
			ps.setInt(6, diario.getId());

			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				// Remover notas antigas e salvar novas via NotaController
				notaController.excluirNotasPorDiario(diario.getId());
				return notaController.salvarNotasPorDiario(diario.getId(), diario.getNotas());
			}
		} catch (SQLException e) {
			System.out.println("Erro ao alterar diário: " + e.getMessage());
		}
		return false;
	}

	public boolean excluir(int id) {
		try {
			// Primeiro excluir notas via NotaController
			if (!notaController.excluirNotasPorDiario(id)) {
				return false;
			}
			
			// Depois excluir diário
			String sql = "DELETE FROM tdiario WHERE id = ?";

			try (Connection conn = Postgres.conectar();
				 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

				if (ps == null) {
					return false;
				}

				ps.setInt(1, id);
				int linhasAfetadas = ps.executeUpdate();
				return linhasAfetadas > 0;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao excluir diário: " + e.getMessage());
		}
		return false;
	}

	public Diario pesquisarAluno(String nomeAluno, String nomeDisciplina) {
		String sql = "SELECT d.id, d.id_aluno, d.id_disciplina, d.id_periodo, d.id_turma, d.status "
				   + "FROM tdiario d "
				   + "INNER JOIN taluno a ON d.id_aluno = a.id "
				   + "INNER JOIN tdisciplina disc ON d.id_disciplina = disc.id "
				   + "WHERE a.nome = ? AND disc.nome_disciplina = ? "
				   + "LIMIT 1";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return null;
			}

			ps.setString(1, nomeAluno);
			ps.setString(2, nomeDisciplina);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Diario diario = new Diario();
				diario.setId(rs.getInt("id"));
				diario.setStatus(rs.getBoolean("status"));
				
				// Carregar notas
				List<model.Nota> notas = buscarNotasPorDiario(rs.getInt("id"));
				diario.setNotas(notas);
				
				return diario;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar diário: " + e.getMessage());
		}
		return null;
	}

	public Integer buscarIdAlunoPorNome(String nome) {
		Aluno aluno = daoAluno.buscarPorNome(nome);
		return aluno != null ? aluno.getId() : null;
	}

	public Integer buscarIdDisciplinaPorNome(String nome) {
		Disciplina disciplina = daoDisciplina.pesquisar(nome);
		return disciplina != null ? disciplina.getId() : null;
	}
}
