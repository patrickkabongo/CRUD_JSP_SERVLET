<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<title> App Aluno</title>		
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">	
</head>


<body>

	<div >
		<div >
			<h2>Logo Instituto</h2>
		</div>
	</div>
	<br/>
	<div id="container">
	
		<div id="content">
			<input class="btn btn-primary" type="button" value="Adicionar aluno"
				   onclick="window.location.href='adicionar-aluno.jsp'; return false;"	
			>
			<br/>
			<br/>
			<table class="table">
				  <thead class="thead-dark">
				    <tr>
				      <th scope="col">Primeiro Nome</th>
				      <th scope="col">Último Nome</th>
				      <th scope="col">E-mail</th>
				      <th scope="col">Ação</th>
				    </tr>
				  </thead>
				  <c:forEach var="tempAluno" items="${LIST_ALUNOS}">
				  
				  <!-- Definir um link para cada aluno -->
					<c:url var="tempLink" value="AlunoControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="alunoId" value="${tempAluno.id}" />
					</c:url>
					
					<!--  Definir um link para deletar aluno -->
					<c:url var="deleteLink" value="AlunoControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="alunoId" value="${tempAluno.id}" />
					</c:url>
				  <tbody>
				    <tr>
				      <td>${tempAluno.primeiroNome} </td>
				      <td>${tempAluno.ultimoNome}</td>
				      <td>${tempAluno.email}</td>
				      <td> 
							<a href="${tempLink}">Atualiza</a> 
							 |
							<a href="${deleteLink}"
							onclick="if (!(confirm('Tem certeza de que deseja excluir este aluno?'))) return false">
							Deleta</a>
						</td>
				    </tr>
				  </tbody>
				  </c:forEach>	
			</table>		
		</div>
	
	</div>
</body>


</html>







