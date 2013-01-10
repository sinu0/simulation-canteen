package canteen;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class GroupGenerator extends ClientGenerator {
	private Random rand;
	private LinkedList<Client> groupOfClient;
	private int groupMax;
	private int groupMin;

	public GroupGenerator(Model arg0, String arg1, boolean arg2, int max) {
		super(arg0, arg1, arg2);
		groupOfClient = new LinkedList<Client>();
		rand = new Random();
		groupMax = max;
		groupMin = 2;
	}

	@Override
	public void lifeCycle() {
		while (true) {
			groupOfClient = new LinkedList<Client>();
			groupClientGenerate++;
			boolean active = false;
			int price = 0;
			int queue = 0;
			double numberOfClient = 0;
			double random = (rand.nextGaussian()) * 100;// guassian zwraca
														// rozkład normalny w
														// zakresie 0 - 100
														// gdzie wartosc
														// oczekiwana to 50
			if (random >= 40 && random <= 60)
				numberOfClient = (int) (groupMax + groupMin) / 2;
			if (random < 40)
				numberOfClient = groupMin;
			if (random > 60)
				numberOfClient = groupMax;
			allClientGenerate += (long)numberOfClient;
			double decisionPoints = 0;
			model.getGroups().update(numberOfClient);
			for (int i = 0; i < numberOfClient; i++) {
				Client client = new Client(model, "Client", false);
				client.setMemberOfGroup(true, groupClientGenerate);
				groupOfClient.add(client);
				price += client.decisionPrice();
				queue += client.decisionMaxQueue();

				model.getClientCount().update();
			}
			price = (int) numberOfClient - price;
			queue = (int) numberOfClient - queue;
			if (numberOfClient > model.getAvailableSeats()
					- model.clientQueue.size()) {
				active = false;
				model.getClientLeftBecOfNoPlace().update((long)numberOfClient);
				// brak miejsca
			} else {
				
				active = false;
				if ((price + queue)/numberOfClient == numberOfClient){
					model.getClientLeftBecOfPrice().update((long)numberOfClient);
					System.out.println("cena i kolejka za wysoka!");}
				else if (price == numberOfClient) {
					
					System.out.println("cena za wysoka!");
					model.getClientLeftBecOfPrice().update((long)numberOfClient);
				} else if (queue == numberOfClient){
					model.getClientLeftBecOfQueue().update((long)numberOfClient);
					System.out.println("Kolejka za dluga!");}
				else
					active = true;

			}
			if (active) {
				for (Client client : groupOfClient) {
					int i=0;
					client.setStayInCanteen(true);
					client.activateAfter(this);

				}
				
			} else {
				for (Client client : groupOfClient) {
					client.setStayInCanteen(false);
					client.activateAfter(this);
				}
			}

			model.change.firePropertyChange("group generate",
					groupClientGenerate - 1, groupClientGenerate);
			hold(new TimeSpan(model.getGroupArrivialTime(), TimeUnit.SECONDS));

		}

	}

	public int getGroupMax() {
		return groupMax;
	}

	public int getGroupMin() {
		return groupMax;
	}

	public void setGroupMax(int max) {
		this.groupMax = max;
	}

	public void setGroupMin(int min) {
		this.groupMax = min;
	}
}
