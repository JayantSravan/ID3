package ID3;
import java.util.ArrayList;

public class Node
{
	private String nodeName;
	private ArrayList<Node> children = new ArrayList<>();
	private ArrayList<Integer> dataList_positive;
	private ArrayList<Integer> dataList_negative;
	
	public Node(String nodeName, ArrayList<Integer> dataList)
	{
		this.nodeName = nodeName;
		for(Integer index : dataList)
		{
			if()
		}
	}
	
	public ArrayList<Node> getChildren()
	{
		return children;
	}
	public void setNodeName(String name)
	{
		this.nodeName = name;
	}
	public String getNodeName()
	{
		return nodeName;
	}
	public void addChildren(Node child)
	{
		children.add(child);
	}
	public ArrayList<Integer> getDataListPositive()
	{
		return dataList_positive;
	}
	public ArrayList<Integer> getDataListNegative()
	{
		return dataList_negative;
	}
}
