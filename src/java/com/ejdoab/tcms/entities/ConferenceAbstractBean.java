package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: ABSTRACTS
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="ConferenceAbstract"
 *    local-jndi-name="ejb.ConferenceAbstractLocalHome"
 *    view-type="local"
 *    primkey-field="id"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM ConferenceAbstract o"
 *
 * @ejb.finder
 *    signature="java.util.Collection findByTitle(java.lang.String title)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceAbstract o WHERE o.title = ?1"
 *    description="TITLE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByType(int type)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceAbstract o WHERE o.type = ?1"
 *    description="TYPE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByTopic(int topic)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceAbstract o WHERE o.topic = ?1"
 *    description="TOPIC is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByLevel(int level)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceAbstract o WHERE o.level = ?1"
 *    description="LEVEL is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByBody(java.lang.String body)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceAbstract o WHERE o.body = ?1"
 *    description="BODY is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByStatus(int status)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM ConferenceAbstract o WHERE o.status = ?1"
 *    description="STATUS is not indexed."
 *
 * @ejb.persistence table-name="ABSTRACTS"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class ConferenceAbstractBean implements javax.ejb.EntityBean {

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
    * Returns the type
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the type
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="TYPE"
    */
   public abstract int getType();

   /**
    * Sets the type
    *
    * @param int the new type value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setType(int type);

   /**
    * Returns the topic
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the topic
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="TOPIC"
    */
   public abstract int getTopic();

   /**
    * Sets the topic
    *
    * @param int the new topic value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setTopic(int topic);

   /**
    * Returns the level
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the level
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="LEVEL"
    */
   public abstract int getLevel();

   /**
    * Sets the level
    *
    * @param int the new level value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setLevel(int level);

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
    * Returns the status
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the status
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="STATUS"
    */
   public abstract int getStatus();

   /**
    * Sets the status
    *
    * @param int the new status value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setStatus(int status);

   /**
    * This is a bi-directional one-to-many relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.PresenterLocal.
    *
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="PRESENTERS-cmp20-ABSTRACTS-cmp20"
    *    role-name="ABSTRACTS-cmp20-has-PRESENTERS-cmp20"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.column-map
    *    foreign-key-column="FK_PRESENTERID"
    *    key-column="PK_ID"
    *
    * @jboss.relation
    *    fk-constraint="true"
    *    fk-column="FK_PRESENTERID"
    *    related-pk-field="id"
    *
    */
   public abstract com.ejdoab.tcms.entities.PresenterLocal getPresenter();

   /**
    * Sets the related com.ejdoab.tcms.entities.PresenterLocal
    *
    * @param com.ejdoab.tcms.entities.ConferenceAbstractLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param presenter the new CMR value
    */
   public abstract void setPresenter(com.ejdoab.tcms.entities.PresenterLocal presenter);

   /**
    * This is a bi-directional one-to-one relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.PresentationLocal.
    *
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="ABSTRACTS-cmp20-PRESENTATIONS-cmp20"
    *    role-name="ABSTRACTS-cmp20-has-PRESENTATIONS-cmp20"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    */
   public abstract com.ejdoab.tcms.entities.PresentationLocal getPresentation();

   /**
    * Sets the related com.ejdoab.tcms.entities.PresentationLocal
    *
    * @param com.ejdoab.tcms.entities.ConferenceAbstractLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param presentation the new CMR value
    */
   public abstract void setPresentation(com.ejdoab.tcms.entities.PresentationLocal presentation);

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
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate(  ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "ABSTRACTS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    */
   public void ejbPostCreate(  ) throws javax.ejb.CreateException {
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
    * @param title the title value
    * @param type the type value
    * @param topic the topic value
    * @param level the level value
    * @param body the body value
    * @param status the status value
    * @param presenter CMR field
    * @param presentation CMR field
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.lang.String title, int type, int topic, int level, java.lang.String body, int status, com.ejdoab.tcms.entities.PresenterLocal presenter, com.ejdoab.tcms.entities.PresentationLocal presentation ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "ABSTRACTS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setTitle(title);
      setType(type);
      setTopic(topic);
      setLevel(level);
      setBody(body);
      setStatus(status);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param title the title value
    * @param type the type value
    * @param topic the topic value
    * @param level the level value
    * @param body the body value
    * @param status the status value
    * @param presenter CMR field
    * @param presentation CMR field
    */
   public void ejbPostCreate( java.lang.String title, int type, int topic, int level, java.lang.String body, int status, com.ejdoab.tcms.entities.PresenterLocal presenter, com.ejdoab.tcms.entities.PresentationLocal presentation ) throws javax.ejb.CreateException {
      // Set CMR fields
      setPresenter(presenter);
      setPresentation(presentation);
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-abstracts-class-code.txt found.

}
