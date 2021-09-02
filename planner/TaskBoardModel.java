package planner;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskBoardModel implements Serializable
{
	//name for taskboard
	private String name = "Taskboard1";
	//arraylist of all the projects in the save file
	private ArrayList<ProjectModel> projects = new ArrayList<ProjectModel>();
	//filename of save, thought not really needed
	private String fileName;
	//serialization for serializable
	private static final long serialVersionUID = 6L;
	//getter method
	public String getName() {
		return name;
	}
	//setter method
	public void setName(String name) {
		this.name = name;
	}
	//getter method
	public ArrayList<ProjectModel> getProjects() {
		return projects;
	}
	//setter method
	public void setProjects(ArrayList<ProjectModel> projects) {
		this.projects = projects;
	}
	//shortcut to set
	public void setProject(int index, ProjectModel project) {
		projects.set(index, project);
	}
	//shortcut to add
	public void addProjects(ProjectModel proj) {
		projects.add(proj);
	}
	//getter method
	public String getFileName() {
		return fileName;
	}
	//setter method
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
