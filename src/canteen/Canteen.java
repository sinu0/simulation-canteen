package canteen;

import java.util.LinkedList;
import java.util.Random;

import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;
import desmoj.core.simulator.TimeSpan;

public class Canteen extends Model {

	static int clientLeftOnInitCount = 0;

	private int cashierCount;
	private int cookCount;
	private int tableTwoCount;
	private int tableFourthCount;
	private int minMealCount;
	private int workingCookCount;
	private int eatingClientCount;
	private int avaiableSeats;
	private double averagePrice;

	private ContDistNormal clientServiceTime;
	private ContDistNormal mealPrepareTime; // in kitchen
	private ContDistNormal mealEatTime; // client:)

	private ContDistUniform clientArrivialTime;
	private ContDistUniform clientDecisionTime;

	private ContDistNormal groupArrivialProbability;
	private ContDistNormal privilegedClientArrivialProbability;

	protected ProcessQueue<Client> clientQueue;
	protected ProcessQueue<Client> clientNoPleceQueue;
	protected ProcessQueue<Cashier> cashierIdelQueue;
	protected ProcessQueue<Cook> cookIdleQueue;
	protected ProcessQueue<Cook> workingCookQueue;

	private Experiment exp;

	private Kitchen kitchen;
	private ClientGenerator clientGenerator;
	private Dishes dishes;
	private Cashier cashier;
	private DishesStorage storage;

	private boolean automaticMode = true;

	public Canteen(Model model, String name, boolean showInRaport,
			boolean showInTrace) {
		super(model, name, showInRaport, showInRaport);

		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */

	public static void main(String[] args) {

	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Symulacja super stołówki ";
	}

	@Override
	public void doInitialSchedules() {
		kitchen = new Kitchen(this, "Kitchen", false);
		clientGenerator = new ClientGenerator(this, "client generator", false);
		dishes = new Dishes(averagePrice);
		cashier = new Cashier(this, "cashier", false);

		kitchen.activate(new TimeSpan(0));
		cashier.activate(new TimeSpan(0));
		clientGenerator.activate(new TimeSpan(0));
		storage = new DishesStorage(averagePrice,5);

	}

	@Override
	public void init() {
		if (automaticMode) {
			clientServiceTime = new ContDistNormal(this, "clibet service time",
					10, 60, false, false);
			mealPrepareTime = new ContDistNormal(this, "meal prepare time",
					5 * 60, 10 * 60, false, false); // in kitchen
			mealEatTime = new ContDistNormal(this, "meal eat time", 1 * 60,
					5 * 60, false, false); // client:)
			clientArrivialTime = new ContDistUniform(this,
					"client arrivial time", 1 * 60, 10 * 60, false, false);
			clientDecisionTime = new ContDistUniform(this,
					"client decision time", 10, 45, false, false);
			groupArrivialProbability = new ContDistNormal(this,
					"group arriviall probability", 10 * 60, 30 * 60, false,
					false);
			privilegedClientArrivialProbability = new ContDistNormal(this,
					"privileged cllient probablity", 60 * 60, 4 * 60 * 60,
					false, false);
			ContDistNormal price = new ContDistNormal(this, "price", 9, 25,
					false, false);
			averagePrice = price.sample();
		}
	}

	public void start() {

		Canteen model = new Canteen(null, "Biathlon simulation", true, true);
		exp = new Experiment("Biatholon_simulation", "output");

		model.connectToExperiment(exp);

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
		this.cookCount = cookCount;
	}

	public int getTableTwoCount() {
		return tableTwoCount;
	}

	public void setTableTwoCount(int tableTwoCount) {
		this.tableTwoCount = tableTwoCount;
	}

	public int getTableFourthCount() {
		return tableFourthCount;
	}

	public void setTableFourthCount(int tableFourthCount) {
		this.tableFourthCount = tableFourthCount;
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
		this.clientServiceTime = new ContDistNormal(this,
				"Client service time", range1, range2, false, false);
	}

	public double getMealPrepareTime() {
		return mealPrepareTime.sample();
	}

	public void setMealPrepareTime(double range1, double range2) {
		this.mealPrepareTime = new ContDistNormal(this, "Meal prepare time",
				range1, range2, false, false);
	}

	public double getMealEatTime() {
		return mealEatTime.sample();
	}

	public void setMealEatTime(double range1, double range2) {
		this.mealEatTime = new ContDistNormal(this, "Eat tieme", range1,
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
		this.groupArrivialProbability = new ContDistNormal(this,
				"Group arrival time", range1, range2, false, false);
	}

	public double getPrivilegedClientArrivialTime() {
		return privilegedClientArrivialProbability.sample();
	}

	public void setPrivilegedClientArrivialTime(double range1, double range2) {
		this.privilegedClientArrivialProbability = new ContDistNormal(this,
				"Privileged Client", range1, range2, false, false);
	}

	public double getClientDecitionTime() {
		return clientDecisionTime.sample();
	}

	public void setClientDecisionTime(double range1, double range2) {
		this.clientDecisionTime = new ContDistUniform(this,
				"Client decision time", range1, range1, false, false);
	}

	public void setClientServiceTime(ContDistNormal clientServiceTime) {
		this.clientServiceTime = clientServiceTime;
	}

	public int getAvailabaleSeats() {
		return avaiableSeats;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public int getAvaiableSeats() {
		return avaiableSeats;
	}

	public void setAvaiableSeats(int avaiableSeats) {
		this.avaiableSeats = avaiableSeats;
	}
	public Dishes getDishes(){
		return dishes;
	}
	public DishesStorage getDishesStorage(){
		return storage;
	}

	
}
