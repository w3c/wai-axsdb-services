package org.w3c.wai.accessdb.rest.resources;

import java.io.File;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.eao.TechniqueEAO;
import org.w3c.wai.accessdb.helpers.TestUnitHelper;
import org.w3c.wai.accessdb.jaxb.ElementWrapper;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.services.ConfigService;
import org.w3c.wai.accessdb.services.TestingSessionService;
import org.w3c.wai.accessdb.services.TestsService;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.w3c.wai.accessdb.utils.AuthenticationException;
import org.w3c.wai.accessdb.utils.InOutUtils;
import org.w3c.wai.accessdb.utils.JAXBUtils;

/**
 * @author evangelos.vlachogiannis
 * @since 16.04.12
 */
@Path("test")
public class TestUnitsResource {
	final static Logger logger = LoggerFactory
			.getLogger(TestUnitsResource.class);
	/**
	 * 
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<TestUnitDescription> findAll() {
		return new ElementWrapper<TestUnitDescription>(
				TestsService.INSTANCE.findAllTestDescriptions());
	}
	
	/**
	 * Finds an existing Test by testUnitId
	 * @param testUnitId
	 * @return
	 */
	@Path("{testUnitId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByUnitId(@PathParam("testUnitId") String testUnitId) {
		TestUnitDescription t = EAOManager.INSTANCE.getTestUnitDescriptionEAO()
				.findByTestUnitId(testUnitId);
		if (t == null)
			return Response.status(Status.NOT_FOUND).build();
		else
			return Response.status(Status.OK).entity(t).build();
	}
	/**
	 * Finds Tests by Technique Id
	 * @param tid
	 * @return
	 */
	@Path("byTechnique/{tid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ElementWrapper<TestUnitDescription> findByTechnique(
			@PathParam("tid") String tid) {
		return new ElementWrapper<TestUnitDescription>(EAOManager.INSTANCE
				.getTestUnitDescriptionEAO().findByTechnique(tid));
	}

