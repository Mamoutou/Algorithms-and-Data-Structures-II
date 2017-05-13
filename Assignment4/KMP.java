import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class KMP {

	private static String pattern;

	public KMP(String pattern) {
		this.pattern = pattern;
	}

	public static int search(String txt) {

		int M = pattern.length();
		int N = txt.length();
		int indexFound;
		int lps[] = new int[M];
		int j = 0;

		computeLPSArray(pattern, M, lps);

		int i = 0;
		while (i < N) {
			if (pattern.charAt(j) == txt.charAt(i)) {
				j++;
				i++;
			}
			if (j == M) {

				indexFound = i - j;
				j = lps[j - 1];
				// return the index of the pattern 
				return indexFound;
			}

			else if (i < N && pattern.charAt(j) != txt.charAt(i)) {
				if (j != 0)
					j = lps[j - 1];
				else
					i = i + 1;
			}
		}
		// if we ever get here, the pattern is not in the text, return the length of the text 
		return N;

	}

	private static void computeLPSArray(String pat, int M, int lps[]) {
		int len = 0;
		int i = 1;
		lps[0] = 0;
		while (i < M) {
			if (pat.charAt(i) == pat.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			} else {
				if (len != 0) {
					len = lps[len - 1];
				} else {
					lps[i] = len;
					i++;
				}
			}
		}
	}
      
  	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text+=s.next()+" ";
			}
			
			for(int i=1; i<args.length ;i++){
				KMP k = new KMP(args[i]);
				int index = search(text);
				if(index >= text.length())System.out.println(args[i]+ " was not found.");
				else System.out.println("The string \""+args[i]+ "\" was found at index "+index + ".");
			}
			
			//System.out.println(text);
			
		}else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
		
		
	}
}