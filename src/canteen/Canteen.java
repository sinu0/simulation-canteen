package canteen;

import gui.AnimPanel;
import gui.MyFrame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.Accumulate;
import desmoj.core.statistic.Count;
import desmoj.core.statistic.Tally;

/**
 * @author mar
 * Jest to główna klasa odpowiadająca za całą symulacje 
 * Uruchamiana jest w oddzielnym watku
 */


public class Canteen extends Model implements Runnable 
{
	protected PropertyChangeSupport change = new PropertyChangeSupport(this);
	
	
	static int clientLeftOnInitCount = 0;

	private AnimPanel animPanel;
	private MyFrame myFrame;

	private int simTime;
	private int cashierCount;
	private int cookCount;
	private int table2Count;
	private int table4Count;
	private int minMealCount;
	private int maxGroupSize;
	private int initDishesValue;
	
	
	
	private ContDistUniform clientServiceTime;
	private ContDistUniform mealPrepareTime; // in kitchen
	private ContDistUniform mealEatTime; // client:)
	private ContDistUniform clientTableDecision; //ta zmienna mowi czy klient woli stolik 2 czy 4;
	private ContDistUniform clientArrivialTime;
	private ContDistUniform clientDecisionTime;
	private ContDistUniform clientAveragePrice;
	private double canteenAveragePrice;

	private ContDistUniform clientMaxAcceptableQueue;
	private ContDistUniform probabilityOfQuitOnNewMenu;

	private ContDistUniform groupArrivialProbability;
	private ContDistUniform privilegedClientArrivialProbability;

	protected ProcessQueue<Client> clientQueue;
	protected ProcessQueue<Client> clientNoPleceQueue;
	protected ProcessQueue<Cashier> cashierIdleQueue;
	protected ProcessQueue<Cook> cookIdleQueue;
	protected ProcessQueue<Cook> workingCookQueue;
	
	private double minClientArrivalTime;
	private double maxClientArrivalTime;
	private double minClientServiceTime;
	private double maxClientServiceTime;
	private double minMealEatTime;
	private double maxMealEatTime;
	private double minMealPrepareTime;
	private double maxMealPrepareTime;
	private double maxAcceptQueueMin;
	private double maxAcceptQueueMax;
	private double maxPriceMin;
	private double maxPriceMax;
	private double groupGeneratorMultiplier;
	private double priviligedClientMultiplier;

	private Experiment exp;

	
	//**** obiekty symulacji ****//
	
	private Kitchen kitchen;
	private ClientGenerator clientGenerator;
	private GroupGenerator groupGenerator;
	private PrivilegedClientGenerator privilegedClientGenerator;
	private Dishes dishes;	// zmienna odpowiedzalna z trzymanie menu
	private DishesStorage storage; // tu znajduja sie skladiki
	
	private LinkedList<Cook> cooks = new LinkedList<Cook>(); //lista kucharzy
	private LinkedList<Cashier> cashiers=new LinkedList<Cashier>(); //lista kasjerek
	private LinkedList<Table> tables = new LinkedList<Table>(); //lista stolikow
	private boolean automaticMode = true;
	
	
	//zbiory na statystyki
	private Count clientCount;
	private Count clientStayed;
    private Count clientLeftBecOfPrice;
    private Count clientLeftBecOfQueue;
    private Count clientLeftBecOfNoPlace;
    private Count clientLeftBecOfNoFood;
    private Tally groupSize;
    //jedzenie... - Accumulate
    private Tally serviceTimeStat;
    private Tally mealPrepareTimeStat;
    private Tally mealEatTimeStat;
    private Accumulate queueToCashier;
    private Accumulate queueToPlace;
    private Accumulate idleCashierStat;
    private Accumulate idleCookStat;
    private HashMap<String, Integer> foodStat;
    private HashMap<String, Integer> foodServed;
	
	
	
	
	

