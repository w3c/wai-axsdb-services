/**
 * 
 */
package org.w3c.wai.accessdb;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.After;
import org.junit.Before;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 18.01.12
 */

public abstract class AbstractRestServiceTest<E>
{ 
    private static TJWSEmbeddedJaxrsServer server;
    public static final int SERVER_PORT = 8888;
    public static final String SERVER_HOST = "localhost";
    public static final String SERVER_URL = "http://" + SERVER_HOST + ":"
            + SERVER_PORT;
   // protected static Class testResourceClass = null;
    
    abstract protected void populate() throws ASBPersistenceException;
    abstract protected Class getResourceClass();
    
    @Before
    public  void beforeClass() throws ASBPersistenceException { 
        //testResourceClass = AgentRestServiceImpl.class;
        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(SERVER_PORT);
        server.start();
        server.getDeployment().getRegistry().addPerRequestResource(getResourceClass());
        populate();
    }
    @After
    public  void afterClass() {
        server.stop();
    }    
}
