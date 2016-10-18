<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<!--meta http-equiv="refresh" content="30"-->
	<meta name="mobile-web-app-capable" content="yes">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>Presença DACOM - Troca senha</title>
	<link rel="stylesheet" type="text/css" href="index.css" >
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

	<script src="jQuery/jquery.toastmessage.js"></script>
	<link rel="stylesheet" href="jQuery/jquery.toastmessage.css">

	<script>
	<?php 
		include 'dao.php';

		if($_REQUEST['nome'] && $_REQUEST['password'] && $_REQUEST['newPassword']){
			$pwd = md5($_REQUEST['password']);
			$newPwd = md5($_REQUEST['newPassword']);

			mysql_query("UPDATE ponto SET password = '".$newPwd."' WHERE nome = '".$_REQUEST['nome']."' AND password = '".$pwd."'");
			$result = mysql_affected_rows($link);
			if($result > 0)
				echo "alert('Senha alterada com sucesso!');";
			else 
				echo "alert('Senha atual não confere.');";
		}

		// Seleciona todos os professores cadastrados
		$result = mysql_query("SELECT p.nome, p.disponivel, s.nome as sala FROM ponto p INNER JOIN sala s ON s.id=p.sala ORDER BY p.nome");
		$profs = array();
		while($result && $row = mysql_fetch_assoc($result)){
			$profs[] = $row;
		}

		mysql_close($link);
	?>
	</script>
</head>

<body onload="$('#nome')[0].onchange();">
	<form id="formPassword" align="center" action="password.php" method="post" title="Troca de senha" >
		<table>
		<tr>
			<td>
				<img id="imgLogin" src="img/refresh.png" width=130 height=130/>
			</td>
			<td>
			Selecione seu ID:
			<select id="nome" name="nome" onchange="$('#imgLogin')[0].src='img/'+this.value+'.jpg';">
				<?php 
				foreach($profs as $p){
					echo '<option value="'.$p["nome"].'">';
					echo $p["nome"];
					echo '</option>';
				}
				?>
			</select>
			<br>Digite sua senha atual:
			<input type="password" id="password" name="password" size=15 >
			<br>Digite sua nova senha :
			<input type="password" id="newPassword" name="newPassword" size=15 ><br>
			<input type="submit" value="Enviar" >
			</td>
		</tr>
		</table>
		* Use uma senha numérica para facilitar a digitação pelo teclado virtual.<br>
		<a href="index.php">Home</a>
	</form>
	<div id="dialog" title="Basic dialog" ></div>
	<div id="keyboard" title="Virtua Keyboard"></div>
</body>
<script>
	$('#formPassword').dialog({
			modal:true, 
			width: 350, 
			close: function() {
					//vKeyboard.Show(false);
				}
			});
</script>
</html>
