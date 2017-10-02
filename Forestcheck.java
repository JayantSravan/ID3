public class Forestcheck{

  public static double findAccuracyOfRandomForest(ArrayList<Node> parent)
  {

    //System.out.println("*********");
      ArrayList<String> temp;                     //just a temporary ArrayList
      int count1=0,count2=0,count=0;                           // obvious
      for(int i=0;i<testDataSet.size();i++)       // iterating over testData
      {
          temp=testDataSet.get(i);
          for(int i=0;i<101;i++)
          {
            if((checkPositiveCaseForTestData(temp,parent.get(i)))
            {
              count1++;
            }
            if(!checkPositiveCaseForTestData(temp,parent.get(i)))
            {
              count2++;
            }
          }
          if(((count2 > count1)&&(temp.get(temp.size()-1).equals("0")))||((count1>=count2)&&(temp.get(temp.size()-1).equals("1"))))
          {
              count++;
          }

      }
      //System.out.println(testDataSet.size());
      double accuracy=(double)count/testDataSet.size();
      //System.out.println("num " + c);
      return accuracy;
  }

  public static boolean checkPositiveCaseForTestData(ArrayList<String> tempString,Node parent)   //returns true or false depending
  {
    /*if(parent.leafBit==1)
    {
      return parent.leafValue;
    }*/
    //System.out.println(parent.children.get(0).nodeName);
    if(!(parent.children.isEmpty())&& parent.children.get(0).leafBit==1) return parent.children.get(0).leafValue;
    //System.out.println(parent.children.get(0).leafBit);
   // System.out.println(parent.nodeName + parent.children.size() + parent.leafBit) ;
    //System.out.println(parent.nodeName + parent.children.get(0).leafValue);
    String word="aa";
    if(!parent.children.isEmpty())
    {
      String[] words=parent.children.get(0).nodeName.split(":");  // for fetching out attributetype eg: wind from wind:high
      word=words[0];

    int attributeindextemp=new ArrayList<String>(attributeRangeHashMap.keySet()).indexOf(word); // calculating indexof element in hashmap so as to pick attributename (eg; high) from tempstring
    String attributeName=tempString.get(attributeindextemp); //getting the attributename from tempstring list
    String completeName = word + ":"+ attributeName; // making the nodename for finding node compared in children list

    for(int i=0;i<parent.children.size();i++)   //just interating to find that node with the name completename
    {
      if(parent.children.get(i).nodeName.equals(completeName))
      {
      //System.out.println(parent.nodeName + parent.children.size() + tempString);
        return checkPositiveCaseForTestData(tempString,parent.children.get(i)); //obvious
        //break;
      }
    }
  }
  return false;
  }
}
