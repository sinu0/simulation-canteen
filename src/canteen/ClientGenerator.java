package canteen;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

/**
 * @author mar
 * Klasa odpowiedzialna za generowanie klienta z odpowiednim odstepem czasowym losowanym z clientArrivialTime
 */
public class ClientGenerator extends SimProcess{

	static public int allClientGenerate=0;
	static public int clientGenerate=0;
	static public int groupClientGenerate=0;
	static public int privilageClientGenerate=0;
	protected Canteen model;
	
	/**
	 * Konstruktor
	 * @param model przyjmuje null poniewaz ta klasa reprezentuje symulacje
	 * @param name nazwa symulacji
	 * @param trace czy pokazywac w pliku raportu
	 */
	public ClientGenerator(Model model, String name, boolean trace) {
		super(model, name, trace);
		
		this.model = (Canteen)model;
		
	}

	/* (non-Javadoc)
	 * @see desmoj.core.simulator.SimProcess#lifeCycle()
	 */
	@Override
	/**
	 * Cykl zycia generatora klientow
	 */
	public void lifeCycle() {
		while(true){
			Client client = new Client(model, "Client", false);
			client.activateAfter(this);
			allClientGenerate++;
			clientGenerate++;
			model.change.firePropertyChange("client generate", clientGenerate-1, clientGenerate);
			hold(new TimeSpan(model.getClientArrivialTime(),TimeUnit.SECONDS));
			model.getClientCount().update();
		}
		
	}
	

}
