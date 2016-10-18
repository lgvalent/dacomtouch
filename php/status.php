<?php 
	include 'dao.php';

	// Seleciona as pessoas disponíveis na sala atual
	$result = mysql_query("SELECT p.nome, p.disponivel, s.nome as sala FROM ponto p INNER JOIN sala s ON s.id=p.sala WHERE TRUE=TRUE ".($_REQUEST['sala'] && $_REQUEST['sala']!=999?"AND p.sala = ".$_REQUEST['sala']:""));
	$ponto = array();
	while($result && $row = mysql_fetch_assoc($result)){
		$ponto[] = $row;
	}

	// Atualiza na sala a data da última atualização se for um dispositivo
	if($_REQUEST['device']=='true')
		mysql_query("UPDATE sala SET ultimaAtualizacao = NOW() WHERE id = ".$_REQUEST['sala']);

	// Obtem os dados das salas
	$result = mysql_query("SELECT s.id, s.nome, s.ultimaAtualizacao FROM sala s");
	$sala = array();
	while($result && $row = mysql_fetch_assoc($result)){
		$sala[] = $row;
	}

	echo '{"pessoas":'.json_encode($ponto).', "salas":'.json_encode($sala).', "hora": "'.date('d/m/y H:i:s').'"}';
	mysql_close($link);
?>
