package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import canteen.Canteen;

public class MyFrame extends JFrame
{
  private static final long serialVersionUID = 1L;
  
  //panele
  private StartPanel startPanel;
  private AnimPanel animPanel;
  private StatPanel statPanel;
  
  private Canteen canteen;
  


  /**
   * Konstruktor
   */
  public MyFrame()
  {
	canteen = new Canteen(null, "Stolowka", false, false);
	
	
	//inicjalizacja paneli
    startPanel = new StartPanel(this);
    animPanel = new AnimPanel(this);
    statPanel = new StatPanel(this);
    
    
    //inicjalizacja wygladu ramki
    setTitle("Symulacja sto³ówki");
    setSize(800, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    setResizable(false);
    setLocation(350, 5);
    
    add(new JScrollPane(startPanel));
    revalidate();
  }

  
  /**
   * Zwraca panel z parametrami pocz¹tkowymi
   * @return Panel z parametrami pocz¹tkowymi
   */
  public StartPanel getStartPanel() {
	return startPanel;
  }

  /**
   * Zwraca panel z animacj¹ symulacji
   * @return Panel z animacj¹ symulacji
   */
  public AnimPanel getAnimPanel() {
	return animPanel;
  }

  /**
   * Zwraca panel z statystykami symulacji
   * @return Panel z statystykami symulacji
   */
  public StatPanel getStatPanel() {
	return statPanel;
  }

  /**
   * Zwraca model
   * @return Model
   */
  public Canteen getCanteen() {
	return canteen;
  }

}
