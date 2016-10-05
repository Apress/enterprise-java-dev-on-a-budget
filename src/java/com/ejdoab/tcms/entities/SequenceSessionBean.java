package com.ejdoab.tcms.entities;

import com.ejdoab.tcms.entities.SequenceLocalHome;
import com.ejdoab.tcms.entities.SequenceLocal;
import com.ejdoab.tcms.entities.SequenceUtil;

/**
 * Sequence Block Session Bean
 *
 * @ejb.bean
 *    type="Stateless"
 *    name="SequenceSession"
 *    description="Sequence generator"
 *    local-jndi-name="com.ejdoab.tcms.entities.SequenceSessionLocalHome"
 *    view-type="local"
 *
 * @ejb.ejb-ref
 *    ejb-name="Sequence"
 *    ref-name="ejb/Sequence"
 *    view-type="local"
 *
 * @ejb:env-entry
 *    name="blockSize"
 *    type="java.lang.Integer"
 *    value="5"
 *
 * @ejb:env-entry
 *    name="retryCount"
 *    type="java.lang.Integer"
 *    value="2"
 */
public abstract class SequenceSessionBean implements javax.ejb.SessionBean {

   private class Entry {
      SequenceLocal sequence;
      int last;
   }

   private java.util.Hashtable _entries = new java.util.Hashtable();
   private int _blockSize;
   private int _retryCount;
   private SequenceLocalHome _sequenceHome;

   /**
    * @ejb.interface-method view-type="local"
    */
   public int getNextSequenceNumber(String name) {
      try {
         Entry entry = (Entry) _entries.get(name);

         if (entry == null) {
            // add an entry to the sequence table
            entry = new Entry();
            try {
               entry.sequence = _sequenceHome.findByPrimaryKey(name);
            } catch (javax.ejb.FinderException e) {
               //System.out.println("\n\n\n\n******** : " + e + "\n\n\n\n");
               // if we couldn't find it, then create it...
               entry.sequence = _sequenceHome.create(name);
            }
            _entries.put(name, entry);
         }
         if (entry.last % _blockSize == 0) {
            for (int retry = 0; true; retry++) {
               try {
                  entry.last = entry.sequence.getValueAfterIncrementingBy(_blockSize);
                  break;
               } catch (javax.ejb.TransactionRolledbackLocalException e) {
                  if (retry < _retryCount) {
                     // we hit a concurrency exception, so try again...
                     //System.out.println("RETRYING");
                     continue;
                  } else {
                     // we tried too many times, so fail...
                     throw new javax.ejb.EJBException(e);
                  }
               }
            }
         }
         return entry.last++;
      } catch (javax.ejb.CreateException e) {
         throw new javax.ejb.EJBException(e);
      }
   }

   public void setSessionContext( javax.ejb.SessionContext sessionContext) {
      try {
         javax.naming.Context namingContext = new javax.naming.InitialContext();
         _blockSize = ((Integer) namingContext.lookup("java:comp/env/blockSize")).intValue();
         _retryCount = ((Integer) namingContext.lookup("java:comp/env/retryCount")).intValue();

         _sequenceHome = SequenceUtil.getLocalHome();
      } catch(javax.naming.NamingException e) {
         throw new javax.ejb.EJBException(e);
      }
   }
}
