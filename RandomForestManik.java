public class RandomForestManik
{

    public static RandomForest(Node parent)
		{	ArrayList<String> attributeList=new ArrayList<String>(attributeRangeHashMap.keySet());
			ArrayList<String> randomAttributeList=new ArrayList<String>();
			ArrayList<String> randomDataSet=new ArrayList<String>();
			int randomNumber;
			for(int i=0;i<4;i++)
			{
				randomNumber = (int)(Math.random() * (attributeList.size());
				randomAttributeList.add(attributeList.get(randomNumber));
				attributeList.remove(randomNumber);
			}
    	for(int i=0;i<dataSet.size();i++)
			{
				randomDataSet.add((int)(dataSet.get(Math.random() * (dataSet.size()))));
			}
    	parent.reducedDataSet=randomDataSet;
      ID3Random(parent);
	}
		public static String calculateMaxIgAttributeRandom(Node parent,ArrayList<ArrayList<String>> randomAttributeList)
		{
		  double maxIG=0.0;
		  //String maxAttributeName = "aa";
		  for(int i=0; i < randomAttributeList.size();i++)
		  {
		    double temp= calculateInformationGain(parent,randomAttributeList.get(i));
		    //System.out.println(temp + " " + attributeKey);
		    if(temp==1.0){ maxAttributeName = randomAttributeList.get(i);  return randomAttributeList.get(i);}
		    else if(temp!=parent.informationGain && maxIG <= temp)
		    {
		    	//System.out.println("max1" + attributeKey);
		      maxIG=temp;
		      maxAttributeName=randomAttributeList.get(i);
		    }
		  }
		    return maxAttributeName;
		}

		public static  void ID3Random(Node parent)
		{
		// System.out.println(parent.nodeName);
		try{ fw.write(parent.nodeName + "\n");}catch(Exception e){}


				if(parent.reducedDataSet.isEmpty())
				{
					//parent.children.add(new Node(true));
					int index =  new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(parent.nodeName.split(":")[0]);
					Boolean b = findMostCommon(parent.reducedDataSet, index,parent.nodeName.split(":")[1] );
				parent.children.add(new Node(b));
				try{ fw.write("1" + true + "\n");}catch(Exception e){}
				c++;
				}
				else if(parent.allYes)
				{
					try{ fw.write("2" + true + "\n");}catch(Exception e){}
					System.out.println(parent.nodeName);
				parent.children.add(new Node(true));
				}
				else if(parent.allNo)
				{
					try{ fw.write("3" + false + "\n");}catch(Exception e){}
					System.out.println(parent.nodeName);
					parent.children.add(new Node(false));
				}

				else
				{
					String maxAttributeName=calculateMaxIgAttributeRandom(parent);
					int attributeIndex = new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(maxAttributeName);   //make a separate array list for keys
					ArrayList<String> attributeVariety=attributeRangeHashMap.get(maxAttributeName);  //like high,low,medium for wind
					if(maxAttributeName.compareTo(parent.nodeName.split(":")[0])==0)
					{
						int index =  new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(parent.nodeName.split(":")[0]);
						Boolean b = findMostCommon(parent.reducedDataSet, index,parent.nodeName.split(":")[1] );
						try{ fw.write("x" + "\n");}catch(Exception e){}
						parent.children.add(new Node(b));
					}
					else{
					for(int j=0;j<attributeVariety.size();j++)
					{
							Node child=new Node(maxAttributeName + ":" + attributeVariety.get(j),obtainReducedDataset(parent,attributeVariety.get(j),attributeIndex));
							parent.children.add(child);
							child.informationGain = calculateEntropy(parent.reducedDataSet, maxAttributeName, attributeVariety.get(j));
							ID3Random(child);
					}}
				}
		}
}
