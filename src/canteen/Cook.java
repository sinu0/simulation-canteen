package canteen;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Cook extends SimProcess {
	private Canteen model;
	private String taskToDo=null;

	public Cook(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		model = (Canteen) arg0;
	}

	@Override
	public void lifeCycle() {
		while (true) {
			if (taskToDo != null) { // jezeli wszystko bedzie ok to ten warunek
									// zawsze bedzi spelniony!
				hold(new TimeSpan(model.getMealPrepareTime(), TimeUnit.SECONDS));
				model.getDishesStorage().addStorage(taskToDo, 5);
				model.getKitchen().dishDone(taskToDo);
				taskToDo = null;
			} else {
				insertMeToIdleQueue();
				model.getKitchen().activateAfter(this);
				passivate();// zostanie zaktywownay przez kuchnie jezeli beda
				// dostepne dania do przygotowania
			}
			
			
		}

	}

	public void insertMeToIdleQueue() {
		model.change.firePropertyChange("cookIdleQueue",
				model.cookIdleQueue.size(), model.cookIdleQueue.size() + 1);
		model.cookIdleQueue.insert(this);
	}

	public void giveMeTask(String name) {
		taskToDo = new String(name);
	}

}
