package planner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginStartUp extends JFrame
{
	//determines name to login with
	private String userName;
	//determines password to login with
	private String password;
	
	//constructor of initial view and control to the MainScreen
	public LoginStartUp(String name, String pass)
	{
		userName = name;
		password = pass;
		this.setTitle("Login Screen");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		JPanel logC = new JPanel(new GridBagLayout());
		JLabel title = new JLabel("Task Board Manager");
		
		JLabel userL = new JLabel("Username: ");
		userL.setForeground(Color.RED);
		
		JLabel passL = new JLabel("Password: ");
		passL.setForeground(Color.RED);
		
		JLabel fail1 = new JLabel(" ");
		fail1.setForeground(Color.RED);
		JLabel fail2 = new JLabel(" ");
		fail2.setForeground(Color.RED);
		JPanel failBox = new JPanel();
        failBox.setLayout(new BoxLayout(failBox, BoxLayout.Y_AXIS));
        failBox.add(fail1);
        failBox.add(Box.createRigidArea(new Dimension(0, 10)));
        failBox.add(fail2);
		JTextField un = new JTextField(15);
		JPasswordField pw = new JPasswordField(15);
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(un.getText().equalsIgnoreCase(userName) && String.valueOf(pw.getPassword()).equals(password))
				{
					LoginStartUp.this.setVisible(false);
					JFrame start = new JFrame("Task Board Manager");
					start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					start.add(new MainScreen(new TaskBoardModel(), true));
					start.setVisible(true);
					start.pack();
					LoginStartUp.this.dispose();
				}
				else
				{
					pw.setText("");
					fail1.setText("The account name or password");
					fail2.setText("that you have entered is in correct.");
				}
			}
		});
		
		placeComp(title, logC, 1, 0, 1, 1, 0, 0, 0, 0, 0, 20, 20, 0, 30);
		placeComp(userL, logC, 0, 1, 1, 1, 0, 0, 0, 0, 0, 10, 10, 0, 0);
		placeComp(passL, logC, 0, 2, 1, 1, 0, 0, 0, 0, 0, 0, 10, 0, 0);
		placeComp(un, logC, 1, 1, 2, 1, 0, 0, 0, 0, 0, 10, 0, 0, 0);
		placeComp(pw, logC, 1, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		placeComp(failBox, logC, 0, 3, 3, 1, 0, 0, 0, 15, 0, 0, 0, 0, 15);
		placeComp(login, logC, 1, 4, 1, 1, 0, 0, 0, 0, 0, 0, 20, 0, 0);
		logC.setPreferredSize(new Dimension(500, 250));
		this.add(logC);
		this.setLocationRelativeTo(null);
		this.pack();
		
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
	//what starts off the whole program
	public static void main(String[] args) 
	{
        LoginStartUp start = new LoginStartUp("admin", "admin");
	}
}
