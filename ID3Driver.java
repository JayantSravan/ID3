
import java.util.ArrayList;
import java.math.*;
import java.io.*;
public class ID3Driver
{
	static ArrayList<ArrayList<String>> dataSet;
	static Range rng;
        
	public static void main(String[] args)
	{
//<<<<<<< HEAD
		try
		{
		
			dataSet = new PreprocessData("./ID3_dataSet.txt","./ID3PreprocessData.txt").createDataPointsList();
			 rng = new Range();
		}
		catch(IOException ioe)
		{	
			ioe.printStackTrace();
		}
	        
		test();
		resolveUnknown();
		//dataSet = new PreprocessData("./ID3_dataSet.txt","./ID3PreprocessData.txt").createDataPointsList();
//=======
		//dataSet = new PreprocessData("ID3_dataSet.txt","ID3PreprocessData.txt").createDataPointsList();
	}


	public static void resolveUnknown()
	{
		//Range rng = new Range();
		ArrayList<String> CommonAttribute = new ArrayList<String>();
		int noOfAttributes = dataSet.get(1).size()-1;
		int i;
                //System.out.println(noOfAttributes);
		for(i=0; i<noOfAttributes; i++)
		{
		
			ArrayList<String> rangeOfAttr = rng.attributeRange.get(i);
			int countOfOccurences[] = new int[rangeOfAttr.size()];
			for(int j : countOfOccurences)
			{
				j=0;
			}
			for(ArrayList<String> Datapoint : dataSet)
			{
				String AttrVal = Datapoint.get(i);
				if (AttrVal.equals("?"))
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
			ArrayList<Integer> indices = new ArrayList<Integer>();
			int k =0;
			for(String s : Set)
			{
				if(s.equals("?"))
				{
					indices.add(k);
				}
				k++;
			}
			for(int t : indices)
			{
				Set.set(t, CommonAttribute.get(t));

			}
		}
		
		/*for(ArrayList<String> printarr : dataSet)
		{
			for(String str : printarr)
			{
				System.out.print(str + " ");
			}
			System.out.println(" ");
		}*/


	}
	public static void test(){
		try{
		
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

    /*for(int i=0;i<rng.attributeRange.size();i++)
		{
			for(int j=0;j<rng.attributeRange.get(i).size();j++)
			{
				System.out.println(rng.attributeRange.get(i).get(j));
			}
		}*/
		
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
