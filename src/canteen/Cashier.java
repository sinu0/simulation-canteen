package canteen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

/**
 * @author mar
 * Klasa reprezentujaca kasjerke w symulacji
 */
public class Cashier extends SimProcess {

	private Canteen model;

	/**
	 * Konstruktor
	 * @param model przyjmuje null poniewaz ta klasa reprezentuje symulacje
	 * @param name nazwa symulacji
	 * @param trace czy pokazywac w pliku raportu
	 */
	public Cashier(Model model, String name, boolean trace) {
		super(model, name, trace);
		this.model = (Canteen) model;

	}

	/* (non-Javadoc)
	 * @see desmoj.core.simulator.SimProcess#lifeCycle()
	 */
	@Override
	/**
	 * Cykl zycia kasjerki
	 */
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

	/**
	 * Metoda ktora aktualizuje magazyn z jedzeniem
	 * @param menu menu klienta
	 */
	private void updateStorage(LinkedList<String> menu) {
		for (String string : menu) {
			model.getDishesStorage().subStorage(string);
			model.getFoodServed().put(string, model.getFoodServed().get(string)+1);
		}
		
	}

	/**
	 * Metoda ktora umieszcza kasjerke w kolejce cashierIdleQueue
	 */
	private void insertMeToIdleQueue() {
		model.change.firePropertyChange("idleCashierQueue", model
				.getCashierIdleQueue().size(),
				model.cashierIdleQueue.size() + 1);
		model.getIdleCashierStat().update(model.cashierIdleQueue.size());
		model.cashierIdleQueue.insert(this);
	}

	/**
	 * @return pierwszego klienta z kolejki
	 */
	private Client getFirstClient() {
		
		model.change.firePropertyChange("clientQueue", model.getClientQueue()
				.size(), model.clientQueue.size() - 1);
		model.getQueueToCashier().update(model.clientQueue.size());
		Client c = model.clientQueue.first();
		model.clientQueue.remove(c);
		return c;
	}

	/**
	 * Metoda sprawdza menu klienta czy jego wybrane skladniki sa dostepne jezeli tak to zwraca true jezeli nie to false
	 * jednoczsnie jezeli kasjerka natrafi na jakis skladnik ktory brakuje w stolowce to informuje otym kuchenie aby to wykonala
	 * @param list menu klienta
	 * @return true or false
	 */
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
