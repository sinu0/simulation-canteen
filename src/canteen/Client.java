package canteen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Client extends SimProcess {

	private boolean isMemberOfGroup;
	private boolean isPrivileged;
	private int groupNumber;
	private LinkedList<String> selectedMenu;
	private Canteen model;
	private double averagePrice;
	private int maxAceptableQueue;
	private boolean hasMeal;

	// private int
	public Client(Model model, String name, boolean trace) {
		super(model, name, trace);
		this.model = (Canteen) model;
	}

	@Override
	public void lifeCycle() {
		if (model.getAveragePrice() > averagePrice) {
			if (maxAceptableQueue < model.clientQueue.size()) {
				if (model.getAvailabaleSeats() - model.clientQueue.size() > 0) {

					selectMenu();// wybiera menu
					if (isPrivileged)

						addMeToQueue();//
					getFirstCashier();
					model.getCashier().activate();// aktywywuje kasjerke
					passivate();// czeka az kasjerka bedzie dostepna kasjerka
								// sama wyciaga go z kolejki
					if (hasMeal) {// czy dosotal jedzenie
						if (model.getAvailabaleSeats() > 0) {
							model.setAvaiableSeats(model.getAvaiableSeats() - 1);// zajmuje
																					// miejsce
																					// przy
																					// stoliku
							hold(new TimeSpan(model.getMealEatTime(),
									TimeUnit.SECONDS));// spozywa jedzenie
							model.setAvaiableSeats(model.getAvaiableSeats() + 1);// odchodzi
																					// od
																					// stolika
							// hold(new Span(random time to live ))//wychodzi z
							// lokalu
						}
					} else
						Canteen.clientLeftOnInitCount++;

				} else
					Canteen.clientLeftOnInitCount++;
			} else
				Canteen.clientLeftOnInitCount++;
		} else
			Canteen.clientLeftOnInitCount++;
	}

	public void selectMenu() {
		Dishes dishes = model.getDishes();
		selectedMenu = new LinkedList<String>();
		Random rand = new Random();
		LinkedList<String> lD = dishes.getDish(); // dish
		selectedMenu.add(lD.get(rand.nextInt(lD.size())));
		LinkedList<String> lS = dishes.getDish();// soup
		selectedMenu.add(lS.get(rand.nextInt(lS.size())));
		LinkedList<String> lDr = dishes.getDish();// drink
		selectedMenu.add(lDr.get(rand.nextInt(lDr.size())));

	}

	public void setHasMeal(boolean meal) {
		hasMeal = meal;
	}

	public void selectMenuOnceAgain(
			HashMap<String, LinkedList<String>> avaliableDishes) {

		selectedMenu = new LinkedList<String>();
		Random rand = new Random();

		LinkedList<String> lD = avaliableDishes.get("dish"); // dish
		selectedMenu.add(lD.get(rand.nextInt(lD.size())));
		LinkedList<String> lS = avaliableDishes.get("soup");// soup
		selectedMenu.add(lS.get(rand.nextInt(lS.size())));
		LinkedList<String> lDr = avaliableDishes.get("drink");// drink
		selectedMenu.add(lDr.get(rand.nextInt(lDr.size())));
	}

	public LinkedList<String> getMenu() {
		return selectedMenu;
	}

	public void addMeToQueue() {
		model.change.firePropertyChange("clientQueue",
				model.clientQueue.size(), model.clientQueue.size() + 1);
		model.clientQueue.insert(this);
	}

	public void addWaitingForTableQueue() {
		model.change.firePropertyChange("clientNoPlaceQueue",
				model.clientNoPleceQueue.size(),
				model.clientNoPleceQueue.size() + 1);
		model.clientNoPleceQueue.insert(this);

	}

	public void addMeFirst() {
		model.change.firePropertyChange("clientQueue",
				model.clientQueue.size(), model.clientQueue.size() + 1);
		model.clientQueue.insertAfter(model.clientQueue.first(), this);
	}

	public Cashier getFirstCashier() {
		model.change.firePropertyChange("idelCashier",
				model.cashierIdelQueue.size(),
				model.cashierIdelQueue.size() + 1);
		return model.cashierIdelQueue.first();
	}

}
