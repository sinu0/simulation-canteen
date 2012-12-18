package canteen;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

/**
 * @author mar
 *
 *	Klasa tworzaca uprzywilejownych klientow, taki klient wchodzi pierwszy 
 *	do kolejki nie zwarzajac na nic oraz tworzony jest z zadana czestoscia
 * 	z przedzialu losowanego ze zmiennej privilegedClientArrivialTime
 */	
public class PrivilegedClientGenerator extends ClientGenerator{

	public PrivilegedClientGenerator(Model arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	/* 
	 * opisuje cykl zycia tego procesu. Tworzy nowych uprzewilejowanych klientow
	 */
	@Override
	public void lifeCycle() {
		while(true){
			Client client = new Client(model, "Client", false);
			client.setPrivileged(true);
			client.activateAfter(this);
			privilageClientGenerate++;
			model.change.firePropertyChange("privileged client generate", privilageClientGenerate-1, privilageClientGenerate);
			hold(new TimeSpan(model.getPrivilegedClientArrivialTime(),TimeUnit.SECONDS));
			model.getClientCount().update();
		}
		
	}

}
