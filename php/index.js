var REFRESH_TIME = 30000;
var refreshIntervalId;
var pessoas;

$(document).ready(function(){
	$("#cancel").click(function(event){
		$('#formLogin').dialog("close");
	});

	$(".img").click(login);

	// Cria a lista das salas e verifica se tem alguma sala definida
	$.each(salas, function (k, v) {
		$('#sala').append($('<option>', { 
		value: k,
		text : v
		}));
	});
	$("#sala").val(sala);

	updateAll();

	// Atualiza o título da janela para indicar a sala atual
	document.title = document.title + " - Sala:" + sala;

	// Prepara a função de reload que testa se a internet está ok!!!
	playReload();
});

function login(event){
	var nome = event.target.parentNode.id;

	$('#formLogin').dialog({modal:true, width: 350});

	document.getElementById("imgLogin").src = event.target.src;
	document.getElementById("nome").value = nome;
	document.getElementById("password").focus();

	// Seleciona a sala de acordo com a última sala retornada pelo status.php
	var sala = $("#sala")[0];
	var status = $("#status")[0];
	for(p = 0; p < pessoas.length; p++){
		if(pessoas[p].nome == nome ){
			for(i = 0; i < sala.options.length; i++){
				if(pessoas[p].sala == sala.options[i].text)
					sala.selectedIndex = i;
			}
			status.selectedIndex = Number(pessoas[p].disponivel)>0?0:1;
		}
	}

	beep();
}

function unavailableElement(elem){
	elem.className = "divImg unavailable";
}

function availableElement(elem){
	elem.className = "divImg available";
}

function servingElement(elem){
	elem.className = "divImg serving";
}

function disableAll(){
	// Desabilita todos os IMG, pois nem todos estão cadastrados no banco e são retornados pelo JSON
	var imgs = $(".divImg");
	var i = 0;
	for(i =0; i < imgs.length; i++){
		imgs[i].className = "outOfThisRoom";
	}
}

function updateAll(){
	disableAll();
	var url="status.php?sala=" + sala;
	$.getJSON(url,function(data){
		// Obtém o status ds pessoas no servidor
		pessoas = data.pessoas;
		$.each(pessoas, function(i,pessoa){
			var p = $("#" + pessoa.nome)[0];
			//console.log("atualizando: " + pessoa.nome + "(" + pessoa.disponivel + ")");
			switch(pessoa.disponivel){
				case "0":	unavailableElement(p);
						p.children[0].title = pessoa.nome + " não está disponível.";
						break;
				case "1":	availableElement(p);
						p.children[0].title = pessoa.nome + " está na " + pessoa.sala;
						break;
				case "2":	servingElement(p);
						p.children[0].title = pessoa.nome + " está em PAluno na " + pessoa.sala;
						break;
			}
		});
		// Obtém atualiza a hora
		var msg = $("#msg")[0];
		var select = $("#sala")[0]; 
		msg.innerHTML = select.options[select.selectedIndex].text + " (" + data.hora + ")";
	});
}

var error = false;
function updateNow(){
  $.ajax({
	type: "GET",
	url: "status.php",
	success: function(msg){
		if(error){
			$('#dialog').dialog("close");
			error = false;
		}
		updateAll();
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		error = true;
		var dlg = $('#dialog')[0];
		dlg.title = "Conexão  indisponível"
		dlg.innerHTML = "<p>Aguarde a conexão ser reestabelecida e a página ser atualizada automaticamente...</p>"; 
		$('#dialog').dialog({modal:true});
	}
  });
}

function playReload(){
	if(refreshIntervalId > 0)
		clearInterval(refreshIntervalId);
	refreshIntervalId = setInterval(updateNow, REFRESH_TIME);
	$("#play").hide();
	$("#pause").show();
}

function pauseReload(){
	clearInterval(refreshIntervalId);
	refreshIntervalId = 0;
	$("#play").show();
	$("#pause").hide();
}

function beep(){
	var audio = document.createElement("audio");
	audio.src = "beep.mp3"
	audio.volume = 0.3;
	audio.playbackRate = 2.0;
	audio.play();
}
