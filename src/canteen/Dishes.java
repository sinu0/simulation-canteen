package canteen;

import java.util.LinkedList;

/**
 * @author mar
 * 
 */
public class Dishes {
	
	protected LinkedList<String> soup;
	protected LinkedList<String> dish;
	protected LinkedList<String> drink;
	protected double averagePrice; //srednia cena dan mowiaca o kosztach jedzenia w lokalu
	
	/**
	 * Konstruktor
	 * @param averagePrice srednia cena dan
	 */
	public Dishes(double averagePrice){
		
		drink=new LinkedList<String>();
		dish = new LinkedList<String>();
		soup = new LinkedList<String>();
		
		
		drink.add("kompot");
		dish.add("kotlet");
		dish.add("ziemniaki");
		dish.add("ry�");
		dish.add("wo�owina");
		soup.add("ros�");
		soup.add("pomidorowa");	
			
		this.averagePrice=averagePrice;
		//add dishes
	}

	/**
	 * Dodaje zupe do listy zup
	 * @param soup nazwa zupy
	 * @return czy sie powiodlo czy nie
	 */
	public boolean soupAdd(String soup){
		if(this.soup.contains(soup))
			return false;
		else
			return this.soup.add(soup);
					
	}
	
	/**
	 * Dodaje drugie danie do listy drugich dan
	 * @param dish nazwa drugiego dania
	 * @return czy sie powiodlo czy nie
	 */
	public boolean dishAdd(String dish){
		if(this.dish.contains(dish))
			return false;
		else
			return this.dish.add(dish);
					
	}
	
	/**
	 * Dodaje napoj do listy napojow
	 * @param soup nazwa napoju
	 * @return czy sie powiodlo czy nie
	 */
	public boolean drinkAdd(String soup){
		if(this.soup.contains(soup))
			return false;
		else
			return this.soup.add(soup);
					
	}

	/**
	 * Zwraca liste zup
	 * @return lista zup
	 */
	public LinkedList<String> getSoup() {
		return soup;
	}

	/**
	 * Zwraca liste drugich dan
	 * @return lista drugich dan
	 */
	public LinkedList<String> getDish() {
		return dish;
	}

	/**
	 * Zwraca liste napojow
	 * @return lista napojow
	 */
	public LinkedList<String> getDrink() {
		return drink;
	}
	
}
