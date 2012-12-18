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
  


  MyFrame()
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
    
    add(new JScrollPane(startPanel));
    revalidate();
  }

  public StartPanel getStartPanel() {
	return startPanel;
  }

  public AnimPanel getAnimPanel() {
	return animPanel;
  }

  public StatPanel getStatPanel() {
	return statPanel;
  }

  public Canteen getCanteen() {
	return canteen;
  }

}
