import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

class Coordenada {

	private int x;
	private int y;
	private int valor;

	public Coordenada(int x, int y, int valor) {

		this.x = x;
		this.y = y;
		this.valor = valor;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return String.valueOf(x) + " " + String.valueOf(y);
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Coordenada)) return false;
		if (obj == null) return false;

		if (obj == this) return true;

		Coordenada nova = (Coordenada)obj;

		if (this.x == nova.x && this.y == nova.y) return true;

		return false;
	}

}

class Labirinto {

	// Matriz que contem o labirinto lido no
	// arquivo de entrada.
	private int[][] labirinto;

	// Coordenada de partida do labirinto lida
	// no arquivo de entrada.
	private Coordenada partida;

	// Coordenada de destino do labirinto
	// lida no arquivo de entrada.
	private Coordenada destino;

	// HashMap contendo todos os caminhos possiveis
	// da coordenada de partida a coordenada de destino.
	private HashMap<Integer, List<Coordenada>> caminhos;


	public Labirinto(int[][] labirinto, Coordenada partida, Coordenada destino) {
		
		this.labirinto = labirinto;
		this.partida = partida;
		this.destino = destino;
		this.caminhos = new HashMap<Integer, List<Coordenada>>();
	}

	// Verifica se a coordenada labirinto[i][j]
	// eh um passo valido.
	public boolean verifica(int i, int j) {

		try {
			if(labirinto[i][j] >= 0) return true;
		} 
		catch (Exception e) {
		}

		return false;
	}

	// Retorna a soma de todos os valores de uma lista de
	// coordenadas
	public static int soma(List<Coordenada> coordenadas) {

		if(coordenadas == null) return 0;

		int soma = 0;

		for(Coordenada c : coordenadas) {
			soma += c.getValor();
		}

		return soma;
	}

	// Metodo recursivo que preenche o HashMap de caminhos possiveis
	// ao encontrar um caminho que alcanca a coordenada de destino.
	public void caminhoRec(Coordenada atual, List<Coordenada> lista) {

		int i = atual.getX();
		int j = atual.getY();

		if(verifica(i, j)) {

			lista.add(new Coordenada(i, j, labirinto[i][j]));

			// Se a coordenada atual for igual a de destino,
			// adiciona o caminho ate entao ao hashmap de
			// caminhos e limpa a lista.
			if(atual.equals(destino)) {
				
				List<Coordenada> caminho = new ArrayList<Coordenada>(lista);

				this.caminhos.put(this.caminhos.size() + 1, caminho);

				lista.clear();
			}
			
			// Marca o caminho ja visitado.
			labirinto[i][j] = -1;

			// Obtem a soma de todos as coordenadas da lista
			// ate o momento.
			int soma = soma(lista);

			// Executa o metodo recursivamente em todas
			// as quatro direcoes possiveis.
			caminhoRec(new Coordenada(i-1, j, soma), lista);
			caminhoRec(new Coordenada(i+1, j, soma), lista);
			caminhoRec(new Coordenada(i, j-1, soma), lista);
			caminhoRec(new Coordenada(i, j+1, soma), lista);
		}
	}

	// Retorna o melhor caminho obtido baseado no map
	// de caminhos possiveis.
	public List<Coordenada> melhorCaminho() {

		caminhoRec(this.partida, new ArrayList<Coordenada>());

		// Se nao exist
		if(this.caminhos.size() == 0) {
			return null;
		}

		int i = 1;
		for(Map.Entry<Integer, List<Coordenada>> entry : this.caminhos.entrySet()) {

			List<Coordenada> caminho = entry.getValue();
			
			// Se a soma dos valores do caminho atual for maior
			// que a soma do maior caminho ate o momento,
			// o caminho atual passa a ser o melhor caminho.
			if(soma(caminho) > soma(this.caminhos.get(i))) i = entry.getKey();
		}

		return this.caminhos.get(i);
	}

	// Baseado no melhor caminho, imprime o resultado na tela 
	// de acordo com as especificacoes passadas no enunciado do EP:
	// <tamanho do caminho encontrado> <valor total dos itens coletados>
	// <linha da posicao 0> <coluna da posicao 0>
	// <linha da posicao 1> <coluna da posicao 1>
	// (...)
	public void resultado() {

		List<Coordenada> melhor = melhorCaminho();

		// Caso nao houver um melhor caminho,
		// imprime na tela o tamanho do caminho
		// encontrado e o valor total dos itens
		// ambos iguais a 0.
		if(melhor == null) {
			System.out.println("0 0");
			return;
		}

		// Imprime o tamanho do caminho e o valor total dos itens.
		System.out.printf("%d %d\n", melhor.size(), soma(melhor));

		// Imprime todas as coordenadas que formam o caminho.
		for(int i = 0; i < melhor.size(); i++) {
			System.out.println(melhor.get(i).toString());
		}
	}

	public int[][] getLabirinto() {
		return this.labirinto;
	}

	public Coordenada getPartida() {
		return this.partida;
	}

	public Coordenada getDestino() {
		return this.destino;
	}

}

public class EP1 {

	// Le o arquivo especificado e retorna um objeto da classe
	// labirinto com os dados carregados.
	public static Labirinto lerArquivo(String nomeArquivo) {
		BufferedReader br = null;
		
		List<String> arquivo = new ArrayList<String>();

		try {
			String linhaAtual;
			br = new BufferedReader(new FileReader(nomeArquivo));
			while((linhaAtual = br.readLine()) != null) {
				arquivo.add(linhaAtual);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null) br.close();
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// Le a primeira linha do arquivo e divide
		// os valores por espaco.
		String[] linha = arquivo.get(0).split(" ");

		int linhas = Integer.parseInt(linha[0]);
		int colunas = Integer.parseInt(linha[1]);

		int[][] labirinto = new int[linhas][colunas];

		// Le da segunda linha ate a penultima, que sao
		// as linhas que contem o labirinto e preenche a 
		// matriz de inteiros que representa o labirinto.
		for(int i = 0; i < arquivo.size() - 3; i++) {
			char[] chars = arquivo.get(i + 1).toCharArray();
			for(int j = 0; j < chars.length; j++) {
				switch (chars[j]) {
				case 'X':
					labirinto[i][j] = -1;
					break;
				case '.':
					labirinto[i][j] = 0;
					break;
				default:
					labirinto[i][j] = Integer.parseInt(Character.toString(chars[j]));
				}
			}
		}

		// Le a penultima linha que contem os dados da
		// coordenada de partida.
		linha = arquivo.get(arquivo.size() - 2).split(" ");
		int partidaLinha = Integer.parseInt(linha[0]);
		int partidaColuna = Integer.parseInt(linha[1]);

		Coordenada partida = new Coordenada(partidaLinha, partidaColuna, 0);

		// Le a ultima linha que contem os dados da
		// coordenada de destino.
		linha = arquivo.get(arquivo.size() - 1).split(" ");
		int destinoLinha = Integer.parseInt(linha[0]);
		int destinoColuna = Integer.parseInt(linha[1]);

		Coordenada destino = new Coordenada(destinoLinha, destinoColuna, 0);

		return new Labirinto(labirinto, partida, destino);
	}
	
	public static void main(String[] args) {
		
		Labirinto l = lerArquivo(args[0]);

		l.resultado();
	}

}