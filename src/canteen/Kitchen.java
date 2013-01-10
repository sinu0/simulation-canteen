package canteen;

import java.util.LinkedList;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

/**
 * @author mar
 * Klasa odpowiedzialna za przeplywem jedzenia od kucharzy oraz nadawaniem zadan dla kucharzy
 */
public class Kitchen extends SimProcess {
	private LinkedList<String> dishToPrepare;
	private LinkedList<String> dishIsPreparing;
	private Canteen model;

	/**
	 * Konstruktor
	 * @param model przyjmuje null poniewaz ta klasa reprezentuje symulacje
	 * @param name nazwa symulacji
	 * @param trace czy pokazywac w pliku raportu
	 */
	public Kitchen(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		model = (Canteen) arg0;
		dishToPrepare = new LinkedList<String>();
		dishIsPreparing = new LinkedList<String>();


	}

	/* (non-Javadoc)
	 * @see desmoj.core.simulator.SimProcess#lifeCycle()
	 */
	@Override
	/**
	 * Cykl zycia kuchnii
	 */
	public void lifeCycle() {
		while (true) {
			if (dishToPrepare.isEmpty()) { 
				passivate();
			} else {
				if (!model.cookIdleQueue.isEmpty()) {
					Cook cook = getFirstCook();
					cook.giveMeTask(dishToPrepare.getFirst());
					addDishIsPreparing(dishToPrepare.getFirst());
					cook.activate();
				} else {
					passivate(); // jezeli kuchaz sie zwolini to aktywuje
									// kuchnie
				}

			}
		}
	}

	/**
	 * Dodaje zlecenie przygotowania dania
	 * @param name nazwa dania
	 * @return true w przypadku dodania skladnika do listy do wykoania, false w przypdku kiedy skladnik jest juz przygotowywany lub jest juz dodany
	 */
	public boolean addDishToPrepare(String name) {
		if (dishToPrepare.contains(name) || dishIsPreparing.contains(name)) {
			return false;
		} else {
			model.change.firePropertyChange("AddToPrepare", name, name);
			dishToPrepare.add(name);
				activate(); // po dodaniu kunia zostaje aktywowana
			return true;
		}
	}

	/**
	 * Przygotowywanie dania
	 * @param name nazwa dania
	 */
	public void addDishIsPreparing(String name) {
		dishToPrepare.remove(name);
		dishIsPreparing.add(name);
	}
	/**
	 * Usuwa jedzenie z listy przygotowywnych dan
	 * @param name
	 */
	public void dishDone(String name){
		dishIsPreparing.remove(name);
	}

	/**
	 * Zwraca pierwszego kucharza z kolejki
	 * @return pierwszego kucharza z kolelki
	 */
	public Cook getFirstCook() {
		model.change.firePropertyChange("cookIdleQueue",
				model.cookIdleQueue.size(), model.cookIdleQueue.size() - 1);
		model.getIdleCookStat().update(model.cookIdleQueue.size());
		Cook c = model.cookIdleQueue.first();
		model.cookIdleQueue.remove(c);
		return c;

	}

}