	/**
	 * Konstruktor symulacji 
	 * @param model przujmuje null pniewaz ta klasa reprezentuje symulacje
	 * @param name
	 * @param showInRaport
	 * @param showInTrace
	 */
	public Canteen(Model model, String name, boolean showInRaport,
			boolean showInTrace) {
		super(model, name, showInRaport, showInRaport);
		change.addPropertyChangeListener(animPanel);
		change.addPropertyChangeListener("cookCount", animPanel);
		change.addPropertyChangeListener("tableTwoCount", animPanel);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Ustawia panel z animacja
	 * @param anim 
	 */
	public void setAnimPanel(AnimPanel anim) {
		animPanel = anim;
	}
	
	/**
	 * Usawia glowne okno
	 * @param frame
	 */
	public void setMyFrame(MyFrame frame)
	{
	  myFrame = frame;
	}

	/**
	 * Dodaje 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.change.addPropertyChangeListener(listener);
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Symulacja sto��wki studenckiej ";
	}

	/* (non-Javadoc)
	 * @see desmoj.core.simulator.Model#doInitialSchedules()
	 */
	@Override
	public void doInitialSchedules() {
		
		for (int i=0;i<cashierCount;i++)
		{
		  this.addCashier();
		}
		
		for (int i=0;i<cookCount;i++)
		{
		  this.addCook();
		}
		
		cookIdleQueue = new ProcessQueue<Cook>(this,
				"Kolejka nudzacych sie kucharzy", false, false);
		clientQueue = new ProcessQueue<Client>(this, "Kolejka klientow", false,
				false);
		clientNoPleceQueue = new ProcessQueue<Client>(this,
				"Kolejka klientow czekajacych na siedzenie", false, false);
		cashierIdleQueue = new ProcessQueue<Cashier>(this,
				"Kolejka wolnych kasjerow", false, false);
		
		clientGenerator = new ClientGenerator(this, "client generator", false);
		groupGenerator = new GroupGenerator(this, "groupGenerator", false, maxGroupSize);
		privilegedClientGenerator = new PrivilegedClientGenerator(this,
				"privilegedClientGenerator", false);
		
		
		initDishesValue = 20;
		dishes = new Dishes(getCanteenAveragePrice());
		kitchen = new Kitchen(this, "Kitchen", false);
		storage = new DishesStorage(dishes.averagePrice, initDishesValue, this);

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
		
		foodStat = new HashMap<String, Integer>();
		foodServed = new HashMap<String, Integer>();
		for (String dish: dishes.soup)
		{
		  foodStat.put(dish, initDishesValue);
		  foodServed.put(dish, 0);
		}
		for (String dish: dishes.dish)
		{
			  foodStat.put(dish, initDishesValue);
			  foodServed.put(dish, 0);
		}
		for (String dish: dishes.drink)
		{
			  foodStat.put(dish, initDishesValue*25);
			  foodServed.put(dish, 0);
		}
	}

	/* (non-Javadoc)
	 * @see desmoj.core.simulator.Model#init()
	 */
	@Override
	public void init() {
		if (automaticMode) {
			
			clientCount = new Count(this, "Client count", false, false);
			clientStayed = new Count(this, "Client stayed in the canteen", false, false);
			clientLeftBecOfPrice = new Count(this, "Clients, who left the canteen because of price", false, false);
			clientLeftBecOfQueue = new Count(this, "Clients, who left the canteen because of length of the queue", false, false);
			clientLeftBecOfNoPlace = new Count(this, "Clients, who left the canteen because of lack of place", false, false);
			clientLeftBecOfNoFood = new Count(this, "Clients, who left the canteen because of lack of food", false, false);
			groupSize = new Tally(this, "Groups statistics", false, false);

			
			serviceTimeStat = new Tally(this, "Service time", false, false);
			mealPrepareTimeStat = new Tally(this, "Meal prepare time", false, false);
			mealEatTimeStat = new Tally(this,  "Meal eat time", false, false);
			queueToCashier = new Accumulate(this, "Queue to cashier", false, false);
			queueToPlace = new Accumulate(this, "Queue to place", false, false);
			idleCashierStat = new Accumulate(this, "Idle cashiers", false, false);
			idleCashierStat.update(0);
			idleCookStat = new Accumulate(this, "Idle cooks", false, false);
			
			
			
			
			clientServiceTime = new ContDistUniform(this,
					"client service time", minClientServiceTime, maxClientServiceTime, false, false);
			mealPrepareTime = new ContDistUniform(this, "meal prepare time",
					60 * minMealPrepareTime, 60 * maxMealPrepareTime, false, false); // in kitchen
			mealEatTime = new ContDistUniform(this, "meal eat time", minMealEatTime * 60,
					maxMealEatTime * 60, false, false); // client:)
			clientArrivialTime = new ContDistUniform(this,
					"client arrivial time", minClientArrivalTime, maxClientArrivalTime, false, false);
			clientDecisionTime = new ContDistUniform(this,
					"client decision time", 30, 1 * 60, false, false);
			groupArrivialProbability = new ContDistUniform(this,
					"group arriviall probability", minClientArrivalTime*groupGeneratorMultiplier, maxClientArrivalTime*groupGeneratorMultiplier, false,
					false);
			privilegedClientArrivialProbability = new ContDistUniform(this,
					"privileged cllient probablity", minClientArrivalTime*priviligedClientMultiplier, maxClientArrivalTime*priviligedClientMultiplier,
					false, false);
			clientAveragePrice = new ContDistUniform(this,
					"privileged cllient probablity", maxPriceMin, maxPriceMax, false, false);
			clientMaxAcceptableQueue = new ContDistUniform(this,
					"Maksymalna akceptowalna kojelka", maxAcceptQueueMin, maxAcceptQueueMax, false, false);
			setProbabilityOfQuitOnNewMenu(0, 1);
			clientTableDecision = new ContDistUniform(this,"Preferowany stolik przez klienta",0,2,false,false);
			setSeed();
		}
	}
	
	/**
	 * metoda ta powoduje ze symulacja za kazdym razem jest inna
	 */
	public void setSeed(){
		clientServiceTime.setSeed((long)(Math.random()*100000));
		mealPrepareTime.setSeed((long)(Math.random()*100000));
		clientTableDecision.setSeed((long)(Math.random()*100000));
		probabilityOfQuitOnNewMenu.setSeed((long)(Math.random()*100000));
		clientMaxAcceptableQueue.setSeed((long)(Math.random()*100000));
		clientAveragePrice.setSeed((long)(Math.random()*100000));
		privilegedClientArrivialProbability.setSeed((long)(Math.random()*100000));
		groupArrivialProbability.setSeed((long)(Math.random()*100000));
		clientDecisionTime.setSeed((long)(Math.random()*100000));
		clientArrivialTime.setSeed((long)(Math.random()*100000));
		mealEatTime.setSeed((long)(Math.random()*100000));
		}

	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		exp = new Experiment("Symulacja sto��wki");
		connectToExperiment(exp);
		exp.stop(new TimeInstant(3600*simTime, TimeUnit.SECONDS));
		setDelay(10);

		exp.start();

		exp.finish();
		
		myFrame.getContentPane().removeAll();
		myFrame.getStatPanel().setStat();
		myFrame.add(myFrame.getStatPanel());
		myFrame.revalidate();
		
	}

