package gui;

import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import canteen.Canteen;
import canteen.ClientGenerator;

public class StatPanel extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  private MyFrame frame;
  private Canteen model;
  
  StatPanel(MyFrame _frame)
  {
    frame = _frame;
    model = frame.getCanteen();
    setLayout(null);
    JLabel stat = new JLabel("Statystyki symulacji");
    add(stat);
    stat.setBounds(320, 10, 150, 20);
  }
  
  public void setStat()
  {
	//klienci
    JLabel client = new JLabel("Klienci");
    client.setBounds(30, 40, 100, 20);
    add(client);
    
    JLabel allClients = new JLabel("Wszyscy klienci: " + ClientGenerator.allClientGenerate);
    allClients.setBounds(40, 60, 150, 20);
    add(allClients);
    
    JLabel indClients = new JLabel("Pojedynczy klienci: " + ClientGenerator.clientGenerate);
    indClients.setBounds(40, 80, 150, 20);
    add(indClients);
    
    JLabel prvClient = new JLabel("Ilosc klientow uprzywilejowanych: " + ClientGenerator.privilageClientGenerate);
    prvClient.setBounds(40, 100, 220, 20);
    add(prvClient);
    
    JLabel groups = new JLabel("Ilosc grup: " + ClientGenerator.groupClientGenerate);
    groups.setBounds(40, 120, 150, 20);
    add(groups);
    
    JLabel avgGroupSize = new JLabel("Sredni rozmiar grupy: " + model.getGroups().getMean());
    avgGroupSize.setBounds(40, 140, 180, 20);
    add(avgGroupSize);
    
    JLabel clientStayed = new JLabel("Zostalo w stolowce: " + model.getClientStayedValue());
    clientStayed.setBounds(40, 170, 150, 20);
    add(clientStayed);
    
    JLabel clientLeftBecOfPrice = new JLabel("Odeszlo przez zbyt wysokie ceny: " + model.getClientLeftBecOfPrice().getValue());
    clientLeftBecOfPrice.setBounds(40, 190, 200, 20);
    add(clientLeftBecOfPrice);
    
    JLabel clientLeftBecOfQueue = new JLabel("Odeszlo przez zbyt dluga kolejke do kasy: " + model.getClientLeftBecOfQueue().getValue());
    clientLeftBecOfQueue.setBounds(40, 210, 270, 20);
    add(clientLeftBecOfQueue);
    
    JLabel clientLeftBecOfNoFood = new JLabel("Odeszlo przez brak dan: " + model.getClientLeftBecOfNoFood().getValue());
    clientLeftBecOfNoFood.setBounds(40, 230, 200, 20);
    add(clientLeftBecOfNoFood);
    
    JLabel clientLeftBecOfNoPlace = new JLabel("Odeszlo przez brak miejsca: " + model.getClientLeftBecOfNoPlace().getValue());
    clientLeftBecOfNoPlace.setBounds(40, 250, 200, 20);
    add(clientLeftBecOfNoPlace);
        
    
    
    
    //czas obslugi klienta
    JLabel serviceTime = new JLabel("Czas obslugi klienta");
    serviceTime.setBounds(30, 290, 120, 20);
    add(serviceTime);
    
    JLabel minServiceTime = new JLabel("Minimalny: " + setMinSec(model.getServiceTimeStat().getMinimum()));
    minServiceTime.setBounds(40, 310, 120, 20);
    add(minServiceTime);
    
    JLabel maxServiceTime = new JLabel("Maksymalny: " + setMinSec(model.getServiceTimeStat().getMaximum()));
    maxServiceTime.setBounds(40, 330, 150, 20);
    add(maxServiceTime);
    
    JLabel meanServiceTime = new JLabel("Sredni: " + setMinSec(model.getServiceTimeStat().getMean()));
    meanServiceTime.setBounds(40, 350, 120, 20);
    add(meanServiceTime);
    
    JLabel stdServiceTime = new JLabel("Odchylenie standardowe: " + setMinSec(model.getServiceTimeStat().getStdDev()));
    stdServiceTime.setBounds(40, 370, 200, 20);
    add(stdServiceTime);
    
    
    
    
    //czas przygotowywania posilku
    JLabel mealPrepare = new JLabel("Czas przygotowania posilku");
    mealPrepare.setBounds(30, 410, 180, 20);
    add(mealPrepare);
    
    JLabel minMealPrepate = new JLabel("Minimalny: " + setMinSec(model.getMealPrepareTimeStat().getMinimum()));
    minMealPrepate.setBounds(40, 430, 180, 20);
    add(minMealPrepate);
    
    JLabel maxMealPrepare = new JLabel("Maksymalny: " + setMinSec(model.getMealPrepareTimeStat().getMaximum()));
    maxMealPrepare.setBounds(40, 450, 180, 20);
    add(maxMealPrepare);
    
    JLabel meanMealPrepare = new JLabel("Sredni: " + setMinSec(model.getMealPrepareTimeStat().getMean()));
    meanMealPrepare.setBounds(40, 470, 180, 20);
    add(meanMealPrepare);
    
    JLabel stdMealPrepare = new JLabel("Odchylenie standardowe: " + setMinSec(model.getMealPrepareTimeStat().getStdDev()));
    stdMealPrepare.setBounds(40, 490, 180, 20);
    add(stdMealPrepare);
    
    
    
    
    //czas jedzenia posilku
    JLabel eatTime = new JLabel("Czas jedzenia posilku");
    eatTime.setBounds(30, 530, 180, 20);
    add(eatTime);
    
    JLabel minEatTime = new JLabel("Minimalny: " + setMinSec(model.getMealEatTimeStat().getMinimum()));
    minEatTime.setBounds(40, 550, 180, 20);
    add(minEatTime);
    
    JLabel maxEatTime = new JLabel("Maksymalny: " + setMinSec(model.getMealEatTimeStat().getMaximum()));
    maxEatTime.setBounds(40, 570, 180, 20);
    add(maxEatTime);
    
    JLabel meanEatTime = new JLabel("Sredni: " + setMinSec(model.getMealEatTimeStat().getMean()));
    meanEatTime.setBounds(40, 590, 180, 20);
    add(meanEatTime);
    
    JLabel stdEatTime = new JLabel("Odchylenie standardowe: " + setMinSec(model.getMealEatTimeStat().getStdDev()));
    stdEatTime.setBounds(40, 610, 180, 20);
    add(stdEatTime);
    
    JLabel queueToCashier = new JLabel("Kolejka klientow do kasy");
    queueToCashier.setBounds(400, 40, 150, 20);
    add(queueToCashier);
    
    JLabel maxQueueToCashier = new JLabel("Maksymalna " + (int)model.getQueueToCashier().getMaximum());
    maxQueueToCashier.setBounds(410, 60, 150, 20);
    add(maxQueueToCashier);
    
    JLabel meanQueueToCashier = new JLabel("Srednia " + model.getQueueToCashier().getMean());
    meanQueueToCashier.setBounds(410, 80, 250, 20);
    add(meanQueueToCashier);
    
    JLabel stdQueueToCashier = new JLabel("Odchylenie standardowe " + model.getQueueToCashier().getStdDev());
    stdQueueToCashier.setBounds(410, 100, 250, 20);
    add(stdQueueToCashier);
    
    JLabel queueForPlace = new JLabel("Kolejka klientow po miejsce przy stoliku");
    queueForPlace.setBounds(400, 130, 250, 20);
    add(queueForPlace);
    
    JLabel maxQueueForPlace = new JLabel("Maksymalna: " + (int)model.getQueueForPlace().getMaximum());
    maxQueueForPlace.setBounds(410, 150, 250, 20);
    add(maxQueueForPlace);
    
    JLabel meanQueueForPlace = new JLabel("Srednia: " + model.getQueueForPlace().getMean());
    meanQueueForPlace.setBounds(410, 170, 250, 20);
    add(meanQueueForPlace);
    
    JLabel stdQueueForPlace = new JLabel("Odchylenie standardowe: " + model.getQueueForPlace().getStdDev());
    stdQueueForPlace.setBounds(410, 190, 250, 20);
    add(stdQueueForPlace);
    
    JLabel idleCashier = new JLabel("Wolne kasjerki");
    idleCashier.setBounds(400, 220, 150, 20);
    add(idleCashier);
    
    JLabel meanIdleCashier = new JLabel("Srednio: " + model.getIdleCashierStat().getMean());
    meanIdleCashier.setBounds(410, 240, 250, 20);
    add(meanIdleCashier);
    
    JLabel stdIdleCashier = new JLabel("Odchylenie standardowe: " + model.getIdleCashierStat().getStdDev());
    stdIdleCashier.setBounds(410, 260, 250, 20);
    add(stdIdleCashier);
    
    JLabel idleCook = new JLabel("Wolni kucharze");
    idleCook.setBounds(400, 290, 150, 20);
    add(idleCook);
    
    JLabel meanIdleCook = new JLabel("Srednio: " + model.getIdleCookStat().getMean());
    meanIdleCook.setBounds(410, 310, 250, 20);
    add(meanIdleCook);
    
    JLabel stdIdleCook = new JLabel("Odchylenie standardowe: " + model.getIdleCookStat().getStdDev());
    stdIdleCook.setBounds(410, 330, 250, 20);
    add(stdIdleCook);
    
    JLabel foodPrepare = new JLabel("Dania             przygotowane      wydane");
    foodPrepare.setBounds(400, 360, 320, 20);
    add(foodPrepare);
    
    LinkedList<String> foodName = new LinkedList<String>();
    for (String dish : model.getDishes().getDish())
      foodName.add(dish);
    for (String dish : model.getDishes().getSoup())
        foodName.add(dish);
    for (String dish : model.getDishes().getDrink())
        foodName.add(dish);
    
    for (int i=0;i<foodName.size();i++)
    {
      JLabel food = new JLabel(foodName.get(i).substring(0, 1).toUpperCase() + foodName.get(i).substring(1, foodName.get(i).length()));
      food.setBounds(410, 380+i*20, 80, 20);
      add(food);
    }
    
    
    for (int i=0;i<foodName.size();i++)
    {
      JLabel food = new JLabel(Integer.toString(model.getFoodStat().get(foodName.get(i))));
      food.setBounds(500, 380+i*20, 140, 20);
      add(food);
    }
    
    for (int i=0;i<foodName.size();i++)
    {
      JLabel served = new JLabel(Integer.toString(model.getFoodServed().get(foodName.get(i))));
      served.setBounds(580, 380+i*20, 100, 20);
      add(served);
    }
  }
  
  
  private String setMinSec(double value)
  {
	int min = (int)value/60;
	value -= 60*min;
	int sec = (int)value;
	if (sec<10)
	  return min + ":0" + sec;
	else
	  return min + ":" + sec;
  }
  
  
}
