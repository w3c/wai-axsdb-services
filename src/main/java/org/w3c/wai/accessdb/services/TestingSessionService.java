package org.w3c.wai.accessdb.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.wai.accessdb.eao.EAOManager;
import org.w3c.wai.accessdb.jaxb.LoginData;
import org.w3c.wai.accessdb.jaxb.TestResultSimple;
import org.w3c.wai.accessdb.jaxb.TestingSession;
import org.w3c.wai.accessdb.om.User;
import org.w3c.wai.accessdb.utils.ASBPersistenceException;
import org.w3c.wai.accessdb.utils.AuthenticationException;

public enum TestingSessionService {
	INSTANCE;
	private static final Logger logger =  LoggerFactory.getLogger(TestingSessionService.class);
	private Map<String, TestingSession> sessions = null;
	private Map<String, User> authenicatedSessions = new HashMap<String, User>();

	TestingSessionService() {
		sessions = new HashMap<String, TestingSession>();
	}

	public TestingSession putSession(TestingSession session) {
		if(session.getSessionId()==null)
			session.setSessionId(UUID.randomUUID().toString());
		session.setLastAccessed(new Date());
		this.sessions.put(session.getSessionId(), session);
		return session;
	}

	public void removeSession(String sessionId) {
		this.sessions.remove(sessionId);
	}

	public TestingSession getSession(String sessionid) {
		TestingSession s = null;
		try{
			s =  this.sessions.get(sessionid);
		}
		catch (Exception e) {
           
		}
		return s;
	}

	// TODO: Schedule this
	// ConfigService.INSTANCE.getConfigParam(ConfigService.TESTING_SESSION_TASK_INTERVAL);
    public void clean()
    {
        String diff = ConfigService.INSTANCE
                .getConfigParam(ConfigService.TESTING_SESSION_TIMEOUT);
        Date date = new Date();
        long now = date.getTime();
        for (TestingSession session : sessions.values())
        {
            if (now - session.getLastAccessed().getTime() > Long
                    .parseLong(diff))
            {
                logger.info("cleaning sessions for id: "
                        + session.getSessionId());
                this.sessions.remove(session);
                this.authenicatedSessions.remove(session.getSessionId());
            }
        }
    }

	public boolean isAuthenticated(String sessionid) {
		if (this.authenicatedSessions.containsKey(sessionid))
			return true;
		return false;
	}
	
	public boolean isAuthenticatedAsExpert(String sessionid) throws AuthenticationException {		
		return AuthenicateService.isAuthenticatedAsExpert(this.getUser(sessionid));
    }
	public boolean isAuthenticatedAsAdmin(String sessionid) throws AuthenticationException {
		return AuthenicateService.isAuthenticatedAsAdmin(this.getUser(sessionid));
    }
	public User getUser(String sessionid) throws AuthenticationException { 
		if (this.authenicatedSessions.containsKey(sessionid))
			return this.authenicatedSessions.get(sessionid);
		else
			throw new AuthenticationException(Response.Status.UNAUTHORIZED);
	}

	public TestingSession login(LoginData lData) throws ASBPersistenceException {
		TestingSession session = this.getSession(lData.getSessionId());
		if(session==null)
			session = TestingSessionService.INSTANCE.createNewSession();
		User user = AuthenicateService.INSTANCE.login(lData);
		if (user == null)
			return session;
		session.setUserId(user.getUserId());
		session.getUserRoles().clear();
		for (String role : user.getUserRoles()) {
			session.getUserRoles().add(role);
		}
		session.getUserTestingProfiles().addAll(user.getUserTestingProfiles());
		this.sessions.put(session.getSessionId(), session);
		this.authenicatedSessions.put(session.getSessionId(), user);
		return session;
	}

	public TestingSession createNewSession(){
		TestingSession s = new TestingSession();
		String sessionid=UUID.randomUUID().toString();
		while(!this.isSessionIdUnique(sessionid)){
			logger.debug("TestingSession not unique with id:" + sessionid);
			sessionid=UUID.randomUUID().toString();
		}
		logger.debug("New TestingSession with id:" + sessionid);
		s.setSessionId(sessionid.toString());
		return s;
	}
	private boolean isSessionIdUnique(String sessionid){
		if (this.authenicatedSessions.containsKey(sessionid))
			return false;
		if (this.sessions.containsKey(sessionid))
			return false;
		return true;
	}
	public TestingSession saveSessionData(String sessionid) {
		TestingSession session = this.sessions.get(sessionid);
		String unregistered_user_id=null;
		User user = this.authenicatedSessions.get(sessionid);
		if (user == null) // if user not authenticated
		{
			if(session.getSessionName()!=null)
				unregistered_user_id = session.getSessionName();
			else
				unregistered_user_id = session.getSessionId();
			user = EAOManager.INSTANCE.getUserEAO().findByUserId("anon");			
		}
		/*//move to profile service
		List<TestingProfile> allProfiles = new ArrayList<TestingProfile>();
		allProfiles.addAll(user.getTestingProfiles());
		
		for (TestingProfile p: session.getUserTestingProfiles()) {
			p.setType(ProfileType.USER);
			p = EAOManager.INSTANCE.getUserTestingProfileEAO().persist(p);
			if(p.getId()<0)
				user.getTestingProfiles().add(p);
		}
		user = EAOManager.INSTANCE.getUserEAO().persist(user);
		session.setUserTestingProfiles(user.getTestingProfiles());

*/		
/*		TestResultsBunch resultsBunch = new TestResultsBunch();
		resultsBunch.setUser(user);
		resultsBunch.setOptionalName(unregistered_user_id);
		resultsBunch.setDate(new Date());	
		List<TestResultSimple> resultSimples = session.getTestResultList();
		for (TestResultSimple res : resultSimples) {			
			TestingProfile p = res.getTestingProfile();
			TestResult testResult = new TestResult();
			testResult.setTestingProfile(p);
			testResult.setRunDate(new Date());
			testResult.setTestingProfile(p);
			testResult
					.setResultValue(Boolean.parseBoolean(res.getResultValue()));
			TestUnitDescription testUnitDescription = EAOManager.INSTANCE.getTestUnitDescriptionEAO().findByTestUnitId(res.getTestUnitId());
			testResult.setTestUnitDescription(testUnitDescription);
			testResult = EAOManager.INSTANCE.getTestResultEAO().persist(testResult);
			resultsBunch.getResults().add(testResult);
		}
		if(!resultsBunch.getResults().isEmpty())
			EAOManager.INSTANCE.getObjectEAO().persist(resultsBunch);*/
/*
		List<RatingSimple> toSaveRatings = session.getRatings();
		for (RatingSimple ratingSimple : toSaveRatings) {
			Rating rating = new Rating();
			rating.setRatedType(ratingSimple.getRatedType());
			long ratedId = 0;
			if(rating.getRatedType().equalsIgnoreCase(TestUnitDescription.class.getSimpleName()))
				ratedId = EAOManager.INSTANCE.getTestUnitDescriptionEAO().findByTestUnitId(ratingSimple.getRatedId()).getId();
			else
				ratedId = Long.parseLong(ratingSimple.getRatedId());
			rating.setRatedId(ratedId);
			rating.setRatingValue(Integer.parseInt(ratingSimple
					.getRatingValue()));
			rating.setComment(ratingSimple.getComment());
			rating.setExperience(Integer.parseInt(ratingSimple.getExperience()));
			EAOManager.INSTANCE.getRatingEAO().persist(rating);
		}
		
		session.getRatings().clear();*/
		session.setTestResultList(new ArrayList<TestResultSimple>());
		this.putSession(session);
		return session;	
	}
	
}
