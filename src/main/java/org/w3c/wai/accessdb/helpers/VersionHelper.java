package org.w3c.wai.accessdb.helpers;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.om.ProductVersion;

public class VersionHelper {
	final static Logger logger = LoggerFactory.getLogger(VersionHelper.class);

	public static ProductVersion parseVersion(String verS){
		ProductVersion ver = new ProductVersion();
		if(ver==null)
			return ver;
		ver.setText(verS);
		StringTokenizer st = new StringTokenizer(verS, ".");
		int[] thisVer = new int[3];
		thisVer[0]=-1;
		thisVer[1]=-1;
		thisVer[2]=-1;
		int index=-1;
		while(st.hasMoreTokens()) {
			index++;
			if(index>2)
				break;
			String s = st.nextToken();
			try{
				thisVer[index] = Integer.parseInt(s);
			}
			catch (Exception e) {
				logger.warn(s + " is not valid version token");
				break;
			}			
		}
		 ver.setMajor(thisVer[0]);
		 ver.setMinor(thisVer[1]);
		 ver.setRevision(thisVer[2]);
		 return ver;
	}
	//{major}.{minor}.{revision}[beta[{beta}]]
	public static int[] getVersionNumbers(String ver) {
	    Matcher m = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(beta(\\d*))?")
	                       .matcher(ver);
	    if (!m.matches())
	        throw new IllegalArgumentException("Malformed FW version");

	    return new int[] { Integer.parseInt(m.group(1)),  // major
	            Integer.parseInt(m.group(2)),             // minor
	            Integer.parseInt(m.group(3)),             // rev.
	            m.group(4) == null ? Integer.MAX_VALUE    // no beta suffix
	                    : m.group(5).isEmpty() ? 1        // "beta"
	                    : Integer.parseInt(m.group(5))    // "beta3"
	    };
	}
	@Deprecated
	public static boolean isProductVerBigger(String thisFW, String thatFW) {
		
	    int[] thisVer = getVersionNumbers(thisFW);
	    int[] thatVer = getVersionNumbers(thatFW);
	    for (int i = 0; i < thisVer.length; i++)
	        if (thisVer[i] != thatVer[i])
	            return thisVer[i] > thatVer[i];
	            
	    return true;
	}
}
