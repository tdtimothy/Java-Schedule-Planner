package planner;

import java.awt.Color;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
public class TaskView extends JPanel
{
	//storage for task name
	private JTextField projName;
	//storage for description
	private JTextArea desc;
	//ProjectModel that stores changes and is returned into MainScreen for changes.
	private ProjectModel project;
	//Constructor method and both the controller and viewer of the class, though not all of the controls, 
	//this is the creating a new task version.
	public TaskView(ProjectModel proj)
	{
		project = proj;
		this.setPreferredSize(new Dimension(400, 400));
		JLabel projectMode = new JLabel("Create new Task");
		this.setLayout(new GridBagLayout());
        placeComp(projectMode, this, 1, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 40);
        JLabel name = new JLabel("Name: ");
        placeComp(name, this, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        projName = new JTextField(20);
        placeComp(projName, this, 1, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        JLabel description = new JLabel("Description: ");
        placeComp(description, this, 0, 2, 1, 1, 0, 0, 0, 0, 0, 45, 10, 0, 50);
        desc = new JTextArea(3, 20);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBorder(new JTextField().getBorder());
        placeComp(desc, this, 1, 2, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        JLabel status = new JLabel("Status: ");
        placeComp(status, this, 0, 3, 1, 1, 0, 0, 0, 0, 0, 20, 0, 0, 0);
        String statusStr[] = proj.getColumns().toArray(new String[proj.getColumns().size()]);
        JComboBox<String> statusBox = new JComboBox<String>(statusStr);
        placeComp(statusBox, this, 1, 3, 2, 1, 0, 0, 0, 0, 0, 20, 20, 0, 0);
        
        String yrStr[] = new String[101];
        for(int i = 0; i < 101; i++)
        {
        	yrStr[i] = Integer.toString(i+2000);
        }
        String febStr[] = new String[28];
        for(int i = 0; i < 28; i++)
        {
        	if(i < 9)
        	{
        		febStr[i] = "0" + Integer.toString(i+1);
        	}
        	else
        	{
        		febStr[i] = Integer.toString(i+1);
        	}
        }
        DefaultComboBoxModel<String> febMod = new DefaultComboBoxModel<String>(febStr);
        String t0Str[] = new String[30];
        for(int i = 0; i < 30; i++)
        {
        	if(i < 9)
        	{
        		t0Str[i] = "0" + Integer.toString(i+1);
        	}
        	else
        	{
        		t0Str[i] = Integer.toString(i+1);
        	}
        }
        DefaultComboBoxModel<String> t0Mod = new DefaultComboBoxModel<String>(t0Str);
        String t1Str[] = new String[31];
        for(int i = 0; i < 31; i++)
        {
        	if(i < 9)
        	{
        		t1Str[i] = "0" + Integer.toString(i+1);
        	}
        	else
        	{
        		t1Str[i] = Integer.toString(i+1);
        	}
        }
        DefaultComboBoxModel<String> t1Mod = new DefaultComboBoxModel<String>(t1Str);
        JComboBox<String> dayBox = new JComboBox<String>(t1Mod);
        String monthStr[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        JComboBox<String> monthBox = new JComboBox<String>(monthStr);
        JComboBox<String> yrBox = new JComboBox<String>(yrStr);
        monthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(monthBox.getSelectedItem().equals("01") || monthBox.getSelectedItem().equals("03") || monthBox.getSelectedItem().equals("05") ||
						monthBox.getSelectedItem().equals("07") || monthBox.getSelectedItem().equals("08") || 
						monthBox.getSelectedItem().equals("10") || monthBox.getSelectedItem().equals("12"))
				{
					dayBox.setModel(t1Mod);
				}
				else if (monthBox.getSelectedItem().equals("02"))
				{
					dayBox.setModel(febMod);
				}
				else
				{
					dayBox.setModel(t0Mod);
				}
			}
        });
        
        JLabel dueDate = new JLabel("Due Date: ");
        placeComp(dueDate, this, 0, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(monthBox, this, 1, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(dayBox, this, 2, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(yrBox, this, 3, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        JPanel colorChooser = new JPanel(new GridBagLayout());
        Integer rgbStr[] = new Integer[256];
        for(int i = 0; i < 256; i++)
        {
        	rgbStr[i] = i;
        }
        JComboBox<Integer> redBox = new JComboBox<Integer>(rgbStr);
        JComboBox<Integer> greenBox = new JComboBox<Integer>(rgbStr);
        JComboBox<Integer> blueBox = new JComboBox<Integer>(rgbStr);
        JLabel color = new JLabel("Color:");
        JLabel colorDes = new JLabel("R             G            B");
        JPanel colorEx = new JPanel();
        colorEx.setBorder(new BevelBorder(BevelBorder.RAISED));
        colorEx.setBackground(new Color(0, 0, 0));
        redBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				colorEx.setBackground(new Color((int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
			}
        });
        greenBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				colorEx.setBackground(new Color((int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
			}
        });
        blueBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				colorEx.setBackground(new Color((int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
			}
        });
        placeComp(redBox, colorChooser, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(greenBox, colorChooser, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(blueBox, colorChooser, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(colorEx, colorChooser, 4, 1, 1, 1, 0, 0, 0, 0, 10, 0, 0, 10, 10);
        placeComp(color, colorChooser, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 15, 0, 0);
        placeComp(colorDes, colorChooser, 0, 0, 5, 1, 0, 0, 0, 0, 0, 0, 10, 0, 0);
        
        placeComp(colorChooser, this, 0, 5, 5, 1, 0, 0, 0, 15, 0, 0, 20, 0, 0);
        JButton quit = new JButton("Cancel");
        quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				Container c = TaskView.this.getParent();
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
				project.addTasks(new TaskModel(projName.getText(), desc.getText(), ((String)dayBox.getSelectedItem() + 
						(String)monthBox.getSelectedItem() + (String)yrBox.getSelectedItem()), (String) statusBox.getSelectedItem(), 
						(int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
				project.setDirty(true);
				Container c = TaskView.this.getParent();
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
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
        confirmPanel.add(Box.createRigidArea(new Dimension(130,0)));
        confirmPanel.add(quit);
        confirmPanel.add(Box.createRigidArea(new Dimension(10,0)));
        confirmPanel.add(ok);
        placeComp(confirmPanel, this, 0, 6, 5, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0);
	}
	
	//Constructor method and both the controller and viewer of the class, though not all of the controls, 
	//this is the edit a task version.
	public TaskView(ProjectModel proj, TaskModel taskM)
	{
		project = proj;
		this.setPreferredSize(new Dimension(400, 400));
		JLabel projectMode = new JLabel("Edit Task");
		this.setLayout(new GridBagLayout());
        placeComp(projectMode, this, 1, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 40);
        JLabel name = new JLabel("Name: ");
        placeComp(name, this, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        projName = new JTextField(taskM.getName(), 20);
        placeComp(projName, this, 1, 1, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        JLabel description = new JLabel("Description: ");
        placeComp(description, this, 0, 2, 1, 1, 0, 0, 0, 0, 0, 45, 10, 0, 50);
        desc = new JTextArea(taskM.getDescription(), 3, 20);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBorder(new JTextField().getBorder());
        placeComp(desc, this, 1, 2, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        JLabel status = new JLabel("Status: ");
        placeComp(status, this, 0, 3, 1, 1, 0, 0, 0, 0, 0, 20, 0, 0, 0);
        String statusStr[] = proj.getColumns().toArray(new String[proj.getColumns().size()]);
        JComboBox<String> statusBox = new JComboBox<String>(statusStr);
        placeComp(statusBox, this, 1, 3, 2, 1, 0, 0, 0, 0, 0, 20, 20, 0, 0);
        statusBox.setSelectedItem(taskM.getStatus());
        
        String yrStr[] = new String[101];
        for(int i = 0; i < 101; i++)
        {
        	yrStr[i] = Integer.toString(i+2000);
        }
        String febStr[] = new String[28];
        for(int i = 0; i < 28; i++)
        {
        	if(i < 9)
        	{
        		febStr[i] = "0" + Integer.toString(i+1);
        	}
        	else
        	{
        		febStr[i] = Integer.toString(i+1);
        	}
        }
        DefaultComboBoxModel<String> febMod = new DefaultComboBoxModel<String>(febStr);
        String t0Str[] = new String[30];
        for(int i = 0; i < 30; i++)
        {
        	if(i < 9)
        	{
        		t0Str[i] = "0" + Integer.toString(i+1);
        	}
        	else
        	{
        		t0Str[i] = Integer.toString(i+1);
        	}
        }
        DefaultComboBoxModel<String> t0Mod = new DefaultComboBoxModel<String>(t0Str);
        String t1Str[] = new String[31];
        for(int i = 0; i < 31; i++)
        {
        	if(i < 9)
        	{
        		t1Str[i] = "0" + Integer.toString(i+1);
        	}
        	else
        	{
        		t1Str[i] = Integer.toString(i+1);
        	}
        }
        DefaultComboBoxModel<String> t1Mod = new DefaultComboBoxModel<String>(t1Str);
        JComboBox<String> dayBox = new JComboBox<String>(t1Mod);
        String monthStr[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        JComboBox<String> monthBox = new JComboBox<String>(monthStr);
        JComboBox<String> yrBox = new JComboBox<String>(yrStr);
        monthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(monthBox.getSelectedItem().equals("01") || monthBox.getSelectedItem().equals("03") || monthBox.getSelectedItem().equals("05") ||
						monthBox.getSelectedItem().equals("07") || monthBox.getSelectedItem().equals("08") || 
						monthBox.getSelectedItem().equals("10") || monthBox.getSelectedItem().equals("12"))
				{
					dayBox.setModel(t1Mod);
				}
				else if (monthBox.getSelectedItem().equals("02"))
				{
					dayBox.setModel(febMod);
				}
				else
				{
					dayBox.setModel(t0Mod);
				}
			}
        });
        if(!taskM.getDueDate().equals(""))
        {
        	monthBox.setSelectedItem(taskM.getDueDate().substring(2, 4));
	        dayBox.setSelectedItem(taskM.getDueDate().substring(0, 2));
	        yrBox.setSelectedItem(taskM.getDueDate().substring(4));
        }
        JLabel dueDate = new JLabel("Due Date: ");
        placeComp(dueDate, this, 0, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(monthBox, this, 1, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(dayBox, this, 2, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(yrBox, this, 3, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        JPanel colorChooser = new JPanel(new GridBagLayout());
        Integer rgbStr[] = new Integer[256];
        for(int i = 0; i < 256; i++)
        {
        	rgbStr[i] = i;
        }
        JComboBox<Integer> redBox = new JComboBox<Integer>(rgbStr);
        JComboBox<Integer> greenBox = new JComboBox<Integer>(rgbStr);
        JComboBox<Integer> blueBox = new JComboBox<Integer>(rgbStr);
        JLabel color = new JLabel("Color:");
        JLabel colorDes = new JLabel("R             G            B");
        JPanel colorEx = new JPanel();
        colorEx.setBorder(new BevelBorder(BevelBorder.RAISED));
        colorEx.setBackground(new Color(0, 0, 0));
        redBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				colorEx.setBackground(new Color((int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
			}
        });
        greenBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				colorEx.setBackground(new Color((int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
			}
        });
        blueBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				colorEx.setBackground(new Color((int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
			}
        });
        placeComp(redBox, colorChooser, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(greenBox, colorChooser, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(blueBox, colorChooser, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        placeComp(colorEx, colorChooser, 4, 1, 1, 1, 0, 0, 0, 0, 10, 0, 0, 10, 10);
        placeComp(color, colorChooser, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 15, 0, 0);
        placeComp(colorDes, colorChooser, 0, 0, 5, 1, 0, 0, 0, 0, 0, 0, 10, 0, 0);
        
        placeComp(colorChooser, this, 0, 5, 5, 1, 0, 0, 0, 15, 0, 0, 20, 0, 0);
        int colors[] = taskM.getRgbColor();
        redBox.setSelectedItem(colors[0]);
        greenBox.setSelectedItem(colors[1]);
        blueBox.setSelectedItem(colors[2]);
        
        JButton quit = new JButton("Cancel");
        quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				Container c = TaskView.this.getParent();
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
				project.removeTasks(taskM);
				project.addTasks(new TaskModel(projName.getText(), desc.getText(), ((String)dayBox.getSelectedItem() + 
						(String)monthBox.getSelectedItem() + (String)yrBox.getSelectedItem()), (String) statusBox.getSelectedItem(), 
						(int)redBox.getSelectedItem(), (int)greenBox.getSelectedItem(), (int)blueBox.getSelectedItem()));
				project.setDirty(true);
				Container c = TaskView.this.getParent();
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
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
        confirmPanel.add(Box.createRigidArea(new Dimension(130,0)));
        confirmPanel.add(quit);
        confirmPanel.add(Box.createRigidArea(new Dimension(10,0)));
        confirmPanel.add(ok);
        placeComp(confirmPanel, this, 0, 6, 5, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0);
	}
	//getter method
	public ProjectModel getProject() {
		return project;
	}
	//setter method
	public void setProject(ProjectModel project) {
		this.project = project;
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
