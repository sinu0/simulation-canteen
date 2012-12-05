package canteen;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class GroupGenerator extends ClientGenerator{
	private Random rand;
	private LinkedList<Client> groupOfClient;
	public GroupGenerator(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		groupOfClient = new LinkedList<Client>();
	}

	@Override
	public void lifeCycle() {
		while(true){
			
			groupClientGenerate++;
			int numberOfClient = (int)(1+rand.nextGaussian())*100;//guassian zwraca rozkład normalny w zakresie 0 - 100 gdzie wartosc oczekiwana to 50
			if(numberOfClient>40 && numberOfClient<60) numberOfClient=2;
			else
			if(numberOfClient<40 && numberOfClient>0) numberOfClient=3;
			else
				numberOfClient=4;
			double decisionPoints=0;
			
			for(int i=0;i<numberOfClient;i++){
				Client client = new Client(model, "Client", false);
				client.setMemberOfGroup(true, groupClientGenerate);
				groupOfClient.add(client);
				decisionPoints+=client.decision()/numberOfClient;
			}
			if(decisionPoints/2>0.5){
				for (Client client : groupOfClient) {
					client.setStayInCanteen(true);
				}
			}
			else{
				for (Client client : groupOfClient) {
					client.setStayInCanteen(false);
				}
			}
			for (Client client : groupOfClient) {
				client.activate();
			}
			model.change.firePropertyChange("group generate", groupClientGenerate-1, groupClientGenerate);
			hold(new TimeSpan(model.getGroupArrivialTime(),TimeUnit.SECONDS));
			
		}
		
	}

}
