/**
 * 
 */
package org.w3c.wai.accessdb.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.eao.TestUnitDescriptionEAO;
import org.w3c.wai.accessdb.helpers.TestResultFilterHelper;
import org.w3c.wai.accessdb.helpers.TestUnitHelper;
import org.w3c.wai.accessdb.jaxb.TestResultFilter;
import org.w3c.wai.accessdb.jaxb.TreeNodeData;
import org.w3c.wai.accessdb.om.Technique;
import org.w3c.wai.accessdb.om.testunit.RefFileType;
import org.w3c.wai.accessdb.om.testunit.Step;
import org.w3c.wai.accessdb.om.testunit.Subject;
import org.w3c.wai.accessdb.om.testunit.TestProcedure;
import org.w3c.wai.accessdb.om.testunit.TestUnitDescription;
import org.w3c.wai.accessdb.sync.om.ExportTestFile;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.w3c.wai.accessdb.utils.InOutUtils;
import org.w3c.wai.accessdb.utils.JAXBUtils;

/**
 * @author evangelos.vlachogiannis
 * @since 16.04.12
 * 
 * 
 * 
 */
public enum TestsService {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(TestsService.class);
	private TestUnitDescriptionEAO eao = EAOManager.INSTANCE
			.getTestUnitDescriptionEAO();
	private String targetRootExportPath = "/tmp/accessdbexport/";

	//FIXME
	private String sourceExportTestFilesPath = "/var/www/testfiles";

	public List<TestUnitDescription> findAllTestDescriptions() {
		return eao.findAll();
	}

	public TreeNodeData getTestsPerTechniqueNode(TestResultFilter filter) {
		List<Technique> l = new ArrayList<Technique>();
		String q = TestResultFilterHelper.buildHQL4Technique(filter);
		logger.info(q);
		try {
			l = EAOManager.INSTANCE.getObjectEAO().doSimpleSelectOnlyQuery(q);
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}
		TreeNodeData rootNode = new TreeNodeData();
		logger.info("getting Techniques from filter: " + l.size());
		rootNode.setNoOfChildren(l.size());
		rootNode.setSubselector(true);
		for (Technique technique : l) {
			TreeNodeData nodeTechnique = new TreeNodeData();
			nodeTechnique.setType(Technique.class.getSimpleName());
			nodeTechnique.setId(String.valueOf(technique.getId()));
			nodeTechnique.setLabel(technique.getNameId() + ": "
					+ technique.getTitle());
			nodeTechnique.setDescription(technique.getTitle());
			nodeTechnique.setValue(String.valueOf(technique.getNameId()));
			// get tests
			List<TestUnitDescription> tunits = EAOManager.INSTANCE
					.getTestunitEAO().findByTechnique(technique.getNameId());
			nodeTechnique.setNoOfChildren(tunits.size());
			nodeTechnique.setSubselector((tunits.size() > 0));
			for (TestUnitDescription tu : tunits) {
				TreeNodeData nodeTest = new TreeNodeData();
				nodeTest.setType(TestUnitDescription.class.getSimpleName());
				nodeTest.setId(String.valueOf(tu.getTestUnitId()));
				nodeTest.setLabel(tu.getTitle());
				nodeTest.setDescription(tu.getTitle());
				nodeTest.setValue(String.valueOf(tu.getTestUnitId()));
				nodeTest.setNoOfChildren(0);
				nodeTest.setSubselector(false);
				nodeTechnique.getChildren().add(nodeTest);
			}
			if (tunits.size() > 0)
				rootNode.getChildren().add(nodeTechnique);
		}
		return rootNode;
	}

	public TestUnitDescription insertOrUpdateTestUnit(
			TestUnitDescription testUnitDescription)
			throws ASBPersistenceException {
		TestUnitDescription tud = null;
		if (testUnitDescription.getTestUnitId() == null
				|| testUnitDescription.getTestUnitId().equals("")
				|| testUnitDescription.getTestUnitId().trim()
						.equalsIgnoreCase("null")) // for json
			tud = this.insertTestUnit(testUnitDescription);
		else
			tud = this.updateTestUnit(testUnitDescription);
		return tud;
	}

