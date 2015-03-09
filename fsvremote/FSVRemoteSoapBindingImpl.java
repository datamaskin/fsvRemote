/**
 * FSVRemoteSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fsvremote;

//For logging
//import org.apache.commons.logging.Log;
//import org.apache.axis.components.logger.LogFactory;
import org.apache.commons.logging.*;

public class FSVRemoteSoapBindingImpl implements FSVRemoteSoapPort{
  
//	For Logging
	private Log log = LogFactory.getLog(FSVRemoteSoapBindingImpl.class);
		
	public java.lang.String transact(java.lang.String userid, java.lang.String password, java.lang.String paramstr) throws java.rmi.RemoteException {
    	log.debug("transact(..) : just got called!!");
    	log.debug("Received the following Parameters..");
    	log.debug("userid :" + userid);
    	log.debug("password :" + password);
    	log.debug("paramstr :" + paramstr);

    	log.debug("Received Parameters:" + paramstr);
    	
    	String ret = "Success!!";
    	BackEndServiceHelper tod = new BackEndServiceHelper();
	      
	      try {
	    	  ret = tod.doTheJob(userid, password, paramstr);
	      }catch ( Exception e){
	    	  log.error("Not able to do the Job!!", e);
	      }
	      log.debug("Received Response :" + ret);
    	   	
    	return ret;
    }

    public java.lang.String getversion() throws java.rmi.RemoteException {
    	String str =  "Version1.0";
    	log.debug("getversion() : just got called!!");
    	return str;
    }    
}
