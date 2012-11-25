package canteen;

import java.util.LinkedList;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class Cashier extends SimProcess {

	private Canteen model;

	public Cashier(Model model, String name, boolean trace) {
		super(model, name, trace);
		model = (Canteen) model;
	}

	@Override
	public void lifeCycle() {
		while (true) {
			if (model.clientQueue.isEmpty()) {
				insertMeToIdleQueue();
				passivate();
			} else {
				Client client=getFirstClient();
				LinkedList<String> menu = client.getMenu();
				
				
			}

		}

	}

	private void insertMeToIdleQueue() {
		model.cashierIdelQueue.insert(this);
	}
	private Client getFirstClient(){
		return model.clientQueue.first();
	}
	private 

}
