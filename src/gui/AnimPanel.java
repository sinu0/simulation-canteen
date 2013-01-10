package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
  private JLabel clientPlaceQueue;
  private LinkedList<JLabel> tables;
  
  private JLabel speedLabel;
  private JSlider speedSlider;
  
  private Image diskImage;

  AnimPanel(MyFrame _frame)
  {
	frame = _frame;
	model = frame.getCanteen();
	frame.getCanteen().addPropertyChangeListener(this);
	frame.getCanteen().setAnimPanel(this);
	setLayout(null);  
	tables = new LinkedList<JLabel>();
	
	
    File imageFile = new File("gui.png");
    try
    {
	  diskImage = ImageIO.read(imageFile);
	}
    catch (IOException e) {}
    
	/*
	try 
	{
	  diskImage = ImageIO.read(getClass().getResourceAsStream("/gui/gui.png"));
	}
	catch (IOException e) {}
	*/
    
	repaint();
  }
  
  /**
   * Rysuje odpowiednia ilosc sto³ów
   * @param g Kontekst graficzny
   */
  public void paintComponent(Graphics g)
  {
    g.drawImage(diskImage, 0, 0, this);
    int j=0;
    for (int i=0;i<model.getTable2Count();i++)
    {
      int k=j/5;
        g.drawRect(100+(j-5*k)*130, 270+k*100, 80, 80);
      j++;
    }
    for (int i=0;i<model.getTable4Count();i++)
    {
    	int k=j/5;
        g.drawRect(100+(j-5*k)*130, 270+k*100, 80, 80);
      j++;
    }
  }
  
  /**
   * Dodaje teksty
   */
  public void drawPanel()
  {
	speedLabel = new JLabel();
	speedLabel.setBounds(650, 25, 100, 20);
	add(speedLabel);
	
	final int max = 101;
	speedSlider = new JSlider(1, max - 1, (max+max/4)/2);
	speedSlider.setBounds(580, 0, 200, 30);
	add(speedSlider);
	speedSlider.addChangeListener(new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			speedLabel.setText("Prêdkoœæ - " + speedSlider.getValue());
			model.setDelay(max - speedSlider.getValue());
		}
	});
	speedLabel.setText("Prêdkoœæ - " + speedSlider.getValue());
	
	
	
    idleCook = new JLabel("Wolni kucharze: " + model.getCookCount());
	idleCook.setBounds(50, 40, 200, 20);
	add(idleCook);
	    
	workingCook = new JLabel("Pracuj¹cy kucharze: 0");
	workingCook.setBounds(50, 60, 300, 20);
	add(workingCook);
	    
	cashier = new JLabel("Kasjerki: " + model.getCashierCount());
	cashier.setBounds(500, 100, 120, 20);
	add(cashier);
	    
	clientQueue = new JLabel("Kolejka klientów: 0");
	clientQueue.setBounds(480, 200, 200, 20);
	add(clientQueue);
	    
	ingredients = new JLabel("Sk³adniki:");
	ingredients.setBounds(270, 0, 100, 20);
	add(ingredients);
	   
	kotlet = new JLabel("Kotlety: 5");
	kotlet.setBounds(280, 15, 100, 20);
	add(kotlet);
	    
	wolowina = new JLabel("Wo³owina: 5");
	wolowina.setBounds(280, 30, 100, 20);
	add(wolowina);    
	    
	ziemniaki = new JLabel("Ziemniaki: 5");
	ziemniaki.setBounds(280, 45, 100, 20);
	add(ziemniaki);    
	    
	ryz = new JLabel("Ry¿: 5");
	ryz.setBounds(280, 60, 100, 20);
	add(ryz);    
	    
	rosol = new JLabel("Rosó³: 5");
	rosol.setBounds(280, 75, 100, 20);
	add(rosol);    
	    
	pomidorowa = new JLabel("Pomidorowa: 5");
	pomidorowa.setBounds(280, 90, 200, 20);
	add(pomidorowa);
	    
	kompot = new JLabel("Kompot: 500");
	kompot.setBounds(280, 105, 100, 20);
	add(kompot);
	
	clientPlaceQueue = new JLabel("Klienci oczekuj¹cy na miejsce przy stoliku: 0");
	clientPlaceQueue.setBounds(100, 200, 300, 20);
	add(clientPlaceQueue);
	    
	
	
	for (int i=0;i<model.getTables().size();i++)
	{
	  JLabel label = new JLabel("0/" + model.getTables().get(i).getSeatCunt());
	  tables.add(label);
	  label.setBounds(131+(i-(i/5)*5)*130, 296+100*(i/5), 100, 25);
	  add(label);
	}
	
  }

  /**
   * Zmienia wyswietlana wartosc, gdy jakas zmienna modelu sie zmieni
   */
  @Override
  public void propertyChange(PropertyChangeEvent event)
  {
    if (event.getPropertyName().equals("clientQueue"))
	  clientQueue.setText("Kolejka klientów: " + model.getClientQueue().size());
    if (event.getPropertyName().equals("cookIdleQueue"))
    {
	  idleCook.setText("Wolni kucharze: " + model.getCookIdleQueue().size());
      workingCook.setText("Pracuj¹cy kucharze: " + (model.getCookCount()-model.getCookIdleQueue().size()));
    }
    if (event.getPropertyName().equals("storage"))
    {	
    	
    	kotlet.setText("Kotlety: " + model.getDishesStorage().getValue("kotlet"));
    	wolowina.setText("Wo³owina: " + model.getDishesStorage().getValue("wo³owina"));
    	ziemniaki.setText("Ziemniaki: " + model.getDishesStorage().getValue("ziemniaki"));
    	ryz.setText("Ry¿: " + model.getDishesStorage().getValue("ry¿"));
    	rosol.setText("Rosó³: " + model.getDishesStorage().getValue("rosó³"));
    	pomidorowa.setText("Pomidorowa: " + model.getDishesStorage().getValue("pomidorowa"));
    	kompot.setText("Kompot: " + model.getDishesStorage().getValue("kompot"));
    }
    
    if (event.getPropertyName().equals("cashiers"))
      cashier.setText("Kasjerzy: " + model.getCashiers().size());
    
    
    if (event.getPropertyName().equals("table"))
    {
      for (int i=0;i<tables.size();i++)
        tables.get(i).setText(model.getTables().get(i).getClientCount() + "/" + model.getTables().get(i).getSeatCunt());
    }
    //  clientNoPlaceQueue
    if (event.getPropertyName().equals("clientNoPlaceQueue"))
      clientPlaceQueue.setText("Klienci oczekuj¹cy na miejsce przy stoliku: " + model.getClientNoPleceQueue().size());
  }

}
