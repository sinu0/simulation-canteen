package canteen;

import java.util.LinkedList;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class Kitchen extends SimProcess {
	private LinkedList<String> dishToPrepare;
	private LinkedList<String> dishIsPreparing;
	private Canteen model;

	public Kitchen(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		model = (Canteen) arg0;
		dishToPrepare = new LinkedList<String>();
		dishIsPreparing = new LinkedList<String>();

	}

	@Override
	public void lifeCycle() {
		while (true) {
			if (dishToPrepare.isEmpty()) { // niema co przygotowac
				passivate();
			} else {
				if (!model.cookIdleQueue.isEmpty()) {
					Cook cook = getFirstCook();
					cook.activate();
				} else {
					passivate(); // jezeli kuchaz sie zwolini to aktywuje
									// kuchnie
				}

			}
		}
	}

	public boolean addDishToPrepare(String name) {
		if (dishToPrepare.contains(name) || dishIsPreparing.contains(name)) {
			return false;
		} else {
			dishToPrepare.add(name);
			activate(); // po dodaniu kunia zostaje aktywowana
			return true;
		}
	}

	public void addDishIsPreparing(String name) {
		dishToPrepare.remove(name);
		dishIsPreparing.add(name);
	}

	public Cook getFirstCook() {
		model.change.firePropertyChange("cookIdleQueue",
				model.cookIdleQueue.size(), model.cookIdleQueue.size() - 1);
		return model.cookIdleQueue.first();

	}

}
