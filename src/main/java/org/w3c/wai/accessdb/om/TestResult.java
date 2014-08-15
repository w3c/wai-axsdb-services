package org.w3c.wai.accessdb.om;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.wai.accessdb.om.base.BaseEntity;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.utils.DateAdapter;
import org.w3c.wai.accessdb.utils.TestUnitIdAdapter;

/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 25.01.12
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class TestResult extends BaseEntity
{
	
    @Embedded
    private TestingProfile testingProfile;

    @OneToOne
	@XmlJavaTypeAdapter(TestUnitIdAdapter.class)
    private TestUnitDescription testUnitDescription;
    
    private String httpHeaders;
    private long duration;

    private boolean resultValue;
    @Column(columnDefinition = "LONGBLOB") 
    private String comment;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date runDate;
    
    public String getComment() {
		return comment;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isResultValue() {
		return resultValue;
	}

	public void setResultValue(boolean resultValue) {
		this.resultValue = resultValue;
	}

	public TestingProfile getTestingProfile()
    {
        return testingProfile;
    }

    public void setTestingProfile(TestingProfile param)
    {
        this.testingProfile = param;
    }

    public TestUnitDescription getTestUnitDescription()
    {
        return testUnitDescription;
    }

    public void setTestUnitDescription(TestUnitDescription testUnitDescription)
    {
        this.testUnitDescription = testUnitDescription;
    }

	public String getHttpHeaders() {
		return httpHeaders;
	}

	public void setHttpHeaders(String httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
}