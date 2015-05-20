<?php 
	include 'dao.php';

	// Seleciona as pessoas disponíveis na sala atual
	$result = mysql_query("SELECT p.nome, p.disponivel, s.nome as sala FROM ponto p INNER JOIN sala s ON s.id=p.sala WHERE TRUE=TRUE ".($_REQUEST['sala']!=999?"AND p.sala = ".$_REQUEST['sala']:""));
	$var = array();
	while($result && $row = mysql_fetch_assoc($result)){
		$var[] = $row;
	}
	echo '{"pessoas":'.json_encode($var).', "hora": "'.date('d/m/y H:i:s').'"}';

	// Atualiza na sala a data da última atualização
	if($_REQUEST['sala'])
		mysql_query("UPDATE sala SET ultimaAtualizacao = NOW() WHERE id = ".$_REQUEST['sala']);

	mysql_close($link);
?>
