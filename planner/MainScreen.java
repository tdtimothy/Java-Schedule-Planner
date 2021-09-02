package planner;

import java.awt.BorderLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainScreen extends JPanel
{
	//Main model used for data, also the save/load file essentially.
	private TaskBoardModel taskboard;
	//Created so actionListeners can used
	DefaultComboBoxModel<ProjectModel> model;
	//Created so actionListeners can used
	JComboBox<ProjectModel> selecter;
	//Created so actionListeners can used
	JScrollPane cenScroll;
	//determines if first save has been made
	File initSave;
	//determines login or load
	boolean initStart = false;
	//constructor that's the main view and controls of the program
	public MainScreen(TaskBoardModel taskB, boolean initStart)
	{
		this.initStart = initStart;
		taskboard = taskB;
		this.setPreferredSize(new Dimension(1600, 900));
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		JPanel loadPanel = new JPanel(new GridBagLayout());
		JPanel controlPanel = new JPanel(new GridBagLayout());
		controlPanel.setPreferredSize(new Dimension(150, 750));
		loadPanel.setPreferredSize(new Dimension(1600, 100));
		loadPanel.setBorder(new LineBorder(Color.GRAY, 2));
		ProjectModel projects[] = taskboard.getProjects().toArray(new ProjectModel[taskboard.getProjects().size()]);
		model = new DefaultComboBoxModel<ProjectModel>(projects);
		selecter = new JComboBox<ProjectModel>(model);
		JButton createProj = new JButton("Create Project");
		createProj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				ProjectView proj = new ProjectView();
				dialog.setLocationRelativeTo(null);
				dialog.getContentPane().add(proj);
				dialog.pack();
				dialog.setVisible(true);
				if(proj.create)
				{
					taskboard.addProjects(proj.getProject());
					model.addElement(proj.getProject());
					if(initStart)
					{
						cenScroll = new JScrollPane(columnsDraw((ProjectModel) selecter.getSelectedItem()), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						MainScreen.this.add(cenScroll, BorderLayout.CENTER);
						MainScreen.this.add(controlPanel, BorderLayout.WEST);
						MainScreen.this.initStart = false;
					}
				}
			}
		});
		JButton editProj = new JButton("Edit Project");
		editProj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				ProjectView proj = new ProjectView((ProjectModel) selecter.getSelectedItem());
				dialog.setLocationRelativeTo(null);
				dialog.getContentPane().add(proj);
				dialog.pack();
				dialog.setVisible(true);
				int index = selecter.getSelectedIndex();
				taskboard.setProject(index, proj.getProject());
				model.insertElementAt(proj.getProject(), index);
				model.removeElementAt(index + 1);
				update();
			}
		});
		if(!MainScreen.this.initStart)
		{
			cenScroll = new JScrollPane(columnsDraw((ProjectModel) selecter.getSelectedItem()), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.add(cenScroll, BorderLayout.CENTER);
			this.add(controlPanel, BorderLayout.WEST);
		}	
		JButton orderCol = new JButton("Reorder Columns");
		orderCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				ColumnsEdit columns = new ColumnsEdit((ProjectModel) selecter.getSelectedItem());
				dialog.setLocationRelativeTo(null);
				dialog.getContentPane().add(columns);
				dialog.pack();
				dialog.setVisible(true);
				int index = selecter.getSelectedIndex();
				taskboard.setProject(index, columns.getProject());
				model.insertElementAt(columns.getProject(), index);
				model.removeElementAt(index + 1);
				update();
			}
		});
		JButton createTasks = new JButton("Create Task");
		createTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				TaskView taskEd = new TaskView((ProjectModel) selecter.getSelectedItem());
				dialog.setLocationRelativeTo(null);
				dialog.getContentPane().add(taskEd);
				dialog.pack();
				dialog.setVisible(true);
				int index = selecter.getSelectedIndex();
				taskboard.setProject(index, taskEd.getProject());
				model.insertElementAt(taskEd.getProject(), index);
				model.removeElementAt(index + 1);
				update();
			}
		});
		JButton save = new JButton("Save");
		JLabel selectProj = new JLabel("Select Project ");
		selecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!MainScreen.this.initStart)
				{
					update();
					System.out.println("hi");
				}
			}
        });
		
		JButton logOut = new JButton("Logout");
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(ProjectModel pm : taskboard.getProjects())
        		{
        			if(pm.isDirty())
        			{
        				JDialog dialog = new JDialog();
        				dialog.setModal(true);
        				QuitSave qs = new QuitSave();
        				dialog.setLocationRelativeTo(null);
        				dialog.getContentPane().add(qs);
        				dialog.pack();
        				dialog.setVisible(true);
        				if(qs.isStatus())
        				{
        					if(initSave == null)
        					{
        						JFileChooser fileChooser = new JFileChooser();
        					    int returnVal = fileChooser.showSaveDialog(save);
        					    if (returnVal == JFileChooser.APPROVE_OPTION) {
        					      File file = fileChooser.getSelectedFile();
        					      if (file == null) {
        					        return;
        					      }
        					      if (!file.getName().toLowerCase().endsWith(".ser")) {
        					        file = new File(file.getParentFile(), file.getName() + ".ser");
        					      }
        					      initSave = file;
        					      try {
        					        FileOutputStream fout = new FileOutputStream(file);
        					        ObjectOutputStream oos = new ObjectOutputStream(fout);
        					        oos.writeObject(taskboard);
        					        oos.close();
        					      } catch(IOException i) {
        					        	i.printStackTrace();
        					        }
        					    }
        				    }
        					else {
        						try {
        					        FileOutputStream fout = new FileOutputStream(initSave);
        					        ObjectOutputStream oos = new ObjectOutputStream(fout);
        					        oos.writeObject(taskboard);
        					        oos.close();
        					      } catch(IOException i) {
        					        	i.printStackTrace();
        					        }
        					}
        				  }
        				Container c = MainScreen.this.getParent();
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
        		}
				for(ProjectModel pm : taskboard.getProjects())
        		{
					pm.setDirty(false);
        		}
				LoginStartUp start = new LoginStartUp("admin", "admin");
			}
		});
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					    "Serializables", "ser");
				fileChooser.setFileFilter(filter);
			    int returnVal = fileChooser.showOpenDialog(load);
			    if (returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			      File file = fileChooser.getSelectedFile();
			      if (file == null) {
			        return;
			      }
			      try {
			    	    FileInputStream fileIn = new FileInputStream(file);
			            ObjectInputStream in = new ObjectInputStream(fileIn);
			            taskboard = (TaskBoardModel) in.readObject();
			            in.close();
			            fileIn.close();
			            System.out.println(MainScreen.this.initStart);
			            for(int i = 0; i < taskboard.getProjects().size(); i++)
			            {
			            	model.addElement(taskboard.getProjects().get(i));
			            }
			            cenScroll = new JScrollPane(columnsDraw((ProjectModel) selecter.getSelectedItem()), JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						MainScreen.this.add(cenScroll, BorderLayout.CENTER);
						MainScreen.this.add(controlPanel, BorderLayout.WEST);
						MainScreen.this.initStart = false;
			         } catch (IOException i) {
			            i.printStackTrace();
			            return;
			         } catch (ClassNotFoundException c) {
			            System.out.println("TaskBoardModel class not found");
			            c.printStackTrace();
			            return;
			         }
			    }
			  }

			});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(initSave == null)
				{
					JFileChooser fileChooser = new JFileChooser();
				    int returnVal = fileChooser.showSaveDialog(save);
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				      File file = fileChooser.getSelectedFile();
				      if (file == null) {
				        return;
				      }
				      if (!file.getName().toLowerCase().endsWith(".ser")) {
				        file = new File(file.getParentFile(), file.getName() + ".ser");
				      }
				      initSave = file;
				      try {
				        FileOutputStream fout = new FileOutputStream(file);
				        ObjectOutputStream oos = new ObjectOutputStream(fout);
				        oos.writeObject(taskboard);
				        oos.close();
				      } catch(IOException i) {
				        	i.printStackTrace();
				        }
				    }
			    }
				else {
					try {
				        FileOutputStream fout = new FileOutputStream(initSave);
				        ObjectOutputStream oos = new ObjectOutputStream(fout);
				        oos.writeObject(taskboard);
				        oos.close();
				      } catch(IOException i) {
				        	i.printStackTrace();
				        }
				}
				for(ProjectModel pm : taskboard.getProjects())
        		{
					pm.setDirty(false);
        		}
			  }

			});
		placeComp(selectProj, loadPanel, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 20, 0, 0);
		placeComp(selecter, loadPanel, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 20, 20, 0);
		placeComp(save, loadPanel, 2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 20, 0, 0);
		placeComp(load, loadPanel, 3, 0, 1, 1, 0, 0, 0, 0, 0, 0, 700, 0, 0);
		placeComp(createProj, loadPanel, 4, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		placeComp(logOut, loadPanel, 5, 0, 1, 1, 0, 0, 0, 0, 150, 0, 0, 0, 0);
		
		placeComp(editProj, controlPanel, 0, 0, 1, 1, 0, 0, 0, 0, 0, 20, 0, 33, 0);
		placeComp(orderCol, controlPanel, 0, 1, 1, 1, 0, 0, 0, 0, 0, 20, 0, 0, 0);
		placeComp(createTasks, controlPanel, 0, 2, 1, 1, 0, 0, 0, 0, 0, 600, 0, 31, 0);
		this.add(loadPanel, BorderLayout.NORTH);
	}
	//the main drawing method and what does the  visuals of the program
	public JPanel columnsDraw(ProjectModel proj)
	{
		JPanel mainDisplay = new JPanel(new GridBagLayout());
		ArrayList<JScrollPane> array = new ArrayList<JScrollPane>();
		ArrayList<String> columns = new ArrayList<String>();
		if(proj.getColumns().size() > 0)
		{
			columns = proj.getColumns();
			for(String s: columns)
			{
				JPanel panel = new JPanel(new GridBagLayout());
				JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scroll.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), s));
				scroll.setPreferredSize(new Dimension(200, 750));
				array.add(scroll);
			}
		}
		proj.setDisplay(array);
		if(proj.getTasks().size() > 0)
		{
			ArrayList<TaskModel> tasks = proj.getTasks();
			for(int i = 0; i < tasks.size(); i++)
			{
				for(int j = 0; j < columns.size(); j++)
				{
					if(tasks.get(i).getStatus().equals(columns.get(j)))
					{
						JPanel panel = (JPanel) array.get(j).getViewport().getView();
						JPanel task = taskCreate(tasks.get(i));
						TaskModel current = tasks.get(i);
						task.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent me) { 
								JDialog dialog = new JDialog();
								dialog.setModal(true);
								TaskView taskEd = new TaskView(proj, current);
								dialog.setLocationRelativeTo(null);
								dialog.getContentPane().add(taskEd);
								dialog.pack();
								dialog.setVisible(true);
								int index = selecter.getSelectedIndex();
								taskboard.setProject(index, taskEd.getProject());
								model.insertElementAt(taskEd.getProject(), index);
								model.removeElementAt(index + 1);
								update();
					          } 
				        });
						placeComp(task, panel, 0, i+1, 1, 1, 0, 0.1, GridBagConstraints.PAGE_START, 10, 0, 0, 0, 0, 0);
					}
				}
			}
		}
		
		for(int i = 0; i < array.size(); i++)
		{
			placeComp(array.get(i), mainDisplay, i, 0, 1, 1, 0, 0, 0, 0, 0, 0, 15, 0, 0);
		}
		return mainDisplay;
	}
	//method to create visuals of tasks
	public JPanel taskCreate(TaskModel task)
	{
		JPanel taskPanel = new JPanel(new GridBagLayout());
		taskPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		int colors[] = task.getRgbColor();
		taskPanel.setBackground(new Color(colors[0], colors[1], colors[2]));
		JLabel name = new JLabel(task.getName());
		int lastSpace = 0;
		int start = 0;
		int count = 1;
		for(int i = 0; i < task.getDescription().length(); i++)
		{
			if(task.getDescription().substring(i, i+1).equals(" "))
			{
				lastSpace = i;
			}
			if(i == 10 && count == 1)
			{
				placeComp(new JLabel("Description: " + task.getDescription().substring(start, lastSpace+1)), taskPanel, 0, count++, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				start = lastSpace+1;
			}
			else if((i+10)%22 == 0 && i != 0 )
			{
				placeComp(new JLabel(task.getDescription().substring(start, lastSpace+1)), taskPanel, 0, count++, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				start = lastSpace+1;
			}
			else if(i == task.getDescription().length()-1)
			{
				placeComp(new JLabel(task.getDescription().substring(start)), taskPanel, 0, count++, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			}
		}
		String dateS = "";
		if(!task.getDueDate().equals(""))
		{
			dateS = "Due: " + task.getDueDate().substring(2, 4) + "/" + task.getDueDate().substring(0, 2) + "/" + task.getDueDate().substring(4);
		}
		else
		{
			dateS = "Due: N/A";
		}
		JLabel date = new JLabel(dateS);
		taskPanel.setPreferredSize(new Dimension(180, 150));
		placeComp(name, taskPanel, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		placeComp(date, taskPanel, 0, count+1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);

		return taskPanel;
	}
	//updates the screen to show changes, convenience. 
	public void update()
	{
		cenScroll.setViewportView(columnsDraw((ProjectModel) selecter.getSelectedItem()));
		repaint();
		revalidate();
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
