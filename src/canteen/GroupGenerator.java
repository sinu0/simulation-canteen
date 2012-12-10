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
		rand = new Random();
	}

	@Override
	public void lifeCycle() {
		while(true){
			
			groupClientGenerate++;
			double numberOfClient = (1+rand.nextGaussian())*100;//guassian zwraca rozkład normalny w zakresie 0 - 100 gdzie wartosc oczekiwana to 50
			System.out.println("clients "+numberOfClient);
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
			System.out.println(decisionPoints/2);
			if(decisionPoints/2>0.5){
				
				for (Client client : groupOfClient) {
					client.setStayInCanteen(true);
					client.activateAfter(this);
				}
			}
			else{
				for (Client client : groupOfClient) {
					client.setStayInCanteen(false);
				}
			}
			model.change.firePropertyChange("group generate", groupClientGenerate-1, groupClientGenerate);
			hold(new TimeSpan(model.getGroupArrivialTime(),TimeUnit.SECONDS));
			
		}
		
	}

}
