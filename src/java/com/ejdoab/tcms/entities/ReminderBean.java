package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: REMINDERS
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="Reminder"
 *    local-jndi-name="ejb.ReminderLocalHome"
 *    view-type="local"
 *    primkey-field="id"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM Reminder o"
 *
 * @ejb.finder
 *    signature="java.util.Collection findByMessage(java.lang.String message)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM Reminder o WHERE o.message = ?1"
 *    description="MESSAGE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByDateAndTime(java.util.Date dateAndTime)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM Reminder o WHERE o.dateAndTime = ?1"
 *    description="DATEANDTIME is not indexed."
 *
 * @ejb.persistence table-name="REMINDERS"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class ReminderBean implements javax.ejb.EntityBean {

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
    * Returns the message
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the message
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="MESSAGE"
    */
   public abstract java.lang.String getMessage();

   /**
    * Sets the message
    *
    * @param java.lang.String the new message value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setMessage(java.lang.String message);

   /**
    * Returns the dateAndTime
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the dateAndTime
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="DATEANDTIME"
    */
   public abstract java.util.Date getDateAndTime();

   /**
    * Sets the dateAndTime
    *
    * @param java.util.Date the new dateAndTime value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setDateAndTime(java.util.Date dateAndTime);

   /**
    * This is a uni-directional one-to-many relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.UserLocal.
    *
    *
    * @ejb.relation
    *    name="USERS-cmp20-REMINDERS-cmp20"
    *    role-name="REMINDERS-cmp20-has-USERS-cmp20"
    *    target-ejb="User"
    *    target-role-name="USERS-cmp20-has-REMINDERS-cmp20"
    *    target-multiple="yes"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.column-map
    *    foreign-key-column="FK_USERID"
    *    key-column="PK_ID"
    *
    * @jboss.relation
    *    fk-constraint="true"
    *    fk-column="FK_USERID"
    *    related-pk-field="id"
    *
    */
   public abstract com.ejdoab.tcms.entities.UserLocal getUser();

   /**
    * Sets the related com.ejdoab.tcms.entities.UserLocal
    *
    * @param com.ejdoab.tcms.entities.ReminderLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param user the new CMR value
    */
   public abstract void setUser(com.ejdoab.tcms.entities.UserLocal user);

   /**
    * This is a bi-directional one-to-many relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.ScheduleEntryLocal.
    *
    * @ejb.interface-method view-type="local"
    *
    * @ejb.relation
    *    name="SCHEDULEENTRIES-cmp20-REMINDERS-cmp20"
    *    role-name="REMINDERS-cmp20-has-SCHEDULEENTRIES-cmp20"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.column-map
    *    foreign-key-column="FK_SCHEDULEENTRYID"
    *    key-column="PK_ID"
    *
    * @jboss.relation
    *    fk-constraint="true"
    *    fk-column="FK_SCHEDULEENTRYID"
    *    related-pk-field="id"
    *
    */
   public abstract com.ejdoab.tcms.entities.ScheduleEntryLocal getScheduleEntry();

   /**
    * Sets the related com.ejdoab.tcms.entities.ScheduleEntryLocal
    *
    * @param com.ejdoab.tcms.entities.ReminderLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param scheduleEntry the new CMR value
    */
   public abstract void setScheduleEntry(com.ejdoab.tcms.entities.ScheduleEntryLocal scheduleEntry);

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
    * @param dateAndTime the dateAndTime value
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.util.Date dateAndTime ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "REMINDERS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setDateAndTime(dateAndTime);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param dateAndTime the dateAndTime value
    */
   public void ejbPostCreate( java.util.Date dateAndTime ) throws javax.ejb.CreateException {
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
    * @param message the message value
    * @param dateAndTime the dateAndTime value
    * @param user CMR field
    * @param scheduleEntry CMR field
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.lang.String message, java.util.Date dateAndTime, com.ejdoab.tcms.entities.UserLocal user, com.ejdoab.tcms.entities.ScheduleEntryLocal scheduleEntry ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "REMINDERS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setMessage(message);
      setDateAndTime(dateAndTime);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param message the message value
    * @param dateAndTime the dateAndTime value
    * @param user CMR field
    * @param scheduleEntry CMR field
    */
   public void ejbPostCreate( java.lang.String message, java.util.Date dateAndTime, com.ejdoab.tcms.entities.UserLocal user, com.ejdoab.tcms.entities.ScheduleEntryLocal scheduleEntry ) throws javax.ejb.CreateException {
      // Set CMR fields
      setUser(user);
      setScheduleEntry(scheduleEntry);
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-reminders-class-code.txt found.

}