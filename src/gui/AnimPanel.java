package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimPanel extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  private MyFrame frame;

  AnimPanel(MyFrame _frame)
  {
	frame = _frame;
    add(new JLabel("Dajesz malina!"));
  }

}
