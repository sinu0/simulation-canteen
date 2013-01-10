package canteen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Cashier extends SimProcess {

	private Canteen model;

	public Cashier(Model model, String name, boolean trace) {
		super(model, name, trace);
		this.model = (Canteen) model;

	}

	@Override
	public void lifeCycle() {
		model.getIdleCashierStat().update(model.cashierIdleQueue.size());
		while (true) {
			if (model.getClientQueue().isEmpty()) {
				insertMeToIdleQueue();
				passivate();
			} else {
				Client client = getFirstClient();
				LinkedList<String> menu = client.getMenu();
				if (checkClinetList(menu)) // jezeli bedzie czegos brakowac to
											// rowniez przesle ta informacej do
											// kuchni
				{
					updateStorage(menu);
				    double serviceTime = model.getClientServiceTime();
					hold(new TimeSpan(serviceTime,TimeUnit.SECONDS));
					model.getServiceTimeStat().update(serviceTime);
					client.setHasMeal(true);
				
					
				} else {
					if (client.getProbabilityOfQuit() < 0.90) {// klient wybiera
																// nowe menu
																// albo wychodzi
																// z lokolu
						hold(new TimeSpan(model.getClientDecitionTime(),TimeUnit.SECONDS));
						if(client.selectMenuOnceAgain(model.getDishesStorage()
								.getAvailableList())==true && checkClinetList(client.getMenu())) //jezeli brakowala czegos dla niego to wychodzi
						{ 
							menu = client.getMenu();
							updateStorage(menu);
							hold(new TimeSpan(model.getClientServiceTime(),TimeUnit.SECONDS));
							client.setHasMeal(true);
							
							
							
						}
					}
				}
				client.activate();
			}

		}
	}

	private void updateStorage(LinkedList<String> menu) {
		for (String string : menu) {
			model.getDishesStorage().subStorage(string);
			model.getFoodServed().put(string, model.getFoodServed().get(string)+1);
		}
		
	}

	private void insertMeToIdleQueue() {
		model.change.firePropertyChange("idleCashierQueue", model
				.getCashierIdleQueue().size(),
				model.cashierIdleQueue.size() + 1);
		model.getIdleCashierStat().update(model.cashierIdleQueue.size());
		model.cashierIdleQueue.insert(this);
	}

	private Client getFirstClient() {
		
		model.change.firePropertyChange("clientQueue", model.getClientQueue()
				.size(), model.clientQueue.size() - 1);
		model.getQueueToCashier().update(model.clientQueue.size());
		Client c = model.clientQueue.first();
		model.clientQueue.remove(c);
		return c;
	}

	private boolean checkClinetList(LinkedList<String> list) {
		HashMap<String, Integer> dishList = model.getDishesStorage()
				.getStorage();
		boolean state = true;
		for (String string : list) {
			if (dishList.get(string) <= 0) {
				state = false;

			}
			if (dishList.get(string) <= model.getMinMealCount()) {
				model.getKitchen().addDishToPrepare(string);
					model.getKitchen().activate();

			}
		}
		return state;
	}

}
