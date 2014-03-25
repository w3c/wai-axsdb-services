package org.w3c.wai.accessdb.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.om.WebTechnology;

public class WebTechnologyAdapter extends XmlAdapter<String, WebTechnology> {

    @Override
    public String marshal(WebTechnology t) throws Exception {
        return t.getNameId();
    }

    @Override
    public WebTechnology unmarshal(String nameId) throws Exception {
        return EAOManager.INSTANCE.getWebTechnologyEAO().findByNameId(nameId);
    }

} 
