import java.math.*;
public class RandomForest
{
    static int number = 1000;
    ArrayList<node> setOfTrees = ArrayList<node>;
    static int numberOfAttributes = Math.ceil(Math.sqrt(NewDriver.datSet.get(1).size()));
    public static void makeForest(int number, int numberOfAttributes)
    {
        for(int n = 0; n<number; n++)
        {
            ArrayList<ArrayList<String>> randomDataSet = new ArrayList<ArrayList<String>>();
            ArrayList<Integer> setOfDatapoints = new ArrayList<Integer>();
            ArrayList<Integer> setOfAttributes = new ArrayList<Integer>();
            for(int i= 0; i<numberOfAttributes; i++)
            {

                int randomNumber = Math.random() * NewDriver.dataSet.get(1).size();
                if(!setOfAttributes.contains(randomNumber))
                {
                    setOfAttributes.add(randomNumber);
                }
                else
                {
                    i--;
                }

            }
            for (int i = 0; i < NewDriver.dataset.size(); i++)
            {
                int randomNumber = Math.random() * NewDriver.dataSet.size();
                setOfDatapoints.add(randomNumber);
            }

            for(Integer datapointNumber : setOfDatapoints)
            {
                ArrayList<String> datapoint = dataSet.get(datapointNumber);
                ArrayList<String> randomDatapoint = new ArrayList<String>();
                for(Integer attributeNumber : setOfAttributes)
                {
                    String attributeVal = datapoint.get(attributeNumber);
                    randomDatapoint.add(attributeVal);
                }
                randomDataSet.add(randomDatapoint);
            }

            Node treeOfTheForest = new Node("SingleTree", randomDataSet);

            treeOfTheForest.informationGain = calculateEntropy(randomDataSet, " ", "");
            System.out.println(treeOfTheForest.informationGain);
            ID3(treeOfTheForest);
            setOfTrees.add(treeOfTheForest);
        }
    }
}