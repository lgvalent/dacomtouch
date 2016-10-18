<?php 
	include 'dao.php';

	$msg = "";
	$error = 0;
	if($_POST['sala'] && $_POST['nome'] && $_POST['password']){
		$result = mysql_query("SELECT id, nome, ultimoRegistro, disponivel, password, sala FROM ponto WHERE nome = '".$_POST['nome']."'");
		if(mysql_num_rows ($result )>0){
			$row = mysql_fetch_assoc($result);
			$t = md5($row['password']);
			// Verifica se a senha bate ou está vazia no banco
			if($row['password']!="" && $row['password']==md5($_POST['password'])){
				// Atualiza o status
				$status = $_REQUEST['status'];
				$result = mysql_query("UPDATE ponto SET sala=".$_POST['sala'].", disponivel=".$status.", ultimoRegistro = CURRENT_TIMESTAMP WHERE id = ".$row['id']);
				$result = mysql_affected_rows($link);
				if($result > 0){
					$msg = $_POST['nome']." alterou seu status com sucesso!";
					$error = 0;
				} else {
					$msg = " Erro ao atualizar o status de ". $_POST['nome'];
					$error = 3;
				}
			} else {
				$msg = "Senha incorreta.";
				$error = 2;
			}
		}else{
			$msg = "Login incorreto.";
			$error = 1;
		}
	}

	echo '{ "error" : '.$error.', "msg": "'.$msg.'"}';

	mysql_close($link);
?>
