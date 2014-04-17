package org.w3c.wai.accessdb.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;

public class TestUnitIdAdapter extends XmlAdapter<String, TestUnitDescription> {

    @Override
    public String marshal(TestUnitDescription t) throws Exception {
        return String.valueOf(t.getTestUnitId());
    }

    @Override
    public TestUnitDescription unmarshal(String id) throws Exception {
        return EAOManager.INSTANCE.getTestUnitDescriptionEAO().findByTestUnitId(id);
    }

} 
  