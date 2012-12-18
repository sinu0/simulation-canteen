package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StartPanel extends JPanel 
{
  private static final long serialVersionUID = 1L;
  
  private MyFrame frame;
  
  private Thread canteenThread;
  
  private SpinnerNumberModel spinnerModel;
  private JSpinner maxSimTimeSpinner;
  private JSpinner cashierSpinner;
  private JSpinner cookSpinner;
  private JSpinner table2Spinner;
  private JSpinner table4Spinner;
  private JSpinner minTimeClientSpinner;
  private JSpinner maxTimeClientSpinner;
  private JSpinner groupFreqSpinner;
  private JSpinner groupSizeSpinner;
  private JSpinner priviligedFreqSpinner;
  private JSpinner highPriceSpinner;
  private JSpinner longQueueSpinner;
  private JSpinner minServiceTimeSpinner;
  private JSpinner maxServiceTimeSpinner;
  private JSpinner minEatTimeSpinner;
  private JSpinner maxEatTimeSpinner;
  private JSpinner minFoodPrepareTimeSpinner;
  private JSpinner maxFoodPrepareTimeSpinner;
  private JSpinner minValueIngredientSpinner;
  private JSpinner mealPriceSpinner;
  
  private JButton startButton;
  
  
  private int minTimeClientValue;
  private int maxTimeClientValue;
  
  private int minClientServiceTime;
  private int maxClientServiceTime;
  
  private int minEatTimeValue;
  private int maxEatTimeValue;
  
  private int minFoodPrepareTimeValue;
  private int maxFoodPrepareTimeValue;
  
  
  StartPanel(MyFrame _frame)
  {
	frame = _frame;
	canteenThread = new Thread(frame.getCanteen());
	
    add(new JLabel("Parametry symulacji"));
    add(Box.createRigidArea(new Dimension(0, 20)));
    setLayout(new BoxLayout(this, 1));
    
    //wymiary 
    Dimension rigidInBox = new Dimension(10, 0); //miedzy elementami w box'ie
	Dimension rigidOutBox = new Dimension(0, 5); //miedzy box'ami
	Dimension spinnerSize = new Dimension(60, 20); //wymiar spinner'a
    
    //max czas symulacji
    Box timeBox = Box.createHorizontalBox();
    JLabel maxTimeLabel = new JLabel("Maksymalny czas symulacji [h]");
    timeBox.add(maxTimeLabel);
    spinnerModel = new SpinnerNumberModel(8, 1, 10, 1);
    maxSimTimeSpinner = new JSpinner(spinnerModel);
    maxSimTimeSpinner.setMaximumSize(new Dimension(80, 20));
    timeBox.add(Box.createRigidArea(rigidInBox));
    timeBox.add(maxSimTimeSpinner);
    add(timeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //ilosc kasjerek
    Box cashierBox = Box.createHorizontalBox();
    JLabel cashierLabel = new JLabel("Ilosc kasjerek");
    cashierBox.add(cashierLabel);
    spinnerModel = new SpinnerNumberModel(1, 1, 5, 1);
    cashierSpinner = new JSpinner(spinnerModel);
    cashierSpinner.setMaximumSize(spinnerSize);
    cashierBox.add(Box.createRigidArea(rigidInBox));
    cashierBox.add(cashierSpinner);
    add(cashierBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //ilosc kucharzy
    Box cookBox = Box.createHorizontalBox();
    JLabel cookLabel = new JLabel("Ilosc kucharzy");
    cookBox.add(cookLabel);
    spinnerModel = new SpinnerNumberModel(2, 1, 5, 1);
    cookSpinner = new JSpinner(spinnerModel);
    cookSpinner.setMaximumSize(spinnerSize);
    cookBox.add(Box.createRigidArea(rigidInBox));
    cookBox.add(cookSpinner);
    add(cookBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //ilosc stolow 2-osobowych
    Box table2Box = Box.createHorizontalBox();
    JLabel table2Label = new JLabel("Ilosc stolow 2-osobowych");
    table2Box.add(table2Label);
    spinnerModel = new SpinnerNumberModel(5, 1 ,10 ,1);
    table2Spinner = new JSpinner(spinnerModel);
    table2Spinner.setMaximumSize(spinnerSize);
    table2Box.add(Box.createRigidArea(rigidInBox));
    table2Box.add(table2Spinner);
    add(table2Box);
    add(Box.createRigidArea(rigidOutBox));
    
    //ilosc stolow 4-osobowych
    Box table4Box = Box.createHorizontalBox();
    JLabel table4Label = new JLabel("Ilosc stolow 4-osobowych");
    table4Box.add(table4Label);
    spinnerModel = new SpinnerNumberModel(5, 1, 10, 1);
    table4Spinner = new JSpinner(spinnerModel);
    table4Spinner.setMaximumSize(spinnerSize);
    table4Box.add(Box.createRigidArea(rigidInBox));
    table4Box.add(table4Spinner);
    add(table4Box);
    add(Box.createRigidArea(rigidOutBox));
    
    
    //minimalny czas pojawiania sie nowego klienta
    Box minTimeClientBox = Box.createHorizontalBox();    
    JLabel minTimeClientLabel = new JLabel("Min i max czas pojawiania sie nowych klientow");
    minTimeClientBox.add(minTimeClientLabel);
    spinnerModel = new SpinnerNumberModel(4, 1, 5, 1);
    minTimeClientSpinner = new JSpinner(spinnerModel);
    minTimeClientValue = (int)minTimeClientSpinner.getValue();
    minTimeClientSpinner.setMaximumSize(spinnerSize);
    
    spinnerModel = new SpinnerNumberModel(6, 5, 30, 1);
    maxTimeClientSpinner = new JSpinner(spinnerModel);
    maxTimeClientValue = (int)maxTimeClientSpinner.getValue();
    maxTimeClientSpinner.setMaximumSize(spinnerSize);
    
    minTimeClientSpinner.addChangeListener(new ChangeListener()
      {
        @Override
		public void stateChanged(ChangeEvent e)
		{
          minTimeClientValue = (int)minTimeClientSpinner.getValue();
          maxTimeClientValue = (int)maxTimeClientSpinner.getValue();
          minTimeClientSpinner.setModel(new SpinnerNumberModel(minTimeClientValue, 1, maxTimeClientValue, 1));
		  maxTimeClientSpinner.setModel(new SpinnerNumberModel(maxTimeClientValue, minTimeClientValue, 30, 1));
		  	
		}
	  }
    );
    maxTimeClientSpinner.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent arg0)
      {
    	minTimeClientValue = (int)minTimeClientSpinner.getValue();
        maxTimeClientValue = (int)maxTimeClientSpinner.getValue();
        maxTimeClientSpinner.setModel(new SpinnerNumberModel(maxTimeClientValue, minTimeClientValue, 30, 1));
        minTimeClientSpinner.setModel(new SpinnerNumberModel(minTimeClientValue, 1, maxTimeClientValue, 1));
		}
	  }
    );
    minTimeClientBox.add(Box.createRigidArea(rigidInBox));
    minTimeClientBox.add(minTimeClientSpinner);
    minTimeClientBox.add(Box.createRigidArea(rigidInBox));
    minTimeClientBox.add(maxTimeClientSpinner);
    add(minTimeClientBox);
    add(Box.createRigidArea(rigidOutBox));
    
    
    
    //czestosc pojawiania sie grupy
    Box groupFreqBox = Box.createHorizontalBox();
    JLabel groupFreqLabel = new JLabel("Czestosc pojawiania sie grupy (w %)");
    groupFreqBox.add(groupFreqLabel);
    spinnerModel = new SpinnerNumberModel(25, 1, 100, 1);
    groupFreqSpinner = new JSpinner(spinnerModel);
    groupFreqSpinner.setMaximumSize(spinnerSize);
    groupFreqBox.add(Box.createRigidArea(rigidInBox));
    groupFreqBox.add(groupFreqSpinner);
    add(groupFreqBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //rozmiar grupy
    Box groupSizeBox = Box.createHorizontalBox();
    JLabel groupSizeLabel = new JLabel("Maksymalny rozmiar grupy");
    groupSizeBox.add(groupSizeLabel);
    spinnerModel = new SpinnerNumberModel(4, 2, 6, 1);
    groupSizeSpinner = new JSpinner(spinnerModel);
    groupSizeSpinner.setMaximumSize(spinnerSize);
    groupSizeBox.add(Box.createRigidArea(rigidInBox));
    groupSizeBox.add(groupSizeSpinner);
    add(groupSizeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //czsetosc przychodzenia klientow uprzywilejowanych
    Box priviligedFreqBox = Box.createHorizontalBox();
    JLabel priviligedFreqLabel = new JLabel("Czestosc pojawiania sie klientow uprzywilejowanych (w %)");
    priviligedFreqBox.add(priviligedFreqLabel);
    spinnerModel = new SpinnerNumberModel(2, 1, 100, 1);
    priviligedFreqSpinner = new JSpinner(spinnerModel);
    priviligedFreqSpinner.setMaximumSize(spinnerSize);
    priviligedFreqBox.add(Box.createRigidArea(rigidInBox));
    priviligedFreqBox.add(priviligedFreqSpinner);
    add(priviligedFreqBox);
    add(Box.createRigidArea(rigidOutBox));
    
    
    //sklonnosc do uznawania cen za wysokie
    Box highPriceBox = Box.createHorizontalBox();
    JLabel highPriceLabel = new JLabel("Srednia akceptowalna cena dan przez klienta");
    highPriceBox.add(highPriceLabel);
    spinnerModel = new SpinnerNumberModel(10, 8, 15, 1);
    highPriceSpinner = new JSpinner(spinnerModel);
    highPriceSpinner.setMaximumSize(spinnerSize);
    highPriceBox.add(Box.createRigidArea(rigidInBox));
    highPriceBox.add(highPriceSpinner);
    add(highPriceBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //sklonnosc klientow do uznawania kolejki za dluga
    Box longQueueBox = Box.createHorizontalBox();
    JLabel longQueueLabel = new JLabel("Srednia akceptowalna dlugosc kolejki przez klienta");
    longQueueBox.add(longQueueLabel);
    spinnerModel = new SpinnerNumberModel(7, 1, 20, 1);
    longQueueSpinner = new JSpinner(spinnerModel);
    longQueueSpinner.setMaximumSize(spinnerSize);
    longQueueBox.add(Box.createRigidArea(rigidInBox));
    longQueueBox.add(longQueueSpinner);
    add(longQueueBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //czas obslugi klienta
    Box serviceTimeBox = Box.createHorizontalBox();
    JLabel serviceTimeLabel = new JLabel("Min i max czas obslugi klienta");
    serviceTimeBox.add(serviceTimeLabel);
    
    spinnerModel = new SpinnerNumberModel(1, 1, 2, 1);
    minServiceTimeSpinner = new JSpinner(spinnerModel);
    minClientServiceTime = (int)minServiceTimeSpinner.getValue();
    minServiceTimeSpinner.setMaximumSize(spinnerSize);
    
    spinnerModel = new SpinnerNumberModel(2, 2, 5, 1);
    maxServiceTimeSpinner = new JSpinner(spinnerModel);
    maxClientServiceTime = (int)maxServiceTimeSpinner.getValue();
    maxServiceTimeSpinner.setMaximumSize(spinnerSize);
    
    minServiceTimeSpinner.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			minClientServiceTime = (int)minServiceTimeSpinner.getValue();
			maxClientServiceTime = (int)maxServiceTimeSpinner.getValue();
			minServiceTimeSpinner.setModel(new SpinnerNumberModel(minClientServiceTime, 1, maxClientServiceTime, 1));
			maxServiceTimeSpinner.setModel(new SpinnerNumberModel(maxClientServiceTime, minClientServiceTime, 5, 1));
			
		}
	});
    maxServiceTimeSpinner.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			maxClientServiceTime = (int)maxServiceTimeSpinner.getValue();
			minClientServiceTime = (int)minServiceTimeSpinner.getValue();
			maxServiceTimeSpinner.setModel(new SpinnerNumberModel(maxClientServiceTime, minClientServiceTime, 5, 1));
			minServiceTimeSpinner.setModel(new SpinnerNumberModel(minClientServiceTime, 1, maxClientServiceTime, 1));
			
			
		}
	});
    serviceTimeBox.add(Box.createRigidArea(rigidInBox));
    serviceTimeBox.add(minServiceTimeSpinner);
    serviceTimeBox.add(Box.createRigidArea(rigidInBox));
    serviceTimeBox.add(maxServiceTimeSpinner);
    add(serviceTimeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    
    //czas jedzenia posilku
    Box eatTimeBox = Box.createHorizontalBox();
    JLabel eatTimeLabel = new JLabel("Min i max czas jedzenia posilku");
    eatTimeBox.add(eatTimeLabel);
    
    spinnerModel = new SpinnerNumberModel(6, 1, 6, 1);
    minEatTimeSpinner = new JSpinner(spinnerModel);
    minEatTimeSpinner.setMaximumSize(spinnerSize);
    minEatTimeValue = (int)minEatTimeSpinner.getValue();
    
    spinnerModel = new SpinnerNumberModel(12, 12, 20, 1);
    maxEatTimeSpinner = new JSpinner(spinnerModel);
    maxEatTimeSpinner.setMaximumSize(spinnerSize);
    maxEatTimeValue = (int)maxEatTimeSpinner.getValue();
    
    
    minEatTimeSpinner.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			minEatTimeValue = (int)minEatTimeSpinner.getValue();
			maxEatTimeValue = (int)maxEatTimeSpinner.getValue();
			minEatTimeSpinner.setModel(new SpinnerNumberModel(minEatTimeValue, 1, maxEatTimeValue, 1));
			maxEatTimeSpinner.setModel(new SpinnerNumberModel(maxEatTimeValue, minEatTimeValue, 20, 1));
			
		}
	});
    maxEatTimeSpinner.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			minEatTimeValue = (int)minEatTimeSpinner.getValue();
			maxEatTimeValue = (int)maxEatTimeSpinner.getValue();
			maxEatTimeSpinner.setModel(new SpinnerNumberModel(maxEatTimeValue, minEatTimeValue, 20, 1));
			minEatTimeSpinner.setModel(new SpinnerNumberModel(minEatTimeValue, 1, maxEatTimeValue, 1));		
		}
	});
    eatTimeBox.add(Box.createRigidArea(rigidInBox));
    eatTimeBox.add(minEatTimeSpinner);
    eatTimeBox.add(Box.createRigidArea(rigidInBox));
    eatTimeBox.add(maxEatTimeSpinner);
    add(eatTimeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    
    //czas przygotowania skladnikow w kuchnii
    Box foodPrepareTimeBox = Box.createHorizontalBox();
    JLabel foodPrepareTimeLabel = new JLabel("Min i max czas przygotowania skladnikow");
    foodPrepareTimeBox.add(foodPrepareTimeLabel);
    spinnerModel = new SpinnerNumberModel(6, 1, 6, 1);
    
    minFoodPrepareTimeSpinner = new JSpinner(spinnerModel);
    minFoodPrepareTimeSpinner.setMaximumSize(spinnerSize);
    minFoodPrepareTimeValue = (int)minFoodPrepareTimeSpinner.getValue();
    
    spinnerModel = new SpinnerNumberModel(12, 12, 25, 1);
    maxFoodPrepareTimeSpinner = new JSpinner(spinnerModel);
    maxFoodPrepareTimeSpinner.setMaximumSize(spinnerSize);
    maxFoodPrepareTimeValue = (int)maxFoodPrepareTimeSpinner.getValue();
    minFoodPrepareTimeSpinner.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			  minFoodPrepareTimeValue = (int)minFoodPrepareTimeSpinner.getValue();
			  maxFoodPrepareTimeValue = (int)maxFoodPrepareTimeSpinner.getValue();
			  minFoodPrepareTimeSpinner.setModel(new SpinnerNumberModel(minFoodPrepareTimeValue, 1, maxFoodPrepareTimeValue, 1));
			  maxFoodPrepareTimeSpinner.setModel(new SpinnerNumberModel(maxFoodPrepareTimeValue, minFoodPrepareTimeValue, 25, 1));
		}
	});
    maxFoodPrepareTimeSpinner.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			  maxFoodPrepareTimeValue = (int)maxFoodPrepareTimeSpinner.getValue();
			  minFoodPrepareTimeValue = (int)minFoodPrepareTimeSpinner.getValue();
			  maxFoodPrepareTimeSpinner.setModel(new SpinnerNumberModel(maxFoodPrepareTimeValue, minFoodPrepareTimeValue, 25, 1));
			  minFoodPrepareTimeSpinner.setModel(new SpinnerNumberModel(minFoodPrepareTimeValue, 1, maxFoodPrepareTimeValue, 1));
		}
	});
    
    foodPrepareTimeBox.add(Box.createRigidArea(rigidInBox));
    foodPrepareTimeBox.add(minFoodPrepareTimeSpinner);
    foodPrepareTimeBox.add(Box.createRigidArea(rigidInBox));
    foodPrepareTimeBox.add(maxFoodPrepareTimeSpinner);
    add(foodPrepareTimeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //minimalna wartosc skladnika
    Box minValueIngredientBox = Box.createHorizontalBox();
    JLabel minValueIngredientLabel = new JLabel("Minimalna wartosc skladnika dania");
    minValueIngredientBox.add(minValueIngredientLabel);
    spinnerModel = new SpinnerNumberModel(3, 1, 10, 1);
    minValueIngredientSpinner = new JSpinner(spinnerModel);
    minValueIngredientSpinner.setMaximumSize(spinnerSize);
    minValueIngredientBox.add(Box.createRigidArea(rigidInBox));
    minValueIngredientBox.add(minValueIngredientSpinner);
    add(minValueIngredientBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //srednia cena dania
    Box mealPriceBox = Box.createHorizontalBox();
    JLabel mealPriceLabel = new JLabel("Srednia cena dan");
    mealPriceBox.add(mealPriceLabel);
    spinnerModel = new SpinnerNumberModel(10, 6, 15, 1);
    mealPriceSpinner = new JSpinner(spinnerModel);
    mealPriceSpinner.setMaximumSize(spinnerSize);
    mealPriceBox.add(Box.createRigidArea(rigidInBox));
    mealPriceBox.add(mealPriceSpinner);
    add(mealPriceBox);
    add(Box.createRigidArea(new Dimension(0, 50)));
    
    
    
    
    startButton = new JButton("START KURWA!!!!!");
    startButton.setMaximumSize(new Dimension(200, 100));
    this.add(startButton);
    startButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent arg0)
        {
          frame.getContentPane().removeAll();
          frame.add(frame.getAnimPanel());
          frame.revalidate();
          frame.repaint();

          
          //frame.set
          frame.getCanteen().setSimTime((int)maxSimTimeSpinner.getValue());
          frame.getCanteen().setCashierCount((int)cashierSpinner.getValue());
          frame.getCanteen().setCookCount((int)cookSpinner.getValue());
          frame.getCanteen().setTable2Count((int)table2Spinner.getValue());
          frame.getCanteen().setTable4Count((int)table4Spinner.getValue());
          frame.getCanteen().setClientArrivialTime(Double.parseDouble(Integer.toString((int)minTimeClientSpinner.getValue())), Double.parseDouble(Integer.toString((int)maxTimeClientSpinner.getValue())));
          frame.getCanteen().setGroupGeneratorMultiplier(100/Double.parseDouble(Integer.toString((int)groupFreqSpinner.getValue())));
          frame.getCanteen().setMaxGroupSize((int)groupSizeSpinner.getValue());
          frame.getCanteen().setPriviligedClientMultiplier(100/Double.parseDouble(Integer.toString((int)priviligedFreqSpinner.getValue())));
          frame.getCanteen().setClientAveragePrice(Double.parseDouble(Integer.toString((int)highPriceSpinner.getValue())), Double.parseDouble(Integer.toString((int)highPriceSpinner.getValue()))+5);
          frame.getCanteen().setClientMaxAcceptableQueue(Double.parseDouble(Integer.toString((int)longQueueSpinner.getValue())), Double.parseDouble(Integer.toString((int)longQueueSpinner.getValue()))+7);
          frame.getCanteen().setClientServiceTime(Double.parseDouble(Integer.toString((int)minServiceTimeSpinner.getValue())), Double.parseDouble(Integer.toString((int)maxServiceTimeSpinner.getValue())));
          frame.getCanteen().setMealEatTime(Double.parseDouble(Integer.toString((int)minEatTimeSpinner.getValue())),Double.parseDouble(Integer.toString((int)maxEatTimeSpinner.getValue())));
          frame.getCanteen().setMealPrepareTime(Double.parseDouble(Integer.toString((int)minFoodPrepareTimeSpinner.getValue())), Double.parseDouble(Integer.toString((int)maxFoodPrepareTimeSpinner.getValue())));
          frame.getCanteen().setCanteenAveragePrice(Double.parseDouble(Integer.toString((int)mealPriceSpinner.getValue())));
          frame.getCanteen().setMinMealCount((int)minValueIngredientSpinner.getValue());
          
         
          
          for (int i=0;i<(int)table2Spinner.getValue();i++)
              frame.getCanteen().addTable2();
          for (int i=0;i<(int)table4Spinner.getValue();i++)
              frame.getCanteen().addTable4();
          
          System.out.println("Watek wystartowany");
          frame.getCanteen().setMyFrame(frame);
          canteenThread.start();
          frame.getAnimPanel().drawPanel();
		}
	  }
    );
    	
        
    
    
    
    
    
    
    
  }

}
