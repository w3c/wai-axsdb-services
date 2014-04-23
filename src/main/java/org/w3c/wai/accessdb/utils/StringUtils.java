package org.w3c.wai.accessdb.utils;

public class StringUtils {
	public static String getParentPath(String path) {
		if ((path == null) || path.equals("") || path.equals("/")) {
			return "";
		}

		int lastSlashPos = path.lastIndexOf('/');

		if (lastSlashPos >= 0) {
			return path.substring(0, lastSlashPos); // strip off the slash
		} else {
			return ""; // we expect people to add + "/somedir on their own
		}
	}
}
