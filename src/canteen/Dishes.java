package canteen;

import java.util.LinkedList;

public class Dishes {

	private LinkedList<String> soup;
	private LinkedList<String> dish;
	private LinkedList<String> drink;

	public Dishes(){
		
		drink=new LinkedList<String>();
		dish = new LinkedList<String>();
		drink = new LinkedList<String>();
		//add dishes
	}

	public boolean soupAdd(String soup){
		if(this.soup.contains(soup))
			return false;
		else
			return this.soup.add(soup);
					
	}
	
	public boolean dishAdd(String dish){
		if(this.dish.contains(dish))
			return false;
		else
			return this.dish.add(dish);
					
	}
	public boolean drinkAdd(String drink){
		if(this.drink.contains(drink))
			return false;
		else
			return this.drink.add(drink);
					
	}
}
