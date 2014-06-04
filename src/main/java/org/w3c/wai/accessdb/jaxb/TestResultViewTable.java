package org.w3c.wai.accessdb.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.wai.accessdb.utils.JAXBUtils;

@XmlRootElement
public class TestResultViewTable
{
    private String id = "undefined";

    private List<List<TestResultViewTableCell>> rows = new ArrayList<List<TestResultViewTableCell>>();

    public List<List<TestResultViewTableCell>> getRows()
    {
        return rows;
    }

    public void setRows(List<List<TestResultViewTableCell>> rows)
    {
        this.rows = rows;
    }

    @Override
    public String toString()
    {
        return JAXBUtils.objectToJSONString(this);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
