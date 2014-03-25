package org.w3c.wai.accessdb.helpers;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.jaxb.TreeNodeData;
import org.w3c.wai.accessdb.services.TestsService;

public class TestTestsTreeSql {

	@Test 
    public void testTestsPerTechniqueNode() {
	   // DBPopulateService.INSTANCE.populateTestUnitDesc();
        TestResultFilter filter = new TestResultFilter();
        filter.setCriteriosLevel("AA");
        filter.getCriterios().add("1.3.1");
        filter.getCriterios().add("1.2.1");
        filter.getCriterios().add("1.1.1");
        filter.getTechnologies().add("HTML");
        String sql = TestResultFilterHelper.buildHQL4Technique(filter);
        TreeNodeData rootNode = TestsService.INSTANCE.getTestsPerTechniqueNode(filter);
        System.out.println(rootNode);
        assertEquals(rootNode.getChildren().size(), 1);
    }
}
