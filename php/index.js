var REFRESH_TIME = 30000;
var refreshIntervalId;
var pessoas;

$(document).ready(function(){
	$("#cancel").click(function(event){
		$('#formLogin').dialog("close");
	});

	$(".img").click(openLogin);
	$("#formLogin").submit(submitLogin);

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

function openLogin(event){
	var nome = event.target.parentNode.id;

	$('#formLogin').dialog({
			modal:true, 
			width: 350, 
			close: function() {
					if(device)
						vKeyboard.Show(false);
				}
			});

	document.getElementById("imgLogin").src = event.target.src;
	document.getElementById("nome").value = nome;
	document.getElementById("password").value = "";
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

	// Só mostra o teclado em tablets fixos
	if(device)
		showKeyboard();

}

// http://api.jquery.com/jquery.post/
function submitLogin(event){
	var posting = $.post( "login.php", $( "#formLogin" ).serialize(), null, 'json');
	posting.done(
		function (data){
			$('#formLogin').dialog("close");
			if(data.error)
				updateMsg(data.msg);
			else{
				var s = document.getElementById("sala");
				sala = s.options[s.selectedIndex].value;
				updateAll();
			}
		}
	);
	return false;
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
	var url="status.php?sala=" + sala + "&device=" + device;
	$.getJSON(url,function(data){
		disableAll();
		// Obtém o status das pessoas no servidor
		pessoas = data.pessoas;
		$.each(pessoas, function(i,pessoa){
			var p = $("#" + pessoa.nome)[0];
			//console.log("atualizando: " + pessoa.nome + "(" + pessoa.disponivel + ")");
			p.className = "divImg status"+pessoa.disponivel;
			switch(pessoa.disponivel){
				case "0":	p.children[0].title = pessoa.nome + " não está disponível.";
						break;
				case "1":	p.children[0].title = pessoa.nome + " está na " + pessoa.sala;
						break;
				case "2":	p.children[0].title = pessoa.nome + " está em PAluno na " + pessoa.sala;
						break;
				case "3":	p.children[0].title = pessoa.nome + " está em Aula";
						break;
			}
		});
		// Obtém a hora
		updateMsg(" (" + data.hora + ")");
	});
}

function updateMsg(addMsg){
	var msg = $("#msg")[0];
	var select = $("#sala")[0]; 
	msg.innerHTML = select.options[select.selectedIndex].text + (addMsg?"<br>"+addMsg:"");
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

var vKeyboard = null;

function showKeyboard(){
	if(!vKeyboard){
		vKeyboard = new VATMpad("keyboard",     // container's id
					pad_callback, // reference to the callback function
                         "Arial",           // font name ("" == system default)
                         "32px",       // font size in px
                         "#005",       // font color
                         "#00F",       // keyboard base background color
                         "#EEF",       // keys' background color
                         "#777",       // border color
                         false,         // show key flash on click? (false by default)
                         "#CC3300",    // font color for flash event
                         "#FF9966",    // key background color for flash event
                         "#CC3300",    // key border color for flash event
                         false,        // embed VNumpad into the page?
                         true);        // use 1-pixel gap between the keys?

	} else vKeyboard.Show(true);

	// Posiciona o Keyboard
	var x = 0, y = 0;
	var o = $("#password")[0];
	while(o){
		x += o.offsetLeft;
		y += o.offsetTop;

		o = o.offsetParent;
	}
	var k = $("#keyboard")[0];
	k.style.left = x;
	k.style.top = y+70;
}

// Advanced callback function:
function pad_callback(ch){
	var text = $("#password")[0]; 

	switch(ch){
		case "Enter": submitLogin();break;
		default: text.value += ch;
	}
}


