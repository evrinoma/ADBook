package entity;

import javax.swing.JPanel;

import com.mxgraph.view.mxGraph;

public class VertexNode {	
		private int level = 0;
		private int x = 0;
		private int y = 0;		
		private UserDto user;	
		
		private int height = 30;
		private int width = 80;
		
		
		public VertexNode(){
		}
		
		public VertexNode(UserDto user)
		{
			this.user = user;
			this.level = 0;
		}
		
		public VertexNode(UserDto user, int level)
		{
			this.user = user;
			this.level = level;
		}
		
		public int getHeight()
		{
			return height;
		}
		
		public int getWidth()
		{
			return width;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
		
		public void setX(int x)
		{
			this.x=x;
		}
		
		public void setY(int y)
		{
			this.y=y;
		}
		
		public int getLevel()
		{
			return level;
		}
		
		public int incLevel()
		{
			return level++;
		}
		
		public void setLevel(int level)
		{
			this.level = level;
		}
		
		public VertexNode addUser(UserDto user)
		{
			this.user = user;
			
			return this;
		}
				
		
		public Object getVertex(mxGraph graph, Object parent)
		{
			return graph.insertVertex(parent, null, user.getSn(), getX(), getY(),  width, height);
		}	
}
