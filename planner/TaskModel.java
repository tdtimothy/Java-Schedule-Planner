package planner;

import java.io.Serializable;
import java.util.Arrays;

//import java.util.TreeSet;

public class TaskModel implements Comparable<TaskModel>, Serializable
{
	//serial for serialization
	private static final long serialVersionUID = 13L;
	//name of task
	private String name;
	//description of task
	private String description;
	//duedate in easy to use format
	private String dueDate;
	//determines what column task goes under
	private String status;
	//color of the task visual
	private int rgbColor[];
	//constructor
	public TaskModel(String name, String description, String dueDate, String status, int red, int green, int blue) 
	{
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
		setRgbColor(new int[]{red, green, blue});
	}
	//getter method
	public String getName() 
	{
		return name;
	}
	//setter method
	public void setName(String name) 
	{
		this.name = name;
	}
	//getter method
	public String getDescription() 
	{
		return description;
	}
	//setter method
	public void setDescription(String description) 
	{
		this.description = description;
	}
	//getter method
	public String getDueDate() {
		return dueDate;
	}
	//setter method
	public void setDueDate(String dueDate) 
	{
		this.dueDate = dueDate;
	}
	//getter method
	public String getStatus() 
	{
		return status;
	}
	//setter method
	public void setStatus(String status) 
	{
		this.status = status;
	}
	//getter method
	public int[] getRgbColor() {
		return rgbColor;
	}
	//setter method
	public void setRgbColor(int rgbColor[]) {
		this.rgbColor = rgbColor;
	}
	//comparable implementation
	public int compareTo(TaskModel task) 
	{
		if(this.dueDate.equals(""))
		{
			return -1;
		}
		else if(task.dueDate.equals(""))
		{
			return 1;
		}
		if(Integer.parseInt(this.dueDate.substring(4)) == Integer.parseInt(task.dueDate.substring(4)))
		{
			if(Integer.parseInt(this.dueDate.substring(2,4)) == Integer.parseInt(task.dueDate.substring(2,4)))
			{
				if(Integer.parseInt(this.dueDate.substring(0,2)) == Integer.parseInt(task.dueDate.substring(0,2)))
				{
					return this.name.compareTo(task.name);
				}
				return Integer.parseInt(this.dueDate.substring(0,2)) - Integer.parseInt(task.dueDate.substring(0,2));
			}
			return Integer.parseInt(this.dueDate.substring(2,4)) - Integer.parseInt(task.dueDate.substring(2,4));
		}
		return Integer.parseInt(this.dueDate.substring(4)) - Integer.parseInt(task.dueDate.substring(4));
	}
	//comparable implementation
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(rgbColor);
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	//comparable implementation
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskModel other = (TaskModel) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(rgbColor, other.rgbColor))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	//returns string of task
	public String toString()
	{
		return "Name: " + name + status + description + dueDate;
	}
}
