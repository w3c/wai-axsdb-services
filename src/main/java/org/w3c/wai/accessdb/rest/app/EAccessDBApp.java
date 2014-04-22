package org.w3c.wai.accessdb.rest.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.w3c.wai.accessdb.rest.resources.AdminResource;
import org.w3c.wai.accessdb.rest.resources.AdminTechniquesResource;
import org.w3c.wai.accessdb.rest.resources.QueryResource;
import org.w3c.wai.accessdb.rest.resources.RatingResource;
import org.w3c.wai.accessdb.rest.resources.TestResultsResource;
import org.w3c.wai.accessdb.rest.resources.TestUnitsResource;
import org.w3c.wai.accessdb.rest.resources.TestingProfileResource;
import org.w3c.wai.accessdb.rest.resources.TestingSessionResource;
import org.w3c.wai.accessdb.rest.resources.WCAG2Resource;


/**
 * @author evangelos.vlachogiannis
 * @since 14.03.12
 */
public class EAccessDBApp extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public EAccessDBApp() {
		singletons.add(new WCAG2Resource());
		singletons.add(new TestUnitsResource());
		singletons.add(new TestingProfileResource());
		singletons.add(new RatingResource());
		singletons.add(new TestingSessionResource());
		singletons.add(new TestResultsResource() );
		singletons.add(new QueryResource() );
		singletons.add(new AdminTechniquesResource() );
		singletons.add(new AdminResource() );
		
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
