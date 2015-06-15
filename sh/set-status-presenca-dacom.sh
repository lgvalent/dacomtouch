#!/bin/bash 

# Script para definição de status no sistema de presença do DACOM.
# autor: rogerioag@utfpr.edu.br

echo "Start: $0."
echo "Definindo status no sistema de presença:"`date`
# Configurações.
URL="https://dacom.valentin.com.br/login.php"
SALA=`cat sala.txt | awk '{$1=$1;print}'`
NOME=`cat professor.txt | awk '{$1=$1;print}'`
PASSWORD=`cat password.txt | awk '{$1=$1;print}'`
SSID_STATUS_1="dacom"
#"\"`cat ssid_status_1.txt | awk '{$1=$1;print}' | sed 's/ /_/'`\""
SSID_STATUS_3="UTFPRADM"
#"\"`cat ssid_status_3.txt | awk '{$1=$1;print}' | sed 's/ /_/'`\""

#Função para exibir mensagens de DEBUG
DEBUG=1;
debug() {
	if [ "$DEBUG" -eq "1" ];
	then
		echo "DEBUG: $1"
	fi 

}
# Função que verifica se tem uma string em uma lista de strings.
listcontains() {
	debug "listcontains($1, \"$2\")"
	for word in $1; do
		[[ $word = \"$2\" ]] && return 0
	done
	return 1
}

# Cria um map com a lista de status para usar o getKey.
declare -A map
# Recupera a chave do valor.
getKey() {
  for key in ${!map[@]}; do
    # echo ${key} ${map[${key}]}
    [[ ${map[${key}]} = $1 ]] && echo $key
  done
}

# Escolhe um STATUS na tela.
chooseStatus() {
	# Lista de status disponíveis.
	# status_list=("Indisponível" "Disponível" "PAluno" "Aula")
	# Leitura da lista de status de um arquivo, possível adicionar mais status com "Auditório".
	status_list=(`cat lista-status.txt`)
	
	str_list=""
	
	# Cria a lista para a tela.
	for status_item in "${status_list[@]}"; do
	    # echo $status_item
	    if [ -z "$str_list" ]; then
				str_list="$str_list TRUE $status_item"
			else
				str_list="$str_list FALSE $status_item"
			fi
	done
	
	index=0
	for status_item in "${status_list[@]}"; do
	    # echo $status_item
	    map+=([$index]=$status_item )
	    index=$((index+1))
	done
	
	#echo "DEBUG Map:"
	#for key in ${!map[@]}; do
	#    echo ${key} ${map[${key}]}
	#done

	# Apresenta uma tela solicitando o status.
	# status: 0-indisponível 1-disponível 2-PAluno 3-Aula
	ans=$(zenity  --list  --text "Qual o status desejado?" --radiolist  --column "Pick" --column "Status" ${str_list});

#	case $ans in
#		"Indisponível") STATUS=0 ;;
#		"Disponível") STATUS=1 ;;
#		"PAluno") STATUS=2 ;;
#		"Aula") STATUS=3 ;;
#		*) echo "INVALID NUMBER!" ;;
#	esac
	
	# Recupera o id do status de um map, para não ficar fixo no código com o case.
	result=$(getKey "$ans");
	echo $result
}

# Define o status padrão: Indisponível.
STATUS=0;
MSG="Indisponível";
# Recupera a lista de SSIDs.
INTERFACES=`iwlist scan 2>/dev/null | awk -F":" '/ESSID/{print $2}' | sed 's/ /_/'`

echo $INTERFACES
# Verifica se a SSID definida como padrão está na lista.
if listcontains "$INTERFACES" $SSID_STATUS_1; 
then 
	debug "Escolhendo STATUS na tela."
	# SSID_STATUS_1 escolhe na tela.
	MSG="Está na UTFPR: DISPONÍVEL.";
	# Recupera o id do status de um map, para não ficar fixo no código com o case.
	STATUS=$( chooseStatus )
	if [ "$STATUS" = "" ];
	then
		STATUS=1;
	fi
	echo "STATUS=$STATUS"
else 
if listcontains "$INTERFACES" $SSID_STATUS_3; 
then 
	debug "Definino STATUS_1"
	# SSID_STATUS_3 está na lista. Está em aula.
	MSG="Está na UTFPR: EM AULA.";
	# Define um status padrão Indisponível=0.
	STATUS=3;
else
	debug "Definindo STATUS_0"
	# SSID não está na lista. Está fora da UTFPR.
	echo "Fora da UTFPR.";
	# Define um status padrão Indisponível=0.
	STATUS=0;
fi
fi

# SSID não está na lista. Está fora da UTFPR.
echo $MSG;
# Define um status padrão Indisponível=0.
outcurl=$(curl -k --data "sala=${SALA}&nome=${NOME}&password=${PASSWORD}&status=${STATUS}" ${URL})

echo ${outcurl}
echo "Finish: $0."
