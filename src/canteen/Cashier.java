package canteen;

import java.util.HashMap;
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
				Client client = getFirstClient();
				LinkedList<String> menu = client.getMenu();
				if (checkClinetList(menu)) // jezeli bedzie czegos brakowac to
											// rowniez przesle ta informacej do
											// kuchni
				{
					client.setHasMeal(true);
					client.activate(new TimeSpan(0));
				} else {//klient wybiera nowe menu albo wychodzi z lokolu
					client.selectMenuOnceAgain(model.getDishesStorage()
							.getAvailableList());
					client.setHasMeal(true);
					client.activate(new TimeSpan(0));

				}

			}

		}

	}

	private void insertMeToIdleQueue() {
		model.change.firePropertyChange("idleCashierQueue", model.cashierIdelQueue.size(), model.cashierIdelQueue.size()+1);
		model.cashierIdelQueue.insert(this);
	}

	private Client getFirstClient() {
		model.change.firePropertyChange("clientQueue", model.clientQueue.size(), model.clientQueue.size()-1);
		return model.clientQueue.first();
	}

	private boolean checkClinetList(LinkedList<String> list) {
		HashMap<String,Integer> dishList = model.getDishesStorage().getStorage();
		boolean state=true;
		for (String string : list) {
			if(dishList.get(string)<=0){
				if(dishList.get(string)<=model.getMinMealCount()) {
					model.getKitchen().addDishToPrepare(string);
					model.getKitchen().activate();
				}
				state = false;
			}
		}
		return state;
	}

}
