package canteen;

import java.util.ArrayList;

import desmoj.core.simulator.Model;

public class Table2Seats extends Table{

	
	/**
	 * Konstruktor
	 * @param model przyjmuje null poniewaz ta klasa reprezentuje symulacje
	 * @param name nazwa symulacji
	 * @param trace czy pokazywac w pliku raportu
	 */
	public Table2Seats(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		this.clientsAtTheTable=new ArrayList<>();
		hasSeats=2;
	}

	

}
