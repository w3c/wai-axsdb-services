package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.jaxb.TechnologyCombination;
import org.w3c.wai.accessdb.om.TestResult;
 

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class TestResultEAO extends BaseEAO<TestResult>
{
	@Override
	public List<TestResult> doSimpleSelectOnlyQuery(String q) {
		q = "from TestResult where "+q;
		return super.doSimpleSelectOnlyQuery(q);
	
	}  
	public List<TechnologyCombination> findUniqueATCombinationsByTechnique(String techId)
    {
        return (List<TechnologyCombination>)this.doNamedQuery("TestResult.findUniqueATCombinationsByTechnique",techId);
    }
	public List<TechnologyCombination> findUniqueATCombinations()
    {
        return (List<TechnologyCombination>)this.doNamedQuery("TestResult.findUniqueATCombinations");
    }
    
	public String countUniqueATCombinationsByTechnique(String namedId)
    {
        return String.valueOf(this.doNamedQuery("TestResult.findUniqueATCombinationsByTechnique", namedId).size());
    }
	public String countUniqueContributorsByTechnique(String namedId)
    {
        return String.valueOf(this.doNamedQuery("TestResult.countUniqueContributorsByTechnique", namedId).get(0));
    }
	
	public List<TestResult> findByTestUnitId(String id)
    {
        return this.findByNamedQuery("TestResult.findByTestUnitId", id);
    }
	public List<TestResult> findByTechniqueNameId(String id)
    {
        return this.findByNamedQuery("TestResult.findByTechniqueNameId", id);
    }
	public String countAllByTechniqueNameId(String namedId)
    {
	    return String.valueOf(this.doNamedQuery("TestResult.countAllByTechniqueNameId", namedId).get(0));
    }
	public String countPassByTechniqueNameId(String namedId)
    {
        String s = String.valueOf(this.doNamedQuery("TestResult.countPassByTechniqueNameId", namedId).get(0));
        return s;
    }

    /* (non-Javadoc)
     * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
     */
    @Override
    public Class<? extends TestResult> getEntityClass()
    {
        return TestResult.class;
    }
    
  
}
