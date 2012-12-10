package canteen;

import gui.AnimPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Canteen extends Model implements Runnable 
{

	static int clientLeftOnInitCount = 0;

	private AnimPanel animPanel;

	private int cashierCount=0;
	private int cookCount=0;
	private int minMealCount = 3;
	private int avaiableSeats = 20;

	private ContDistUniform clientServiceTime;
	private ContDistUniform mealPrepareTime; // in kitchen
	private ContDistUniform mealEatTime; // client:)

	private ContDistUniform clientArrivialTime;
	private ContDistUniform clientDecisionTime;
	private ContDistUniform clientAveragePrice;

	private ContDistUniform clientMaxAcceptableQueue;
	private ContDistUniform probabilityOfQuitOnNewMenu;

	private ContDistUniform groupArrivialProbability;
	private ContDistUniform privilegedClientArrivialProbability;

	protected ProcessQueue<Client> clientQueue;
	protected ProcessQueue<Client> clientNoPleceQueue;
	protected ProcessQueue<Cashier> cashierIdleQueue;
	protected ProcessQueue<Cook> cookIdleQueue;
	protected ProcessQueue<Cook> workingCookQueue;

	private Experiment exp;

	private Kitchen kitchen;
	private ClientGenerator clientGenerator;
	private GroupGenerator groupGenerator;
	private PrivilegedClientGenerator privilegedClientGenerator;
	private Dishes dishes;
	private DishesStorage storage;
	private LinkedList<Cook> cooks;
	private LinkedList<Cashier> cashiers;
	private boolean automaticMode = true;

	protected PropertyChangeSupport change = new PropertyChangeSupport(this);

	public Canteen(Model model, String name, boolean showInRaport,
			boolean showInTrace) {
		super(model, name, showInRaport, showInRaport);
		change.addPropertyChangeListener(animPanel);
		change.addPropertyChangeListener("cookCount", animPanel);
		change.addPropertyChangeListener("tableTwoCount", animPanel);
		cooks = new LinkedList<Cook>();
		cashiers = new LinkedList<Cashier>();
		// TODO Auto-generated constructor stub
	}

	public void setAnimPanel(AnimPanel anim) {
		animPanel = anim;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Symulacja super stołówki ";
	}

	@Override
	public void doInitialSchedules() {
		System.out.println("InitialSchedules");
		this.addCashier();
		this.addCashier();
		this.addCook();
		this.addCook();
		this.addCook();
		this.addCook();
		cookIdleQueue = new ProcessQueue<Cook>(this,
				"Kolejka nudzacych sie kucharzy", false, false);
		clientQueue = new ProcessQueue<Client>(this, "Kolejka klientow", false,
				false);
		clientNoPleceQueue = new ProcessQueue<Client>(this,
				"Kolejka klientow czekajacych na siedzenie", false, false);
		cashierIdleQueue = new ProcessQueue<Cashier>(this,
				"Kolejka wolnych kasjerow", false, false);
		
		clientGenerator = new ClientGenerator(this, "client generator", false);
		groupGenerator = new GroupGenerator(this, "groupGenerator", false);
		privilegedClientGenerator = new PrivilegedClientGenerator(this,
				"privilegedClientGenerator", false);
		
		dishes = new Dishes(getClientAveragePrice());
		kitchen = new Kitchen(this, "Kitchen", false);
		storage = new DishesStorage(dishes.averagePrice, 5, this);

		kitchen.activate(new TimeSpan(0));
		clientGenerator.activate(new TimeSpan(0));
		groupGenerator.activate(new TimeSpan(0));
		privilegedClientGenerator.activate(new TimeSpan(0));
		
		for (Cook cook : cooks) {
			cook.activate(new TimeSpan(0));
		}
		for (Cashier cashier : cashiers) {
			cashier.activate(new TimeSpan(0));
		}
	}

	@Override
	public void init() {
		System.out.println("INIT");
		if (automaticMode) {
			clientServiceTime = new ContDistUniform(this,
					"client service time", 60, 120, false, false);
			mealPrepareTime = new ContDistUniform(this, "meal prepare time",
					60 * 1, 60 * 10, false, false); // in kitchen
			mealEatTime = new ContDistUniform(this, "meal eat time", 5 * 60,
					10 * 60, false, false); // client:)
			clientArrivialTime = new ContDistUniform(this,
					"client arrivial time", 1*60, 5*60, false, false);
			clientDecisionTime = new ContDistUniform(this,
					"client decision time", 30, 1 * 60, false, false);
			groupArrivialProbability = new ContDistUniform(this,
					"group arriviall probability", 10 * 60, 15 * 60, false,
					false);
			privilegedClientArrivialProbability = new ContDistUniform(this,
					"privileged cllient probablity", 30 * 60, 1 * 60 * 60,
					false, false);
			clientAveragePrice = new ContDistUniform(this,
					"privileged cllient probablity", 7, 20, false, false);
			ContDistNormal price = new ContDistNormal(this, "price", 9, 25,
					false, false);

			clientMaxAcceptableQueue = new ContDistUniform(this,
					"Maksymalna akceptowalna kojelka", 5, 15, false, false);
			setProbabilityOfQuitOnNewMenu(0, 1);
		}
	}

	//public void start() {

		// Canteen model = new Canteen(null, "Biathlon simulation", true, true);
		// exp = new Experiment("Biatholon_simulation", "output");
		

	//}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		exp = new Experiment("Symulacja stolowki");
		connectToExperiment(exp);
		 exp.stop(new TimeInstant(3600*8, TimeUnit.SECONDS));
		setDelay(1);

		exp.start();

		exp.finish();
		
	}

	// przerwa pomiedzy zadaniami
	public void setDelay(long milis) {
		exp.setDelayInMillis(milis);
	}

	public void Pause() {
		exp.stop();
	}

	public void unPause() {
		exp.proceed();
	}
	
	public ProcessQueue<Cook> getCookIdleQueue()
	{
	  return cookIdleQueue;
	}

	public int getCashierCount() {
		return cashierCount;
	}

	public void setCashierCount(int cashierCount) {
		this.cashierCount = cashierCount;
	}

	public int getCookCount() {
		return cookCount;
	}

	public void setCookCount(int cookCount) {
		change.firePropertyChange("cookCount", this.cookCount, cookCount);
		this.cookCount = cookCount;
	}

	public int getMinMealCount() {
		return minMealCount;
	}

	public void setMinMealCount(int minMealCount) {
		this.minMealCount = minMealCount;
	}

	public double getClientServiceTime() {
		return clientServiceTime.sample();

	}

	public void setClientServiceTime(double range1, double range2) {
		this.clientServiceTime = new ContDistUniform(this,
				"Client service time", range1, range2, false, false);
	}

	public double getMealPrepareTime() {
		return mealPrepareTime.sample();
	}

	public void setMealPrepareTime(double range1, double range2) {
		this.mealPrepareTime = new ContDistUniform(this, "Meal prepare time",
				range1, range2, false, false);
	}

	public double getMealEatTime() {
		return mealEatTime.sample();
	}

	public void setMealEatTime(double range1, double range2) {
		this.mealEatTime = new ContDistUniform(this, "Eat tieme", range1,
				range2, false, false);
	}

	public double getClientArrivialTime() {
		return clientArrivialTime.sample();
	}

	public void setClientArrivialTime(double range1, double range2) {
		this.clientArrivialTime = new ContDistUniform(this,
				"Clinet arrivial time", range1, range2, false, false);
	}

	public double getGroupArrivialTime() {
		return groupArrivialProbability.sample();
	}

	public void setGroupArrivialTime(double range1, double range2) {
		this.groupArrivialProbability = new ContDistUniform(this,
				"Group arrival time", range1, range2, false, false);
	}

	public double getPrivilegedClientArrivialTime() {
		return privilegedClientArrivialProbability.sample();
	}

	public void setPrivilegedClientArrivialTime(double range1, double range2) {
		this.privilegedClientArrivialProbability = new ContDistUniform(this,
				"Privileged Client", range1, range2, false, false);
	}

	public double getClientDecitionTime() {
		return clientDecisionTime.sample();
	}

	public void setClientDecisionTime(double range1, double range2) {
		this.clientDecisionTime = new ContDistUniform(this,
				"Client decision time", range1, range1, false, false);
	}

	public void setClientServiceTime(ContDistUniform clientServiceTime) {
		this.clientServiceTime = clientServiceTime;
	}

	public int getAvailabaleSeats() {
		return avaiableSeats;
	}

	public int getAvaiableSeats() {
		return avaiableSeats;
	}

	public void setAvaiableSeats(int avaiableSeats) {
		this.avaiableSeats = avaiableSeats;
	}

	public ProcessQueue<Cashier> getCashierIdleQueue() {
		return cashierIdleQueue;
	}

	public void setCashierIdleQueue(ProcessQueue<Cashier> cashierIdelQueue) {
		this.cashierIdleQueue = cashierIdelQueue;
	}

	public ProcessQueue<Client> getClientQueue() {
		return clientQueue;
	}

	public Dishes getDishes() {
		return dishes;
	}

	public DishesStorage getDishesStorage() {
		return storage;
	}


	public Kitchen getKitchen() {
		return kitchen;
	}

	public double getProbabilityOfQuitOnNewMenu() {
		return probabilityOfQuitOnNewMenu.sample();
	}

	public void setProbabilityOfQuitOnNewMenu(double one, double two) {
		this.probabilityOfQuitOnNewMenu = new ContDistUniform(this,
				"Probability of quit on new menu", one, two, false, false);
	}

	public double getClientMaxAcceptableQueue() {
		return clientMaxAcceptableQueue.sample();
	}

	public void setClientMaxAcceptableQueue(
			ContDistUniform clientMaxAcceptableQueue) {
		this.clientMaxAcceptableQueue = clientMaxAcceptableQueue;
	}

	public double getClientAveragePrice() {
		return clientAveragePrice.sample();
	}

	public void setClientAveragePrice(ContDistUniform clientAveragePrice) {
		this.clientAveragePrice = clientAveragePrice;
	}

	public LinkedList<Cook> getCooks() {
		return cooks;
	}
	public void addCashier(){
		Cashier cashier = new Cashier(this,"Cashier",false);
		cashierCount++;
		cashiers.add(cashier);
		
	}
	public void addCook(){
		Cook cook = new Cook(this,"Cashier",false);
		cookCount++;
		cooks.add(cook);
		
	}
	public LinkedList<Cashier> getCashiers() {
		return cashiers;
	}
	

}
