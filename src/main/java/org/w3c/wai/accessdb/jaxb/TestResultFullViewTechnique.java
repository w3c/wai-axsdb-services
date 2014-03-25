package org.w3c.wai.accessdb.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.utils.JAXBUtils;

@XmlRootElement
public class TestResultFullViewTechnique
{
    private String techniqueNameId = "undefined";

    private List<List<TestResultFullViewTechniqueCell>> rows = new ArrayList<List<TestResultFullViewTechniqueCell>>();

    public String getTechniqueNameId()
    {
        return techniqueNameId;
    }

    public void setTechniqueNameId(String techniqueNameId)
    {
        this.techniqueNameId = techniqueNameId;
    }

    public List<List<TestResultFullViewTechniqueCell>> getRows()
    {
        return rows;
    }

    public void setRows(List<List<TestResultFullViewTechniqueCell>> rows)
    {
        this.rows = rows;
    }

    @Override
    public String toString()
    {
        return JAXBUtils.objectToJSONString(this);
    }

}
