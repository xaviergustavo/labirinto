# Introdução à Análise de Algoritmos Atividade 1: Labirinto

## Objetivo

O objetivo deste exercício programa é implementar um algoritmo que encontre um caminho em um labirinto, representado por uma matriz, dada uma posição de partida e outra de destino. A movimentação pelo labirinto pode se dar em 4 direções: para cima, para baixo, para a esquerda e para a direita. Além disso, podem haver itens, cada um com um determinado valor, espalhados pelo labirinto. Dessa forma, o caminho encontrado deve ser aquele que maximize o valor dos itens coletados (dizemos que um item é coletado se o caminho passa pela posição em que o item se encontra). Uma restrição adicional é que um caminho só pode passar por uma posição uma única vez.

Seu programa deve ler um arquivo de entrada, contendo a definição do labirinto (representado por uma matriz de caracteres) e as posições de partida e de destino. Deve ser gerada como saída uma listagem de coordenadas, referentes às posições que fazem parte do caminho encontrado. Maiores detalhes sobre o arquivo de entrada a a formatação da saída são apresentados a seguir.

## Arquivo de Entrada

Seu programa deve receber como único parâmetro de linha de comando um nome de arquivo, da seguinte forma:

java EP1 entrada.txt

O arquivo de entrada deve conter a definição do labirinto e as posições de partida e destino, respeitando a seguinte formatação:

[numero de linhas do labirinto] [numero de colunas do labirinto]<br>
[linha 0]<br>
[linha 1]<br>
[linha 2]<br>
(...)<br>
[linha da posição de partida> <coluna da posição de partida]<br>
[linha da posição de destino> <coluna da posição de destino]<br>

A seguir, um exemplo de um arquivo de entrada:

7 5<br>
.....<br>
.XXX.<br>
8XXX.<br>
.XXX7<br>
1XXX.<br>
.XXX.<br>
.....<br>
6 2<br>
0 2<br>

A primeira linha do arquivo contem 2 valores inteiros que definem o número de linhas e o número de colunas do labirinto (no caso do exemplo, 7 linhas e 5 colunas). Cada uma das 7 (no caso do exemplo) linhas seguintes contem uma sequência de caracteres de tamanho igual ao número de colunas (5, para o arquivo de exemplo), e cada caractere representa uma posição do labirinto. O caractere '.' indica uma posição livre, enquanto que o caractere 'X' representa uma posição bloqueada (ou seja, não pode fazer parte de um caminho). Os caracteres '1' a '9' também representam posições livres, mas que possuem um item de valor igual ao dígito representado pelo caractere (assim, cada item pode ter um valor entre 1 e 9). Para o exemplo, existem 3 itens espalhados no labirinto, com os seguintes valores: 1, 7 e 8. Finalmente, a penúltima linha do arquivo define as coordenadas (linha e coluna, respectivamente) da posição de partida (linha 6 e coluna 2, para o exemplo) e a última linha as coordenadas da posição de destino (linha 0 e coluna 2, no caso do exemplo).

## Saída

A saída gerada pelo seu programa deve ser impressa na saída padrão e deve respeitar a seguinte formatação:

[tamanho do caminho encontrado> <valor total dos itens coletados]<br>
[linha da posição 0] [coluna da posição 0]<br>
[linha da posição 1] [coluna da posição 1]<br>
[linha da posição 2] [coluna da posição 2]<br>
(...)<br>

Para o arquivo de entrada apresentado como exemplo, a saída gerada deve ser a seguinte:

11 9<br>
6 2<br>
6 1<br>
6 0<br>
5 0<br>
4 0<br>
3 0<br>
2 0<br>
1 0<br>
0 0<br>
0 1<br>
0 2<br>

Os dois valores numéricos da primeira linha indicam que o caminho encontrado que maximiza o valor coletado de itens passa por 11 posições, e o valor total dos itens coletados é igual a 9. Cada uma das 11 linhas que seguem contem dois valores numéricos que representam as coordenadas (linha e coluna, respectivamente) das posições que formam o caminho.