	/**
	 * Ustawia przerwe pomiedzy zadaniami
	 * @param milis czas w milisekundach
	 */
	public void setDelay(long milis) {
		exp.setDelayInMillis(milis);
	}

	/**
	 * Zatrzymuje symulacje
	 */
	public void Pause() {
		exp.stop();
	}

	/**
	 * Wznawia symulacje
	 */
	public void unPause() {
		exp.proceed();
	}
	
	/**
	 * 
	 * @return kolejke kucharzy kórzy sa w daniej chwili wolni
	 */
	public ProcessQueue<Cook> getCookIdleQueue()
	{
	  return cookIdleQueue;
	}

	/**
	 * 
	 * @return ilosc kasjerek w symulacji
	 */
	public int getCashierCount() {
		return cashierCount;
	}

	/**
	 * Ustawia ilosc kasjerek w symulacji
	 * @param cashierCount liczba kasjerek w symulacji
	 */
	public void setCashierCount(int cashierCount) {
		change.firePropertyChange("cashierCount", this.cashierCount, cashierCount);
		this.cashierCount = cashierCount;
		
	}

	/**
	 * @return liczbe kucharzy w sumulacji
	 */
	public int getCookCount() {
		return cookCount;
	}

	/**
	 * Ustaiwa ilosc kucharzy w symualcji
	 * @param cookCount ilosc kucharzy w symulacji
	 */
	public void setCookCount(int cookCount) {
		change.firePropertyChange("cookCount", this.cookCount, cookCount);
		this.cookCount = cookCount;
	}

