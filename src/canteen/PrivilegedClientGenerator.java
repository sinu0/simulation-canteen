package canteen;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class PrivilegedClientGenerator extends ClientGenerator{

	public PrivilegedClientGenerator(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void lifeCycle() {
		while(true){
			Client client = new Client(model, "Client", false);
			client.setPrivileged(true);
			client.activateAfter(this);
			privilageClientGenerate++;
			model.change.firePropertyChange("privileged client generate", privilageClientGenerate-1, privilageClientGenerate);
			hold(new TimeSpan(model.getPrivilegedClientArrivialTime(),TimeUnit.SECONDS));
			
		}
		
	}

}
