package canteen;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class ClientGenerator extends SimProcess{

	static public int clientGenerate=0;
	static public int groupClientGenerate=0;
	static public int privilageClientGenerate=0;
	protected Canteen model;
	public ClientGenerator(Model model, String name, boolean trace) {
		super(model, name, trace);
		
		this.model = (Canteen)model;
		
	}

	@Override
	public void lifeCycle() {
		while(true){
			Client client = new Client(model, "Client", false);
			client.activateAfter(this);
			clientGenerate++;
			model.change.firePropertyChange("client generate", clientGenerate-1, clientGenerate);
			hold(new TimeSpan(model.getClientArrivialTime(),TimeUnit.SECONDS));
			
		}
		
	}
	

}
