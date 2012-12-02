package gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MyFrame extends JFrame
{
  private static final long serialVersionUID = 1L;
  
  //panele
  private StartPanel startPanel;
  private AnimPanel animPanel;
  private StatPanel statPanel;
  
  MyFrame()
  {
	//inicjalizacja paneli
    startPanel = new StartPanel(this);
    animPanel = new AnimPanel(this);
    statPanel = new StatPanel(this);
    
    //inicjalizacja wygladu ramki
    setTitle("Symulacja sto³ówki");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    
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

}
