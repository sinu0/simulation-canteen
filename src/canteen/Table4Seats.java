package canteen;

import java.util.ArrayList;

import desmoj.core.simulator.Model;

public class Table4Seats extends Table {

	public Table4Seats(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		this.clientsAtTheTable=new ArrayList<>();
		hasSeats=4;
	}

}
