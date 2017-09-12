
public class Attribute {
	
	public String attr_name;
	public int attr_val;
	
	public Attribute(String attr_name, int attr_val){
		this.attr_name = attr_name;
		this.attr_val = attr_val;
	}
	
	public void deepCopy(Attribute attribute){
		this.attr_name = attribute.attr_name;
		this.attr_val = attribute.attr_val;
	}
	
	public String getName(){
		return this.attr_name;
	}
	
	public int getValue(){
		return this.attr_val;
	}
	
	public void setName(String attr_name){
		this.attr_name = attr_name;
	}
	
	public void setValue(int attr_val){
		this.attr_val = attr_val;
	}
}
