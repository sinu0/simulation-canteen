package canteen;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class ClientGenerator extends SimProcess{

	static public int clientGenerate=0;
	static public int groupClientGenerate=0;
	static public int privilageClientGenerate=0;
	private Canteen model;
	public ClientGenerator(Model model, String name, boolean trace) {
		super(model, name, trace);
		
		this.model = (Canteen)model;
		
	}

	@Override
	public void lifeCycle() {
		while(true){
			
		}
		
	}
	

}
