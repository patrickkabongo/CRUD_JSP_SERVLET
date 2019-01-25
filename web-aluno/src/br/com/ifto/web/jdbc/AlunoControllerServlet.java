//GRANT ALL ON SEQUENCE descriptions_id_seq TO sdc;
package br.com.ifto.web.jdbc;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.ifto.web.jdbc.Aluno;

/**
 * Servlet implementation class AlunoControllerServlet
 */
@WebServlet("/AlunoControllerServlet")
public class AlunoControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private AlunoDbUtil alunoDbUtil;
	
	@Resource(name="jdbc/postgres")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// criar nosso aluno db util ... e passa no pool de conexão
		try {
			alunoDbUtil = new AlunoDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// Ler o parametro "command" 
			String oCommand = request.getParameter("command");
			
			if (oCommand == null) {
				oCommand = "LIST";
			}
			
			
			switch (oCommand) {
			
				case "LIST":
					listarAlunos(request, response);
					break;
					
				case "ADD":
					adicionaAluno(request, response);
					break;
					
				case "LOAD":
					carregaAluno(request, response);
					break;
				
				case "UPDATE":
					atualizaAluno(request, response);
					break;
					
				case "DELETE":
					deletaAluno(request, response);
					break;
					
				default:
					listarAlunos(request, response);
				}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
		
	}
	
	private void deletaAluno(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			// ler o ID do aluno a partir dos dados do formulário
			String oAlunoId = request.getParameter("alunoId");
			
			// deleta aluno do banco de dados
			alunoDbUtil.deletaAluno(oAlunoId);
			
			// enviá-los de volta para a página "listar alunos"
			listarAlunos(request, response);
		}
	
	private void atualizaAluno(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			// ler informações do aluno a partir dos dados do formulário
			int id = Integer.parseInt(request.getParameter("alunoId"));
			String primeiroNome = request.getParameter("primeiroNome");
			String ultimoNome = request.getParameter("ultimoNome");
			String email = request.getParameter("email");
			
			// criar um novo objeto Aluno
			Aluno oAluno = new Aluno(id, primeiroNome, ultimoNome, email);
			
			// realizar atualização no banco de dados
			alunoDbUtil.atualizaAluno(oAluno);
			
			// enviá-los de volta para a página "listar alunos"
			listarAlunos(request, response);
			
		}
	
	private void carregaAluno(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

			// ler o ID do aluno a partir dos dados do formulário
			String oAlunoId = request.getParameter("alunoId");
			
			// obter aluno do banco de dados (db util)
			Aluno oAluno = alunoDbUtil.getAluno(oAlunoId);
			
			// colocar aluno no atributo request
			request.setAttribute("O_ALUNO", oAluno);
			
			// enviar para a página jsp: atualiza-aluno.jsp
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("/atualizar-aluno.jsp");
			dispatcher.forward(request, response);		
		}
	
	private void adicionaAluno(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Ler informação aluno do formuçário adiciona-aluno
		String primeiroNome = request.getParameter("primeiroNome");
		String ultimoNome = request.getParameter("ultimoNome");
		String email = request.getParameter("email");		
		
		// criar a novo objeto aluno
		Aluno oAluno = new Aluno(primeiroNome, ultimoNome, email);
		
		
		// adiciona aluno no banco de dados
		alunoDbUtil.adicionaAluno(oAluno);
				
		// voltar para listar alunos
		listarAlunos(request, response);
	}

	private void listarAlunos(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// Obter alunos a partir do db util
		List<Aluno> alunos = alunoDbUtil.getAlunos();
		
		// adiciona alunos ao request 
		request.setAttribute("LIST_ALUNOS", alunos);
				
		// enviar na página JSP (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listar-alunos.jsp");
		dispatcher.forward(request, response);
	}

}


