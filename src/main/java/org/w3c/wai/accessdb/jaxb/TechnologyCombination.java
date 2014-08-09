package org.w3c.wai.accessdb.jaxb;

public class TechnologyCombination implements Comparable<TechnologyCombination>{
	private String atName;
	private String atVersion;
	private String uaName;
	private String uaVersion;
	private String osName;
	private String osVersion;

	public TechnologyCombination() {
	}

	public TechnologyCombination(String atName, String atVersion,
			String uaName, String uaVersion, String osName, String osVersion) {
		this.atName = atName;
		this.atVersion = atVersion;
		this.uaName = uaName;
		this.uaVersion = uaVersion;
		this.osName = osName;
		this.osVersion = osVersion;
	}
	public TechnologyCombination(Object[] o) {
        this.atName = String.valueOf(o[0]);
        this.atVersion =  String.valueOf(o[1]);
        this.uaName =  String.valueOf(o[2]);
        this.uaVersion = String.valueOf(o[3]);
        this.osName =  String.valueOf(o[4]);
        this.osVersion =  String.valueOf(o[5]);
    }

	public String getAtName() {
		return atName;
	}

	public void setAtName(String atName) {
		this.atName = atName;
	}

	public String getAtVersion() {
		return atVersion;
	}

	public void setAtVersion(String atVersion) {
		this.atVersion = atVersion;
	}

	public String getUaName() {
		return uaName;
	}

	public void setUaName(String uaName) {
		this.uaName = uaName;
	}

	public String getUaVersion() {
		return uaVersion;
	}

	public void setUaVersion(String uaVersion) {
		this.uaVersion = uaVersion;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	@Override
	public String toString() {
		return this.atName + " " + this.atVersion + " " + this.uaName + " "
				+ this.uaVersion + " " + this.osName + " " + this.osVersion;
	}

	@Override
	public int compareTo(TechnologyCombination c) {
		String s1 = this.getUaName() + " " + this.getUaVersion();
		String s2 = c.getUaName() + " " + c.getUaVersion();
		return s1.compareTo(s1);
	}
}
