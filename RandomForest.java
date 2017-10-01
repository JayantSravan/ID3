import java.math.*;
import java.util.*;
public class RandomForest
{
    static int number;
    static ArrayList<Node> setOfTrees;
    static int numberOfAttributes;
    public RandomForest()
    {
      number = 10;
      setOfTrees = new ArrayList<Node>();
      numberOfAttributes = (int)(Math.sqrt((NewDriver.dataSet.get(1).size()-1))) +1;
    }
    public static void makeForest()
    {
        for(int n = 0; n<number; n++)
        {
            ArrayList<ArrayList<String>> randomDataSet = new ArrayList<ArrayList<String>>();
            ArrayList<Integer> setOfDatapoints = new ArrayList<Integer>();
            ArrayList<Integer> setOfAttributes = new ArrayList<Integer>();
            for(int i= 0; i<numberOfAttributes; i++)
            {

                int randomNumber = (int)(Math.random() * (NewDriver.dataSet.get(1).size()-1));
                if(!setOfAttributes.contains(randomNumber))
                {
                    setOfAttributes.add(randomNumber);
                }
                else
                {
                    i--;
                }

            }
            for (int i = 0; i < NewDriver.dataSet.size()-1; i++)
            {
                int randomNumber = (int)(Math.random() * (NewDriver.dataSet.size()-1));
                setOfDatapoints.add(randomNumber);
            }

            for(Integer datapointNumber : setOfDatapoints)
            {
                ArrayList<String> datapoint = NewDriver.dataSet.get(datapointNumber);
                ArrayList<String> randomDatapoint = new ArrayList<String>();
                for(Integer attributeNumber : setOfAttributes)
                {
                    String attributeVal = datapoint.get(attributeNumber);
                    randomDatapoint.add(attributeVal);
                }
                randomDataSet.add(randomDatapoint);
            }

            Node treeOfTheForest = new Node("SingleTree", randomDataSet);

            treeOfTheForest.informationGain = NewDriver.calculateEntropy(randomDataSet, " ", "");
            System.out.println(treeOfTheForest.informationGain);
            NewDriver.ID3(treeOfTheForest);
            setOfTrees.add(treeOfTheForest);
        }
    }
}