	public TestUnitDescription updateTestUnit(
			TestUnitDescription testUnitDescription)
			throws ASBPersistenceException {
		TestUnitDescription tu = eao.findByTestUnitId(testUnitDescription
				.getTestUnitId());
		testUnitDescription.setId(tu.getId());
		TestProcedure p = testUnitDescription.getTestProcedure();
		Set<Step> steps = p.getStep();
		Set<Step> steps_ = new HashSet<Step>();
		for (Iterator<Step> iterator = steps.iterator(); iterator.hasNext();) {
			Step step = iterator.next();
			Step step1 = (Step) EAOManager.INSTANCE.getObjectEAO()
					.persist(step);
			steps_.add(step1);
		}
		p.getStep().clear();
		p.getStep().addAll(steps_);

		Subject s = testUnitDescription.getSubject();
		if (s.getTestFile() != null) {
			RefFileType testFile = s.getTestFile();
			testFile = EAOManager.INSTANCE.getRefFileTypeEAO()
					.persist(testFile);
			s.setTestFile(testFile);
			List<RefFileType> res = s.getResourceFiles();
			List<RefFileType> res_ = new ArrayList<RefFileType>();
			for (Iterator<RefFileType> iterator = res.iterator(); iterator
					.hasNext();) {
				RefFileType refFileType = iterator.next();
				RefFileType refFileType1 = EAOManager.INSTANCE
						.getRefFileTypeEAO().persist(refFileType);
				res_.add(refFileType1);
			}
			s.getResourceFiles().clear();
		}
		testUnitDescription.setTestProcedure(p);
		testUnitDescription.setSubject(s);
		return eao.persist(testUnitDescription);
	}

	public TestUnitDescription insertTestUnit(
			TestUnitDescription testUnitDescription)
			throws ASBPersistenceException {
		testUnitDescription = eao.persist(testUnitDescription);
		testUnitDescription = TestUnitHelper
				.generateTestUnitDescriptionId(testUnitDescription);
		testUnitDescription = eao.persist(testUnitDescription);
		return testUnitDescription;
	}

	public boolean deleteTestUnit(long id) {
		try {
			TestUnitDescription tu = EAOManager.INSTANCE
					.getTestUnitDescriptionEAO().findById(id);
			return this.deleteTestUnit(tu);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return false;
		}
	}

	public boolean deleteTestUnit(String id) {
		try {
			TestUnitDescription tu = EAOManager.INSTANCE
					.getTestUnitDescriptionEAO().findByTestUnitId(id);
			return this.deleteTestUnit(tu);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return false;
		}
	}

	public boolean deleteTestUnit(TestUnitDescription tu) {
		try {
			List<RefFileType> resources = tu.getSubject().getResourceFiles();
			logger.debug("Deleting test with id: " + tu.getId());
			if (tu == null) {
				logger.warn("id not found");
				return false;
			}
			String path = TestUnitHelper.getTestUnitFolderPath(tu);
			logger.debug("deleting path " + path);
			File folder = new File(path);
			if (folder.exists()) {
				InOutUtils.deleteFolderContents(folder);
				folder.delete();
				logger.debug("deleting path " + path);
			}
			EAOManager.INSTANCE.getTestUnitDescriptionEAO().delete(tu);
			for (RefFileType r : resources) {
				EAOManager.INSTANCE.getRefFileTypeEAO().delete(r);
			}
			logger.debug("db entry removed");
			return true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return false;
		}

	}

