
import java.util.ArrayList;
import java.math.*;
import java.io.*;
public class ID3Driver
{
	static ArrayList<ArrayList<String>> dataSet;

	public static void main(String[] args)
	{
<<<<<<< HEAD
		try{
		dataSet = new PreprocessData("./ID3_dataSet.txt","./ID3PreprocessData.txt").createDataPointsList();
	}catch(IOException ioe){ioe.printStackTrace();}
		test();
		//dataSet = new PreprocessData("./ID3_dataSet.txt","./ID3PreprocessData.txt").createDataPointsList();
=======
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

>>>>>>> a51f1559329c39155db196eea10434c36e092ec8
	}
	public static void test(){
		try{
		Range rng = new Range();
		//if(rng.attributeRange.get(3).contains("continuous")) System.out.println("True");
		for(int j=0;j<rng.attributeRange.size();j++)
		{
				if(rng.attributeRange.get(j).contains("continuous")){
						double average = 0;
						//System.out.println(dataSet.size());
						for(int i=0;i<dataSet.size();i++)
						{
							//System.out.println(i + " " + j );
							//if(rng.attributeRange.get(13).contains("continuous")) System.out.println(rng.attributeRange.get(13).get(0));
							//System.out.println(dataSet.get(i).get(j) );
							average+=Integer.parseInt(dataSet.get(i).get(j));
						}

						double sd = 0;
						for (int i = 0; i < dataSet.size(); i++)
						{
							sd += Math.pow((Integer.parseInt(dataSet.get(i).get(j)) - average),2) / dataSet.size();
						}
						double standardDeviation = Math.sqrt(sd);

					for(int i=0;i<dataSet.size();i++)
					{
						 if(dataSet.get(i).get(j).equals("?"))
						 {

						 }
						else if(Integer.parseInt(dataSet.get(i).get(j))< (average - standardDeviation))
						{
							 dataSet.get(i).set(j,"low");
						}
						else if(Integer.parseInt(dataSet.get(i).get(j))> (average - standardDeviation))
						{
								dataSet.get(i).set(j,"high");
						}
						else
						{
							 dataSet.get(i).set(j,"medium");
						}
			 }
			 rng.attributeRange.get(j).remove("continuous");
			 rng.attributeRange.get(j).add("high");
			 rng.attributeRange.get(j).add("medium");
			 rng.attributeRange.get(j).add("low");
		 }
 }

//System.out.println("random");

    for(int i=0;i<rng.attributeRange.size();i++)
		{
			for(int j=0;j<rng.attributeRange.get(i).size();j++)
			{
				System.out.println(rng.attributeRange.get(i).get(j));
			}
		}
		
}
catch(Exception e){ e.printStackTrace();}
	/*public static double calculateEntropy(Node attribute)
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
			//dataSet[attributeMap(attribute)][]
		}

	}



	public static void main(String[] args)
	{

	}
*/

}
}
