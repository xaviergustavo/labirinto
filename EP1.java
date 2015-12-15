import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

class Coordenada {

	private int x;
	private int y;

	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}

class Labirinto {

	private int[][] labirinto;

	private Coordenada partida;

	private Coordenada destino;

	public Labirinto(int[][] labirinto, Coordenada partida, Coordenada destino) {
		
		this.labirinto = labirinto;
		
		this.partida = partida;
		
		this.destino = destino;
	
	}

	public void imprimeLabirinto() {
		
		for(int i = 0; i < labirinto.length; i++) {
			for(int j = 0; j < labirinto[i].length; j++) {
				System.out.printf("%d\t", labirinto[i][j]);
			}
			System.out.println();
		}

	}

	public boolean verifica(int i, int j) {

		try {
			
			if(labirinto[i][j] >= 0) return true;
		
		} catch (Exception e) {

		}

		return false;

	}

	public int[][] getLabirinto() {

		return labirinto;

	}

	public Coordenada getPartida() {
		
		return partida;
	
	}

	public Coordenada getDestino() {
		
		return destino;
	
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

		String[] primeiraLinha = arquivo.get(0).split(" ");

		int linhas = Integer.parseInt(primeiraLinha[0]);
		int colunas = Integer.parseInt(primeiraLinha[1]);

		int[][] labirinto = new int[linhas][colunas];

		for(int i = 0; i < arquivo.size() - 3; i++) {
			char[] linha = arquivo.get(i+1).toCharArray();
			for(int j = 0; j < linha.length; j++) {
				switch (linha[j]) {
					case 'X':
						labirinto[i][j] = -1;
						break;
					case '.':
						labirinto[i][j] = 0;
						break;
					default:
						labirinto[i][j] = Integer.parseInt(Character.toString(linha[j]));
				}
			}
		}

		String[] penultimaLinha = arquivo.get(arquivo.size() - 2).split(" ");
		int partidaLinha = Integer.parseInt(penultimaLinha[0]);
		int partidaColuna = Integer.parseInt(penultimaLinha[1]);

		Coordenada partida = new Coordenada(partidaLinha, partidaColuna);

		String[] ultimaLinha = arquivo.get(arquivo.size() - 1).split(" ");
		int destinoLinha = Integer.parseInt(ultimaLinha[0]);
		int destinoColuna = Integer.parseInt(ultimaLinha[1]);

		Coordenada destino = new Coordenada(destinoLinha, destinoColuna);

		return new Labirinto(labirinto, partida, destino);
	}
	
	public static void main(String[] args) {
		
		Labirinto l = lerArquivo("labirinto.txt");

		l.imprimeLabirinto();
	}

}
