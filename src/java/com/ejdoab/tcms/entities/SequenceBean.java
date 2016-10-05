package com.ejdoab.tcms.entities;

import javax.ejb.*;

/**
 * Sequence Block Entity Bean
 *
 * @author <a href="http://boss.bekk.no/boss/middlegen/">Middlegen</a>
 *
 * @ejb.bean
 *    type="CMP"
 *    cmp-version="2.x"
 *    name="Sequence"
 *    local-jndi-name="middlegen.sequencegenerator.ejb.SequenceLocalHome"
 *    view-type="local"
 *    primkey-field="name"
 *
 * @ejb.persistence table-name="SEQ_BLOCK"
 */
public abstract class SequenceBean implements EntityBean {

   /**
    * @ejb.create-method
    */
   public String ejbCreate(String name) throws javax.ejb.CreateException {
      setName(name);
      setIndex(0);
      return null;
   }

   /**
    * Returns the index
    *
    * @return the index
    *
    * @ejb.persistence column-name="idx"
    */
   public abstract int getIndex();
   public abstract void setIndex(int newIndex);

   /**
    * Returns the name
    *
    * @return the name
    *
    * @ejb.pk-field
    * @ejb.persistence column-name="name"
    */
   public abstract String getName();
   public abstract void setName(java.lang.String newName);

   /**
    * @ejb.interface-method view-type="local"
    */
   public int getValueAfterIncrementingBy(int blockSize) {
      setIndex(getIndex() + blockSize);
      return getIndex();
   }
}
