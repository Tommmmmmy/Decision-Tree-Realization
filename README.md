# Decision-Tree-Realization
# Maoming Tang
# mxt162330

# This application has 5 classes in total:

- Attribute: the type of each element in the arraylist
  - attr_name: the name of attribute of the element
  - attr_val: the value of attribute of the element
  - deepCopy(): copy the attr_name and attr_val to a new Attribute instance
  - getName(): return the name of attribute of the element
  - getValue(): return the value of attribute of the element
  - setName(): set the name of attribute of the element
  - setValue(): set the value of attribute of the element

- Point: the type of each node in the decision tree
  - Point[]: an array of type Point that contains the children Point of this Point
  - dataList: the remaining examples after each extraction that are stored in Point 
  - attr_index: number the Point
  - remainEntropy: the entropy of dataList
  - result: if the Point instance is a leaf, then the result is assigned as the classification
  - attribute: the attribute that has been chosen to be the new root
  - setDataList(List<List<Attribute>> list)
  - setIndex(int attr_index)
  - setEntropy(double entropy)
  - setResult(int result)
  - setAttribute(Attribute attribute)
  - deepCopy(): copy everything from this Point to a Point

- Tree: where the main class is
  - buildTree(): create a tree recursively according to information gain or variance impurity
  - print(): print the tree as asked

- Prune: post-pruning and calculating accuracy

- Parser: read csv file and calculate entropy, information gain and variance impurity

# How to run it in cmd:
Firstly, get into the directory where the java file that contains the main class is. Then type "javac Tree.java" and this java file will be compiled. Finally, type "java Tree L K <source> <source> <source> toPrint".
