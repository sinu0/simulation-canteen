package canteen;

import java.util.LinkedList;

public class Dishes {
	
	protected LinkedList<String> soup;
	protected LinkedList<String> dish;
	protected LinkedList<String> drink;
	protected double averagePrice; //Å›rednia cena dan mowiaca o kosztach jedzenia w loklu
	public Dishes(double averagePrice){
		
		drink=new LinkedList<String>();
		dish = new LinkedList<String>();
		soup = new LinkedList<String>();
		
		
		drink.add("kompot");
		dish.add("kotlet");
		dish.add("ziemniaki");
		dish.add("ry¿");
		dish.add("wo³owina");
		soup.add("rosó³");
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
