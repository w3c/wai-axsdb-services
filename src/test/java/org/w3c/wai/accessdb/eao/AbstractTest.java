/**
 * 
 */
package org.w3c.wai.accessdb.eao;

import java.io.IOException;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.w3c.wai.accessdb.DummyDataFactory;
import org.w3c.wai.accessdb.services.DBInitService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 *
 */
@Ignore
public class AbstractTest extends TestCase 
{
    private static Logger logger = Logger.getLogger(AbstractTest.class.getName());

    private EntityManagerFactory emFactory;

    private EntityManager em; 

    private static boolean populated = false;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if(!populated)
        {
        	this.populate();
        	populated = true;
        }
        	
        
        
        /*
        try {
        	
            logger.info("Starting in-memory database for unit tests");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa;create=true").close();
            Configuration cfg = new Configuration()
            .addClass(org.w3c.wai.accessdb.om.RequirementType.class)
            .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
            .setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/test")
            .setProperty("hibernate.order_updates", "true");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during database startup.");
        }
        try {
            logger.info("Building JPA EntityManager for unit tests");
            emFactory = Persistence.createEntityManagerFactory("accessdb");
            em = emFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during JPA EntityManager instanciation.");
        }
        */
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        /*
        logger.info("Shuting down Hibernate JPA layer.");
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
        logger.info("Stopping in-memory database.");
        try {
            DriverManager.getConnection("jdbc:derby:memory:unit-testing-jpa;shutdown=true").close();
        } catch (SQLNonTransientConnectionException ex) {
            if (ex.getErrorCode() != 45000) {
                throw ex;
            }
            // Shutdown success
        }
      //  VFMemoryStorageFactory.purgeDatabase(new File("unit-testing-jpa").getCanonicalPath());
   */
    }
    public void populate() throws ASBPersistenceException {
		DBInitService.INSTANCE.initAll();
		try {
			if (EAOManager.INSTANCE.getTestResultsBunchEAO().findAll().size() < 1) {
				DummyDataFactory.createDummyTestsAndResults(3, 3, 2, 3);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
   
}
