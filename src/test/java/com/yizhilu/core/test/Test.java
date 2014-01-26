package com.yizhilu.core.test;
import com.google.gson.JsonObject;

public class Test {
	public static void main(String[] args) {
		JsonObject json = new JsonObject();
		json.addProperty("12", "dfdsf");
		json.addProperty("error", 0);
		System.out.println( ""+json);

	}
}
