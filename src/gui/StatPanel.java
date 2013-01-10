package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import canteen.Canteen;
import canteen.ClientGenerator;

public class StatPanel extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  private MyFrame frame;
  private Canteen model;
  private JButton save;
  
  private LinkedList<String> statList;
  
  
  /**
   * Konstruktor
   * @param _frame obiekt okna
   */
  public StatPanel(MyFrame _frame)
  {
    frame = _frame;
    model = frame.getCanteen();
    setLayout(null);
    statList = new LinkedList<String>();
    JLabel stat = new JLabel("Statystyki symulacji");
    add(stat);
    stat.setBounds(320, 10, 150, 20);
    save = new JButton("Zapisz dane symulacji");
    save.setBounds(420, 560, 160, 50);
    add(save);
    save.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          JFileChooser fileChooser = new JFileChooser();
		  int wynik = fileChooser.showSaveDialog(null);
		  if (wynik == 0)
		  {
		    File plik = fileChooser.getSelectedFile();
			FileWriter tw = null;
			try
			{
			  tw = new FileWriter(plik);
			}
		    catch (IOException e1) {}
			
			
			PrintWriter pw = new PrintWriter(tw);
			pw.println(model.description());
			pw.println();
			pw.println();
			pw.println();
			pw.println();
			pw.println();
			pw.println("Parametry symulacji:");
			pw.println();
			for (int i=0;i<frame.getStartPanel().getParamList().size();i++)
				pw.println(frame.getStartPanel().getParamList().get(i));
			pw.println();
			pw.println();
			pw.println();
			pw.println();
			pw.println();
			pw.println("Statystyki symulacji:");
			pw.println();
            for (int i=0;i<statList.size();i++)
            	pw.println(statList.get(i));
            
            
            pw.close();
            try
            {
			  tw.close();
			}
            catch (IOException e1) {}
	      }
		}
	  }
    );
  }
  
  /**
   * Ustawia wartosci do wyswietlenia i zapisu do pliku
   */
  public void setStat()
  {
	//klienci
    JLabel client = new JLabel("Klienci");
    client.setBounds(30, 40, 100, 20);
    add(client);
    statList.add("Klienci");
    
    JLabel allClients = new JLabel("Wszyscy klienci: " + ClientGenerator.allClientGenerate);
    allClients.setBounds(40, 60, 150, 20);
    add(allClients);
    statList.add(" Wszyscy klienci: " + ClientGenerator.allClientGenerate);
    
    JLabel indClients = new JLabel("Pojedynczy klienci: " + ClientGenerator.clientGenerate);
    indClients.setBounds(40, 80, 170, 20);
    add(indClients);
    statList.add(" Pojedynczy klienci: " + ClientGenerator.clientGenerate);
    
    JLabel prvClient = new JLabel("Iloœæ klientów uprzywilejowanych: " + ClientGenerator.privilageClientGenerate);
    prvClient.setBounds(40, 100, 280, 20);
    add(prvClient);
    statList.add(" Iloï¿½ï¿½ klientów uprzywilejowanych: " + ClientGenerator.privilageClientGenerate);
    
    JLabel groups = new JLabel("Iloœæ grup: " + ClientGenerator.groupClientGenerate);
    groups.setBounds(40, 120, 150, 20);
    add(groups);
    statList.add(" Iloœæ½ grup: " + ClientGenerator.groupClientGenerate);
    
    JLabel avgGroupSize = new JLabel("Œredni rozmiar grupy: " + model.getGroups().getMean());
    avgGroupSize.setBounds(40, 140, 230, 20);
    add(avgGroupSize);
    statList.add(" Œredni rozmiar grupy: " + model.getGroups().getMean());
    
    JLabel clientStayed = new JLabel("Zosta³o w sto³ówce: " + model.getClientStayedValue());
    clientStayed.setBounds(40, 170, 190, 20);
    add(clientStayed);
    statList.add(" Zosta³o w sto³ówce: " + model.getClientStayedValue());
    
    JLabel clientLeftBecOfPrice = new JLabel("Odesz³o przez zbyt wysokie ceny: " + model.getClientLeftBecOfPrice().getValue());
    clientLeftBecOfPrice.setBounds(40, 190, 270, 20);
    add(clientLeftBecOfPrice);
    statList.add(" Odesz³o przez zbyt wysokie ceny: " + model.getClientLeftBecOfPrice().getValue());
    
    JLabel clientLeftBecOfQueue = new JLabel("Odesz³o przez zbyt d³ug¹ kolejkê do kasy: " + model.getClientLeftBecOfQueue().getValue());
    clientLeftBecOfQueue.setBounds(40, 210, 320, 20);
    add(clientLeftBecOfQueue);
    statList.add(" Odesz³o przez zbyt d³ug¹ kolejkê do kasy: " + model.getClientLeftBecOfQueue().getValue());
    
    JLabel clientLeftBecOfNoFood = new JLabel("Odesz³o przez brak oczekiwanych dañ: " + model.getClientLeftBecOfNoFood().getValue());
    clientLeftBecOfNoFood.setBounds(40, 230, 250, 20);
    add(clientLeftBecOfNoFood);
    statList.add(" Odesz³o przez brak oczekiwanych dañ: " + model.getClientLeftBecOfNoFood().getValue());
    
    JLabel clientLeftBecOfNoPlace = new JLabel("Odesz³o przez brak miejsca: " + model.getClientLeftBecOfNoPlace().getValue());
    clientLeftBecOfNoPlace.setBounds(40, 250, 250, 20);
    add(clientLeftBecOfNoPlace);
    statList.add(" Odesz³o przez brak miejsca: " + model.getClientLeftBecOfNoPlace().getValue());
        
    statList.add("");
    
    
    //czas obslugi klienta
    JLabel serviceTime = new JLabel("Czas obs³ugi klienta");
    serviceTime.setBounds(30, 290, 170, 20);
    add(serviceTime);
    statList.add("Czas obs³ugi klienta");
    
    JLabel minServiceTime = new JLabel("Minimalny: " + setMinSec(model.getServiceTimeStat().getMinimum()));
    minServiceTime.setBounds(40, 310, 120, 20);
    add(minServiceTime);
    statList.add(" Minimalny: " + setMinSec(model.getServiceTimeStat().getMinimum()));
    
    JLabel maxServiceTime = new JLabel("Maksymalny: " + setMinSec(model.getServiceTimeStat().getMaximum()));
    maxServiceTime.setBounds(40, 330, 150, 20);
    add(maxServiceTime);
    statList.add(" Maksymalny: " + setMinSec(model.getServiceTimeStat().getMaximum()));
    
    JLabel meanServiceTime = new JLabel("Œredni: " + setMinSec(model.getServiceTimeStat().getMean()));
    meanServiceTime.setBounds(40, 350, 120, 20);
    add(meanServiceTime);
    statList.add(" Œredni: " + setMinSec(model.getServiceTimeStat().getMean()));
    
    JLabel stdServiceTime = new JLabel("Odchylenie standardowe: " + setMinSec(model.getServiceTimeStat().getStdDev()));
    stdServiceTime.setBounds(40, 370, 250, 20);
    add(stdServiceTime);
    statList.add(" Odchylenie standardowe: " + setMinSec(model.getServiceTimeStat().getStdDev()));
    
    statList.add("");
    
    
    //czas przygotowywania posilku
    JLabel mealPrepare = new JLabel("Czas przygotowania posi³ku");
    mealPrepare.setBounds(30, 410, 220, 20);
    add(mealPrepare);
    statList.add("Czas przygotowania posi³ku");
    
    JLabel minMealPrepate = new JLabel(" Minimalny: " + setMinSec(model.getMealPrepareTimeStat().getMinimum()));
    minMealPrepate.setBounds(40, 430, 180, 20);
    add(minMealPrepate);
    statList.add(" Minimalny: " + setMinSec(model.getMealPrepareTimeStat().getMinimum()));
    
    JLabel maxMealPrepare = new JLabel("Maksymalny: " + setMinSec(model.getMealPrepareTimeStat().getMaximum()));
    maxMealPrepare.setBounds(40, 450, 180, 20);
    add(maxMealPrepare);
    statList.add(" Maksymalny: " + setMinSec(model.getMealPrepareTimeStat().getMaximum()));
    
    JLabel meanMealPrepare = new JLabel("Œredni: " + setMinSec(model.getMealPrepareTimeStat().getMean()));
    meanMealPrepare.setBounds(40, 470, 180, 20);
    add(meanMealPrepare);
    statList.add(" Œredni: " + setMinSec(model.getMealPrepareTimeStat().getMean()));
    
    JLabel stdMealPrepare = new JLabel("Odchylenie standardowe: " + setMinSec(model.getMealPrepareTimeStat().getStdDev()));
    stdMealPrepare.setBounds(40, 490, 250, 20);
    add(stdMealPrepare);
    statList.add(" Odchylenie standardowe: " + setMinSec(model.getMealPrepareTimeStat().getStdDev()));
    
    statList.add("");
    
    
    //czas jedzenia posilku
    JLabel eatTime = new JLabel("Czas jedzenia pos³ku");
    eatTime.setBounds(30, 530, 180, 20);
    add(eatTime);
    statList.add("Czas jedzenia posi³ku");
    
    JLabel minEatTime = new JLabel("Minimalny: " + setMinSec(model.getMealEatTimeStat().getMinimum()));
    minEatTime.setBounds(40, 550, 180, 20);
    add(minEatTime);
    statList.add(" Minimalny: " + setMinSec(model.getMealEatTimeStat().getMinimum()));
    
    JLabel maxEatTime = new JLabel("Maksymalny: " + setMinSec(model.getMealEatTimeStat().getMaximum()));
    maxEatTime.setBounds(40, 570, 180, 20);
    add(maxEatTime);
    statList.add(" Maksymalny: " + setMinSec(model.getMealEatTimeStat().getMaximum()));
    
    JLabel meanEatTime = new JLabel("Œredni: " + setMinSec(model.getMealEatTimeStat().getMean()));
    meanEatTime.setBounds(40, 590, 180, 20);
    add(meanEatTime);
    statList.add(" Œredni: " + setMinSec(model.getMealEatTimeStat().getMean()));
    
    JLabel stdEatTime = new JLabel("Odchylenie standardowe: " + setMinSec(model.getMealEatTimeStat().getStdDev()));
    stdEatTime.setBounds(40, 610, 250, 20);
    add(stdEatTime);
    statList.add(" Odchylenie standardowe: " + setMinSec(model.getMealEatTimeStat().getStdDev()));
    
    statList.add("");
    
    //kolejka do kasy
    JLabel queueToCashier = new JLabel("Kolejka klientów do kasy");
    queueToCashier.setBounds(400, 40, 190, 20);
    add(queueToCashier);
    statList.add("Kolejka klientów do kasy");
    
    JLabel maxQueueToCashier = new JLabel("Maksymalna " + (int)model.getQueueToCashier().getMaximum());
    maxQueueToCashier.setBounds(410, 60, 150, 20);
    add(maxQueueToCashier);
    statList.add(" Maksymalna " + (int)model.getQueueToCashier().getMaximum());
    
    JLabel meanQueueToCashier = new JLabel("Œrednia " + model.getQueueToCashier().getMean());
    meanQueueToCashier.setBounds(410, 80, 250, 20);
    add(meanQueueToCashier);
    statList.add(" Œrednia " + model.getQueueToCashier().getMean());
    
    JLabel stdQueueToCashier = new JLabel("Odchylenie standardowe " + model.getQueueToCashier().getStdDev());
    stdQueueToCashier.setBounds(410, 100, 250, 20);
    add(stdQueueToCashier);
    statList.add(" Odchylenie standardowe " + model.getQueueToCashier().getStdDev());
    
    statList.add("");
    
    //kolejka po miejsce
    JLabel queueForPlace = new JLabel("Kolejka klientów po miejsce przy stoliku");
    queueForPlace.setBounds(400, 130, 300, 20);
    add(queueForPlace);
    statList.add("Kolejka klientów po miejsce przy stoliku");
    
    JLabel maxQueueForPlace = new JLabel("Maksymalna: " + (int)model.getQueueForPlace().getMaximum());
    maxQueueForPlace.setBounds(410, 150, 250, 20);
    add(maxQueueForPlace);
    statList.add(" Maksymalna: " + (int)model.getQueueForPlace().getMaximum());
    
    JLabel meanQueueForPlace = new JLabel("Œrednia: " + model.getQueueForPlace().getMean());
    meanQueueForPlace.setBounds(410, 170, 250, 20);
    add(meanQueueForPlace);
    statList.add(" Œrednia: " + model.getQueueForPlace().getMean());
    
    JLabel stdQueueForPlace = new JLabel("Odchylenie standardowe: " + model.getQueueForPlace().getStdDev());
    stdQueueForPlace.setBounds(410, 190, 250, 20);
    add(stdQueueForPlace);
    statList.add(" Odchylenie standardowe: " + model.getQueueForPlace().getStdDev());
    
    statList.add("");
    
    //wolne kasjerki
    JLabel idleCashier = new JLabel("Wolne kasjerki");
    idleCashier.setBounds(400, 220, 150, 20);
    add(idleCashier);
    statList.add("Wolne kasjerki");
    
    double mean = model.getIdleCashierStat().getMean();
    double std = model.getIdleCashierStat().getStdDev();
    if (new Double(model.getIdleCashierStat().getMean()).equals(Double.NaN))
    {
      mean = 0;
      std=0;
    }
    JLabel meanIdleCashier = new JLabel(" Œrednio: " + mean);
    meanIdleCashier.setBounds(410, 240, 250, 20);
    add(meanIdleCashier);
    statList.add(" Œrednio: " + mean);
    
    JLabel stdIdleCashier = new JLabel(" Odchylenie standardowe: " + std);
    stdIdleCashier.setBounds(410, 260, 250, 20);
    add(stdIdleCashier);
    statList.add(" Odchylenie standardowe: " + std);
    
    statList.add("");
    
    
    //wolni kucharze
    JLabel idleCook = new JLabel("Wolni kucharze");
    idleCook.setBounds(400, 290, 150, 20);
    add(idleCook);
    statList.add("Wolni kucharze");
    
    JLabel meanIdleCook = new JLabel(" Œrednio: " + model.getIdleCookStat().getMean());
    meanIdleCook.setBounds(410, 310, 250, 20);
    add(meanIdleCook);
    statList.add(" Œrednio: " + model.getIdleCookStat().getMean());
    
    JLabel stdIdleCook = new JLabel(" Odchylenie standardowe: " + model.getIdleCookStat().getStdDev());
    stdIdleCook.setBounds(410, 330, 250, 20);
    add(stdIdleCook);
    statList.add(" Odchylenie standardowe: " + model.getIdleCookStat().getStdDev());
    
    statList.add("");
    
    JLabel foodPrepare = new JLabel("Dania             przygotowane      wydane");
    foodPrepare.setBounds(400, 360, 320, 20);
    add(foodPrepare);
    statList.add("Dania             przygotowane      wydane");
    
    LinkedList<String> foodName = new LinkedList<String>();
    for (String dish : model.getDishesStorage().getDish())
      foodName.add(dish);
    for (String dish : model.getDishesStorage().getSoup())
        foodName.add(dish);
    for (String dish : model.getDishesStorage().getDrink())
        foodName.add(dish);
    
    for (int i=0;i<foodName.size();i++)
    {
      JLabel food = new JLabel(foodName.get(i).substring(0, 1).toUpperCase() + foodName.get(i).substring(1, foodName.get(i).length()));
      food.setBounds(410, 380+i*20, 120, 20);
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
    
    
    for (String dish : model.getDishesStorage().getDish())
    {
      statList.add(" " + dish + spaces(dish.length()) + model.getFoodStat().get(dish) + "             " + model.getFoodServed().get(dish));
    }
    for (String dish : model.getDishesStorage().getSoup())
    {
      statList.add(" " + dish + spaces(dish.length()) + model.getFoodStat().get(dish) + "             " + model.getFoodServed().get(dish));
    }
    for (String dish : model.getDishesStorage().getDrink())
    {
      statList.add(" " + dish + spaces(dish.length()) + model.getFoodStat().get(dish) + "            " + model.getFoodServed().get(dish));
    }
  }
  
  /**
   * Zwraca napis w formacie MM:SS
   * @param value czas do przekonwertowania
   * @return napis w formacie MM:SS
   */
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
  
  /**
   * Zwraca napis bedacy samymi spacjami
   * @param value ilosc spacji do wytworzenia
   * @return napis bedacy spacjami
   */
  private String spaces(int value)
  {
	String napis = "";
    for (int i=0;i<22-value;i++)
      napis+=" ";
    return napis;
  }
  
  
}
