package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatPanel extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  private MyFrame frame;
  
  StatPanel(MyFrame _frame)
  {
    frame = _frame;
    add(new JLabel("Statystyki animacji"));
  }
}
