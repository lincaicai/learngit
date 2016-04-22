import java.util.*;
public class TestVector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack al = new Stack();
		al.push("a");
		al.push("b");
		al.push("c");
		al.push("d");
		for(Iterator i = al.iterator();i.hasNext();)
		{
			
			String str = (String)i.next();
			System.out.println(str);
		}
		
		for(Iterator i = al.iterator();i.hasNext();)
		{
			
			String str1 = (String)al.pop();
			System.out.println(str1);
		}
		
	}

}
