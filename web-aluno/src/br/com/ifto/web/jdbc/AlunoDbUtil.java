package br.com.ifto.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.ifto.web.jdbc.Aluno;

public class AlunoDbUtil {
	private DataSource dataSource;

	public AlunoDbUtil(DataSource oDataSource) {
		dataSource = oDataSource;
	}
	
	public List<Aluno> getAlunos() throws Exception {
		
		List<Aluno> alunos = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			
			String sql = "select * from alunos";
			
			myStmt = myConn.createStatement();
			
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				
				int id = myRs.getInt("id");
				String primeiroNome = myRs.getString("primeiro_nome");
				String ultimoNome = myRs.getString("ultimo_nome");
				String email = myRs.getString("email");
				
				// criar novo objeto ALuno
				Aluno tempAluno = new Aluno(id, primeiroNome, ultimoNome, email);
				
				// adicionar ele na lista dos alunos
				alunos.add(tempAluno);				
			}
			
			return alunos;		
		}
		finally {
			// Fechar objetos JDBC
			close(myConn, myStmt, myRs);
		}		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public  void adicionaAluno(Aluno oAluno) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// obter conexão banco de dados
			myConn = dataSource.getConnection();
			
			// criar sql para insert
			String sql = "insert into alunos "
					   + "(primeiro_nome, ultimo_nome, email) "
					   + "values (?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// define os valores do parametro para o aluno
			myStmt.setString(1, oAluno.getPrimeiroNome());
			myStmt.setString(2, oAluno.getUltimoNome());
			myStmt.setString(3, oAluno.getEmail());
			
			// executr sql insert
			myStmt.execute();
		}
		finally {
			// limpar objetos JDBC 
			close(myConn, myStmt, null);
		}
	}
	
	public Aluno getAluno(String oAlunoId) throws Exception {

		Aluno oAluno = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int alunoId;
		
		try {
			// converte aluno id para int
			alunoId = Integer.parseInt(oAlunoId);
			
			// obter conexão com o banco de dados
			myConn = dataSource.getConnection();
			
			// crie o sql para obter o aluno selecionado
			String sql = "select * from alunos where id=?";
			
			// criar declaração preparada
			myStmt = myConn.prepareStatement(sql);
			
			// definir parametros
			myStmt.setInt(1, alunoId);
			
			// execute declaração
			myRs = myStmt.executeQuery();
			
			// recuperar dados do conjunto de resultados
			if (myRs.next()) {
				String primeiroNome = myRs.getString("primeiro_nome");
				String ultimoNome = myRs.getString("ultimo_nome");
				String email = myRs.getString("email");
				
				// use o alunoId durante a construção
				oAluno = new Aluno(alunoId, primeiroNome, ultimoNome, email);
			}
			else {
				throw new Exception("Não consegui encontrar id aluno: " + alunoId);
			}				
			
			return oAluno;
		}
		finally {
			// Limpar objetos JDBC
			close(myConn, myStmt, myRs);
		}
	}
	
	public void atualizaAluno(Aluno oAluno) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// obter conexão banco de dados
			myConn = dataSource.getConnection();
			
			// criar declaração SQL update
			String sql = "update alunos "
						+ "set primeiro_nome=?, ultimo_nome=?, email=? "
						+ "where id=?";
			
			// prepare declaração
			myStmt = myConn.prepareStatement(sql);
			
			// definir parametros
			myStmt.setString(1, oAluno.getPrimeiroNome());
			myStmt.setString(2, oAluno.getUltimoNome());
			myStmt.setString(3, oAluno.getEmail());
			myStmt.setInt(4, oAluno.getId());
			
			// execute declaração SQL
			myStmt.execute();
		}
		finally {
			// Limpar objetos JDBC
			close(myConn, myStmt, null);
		}
	}
	public void deletaAluno(String oAlunoId) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// converte aluno id para int
			int alunoId = Integer.parseInt(oAlunoId);
			
			// obter conexão banco de dados
			myConn = dataSource.getConnection();
			
			// cria sql para deletar aluno
			String sql = "delete from alunos where id=?";
			
			// prepare declaração
			myStmt = myConn.prepareStatement(sql);
			
			// definir parametros
			myStmt.setInt(1, alunoId);
			
			// execute declaração SQL
			myStmt.execute();
		}
		finally {
			// Limpar objetos JDBC
			close(myConn, myStmt, null);
		}	
	}
}
