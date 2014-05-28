package org.w3c.wai.accessdb.rest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.om.Guideline;
import org.w3c.wai.accessdb.om.Principle;
import org.w3c.wai.accessdb.om.SuccessCriterio;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.services.TechniquesService;
import org.w3c.wai.accessdb.services.WCAG2Service;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;

/**
 * @author evangelos.vlachogiannis
 * @since 16.04.12
 */
@Path("wcag2")
public class WCAG2Resource {
	/**
	 * The WCAG2 Principles/geuidelines/SuccessCriteria TreeNodeData
	 * 
	 * @param level
	 * @return
	 */
	@Path("browse/wcag2/tree/{level}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getWCAG2TreeData(@PathParam("level") String level) {
		return Response.ok(WCAG2Service.INSTANCE.getSimpleWCAGTreeData(level))
				.build();
	}

	/**
	 * The Web Technologies (Technologies Techniques) TreeNodeData
	 * 
	 * @return
	 */
	@Path("browse/webtechs/tree")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getWCAGTechs() {
		return Response.ok(TechniquesService.INSTANCE.getWebTechnologiesTree())
				.build();
	}

	/**
	 * The Web Technologies (Technologies Techniques) TreeNodeData
	 * 
	 * @return
	 */
	@Path("browse/webtechswithtechniques/tree")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getWebTechnologiesWithTechniquesTreeData() {
		return Response.ok(
				TechniquesService.INSTANCE
						.getWebTechnologiesWithTechniquesTree()).build();
	}

	/**
	 * Get the Techniques by web technology name id (e.g WCAG20-ARIA-TECHS)
	 * 
	 * @param nameId
	 * @return
	 * @throws ASBPersistenceException
	 */
	@Path("techniques/{nameId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<Technique> findTechniquesByWebTechNameId(
			@PathParam("nameId") String nameId) throws ASBPersistenceException {
		return new ElementWrapper<Technique>(
				TechniquesService.INSTANCE.retrieveTechniques(nameId));
	}

	/**
	 * Get all techniques
	 * 
	 * @return
	 */
	@Path("techniques")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<Technique> getAllTechniques() {
		return new ElementWrapper<Technique>(EAOManager.INSTANCE
				.getTechniqueEAO().findAll());
	}

	/**
	 * Get Technique by DB id
	 */
	@Path("technique/byid/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Technique getTechniqueById(@PathParam("id") String id) {
		try {
			return EAOManager.INSTANCE.getTechniqueEAO().findById(
					Long.parseLong(id));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	@GET
	@Path("techniques/byterm/{term}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Technique> getTechniqueByTerm(@PathParam("term") String term) {
		return TechniquesService.INSTANCE.getTechniquesByTerm(term);
	}

	/**
	 * Get all Principles
	 * 
	 * @return
	 */
	@Path("principles")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<Principle> getAllPrinciples() {
		return new ElementWrapper<Principle>(EAOManager.INSTANCE
				.getPrincipleEAO().findAll());
	}

	/**
	 * Get all Guidelines
	 * 
	 * @return
	 */
	@Path("guidelines")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<Guideline> getAllGuidelines() {
		return new ElementWrapper<Guideline>(EAOManager.INSTANCE
				.getGuidelineEAO().findAll());
	}

	/**
	 * Get all Success Criteria
	 * 
	 * @return
	 */
	@Path("criteria")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<SuccessCriterio> getAllSuccessCriteria() {
		return new ElementWrapper<SuccessCriterio>(EAOManager.INSTANCE
				.getCriterioEAO().findAll());
	}
}
