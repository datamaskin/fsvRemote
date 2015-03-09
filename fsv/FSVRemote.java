/**
 * FSVRemote.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fsv;

public interface FSVRemote extends javax.xml.rpc.Service {
    public java.lang.String getFSVRemoteSoapPortAddress();

    public fsv.FSVRemoteSoapPort getFSVRemoteSoapPort() throws javax.xml.rpc.ServiceException;

    public fsv.FSVRemoteSoapPort getFSVRemoteSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
