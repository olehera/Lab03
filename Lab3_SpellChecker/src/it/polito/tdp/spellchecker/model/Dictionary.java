package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	
	private List<String> diz;
	
	public Dictionary() {
		diz = new LinkedList<String>();
	}

	public void loadDictionary(String language) {
		try {
			FileReader fr = new FileReader("rsc/"+language+".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
				diz.add(word);
			}
			br .close();
			} catch (IOException e ) {
			System.out.println( "Errore nella lettura del file!" );
			}
	}

	public List<RichWord> spellCheckText(List<String> inputTextList ) {
		
		List<RichWord> parole = new LinkedList<RichWord>();
		
		for (String temp : inputTextList)
			if (diz.contains(temp))
				parole.add(new RichWord(temp, true));
			else
				parole.add(new RichWord(temp, false));
		
		return parole;
	}
	
}
