import java.util.ArrayList;
import java.util.List;

public class Tree {
	
	int num = 0;

	public Point buildTree(List<List<Attribute>> list, Point root, int means){
		 int index = -1;
		 List<List<Attribute>> leftTuples = new ArrayList<>();
		 List<List<Attribute>> rightTuples = new ArrayList<>();
		 double bestGain = 0;
		 Parser parser = new Parser();
		 Attribute attribute = new Attribute("", -1);
		 root.setDataList(list);;
		 if(means == 0){
			 root.setEntropy(parser.calculateEntropy(list));
		 }
		 else if(means == 1){
			 root.setEntropy(parser.calculateImpurity(list));
		 }
		 for(int i = 0; i < list.get(0).size() - 1; i++){  //All columns except the last one which is classification
			 List<List<Attribute>> leftOnes = new ArrayList<>();
			 List<List<Attribute>> rightOnes = new ArrayList<>();
			 for(int j = 0; j < list.size(); j++){
				 if(list.get(j).get(i).attr_val == 0){
					 leftOnes.add(list.get(j));
				 }
				 else{
					 rightOnes.add(list.get(j));
				 }
			 }
			 ArrayList<Double> childrenEntropy = new ArrayList<>();
			 if(means == 0){
				 childrenEntropy.add(parser.calculateEntropy(leftOnes));
				 childrenEntropy.add(parser.calculateEntropy(rightOnes));
			 }
			 else if(means == 1){
				 childrenEntropy.add(parser.calculateImpurity(leftOnes));
				 childrenEntropy.add(parser.calculateImpurity(rightOnes));
			 }
			 ArrayList<Integer> childrenDataSize = new ArrayList<>();
			 childrenDataSize.add(leftOnes.size());
			 childrenDataSize.add(rightOnes.size());
			 double gain = parser.calculateGain(root.remainEntropy, childrenEntropy, childrenDataSize, list.size());
//			 System.out.println(gain);
			 if(gain > bestGain){
				 index = i;
				 attribute.deepCopy(list.get(0).get(i));
				 bestGain = gain;
				 leftTuples = leftOnes;
				 rightTuples = rightOnes;
			 }
		 }
		 if(index != -1){
			 for(int j = 0; j < leftTuples.size(); j++){
				 leftTuples.get(j).remove(index);
			 }
			 for(int j = 0; j < rightTuples.size(); j++){
				 rightTuples.get(j).remove(index);
			 }
			 Point leftChild = new Point();
			 Point rightChild = new Point();
			 root.attribute.deepCopy(attribute);
			 root.attr_index = ++this.num; 
			 root.children = new Point[2];
			 root.children[0] = buildTree(leftTuples, leftChild, means);
			 root.children[1] = buildTree(rightTuples, rightChild, means);
		 }
		 else{
			 root.setResult(list.get(0).get(list.get(0).size() - 1).attr_val);//only one example left
			 return root;//the final classification
		 }
		 return root;
	}
	
	public String print(Point root, int level){
		StringBuilder sb = new StringBuilder();
		if(root.children != null){
		for(int i = 0; i < root.children.length; i++){
			sb.append("\n");
			for(int j = 0; j < level; j++){
				sb.append("| ");
			}
			sb.append(root.attribute.attr_name + " = " + i + " : ");
			if(root.children[i] != null){
				sb.append(print(root.children[i], level + 1));
			}
		}
	   }
		else{
			sb.append(root.result);
		}
		return sb.toString();
	}
	
	public static void main(String[] args){
		Parser parser = new Parser();
		List<List<Attribute>> training_set = parser.Parse(args[2]);
		List<List<Attribute>> test_set = parser.Parse(args[3]);
		List<List<Attribute>> validation_set = parser.Parse(args[4]);
//		for(List<Attribute> tuple: training_set){
//			for(Attribute attribute: tuple){
//				System.out.print(attribute.attr_name);
//				System.out.print(attribute.attr_val);
//				System.out.print(" ");
//			}
//			System.out.println();
//		}
//		double entropy = parser.calculateImpurity(training_set);
//		System.out.println(entropy);
		Tree tree1 = new Tree();
		Point root1 = new Point();
		root1 = tree1.buildTree(training_set, root1,0);
		Prune prune1 = new Prune();
		System.out.println("Using Information Gain && Before Pruning :" + prune1.validation(test_set, root1));
		if(args[5].equals("yes"))System.out.println(tree1.print(root1,0));
		root1 = prune1.postPrune(Integer.valueOf(args[0]), Integer.valueOf(args[1]), validation_set, root1, tree1.num);
		System.out.println("Using Information Gain && After Pruning :" + prune1.validation(test_set, root1));
		if(args[5].equals("yes"))System.out.println(tree1.print(root1,0));

		Tree tree2 = new Tree();
		Point root2 = new Point();
		training_set = parser.Parse(args[2]);
		root2 = tree2.buildTree(training_set, root2, 1);
		Prune prune2 = new Prune();
		System.out.println("Using Variance Impurity && Before Pruning :" + prune2.validation(test_set, root2));
		if(args[5].equals("yes"))System.out.println(tree2.print(root2,0));
		root2 = prune2.postPrune(Integer.valueOf(args[0]), Integer.valueOf(args[1]), validation_set, root2, tree2.num);
		System.out.println("Using Variance Impurity && After Pruning :" + prune2.validation(test_set, root2));
		if(args[5].equals("yes"))System.out.println(tree2.print(root2,0));
	}
}
