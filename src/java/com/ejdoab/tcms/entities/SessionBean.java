package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: SESSIONS
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="Session"
 *    local-jndi-name="ejb.SessionLocalHome"
 *    view-type="local"
 *    primkey-field="id"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM Session o"
 *
 * @ejb.finder
 *    signature="java.util.Collection findByDateTimeBegin(java.util.Date dateTimeBegin)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM Session o WHERE o.dateTimeBegin = ?1"
 *    description="DATETIMEBEGIN is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByDateTimeEnd(java.util.Date dateTimeEnd)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM Session o WHERE o.dateTimeEnd = ?1"
 *    description="DATETIMEEND is not indexed."
 *
 * @ejb.persistence table-name="SESSIONS"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class SessionBean implements javax.ejb.EntityBean {

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
    * Returns the dateTimeBegin
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the dateTimeBegin
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="DATETIMEBEGIN"
    */
   public abstract java.util.Date getDateTimeBegin();

   /**
    * Sets the dateTimeBegin
    *
    * @param java.util.Date the new dateTimeBegin value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setDateTimeBegin(java.util.Date dateTimeBegin);

   /**
    * Returns the dateTimeEnd
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the dateTimeEnd
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="DATETIMEEND"
    */
   public abstract java.util.Date getDateTimeEnd();

   /**
    * Sets the dateTimeEnd
    *
    * @param java.util.Date the new dateTimeEnd value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setDateTimeEnd(java.util.Date dateTimeEnd);

   /**
    * This is a bi-directional one-to-many relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.PresentationLocal.
    *
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="PRESENTATIONS-cmp20-SESSIONS-cmp20"
    *    role-name="SESSIONS-cmp20-has-PRESENTATIONS-cmp20"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.column-map
    *    foreign-key-column="FK_PRESENTATIONID"
    *    key-column="PK_ID"
    *
    * @jboss.relation
    *    fk-constraint="true"
    *    fk-column="FK_PRESENTATIONID"
    *    related-pk-field="id"
    *
    */
   public abstract com.ejdoab.tcms.entities.PresentationLocal getPresentation();

   /**
    * Sets the related com.ejdoab.tcms.entities.PresentationLocal
    *
    * @param com.ejdoab.tcms.entities.SessionLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param presentation the new CMR value
    */
   public abstract void setPresentation(com.ejdoab.tcms.entities.PresentationLocal presentation);

   /**
    * This is a uni-directional one-to-many relationship CMR method
    *
    * @return a java.util.Collection of related com.ejdoab.tcms.entities.ScheduleEntryLocal.
    * middlegen.plugins.entitybean.Entity20Table@1e88b35
    * class middlegen.plugins.entitybean.Entity20Table
    * middlegen.plugins.entitybean.Entity20Table
    *
    * @ejb.relation
    *    name="SESSIONS-cmp20-SCHEDULEENTRIES-cmp20"
    *    role-name="SESSIONS-cmp20-has-SCHEDULEENTRIES-cmp20"
    *    target-ejb="ScheduleEntry"
    *    target-role-name="SCHEDULEENTRIES-cmp20-has-SESSIONS-cmp20"
    *    target-multiple="no"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.target-column-map
    *    foreign-key-column="FK_SESSIONID"
    *    key-column="PK_ID"
    *
    * @jboss.target-relation
    *    fk-constraint="true"
    *    fk-column="FK_SESSIONID"
    *    related-pk-field="id"
    *
    */
   public abstract java.util.Collection getScheduleEntries();

   /**
    * Sets a collection of related com.ejdoab.tcms.entities.ScheduleEntryLocal
    *
    * @param a collection of related com.ejdoab.tcms.entities.ScheduleEntryLocal
    *
    * @ejb.interface-method view-type="local"
    *
    * @param scheduleEntries the new CMR value
    */
   public abstract void setScheduleEntries(java.util.Collection scheduleEntries);

   /**
    * This is a bi-directional one-to-many relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.RoomLocal.
    *
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="ROOMS-cmp20-SESSIONS-cmp20"
    *    role-name="SESSIONS-cmp20-has-ROOMS-cmp20"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.column-map
    *    foreign-key-column="FK_ROOMID"
    *    key-column="PK_ID"
    *
    * @jboss.relation
    *    fk-constraint="true"
    *    fk-column="FK_ROOMID"
    *    related-pk-field="id"
    *
    */
   public abstract com.ejdoab.tcms.entities.RoomLocal getRoom();

   /**
    * Sets the related com.ejdoab.tcms.entities.RoomLocal
    *
    * @param com.ejdoab.tcms.entities.SessionLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param room the new CMR value
    */
   public abstract void setRoom(com.ejdoab.tcms.entities.RoomLocal room);

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
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "SESSIONS.PK_ID" )));
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
    * @param dateTimeBegin the dateTimeBegin value
    * @param dateTimeEnd the dateTimeEnd value
    * @param presentation CMR field
    * @param room CMR field
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.util.Date dateTimeBegin, java.util.Date dateTimeEnd, com.ejdoab.tcms.entities.PresentationLocal presentation, com.ejdoab.tcms.entities.RoomLocal room ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "SESSIONS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setDateTimeBegin(dateTimeBegin);
      setDateTimeEnd(dateTimeEnd);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param dateTimeBegin the dateTimeBegin value
    * @param dateTimeEnd the dateTimeEnd value
    * @param presentation CMR field
    * @param room CMR field
    */
   public void ejbPostCreate( java.util.Date dateTimeBegin, java.util.Date dateTimeEnd, com.ejdoab.tcms.entities.PresentationLocal presentation, com.ejdoab.tcms.entities.RoomLocal room ) throws javax.ejb.CreateException {
      // Set CMR fields
      setPresentation(presentation);
      setRoom(room);
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-sessions-class-code.txt found.

}