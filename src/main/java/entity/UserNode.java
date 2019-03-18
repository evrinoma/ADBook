package entity;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class UserNode {

	private UserDto user = null;	
	private int count = 0;
	private TreePath path = null;
	
	public UserNode(UserDto user)
	{
		this.user = user;
	}
	
	public UserNode()
	{
	}
	
	public UserDto getUser()
	{
		return user;
	}
	
	public void setUser(UserDto user)
	{
		this.user = user;
	}
	
	public void incCountUser(int count)
	{
		this.count += count;
	}
	
	public int getCountUsers()
	{
		return this.count;
	}
	
	public boolean isOnly()
	{
		return 1 == this.count;
	}
	
	public TreePath getPath()
	{
		return this.path;
	}
	
	public void findUser(JTree tree) 
	{
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		DefaultMutableTreeNode node = null;
		if (root != null) {
			for (Enumeration e = root.breadthFirstEnumeration(); e.hasMoreElements();) {
				DefaultMutableTreeNode current = (DefaultMutableTreeNode) e.nextElement();
				if (current.getUserObject() instanceof UserDto) {
					path = new TreePath(current.getPath());
				}
			}
		}
	}
}
