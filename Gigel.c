
/**
 * @author Moisa Anca-Elena, 321CA
 * Palindrom
 */

#include <stdio.h>
#include <string.h>

/**
 * Functie care face interschimbul intre elementele toate elementele unui
 * vector de char-uri, care sunt cuprinse intre cei 2 indici primiti ca parametru
 * (beginning si end)
 */
int permute(char word[], int beginning, int end, int count) {
	int i;
	for (i = beginning; i < end; i++) {
		char aux[350];
		aux[i] = word[i];
		word[i] = word[i + 1];
		word[i + 1] = aux[i];
	}
	count += end - beginning;
	return count;
}

/**
 * Functie ce cauta daca o litera se mai gaseste in cuvant
 */
int charPosition(char word[], int character, int beginning, int end) {
	int i;
	for (i = end - 1; i >= beginning; i--) {
		if (word[i] == character) {
			return i;
		}
	}
	return -1; // litera se afla doar o data in cuvant
}

/**
 * Genereaza numarul minim de inversiuni
 */
int computePalindrome(char word[], int count) {
	count = 0;
	int i, len, position;
	len = strlen(word);
	for (i = 0; i < len - 1; i++) {
		position = charPosition(word, word[i], i + 1, len - i);

		if (position == - 1) {
			// cuvantul va fi permutat spre mijloc
			count = permute(word, i, len / 2, count);
			return count;
		}
		else
			// cuvantul va fi permutat catre pozitia in care se 
			// gaseste 'oglinda' literei curente 
			count = permute(word, position, len - 1 - i, count);
	}
	return count;
}

/**
 * Verifica daca un numar este palindrom
 */
int isPalindrome(char word[]) {
	int beginning = 0;
	int end = strlen(word) - 1;

	while (beginning < end) {
		if (word[beginning] != word[end]) {
			return 0; // cuvantul nu este palindrom
		}
		beginning ++;
		end --;
	}
	return 1; // cuvantul este palindrom
}

int main() {
	char word[350];
	int wordsNumber, i, count;
	count = 0;

	FILE *f;
	FILE *g;

	f = fopen("joc.in", "r");
	g = fopen("joc.out", "w");
	
	// extragere din fisier numarul de cuvinte generate
	fscanf(f, "%d", &wordsNumber);
	// extragere din fisier fiecare cuvant in parte
	for (i = 0; i < wordsNumber; i++) {
		fscanf(f, "%s", word);

		count = computePalindrome(word, count);
		if (isPalindrome(word))
			fprintf(g, "%d\n", count);
		else
			fprintf(g , "-1\n");
	}

	fclose(f);
	fclose(g);
	return 0;
}

