<!DOCTYPE html>
<html>

<head>
	<title>Adicionar Aluno</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">	
		
</head>

<body>
	<div >
		<div>
			<h2>Logo Instituto</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Adicionar Aluno</h3>
		
		<form action="AlunoControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Primeiro Nome:</label></td>
						<td><input type="text" name="primeiroNome" /></td>
					</tr>

					<tr>
						<td><label>Último Nome:</label></td>
						<td><input type="text" name="ultimoNome" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Salvar" class="btn btn-primary" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		
		
		<p>
			<a href="AlunoControllerServlet">Volta à Lista</a>
		</p>
	</div>
</body>

</html>











