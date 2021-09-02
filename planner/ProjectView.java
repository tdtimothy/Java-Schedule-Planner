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
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ProjectView extends JPanel
{
	//storage for input project name
	private JTextField projName;
	//arraylist to hold columns to make display easier
	private ArrayList<JTextField> columns;
	//arraylist to hold subtract buttons to make display easier
	private ArrayList<JButton> subs;
	//ProjectModel that stores changes and is returned into MainScreen for changes.
	private ProjectModel project;
	//boolean to determine if yes or no was pressed, used to make MainScreen controls easier
	boolean create = false;
	
	//Constructor method and both the controller and viewer of the class, though not all of the controls, 
	//this is the creating a new project version.
	public ProjectView()
	{
		this.setPreferredSize(new Dimension(500, 400));;
		JLabel projectMode = new JLabel("Create new Project");
		this.setLayout(new GridBagLayout());
		JPanel init = new JPanel(new GridBagLayout());
		this.add(init);
        placeComp(projectMode, init, 2, 0, 2, 1, 0, 0, 0, 0, 0, 30, 0, 0, 0);
        JPanel columnPanel = new JPanel(new GridBagLayout());
        JLabel name = new JLabel("Name: ");
        placeComp(name, init, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        projName = new JTextField(20);
        placeComp(projName, init, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        ActionListener remove = new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                for(int i = 0; i < subs.size(); i++)
                {
                	if( ((JButton)e.getSource()) == subs.get(i))
                	{
                		columnPanel.remove(subs.get(i));
        				columnPanel.remove(columns.get(i));
        				columnPanel.revalidate();
        				columnPanel.repaint();
                	}
                }
            }
        };
        JLabel column = new JLabel("Columns");
        placeComp(column, init, 0, 2, 1, 1, 0, 0, 0, 30, 10, 0, 0, 0, 0);
        
        JButton add = new JButton("+");
        add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				columns.add(new JTextField(15));
				subs.add(new JButton("-"));
				subs.get(subs.size()-1).addActionListener(remove);
				placeComp(subs.get(subs.size()-1), columnPanel, 1, (subs.size()-1), 1, 1, 0, 0, 0, 15, 20, 0, 0, 0, 0);
				placeComp(columns.get(columns.size()-1), columnPanel, 0, (columns.size()-1), 1, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0);
				columnPanel.revalidate();
				columnPanel.repaint();
			}
		});
        placeComp(add, init, 2, 2, 1, 1, 0, 0, 0, 30, 150, 0, 0, 0, 0);
        
        JScrollPane scrollPane = new JScrollPane(columnPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        placeComp(scrollPane, this, 0, 3, 2, 2, 0, 0, 0, 20, 10, 0, 0, 50, 0);
        
        scrollPane.setViewportView(columnPanel);
        scrollPane.setPreferredSize(new Dimension(280, 140));
        JTextField column1 = new JTextField(15);
        placeComp(column1, columnPanel, 0, 0, 1, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0);
        columns = new ArrayList<JTextField>();
        columns.add(column1);
        
        JButton sub = new JButton("-");	
        placeComp(sub, columnPanel, 1, 0, 1, 1, 0, 0, 0, 15, 20, 0, 0, 0, 0);
        sub.addActionListener(remove);
        subs = new ArrayList<JButton>();
        subs.add(sub);
        
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
        JButton quit = new JButton("Cancel");
        quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container c = ProjectView.this.getParent();
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
				ArrayList<String> array = new ArrayList<String>();
				for(JTextField tf: columns)
				{
					if(!tf.getText().equals(""))
					{
						array.add(tf.getText());
					}
				}
				project = new ProjectModel(projName.getText(), array);
				create = true;
				project.setDirty(true);
				Container c = ProjectView.this.getParent();
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
        confirmPanel.add(Box.createRigidArea(new Dimension(130,0)));
        confirmPanel.add(quit);
        confirmPanel.add(Box.createRigidArea(new Dimension(10,0)));
        confirmPanel.add(ok);
        placeComp(confirmPanel, this, 0, 5, 1, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0);
	}
	
	//Constructor method and both the controller and viewer of the class, though not all of the controls, 
	//this is the editing an existing project version.
	public ProjectView(ProjectModel proj)
	{
		project = proj;
		this.setPreferredSize(new Dimension(500, 450));;
		JLabel projectMode = new JLabel("Edit Project");
		this.setLayout(new GridBagLayout());
		JPanel init = new JPanel(new GridBagLayout());
		this.add(init);
        placeComp(projectMode, init, 2, 0, 2, 1, 0, 0, 0, 0, 0, 30, 0, 0, 0);
        JPanel columnPanel = new JPanel(new GridBagLayout());
        JLabel name = new JLabel("Name: ");
        placeComp(name, init, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        projName = new JTextField(project.getName(), 20);
        placeComp(projName, init, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        ActionListener remove = new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                for(int i = 0; i < subs.size(); i++)
                {
                	if( ((JButton)e.getSource()) == subs.get(i))
                	{
                		columnPanel.remove(subs.get(i));
        				columnPanel.remove(columns.get(i));
        				columnPanel.revalidate();
        				columnPanel.repaint();
        				columns.remove(i);
        				subs.remove(i);
                	}
                }
            }
        };
        JLabel column = new JLabel("Columns");
        placeComp(column, init, 0, 2, 1, 1, 0, 0, 0, 30, 10, 0, 0, 0, 0);
        
        JButton add = new JButton("+");
        add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				columns.add(new JTextField(15));
				subs.add(new JButton("-"));
				subs.get(subs.size()-1).addActionListener(remove);
				placeComp(subs.get(subs.size()-1), columnPanel, 1, (subs.size()-1), 1, 1, 0, 0, 0, 15, 20, 0, 0, 0, 0);
				placeComp(columns.get(columns.size()-1), columnPanel, 0, (columns.size()-1), 1, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0);
				columnPanel.revalidate();
				columnPanel.repaint();
			}
		});
        placeComp(add, init, 2, 2, 1, 1, 0, 0, 0, 30, 150, 0, 0, 0, 0);
        
        JScrollPane scrollPane = new JScrollPane(columnPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        placeComp(scrollPane, this, 0, 3, 2, 2, 0, 0, 0, 20, 10, 0, 0, 50, 0);
        
        scrollPane.setViewportView(columnPanel);
        scrollPane.getViewport().setPreferredSize(new Dimension(280, 140));
        ArrayList<String> tempCol = proj.getColumns();
        columns = new ArrayList<JTextField>();
        subs = new ArrayList<JButton>();
        scrollPane.setViewportView(columnPanel);
        
        for(int i = 0; i < proj.getColumns().size(); i++)
        {
        	columns.add(new JTextField(tempCol.get(i), 15));
			subs.add(new JButton("-"));
			subs.get(subs.size()-1).addActionListener(remove);
			placeComp(subs.get(subs.size()-1), columnPanel, 1, (subs.size()-1), 1, 1, 0, 0, 0, 15, 20, 0, 0, 0, 0);
			placeComp(columns.get(columns.size()-1), columnPanel, 0, (columns.size()-1), 1, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0);
        }
        
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
        JButton quit = new JButton("Cancel");
        quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container c = ProjectView.this.getParent();
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
				ArrayList<String> array = new ArrayList<String>();
				for(JTextField tf: columns)
				{
					if(!tf.getText().equals(""))
					{
						array.add(tf.getText());
					}
				}
				project.setDirty(true);
				ArrayList<TaskModel> tasks = project.getTasks();
				ArrayList<String> oldColumns = project.getColumns();
				int taskSize = tasks.size();
				int index = 0;
				boolean rename = false;
				for(int i = 0; i < taskSize; i++)
				{
					for(int j = 0; j < array.size(); j++)
					{
						if(tasks.get(i).getStatus().equals(array.get(j)))
						{
							break;
						}
						else if(j < oldColumns.size())
						{
							if(tasks.get(i).getStatus().equals(oldColumns.get(j)))
							{
								index = j;
								rename = true;
							}
						}
						if(j == array.size()-1)
						{
							if(rename)
							{
								tasks.get(i).setStatus(array.get(index));
								rename = false;
							}
						}
					}
				}
				project = new ProjectModel(projName.getText(), array);
				project.setTasks(tasks);
				Container c = ProjectView.this.getParent();
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
        confirmPanel.add(Box.createRigidArea(new Dimension(130,0)));
        confirmPanel.add(quit);
        confirmPanel.add(Box.createRigidArea(new Dimension(10,0)));
        confirmPanel.add(ok);
        placeComp(confirmPanel, this, 0, 5, 1, 1, 0, 0, 0, 20, 0, 0, 0, 0, 0); 
        this.repaint();
        this.revalidate();
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
	//getter method
	public ProjectModel getProject()
	{
		return project;
	}		
}
