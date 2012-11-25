package canteen;

import java.util.LinkedList;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

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
				if(checkClinetList(menu)) //jezeli bedzie czegos brakowac to rowniez przesle ta informacej do kuchni	
				{
					client.setHasMeal(true);
					client.activate(new TimeSpan(0));
				}
				
			}

		}

	}

	private void insertMeToIdleQueue() {
		model.cashierIdelQueue.insert(this);
	}
	private Client getFirstClient(){
		return model.clientQueue.first();
	}
	private boolean checkClinetList(LinkedList<String> list){
		return false;
	}

}
