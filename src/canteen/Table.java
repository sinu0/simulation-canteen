package canteen;

import java.util.ArrayList;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;

public class Table {

	protected ArrayList<Client> clientsAtTheTable;
	int hasSeats;
	Canteen model;
	public Table(Model arg0, String arg1, boolean arg2) {
		this.model=(Canteen)arg0;
	}

	public boolean addClient(Client client) {
		if (clientsAtTheTable.size() >= hasSeats)
			return false;
		clientsAtTheTable.add(client);
		return true;

	}

	public void removeClient(Client client) {
		if(this instanceof Table2Seats)
		
		clientsAtTheTable.remove(client);
	}

	public int getClientCount() {
		return clientsAtTheTable.size();
	}

	public int getEmpySeatCount() {
		return hasSeats - clientsAtTheTable.size();
	}

	public int getSeatCunt() {
		return hasSeats;
	}

}
