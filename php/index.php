<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<!--meta http-equiv="refresh" content="30"-->
	<meta name="mobile-web-app-capable" content="yes">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<title>Presença DACOM - v0.1b</title>
	<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
	<!--script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<link rel="stylesheet" href="jQuery/jquery-ui.css"-->
	<script src="jQuery/jquery-1.10.2.js"></script>
	<script src="jQuery/jquery-ui.js"></script>

	<link rel="stylesheet" type="text/css" href="index.css" >
	<script src="index.js"></script>

	<script src="keyboard.js"></script>

	<script>
	<?php 

		include 'dao.php';

		session_start();
		if($_REQUEST['sala'])
			$_SESSION['sala'] = $_REQUEST['sala'];

		if($_SESSION['sala']){
			echo "var sala ='".$_SESSION['sala']."';\n";
		}else{
			echo "var sala = 999;\n";
			$_SESSION['sala'] = 999;
		}

		if($_REQUEST['device'])
			$_SESSION['device'] = true;

		if($_SESSION['device']){
			echo "var device = true;\n";
		}else{
			echo "var device = false;\n";
		}

		if($_POST['nome'] && $_POST['password']){
			$result = mysql_query("SELECT id, nome, ultimoRegistro, disponivel, password, sala FROM ponto WHERE nome = '".$_POST['nome']."'");
			if(mysql_num_rows ($result )>0){
				$row = mysql_fetch_assoc($result);
				$t = md5($row['password']);
				echo "//".$row['nome']."=".$row['disponivel']."---".$status."  pwd=".$row['password']."==".$t."\n";
				// Verifica se a senha bate ou está vazia no banco
				if($row['password']=="" ||  ($row['password']!="" && $row['password']==md5($_POST['password']))){
					// Atualiza o status
					$status = $_REQUEST['status'];
					$result = mysql_query("UPDATE ponto SET sala=".$_SESSION['sala'].", disponivel=".$status.", ultimoRegistro = CURRENT_TIMESTAMP WHERE id = ".$row['id']);
				}

			} else {
				// Insere a pessoa pela primeira vez com a senha fornecida
				$result = mysql_query("INSERT INTO ponto (nome, ultimoRegistro, disponivel, password, sala) VALUES('".$_POST['nome']."', CURRENT_TIMESTAMP, '1', '".md5($_POST['password'])."', ".$_SESSION['sala'].")");
				if (!$result){die('Could not query:'.mysql_error());}
			}
		}
		
		// Seleciona todos os professores cadastrados
//		$profs = array("Kraemer", "Ana", "Kawamoto", "Aretha", "Diego", "Everton", "Filipe", "Frank", "Steinmacher", "Wiese", "Ivanilton", "Juliano", "Lucio", "Luiz", "Marco", "Silvano", "Narci", "PCGoncalves", "Sabo", "Radames", "Liberato", "Zottesso", "Re", "Campiolo", "Hubner", "Rag");
//		$profs = array("Ana", "Kawamoto", "Aretha", "Diego", "Everton", "Filipe", "Frank", "Steinmacher", "Wiese", "Ivanilton", "Juliano", "Lucio", "Marco", "Silvano", "Narci", "PCGoncalves", "Sabo", "Radames", "Liberato", "Zottesso", "Re", "Campiolo", "Hubner", "Rag");
		$result = mysql_query("SELECT p.nome, p.disponivel, s.nome as sala FROM ponto p INNER JOIN sala s ON s.id=p.sala ORDER BY p.nome");
		$profs = array();
		while($result && $row = mysql_fetch_assoc($result)){
			$profs[] = $row;
		}

		// Seleciona todas as salas disponíveis
		$result = mysql_query("SELECT id, nome FROM sala");
		echo "var salas = {";
		while($row = mysql_fetch_assoc($result)){
			echo $row['id'].": '".$row['nome']."', ";
		}
		echo " 999: '(Todas)'};\n";

		mysql_close($link);
	?>
	</script>
</head>

<body>
	<form id="formLogin" align="center" method="post" title="Troca de status" >
		<table>
		<tr>
			<td>
				<img id="imgLogin" src="img/refresh.png" width=130 height=130/>
			</td>
			<td>
			<select id="sala" name="sala"></select>
			<input type="text" id="nome" name="nome" readOnly="true" size=15 ><br/>
			Digite sua senha:
			<input type="password" id="password" name="password" size=15 ><br/><br/>
			<select id="status" name="status">
				<option value="0">Indisponível</option>
				<option value="1">Presente</option>
				<option value="2">PAluno</option>
			</select>
			<input type="submit" value="Enviar" >
			</td>
		</tr>
		</table>
	</form>
	<div class="divGrid">
		<?php 
		foreach($profs as $p){
			echo '<div id="'.$p["nome"].'" class="divImg">';
			echo '  <img class="img" src="img/'.$p["nome"].'.jpg" title="'.$p["nome"].'">';
			echo '  <div class="divStatus" ></div>';
			echo '</div>';
		}
		?>

	</div>
	<div id="menu" >
		<img id="reload" onclick="updateAll();" src="img/refresh.png" width=40 height=40 title="Atualizar"/>
		<img id="play" onclick="playReload();" src="img/play.png" width=40 height=40 title="Habilitar atualização automática" />
		<img id="pause" onclick="pauseReload();" src="img/pause.png" width=40 height=40 title="Desabilitar atualização automática"/><br/>
		<span id="msg" />
	</div>
	<div id="dialog" title="Basic dialog" ></div>
	<div id="keyboard" title="Virtua Keyboard"></div>
</body>
</html>
