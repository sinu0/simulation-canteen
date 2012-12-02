package canteen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class DishesStorage extends Dishes {
	// <<gropa,nazwa>,ilosc>
	private HashMap<String, LinkedList<String>> aviapleDishes;
	private HashMap<String, Integer> storage;
	private int startDishValue;

	public DishesStorage(double averagePrice, int initValue) {
		super(averagePrice);

		this.startDishValue = initValue;

		for (String dishs : soup) {

			storage.put(dishs, initValue);
		}
		for (String dishs : dish) {
			storage.put(dishs, initValue);
		}
		for (String dishs : drink) {
			storage.put(dishs, initValue * 100); // z piciem nie
													// powinnobyc problemu
		}

	}

	public boolean setStorage(String name, int value) {
		if (storage.put(name, value) == null) {
			return false;
		} else
			return true;
	}

	public int getValue(String name) {
		return storage.get(name);
	}

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
}
