package canteen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * @author mar
 * Przechowywalnia jedzenia
 */
public class DishesStorage extends Dishes {
	private HashMap<String, LinkedList<String>> aviableDishes;
	private HashMap<String, Integer> storage;
	private int startDishValue;
	private Canteen model;
	private Random rand;
	
	/**
	 * Tworzy przechowywalnie jedzenia argumenty to kolejno srednia cena wartosc poczatkwa oraz model do ktorego ta klasa nalezy
	 * @param averagePrice średnia cena dan symulujaca cene stołowce
	 * @param initValue liczba skladnikw ktora jest tworzona na poczatku symulacji
	 * @param model klasa symulacji
	 */
	public DishesStorage(double averagePrice, int initValue,Canteen model) {
		super(averagePrice);
		this.model = model;
		storage = new HashMap<String, Integer>();
		this.startDishValue = initValue;

		for (String dishs : soup) {

			storage.put(dishs, initValue);
		}
		for (String dishs : dish) {
			storage.put(dishs, initValue);
		}
		for (String dishs : drink) {
			storage.put(dishs, initValue * 25); // z piciem nie
													// powinnobyc problemu
		}

	}
	
	/**
	 * Dodaje skladniki do "przechowywalni"
	 * @param name nazwa skladnika	
	 * @param howMany ilosc skladnika dodawanych do "przechowywalni"
	 * @return true w przypdku kiedy uda sie dodac do "przechowywalni" 
	 * nowy skladnik przygotowany przez kucharza, jezeli dodanie sie nie powiedze 
	 * metoda zwrocifalse 
	 */
	public boolean addStorage(String name,int howMany) {
		int oldvalue=storage.get(name);
		if (storage.put(name, oldvalue+howMany) == oldvalue) {
			model.change.firePropertyChange("storage",(int)storage.get(name)-howMany,storage.get(name)-1);
			return true;
		} else
		{
			return false;
		}
	}
	
	
	/**
	 * Odejmuje skladniki
	 * @param name nazwa skladnika ktora ma byc pobrana
	 * @return true or false jezeli skladnik znajdowal sie w przechowywalni to zwraca true w przeciwnym przypdku false
	 */
	
	
	public boolean subStorage(String name) {
		
		int oldvalue=storage.get(name);
		if (storage.put(name, oldvalue-1) != null) {
			model.change.firePropertyChange("storage",(int)storage.get(name),storage.get(name)-1);
			return true;
		} else
		{
			return false;
		}
	}
	
	/**
	 * Zwraca ilosc skladnikow dla danego dania
	 * @param name nazwa dania
	 * @return ilosc skladnikow
	 */
	public int getValue(String name) {
		return storage.get(name);
	}

	
	
	/**
	 * Zwraca aktualnie dostepne skladniki
	 * @return  aktualnie dostepne skladniki dostepne w solowce
	 */
	
	
	public HashMap<String, LinkedList<String>> getAvailableList() {
		HashMap<String, LinkedList<String>> returnList = new HashMap<String, LinkedList<String>>();
		LinkedList<String> tmpSoup=new LinkedList<String>();
		for (String dishs : soup) {
			
			if (storage.get(dishs) > 0)
				tmpSoup.add(dishs);
		}
		returnList.put("soup", tmpSoup);
		LinkedList<String> tmpDish=new LinkedList<String>();
		for (String dishs : dish) {
			if (storage.get(dishs) > 0)
				 tmpDish.add(dishs);
		}
		returnList.put("dish",tmpDish);
		LinkedList<String> tmpDrink=new LinkedList<String>();
		for (String dishs : drink) {
			if (storage.get(dishs) > 0)
				 tmpDrink.add(dishs);
		}
		returnList.put("drink",tmpDrink);
		return returnList;
	}
	
	/**
	 * Zwraca magazyn jedzenia
	 * @return magazyn jedzenia
	 */
	public HashMap<String, Integer>  getStorage(){
		return storage;
	}
}