	/**
	 * Get TreeNodeData with Tests based on Filter
	 * @param filter
	 * @return
	 */
	@Path("tree")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getTestsTreeData(TestResultFilter filter) {
		try {
			return Response.ok(
					TestsService.INSTANCE.getTestsPerTechniqueNode(filter))
					.build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	/**
	 * Get Test as XML by testUnitId
	 * @param id
	 * @return
	 */
	@Path("xml/{id}")
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public String getTestAsXml(@PathParam("id") String id) {
		TestUnitDescription t = EAOManager.INSTANCE.getTestUnitDescriptionEAO()
				.findByTestUnitId(id);
		return JAXBUtils.XmlObjectToString(t);
	}

	/**
	 * Update Test from XML 
	 * @param s
	 * @param sessionId
	 * @param tu
	 * @return
	 */
	@Path("xml/{sessionId}")
	@POST
	public Response updateTestFromXml(String s,
			@PathParam("sessionId") String sessionId, TestUnitDescription tu)

	{
		if (!TestingSessionService.INSTANCE.isAuthenticated(sessionId))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		try {
			tu = (TestUnitDescription) JAXBUtils
					.stringToObject(s, TestUnitDescription.class);
			tu = EAOManager.INSTANCE.getTestUnitDescriptionEAO().persist(tu);
		} catch (JAXBException e1) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} catch (ASBPersistenceException e) {
			Response.notModified(e.getLocalizedMessage()).build();
		}
		return Response.ok(tu).build();
	}
	/**
	 * Delete Test by testUnitId
	 * @param sessionId
	 * @param id
	 * @return
	 */
	@Path("{sessionId}/{id}")
	@DELETE
	public Response deleteTest(@PathParam("sessionId") String sessionId,
			@PathParam("id") String id) {
		if (!TestingSessionService.INSTANCE.isAuthenticated(sessionId))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		if (TestsService.INSTANCE.deleteTestUnit(Long.parseLong(id)))
			return Response.status(Response.Status.OK).build();
		else
			return Response.status(Response.Status.NOT_MODIFIED).build();
	}
	/**
	 * Updates a test
	 * @param tu
	 * @param sessionId
	 * @return
	 */
	@Path("update/{sessionId}")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateTest(TestUnitDescription tu,
			@PathParam("sessionId") String sessionId) {
		try {
			if (!TestingSessionService.INSTANCE
					.isAuthenticatedAsExpert(sessionId)) {
				logger.info("not appropriate permission for updateTestUnitStatus");
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e1) {
			return Response.status(e1.getErrorStatus()).build();
		}
		try {
			tu = EAOManager.INSTANCE.getTestUnitDescriptionEAO().persist(tu);
		} catch (ASBPersistenceException e) {
			logger.warn(e.getLocalizedMessage());
			return Response.notModified(e.getLocalizedMessage()).build();
		}
		return Response.ok(tu).build();

	}

	@DELETE
	@Path("resource/{sessionId}/{fileId}")
	public Response deleteResourceFile(
			@PathParam("sessionId") String sessionId,
			@PathParam("fileId") String fileId) {
		try {
			if (!TestingSessionService.INSTANCE
					.isAuthenticatedAsExpert(sessionId)) {
				logger.info("not appropriate permission for saving a test case. Need to be expert role");
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e1) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		logger.info("OK you have permission for deleting resource file.");
		try {
			RefFileType refFile = EAOManager.INSTANCE.getRefFileTypeEAO()
					.findById(Long.parseLong(fileId));
			TestUnitDescription testUnitDescription = EAOManager.INSTANCE
					.getTestUnitDescriptionEAO().findByRefFile(refFile);

			String folderPath = TestUnitHelper
					.getTestUnitFolderPath(testUnitDescription);
			File file = new File(folderPath + "/" + refFile.getSrc());
			for (RefFileType r : testUnitDescription.getSubject()
					.getResourceFiles()) {
				if (r.getId() == refFile.getId()) {
					testUnitDescription.getSubject().getResourceFiles()
							.remove(r);
					break;
				}
			}
			EAOManager.INSTANCE.getTestUnitDescriptionEAO().persist(
					testUnitDescription);
			if (file.delete())
				logger.info(file.getName() + " is deleted!");
			else
				logger.warn(file.getName() + " cannot be deleted");
			EAOManager.INSTANCE.getRefFileTypeEAO().delete(refFile);
			return Response.ok(testUnitDescription).build();
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage());
			return Response.notModified(e.getLocalizedMessage()).build();
		}
	}
/**
 * Saves a test posted by a form. 
 * See config file (FORM_TESTUNIT_FORMFIELD_TESTUNITDESCRIPTION, FORM_TESTUNIT_FORMFIELD_CODE,FORM_TESTUNIT_FORMFIELD_TESTFILE)
 * @param req
 * @param sessionId
 * @return
 */
	@POST
	@Path("commit/{sessionId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response testPersist(@Context HttpServletRequest req,
			@PathParam("sessionId") String sessionId) {
		try {
			if (!TestingSessionService.INSTANCE
					.isAuthenticatedAsExpert(sessionId)) {
				logger.info("not appropriate permission for saving a test case. Need to be expert role");
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e2) {
			logger.warn("not authenticated session");
			return Response.status(Response.Status.GONE).build();
		}
		logger.info("OK you have permission for saving test case.");
		TestUnitDescription testUnitDescription = null;
		File testFolder = null;
		boolean isNew = true;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(req);
			FileItem descItem = null;
			FileItem codeItem = null;
			// process field items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (item.isFormField()) {
					if (item.getFieldName()
							.equals(ConfigService.INSTANCE
									.getConfigParam(ConfigService.FORM_TESTUNIT_FORMFIELD_TESTUNITDESCRIPTION))) {
						descItem = item;
					}
					if (item.getFieldName()
							.equals(ConfigService.INSTANCE
									.getConfigParam(ConfigService.FORM_TESTUNIT_FORMFIELD_CODE))) {
						codeItem = item;
					}

				}
			}
			String unitjson = descItem.getString();
			logger.debug("unitjson: " + unitjson);
			ObjectMapper mapper = new ObjectMapper();
			testUnitDescription = mapper.readValue(unitjson,
					TestUnitDescription.class);
			if (testUnitDescription.getId() > 0)
				isNew = false;
			TechniqueEAO reqEAO = new TechniqueEAO();
			Technique requirement = reqEAO.getRequirementsByTerm(
					testUnitDescription.getTechnique().getNameId()).get(0);
			testUnitDescription.setTechnique(requirement);
			// store in meta db with generated id if not existing
			if (testUnitDescription.getId() < 1) // for json
				testUnitDescription = TestsService.INSTANCE
						.insertTestUnit(testUnitDescription);
			// store files in file system
			String path = TestUnitHelper
					.getTestUnitFolderPath(testUnitDescription);
			testFolder = new File(path);
			if (!testFolder.exists())
				InOutUtils.makeDir(path);
			RefFileType refTestFile = new RefFileType();
			if (codeItem.isFormField()) {
				String fileName = testUnitDescription.getTestUnitId() + "."
						+ "html";
				refTestFile.setSrc(fileName);
				testUnitDescription.getSubject().setTestFile(refTestFile);
				File fullFile = new File(fileName);
				File savedFile = new File(path, fullFile.getName());
				// store files in testFolder
				logger.info("Saving Test File with Name:" + fileName);
				codeItem.write(savedFile);
			}
			// save resource files
			Iterator<FileItem> iter1 = items.iterator();
			while (iter1.hasNext()) {
				FileItem item = iter1.next();
				String fileName = item.getName();
				RefFileType f = new RefFileType();
				if (!item.isFormField() && item.getSize() > 0) {
					String ext = fileName
							.substring(fileName.lastIndexOf('.') + 1);
					if (item.getFieldName()
							.equals(ConfigService.INSTANCE
									.getConfigParam(ConfigService.FORM_TESTUNIT_FORMFIELD_TESTFILE))) {
						fileName = testUnitDescription.getTestUnitId() + "."
								+ ext;
						f.setSrc(fileName);
						testUnitDescription.getSubject().setTestFile(f);
						logger.debug("FORM_TESTUNIT_FORMFIELD_TESTFILE");
					} else {
						f.setSrc(fileName);
						testUnitDescription.getSubject().getResourceFiles()
								.add(f);
						logger.debug("else FORM_TESTUNIT_FORMFIELD_TESTFILE");
					}
					File fullFile = new File(fileName);
					File savedFile = new File(path, fullFile.getName());
					// store files in testFolder
					logger.info("Resource File Name:" + fileName);
					item.write(savedFile);
				}
			}
			testUnitDescription = EAOManager.INSTANCE
					.getTestUnitDescriptionEAO().persist(testUnitDescription);
			logger.info("testUnitDescription saved : "
					+ testUnitDescription.getTestUnitId());

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			if (isNew) {
				try {
					EAOManager.INSTANCE.getTestUnitDescriptionEAO().delete(
							testUnitDescription);
				} catch (ASBPersistenceException e1) {
					logger.error(e1.getLocalizedMessage());
				}
				InOutUtils.deleteFolderContents(testFolder);
			}
			return Response.notModified(e.getLocalizedMessage()).build();
		}
		return Response.status(Status.OK).entity(testUnitDescription).build();
	}
}
