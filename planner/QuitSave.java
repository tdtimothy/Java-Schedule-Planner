package planner;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuitSave extends JPanel
{
	//determines to save or not
	private boolean status;
	//constructor and controller and view of save exit prompt 
	public QuitSave()
	{
		JButton quit = new JButton("Quit");
	    quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				status = false;
				Container c = QuitSave.this.getParent();
		        while (c.getParent()!=null) 
		        {
		            c = c.getParent();
		        }
		        if (c instanceof Window) 
		        {
		        	c.setVisible(false);
		        	((Window) c).dispose();
		        }
			}
		});
	    
	    
	    JButton ok = new JButton("    Ok    ");	
	    ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = true;
				Container c = QuitSave.this.getParent();
		        while (c.getParent()!=null) 
		        {
		            c = c.getParent();
		        }
		        if (c instanceof Window) 
		        {
		        	c.setVisible(false);
		        	((Window) c).dispose();
		        }
			}
		});
	    JPanel confirmPanel = new JPanel();
	    this.setLayout(new GridBagLayout());
	    confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
	    confirmPanel.add(Box.createRigidArea(new Dimension(130,0)));
	    confirmPanel.add(quit);
	    confirmPanel.add(Box.createRigidArea(new Dimension(10,0)));
	    confirmPanel.add(ok);
	    placeComp(new JLabel("Are you sure? There are unsaved changes."), this, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	    placeComp(confirmPanel, this, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	//getter method
	public boolean isStatus() {
		return status;
	}
	//setter method
	public void setStatus(boolean status) {
		this.status = status;
	}
	//method used to make GridBagLayout positioning easier
	public static void placeComp(Component cp, JPanel jp, int gx, int gy, int gw, int gh, double wx, double wy, int an, int it, int il, int ib, int ir, int xpad, int ypad)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gx;
		c.gridy = gy;
		c.gridwidth = gw;
		c.gridheight = gh;
		c.weightx = wx;
		c.weighty = wy;
		c.anchor = an;
		if(an == 0)
		{
			c.anchor = GridBagConstraints.CENTER;
		}
		c.insets = new Insets(it, il, ib, ir);
		c.ipadx = xpad;
		c.ipady = ypad;
		jp.add(cp, c);
	}
}