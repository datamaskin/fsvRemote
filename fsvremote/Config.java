package fsvremote;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.*;


public class Config {
	static Config conf = null;
	private Log log = LogFactory.getLog(Config.class);
	
	private String configFile = System.getenv("WS_FSVREMOTE_CONFIG");		
	private XMLConfiguration config;
	
	
	public String getDbAccessConenctionURL(){
		log.debug(" getDbAccessConenctionURL Query String :" + config.getString("fsvremote.DBAccess.connectionURL"));
		return config.getString("fsvremote.DBAccess.connectionURL");
	}
	public String getDbAccessDriver(){
		log.debug(" getDbAccessDriver Query String :" + config.getString("fsvremote.DBAccess.driverClass"));
		return config.getString("fsvremote.DBAccess.driverClass");
	}
	public String getSelectQuery(){
		log.debug(" getSelectQuery String :" + config.getString("fsvremote.DBAccess.selectQuery"));
		return config.getString("fsvremote.DBAccess.selectQuery");
	}
	public String getSocketTimeOut(){
		log.debug(" getSocketTimeOut Query String :" + config.getString("fsvremote.Socket.timeout"));
		return config.getString("fsvremote.Socket.timeout");
	}
	public String getSocketReadTimeOut(){
		log.debug(" getSocketTimeOut Query String :" + config.getString("fsvremote.Socket.readTimeOut"));
		return config.getString("fsvremote.Socket.readTimeOut");
	}
	
	private Config(){
				
		try
		{
			if (configFile == null){
				log.debug("Loading Config file from env variable.." + configFile);
				//configFile = "/home/gpatra/AppConfig/fsvremote.xml";
				configFile = "C:/Documents and Settings/davidb/My Documents/dev/FSVRemoteProjec/fsvremote/fsv/fsvremote.xml";
				//log.debug("Loading Config file.." + configFile);
			}
			log.debug("Loading Config file.." + configFile);
		    
			config = new XMLConfiguration(configFile);
		    // do something with config
		}
		catch(ConfigurationException cex)
		{
		    // something went wrong, e.g. the file was not found
			log.fatal("Config File Not Found" + cex.getMessage());
			log.fatal("Please check the env variable <WS_FSVREMOTE_CONFIG>");
			log.fatal(cex.getStackTrace());
		}
		
	}
	
	public static synchronized Config getInstance(){
		if ( conf == null){
			conf = new Config();
		}
		return conf;
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Config con = Config.getInstance();
		System.out.println(" getSocketTimeOut Query is : " + con.getSocketTimeOut());
		System.out.println(" getDbAccessConenctionURL Query is : " + con.getDbAccessConenctionURL());
		System.out.println(" getDbAccessDriver Query is : " + con.getDbAccessDriver());
		System.out.println(" getSelectQuery is : " + con.getSelectQuery());
		
	}
}
