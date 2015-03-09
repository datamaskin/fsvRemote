/**
 * FSVRemoteSoapPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fsvremote;

public interface FSVRemoteSoapPort extends java.rmi.Remote {
    public java.lang.String transact(java.lang.String userid, java.lang.String password, java.lang.String paramstr) throws java.rmi.RemoteException;
    public java.lang.String getversion() throws java.rmi.RemoteException;
}
