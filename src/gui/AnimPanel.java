package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import canteen.Canteen;

public class AnimPanel extends JPanel implements PropertyChangeListener
{
  private static final long serialVersionUID = 1L;
  
  private MyFrame frame;
  private Canteen model;
  
  private JLabel idleCook;
  private JLabel workingCook;
  private JLabel cashier;
  private JLabel clientQueue;
  private JLabel ingredients;
  private JLabel kotlet;
  private JLabel wolowina;
  private JLabel ziemniaki;
  private JLabel ryz;
  private JLabel rosol;
  private JLabel pomidorowa;
  private JLabel kompot;
  private LinkedList<JLabel> tables;
  
  private JLabel speedLabel;
  private JSlider speedSlider;
  
  private BufferedImage diskImage;

  AnimPanel(MyFrame _frame)
  {
	frame = _frame;
	model = frame.getCanteen();
	frame.getCanteen().addPropertyChangeListener(this);
	frame.getCanteen().setAnimPanel(this);
	setLayout(null);  
	tables = new LinkedList<JLabel>();
	
	//Utworzenie nowego pliku na dysku
    File imageFile = new File("gui.png");
    //wczytanie obrazu z pliku
    try {
		diskImage = ImageIO.read(imageFile);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	repaint();
  }
  
  public void paintComponent(Graphics g)
  {
	
      g.drawImage(diskImage, 0, 0, this);
  }
  
  public void drawPanel()
  {
	speedLabel = new JLabel();
	speedLabel.setBounds(300, 0, 100, 20);
	add(speedLabel);
	
	speedSlider = new JSlider(1, 100, 50);
	speedSlider.setBounds(300, 20, 200, 30);
	add(speedSlider);
	speedSlider.addChangeListener(new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			//System.out.println("ZMIENILEM WARTOSC");
			speedLabel.setText("Predkosc - " + speedSlider.getValue());
			model.setDelay(speedSlider.getValue());
		}
	});
	speedLabel.setText("Predkosc - " + speedSlider.getValue());
	
	
	
    idleCook = new JLabel("Wolni kucharze: " + model.getCookCount());
	idleCook.setBounds(100, 50, 140, 20);
	add(idleCook);
	    
	workingCook = new JLabel("Pracuj�cy kucharze: 0");
	workingCook.setBounds(100, 80, 150, 20);
	add(workingCook);
	    
	cashier = new JLabel("Kasjerzy: " + model.getCashierCount());
	cashier.setBounds(350, 150, 120, 20);
	add(cashier);
	    
	clientQueue = new JLabel("Kolejka klient�w: 0");
	clientQueue.setBounds(310, 180, 200, 20);
	add(clientQueue);
	    
	ingredients = new JLabel("Skladniki:");
	ingredients.setBounds(550, 10, 100, 20);
	add(ingredients);
	   
	kotlet = new JLabel("Kotlety: 5");
	kotlet.setBounds(560, 30, 100, 20);
	add(kotlet);
	    
	wolowina = new JLabel("Wolowina: 5");
	wolowina.setBounds(560, 50, 100, 20);
	add(wolowina);    
	    
	ziemniaki = new JLabel("Ziemniaki: 5");
	ziemniaki.setBounds(560, 70, 100, 20);
	add(ziemniaki);    
	    
	ryz = new JLabel("Ryz: 5");
	ryz.setBounds(560, 90, 100, 20);
	add(ryz);    
	    
	rosol = new JLabel("Rosol: 5");
	rosol.setBounds(560, 110, 100, 20);
	add(rosol);    
	    
	pomidorowa = new JLabel("Pomidorowa: 5");
	pomidorowa.setBounds(560, 130, 100, 20);
	add(pomidorowa);
	    
	kompot = new JLabel("Kompot: 500");
	kompot.setBounds(560, 150, 100, 20);
	add(kompot);
	    
	System.out.println("Model tables " + (model.getTable4Count()+model.getTable4Count()));
	    
	for (int i=0;i<model.getTables().size();i++)
	{
	  JLabel label = new JLabel("0/" + model.getTables().get(i).getSeatCunt());
	  tables.add(label);
	  label.setBounds(100+i*30, 400+20*(i/10), 100, 25);
	  add(label);
	}
	/*
	for (int i=0;i<model.getTable2Count();i++)
	{
	  JLabel label = new JLabel("0/2");
	  tables.add(label);
	  label.setBounds(100+i*30, 400, 100, 25);
	  add(label);
	}
	for (int i=0;i<model.getTable4Count();i++)
	{
	  JLabel label = new JLabel("0/4");
	  tables.add(label);
	  label.setBounds(100+i*30, 400, 100, 25);
	  add(label);
	}
	*/
  }

  @Override
  public void propertyChange(PropertyChangeEvent event)
  {
    if (event.getPropertyName().equals("clientQueue"))
	  clientQueue.setText("Kolejka klient�w: " + model.getClientQueue().size());
    if (event.getPropertyName().equals("cookIdleQueue"))
    {
	  idleCook.setText("Wolni kucharze: " + model.getCookIdleQueue().size());
      workingCook.setText("Pracujacy kucharze: " + (model.getCookCount()-model.getCookIdleQueue().size()));
    }
    if (event.getPropertyName().equals("storage"))
    {	
    	
    	kotlet.setText("Kotlety: " + model.getDishesStorage().getValue("kotlet"));
    	wolowina.setText("Wolowina: " + model.getDishesStorage().getValue("wolownia"));
    	ziemniaki.setText("Ziemniaki: " + model.getDishesStorage().getValue("ziemniaki"));
    	ryz.setText("Ryz: " + model.getDishesStorage().getValue("ryz"));
    	rosol.setText("Rosol: " + model.getDishesStorage().getValue("rosoł"));
    	pomidorowa.setText("Pomidorowa: " + model.getDishesStorage().getValue("pomidorowa"));
    	kompot.setText("Kompot: " + model.getDishesStorage().getValue("kompot"));
    }
    
    if (event.getPropertyName().equals("cashiers"))
      cashier.setText("Kasjerzy: " + model.getCashiers().size());
    
    if (event.getPropertyName().equals("cookCount"))
      System.out.println("COOK COUNT" + event.getNewValue());
    /*
    if (event.getPropertyName().equals("Storage kompot"))
        System.out.println("KUMPOT" + event.getNewValue());
        */
    if (event.getPropertyName().equals("table"))
    {
      for (int i=0;i<tables.size();i++)
        tables.get(i).setText(model.getTables().get(i).getClientCount() + "/" + model.getTables().get(i).getSeatCunt());
    }
  }

}
