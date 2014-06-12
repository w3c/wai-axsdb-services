package org.w3c.wai.accessdb.eao;

import java.util.List;

import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */

public class TestUnitDescriptionEAO extends BaseEAO<TestUnitDescription> {

	public TestUnitDescription findByTestUnitId(String id) {
		List<TestUnitDescription> l = this.findByNamedQuery(
				"TestUnitDescription.findbyUnitId", id);
		if (l != null && l.size() > 0)
			return (TestUnitDescription) l.get(0);
		else
			return null;
	}
	public TestUnitDescription findByRefFile(RefFileType refFile) {
		List<TestUnitDescription> l = this.findByNamedQuery(
				"TestUnitDescription.findByRefFile", refFile);
		if (l != null && l.size() > 0)
			return (TestUnitDescription) l.get(0);
		else
			return null;
	}

	public List<TestUnitDescription> findByTechnique(String id) {
		return this.findByNamedQuery("TestUnitDescription.findbyTechnique", id);

	}

	@Override
	public List<TestUnitDescription> doSimpleSelectOnlyQuery(String q) {
		//q = "from TestUnitDescription where " + q;
		return super.doSimpleSelectOnlyQuery(q);

	}

	public TestUnitDescriptionEAO() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.wai.eao.BaseEAO#getEAOClass()
	 */
	@Override
	public Class getEntityClass() {
		return TestUnitDescription.class;
	}

}
