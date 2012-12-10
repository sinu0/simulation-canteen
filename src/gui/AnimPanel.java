package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

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

  AnimPanel(MyFrame _frame)
  {
	frame = _frame;
	model = frame.getCanteen();
	frame.getCanteen().addPropertyChangeListener(this);
	frame.getCanteen().setAnimPanel(this);
	setLayout(null);
	
    idleCook = new JLabel("Wolni kucharze ");
    idleCook.setBounds(100, 50, 100, 20);
    add(idleCook);
    
    workingCook = new JLabel("Pracuj¹cy kucharze");
    workingCook.setBounds(100, 80, 150, 20);
    add(workingCook);
    
    cashier = new JLabel("Kasjerzy: ");
    cashier.setBounds(350, 150, 120, 20);
    add(cashier);
    
    if (model.getClientQueue()==null)
      System.out.println("NULL");
    clientQueue = new JLabel("Kolejka klientów: ");
    clientQueue.setBounds(310, 180, 200, 20);
    add(clientQueue);
    
    ingredients = new JLabel("Skladniki:");
    ingredients.setBounds(550, 10, 100, 20);
    add(ingredients);
    
    kotlet = new JLabel("Kotlety");
    kotlet.setBounds(560, 30, 100, 20);
    add(kotlet);
    
    wolowina = new JLabel("Wolowina");
    wolowina.setBounds(560, 50, 100, 20);
    add(wolowina);    
    
    ziemniaki = new JLabel("Ziemniaki");
    ziemniaki.setBounds(560, 70, 100, 20);
    add(ziemniaki);    
    
    ryz = new JLabel("Ryz");
    ryz.setBounds(560, 90, 100, 20);
    add(ryz);    
    
    rosol = new JLabel("Rosol");
    rosol.setBounds(560, 110, 100, 20);
    add(rosol);    
    
    pomidorowa = new JLabel("Pomidorowa");
    pomidorowa.setBounds(560, 130, 100, 20);
    add(pomidorowa);
    
    kompot = new JLabel("Kompot");
    kompot.setBounds(560, 150, 100, 20);
    add(kompot);
    
  }

  @Override
  public void propertyChange(PropertyChangeEvent event)
  {
    if (event.getPropertyName().equals("cookCount"))
      System.out.println("COOK COUNT" + event.getNewValue());
    if (event.getPropertyName().equals("clientQueue"))
        clientQueue.setText("Kolejka klientów: " + model.getClientQueue().size());
    
    if (event.getPropertyName().equals("Storage kompot"))
        System.out.println("KUMPOT" + event.getNewValue());
  }

}
