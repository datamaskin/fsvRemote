/**
 * FSVRemoteLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fsvremote;

public class FSVRemoteLocator extends org.apache.axis.client.Service implements fsvremote.FSVRemote {

    public FSVRemoteLocator() {
    }


    public FSVRemoteLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FSVRemoteLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FSVRemoteSoapPort
    private java.lang.String FSVRemoteSoapPort_address = "http://TECH-PC06:8080/axis2/services/FSVRemote.wsdl";

    public java.lang.String getFSVRemoteSoapPortAddress() {
        return FSVRemoteSoapPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FSVRemoteSoapPortWSDDServiceName = "FSVRemoteSoapPort";

    public java.lang.String getFSVRemoteSoapPortWSDDServiceName() {
        return FSVRemoteSoapPortWSDDServiceName;
    }

    public void setFSVRemoteSoapPortWSDDServiceName(java.lang.String name) {
        FSVRemoteSoapPortWSDDServiceName = name;
    }

    public fsvremote.FSVRemoteSoapPort getFSVRemoteSoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FSVRemoteSoapPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFSVRemoteSoapPort(endpoint);
    }

    public fsvremote.FSVRemoteSoapPort getFSVRemoteSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            fsvremote.FSVRemoteSoapBindingStub _stub = new fsvremote.FSVRemoteSoapBindingStub(portAddress, this);
            _stub.setPortName(getFSVRemoteSoapPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFSVRemoteSoapPortEndpointAddress(java.lang.String address) {
        FSVRemoteSoapPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (fsvremote.FSVRemoteSoapPort.class.isAssignableFrom(serviceEndpointInterface)) {
                fsvremote.FSVRemoteSoapBindingStub _stub = new fsvremote.FSVRemoteSoapBindingStub(new java.net.URL(FSVRemoteSoapPort_address), this);
                _stub.setPortName(getFSVRemoteSoapPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FSVRemoteSoapPort".equals(inputPortName)) {
            return getFSVRemoteSoapPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/FSVRemote/wsdl/", "FSVRemote");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/FSVRemote/wsdl/", "FSVRemoteSoapPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FSVRemoteSoapPort".equals(portName)) {
            setFSVRemoteSoapPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
