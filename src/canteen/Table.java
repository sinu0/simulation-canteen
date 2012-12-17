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
	public Table(Model arg0, String arg1, boolean arg2) {
		this.model=(Canteen)arg0;
	}

	/**
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
	 * @param client kroty ma zamiar odejsc z stolika
	 */
	public void removeClient(Client client) {
		
		clientsAtTheTable.remove(client);
	}

	/**
	 * @return liczb siedzacych klientow przy stoliku
	 */
	public int getClientCount() {
		return clientsAtTheTable.size();
	}

	/**
	 * @return liczbe wolnych miejsc
	 */
	public int getEmpySeatCount() {
		return hasSeats - clientsAtTheTable.size();
	}

	/**
	 * @return liczbe dostepnych siedzen
	 */
	public int getSeatCunt() {
		return hasSeats;
	}

}
