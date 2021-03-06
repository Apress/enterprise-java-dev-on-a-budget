package com.ejdoab.tcms.entities;

/**
 * A TCMS domain entity for table: REGISTRATIONDATEPRICINGRULES
 * Generated by Middlegen CMP2.0 Plugin 
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="RegistrationDatePricingRule"
 *    local-jndi-name="ejb.RegistrationDatePricingRuleLocalHome"
 *    view-type="local"
 *    primkey-field="pricingModel"
 *
 * @ejb.finder
 *    signature="java.util.Collection findAll()"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT OBJECT(o) FROM RegistrationDatePricingRule o"
 *
 * @ejb.finder
 *    signature="java.util.Collection findByStartDate(java.util.Date startDate)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM RegistrationDatePricingRule o WHERE o.startDate = ?1"
 *    description="STARTDATE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByEndDate(java.util.Date endDate)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM RegistrationDatePricingRule o WHERE o.endDate = ?1"
 *    description="ENDDATE is not indexed."
 *
 * @ejb.finder
 *    signature="java.util.Collection findByPrice(double price)"
 *    result-type-mapping="Local"
 *    method-intf="LocalHome"
 *    query="SELECT DISTINCT OBJECT(o) FROM RegistrationDatePricingRule o WHERE o.price = ?1"
 *    description="PRICE is not indexed."
 *
 * @ejb.persistence table-name="REGISTRATIONDATEPRICINGRULES"
 *
 * @weblogic.data-source-name $plugin.middlegen.middlegenTask.datasourceJNDIName
 */
public abstract class RegistrationDatePricingRuleBean implements javax.ejb.EntityBean {

   /**
    * Returns the pricingModel
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the pricingModel
    *
    * @ejb.pk-field
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="PK_PRICINGMODEL"
    */
   public abstract java.lang.String getPricingModel();

   /**
    * Sets the pricingModel
    *
    * @param java.lang.String the new pricingModel value
    */
   public abstract void setPricingModel(java.lang.String pricingModel);

   /**
    * Returns the startDate
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the startDate
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="STARTDATE"
    */
   public abstract java.util.Date getStartDate();

   /**
    * Sets the startDate
    *
    * @param java.util.Date the new startDate value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setStartDate(java.util.Date startDate);

   /**
    * Returns the endDate
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the endDate
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="ENDDATE"
    */
   public abstract java.util.Date getEndDate();

   /**
    * Sets the endDate
    *
    * @param java.util.Date the new endDate value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setEndDate(java.util.Date endDate);

   /**
    * Returns the price
    * @todo support OracleClob,OracleBlob on WLS
    *
    * @return the price
    *
    * @ejb.interface-method view-type="local"
    * @ejb.persistence column-name="PRICE"
    */
   public abstract double getPrice();

   /**
    * Sets the price
    *
    * @param double the new price value
    * @ejb.interface-method view-type="local"
    */
   public abstract void setPrice(double price);

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
    * @param startDate the startDate value
    * @param endDate the endDate value
    * @param price the price value
    * @return the primary key of the new instance
    *
    * @ejb.create-method
    */
   public java.lang.String ejbCreate( java.util.Date startDate, java.util.Date endDate, double price ) throws javax.ejb.CreateException {
      // Use Middlegen's Sequence Block PK generator. Only works for numeric fields
      try {
         com.ejdoab.tcms.entities.SequenceSessionLocal sequenceGenerator = 
         com.ejdoab.tcms.entities.SequenceSessionUtil.getLocalHome().create();      
      } catch( javax.naming.NamingException e ) {
         throw new javax.ejb.CreateException(e.getMessage());
      }
      // Use XDoclet's GUID generator. Only works for String fields
      // This requires <utilobject includeGUID="true"/> in ejbdoclet.
      setPricingModel(com.ejdoab.tcms.entities.RegistrationDatePricingRuleUtil.generateGUID(this));

      // Set CMP fields
      setStartDate(startDate);
      setEndDate(endDate);
      setPrice(price);
      // EJB 2.0 spec says return null for CMP ejbCreate methods.
      return null;
   }

   /**
    * The container invokes thos method immediately after it calls ejbCreate. 
    *
    * @param startDate the startDate value
    * @param endDate the endDate value
    * @param price the price value
    */
   public void ejbPostCreate( java.util.Date startDate, java.util.Date endDate, double price ) throws javax.ejb.CreateException {
      // Set CMR fields
   }

     
   // No C:\tcms\src\middlegen\merge/cmp20-registrationdatepricingrules-class-code.txt found.

}
