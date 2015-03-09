package fsvremote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetSocketAddress;
//import java.net.IllegalArgumentException;
import java.net.SocketTimeoutException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

//For logging
//import org.apache.commons.logging.Log;
//import org.apache.axis.components.logger.LogFactory;
import org.apache.commons.logging.*;

public class BackEndServiceHelper {
		//	For Logging
	private static Log log = LogFactory.getLog(BackEndServiceHelper.class);
	
	private Config config = null;
	private String host;
	private int port;
	private String chIntfId;
	private String errorString;
	
	BackEndServiceHelper(){
		host = null;
		port = 0;
		chIntfId = null;
		config = Config.getInstance();
		errorString = new String();
	}
	
	public boolean getDBParams(String uid, String passwd){
		  boolean res = false;
		  Connection conn = null;
		  String url = new String();
		 
		  try {
			  Class.forName(config.getDbAccessDriver());
		  }catch(Exception e){
			  log.error("Can not load DB Driver", e);
		  }
		
		  if (config.getDbAccessConenctionURL().length() != 0){
			  url = config.getDbAccessConenctionURL();
		  }else{
			  log.error("set the ConnectionURL in the configfile!!");
		  }
		 
		  log.info ("Using uid : "+ uid + " Password : " + passwd );
		  log.debug("Using  URL : \n" + url);
		  
		  try {
				OracleDataSource ods = new OracleDataSource();
				ods.setUser(uid);
				ods.setPassword(passwd);
				ods.setURL(url);

				conn = ods.getConnection();
		  }catch(SQLException e){
			  log.error("Can not connect too Database");
			  setErrorString("User ID is not set up to access transaction.");
			  return false;
		  }
		 
	     try {			
			String query = config.getSelectQuery();
			// Create a statement
			PreparedStatement pStmt = conn.prepareStatement(query);
			//Padd with blank space for portability of CHAR(15) on Oracle.
			String newUid = new String(uid);
			for ( int start = uid.length(); start < 15; start++){
				newUid = newUid + " ";
			}
			log.debug("New Uid : " + newUid + " Length is : " + newUid.length());
			pStmt.setString(1, newUid);
			log.debug("Just Before ExecuteQuery");
			ResultSet rset = pStmt.executeQuery();
			while (rset.next()) {
				log.debug("Inside while ");
				host = rset.getObject(1).toString();
				port = new Integer(rset.getObject(2).toString()).intValue();
				chIntfId = rset.getObject(3).toString();
				log.debug("VCHost : " + host + " VCPort : " + port
						+ " CHInterfaceID : " + chIntfId);
			}
			
			setHost(host);
			setPort(port);
			setChIntfId(chIntfId);
			
			rset.close();
			pStmt.close();
			conn.close();
			res = true;
		} catch (SQLException e) {
			log.error(e.getMessage());			    
		}
	    return res;
	  }
	

	  
	 public String getResult(String paramStr){
		 String result = null; 
		 String dataTobeSent = null;
	     int dataSize = 0;
	     String dataSizeStr = null;
	    // Socket serverSocket =  new Socket();;
	      Socket serverSocket =  new Socket();;

	     InetSocketAddress inetAddress = null;
	     PrintWriter out = null;
	     BufferedReader in = null;
	     boolean connectionError = false;
	 
	     //create an InetSocketAddress
	     try{
	    	 log.debug("Using host : " + getHost() + " And Port :"+getPort() + "Interface Id : " + getChIntfId());
	         inetAddress = new InetSocketAddress(getHost(),getPort());
	     }catch(IllegalArgumentException ex){
	    	 log.error("Either HostName or Port is passed as null\n" +
	    			 "It can not resolve the hostname.");
	    	 log.error(ex.getMessage());
	     }catch(SecurityException ex){
	    	 log.error("It can not connect to the host because of security issues.\n" +
	    			 "See your local admin");
	    	 log.error(ex.getMessage());
	     }
	     //h2-fsvp-sdb01 -p 59225922
	     try{
	    	 serverSocket = new Socket();
	    	 serverSocket.connect(inetAddress, new Integer(config.getSocketTimeOut()).intValue());
	    	 //serverSocket = new Socket("h2-fsvp-sdb01",5922);
	     }catch(SocketTimeoutException  e){
	    	 log.error("Connection TimeOut.);");
	    	 log.error(e.getMessage());
	    	 connectionError = true;
	     }catch(IOException e){
	    	 log.error("Server Connection error" + e.toString());
	    	 log.error(e.getMessage());
	    	 connectionError = true;
	     }catch(IllegalArgumentException e){
	    	 log.error("Server Connection error<IllegalArgumentException>");
	    	 log.error(e.getMessage());
	    	 connectionError = true;
	     }
	     if ( connectionError){
	    	 return "Socket connect error - Negative connect return";
	     }
	     
	     try{
	    	 out = new PrintWriter(serverSocket.getOutputStream(), true);
	     }catch(IOException e){
	    	 log.error("IO Error " + e.getMessage());
	    	 return "Socket connect error - Negative connect return";
	     }
	     try{
	    	 in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
	     }catch(IOException e){
	    	 log.error(e.getMessage());
	     }
	     
	     dataTobeSent = getChIntfId().toString() + "," + paramStr;
	     
	     dataSize = dataTobeSent.length();
	     
	     if (dataSize < 9996){
	    	 if (dataSize <10)
	    		 dataSizeStr = "000" + new Integer(dataSize).toString();
	    	 else if (dataSize < 100)
	    		 dataSizeStr = "00" + new Integer(dataSize).toString();
	    	 else if (dataSize < 1000)
	    		 dataSizeStr = "0" + new Integer(dataSize).toString();
	    	 else
	    		 dataSizeStr = new Integer(dataSize).toString();
	     }else {
	    	 return "Input buffer is too big";
	     }
	     //sInterface & "," & paramstr
	    // dataTobeSent = dataSizeStr + paramStr;
	     dataTobeSent = dataSizeStr + dataTobeSent;
	     
	     log.debug("Passed to server: " + dataTobeSent);
	     out.println(dataTobeSent);
	     String temp = null;
	     try{
	    	 //This is for non blockign read() operation
	    	 serverSocket.setSoTimeout(new Integer(config.getSocketReadTimeOut()).intValue());
	    	 while (( temp = in.readLine()) != null ){
	    		 result = temp;
	    		 log.debug("Server Response: " + result);
	    	 }
	     }catch(IOException e){
	    	 log.error("Something wrong happened!! Backend could not process the request ", e);
	     }
	     out.close();	      
	     try {
	    	 in.close();
	    	 serverSocket.close();
	     } catch (IOException e) {
	    	 log.error(e.getMessage());
	     }	      
	     return result;
	  }
	 public String doTheJob(String uid, String passwd, String paramStr){
		  String result = null;
		  if (getDBParams(uid,passwd)){
			  result = getResult(paramStr);
		  }else{
			  return getErrorString();
		  }
		  return result;
	  }
	/**
	 * @return the chIntfId
	 */
	public String getChIntfId() {
		return chIntfId;
	}
	/**
	 * @param chIntfId the chIntfId to set
	 */
	public void setChIntfId(String chIntfId) {
		this.chIntfId = chIntfId;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setErrorString(String str){
		this.errorString = str;
	}
	
	public String getErrorString(){
		return errorString;
	}
	public static void main (String args []) throws Exception
	  {
		  String fromUser = "8036000000000200,2222,3801,Verify FSV SOAP Connection Services";
		  //String fromUser = "6036336000121205,1234,1216,6036336000121205";
		  //String fromUser = "6036336000121205,1234,2956,6036336000121189,1234,6036336000231731,20070608,06/11/07,132.45,132.45,41.2300,.0000,.00,.00,292.78,292.78,53.67,53.67,106.66,106.66,.00,.00,6500,35,19.09,19.09,18.15,18.15,4.25,4.25,W,CA- State In,8.99,8.99,W,CA State Un,.26,.26,W,SUGAR WAY,2.93,2.93,E,Back Pay Reg,20.9300,2.83,59.24,,E,Back Pay Reg,20.3000,6.25,126.88,186.12,E,Tips Claimed,,,106.66,106.66,D,Tips Claimed,106.66,106.66,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,,";
		  //String fromUser = "6036336000121205,1234,1252,6036336000250145,200707040000,200705170000,1,0,,";
		  //String fromUser = "6036336000121197,1234,2915,6036336000113681,1234,6036336000121239,0.05,SOAP test,SOAP test";
		  
		  String uid = "DBROWNDEV";
		  //String uid = "MACALUSODEV";
		  //String passwd = "fsmd8125";
		  String passwd = "LUTEFISK";
	      String output = null;
	      
	      BackEndServiceHelper tod = new BackEndServiceHelper();
	      
	      try {
	      output = tod.doTheJob(uid, passwd, fromUser);
	      }catch ( Exception e){
	    	  log.error("Unableto Perform!!!", e);
	      }
	      log.debug("Received Response :" + output);
	  }
	}