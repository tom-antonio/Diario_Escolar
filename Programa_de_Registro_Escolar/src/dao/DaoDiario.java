package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import controller.NotaController;
import model.Aluno;
import model.Diario;
import model.Disciplina;

public class DaoDiario {

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(DaoDiario.class.getName());
	private final NotaController notaController;
	private final DaoAluno daoAluno;
	private final DaoDisciplina daoDisciplina;

	public DaoDiario() {
		this.notaController = new NotaController();
		this.daoAluno = new DaoAluno();
		this.daoDisciplina = new DaoDisciplina();
	}

	public boolean salvar(Diario diario, int idAluno, int idDisciplina, int idPeriodo, int idTurma) {
		LOG.info("Salvando diário para aluno ID: " + idAluno + ", disciplina ID: " + idDisciplina);
		String sql = "INSERT INTO tdiario (fk_aluno, fk_disciplina, fk_periodo, fk_turma, fk_nota, status) "
				   + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, idAluno);
			ps.setInt(2, idDisciplina);
			ps.setInt(3, idPeriodo);
			ps.setInt(4, idTurma);
			
			// Se houver notas, salvar a primeira e usar seu ID
			Integer idNota = null;
			if (diario.getNotas() != null && !diario.getNotas().isEmpty()) {
				// Salvar a primeira nota para obter o ID
				model.Nota primeiraNota = diario.getNotas().get(0);
				DaoNota daoNota = new DaoNota();
				if (daoNota.salvar(primeiraNota)) {
					idNota = primeiraNota.getId();
				}
			}
			
			ps.setObject(5, idNota);
			ps.setBoolean(6, diario.isStatus());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int idDiario = rs.getInt(1);
				diario.setId(idDiario);
				
				// Salvar demais notas se existirem
				if (diario.getNotas() != null && diario.getNotas().size() > 1) {
					DaoNota daoNota = new DaoNota();
					for (int i = 1; i < diario.getNotas().size(); i++) {
						daoNota.salvar(diario.getNotas().get(i));
					}
				}
				
				return true;
			}
		} catch (SQLException e) {
			System.err.println("Erro ao salvar diário: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public List<Diario> listarTodos() {
		LOG.info("Listando todos os diários na base de dados");
		List<Diario> diarios = new ArrayList<>();
		String sql = "SELECT id, fk_aluno, fk_disciplina, fk_periodo, fk_turma, fk_nota, status FROM tdiario ORDER BY id";

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
				
				// Carregar nota primária via fk_nota
				int fkNota = rs.getInt("fk_nota");
				List<model.Nota> notas = new ArrayList<>();
				
				if (fkNota > 0) {
					DaoNota daoNota = new DaoNota();
					model.Nota nota = daoNota.buscarPorId(fkNota);
					if (nota != null) {
						notas.add(nota);
					}
				}
				
				diario.setNotas(notas);
				diarios.add(diario);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar diários: " + e.getMessage());
		}
		return diarios;
	}

	public boolean alterar(Diario diario, int idAluno, int idDisciplina, int idPeriodo, int idTurma) {
		LOG.info("Alterando diário ID: " + diario.getId());
		String sql = "UPDATE tdiario SET fk_aluno = ?, fk_disciplina = ?, fk_periodo = ?, fk_turma = ?, fk_nota = ?, status = ? WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, idAluno);
			ps.setInt(2, idDisciplina);
			ps.setInt(3, idPeriodo);
			ps.setInt(4, idTurma);
			
			// Se houver notas, salvar a primeira e usar seu ID
			Integer idNota = null;
			if (diario.getNotas() != null && !diario.getNotas().isEmpty()) {
				model.Nota primeiraNota = diario.getNotas().get(0);
				DaoNota daoNota = new DaoNota();
				if (daoNota.salvar(primeiraNota)) {
					idNota = primeiraNota.getId();
				}
			}
			
			ps.setObject(5, idNota);
			ps.setBoolean(6, diario.isStatus());
			ps.setInt(7, diario.getId());

			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0) {
				// Salvar demais notas se existirem
				if (diario.getNotas() != null && diario.getNotas().size() > 1) {
					DaoNota daoNota = new DaoNota();
					for (int i = 1; i < diario.getNotas().size(); i++) {
						daoNota.salvar(diario.getNotas().get(i));
					}
				}
				return true;
			}
		} catch (SQLException e) {
			System.err.println("Erro ao alterar diário: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean excluir(int id) {
		LOG.info("Excluindo diário ID: " + id);
		String sql = "DELETE FROM tdiario WHERE id = ?";

		try (Connection conn = Postgres.conectar();
			 PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

			if (ps == null) {
				return false;
			}

			ps.setInt(1, id);
			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao excluir diário: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public Diario pesquisarAluno(String nomeAluno, String nomeDisciplina) {
		LOG.info("Pesquisando diário para aluno: " + nomeAluno + ", disciplina: " + nomeDisciplina);
		String sql = "SELECT d.id, d.fk_aluno, d.fk_disciplina, d.fk_periodo, d.fk_turma, d.fk_nota, d.status "
				   + "FROM tdiario d "
				   + "INNER JOIN taluno a ON d.fk_aluno = a.id "
				   + "INNER JOIN tdisciplina disc ON d.fk_disciplina = disc.id "
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
				
				// Carregar nota primária via fk_nota
				int fkNota = rs.getInt("fk_nota");
				List<model.Nota> notas = new ArrayList<>();
				
				if (fkNota > 0) {
					DaoNota daoNota = new DaoNota();
					model.Nota nota = daoNota.buscarPorId(fkNota);
					if (nota != null) {
						notas.add(nota);
					}
				}
				
				diario.setNotas(notas);
				return diario;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar diário: " + e.getMessage());
		}
		return null;
	}

	public Integer buscarIdAlunoPorNome(String nome) {
		LOG.info("Buscando ID do aluno por nome: " + nome);
		Aluno aluno = daoAluno.buscarPorNome(nome);
		return aluno != null ? aluno.getId() : null;
	}

	public Integer buscarIdDisciplinaPorNome(String nome) {
		LOG.info("Buscando ID da disciplina por nome: " + nome);
		Disciplina disciplina = daoDisciplina.pesquisar(nome);
		return disciplina != null ? disciplina.getId() : null;
	}
}
