package ID3;
import java.util.ArrayList;

public class ID3Driver
{
	ArrayList<ArrayList<String>> dataSet;

	public static void main(String[] args)
	{
		dataSet = new PreprocessData("ID3_dataSet.txt","ID3PreprocessData.txt").createDataPointsList();
	}


	public void resolveUnknown()
	{
		Range range = new Range();
		ArrayList<String> CommonAttribute = new ArrayList<String>();
		int noOfAttributes = lastIndexOf(dataset.get(1));
		int i;

		for(i=0; i<noOfAttributes; i++)
		{
			ArrayList<String> rangeOfAttr = range.attributeRange.get(i);
			int[] countOfOccurences = new() int[rangeOfAttr.size()];
			for(int j : countOfOccurences)
			{
				j=0;
			}
			for(ArrayList<String> Datapoint : dataSet)
			{
				String AttrVal = Datapoint.get(i);
				if AttrVal.equals("?")
				{
					continue;
				}



				for(String s : rangeOfAttr)
				{
					if(s.equals(AttrVal))
					{
						int k = rangeOfAttr.indexOf(s);
						countOfOccurences[k]++;
					}

				}

			}

			int indexOfCommon = 0;
			int max = 0;
			int k =0;
			for(int j : countOfOccurences)
			{
				if(j>max)
				{
					max =j;
					indexOfCommon = k;
				}
				k++;
			}
			String mostCommonValue = rangeOfAttr.get(indexOfCommon);
			CommonAttribute.add(mostCommonValue);

		}


		for(ArrayList<String> Set : dataSet)
		{
			ArrayList<int> indices = ArrayList<int>;
			int k =0;
			for(String s : Set)
			{
				if(s.equals("?"))
				{
					indices.add(k);
				}
				k++;
			}
			for(int k : indices)
			{
				Set.set(k, CommonAttribute.get(k));

			}
		}

	}

	public static double calculateEntropy(Node attribute)
	{
		double p_positive = attribute.getDataListPositive().size()/(attribute.getDataListPositive().size() + attribute.getDataListNegative().size());
		double p_negative = attribute.getDataListNegative().size()/(attribute.getDataListPositive().size() + attribute.getDataListNegative().size());
		return (-(Math.log(p_positive)*(p_positive))-(Math.log(p_negative)*(p_negative)))/Math.log(2);
	}

	public void resolveUnknown()//remove all the datapoints with unknown attributeValues
	{

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
