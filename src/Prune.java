import java.util.List;
import java.util.Random;

public class Prune {
	public Point point;
	public Point postPrune(int L, int K, List<List<Attribute>> list, Point root, int number){
		Point result = root.deepCopy();
		for(int i = 1; i <= L; i++){
			point = root.deepCopy();
			Random random = new Random();
			int M = random.nextInt(K);
			for(int j = 1; j <= M; j++){
				int P = random.nextInt(number);
				postPruneHelper(point,P);
			}
//			System.out.println(validation(list,point));
//			System.out.println(validation(list,result));
			if(validation(list,point) > validation(list,result)){
				result = point.deepCopy();
			}
		}
		return result;
	}
	public void postPruneHelper(Point root, int index){
		if(root == null){
			return;
		}
		if(root.attr_index == index){
			int[] classCount = new int[2];
			for(List<Attribute> list : root.dataList){
				if(list.get(list.size()-1).attr_val == 0){
					classCount[0]++;
				}
				else{
					classCount[1]++;
				}
			}
			if(classCount[0] > classCount[1]){
				root.children = null;
				root.attr_index = -1;
				root.result = 0;
			}
			else{
				root.children = null;
				root.attr_index = -1;
				root.result = 1;
			}
			return;
		}
		else if(root.children != null){
			postPruneHelper(root.children[0],index);
			postPruneHelper(root.children[1],index);
		}
	}
	
	public double validation(List<List<Attribute>> list, Point root){
		double result = 0;
		for(List<Attribute> tuple : list){
			if(validationHelper(tuple, root)){
				result++;
			}
		}
		return result * 100 / list.size();
	}
	
	public static boolean validationHelper(List<Attribute> tuple, Point root){
		if(root == null){
			return false;
		}
		if(root.children == null && tuple.get(tuple.size()-1).attr_val == root.result){
			return true;
		}
		else if(root.children == null && tuple.get(tuple.size()-1).attr_val != root.result){
			return false;
		}
		int index = -1;
		for(int i = 0; i < tuple.size()-1; i++){
			if(root.attribute.attr_name.equals(tuple.get(i).attr_name)){
				index = i;
				break;
			}
		}
		if(index != -1){
			if(tuple.get(index).attr_val == 0){
				return validationHelper(tuple, root.children[0]);
			}
			else{
				return validationHelper(tuple, root.children[1]);
			}
		}
		return false;
	}
}
