package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.jaxb.TechnologyCombination;
import org.w3c.wai.accessdb.om.TestResult;
import org.w3c.wai.accessdb.om.TestResultsBunch;
 

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class TestResultsBunchEAO extends BaseEAO<TestResultsBunch>
{ 
	

    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class<? extends TestResultsBunch> getEntityClass()
    {
        return TestResultsBunch.class;
    }
    public TestResultsBunch findbyTestResultId(long id)
    {
    	List<TestResultsBunch> l = this.doNamedQuery("TestResultsBunch.findbyTestResultId",id);
    	if(l.size()>0)
    		return l.get(0);
    	else
    		return null;
    }
    
}
