package canteen;

import java.util.LinkedList;

public class Dishes {
	
	private LinkedList<String> soup;
	private LinkedList<String> dish;
	private LinkedList<String> drink;
	private double averagePrice; //średnia cena dan mowiaca o kosztach jedzenia w loklu
	public Dishes(double averagePrice){
		
		drink=new LinkedList<String>();
		dish = new LinkedList<String>();
		soup = new LinkedList<String>();
		
		drink.add("kompot");
		dish.add("kotlet");
		dish.add("ziemniaki");
		dish.add("ryz");
		dish.add("wolownia");
		soup.add("rosoł");
		soup.add("pomidorowa");
			
		this.averagePrice=averagePrice;
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
	public boolean drinkAdd(String soup){
		if(this.soup.contains(soup))
			return false;
		else
			return this.soup.add(soup);
					
	}

	public LinkedList<String> getSoup() {
		return soup;
	}

	public LinkedList<String> getDish() {
		return dish;
	}

	public LinkedList<String> getDrink() {
		return drink;
	}
	
}