package org.w3c.wai.accessdb.eao;



/**
 * @author evangelos.vlachogiannis@fit.fraunhofer.de
 * @since 12.05.12
 */
public enum EAOManager {
	INSTANCE;
	BaseEntityEAO objectEAO = new BaseEntityEAO();
	WebTechnologyEAO webTechnologyEAO = new WebTechnologyEAO();
	TechniqueEAO techniqueEAO = new TechniqueEAO();
	SuccessCriterioEAO criterioEAO = new SuccessCriterioEAO();
	ProductEAO productEAO = new ProductEAO();
	TestUnitDescriptionEAO testunitEAO = new TestUnitDescriptionEAO();
	RefFileTypeEAO refFileTypeEAO = new RefFileTypeEAO();
	TestResultEAO testResultEAO = new TestResultEAO();
	UserTestingProfileEAO userTestingProfileEAO = new UserTestingProfileEAO();
	RatingEAO ratingEAO = new RatingEAO();
	UserEAO userEAO = new UserEAO();
	PrincipleEAO principleEAO = new PrincipleEAO();
	GuidelineEAO guidelineEAO = new GuidelineEAO();
	PlatformEAO platformEAO = new PlatformEAO();
	AssistiveTechnologyEAO assistiveTechnologyEAO = new AssistiveTechnologyEAO();
	SuccessCriterioTechniqueRelationEAO criterioTechniqueRelationEAO = new SuccessCriterioTechniqueRelationEAO();
	TestResultsBunchEAO testResultsBunchEAO = new TestResultsBunchEAO();
	TestingProfileEAO testingProfileEAO =  new TestingProfileEAO();
	UAgentEAO uAgentEAO = new UAgentEAO();
	PluginEAO pluginEAO = new PluginEAO();
	
	public UAgentEAO getuAgentEAO()
    {
        return uAgentEAO;
    }
	public PluginEAO getPluginEAO()
    {
        return pluginEAO;
    }
	public TestingProfileEAO getTestingProfileEAO()
    {
        return testingProfileEAO;
    }
	public TestResultsBunchEAO getTestResultsBunchEAO() {
		return testResultsBunchEAO;
	}

	public AssistiveTechnologyEAO getAssistiveTechnologyEAO() {
		return assistiveTechnologyEAO;
	}

	public PlatformEAO getPlatformEAO() {
		return platformEAO;
	}

	public SuccessCriterioTechniqueRelationEAO getCriterioTechniqueRelationEAO() {
		return criterioTechniqueRelationEAO;
	}

	public SuccessCriterioEAO getCriterioEAO() {
		return criterioEAO;
	}

	public PrincipleEAO getPrincipleEAO() {
		return principleEAO;
	}

	public GuidelineEAO getGuidelineEAO() {
		return guidelineEAO;
	}

	public void setCriterioEAO(SuccessCriterioEAO criterioEAO) {
		this.criterioEAO = criterioEAO;
	}

	public UserEAO getUserEAO() {
		return userEAO;
	}

	public RatingEAO getRatingEAO() {
		return ratingEAO;
	}


	
	public UserTestingProfileEAO getUserTestingProfileEAO() {
		return userTestingProfileEAO;
	}

	EAOManager() {

	}

	public TestResultEAO getTestResultEAO() {
		return testResultEAO;
	}

	public BaseEntityEAO getObjectEAO() {
		return objectEAO;
	}

	public TechniqueEAO getTechniqueEAO() {
		return techniqueEAO;
	}



	public WebTechnologyEAO getWebTechnologyEAO() {
		return webTechnologyEAO;
	}

	public TestUnitDescriptionEAO getTestunitEAO() {
		return testunitEAO;
	}

	public ProductEAO getProductEAO() {
		return productEAO;
	}

	public TestUnitDescriptionEAO getTestUnitDescriptionEAO() {
		return testunitEAO;
	}

	public RefFileTypeEAO getRefFileTypeEAO() {
		return refFileTypeEAO;
	}

}