	/**
	 * @return	minimalna ilosc skladnikow, ktora powinna sie znajdowac w magazynie
	 */
	public int getMinMealCount() {
		return minMealCount;
	}

	/**
	 * Ustawia minimalna ilosc skladnikow w magazynie
	 * @param minMealCount minimalna liczba sklasnikow przy ktorej kucharze musza robic danie
	 */
	public void setMinMealCount(int minMealCount) {
		this.minMealCount = minMealCount;
	}
	
	/**
	 * @return czas symulacji
	 */
	public int getSimTime() {
		return simTime;
	}

	/**
	 * Ustawia czas symulacji
	 * @param simTime czas w sekundach
	 */
	public void setSimTime(int simTime) {
		this.simTime = simTime;
	}
	
	/**
	 * @return losowa wartosc z zadanego przedzialu gdzie
	 */
	public double getClientServiceTime() {
		return clientServiceTime.sample();

	}
	
	/**
	 * Metoda ustawia zakres czasu obsługi klienta
	 * @param range1 min
	 * @param range2 max
	 */
	public void setClientServiceTime(double range1, double range2) {
		minClientServiceTime = range1;
		maxClientServiceTime = range2;
	}

	/**
	 *  
	 * @return czas przygotowania jedzenia
	 */
	public double getMealPrepareTime() {
		return mealPrepareTime.sample();
	}
	

	/**
	 Metoda ustawia zakres czasu przygotowania jedzenia
	 * @param range1 min
	 * @param range2 max
	 */
	public void setMealPrepareTime(double range1, double range2) {
		minMealPrepareTime = range1;
		maxMealPrepareTime = range2;
	}

	/**
	 * @return czas jedzenia z poprzednio ustawionego zakresu min max
	 */
	public double getMealEatTime() {
		return mealEatTime.sample();
	}

	/**
 	 *Metoda ustawia zakres czasu jedzenia posilku
	 * @param range1 min
	 * @param range2 max
	 */
	public void setMealEatTime(double range1, double range2) {
		minMealEatTime = range1;
		maxMealEatTime = range2;
	}

	/**
	 * @return czas przyjscia nastepnego klienta
	 */
	public double getClientArrivialTime() {
		return clientArrivialTime.sample();
	}
	/**
	 * Metoda ustawia zakres czasu jedzenia posilku
	 * @param range1 min
	 * @param range2 max
	 */
	public void setClientArrivialTime(double range1, double range2) {
		minClientArrivalTime = range1;
		maxClientArrivalTime = range2;
	}

	/**
	 * @return czas pojawienie sie nastepniej grupy
	 */
	public double getGroupArrivialTime() {
		return groupArrivialProbability.sample();
	}

	
	/**
	 * @return czas pojawienia sie nastepnej grupy
	 */
	public double getPrivilegedClientArrivialTime() {
		return privilegedClientArrivialProbability.sample();
	}


	/**
	 * @return czas decyzji klienta nad nowym menu
	 */
	public double getClientDecitionTime() {
		return clientDecisionTime.sample();
	}


	

	/**
	 * @return kolejke wolnych kasjerek
	 */
	public ProcessQueue<Cashier> getCashierIdleQueue() {
		return cashierIdleQueue;
	}
	
