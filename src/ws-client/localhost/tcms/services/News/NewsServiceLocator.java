/**
 * NewsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package localhost.tcms.services.News;

public class NewsServiceLocator extends org.apache.axis.client.Service implements localhost.tcms.services.News.NewsService {

    // Use to get a proxy class for News
    private final java.lang.String News_address = "http://localhost:8080/tcms/services/News";

    public java.lang.String getNewsAddress() {
        return News_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NewsWSDDServiceName = "News";

    public java.lang.String getNewsWSDDServiceName() {
        return NewsWSDDServiceName;
    }

    public void setNewsWSDDServiceName(java.lang.String name) {
        NewsWSDDServiceName = name;
    }

    public localhost.tcms.services.News.News getNews() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(News_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNews(endpoint);
    }

    public localhost.tcms.services.News.News getNews(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            localhost.tcms.services.News.NewsSoapBindingStub _stub = new localhost.tcms.services.News.NewsSoapBindingStub(portAddress, this);
            _stub.setPortName(getNewsWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (localhost.tcms.services.News.News.class.isAssignableFrom(serviceEndpointInterface)) {
                localhost.tcms.services.News.NewsSoapBindingStub _stub = new localhost.tcms.services.News.NewsSoapBindingStub(new java.net.URL(News_address), this);
                _stub.setPortName(getNewsWSDDServiceName());
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
        String inputPortName = portName.getLocalPart();
        if ("News".equals(inputPortName)) {
            return getNews();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8080/tcms/services/News", "NewsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("News"));
        }
        return ports.iterator();
    }

}
