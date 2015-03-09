/**
 * FSVRemoteSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fsv;

public class FSVRemoteSoapBindingSkeleton implements fsv.FSVRemoteSoapPort, org.apache.axis.wsdl.Skeleton {
    private fsv.FSVRemoteSoapPort impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "paramstr"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("transact", _params, new javax.xml.namespace.QName("", "Result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://tempuri.org/FSVRemote/message/", "Transact"));
        _oper.setSoapAction("http://tempuri.org/FSVRemote/action/FSVRemote.Transact");
        _myOperationsList.add(_oper);
        if (_myOperations.get("transact") == null) {
            _myOperations.put("transact", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("transact")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getversion", _params, new javax.xml.namespace.QName("", "Result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://tempuri.org/FSVRemote/message/", "getversion"));
        _oper.setSoapAction("http://tempuri.org/FSVRemote/action/FSVRemote.getversion");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getversion") == null) {
            _myOperations.put("getversion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getversion")).add(_oper);
    }

    public FSVRemoteSoapBindingSkeleton() {
        this.impl = new fsv.FSVRemoteSoapBindingImpl();
    }

    public FSVRemoteSoapBindingSkeleton(fsv.FSVRemoteSoapPort impl) {
        this.impl = impl;
    }
    public java.lang.String transact(java.lang.String userid, java.lang.String password, java.lang.String paramstr) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.transact(userid, password, paramstr);
        return ret;
    }

    public java.lang.String getversion() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getversion();
        return ret;
    }

}
