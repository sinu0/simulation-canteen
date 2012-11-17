package canteen;

import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.ProcessQueue;

public class Canteen extends Model {

	static int clientLeftOnInitCount = 0;

	private int cashierCount;
	private int cookCount;
	private int tableTwoCount;
	private int tableFourthCount;
	private int minMealCount;
	private int workingCookCount;
	private int eatingClientCount;

	private ContDistNormal clientServiceTime;
	private ContDistNormal mealPrepareTime; // in kitchen
	private ContDistNormal mealEatTime; // client:)

	private ContDistUniform clientArrivialTime;
	private ContDistUniform clientDecisionTime;

	private ContDistNormal groupArrivialProbability;
	private ContDistNormal privilegedClientArrivialProbability;

	private ProcessQueue<Client> clientQueue;
	private ProcessQueue<Client> clientNoPleceQueue;
	private ProcessQueue<Client> clinetDecisionQueue;
	private ProcessQueue<Cook> cookIdleQueue;
	private ProcessQueue<Cook> workingCookQueue;
	
	private Experiment exp;
	
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
		return "Symulacja super stołówki";
	}

	@Override
	public void doInitialSchedules() {
		
	}

	@Override
	public void init() {

	}
	public void start(){
		Canteen model = new Canteen(null, "Biathlon simulation", true, true);
		exp = new Experiment("Biatholon_simulation", "output");

		model.connectToExperiment(exp);		

		exp.start();
	
		exp.finish();
		
	}
	public void Pause(){
		exp.stop();
	}
	public void unPause(){
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

}
