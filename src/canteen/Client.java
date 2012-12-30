package canteen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Client extends SimProcess {
	private LinkedList<String> selectedMenu;
	private Canteen model;

	private int groupNumber;
	private int maxAceptableQueue;
	private double averagePrice;
	private double probabilityOfQuit;

	private boolean isMemberOfGroup;
	private boolean isPrivileged;
	private boolean hasMeal;
	private boolean stayInCanteen = true;
	private long client = 0;
	private double clientCharacter;
	private Table table; // referencja na zajete miejsce w stolowce

	public Client(Model model, String name, boolean trace) {
		super(model, name, trace);
		this.model = (Canteen) model;
		probabilityOfQuit = this.model.getProbabilityOfQuitOnNewMenu();
		maxAceptableQueue = (int) this.model.getClientMaxAcceptableQueue();
		averagePrice = this.model.getClientAveragePrice();
		clientCharacter = this.model.getClientTableDecision();
	}

	/* 
	 * cykl zycia klienta. Klient przechodzi przez kilka procesow decyzujnech.
	 *  W symulacji wyrózniamy 3 klientow: klient zwykly, grupa klientow 
	 *  oraz klient uprzywilejowany.  
	 */
	@Override
	public void lifeCycle() {
		if (stayInCanteen) { // jezeli jest czlonkiem grupy to proces decyzji juz przeszedl
			if (model.getDishes().averagePrice < averagePrice
					|| isMemberOfGroup || isPrivileged) {

				if (maxAceptableQueue > model.clientQueue.size()
						|| isMemberOfGroup || isPrivileged) {

					if (model.getAvailableSeats() - model.clientQueue.size() > 0
							|| isMemberOfGroup) {
						selectMenu();// wybiera menu
						//if (!isMemberOfGroup)
						{
						  //model.getClientStayed().update();
						}
						model.getClientStayed().update();
						if (isPrivileged)
						{
						    System.out.println("Jestem uprzywilejowany");
							addMeFirst();// jeżeli klient jest uprzywilejowany
											// zostaje dodany na poczatek
											// kolejki
						}
						else
							addMeToQueue();
						if (!model.cashierIdleQueue.isEmpty()) {
							Cashier cashier = getFirstCashier();
							cashier.activate();// aktywywuje kasjerke
						}

						passivate();// czeka az kasjerka bedzie dostepna,
									// kasjerka
									// sama wyciaga go z kolejki
						if (hasMeal) {// czy dosotal jedzenie
							//model.getClientStayed().update();
							if (model.getAvailableSeats() <= 0) { //jezeli jest brak siedzen klient oczekuje w kolejce na wolne siedzenie
								addWaitingForTableQueue();
								System.out.println("gdfg");
								passivate(); // czeaka az zwolnia sie miejsca w
												// stolowce

							}
							
							// zajmuje miejsce przy stoliku
							for (Table table : model.getTables()) {
								if(this.table==null)
									this.table=table;
								else
								if (table.getEmpySeatCount()>0) {
									if(this.table.getClientCount()>table.getClientCount())
										this.table=table;
									
									
								}

							}
							table.addClient(this);//klient sieada
							model.change.firePropertyChange("table", model.getSeatsCount(), model.getSeatsCount()-1);
							double eatTime = model.getMealEatTime();
							model.getMealEatTimeStat().update(eatTime);
							hold(new TimeSpan(eatTime,
									TimeUnit.SECONDS));// spozywa jedzenie
							// odchodzi od stolika
							table.removeClient(this);
							model.change.firePropertyChange("table", model.getSeatsCount()-1, model.getSeatsCount());
							if (!model.clientNoPleceQueue.isEmpty()) {
								//kiedy klient konczy jedzenie i ktos czeka na wolne miejsce przy stoliku aktywuje pierwszego z koljeki
								Client client = getFirstFromWaitingQueue();
								client.activate();
							}

						} else{
							Canteen.clientLeftOnInitCount++;
							model.getClientLeftBecOfNoFood().update();
							System.out.println("Nie ma dla mnie jedzenia");
						}
					} else{
						Canteen.clientLeftOnInitCount++;
						model.getClientLeftBecOfNoPlace().update();
					System.out.println("Brak miejsca");}
				} else{
					Canteen.clientLeftOnInitCount++;
					model.getClientLeftBecOfQueue().update();
				System.out.println("Za dluga kolejka ");}
			} else{
				Canteen.clientLeftOnInitCount++;
			model.getClientLeftBecOfPrice().update();
			System.out.println("cena jest za wysoka");

		}}

	}

	/**
	 * Klient wybiera menu
	 */
	public void selectMenu() {
		Dishes dishes = model.getDishes();
		selectedMenu = new LinkedList<String>();
		Random rand = new Random();

		LinkedList<String> lD = dishes.getDish(); // dish
		selectedMenu.add(lD.get(rand.nextInt(lD.size())));

		LinkedList<String> lS = dishes.getSoup();// soup
		selectedMenu.add(lS.get(rand.nextInt(lS.size())));

		LinkedList<String> lDr = dishes.getDrink();// drink
		selectedMenu.add(lDr.get(rand.nextInt(lDr.size())));

	}
	
	/**
	 * Metoda ktora ustawia czy klient posiada jedzenie aktywowana zostaje przez kasjerke
	 * @param meal true or false (klient ma danie albo nie)
	 */
	public void setHasMeal(boolean meal) {
		hasMeal = meal;
	}
	
	/**
	 * Zwaraca true wprzypdku wybrania nowego menu przeciwnym przypdku 
	 * metoda zwraca false co znaczy ze klient nie wybral nowego menu 
	 * oraz wychodzi z lokalu
	 * 
	 * @param avaliableDishes jest to lista dostepnych w danej chwili składikow 
	 * @return true or false
	 */
	public boolean selectMenuOnceAgain(
			HashMap<String, LinkedList<String>> avaliableDishes) {
		try {
			selectedMenu = new LinkedList<String>();
			Random rand = new Random();

			LinkedList<String> lD = avaliableDishes.get("dish"); // dish
			selectedMenu.add(lD.get(rand.nextInt(lD.size())));

			LinkedList<String> lS = avaliableDishes.get("soup");// soup
			selectedMenu.add(lS.get(rand.nextInt(lS.size())));

			LinkedList<String> lDr = avaliableDishes.get("drink");// drink
			selectedMenu.add(lDr.get(rand.nextInt(lDr.size())));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public LinkedList<String> getMenu() {
		return selectedMenu;
	}

	public void addMeToQueue() {
		model.change.firePropertyChange("clientQueue",
				model.clientQueue.size(), model.clientQueue.size() + 1);
		model.clientQueue.insert(this);
	}

	public Client getFirstFromWaitingQueue() {
		model.change.firePropertyChange("clientNoPlaceQueue",
				model.clientNoPleceQueue.size(),
				model.clientNoPleceQueue.size() - 1);
		Client c = model.clientNoPleceQueue.first();
		model.clientNoPleceQueue.remove(c);
		return c;
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
		if (model.clientQueue.isEmpty())
			model.clientQueue.insert(this);
		else
			model.clientQueue.insertAfter(model.clientQueue.first(), this);
	}

	public Cashier getFirstCashier() {
		model.change.firePropertyChange("idelCashier",
				model.cashierIdleQueue.size(),
				model.cashierIdleQueue.size() - 1);
		Cashier c = model.cashierIdleQueue.first();
		model.cashierIdleQueue.remove(c);
		return c;
	}

	public double getProbabilityOfQuit() {
		return probabilityOfQuit;
	}

	public void setPrivileged(boolean set) {
		isPrivileged = set;
	}

	public void setMemberOfGroup(boolean set, int number) {
		isMemberOfGroup = set;
		groupNumber = number;
	}

	public int decisionPrice() {
			int dec = (model.getDishes().averagePrice < averagePrice) ? 1 : 0; // ilosc punktow symuluje decyzje w grupie, jezeli true dec:=1 else dec:=0
			return dec;
	}
	public int decisionMaxQueue() {
		int dec= (maxAceptableQueue > model.clientQueue.size()) ? 1 : 0;
		return dec;
	}

	public void setStayInCanteen(boolean set) {
		stayInCanteen = set;
	}

}
