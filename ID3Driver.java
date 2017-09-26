package ID3;
import java.util.ArrayList;

public class ID3Driver
{
	String[][] dataSet;	
	
	public static double calculateEntropy(Node attribute)
	{
		double p_positive = attribute.getDataListPositive().size()/(attribute.getDataListPositive().size() + attribute.getDataListNegative().size());
		double p_negative = attribute.getDataListNegative().size()/(attribute.getDataListPositive().size() + attribute.getDataListNegative().size());
		return (-(Math.log(p_positive)*(p_positive))-(Math.log(p_negative)*(p_negative)))/Math.log(2);
	}
	
	public static void addChild(Node parent,Node child)
	{
		parent.addChildren(child);
	}
	
	public static void findChildDataset(Node parent, String attribute, String attributeValue)
	{
		ArrayList<Integer> parentDataSet = parent.getDataListPositive();
		for(Integer dataRowNum : parentDataSet)
		{
			dataSet[attributeMap(attribute)][]
		}
		
	}
	
	
	
	public static void main(String[] args)
	{
		
	}
	
}
