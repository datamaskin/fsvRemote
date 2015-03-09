package test;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import fsvremote.FSVRemoteLocator;
import fsvremote.FSVRemoteSoapPort;

public class FsvRemoteTest {
	
	String test(String url, String userid, String password, String paramstr){
		String temp = null;
		
		FSVRemoteLocator fsvLoc = 	new FSVRemoteLocator();
		fsvLoc.setFSVRemoteSoapPortEndpointAddress(url);
		FSVRemoteSoapPort fsv = null;
		try {
			fsv = fsvLoc.getFSVRemoteSoapPort();
			temp = fsv.transact(userid, password, paramstr);
		} catch (ServiceException e) {
			System.out.println("Service Exception: " + e.getMessage());
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("RemoteException: " + e.getMessage());
			e.printStackTrace();
		}		
		return temp;
	}
	/**
	 * @param args
	 *  String fromUser = "8036000000000200,2222,3801,Verify FSV SOAP Connection Services";
		 // String uid = "MACALUSODEV";
		  String uid = "MACALUSODEV";
		  String passwd = "fsmd8125";
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String URL = "http://10.226.168.4/axis2/services/FSVRemote";

		String URL = "http://tech-pc06:8080/axis2/services/FSVRemote";
		
		//String URL = "http://localhost/axis/services/FSVRemote.wsdl";
		//String uid = "GPATRADEV";
		//String password = "BABY1234";
		String uid = "DBROWNDEV";
		String password = "LUTEFISK";
		
		String paramstr = "6036336000121197,1234,1216,6036336000121205";
		FsvRemoteTest test = new FsvRemoteTest();
		String result = test.test(URL, uid, password, paramstr);
		System.out.println("Result is : " + result);
	}
}
