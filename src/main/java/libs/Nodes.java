package libs;

import java.util.ArrayList;
import java.util.HashMap;

import entity.LevelNode;
import entity.UserDto;

public class Nodes {

	private Core core = null;
	private HashMap<Integer, ArrayList<LevelNode>> nodes;

	public Nodes(Core core) {
		nodes = new HashMap<Integer, ArrayList<LevelNode>>();
		this.core = core;
	}

	private void getUserDependency(LevelNode parentUser, int level) {
		level++;
		ArrayList<LevelNode> levelNodesUsers = nodes.get(level);
		if (null == levelNodesUsers) {
			levelNodesUsers = new ArrayList<LevelNode>();
		}
		for (String distinguishedName : parentUser.getUser().getDirectReports()) {
			UserDto manager = core.getCompanys().findUserByDistinguishedName(distinguishedName);
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
}
