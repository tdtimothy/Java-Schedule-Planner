package planner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JScrollPane;

public class ProjectModel implements Serializable
{
	//Serial number for serialization
	private static final long serialVersionUID = 29L;
	//name of project
	private String name;
	//an arraylist of the columns for the project
	private ArrayList<String> columns = new ArrayList<String>();
	//an arraylist of the tasks for the project
	private ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();
	//an arraylist of JScrollPanes for the project. Used to make the MainScreen easier.
	private ArrayList<JScrollPane> display;
	//flag to determine whether saving is needed or not
	private boolean dirty = false;
	
	//constructor for project
	public ProjectModel(String name, ArrayList<String> array)
	{
		this.name = name;
		columns = array;
	}
	//getter method
	public String getName() {
		return name;
	}
	//setter method
	public void setName(String name) {
		this.name = name;
	}
	//getter method
	public ArrayList<String> getColumns() {
		return columns;
	}
	//setter method
	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}
	//getter method
	public ArrayList<TaskModel> getTasks() {
		return tasks;
	}
	//setter method, also sorts using comparable
	public void setTasks(ArrayList<TaskModel> tasks) {
		this.tasks = tasks;
		Collections.sort(tasks);
	}
	//shortcut to adding tasks to arraylist and sorts
	public void addTasks(TaskModel task)
	{
		tasks.add(task);
		Collections.sort(tasks);
	}
	//shortcut to removing tasks from arraylist
	public void removeTasks(TaskModel task)
	{
		tasks.remove(task);
		Collections.sort(tasks);
	}
	//getter method
	public ArrayList<JScrollPane> getDisplay() {
		return display;
	}
	//setter method
	public void setDisplay(ArrayList<JScrollPane> display) {
		this.display = display;
	}
	//getter method
	public boolean isDirty() {
		return dirty;
	}
	//setter method
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	//Shows project name, mainly used for testing.
	public String toString()
	{
		return name;
	}
}
