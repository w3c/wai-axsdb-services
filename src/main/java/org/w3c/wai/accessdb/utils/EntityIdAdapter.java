package org.w3c.wai.accessdb.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.base.BaseEntity;

public class EntityIdAdapter extends XmlAdapter<String, BaseEntity> {

    @Override
    public String marshal(BaseEntity t) throws Exception {
        return String.valueOf(t.getId());
    }

    @Override
    public BaseEntity unmarshal(String id) throws Exception {
        return EAOManager.INSTANCE.getObjectEAO().findById(Long.parseLong(id));
    }

} 
 