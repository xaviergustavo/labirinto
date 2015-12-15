import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;

class Coordenada implements Comparable<Coordenada> {

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
	public int compareTo(Coordenada c) {
		return Integer.compare(this.valor, c.valor);
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

	private int[][] labirinto;
	private Coordenada partida;
	private Coordenada destino;
	private List<Coordenada> caminho;

	public Labirinto(int[][] labirinto, Coordenada partida, Coordenada destino) {
		
		this.labirinto = labirinto;
		this.partida = partida;
		this.destino = destino;
		this.caminho = new ArrayList<Coordenada>();
		
	}

	public boolean verifica(int i, int j) {

		try {
			if(labirinto[i][j] >= 0) return true;
		} 
		catch (Exception e) {
		}

		return false;

	}
	
	public int totalCaminhoRec(Coordenada atual, int soma) {
		
		int i = atual.getX();
		int j = atual.getY();
		
		if(verifica(i, j)) {
			
			if(atual.equals(this.destino)) return soma;
			
			soma += labirinto[i][j];
			
			labirinto[i][j] = -1;
			
			int direita = totalCaminhoRec(new Coordenada(i, j+1, 0), soma);
			int baixo = totalCaminhoRec(new Coordenada(i+1, j, 0), soma);
			int esquerda = totalCaminhoRec(new Coordenada(i, j-1, 0), soma);
			int cima = totalCaminhoRec(new Coordenada(i-1, j, 0), soma);
			
			List<Coordenada> valores = new ArrayList<Coordenada>();
			
			valores.add(new Coordenada(i, j+1, direita));
			valores.add(new Coordenada(i+1, j, baixo));
			valores.add(new Coordenada(i, j-1, esquerda));
			valores.add(new Coordenada(i-1, j, cima));
			
			Collections.sort(valores);
			
			Coordenada melhor = valores.get(valores.size() - 1);
			
			this.caminho.add(melhor);
			
			return melhor.getValor();
			
		}
		
		return -1;
		
	}
	
	public int totalCaminho() {
		
		int total = totalCaminhoRec(this.partida, 0);
		
		this.caminho.add(partida);
		
		Collections.reverse(this.caminho);
		
		return total;
		
	}
	
	public void resultado() {
		
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
	
	public List<Coordenada> getCaminho() {
		return this.caminho;
	}

}

public class EP1 {

	public static Labirinto lerArquivo(String nomeArquivo) {
		BufferedReader br = null;
		
		List<String> arquivo = new ArrayList<String>();

		try {
			String linhaAtual;
			br = new BufferedReader(new FileReader(nomeArquivo));
			while((linhaAtual = br.readLine()) != null) {
				arquivo.add(linhaAtual);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		String[] linha = arquivo.get(0).split(" ");

		int linhas = Integer.parseInt(linha[0]);
		int colunas = Integer.parseInt(linha[1]);

		int[][] labirinto = new int[linhas][colunas];

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

		linha = arquivo.get(arquivo.size() - 2).split(" ");
		int partidaLinha = Integer.parseInt(linha[0]);
		int partidaColuna = Integer.parseInt(linha[1]);

		Coordenada partida = new Coordenada(partidaLinha, partidaColuna, 0);

		linha = arquivo.get(arquivo.size() - 1).split(" ");
		int destinoLinha = Integer.parseInt(linha[0]);
		int destinoColuna = Integer.parseInt(linha[1]);

		Coordenada destino = new Coordenada(destinoLinha, destinoColuna, 0);

		return new Labirinto(labirinto, partida, destino);
	}
	
	public static void main(String[] args) {
		
		Labirinto l = lerArquivo(args[0]);
		
		int total = l.totalCaminho();
		
		List<Coordenada> caminho = l.getCaminho();
		
		System.out.printf("%d %d\n", caminho.size(), total);
		
		for(int i = 0; i < caminho.size(); i++) {
			System.out.println(caminho.get(i).toString());
		}
	}

}
