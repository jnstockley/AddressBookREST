package mysql;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


public class Helper {

	private final static String INTERNALIP = "10.0.0.173";
	private final static String EXTERNALIP = "jackstockley.ddns.net";
	private final static String PORT = "3360";
	
	public static String getInternalIP(){
		return INTERNALIP;
	}
	
	public static String getExternalIP(){
		return EXTERNALIP;
	}
	
	public static String getPort(){
		return PORT;
	}
	
	public static Response getNullResponse(){
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	public static Response getJSONResponse(Person output){
		Gson json = new Gson();
		return Response.ok(json.toJson(output), MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
	}
}