	/**
	 * @return zwaraca kolejke klientow
	 */
	public ProcessQueue<Client> getClientQueue() {
		return clientQueue;
	}
	
	
	/**
	 * @return przechowywalne jedzenia
	 */
	public DishesStorage getDishesStorage() {
		return storage;
	}


	/**
	 * @return zwraca kuchnie
	 */
	public Kitchen getKitchen() {
		return kitchen;
	}

	/**
	 * @return prawdopodobienstwo wyjscia z stołowki kiedy brakuje
	 *  dla niego wybranego za pierwszym razem jedzenia
	 */
	public double getProbabilityOfQuitOnNewMenu() {
		return probabilityOfQuitOnNewMenu.sample();
	}

	/**
	 * Metoda towrzy i ustawia prawdopodobienstwo wyjscia z lokalu 
	 * @param one min
	 * @param two max
	 */
	public void setProbabilityOfQuitOnNewMenu(double one, double two) {
		this.probabilityOfQuitOnNewMenu = new ContDistUniform(this,
				"Probability of quit on new menu", one, two, false, false);
	}

	/**
	 * @return akceptowalna dlugosc kolejki przez klienta
	 */
	public double getClientMaxAcceptableQueue() {
		return clientMaxAcceptableQueue.sample();
	}
	/**
	 * Metoda ustawia zakres akceptowalnej dlugosci kolejki przez klienta
	 * @param range1 min
	 * @param range2 max
	 */
	public void setClientMaxAcceptableQueue(double one, double two) {
		maxAcceptQueueMin = one;
		maxAcceptQueueMax = two;
	}

	/**
	 * @return srednia cene jedzenia
	 */
	public double getClientAveragePrice() {
		return clientAveragePrice.sample();
	}
	/**
	 * Metoda ustawia zakres z jakiego przedzialu cenowego jest srednia cena jedzenia w stolowce
	 * @param range1 min
	 * @param range2 max
	 */
	public void setClientAveragePrice(double one, double two) {
		maxPriceMin = one;
		maxPriceMax = two;
	}

	/**
	 * @return liste kucharzy
	 */
	public LinkedList<Cook> getCooks() {
		return cooks;
	}
	/**
	 *  Metoda tworzy nowa kasjerke
	 */
	public void addCashier(){
		Cashier cashier = new Cashier(this,"Cashier",false);
		cashiers.add(cashier);
		change.firePropertyChange("cashiers", cashiers.size()-1, cashiers.size());
		
	}
	/**
	 *  Metoda tworzy nowego kucharza
	 */
	public void addCook(){
		Cook cook = new Cook(this,"Cashier",false);
		cooks.add(cook);
		
	}
	/**
	 * @return liste kasjerow
	 */
	public LinkedList<Cashier> getCashiers() {
		return cashiers;
	}
	
	/**
	 * @return liste stolikow
	 */
	public LinkedList<Table> getTables(){
		return tables;
	}
	
	/**
	 * @return ilosc miejsca w sotlowce
	 */
	public int getSeatsCount(){
		int count=0;
		for (Table table : tables) {
			count+=table.getSeatCunt();
		}
		return count;
	}
	
	/**
	 * @return ilosc wolnego miejsca w stolowce
	 */
	public int getAvailableSeats(){
		int count=0;
		for (Table table : tables) {
			count+=table.getEmpySeatCount();
		}
		return count;
	}
	
	/**
	 *  Tworzy stolik 2-osobowy
	 */
	public void addTable2(){
		tables.add(new Table2Seats(this, "table2", true));
	}
	/**
	 *  Tworzy stolik 4-osobowy
	 */
	public void addTable4(){
		tables.add(new Table4Seats(this, "table2", true));
	}
	
	
	/**
	 * @return zwraca ilosc stolikow 2-osobowych
	 */
	public int getTable2Count() {
		return table2Count;
	}
	
