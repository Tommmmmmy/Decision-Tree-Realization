import java.util.ArrayList;
import java.util.List;

public class Point {
	public Point[] children;
	public List<List<Attribute>> dataList;
	public int attr_index;
	public double remainEntropy;
	public int result;
	public Attribute attribute;
	
	public Point(){
		children = null;  //left and right
		dataList = new ArrayList<>();
		attr_index = -1;
		remainEntropy = 0;
		result = 0;
		attribute = new Attribute("", 0);
	}
	
//	public Point(Point root)
//	{
//		if(root.children!=null){
//		for(int i=0;i<=1;i++)
//		{
//			if(root.children[i]!=null){
//			this.children[i]=new Point(root.children[i]);
//			}
//		}
//		}
//		this.dataList=new ArrayList<>(root.dataList);
//		this.attr_index=root.attr_index;
//		this.remainEntropy=root.remainEntropy;
//		this.result=root.result;
//		this.attribute=new Attribute(root.attribute.attr_name,root.attribute.attr_val);
//		
//	}
	
	public void setDataList(List<List<Attribute>> list){
		for(int i = 0; i < list.size(); i++){
			List<Attribute> temp = new ArrayList<>();
			for(int j = 0; j < list.get(i).size(); j++){
				temp.add(new Attribute(list.get(i).get(j).attr_name, list.get(i).get(j).attr_val));
			}
			this.dataList.add(temp);
		}
	}
	
	public void setIndex(int attr_index){
		this.attr_index = attr_index;
	}
	
	public void setEntropy(double entropy){
		this.remainEntropy = entropy;
	}
	
	public void setResult(int result){
		this.result = result;
	}
	
	public void setAttribute(Attribute attribute){
		this.attribute.deepCopy(attribute);
	}
	
	 public Point deepCopy(){
		Point point = new Point();
		point.setIndex(this.attr_index);
		point.setEntropy(this.remainEntropy);
		point.setResult(this.result);
		point.setAttribute(this.attribute);
		point.setDataList(this.dataList);
		if(this.children != null){
			point.children = new Point[2];
			if(this.children[0] != null){
				point.children[0] = this.children[0].deepCopy();
			}
			if(this.children[1] != null){
				point.children[1] = this.children[1].deepCopy();
			}
		}
		return point;
		}
}
