/**
 * FSVRemoteSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fsv;

//For logging
import org.apache.commons.logging.Log;
import org.apache.axis.components.logger.LogFactory;

public class FSVRemoteSoapBindingImpl implements fsv.FSVRemoteSoapPort{
  
//	For Logging
	private static Log log = LogFactory.getLog("FSVRemoteSoapBindingImpl");
		
	public java.lang.String transact(java.lang.String userid, java.lang.String password, java.lang.String paramstr) throws java.rmi.RemoteException {
    	log.debug("transact(..) : just got called!!");
    	log.debug("Received the following Parameters..");
    	log.debug("userid :" + userid);
    	log.debug("password :" + password);
    	log.debug("paramstr :" + paramstr);

    	log.info("Received Parameters:" + paramstr);
    	
    	String ret = "Success!!";
    	BackEndServiceHelper tod = new BackEndServiceHelper();
	      
	      try {
	    	  ret = tod.doTheJob(userid, password, paramstr);
	      }catch ( Exception e){
	    	  log.error("Not able to do the Job!!", e);
	      }
	      log.info("Received Response :" + ret);
    	   	
    	return ret;
    }

    public java.lang.String getversion() throws java.rmi.RemoteException {
    	String str =  "Version1.0";
    	log.info("getversion() : just got called!!");
    	return str;
    }    
}
