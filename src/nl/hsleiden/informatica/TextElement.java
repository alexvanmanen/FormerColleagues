package nl.hsleiden.informatica;

import java.util.List;

public class TextElement {

	private String text = "Designing object oriented software is hard, and designing reusable object oriented software is even harder.";
	private Colleague colleague;
	
	public TextElement(Colleague c) {
		this.colleague = c;
	}
	


	public void setColleague(Colleague c) {
        this.colleague = c;
	}
	
	public String getText(){
		return text;
	}

}
