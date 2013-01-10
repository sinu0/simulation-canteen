package canteen;

import java.util.ArrayList;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;

/**
 * @author mar
 *	Klasa reprezentujaca stolik kazdy stolik posiada 2 lub 4 siedzenia
 */
public class Table  {

	protected ArrayList<Client> clientsAtTheTable;
	int hasSeats;
	Canteen model;
	
	/**
	 * Konstruktor
	 * @param arg0 przyjmuje null poniewaz ta klasa reprezentuje symulacje
	 * @param arg1 nazwa symulacji
	 * @param arg2 czy pokazywac w pliku raportu
	 */
	public Table(Model arg0, String arg1, boolean arg2) {
		this.model=(Canteen)arg0;
	}

	/**
	 * Dodaje klientw do stolika
	 * @param client ktory chce usiasc przy stoliku
	 * @return true w przypdku kiedy kilient usiadł false w przypdku kiedy nie ma miejsca dla klienta
	 */
	public boolean addClient(Client client) {
		if (clientsAtTheTable.size() >= hasSeats)
			return false;
		clientsAtTheTable.add(client);
		return true;

	}

	/**
	 * Usuwa klienta ze stolika
	 * @param client kroty ma zamiar odejsc z stolika
	 */
	public void removeClient(Client client) {
		
		clientsAtTheTable.remove(client);
	}

	/**
	 * Zwraca licze siedzacych klientow przy stoliku
	 * @return liczbe siedzacych klientow przy stoliku
	 */
	public int getClientCount() {
		return clientsAtTheTable.size();
	}

	/**
	 * Zwraca liczbe wolnych miejsc
	 * @return liczbe wolnych miejsc
	 */
	public int getEmpySeatCount() {
		return hasSeats - clientsAtTheTable.size();
	}

	/**
	 * Zwraca liczbe miejsc
	 * @return liczbe dostepnych miejsc
	 */
	public int getSeatCunt() {
		return hasSeats;
	}

}
