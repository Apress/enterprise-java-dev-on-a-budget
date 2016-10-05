package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: ROOMS
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="Room"
 *    local-jndi-name="ejb.RoomLocalHome"
 *    view-type="local"
 *    primkey-field="id"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM Room o"
 *
 * @ejb.finder
 *    signature="java.util.Collection findByName(java.lang.String name)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM Room o WHERE o.name = ?1"
 *    description="NAME is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByNotes(java.lang.String notes)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM Room o WHERE o.notes = ?1"
 *    description="NOTES is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByCapacity(int capacity)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM Room o WHERE o.capacity = ?1"
 *    description="CAPACITY is not indexed."
 *
 * @ejb.persistence table-name="ROOMS"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class RoomBean implements javax.ejb.EntityBean {

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
    * Returns the name
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the name
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="NAME"
    */
   public abstract java.lang.String getName();

   /**
    * Sets the name
    *
    * @param java.lang.String the new name value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setName(java.lang.String name);

   /**
    * Returns the notes
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the notes
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="NOTES"
    */
   public abstract java.lang.String getNotes();

   /**
    * Sets the notes
    *
    * @param java.lang.String the new notes value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setNotes(java.lang.String notes);

   /**
    * Returns the capacity
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the capacity
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="CAPACITY"
    */
   public abstract int getCapacity();

   /**
    * Sets the capacity
    *
    * @param int the new capacity value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setCapacity(int capacity);

   /**
    * This is a uni-directional one-to-many relationship CMR method
    *
    * @return the related com.ejdoab.tcms.entities.VenueLocal.
    *
    *
    * @ejb.relation
    *    name="VENUES-cmp20-ROOMS-cmp20"
    *    role-name="ROOMS-cmp20-has-VENUES-cmp20"
    *    target-ejb="Venue"
    *    target-role-name="VENUES-cmp20-has-ROOMS-cmp20"
    *    target-multiple="yes"
    *
    * @jboss.relation-mapping style="foreign-key"
    *
    * @weblogic.column-map
    *    foreign-key-column="FK_VENUEID"
    *    key-column="PK_ID"
    *
    * @jboss.relation
    *    fk-constraint="true"
    *    fk-column="FK_VENUEID"
    *    related-pk-field="id"
    *
    */
   public abstract com.ejdoab.tcms.entities.VenueLocal getVenue();

   /**
    * Sets the related com.ejdoab.tcms.entities.VenueLocal
    *
    * @param com.ejdoab.tcms.entities.RoomLocal the related $target.variableName
    *
    * @ejb.interface-method view-type="local"
    *
    * @param venue the new CMR value
    */
   public abstract void setVenue(com.ejdoab.tcms.entities.VenueLocal venue);

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
    *    name="ROOMS-cmp20-SESSIONS-cmp20"
    *    role-name="ROOMS-cmp20-has-SESSIONS-cmp20"
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
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "ROOMS.PK_ID" )));
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
    * @param name the name value
    * @param notes the notes value
    * @param capacity the capacity value
    * @param venue CMR field
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.lang.String name, java.lang.String notes, int capacity, com.ejdoab.tcms.entities.VenueLocal venue ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "ROOMS.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setName(name);
      setNotes(notes);
      setCapacity(capacity);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param name the name value
    * @param notes the notes value
    * @param capacity the capacity value
    * @param venue CMR field
    */
   public void ejbPostCreate( java.lang.String name, java.lang.String notes, int capacity, com.ejdoab.tcms.entities.VenueLocal venue ) throws javax.ejb.CreateException {
      // Set CMR fields
      setVenue(venue);
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-rooms-class-code.txt found.

}
