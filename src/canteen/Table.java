package canteen;

import java.util.ArrayList;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;


public class Table extends Entity{

	protected ArrayList<Client> clientsAtTheTable;
	int hasSeats;
	public Table(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}
	public void addClient(Client client){
		clientsAtTheTable.add(client);
		
	}
	
	public void removeClient(Client client){
		clientsAtTheTable.remove(client);
	}
	
	public int getClientCount(){
		return clientsAtTheTable.size();
	}
	public int getEmpySeatCount(){
		return hasSeats-clientsAtTheTable.size();
	}

}
