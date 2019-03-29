package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	
	private List<String> diz;
	
	public Dictionary() {
	  //  diz = new LinkedList<String>(); 
	      diz = new ArrayList<String>();
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
			System.out.println("Errore nella lettura del file!");
			}
	}

	public List<RichWord> spellCheckText(List<String> inputTextList) {
		
		List<RichWord> parole = new LinkedList<RichWord>();
		
		for (String temp : inputTextList)
			if (diz.contains(temp))
				parole.add(new RichWord(temp, true));
			else
				parole.add(new RichWord(temp, false));
		
		return parole;
	}
	
    public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		
		List<RichWord> parole = new LinkedList<RichWord>();
		
		for (String s : inputTextList) {
			boolean flag = false;
			for (String d : diz)
				if (d.compareTo(s)==0) {
					flag = true;
					continue;
				}
					
				parole.add(new RichWord(s, flag));
		}
		
		return parole;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		
		List<RichWord> parole = new LinkedList<RichWord>();
	
		for (String s : inputTextList) {
			boolean flag = false;
			int min = 0;
			int mediana = 0;
			int max = diz.size()-1;
			
			while (min <= max) {
				mediana = (min+max)/2;
				
				if (diz.get(mediana).compareTo(s)==0) {
					flag = true;
					break;
				}
				if (diz.get(mediana).compareTo(s) < 0)
					min = mediana+1;
				else
					max = mediana-1;
			}
		    parole.add(new RichWord(s, flag));
		}
		
		return parole;
	}
}