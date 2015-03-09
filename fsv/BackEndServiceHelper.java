package fsv;

import fsvremote.Config;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.axis.components.logger.LogFactory;
import org.apache.commons.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.net.IllegalArgumentException;
//For logging

public class BackEndServiceHelper {
		//	For Logging
	private static Log log = LogFactory.getLog("BackEndServiceHelper");
	
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
		  log.info("Using  URL : \n" + url);
		  
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
			log.info("New Uid : " + newUid + " Length is : " + newUid.length());
			pStmt.setString(1, newUid);
			log.info("Just Before ExecuteQuery");
			ResultSet rset = pStmt.executeQuery();
			while (rset.next()) {
				log.info("Inside while ");
				host = rset.getObject(1).toString();
				port = new Integer(rset.getObject(2).toString()).intValue();
				chIntfId = rset.getObject(3).toString();
				log.info("VCHost : " + host + " VCPort : " + port
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
	     Socket serverSocket = null;
	     InetSocketAddress inetAddress = null;
	     PrintWriter out = null;
	     BufferedReader in = null;
	     boolean connectionError = false;
	 
	     //create an InetSocketAddress
	     try{
	    	 log.info("Using host : " + getHost() + " And Port :"+getPort() + "Interface Id : " + getChIntfId());
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
	     
	     try{
	    	 serverSocket = new Socket();
	    	 serverSocket.connect(inetAddress, new Integer(config.getSocketTimeOut()).intValue());
	     }catch(SocketTimeoutException  e){
	    	 log.error("Connection TimeOut.);");
	    	 log.error(e.getMessage());
	    	 connectionError = true;
	     }catch(IOException e){
	    	 log.error("Server Connection error");
	    	 log.error(e.getMessage());
	    	 connectionError = true;
	     }catch(IllegalArgumentException e){
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
	     dataSize = paramStr.length();
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

	     dataTobeSent = dataSizeStr + paramStr;

	     log.info("Passed to server: " + dataTobeSent);
	     out.println(dataTobeSent);
	     String temp = null;
	     try{
	    	 //This is for non blockign read() operation
	    	 serverSocket.setSoTimeout(new Integer(config.getSocketReadTimeOut()).intValue());
	    	 while (( temp = in.readLine()) != null ){
	    		 result = temp;
	    		 log.info("Server Response: " + result);
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
		 // String uid = "MACALUSODEV";
		  String uid = "MACALUSODEV";
		  String passwd = "fsmd8125";
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