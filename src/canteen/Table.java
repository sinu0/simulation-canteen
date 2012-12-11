package canteen;

import java.util.ArrayList;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;

public class Table {

	protected ArrayList<Client> clientsAtTheTable;
	int hasSeats;

	public Table(Model arg0, String arg1, boolean arg2) {
		// TODO Auto-generated constructor stub
	}

	public boolean addClient(Client client) {
		if (clientsAtTheTable.size() >= hasSeats)
			return false;
		clientsAtTheTable.add(client);
		return true;

	}

	public void removeClient(Client client) {
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
