package canteen;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class GroupGenerator extends ClientGenerator{
	private Random rand;
	private LinkedList<Client> groupOfClient;
	private int groupMax;
	private int groupMin;
	public GroupGenerator(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		groupOfClient = new LinkedList<Client>();
		rand = new Random();
		groupMax=4;
		groupMin=2;
	}

	@Override
	public void lifeCycle() {
		while(true){
			
			groupClientGenerate++;
			double numberOfClient = (1+rand.nextGaussian())*100;//guassian zwraca rozkład normalny w zakresie 0 - 100 gdzie wartosc oczekiwana to 50
			if(numberOfClient>40 && numberOfClient<60) numberOfClient=groupMin;
			else
			if(numberOfClient<40 && numberOfClient>0) numberOfClient=(int)(groupMax+groupMin)/2;
			else
			if(numberOfClient>100 || numberOfClient<0) numberOfClient=0;
				numberOfClient=groupMax;
			double decisionPoints=0;
			
			for(int i=0;i<numberOfClient;i++){
				Client client = new Client(model, "Client", false);
				groupOfClient=new LinkedList<Client>();
				client.setMemberOfGroup(true, groupClientGenerate);
				groupOfClient.add(client);
				
				decisionPoints+=client.decision()/numberOfClient;
			}
			if(decisionPoints/2>0.5){
				
				for (Client client : groupOfClient) {
					client.setStayInCanteen(true);
					client.activateAfter(this);
					
				}
			}
			else{
				for (Client client : groupOfClient) {
					client.setStayInCanteen(false);
					client.activateAfter(this);}
			}
			
			model.change.firePropertyChange("group generate", groupClientGenerate-1, groupClientGenerate);
			hold(new TimeSpan(model.getGroupArrivialTime(),TimeUnit.SECONDS));
			
		}
		
	}
	public int getGroupMax(){
		return groupMax;
	}
	public int getGroupMin(){
		return groupMax;
	}
	public void setGroupMax(int max){
		this.groupMax=max;
	}
	public void setGroupMin(int min){
		this.groupMax=min;
	}
}