	public TestUnitDescription insertTestUnitAndFiles(
			TestUnitDescription testUnitDescription, byte[] testFile,
			String testFileExtension, HashMap<String, byte[]> resourceFiles)
			throws ASBPersistenceException {
		TestUnitDescription tu = eao.persist(testUnitDescription);
		tu = TestUnitHelper.generateTestUnitDescriptionId(tu);
		tu = eao.persist(tu);
		String path = TestUnitHelper.getTestUnitFolderPath(testUnitDescription);
		File folder = new File(path);
		if (!folder.exists())
			InOutUtils.makeDir(path);
		else
			InOutUtils.deleteFolderContents(folder);
		// for test file
		String fileName = testUnitDescription.getTestUnitId() + "."
				+ testFileExtension;
		RefFileType f = new RefFileType();
		f.setSrc(fileName);
		testUnitDescription.getSubject().setTestFile(f);
		File fullFile = new File(fileName);
		File savedFile = new File(path, fullFile.getName());
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(savedFile);
			fos.write(testFile);
		} catch (FileNotFoundException e1) {
			logger.debug(e1.getLocalizedMessage());
		} catch (IOException e) {
			logger.debug(e.getLocalizedMessage());
		}

		// resource files
		Iterator<?> iter = resourceFiles.entrySet().iterator();
		while (iter.hasNext()) {
			String fName = (String) iter.next();
			byte[] fContents = resourceFiles.get(fName);
			f.setSrc(fileName);
			testUnitDescription.getSubject().getResourceFiles().add(f);
			File fFile = new File(fName);
			File sFile = new File(path, fFile.getName());
			FileOutputStream foos;
			try {
				foos = new FileOutputStream(sFile);
				foos.write(fContents);
			} catch (FileNotFoundException e1) {
				logger.debug(e1.getLocalizedMessage());
			} catch (IOException e) {
				logger.debug(e.getLocalizedMessage());
			}

		}
		tu = EAOManager.INSTANCE.getTestUnitDescriptionEAO().persist(
				testUnitDescription);
		return tu;
	}

	public boolean importTests(String indexFilePath)
			throws ASBPersistenceException, JAXBException, ClassNotFoundException {
		File indexF = new File(indexFilePath);
		ExportTestFile indexFile = new ExportTestFile();
		indexFile = (ExportTestFile) JAXBUtils.fileToObject(indexF,
				ExportTestFile.class);
		if (indexFile == null) {
			logger.error("Cannot load index file for importing");
			return false;
		}
		String rootImportPath = indexF.getParent() + "/";
		TestUnitDescription test = null;
		for (String testPath : indexFile.getTests()) {
			String metaPath = rootImportPath + testPath + "/meta.xml";
			File metaFile = null;
			try {
				metaFile = new File(metaPath);
				TestUnitDescription tu = (TestUnitDescription) JAXBUtils
						.fileToObject(metaFile, TestUnitDescription.class);
				if (EAOManager.INSTANCE.getTestUnitDescriptionEAO()
						.findByTestUnitId(tu.getTestUnitId()) != null) {
					logger.warn(tu.getTestUnitId()
							+ " is already there.. skipping.. ");
					continue;
				}
				String techId = tu.getTechnique().getNameId();
				Technique technique = EAOManager.INSTANCE.getTechniqueEAO()
						.findByNameId(techId);
				if (technique == null) {
					logger.warn(techId + " technique not exists.. skipping.. ");
					continue;
				}
				test = TestUnitHelper.cloneTest(tu);
				test.setTechnique(technique);
				test = EAOManager.INSTANCE	.getTestUnitDescriptionEAO().persist(test);
				if (test != null && test.getId() > 0) {
					// copy files
					File sourceTestFile = new File(rootImportPath + testPath + "/");
					File targetTestFolder = new File(
							TestUnitHelper.getTestUnitFolderPath(test));
					if (!targetTestFolder.exists())
						InOutUtils.makeDir(targetTestFolder.getAbsolutePath());
					//copy test file
					String srcPath = sourceTestFile.getAbsolutePath()+ "/" + test.getSubject().getTestFile().getSrc();
					String destPath = targetTestFolder.getAbsolutePath() + "/" + test.getSubject().getTestFile().getSrc();
					logger.info("cp "+srcPath+" --> "+destPath);
					File srcFile = new File(srcPath);
					File destFile = new File(destPath);
					FileUtils.copyFile(srcFile, destFile);
					List<RefFileType> rFs = test.getSubject().getResourceFiles();
					for (RefFileType rf : rFs) {
						if(rf.getSrc()==null || rf.getSrc().trim().length()<2)
							continue;
						srcPath = sourceTestFile.getAbsolutePath() + "/" + rf.getSrc();
						destPath = targetTestFolder.getAbsolutePath()  + "/" + rf.getSrc();
						logger.info("cp "+srcPath+" --> "+destPath);
						srcFile = new File(srcPath);
						destFile = new File(destPath);
						FileUtils.copyFile(srcFile, destFile);
					}
				}
			} catch (IOException e) {
				logger.error("Error while copying test files: " + metaPath);
				logger.debug(e.getLocalizedMessage());
				this.deleteTestUnit(test);
				logger.error("deleting also the meta for: " + test.getId());
				continue;
			}
		}
		return true;
	}

	public void test2XMLFile(TestUnitDescription test) throws IOException,
			JAXBException {
		String path = TestUnitHelper.getTestUnitFolderPath(test);
		JAXBUtils.objectToXmlFile(path + "/" + test.getTestUnitId() + ".xml",
				test);
	}

	public void exportAllTests() {
		targetRootExportPath = ConfigService.INSTANCE
				.getConfigParam("targetRootExportPath");
		logger.info("Taking config param targetRootExportPath="
				+ targetRootExportPath);
		sourceExportTestFilesPath = ConfigService.INSTANCE
				.getConfigParam("sourceExportTestFilesPath");
		logger.info("Taking config param sourceExportTestFilesPath="
				+ sourceExportTestFilesPath);
		Date date = new Date();
		ExportTestFile indexFile = new ExportTestFile();
		indexFile.setCreated(date);
		String exportId = "accessdbDataExport" + date.getTime();
		String targetExportPath = targetRootExportPath + exportId;
		File folder = null;
		try {
			folder = InOutUtils.createFolder(targetExportPath);
			logger.info("Created export folder: " + targetExportPath);
		} catch (Exception e) {
			logger.error("Cannot create export folder: " + targetExportPath);
			logger.error(e.getLocalizedMessage());
		}
		InOutUtils.deleteFolderContents(folder);

		List<TestUnitDescription> tests = EAOManager.INSTANCE
				.getTestUnitDescriptionEAO().findAll();
		for (TestUnitDescription test : tests) {
			String testpath = null;
			try {
				String testId = test.getTestUnitId();
				String techId = test.getTechnique().getWebTechnology()
						.getNameId();
				InOutUtils.createFolder(targetExportPath + "/" + techId);
				testpath = techId + "/" + testId;
				File targetExportFile = new File(targetExportPath + "/"
						+ testpath);
				File sourceExportTestFilesFile = new File(
						sourceExportTestFilesPath + "/" + testpath);
				InOutUtils.copyFolder(sourceExportTestFilesFile,
						targetExportFile);
				JAXBUtils.objectToXmlFile(targetExportFile + "/meta.xml", test);
				indexFile.getTests().add(testpath);
			} catch (IOException e) {
				logger.error("Error while copying test files: " + testpath);
				logger.debug(e.getLocalizedMessage());
				continue;
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			JAXBUtils.objectToXmlFile(targetExportPath + "/index.xml",
					indexFile);
			InOutUtils.zip(new File(targetExportPath), new File(
					targetRootExportPath + "/" + exportId + ".zip"));
		} catch (IOException e) {
			logger.error("Error while writing export index file");
			logger.debug(e.getLocalizedMessage());
		} catch (JAXBException e) {
			logger.error("Error while writing export index file");
			logger.debug(e.getLocalizedMessage());
		}
	}

	public void deleteDeepTestUnitById(String nameId)
			throws ASBPersistenceException {
		TestResultsService.INSTANCE.deleteTestResultsByTestNameId(nameId);
		this.deleteTestUnit(nameId);
	}
}
