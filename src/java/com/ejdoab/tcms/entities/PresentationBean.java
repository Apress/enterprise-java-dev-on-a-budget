package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: PRESENTATIONS
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="Presentation"
 *    local-jndi-name="ejb.PresentationLocalHome"
 *    view-type="local"
 *    primkey-field="id"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM Presentation o"
 *
 * @ejb.persistence table-name="PRESENTATIONS"
 *
 * @weblogic.cache concurrency-strategy="ReadOnly"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class PresentationBean implements javax.ejb.EntityBean {

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
    * This is a bi-directional one-to-many relationship CMR method
    *
    * @return a java.util.Collection of related com.ejdoab.tcms.entities.SessionLocal.
    * middlegen.plugins.entitybean.Entity20Table@6f8b2b
    * class middlegen.plugins.entitybean.Entity20Table
    * middlegen.plugins.entitybean.Entity20Table
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="PRESENTATIONS-cmp20-SESSIONS-cmp20"
    *    role-name="PRESENTATIONS-cmp20-has-SESSIONS-cmp20"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    */
   public abstract java.util.Collection getSessions();

   /**
    * Sets a collection of related com.ejdoab.tcms.entities.SessionLocal
    *
    * @param a collection of related com.ejdoab.tcms.entities.SessionLocal
    *
    * @ejb.interface-method view-type="local"
    *
    * @param sessions the new CMR value
    */
   public abstract void setSessions(java.util.Collection sessions);

   /**
    * This is a uni-directional one-to-many relationship CMR method
    *
    * @return a java.util.Collection of related com.ejdoab.tcms.entities.QuestionnaireLocal.
    * middlegen.plugins.entitybean.Entity20Table@18b8914
    * class middlegen.plugins.entitybean.Entity20Table
    * middlegen.plugins.entitybean.Entity20Table
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="PRESENTATIONS-cmp20-QUESTIONNAIRES-cmp20"
    *    role-name="PRESENTATIONS-cmp20-has-QUESTIONNAIRES-cmp20"
    *    target-ejb="Questionnaire"
    *    target-role-name="QUESTIONNAIRES-cmp20-has-PRESENTATIONS-cmp20"
    *    target-multiple="no"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.target-column-map
    *    foreign-key-column="FK_PRESENTATIONID"
    *    key-column="PK_ID"
    *
    * @jboss.target-relation
    *    fk-constraint="true"
    *    fk-column="FK_PRESENTATIONID"
    *    related-pk-field="id"
    *
    */
   public abstract java.util.Collection getQuestionnaires();

   /**
    * Sets a collection of related com.ejdoab.tcms.entities.QuestionnaireLocal
    *
    * @param a collection of related com.ejdoab.tcms.entities.QuestionnaireLocal
    *
    * @ejb.interface-method view-type="local"
    *
    * @param questionnaires the new CMR value
    */
   public abstract void setQuestionnaires(java.util.Collection questionnaires);

   /**
    * This is a bi-directional one-to-one relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.ConferenceAbstractLocal.
    *
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="ABSTRACTS-cmp20-PRESENTATIONS-cmp20"
    *    role-name="PRESENTATIONS-cmp20-has-ABSTRACTS-cmp20"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.column-map
    *    foreign-key-column="FK_ABSTRACTID"
    *    key-column="PK_ID"
    *
    * @jboss.relation
    *    fk-constraint="true"
    *    fk-column="FK_ABSTRACTID"
    *    related-pk-field="id"
    *
    */
   public abstract com.ejdoab.tcms.entities.ConferenceAbstractLocal getConferenceAbstract();

   /**
    * Sets the related com.ejdoab.tcms.entities.ConferenceAbstractLocal
    *
    * @param com.ejdoab.tcms.entities.PresentationLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param conferenceAbstract the new CMR value
    */
   public abstract void setConferenceAbstract(com.ejdoab.tcms.entities.ConferenceAbstractLocal conferenceAbstract);

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
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "PRESENTATIONS.PK_ID" )));
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
    * @param conferenceAbstract CMR field
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( com.ejdoab.tcms.entities.ConferenceAbstractLocal conferenceAbstract ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "PRESENTATIONS.PK_ID" )));
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
    * @param conferenceAbstract CMR field
    */
   public void ejbPostCreate( com.ejdoab.tcms.entities.ConferenceAbstractLocal conferenceAbstract ) throws javax.ejb.CreateException {
      // Set CMR fields
      setConferenceAbstract(conferenceAbstract);
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-presentations-class-code.txt found.

}
