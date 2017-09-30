public class ID3{

  Node ID3(Node parent)
  {
      if(parent.reducedDataSet== null)
      {
        parent.finalyReturnValue="empty";  /// build a new leaf node with label "Yes",
      }
      else if(parent.checkAllPositive())
      {
        parent.finalReutrnValue="Yes";   /// build a new leaf node with label "Yes",
      }
      else if(parent.checkAllNegative())
      {
        parent.finalReutrnValue="No";   /// /// build a new leaf node with label "No",
      }
      else
      {
        String maxAttributeName=parent.calculateMaxIgAttribute();
        ArrayList<String> attributeVariety=attributeRangeHashMap.get(maxAttributeName);
        for(int j=0;j<attributeVariety.size();j++)
        {
            Node child=new Node(maxAttributeName + ":" + attributVariety.get(j),parent.obtainReducedDataset(maxAttributeName,j));
            parent.children.add(child);
            ID3(child);
        }
      }
  }
}
