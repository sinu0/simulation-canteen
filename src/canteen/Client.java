package canteen;

import java.util.LinkedList;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class Client extends SimProcess {

	boolean isMemberOfGroup;
	boolean isPrivileged;
	int groupNumber;
	LinkedList<String> selectedMenu;
	
	public Client(Model model, String name, boolean trace) {
		super(model, name, trace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void lifeCycle() {
		// TODO Auto-generated method stub

	}
	public void selectMenu(){
		
	}

}
