<?php
	$link = mysql_connect('localhost', 'dacom', 'xxxxx');
	if (!$link) {
		die('Não foi possível conectar: ' . mysql_error());
	}
	mysql_select_db('dacom', $link) or die('Não foi possível selecionar o banco "dacom".'.mysql_error());

?>
