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
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicArrowButton;

public class ColumnsEdit extends JPanel
{
	//ProjectModel that stores changes and is returned into MainScreen for changes.
	ProjectModel project;
	
	//Constructor method and both the controller and viewer of the class, though not all of the controls
	public ColumnsEdit(ProjectModel proj)
	{
		project = proj;
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for(String p: project.getColumns())
		{
			listModel.addElement(p);
		}
		JList<String> jList = new JList<String>(listModel);
		jList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollList = new JScrollPane(jList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollList.setPreferredSize(new Dimension(200, 250));
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(280, 350));
		placeComp(scrollList, this, 0, 1, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		JLabel reorder = new JLabel("Reorder Columns");
		placeComp(reorder, this, 0, 0, 3, 1, 0, 0, 0, 0, 0, 30, 0, 0, 0);
		BasicArrowButton up = new BasicArrowButton(BasicArrowButton.NORTH);
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = jList.getSelectedIndex();
				if(index == 0 || jList.isSelectionEmpty())
				{
					return;
				}
				String temp = listModel.get(index);
				listModel.set(index, listModel.get(index - 1));
				listModel.set(index - 1, temp);
				jList.setSelectedIndex(index - 1);
				jList.updateUI();
			}
		});
		BasicArrowButton down = new BasicArrowButton(BasicArrowButton.SOUTH);
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int index = jList.getSelectedIndex();
				if(index == listModel.getSize() - 1 || jList.isSelectionEmpty())
				{
					return;
				}
				String temp = listModel.get(index);
				listModel.set(index, listModel.get(index + 1));
				listModel.set(index + 1, temp);
				jList.setSelectedIndex(index + 1);
				jList.updateUI();
			}
		});
		placeComp(up, this, 2, 3, 1, 1, 0, 0, 0, 70, 10, 0, 0, 10, 10);
		placeComp(down, this, 2, 4, 1, 1, 0, 0, 0, 0, 10, 70, 0, 10, 10);
		
		JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
        JButton quit = new JButton("Cancel");
        quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container c = ColumnsEdit.this.getParent();
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
				for(int i = 0; i < listModel.getSize(); i++)
				{
					array.add(listModel.get(i));
				}
				project.setColumns(array);
				project.setDirty(true);
				Container c = ColumnsEdit.this.getParent();
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
        confirmPanel.add(Box.createRigidArea(new Dimension(10,0)));
        confirmPanel.add(quit);
        confirmPanel.add(Box.createRigidArea(new Dimension(10,0)));
        confirmPanel.add(ok);
        placeComp(confirmPanel, this, 1, 5, 1, 1, 0, 0, 0, 10, 0, 0, 0, 0, 0);
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
