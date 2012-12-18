package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import canteen.Canteen;

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
    
    JLabel allClients = new JLabel("Wszyscy klienci: " + model.getClientCount().getValue());
    allClients.setBounds(40, 60, 150, 20);
    add(allClients);
    
    JLabel indClients = new JLabel("Pojedynczy klienci: " + model.getIndyvidualClients());
    indClients.setBounds(40, 80, 150, 20);
    add(indClients);
    
    JLabel prvClient = new JLabel("Ilosc klientow uprzywilejowanych: " + model.getPriviligedCount());
    prvClient.setBounds(40, 100, 220, 20);
    add(prvClient);
    
    JLabel groups = new JLabel("Ilosc grup: " + model.getGroupsCount());
    groups.setBounds(40, 120, 150, 20);
    add(groups);
    
    JLabel avgGroupSize = new JLabel("Sredni rozmiar grupy: " + model.getGroups().getMean());
    avgGroupSize.setBounds(40, 140, 180, 20);
    add(avgGroupSize);
    
    JLabel clientStayed = new JLabel("Zostalo w stolowce: " + model.getClientStayed().getValue());
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
