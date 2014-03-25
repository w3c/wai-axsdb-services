package org.w3c.wai.accessdb.eao;

import org.w3c.wai.accessdb.om.TestingProfile;


/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 */

public class TestingProfileEAO extends BaseEAO<TestingProfile>
{
 
	
    @Override
    public Class<? extends TestingProfile> getEntityClass()
    {
        return TestingProfile.class;
    }
    
  
}
