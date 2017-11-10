
/**
 * @author Moisa Anca-Elena, 321CA
 * Editari
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GigelExpertul {
	/**
	 * Functie care efectueaza ajustari: stergere litera, adaugare litera, inlocuire litera
	 * 
	 * @param matrix - se compara cu word
	 * @param word - se compara cu matrix
	 * @param lineNumber - numarul de linii ale matricei
	 * @param columnNumber - numarul de coloane ale matricei
	 * @param wordLength - lungimea cuvantului ce va fi ajustat
	 * @return distanta dintre matrice si cuvant, mai exact numarul de ajustari ce trebuie facute
	 */
	public static int minNumberOfEdits(int matrix[][], int word[], int lineNumber, int columnNumber, int wordLength) {
		// matrice care retine numarul minim de editari ce trebuie aplicate
		int editsMatrix[][] = new int[columnNumber + 1][wordLength + 1];
		int editsMatrixColumnNumber = editsMatrix[0].length;
		int editsMatrixLineNumber = editsMatrix.length;
		
		// obtin o "bordare" inainte de prima coloana pentru a determina numarul de ajustari ce
		// trebuie facute de la o litera la alta
		for (int i = 0; i < editsMatrixLineNumber; i++)
			editsMatrix[i][0] = i;
		
		// obtin o "bordare" deasupra primei linii pentru a determina numarul de ajustari ce 
		// trebuie facute de la o litera la alta
		for (int i = 0; i < editsMatrixColumnNumber; i++)
				editsMatrix[0][i] = i;
		int count = 1;
		int diag = 0, up = 0, left = 0;
		// parcurgere matrice si vector in acelasi timp
		for (int j = 1; j <= wordLength; j++) {
			for (int i = 1; i <= columnNumber; i++) {
				for (int k = 1; k <= lineNumber; k++) {
					if (word[j - 1] == matrix[k - 1][i - 1]) {
						count = 0;
						break;
					}
				}
				if (count == 0) {
					diag = editsMatrix[i - 1][j - 1];
					left = Integer.MAX_VALUE;
					up = Integer.MAX_VALUE;
				}
				else {
					diag = editsMatrix[i - 1][j - 1];
					left = editsMatrix[i][j - 1];
					up = editsMatrix[i - 1][j];
				}
				int min1 = Math.min(up + count, left + count);
				editsMatrix[i][j] = Math.min(min1, diag + count);
				count = 1;	
			}
		}
		return editsMatrix[columnNumber][wordLength];
	}

	public static void main(String args[]) throws Exception {
		BufferedReader fileIn = new BufferedReader(new FileReader("evaluare.in"));
		int lineNumber = 0;
		int columnNumber = 0;
		int wordLength = 0;
		int matrix[][] = null;
		int word[] = null;
		String line = fileIn.readLine();
		String[] remove = line.split("\\s+"); // eliminare spatii
		
		try {
			lineNumber = Integer.parseInt(remove[0]); // obtinere numarul de linii ale matricei			
			columnNumber = Integer.parseInt(remove[1]); // obtinere numarul de coloane ale matricei
			matrix = new int[lineNumber][columnNumber];
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		int row = 0;
					
		while ((line = fileIn.readLine()) != null && row < matrix.length) {
			remove = line.split("\\s+");
			
			// obtinere din fisier a matricei
			for (int column = 0; column < remove.length; column++) {
				try {
					int value = Integer.parseInt(remove[column]);
					matrix[row][column] = value;
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			row++;
		}
		
		remove = line.split("\\s+");
		
		try {
			wordLength = Integer.parseInt(remove[0]); // extragere in fisier a lungimii cuvantului
		} catch(Exception e) {
			e.printStackTrace();
		}

		word = new int[wordLength];
		line = fileIn.readLine();
		remove = line.split("\\s+");
		
		// extragere din fisier a cuvantului ce va fi editat ulterior
		for (int i = 0; i < remove.length; i++) {
			try {
				int value = Integer.parseInt(remove[i]);
				word[i] = value;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			fileIn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		int result = minNumberOfEdits(matrix, word, lineNumber, columnNumber, wordLength);	
		String stringResult = String.valueOf(result);
		BufferedWriter fileOut = new BufferedWriter(new FileWriter("evaluare.out"));
		fileOut.write(stringResult);
		fileOut.close();
	}
}
