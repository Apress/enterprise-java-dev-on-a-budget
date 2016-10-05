/**
 * NewsService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package localhost.tcms.services.News;

public interface NewsService extends javax.xml.rpc.Service {
    public java.lang.String getNewsAddress();

    public localhost.tcms.services.News.News getNews() throws javax.xml.rpc.ServiceException;

    public localhost.tcms.services.News.News getNews(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
