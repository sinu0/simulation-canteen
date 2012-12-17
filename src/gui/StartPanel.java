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
  private JSpinner highPriceSpinner;
  private JSpinner longQueueSpinner;
  private JSpinner serviceTimeSpinner;
  private JSpinner eatTimeSpinner;
  private JSpinner foodPrepareTimeSpinner;
  private JSpinner minValueIngredientSpinner;
  
  private JButton startButton;
  
  
  private int minTimeClientValue;
  private int maxTimeClientValue;
  
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
    
    
    //minimalny czas pojawiania sie nowego klienta badz grupy
    Box minTimeClientBox = Box.createHorizontalBox();    
    JLabel minTimeClientLabel = new JLabel("Min i max czas pojawiania sie nowych klientow");
    minTimeClientBox.add(minTimeClientLabel);
    spinnerModel = new SpinnerNumberModel(5, 1, 30, 1);
    maxTimeClientSpinner = new JSpinner(spinnerModel);
    maxTimeClientValue = (int)maxTimeClientSpinner.getValue();
    minTimeClientSpinner = new JSpinner(spinnerModel);
    minTimeClientValue = (int)minTimeClientSpinner.getValue();
    minTimeClientSpinner.setMaximumSize(spinnerSize);
    minTimeClientSpinner.addChangeListener(new ChangeListener()
      {
        @Override
		public void stateChanged(ChangeEvent e)
		{
		  maxTimeClientSpinner.setModel(new SpinnerNumberModel(maxTimeClientValue, (int)minTimeClientSpinner.getValue(), 30, 1));
		  minTimeClientValue = (int)minTimeClientSpinner.getValue();	
		}
	  }
    );
    minTimeClientBox.add(Box.createRigidArea(rigidInBox));
    minTimeClientBox.add(minTimeClientSpinner);
    add(minTimeClientBox);
    add(Box.createRigidArea(rigidOutBox));
    
    
    
    
    //maksymalny czas pojawiania sie nowego klienta badz grupy
    Box maxTimeClientBox = Box.createHorizontalBox();
    //JLabel maxTimeClientLabel = new JLabel("Maksymalny czas pojawiania sie nowych klientow");
    //maxTimeClientBox.add(maxTimeClientLabel);
    spinnerModel = new SpinnerNumberModel(5, 1, 30, 1);
    
    maxTimeClientSpinner.setMaximumSize(spinnerSize);
    maxTimeClientSpinner.addChangeListener(new ChangeListener()
      {
        public void stateChanged(ChangeEvent arg0)
        {
          minTimeClientSpinner.setModel(new SpinnerNumberModel(minTimeClientValue, 1, (int)maxTimeClientSpinner.getValue(), 1));
          maxTimeClientValue = (int)maxTimeClientSpinner.getValue();
		}
	  }
    );
    minTimeClientBox.add(Box.createRigidArea(rigidInBox));
    minTimeClientBox.add(maxTimeClientSpinner);
    add(maxTimeClientBox);
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
    spinnerModel = new SpinnerNumberModel(3, 2, 4, 1);
    groupSizeSpinner = new JSpinner(spinnerModel);
    groupSizeSpinner.setMaximumSize(spinnerSize);
    groupSizeBox.add(Box.createRigidArea(rigidInBox));
    groupSizeBox.add(groupSizeSpinner);
    add(groupSizeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //sklonnosc do uznawania cen za wysokie
    Box highPriceBox = Box.createHorizontalBox();
    JLabel highPriceLabel = new JLabel("Sklonnosc klientow do uznawania cen za wysokie (niska 1-10 wysoka)");
    highPriceBox.add(highPriceLabel);
    spinnerModel = new SpinnerNumberModel(3, 1, 10, 1);
    highPriceSpinner = new JSpinner(spinnerModel);
    highPriceSpinner.setMaximumSize(spinnerSize);
    highPriceBox.add(Box.createRigidArea(rigidInBox));
    highPriceBox.add(highPriceSpinner);
    add(highPriceBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //sklonnosc klientow do uznawania kolejki za dluga
    Box longQueueBox = Box.createHorizontalBox();
    JLabel longQueueLabel = new JLabel("Sklonnosc klientow do uznawania kolejki za dluga (niska 1-10 wysoka)");
    longQueueBox.add(longQueueLabel);
    spinnerModel = new SpinnerNumberModel(3, 1, 10, 1);
    longQueueSpinner = new JSpinner(spinnerModel);
    longQueueSpinner.setMaximumSize(spinnerSize);
    longQueueBox.add(Box.createRigidArea(rigidInBox));
    longQueueBox.add(longQueueSpinner);
    add(longQueueBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //czas obslugi klienta
    Box serviceTimeBox = Box.createHorizontalBox();
    JLabel serviceTimeLabel = new JLabel("Czas obslugi klienta");
    serviceTimeBox.add(serviceTimeLabel);
    spinnerModel = new SpinnerNumberModel(10, 1, 100, 1);
    serviceTimeSpinner = new JSpinner(spinnerModel);
    serviceTimeSpinner.setMaximumSize(spinnerSize);
    serviceTimeBox.add(Box.createRigidArea(rigidInBox));
    serviceTimeBox.add(serviceTimeSpinner);
    add(serviceTimeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //czas jedzenia posilku
    Box eatTimeBox = Box.createHorizontalBox();
    JLabel eatTimeLabel = new JLabel("Czas jedzenia posilku");
    eatTimeBox.add(eatTimeLabel);
    spinnerModel = new SpinnerNumberModel(10, 1, 100, 1);
    eatTimeSpinner = new JSpinner(spinnerModel);
    eatTimeSpinner.setMaximumSize(spinnerSize);
    eatTimeBox.add(Box.createRigidArea(rigidInBox));
    eatTimeBox.add(eatTimeSpinner);
    add(eatTimeBox);
    add(Box.createRigidArea(rigidOutBox));
    
    //czas przygotowania skladnikow w kuchnii
    Box foodPrepareTimeBox = Box.createHorizontalBox();
    JLabel foodPrepareTimeLabel = new JLabel("Czas przygotowania skladnikow");
    foodPrepareTimeBox.add(foodPrepareTimeLabel);
    spinnerModel = new SpinnerNumberModel(10, 1, 100, 1);
    foodPrepareTimeSpinner = new JSpinner(spinnerModel);
    foodPrepareTimeSpinner.setMaximumSize(spinnerSize);
    foodPrepareTimeBox.add(Box.createRigidArea(rigidInBox));
    foodPrepareTimeBox.add(foodPrepareTimeSpinner);
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
          
          
          
          frame.getCanteen().setMinMealCount((int)minValueIngredientSpinner.getValue());
          frame.getCanteen().setClientArrivialTime(Double.parseDouble(Integer.toString((int)minTimeClientSpinner.getValue())), Double.parseDouble(Integer.toString((int)maxTimeClientSpinner.getValue())));
          
          //if (frame.getCanteen()!=null) System.out.println("NIE NULL");
          frame.getCanteen().setMyFrame(frame);
          canteenThread.start();
          for (int i=0;i<(int)table2Spinner.getValue();i++)
              frame.getCanteen().addTable2();
          for (int i=0;i<(int)table4Spinner.getValue();i++)
              frame.getCanteen().addTable4();
          
          
          System.out.println("Watek wystartowany");
          frame.getAnimPanel().drawPanel();
		}
	  }
    );
    	
        
    
    
    
    
    
    
    
  }

}
