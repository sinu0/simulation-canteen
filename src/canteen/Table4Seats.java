package canteen;

import java.util.ArrayList;

import desmoj.core.simulator.Model;

public class Table4Seats extends Table {

	
	/**
	 * Konstruktor
	 * @param arg0 przyjmuje null poniewaz ta klasa reprezentuje symulacje
	 * @param arg1 nazwa symulacji
	 * @param arg2 czy pokazywac w pliku raportu
	 */
	public Table4Seats(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		this.clientsAtTheTable=new ArrayList<>();
		hasSeats=4;
	}

}
