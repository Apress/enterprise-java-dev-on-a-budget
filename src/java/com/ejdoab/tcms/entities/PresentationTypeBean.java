package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: PRESENTATIONTYPES
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="PresentationType"
 *    local-jndi-name="ejb.PresentationTypeLocalHome"
 *    view-type="local"
 *    primkey-field="id"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM PresentationType o"
 *
 * @ejb.finder
 *    signature="java.util.Collection findByName(java.lang.String name)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM PresentationType o WHERE o.name = ?1"
 *    description="NAME is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByDescription(java.lang.String description)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM PresentationType o WHERE o.description = ?1"
 *    description="DESCRIPTION is not indexed."
 *
 * @ejb.persistence table-name="PRESENTATIONTYPES"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class PresentationTypeBean implements javax.ejb.EntityBean {

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
    * Returns the description
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the description
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="DESCRIPTION"
    */
   public abstract java.lang.String getDescription();

   /**
    * Sets the description
    *
    * @param java.lang.String the new description value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setDescription(java.lang.String description);

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
    * @param name the name value
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.lang.String name ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "PRESENTATIONTYPES.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setName(name);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param name the name value
    */
   public void ejbPostCreate( java.lang.String name ) throws javax.ejb.CreateException {
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
    * @param description the description value
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.Integer ejbCreate( java.lang.String name, java.lang.String description ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
         setId(new java.lang.Integer(sequenceGenerator.getNextSequenceNumber( "PRESENTATIONTYPES.PK_ID" )));
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.

      // Set CMP fields
      setName(name);
      setDescription(description);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param name the name value
    * @param description the description value
    */
   public void ejbPostCreate( java.lang.String name, java.lang.String description ) throws javax.ejb.CreateException {
      // Set CMR fields
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-presentationtypes-class-code.txt found.

}
