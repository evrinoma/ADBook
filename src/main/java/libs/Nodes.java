package libs;

import java.util.ArrayList;
import java.util.HashMap;

import entity.LevelNode;
import entity.UserDto;

public class Nodes {

	private int maxDeep = 0;
	private int maxColumn = 0;
	private int maxColumnLevel = 0;
	private int maxWidth = 0;
	private int maxHeight = 0;

	private HashMap<String, UserDto> users;

	private HashMap<Integer, ArrayList<LevelNode>> nodes;

	public Nodes(HashMap<String, UserDto> users) {
		nodes = new HashMap<Integer, ArrayList<LevelNode>>();
		this.users = users;
	}

	private void getUserDependency(LevelNode parentUser, int level) {
		level++;
		ArrayList<LevelNode> levelNodesUsers = nodes.get(level);
		if (null == levelNodesUsers) {
			levelNodesUsers = new ArrayList<LevelNode>();
		}
		for (String dn : parentUser.getUser().getDirectReports()) {
			UserDto manager = this.users.get(dn);
			if (null != manager) {
				LevelNode managerNode = new LevelNode(manager, parentUser, level);
				managerNode.calcX(levelNodesUsers.size());
				levelNodesUsers.add(managerNode);				
				parentUser.addChild(managerNode);
				getUserDependency(managerNode, level);				
			}
		}
		if (!levelNodesUsers.isEmpty()) {
			nodes.put(level, levelNodesUsers);
		}
	}

	public HashMap<Integer, ArrayList<LevelNode>> getLevels(UserDto user) {
		ArrayList<LevelNode> levelNodesUsers = new ArrayList<LevelNode>();
		LevelNode rootUser = new LevelNode(user);
		levelNodesUsers.add(rootUser);
		nodes.put(0, levelNodesUsers);
		getUserDependency(rootUser, 0);

		return nodes;
	}

	/*
	 * public LevelNodes getUserDependency(UserDto user, int level) { LevelNodes
	 * users = new LevelNodes(user); users.setLevel(level); level++; if (maxDeep
	 * < level) { maxDeep = level; } for (String dn : user.getDirectReports()) {
	 * UserDto manager = this.users.get(dn); if (null != manager) {
	 * users.addLink(getUserDependency(manager, level)); } }
	 * 
	 * return users; }
	 * 
	 * public void calc(LevelNodes root, int column) { for (LevelNodes child :
	 * root.getUserLinks()) {
	 * 
	 * } }
	 * 
	 * public void calc(LevelNodes root) { calc(root, maxColumn); }
	 * 
	 * public LevelNodes getUserDependency(UserDto user, HashMap<String,
	 * UserDto> users) { this.users = users; LevelNodes root =
	 * getUserDependency(user, 0); root.setDeep(maxDeep);
	 * root.setMaxColumn(maxColumn); root.setMaxColumnLevel(maxColumnLevel);
	 * calc(root);
	 * 
	 * return root; }
	 */
}
