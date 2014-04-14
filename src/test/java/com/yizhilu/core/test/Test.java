package com.yizhilu.core.test;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

public class Test {
	public static void main(String[] args) {
		/*JsonObject json = new JsonObject();
		json.addProperty("12", "dfdsf");
		json.addProperty("error", 0);
		System.out.println( ""+json);
*/
	 /*   
	    String photoName = "/dfd/dfdf/dfdfdaa/a099888.jpg"
                .substring("/dfd/dfdf/dfdfdaa/a099888.jpg".lastIndexOf("/")+1);
	    System.out.println(photoName);
        photoName = FileUtil.getRandomFileNameString(photoName);
        System.out.println(photoName);*/
        
	    DateTime dateTime = new DateTime();
	    System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
	    System.out.println(DateTimeUtils.currentTimeMillis());
	    
	}
}