	/**
	 * @param table2Count - ilosc stolikow 2-osobowych w symulacji
	 */
	public void setTable2Count(int table2Count) {
		this.table2Count = table2Count;
	}
	/**
	 * @return zwraca ilosc stolikow 4-osobowych
	 */
	public int getTable4Count() {
		return table4Count;
	}
	/**
	 * @param table2Count - ilosc stolikow 4-osobowych w symulacji
	 */
	public void setTable4Count(int table4Count) {
		this.table4Count = table4Count;
	}
	
	/**
	 * @return aktualna liczbe klientow w solowce
	 */
	public int ClientsInCanteen(){
		int count=0;
		for (Table table : tables) {
			count+=table.getClientCount();
		}
		return clientQueue.size()+clientNoPleceQueue.size()+count;
	}
	
	/**
	 * @return srednia cene dan
	 */
	public double getCanteenAveragePrice() {
		return canteenAveragePrice;
	}
	
	/**
	 * @param one ?
	 */
	public void setCanteenAveragePrice(double one) {
		
		this.canteenAveragePrice = one;
	}
	
	/**
	 * @param value 
	 */
	public void setGroupGeneratorMultiplier(double value)
	{
	  groupGeneratorMultiplier = value;
	}
	
	/**
	 * @param value
	 */
	public void setPriviligedClientMultiplier(double value)
	{
	  priviligedClientMultiplier = value;
	}
	
	/**
	 * @param size maksymalna wielkosc grupy
	 */
	public void setMaxGroupSize(int size)
	{
	  maxGroupSize = size;
	}

	/**
	 * @return ilosc klientow w calej symulacji
	 */
	public Count getClientCount() {
		return clientCount;
	}

	/**
	 * @return ilosc klientow ktora zostala w stolowce
	 */
	public Count getClientStayed() {
		return clientStayed;
	}
	
	/**
	 * @return ilos klientow pozostala w sotlowce
	 */
	public int getClientStayedValue()
	{
		return (int)(clientStayed.getValue() - clientLeftBecOfNoFood.getValue());
	}
	

	/**
	 * @return ilosc klientow ktora opuscila lokal z powodu zbyt wysokich cen
	 */
	public Count getClientLeftBecOfPrice() {
		return clientLeftBecOfPrice;
	}

	/**
	 * @return ilosc klientow ktora opuscila lokal z powodu zbyt dlugiej kolejki
	 */
	public Count getClientLeftBecOfQueue() {
		return clientLeftBecOfQueue;
	}

	/**
	 * @return srednia wielkosc grupy
	 */
	public Tally getGroups() {
		return groupSize;
	}
	/**
	 * @return srecni czas obslugi przez kasjerke
	 */
	public Tally getServiceTimeStat() {
		return serviceTimeStat;
	}
	/**
	 * @return sredni czas przygotowania jedzenia przez kucharza
	 */
	public Tally getMealPrepareTimeStat() {
		return mealPrepareTimeStat;
	}
	/**
	 * @return sredni czas jedzenie przez klienta
	 */
	public Tally getMealEatTimeStat() {
		return mealEatTimeStat;
	}
	
	/**
	 * @return odchylenie standartowe 
	 */
	public Accumulate getQueueToCashier() {
		return queueToCashier;
	}

	public Accumulate getQueueForPlace() {
		return queueToPlace;
	}

	public Accumulate getIdleCashierStat() {
		return idleCashierStat;
	}

	public Accumulate getIdleCookStat() {
		return idleCookStat;
	}
	
	public Count getClientLeftBecOfNoPlace()
	{
	  return clientLeftBecOfNoPlace;
	}
	
	public Count getClientLeftBecOfNoFood()
	{
	  return clientLeftBecOfNoFood;
	}
	
	public HashMap<String, Integer> getFoodStat()
	{
	  return foodStat;
	}
	
	public HashMap<String, Integer> getFoodServed()
	{
	  return foodServed;
	}

	public ProcessQueue<Client> getClientNoPleceQueue() {
		return clientNoPleceQueue;
	}
	
}