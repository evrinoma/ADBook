package entity;

import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JPanel;

import com.mxgraph.view.mxGraph;

/**
 * @author nikolns
 *
 */
public class LevelNode {

	private String A = "";

	private int level = 0;
	private UserDto user;
	private LevelNode parent = null;

	private int startY = 50;
	private int startX = 50;
	
	private int x = 0;
	private int y = 0;

	private int stepX = 50;
	private int stepY = 250;

	private int height = 45;
	private int width = 100;

	private mxGraph graph = null;
	private Object vertex = null;	
	private Object userVertex = null;

	private ArrayList<LevelNode> childs;

	private void createLevelNode(UserDto user, LevelNode parent, int level) {
		setLevel(level);
		setUser(user);
		setParent(parent);
		calcY(level);
		this.childs = new ArrayList<LevelNode>();
		this.A = user.getCn();
	}

	public LevelNode(UserDto user, LevelNode parent, int level) {
		createLevelNode(user, parent, level);
	}

	public LevelNode(UserDto user) {
		createLevelNode(user, null, 0);
	}

	public LevelNode(UserDto user, int level) {
		createLevelNode(user, null, level);
	}

	public void addChild(LevelNode child) {
		childs.add(child);
	}

	public UserDto getUser() {
		return user;
	}

	private void setUser(UserDto user) {
		this.user = user;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<LevelNode> getChilds() {
		return childs;
	}

	public void setParent(LevelNode parent) {
		this.parent = parent;
	}

	public LevelNode getParent() {
		return parent;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getStepX() {
		return stepX;
	}

	public void setStepX(int stepX) {
		this.stepX = stepX;
	}

	public int getStepY() {
		return stepY;
	}

	public void setStepY(int stepY) {
		this.stepY = stepY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void calcX(int size) {
		setX(size * (this.stepX + width));
	}

	public void calcY(int level) {
		setY(level * this.stepY);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isRoot() {
		return (null == parent) ? true : false;
	}

	public LevelNode setGraph(mxGraph graph) {
		this.graph = graph;
		return this;
	}

	public LevelNode setVertex(Object parentVertex) {
		this.vertex = parentVertex;
		return this;
	}

	private boolean isVertex() {
		return (null == graph || null == vertex) ? true : false;
	}

	public Object getVertex() {

		return isVertex() ? null : getChildVertex();
	}

	private Object createVertex()
	{
		userVertex = graph.insertVertex(vertex, null, user.getLastName()+"\n"+user.getFirstName()+"\n"+user.getMiddleName()+"\n", x+startX, y+startY, width, height);
		return userVertex;
	}
	
	private Object getChildVertex() {
		return (null == userVertex)? createVertex(): userVertex;
	}
	
	public void setVertexLink() {
		if (!isRoot() & !isVertex()) {
			graph.insertEdge(vertex, null, "", parent.getVertex(), getChildVertex());
		}
	}

}
