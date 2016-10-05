package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: NEWS
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="ConferenceNews"
 *    local-jndi-name="ejb.ConferenceNewsLocalHome"
 *    view-type="local"
 *    primkey-field="id"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM ConferenceNews o"
 *
 * @ejb.finder
 *    signature="java.util.Collection findByDate(java.util.Date date)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceNews o WHERE o.date = ?1"
 *    description="DATE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByRemoveDate(java.util.Date removeDate)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceNews o WHERE o.removeDate = ?1"
 *    description="REMOVEDATE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByCreationDate(java.util.Date creationDate)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceNews o WHERE o.creationDate = ?1"
 *    description="CREATIONDATE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByPublished(boolean published)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceNews o WHERE o.published = ?1"
 *    description="PUBLISHED is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByTitle(java.lang.String title)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceNews o WHERE o.title = ?1"
 *    description="TITLE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByBody(java.lang.String body)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceNews o WHERE o.body = ?1"
 *    description="BODY is not indexed."
 *
 * @ejb.persistence table-name="NEWS"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class ConferenceNewsBean implements javax.ejb.EntityBean {

   /**
    * Returns the id
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the id
    *
    * @ejb.pk-field
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="PK_ID"
    */
   public abstract java.lang.Integer getId();

   /**
    * Sets the id
    *
    * @param java.lang.Integer the new id value
    */
   public abstract void setId(java.lang.Integer id);

   /**
    * Returns the date
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the date
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="DATE"
    */
   public abstract java.util.Date getDate();

   /**
    * Sets the date
    *
    * @param java.util.Date the new date value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setDate(java.util.Date date);

   /**
    * Returns the removeDate
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the removeDate
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="REMOVEDATE"
    */
   public abstract java.util.Date getRemoveDate();

   /**
    * Sets the removeDate
    *
    * @param java.util.Date the new removeDate value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setRemoveDate(java.util.Date removeDate);

   /**
    * Returns the creationDate
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the creationDate
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="CREATIONDATE"
    */
   public abstract java.util.Date getCreationDate();

   /**
    * Sets the creationDate
    *
    * @param java.util.Date the new creationDate value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setCreationDate(java.util.Date creationDate);

   /**
    * Returns the published
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the published
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="PUBLISHED"
    */
   public abstract boolean getPublished();

   /**
    * Sets the published
    *
    * @param boolean the new published value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setPublished(boolean published);

   /**
    * Returns the title
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the title
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="TITLE"
    */
   public abstract java.lang.String getTitle();

   /**
    * Sets the title
    *
    * @param java.lang.String the new title value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setTitle(java.lang.String title);

   /**
    * Returns the body
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the body
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="BODY"
    */
   public abstract java.lang.String getBody();

   /**
    * Sets the body
    *
    * @param java.lang.String the new body value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setBody(java.lang.String body);

   /**
    * This create method takes only mandatory (non-nullable) parameters.
    *
    * When the client invokes a create method, the EJB container invokes the ejbCreate method. 
    * Typically, an ejbCreate method in an entity bean performs the following tasks: 
    * <UL>
    * <LI>Inserts the entity state into the database.</LI>
    * <LI>Initializes the instance variables.</LI>
    * <LI>Returns the primary key.</LI>
    * </UL>
    *
    * @param date the date value
    * @param removeDate the removeDate value
    * @param creationDate the creationDate value
    * @param published the published value
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.util.Date date, java.util.Date removeDate, java.util.Date creationDate, boolean published ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "NEWS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setDate(date);
      setRemoveDate(removeDate);
      setCreationDate(creationDate);
      setPublished(published);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param date the date value
    * @param removeDate the removeDate value
    * @param creationDate the creationDate value
    * @param published the published value
    */
   public void ejbPostCreate( java.util.Date date, java.util.Date removeDate, java.util.Date creationDate, boolean published ) throws javax.ejb.CreateException {
      // Set CMR fields
   }

   /**
    * This create method takes all parameters (both nullable and not nullable). 
    *
    * When the client invokes a create method, the EJB container invokes the ejbCreate method. 
    * Typically, an ejbCreate method in an entity bean performs the following tasks: 
    * <UL>
    * <LI>Inserts the entity state into the database.</LI>
    * <LI>Initializes the instance variables.</LI>
    * <LI>Returns the primary key.</LI>
    * </UL>
    *
    * @param date the date value
    * @param removeDate the removeDate value
    * @param creationDate the creationDate value
    * @param published the published value
    * @param title the title value
    * @param body the body value
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.util.Date date, java.util.Date removeDate, java.util.Date creationDate, boolean published, java.lang.String title, java.lang.String body ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "NEWS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setDate(date);
      setRemoveDate(removeDate);
      setCreationDate(creationDate);
      setPublished(published);
      setTitle(title);
      setBody(body);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param date the date value
    * @param removeDate the removeDate value
    * @param creationDate the creationDate value
    * @param published the published value
    * @param title the title value
    * @param body the body value
    */
   public void ejbPostCreate( java.util.Date date, java.util.Date removeDate, java.util.Date creationDate, boolean published, java.lang.String title, java.lang.String body ) throws javax.ejb.CreateException {
      // Set CMR fields
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-news-class-code.txt found.

}
