package org.w3c.wai.accessdb.helpers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.wai.accessdb.om.product.UAgent;

public class TestVersions {

	@Test
	public void test0() {
		UAgent u1 = new UAgent("Chrome","1.2.10");
		UAgent u2 = new UAgent("Chrome","1.2.1");
		assertEquals(u1.equals(u2), 1);
	}
	@Test
	public void test1() {
		UAgent u1 = new UAgent("Chrome","1.2");
		UAgent u2 = new UAgent("Chrome","1.2.1");
		assertEquals(u1.equals(u2), -1);
	}
	@Test
	public void test2() {
		UAgent u1 = new UAgent("Chrome","1.0.1.1");
		UAgent u2 = new UAgent("Chrome","1.2.1");
		assertEquals(u1.equals(u2), -1);
	}
	@Test
	public void test3() {
		UAgent u1 = new UAgent("Chrome","1.alpha.1.1");
		UAgent u2 = new UAgent("Chrome","1.2.1");
		assertEquals(u1.equals(u2), -1);
	}
}
