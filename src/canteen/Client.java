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
	private boolean stayInCanteen=true;
	private long client=0;
	public Client(Model model, String name, boolean trace) {
		super(model, name, trace);
		this.model = (Canteen) model;
		probabilityOfQuit = this.model.getProbabilityOfQuitOnNewMenu();
		maxAceptableQueue = (int)this.model.getClientMaxAcceptableQueue();
		averagePrice = this.model.getClientAveragePrice();
	}

	@Override
	public void lifeCycle() {
		if(stayInCanteen){ //jezeli jest czlonkiem grupy to proces decyzji przeszedl
		if (model.getDishes().averagePrice < averagePrice || isMemberOfGroup || isPrivileged) {
			
			if (maxAceptableQueue > model.clientQueue.size() || isMemberOfGroup || isPrivileged) {
				
				if (model.getAvailabaleSeats() - model.clientQueue.size() > 0 || isMemberOfGroup) {
					
					selectMenu();// wybiera menu
					if (isPrivileged)
						addMeFirst();//jeżeli klient jest uprzywilejowany zostaje dodany na poczatek kolejki
					else
						addMeToQueue();
					if (!model.cashierIdleQueue.isEmpty()) {
						Cashier cashier = getFirstCashier();
						cashier.activate();// aktywywuje kasjerke
					}
					
					passivate();// czeka az kasjerka bedzie dostepna, kasjerka
								// sama wyciaga go z kolejki
					
					if (hasMeal) {// czy dosotal jedzenie
						if (model.getAvailabaleSeats() <= 0) {
							
							addWaitingForTableQueue();
							passivate(); //czeaka az zwolnia sie miejsca w stolowce
					
						}
						// zajmuje miejsce przy stoliku
						model.setAvaiableSeats(model.getAvaiableSeats() - 1);
						hold(new TimeSpan(model.getMealEatTime(),
								TimeUnit.SECONDS));// spozywa jedzenie
						// odchodzi od stolika
						model.setAvaiableSeats(model.getAvaiableSeats() + 1);
						
						if(!model.clientNoPleceQueue.isEmpty()){
							//poczym aktywuje pierwszego z kolejki czekajacych
							Client client = getFirstFromWaitingQueue();
							client.activate();
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
		
	}

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

	public void setHasMeal(boolean meal) {
		hasMeal = meal;
	}

	public boolean selectMenuOnceAgain(
		HashMap<String, LinkedList<String>> avaliableDishes) {
		try{
			selectedMenu = new LinkedList<String>();
			Random rand = new Random();

			LinkedList<String> lD = avaliableDishes.get("dish"); // dish
			selectedMenu.add(lD.get(rand.nextInt(lD.size())));
		
			LinkedList<String> lS = avaliableDishes.get("soup");// soup
			selectedMenu.add(lS.get(rand.nextInt(lS.size())));
		
			LinkedList<String> lDr = avaliableDishes.get("drink");// drink
			selectedMenu.add(lDr.get(rand.nextInt(lDr.size())));
		}
		catch(Exception e){
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
	public Client getFirstFromWaitingQueue(){
		model.change.firePropertyChange("clientNoPlaceQueue",model.clientNoPleceQueue.size(),model.clientNoPleceQueue.size()-1);
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
		if(model.clientQueue.isEmpty())
			model.clientQueue.insert(this);
		else
			model.clientQueue.insertAfter(model.clientQueue.first(), this);
	}

	public Cashier getFirstCashier() {
		model.change.firePropertyChange("idelCashier",
				model.cashierIdleQueue.size(),
				model.cashierIdleQueue.size() - 1);
		Cashier c =model.cashierIdleQueue.first();
		model.cashierIdleQueue.remove(c);
		return  c;
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
	public int decision(){

		if(model.getAvailabaleSeats() - model.clientQueue.size() >=0 ){
			int dec = (model.getDishes().averagePrice<averagePrice)? 1 : 0; //ilosc punktow symuluje decyzje w grupie, jezeli true dec:=1 else dec:=0
			 dec +=(maxAceptableQueue >model.clientQueue.size())? 1:0;
			 return dec;
		}
		else
			return 0;
		
				
	}
	public void setStayInCanteen(boolean set){
		stayInCanteen=set;
	}

}
