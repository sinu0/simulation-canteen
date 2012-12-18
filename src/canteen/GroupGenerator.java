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
	public GroupGenerator(Model arg0, String arg1, boolean arg2, int max) {
		super(arg0, arg1, arg2);
		groupOfClient = new LinkedList<Client>();
		rand = new Random();
		groupMax = max;
		groupMin=2;
	}

	@Override
	public void lifeCycle() {
		while(true){
			
			groupClientGenerate++;
			double numberOfClient = 0;
		    double random = (rand.nextGaussian())*100;//guassian zwraca rozkład normalny w zakresie 0 - 100 gdzie wartosc oczekiwana to 50
		    /*
			if(random>40 && random<60) numberOfClient=groupMin;
			else
			if(random<40 && random>=0) numberOfClient=(int)(groupMax+groupMin)/2;
			else
			if(random>100 || random<0) numberOfClient=0;
				numberOfClient=groupMax;
				*/
			if(random>=40 && random<=60) numberOfClient=(int)(groupMax+groupMin)/2;
			if(random<40) numberOfClient=groupMin;
			if (random>60) numberOfClient=groupMax;
			//if (random<0 && random>100) numberOfClient=0;
			
			double decisionPoints=0;
			model.getGroups().update(numberOfClient);
			
			for(int i=0;i<numberOfClient;i++){
				Client client = new Client(model, "Client", false);
				groupOfClient=new LinkedList<Client>();
				client.setMemberOfGroup(true, groupClientGenerate);
				groupOfClient.add(client);
				
				decisionPoints+=client.decision()/numberOfClient;
				model.getClientCount().update();
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
