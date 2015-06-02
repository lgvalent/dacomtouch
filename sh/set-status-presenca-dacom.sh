#!/bin/bash 

# Script para definição de status no sistema de presença do DACOM.
# autor: rogerioag@utfpr.edu.br

echo "Start: $0."
echo "Definindo status no sistema de presença."

# Função que verifica se tem uma string em uma lista de strings.
listcontains() {
  for word in $1; do
    [[ $word = $2 ]] && return 0
  done
  return 1
}

# Recupera a chave do valor.
getKey() {
  for key in ${!map[@]}; do
    # echo ${key} ${map[${key}]}
    [[ ${map[${key}]} = $1 ]] && echo ${key}
  done
}

# Configurações.
URL="https://dacom.valentin.com.br/login.php"
SALA=`cat sala.txt | awk '{$1=$1;print}'`
NOME=`cat professor.txt | awk '{$1=$1;print}'`
PASSWORD=`cat password.txt | awk '{$1=$1;print}'`
SSID="\"`cat ssid.txt | awk '{$1=$1;print}' | sed 's/ /_/'`\""

# Define o status padrão: Indisponível.
STATUS=0;

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

# Cria um map com a lista de status para usar o getKey.
declare -A map

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

# Recupera a lista de SSIDs.
INTERFACES=`sudo iwlist scan 2>/dev/null | awk -F":" '/ESSID/{print $2}' | sed 's/ /_/'`

echo $INTERFACES
# Verifica se a SSID definida como padrão está na lista.
if listcontains "$INTERFACES" $SSID; 
then 
	echo "Está na UTFPR.";
	# Apresenta uma tela solicitando o status.
	# status: 0-indisponível 1-disponível 2-PAluno 3-Aula
	ans=$(zenity  --list  --text "Qual o status desejado?" --radiolist  --column "Pick" --column "Status" ${str_list});
	echo $ans

#	case $ans in
#		"Indisponível") STATUS=0 ;;
#		"Disponível") STATUS=1 ;;
#		"PAluno") STATUS=2 ;;
#		"Aula") STATUS=3 ;;
#		*) echo "INVALID NUMBER!" ;;
#	esac
	
	# Recupera o id do status de um map, para não ficar fixo no código com o case.
	STATUS=$( getKey "$ans" ) && echo "Status: ${STATUS}"

	# Chama a url do sistema para definir um status.
	outcurl=$(curl -k --data "sala=${SALA}&nome=${NOME}&password=${PASSWORD}&status=${STATUS}" ${URL})
else 
		# SSID não está na lista. Está fora da UTFPR.
		echo "Fora da UTFPR.";
		# Define um status padrão Indisponível=0.
		outcurl=$(curl -k --data "sala=${SALA}&nome=${NOME}&password=${PASSWORD}&status=${STATUS}" ${URL})
fi
echo ${outcurl}
echo "Finish: $0."
