package entity;

import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JPanel;

public class LevelNodes {
	
	private int size = 0;	
	private int level = 0;	
	private ArrayList<VertexNode> points = null;
	private ArrayList<Object> links = null;
	
	public LevelNodes()
	{
		points = new ArrayList<VertexNode> ();
		links = new ArrayList<Object> ();
	}
	
	
	public void addListPoints(VertexNode point)
	{
		points.add(point);
		size++;
	}
	
	public void addListPoints(int level, UserDto user)
	{
		VertexNode point = new VertexNode();
		point.setLevel(level);
		point.addUser(user);
		
		addListPoints(point);
	}
	
	public void addLink(Object link)
	{
		links.add(link);
	}
	
	public boolean isEmpty()
	{
		return (0 == size) ? true : false;
	}
	
	public int size()
	{
		return size;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public ArrayList<Object> getVertexLinks()
	{
		return links;
	}
	
	public ArrayList<VertexNode> getVertexNodes()
	{
		return points;
	}
	
	public int calcMaxHeight()
	{
		
		return (!points.isEmpty()) ? points.stream().max(Comparator.comparing(VertexNode -> VertexNode.getHeight())).get().getHeight() : 0;		
	}
	
	public int calcMaxWidth()
	{
		return (!points.isEmpty()) ? points.stream().max(Comparator.comparing(VertexNode -> VertexNode.getWidth())).get().getWidth() : 0;
	}
}
