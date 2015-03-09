

        /**
        * FSVRemoteMessageReceiverInOut.java
        *
        * This file was auto-generated from WSDL
        * by the Apache Axis2 version: 1.1 Nov 13, 2006 (07:31:44 LKT)
        */
        package fsv;

        /**
        *  FSVRemoteMessageReceiverInOut message receiver
        */

        public class FSVRemoteMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        fsv.FSVRemoteSkeleton skel = (fsv.FSVRemoteSkeleton)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if(op.getName() != null & (methodName = op.getName().getLocalPart()) != null){

        

            if("getversion".equals(methodName)){

            
            
                            //doc style
                            org.tempuri.fsvremote.wsdl.Getversion wrappedParam =
                                                 (org.tempuri.fsvremote.wsdl.Getversion)fromOM(
                        msgContext.getEnvelope().getBody().getFirstElement(),
                        org.tempuri.fsvremote.wsdl.Getversion.class,
                        getEnvelopeNamespaces(msgContext.getEnvelope()));
                                    
                                   
                                             skel.getversion(wrappedParam) ;
                                        
                                    envelope = getSOAPFactory(msgContext).getDefaultEnvelope();
                                

            }
        

            if("Transact".equals(methodName)){

            
            
                            //doc style
                            
                                     skel.Transact();
                                
                                    envelope = getSOAPFactory(msgContext).getDefaultEnvelope();
                                

            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(org.tempuri.fsvremote.wsdl.Getversion param, boolean optimizeContent){
            
                     return param.getOMElement(org.tempuri.fsvremote.wsdl.Getversion.MY_QNAME,
                                  org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                    

            }
        


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces){

        try {
        
                if (org.tempuri.fsvremote.wsdl.Getversion.class.equals(type)){
                
                           return org.tempuri.fsvremote.wsdl.Getversion.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (Exception e) {
        throw new RuntimeException(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    