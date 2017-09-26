public class Range{
ArrayList<ArrayList<String[]>> attributeRange = new ArrayList<ArrayList<String[]>>();


ArrayList<String[]> inner2 = new ArrayList<String[]>();
inner2.add("age");
inner2.add("continuous");
attributeRange.add(inner2); 

ArrayList<String[]> inner3 = new ArrayList<String[]>();
inner3.add("workclass");   //workclass: Private, Self-emp-not-inc, Self-emp-inc, Federal-gov, Local-gov, State-gov, Without-pay, Never-worked
inner3.add("Private");
inner3.add("Self-emp-not-inc");
inner3.add("Self-emp-inc");
inner3.add("Federal-gov");
inner3.add("Local-gov");
inner3.add("State-gov");
inner3.add("Without-pay");
inner3.add("cNever-worked");
attributeRange.add(inner3); 

ArrayList<String[]> inner4 = new ArrayList<String[]>();
inner4.add("fnlwgt");   //fnlwgt: continuous
inner4.add("continuous");
attributeRange.add(inner4); 

ArrayList<String[]> inner5 = new ArrayList<String[]>();
inner5.add("education");   //education: Bachelors, Some-college, 11th, HS-grad, Prof-school, Assoc-acdm, Assoc-voc, 9th, 7th-8th, 12th, Masters, 1st-4th, 10th, Doctorate, 5th-6th, Preschool
inner5.add("Bachelors");
inner5.add("Some-college");
inner5.add("11th");
inner5.add("HS-grad");
inner5.add("Prof-school");
inner5.add("Assoc-acdm");
inner5.add("Assoc-voc");
inner5.add("9th");
inner5.add("7th-8th");
inner5.add("12th");
inner5.add("Masters");
inner5.add("1st-4th");
inner5.add("10th");
inner5.add("Doctorate");
inner5.add("5th-6th");
inner5.add("Preschool");
//inner5.add("Bachelors");
attributeRange.add(inner5);

ArrayList<String[]> inner6 = new ArrayList<String[]>();
inner5.add("education-num");   //education-num: continuous.
inner5.add("continuous");
attributeRange.add(inner6); 

ArrayList<String[]> inner7 = new ArrayList<String[]>();
inner7.add("marital-status");   //marital-status: Married-civ-spouse, Divorced, Never-married, Separated, Widowed, Married-spouse-absent, Married-AF-spouse
inner7.add("Married-civ-spouse");
inner7.add("Divorced");
inner7.add("Never-married");
inner7.add("Widowed");
inner7.add("Married-spouse-absent");
inner7.add("Married-AF-spouse");
attributeRange.add(inner6);


ArrayList<String[]> inner8 = new ArrayList<String[]>();
inner8.add("occupation");   //occupation: Tech-support, Craft-repair, Other-service, Sales, Exec-managerial, Prof-specialty, Handlers-cleaners, Machine-op-inspct, Adm-clerical, Farming-fishing, Transport-moving, Priv-house-serv, Protective-serv, Armed-Forces
inner8.add("Tech-support");
inner8.add("Craft-repair");
inner8.add("Other-service");
inner8.add("Sales");
inner8.add("Exec-managerial");
inner8.add("Prof-specialty");
inner8.add("Handlers-cleaners");
inner8.add("Machine-op-inspct");
inner8.add("Adm-clerical");
inner8.add("Farming-fishing");
inner8.add("Transport-moving");
inner8.add("Priv-house-serv");
inner8.add("Protective-serv");
inner8.add("Armed-Forces");
attributeRange.add(inner8); 



ArrayList<String[]> inner9 = new ArrayList<String[]>();
inner9.add("relationship");   //relationship: Wife, Own-child, Husband, Not-in-family, Other-relative, Unmarried.
inner9.add("Wife");
inner9.add("Own-child");
inner9.add("Husband");
inner9.add("Not-in-family");
inner9.add("Other-relative");
attributeRange.add(inner9); 


ArrayList<String[]> inner10 = new ArrayList<String[]>();
inner10.add("race");   //race: White, Asian-Pac-Islander, Amer-Indian-Eskimo, Other, Black.
inner10.add("White");
inner10.add("Asian-Pac-Islander");
inner10.add("Amer-Indian-Eskimo");
inner10.add("Other");
inner10.add("Black");
attributeRange.add(inner10)



ArrayList<String[]> inner11 = new ArrayList<String[]>();
inner11.add("sex"); 
inner11.add("Female");   //sex: Female, Male.
inner11.add("Male");
attributeRange.add(inner11); 



ArrayList<String[]> inner12 = new ArrayList<String[]>();
inner12.add("capital-gain"); 
inner12.add("continuous");   //capital-gain: continuous.
attributeRange.add(inner12); 


ArrayList<String[]> inner13 = new ArrayList<String[]>();
inner13.add("capital-loss"); 
inner13.add("continuous");   //capital-loss: continuous.
attributeRange.add(inner13); 


ArrayList<String[]> inner14 = new ArrayList<String[]>();
inner11.add("hours-per-week"); 
inner11.add("continuous");   //capital-loss: continuous.
attributeRange.add(inner14); 



ArrayList<String[]> inner15 = new ArrayList<String[]>();
inner15.add("hours-per-week"); 
inner15.add("continuous");   //capital-loss: continuous.
attributeRange.add(inner15); 


ArrayList<String[]> inner16 = new ArrayList<String[]>();
inner16.add("native-country"); 
inner16.add("United-States");   //native-country: United-States, Cambodia, England, Puerto-Rico, Canada, Germany, Outlying-US(Guam-USVI-etc), India, Japan, Greece, South, China, Cuba, Iran, Honduras, Philippines, Italy, Poland, Jamaica, Vietnam, Mexico, Portugal, Ireland, France, Dominican-Republic, Laos, Ecuador, Taiwan, Haiti, Columbia, Hungary, Guatemala, Nicaragua, Scotland, Thailand, Yugoslavia, El-Salvador, Trinadad&Tobago, Peru, Hong, Holand-Netherlands.
inner16.add("Cambodia"); 
inner16.add("England");
inner16.add("Puerto-Rico");
inner16.add("Canada");
inner16.add("Germany");
inner16.add("Outlying-US(Guam-USVI-etc)");
inner16.add("India");
inner16.add("Japan");
inner16.add("Greece");
inner16.add("South");
inner16.add("China");
inner16.add("Cuba");
inner16.add("Iran");
inner16.add("Honduras");
inner16.add("Philippines");
inner16.add("Italy");inner16.add("Poland");
inner16.add("Jamaica");
inner16.add("Vietnam");inner16.add("Mexico");
inner16.add("Portugal");
inner16.add("Ireland");
inner16.add("France");
inner16.add("Dominican-Republi");
inner16.add("Laos");
inner16.add("Ecuador");
inner16.add("Taiwan");
inner16.add("Haiti");
inner16.add("Columbia");inner16.add("Hungary");
inner16.add("Guatemala");
inner16.add("Nicaragua");
inner16.add("Scotland");
inner16.add("Thailand");
inner16.add("Yugoslavia");

inner16.add("El-Salvador");
inner16.add("Trinadad&Tobago");
inner16.add("Peru");
inner16.add("Hong");
inner16.add("Holand-Netherlands");
attributeRange.add(inner16); 
}

