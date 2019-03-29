package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.*;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	
	private Dictionary model;
	
	public void setModel(Dictionary model) {
		this.model = model;
		btnClear.setDisable(true);
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbxLang;

    @FXML
    private TextArea txtWrite;

    @FXML
    private Button btnSpell;

    @FXML
    private TextArea txtResult;

    @FXML
    private Label lblErr;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblTime;

    @FXML
    void doClearText(ActionEvent event) {
    	txtWrite.clear();
    	txtResult.clear();
    	lblErr.setText("  ");
    	lblTime.setText("  ");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	double t = System.nanoTime();
    	btnClear.setDisable(false);
    	txtResult.clear();
        int err = 0;
        String st = "";
    	model.loadDictionary(cbxLang.getValue());
    	
    	List<String> lista = new LinkedList<String>();
    	String testo = txtWrite.getText().toLowerCase().replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", " ");
    	String s[] = testo.split("\\s+");
    	
    	for (int i=0; i<s.length; i++)
    		if (s[i].compareTo("")!=0)
    			lista.add(s[i].trim());
    		
    	List<RichWord> rw = model.spellCheckTextDichotomic(lista);
    	
    	for (RichWord temp : rw)
    		if (!temp.isCorretta()) {
    			err++;
    			st += temp.getParola()+"\n";
    		}
    	
    	txtResult.appendText(st);
    	lblErr.setText("The text contains "+err+" errors");
    	lblTime.setText("Spell check completed in "+(System.nanoTime()-t)/1000000000+" seconds");
    }

    @FXML
    void initialize() {
    	assert cbxLang != null : "fx:id=\"cbxLang\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtWrite != null : "fx:id=\"txtWrite\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErr != null : "fx:id=\"lblErr\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        cbxLang.getItems().addAll("English","Italian");
		cbxLang.setValue("English");
    }
    
}
