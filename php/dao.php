<?php
	$link = mysql_connect('robb0408.publiccloud.com.br', 'lgval_dacom', 'User#2015');
	if (!$link) {
		die('Não foi possível conectar: ' . mysql_error());
	}
	mysql_select_db('lgvalent_dacom', $link) or die('Não foi possível selecionar o banco "lgvalent_dacom".'.mysql_error());

?>
