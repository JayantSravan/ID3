//ackage ID3;
import java.io.*;
import java.util.*;
import java.math.*;

public class ResolveMissing_and_ContinuousValues
{
	ArrayList<ArrayList<String>> dataSet;
	LinkedHashMap<String, ArrayList<String>> attributeRangeHashMap;

	public ResolveMissing_and_ContinuousValues(ArrayList<ArrayList<String>> dataSet)
	{
		this.dataSet = dataSet;
		attributeRangeHashMap = new Range2("classListID3.txt").attributeRangeHashMap;
		resolveMissingValues();
		resolveContinuousValues();
	}


	public  void resolveContinuousValues()
	{
		int j = 0;
		try
		{
			for(String keyAttribute : attributeRangeHashMap.keySet())
			{
				if(j==13) break;
				ArrayList<String> attributeValueList = attributeRangeHashMap.get(keyAttribute);
				if(attributeValueList.contains("continuous"))
				{
					double average = 0;
					double standardDeviation = 0;
					for(ArrayList<String> dataRow : dataSet)
					{
						average+=Integer.parseInt(dataRow.get(j))/dataSet.size();
					}
					//average = average/dataSet.size();
					for(ArrayList<String> dataRow : dataSet)
					{
						standardDeviation += Math.pow((Integer.parseInt(dataRow.get(j)) - average),2) / dataSet.size();
					}
					standardDeviation = Math.sqrt(standardDeviation);

					for(ArrayList<String> dataRow : dataSet)
					{
						if(dataRow.get(j).equals("?")) {}
						else if(Integer.parseInt(dataRow.get(j))<(average - standardDeviation)) dataRow.set(j,"low");
						else if(Integer.parseInt(dataRow.get(j))>(average + standardDeviation)) dataRow.set(j,"high");
						else dataRow.set(j,"medium");

					}
					attributeRangeHashMap.get(keyAttribute).add("high");
					attributeRangeHashMap.get(keyAttribute).add("low");
					attributeRangeHashMap.get(keyAttribute).add("medium");
					attributeRangeHashMap.get(keyAttribute).remove("continuous");
				}
				j++;
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void resolveMissingValues()
	{
		ArrayList<String> commonAttribute = new ArrayList<String>();
		int numOfAttributes = dataSet.get(1).size()-1;
		ArrayList<String> rangeOfAttributes;
		int i = 0;

		for(String attribute : attributeRangeHashMap.keySet())
		{
			int indexOfCommon = 0,max=0,k=0;
			//System.out.println("Yo");
			rangeOfAttributes = attributeRangeHashMap.get(attribute);
			int countOfOccurences[] = new int[rangeOfAttributes.size()];
			Arrays.fill(countOfOccurences, 0);

			for(ArrayList<String> dataRow : dataSet)
			{
				String attributeValue = dataRow.get(i);
				if (attributeValue.equals("?"))
				{
					continue;
				}
				for(String s : rangeOfAttributes)
				{
					if(s.equals(attributeValue))
					{
						int l = rangeOfAttributes.indexOf(s);
						countOfOccurences[l]++;
					}
				}
			}

			for(int j : countOfOccurences)
			{
				if(j>max)
				{
					max =j;
					indexOfCommon = k;
				}
				k++;
			}
			String mostCommonValue = rangeOfAttributes.get(indexOfCommon);
			commonAttribute.add(mostCommonValue);
			i++;
		}

		for(ArrayList<String> dataRow : dataSet)
		{
			ArrayList<Integer> indices = new ArrayList<Integer>();
			int k =0;
			for(String s : dataRow)
			{
				if(s.equals("?"))
				{
					indices.add(k);
				}
				k++;
			}
			for(int t : indices)
			{
				dataRow.set(t, commonAttribute.get(t));

			}
		}

	}

}
