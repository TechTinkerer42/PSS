/**
 * 
 */
package org.ets.pss.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.time.DateUtils;
import org.ets.ereg.security.user.ERegUser;
import org.ets.pss.controller.TaskController;
import org.ets.pss.persistence.dao.AsgndTskDao;
import org.ets.pss.persistence.dao.BlcCustomerDao;
import org.ets.pss.persistence.dao.CustBlbDao;
import org.ets.pss.persistence.dao.CustCrAsgndTskDocDao;
import org.ets.pss.persistence.dao.CustCrDao;
import org.ets.pss.persistence.dao.CustomerPromptDAO;
import org.ets.pss.persistence.dao.DocDao;
import org.ets.pss.persistence.dao.EtsCustDao;
import org.ets.pss.persistence.dao.MdaMimeTypDao;
import org.ets.pss.persistence.dao.TaskStatusTypeDAO;
import org.ets.pss.persistence.dao.TskDao;
import org.ets.pss.persistence.dao.UserTaskDao;
import org.ets.pss.persistence.dto.ContentManagementDTO;
import org.ets.pss.persistence.dto.PromptDTO;
import org.ets.pss.persistence.dto.TaskDTO;
import org.ets.pss.persistence.dto.TaskDraft;
import org.ets.pss.persistence.dto.TaskStepDTO;
import org.ets.pss.persistence.model.AsgndTsk;
import org.ets.pss.persistence.model.AsgndTskPK;
import org.ets.pss.persistence.model.BlcCustomer;
import org.ets.pss.persistence.model.CrBlb;
import org.ets.pss.persistence.model.CustCr;
import org.ets.pss.persistence.model.CustCrAsgndTskDoc;
import org.ets.pss.persistence.model.CustCrAsgndTskDocPK;
import org.ets.pss.persistence.model.CustCrPK;
import org.ets.pss.persistence.model.Doc;
import org.ets.pss.persistence.model.EtsCust;
import org.ets.pss.persistence.model.MdaMimeTyp;
import org.ets.pss.persistence.model.Prompt;
import org.ets.pss.persistence.model.PromptGuide;
import org.ets.pss.persistence.model.Task;
import org.ets.pss.persistence.model.TaskStatusType;
import org.ets.pss.persistence.model.TaskStep;
import org.ets.pss.persistence.model.Test;
import org.ets.pss.persistence.model.UserTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaltura.client.enums.KalturaMediaType;
import com.kaltura.client.types.KalturaMediaEntry;

/**
 * @author ashwin sampath->asampath@ets.org
 *
 */
@Service("taskService")
@Transactional(propagation= Propagation.REQUIRED, readOnly=true)
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskController.class);
	
	/* (non-Javadoc)
	 * @see org.ets.pss.service.TaskService#saveDoc(org.ets.pss.persistence.model.Doc)
	 */
	@Autowired
	@Qualifier("taskDao")
	TskDao taskDaoImpl; 
	
	@Autowired
	@Qualifier("userTaskDao")
	UserTaskDao userTaskDaoImpl;
	
	@Autowired
	@Qualifier("artifactDao")
	DocDao artifactDaoImpl;
	
	@Autowired
	@Qualifier("customerDao")
	EtsCustDao customerDaoImpl;
    
	@Autowired
	@Qualifier("mdaMimeTypDao")
	MdaMimeTypDao mdaMimeTypeDao;
	
	@Autowired
	@Qualifier("asgndTskDao")
	AsgndTskDao asgndTskDao;
	
	@Autowired
	@Qualifier("custCrDao")
	CustCrDao custCrDao;
	
	@Autowired
	@Qualifier("blcCustomerDao")
	BlcCustomerDao blcCustomerDao;
	
	@Autowired
	@Qualifier("artifactDao")
	DocDao docDao;	
	
	@Autowired
	@Qualifier("taskStatusTypeDAO")
	TaskStatusTypeDAO taskStatusTypeDAO;	
	
	@Autowired
	@Qualifier("customerPromptDAO")
	CustomerPromptDAO customerPromptDAO; 
	
	@Autowired
	@Qualifier("custBlbDao")
	CustBlbDao custBlbDao; 
	
	@Autowired
	@Qualifier("custCrAsgndTskDocDao")
	CustCrAsgndTskDocDao  custCrAsgndTskDocDao ; 
	
	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public void saveDoc(Doc doc) {		
    	artifactDaoImpl.create(doc);            	
	}
	
	
	
	public ContentManagementDTO uploadContent(boolean exists,ContentManagementDTO contentManagementVO) {
		logger.info("ContentManagementProviderServiceKulturaImpl.uploadContent called");	
		
		CRSContentUploadUtilServiceImpl uploadUtilService=new CRSContentUploadUtilServiceImpl();
		uploadUtilService.initUpload(exists,contentManagementVO);
		
		return contentManagementVO;
	} 
	

	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public boolean getVideoEntries(ContentManagementDTO dto,long customerId,long tskId, long promptId) {		
		
    	CustCrPK custCrPk = new CustCrPK();
    	custCrPk.setCustomerId(customerId);
    	custCrPk.setTskId(tskId);
    	custCrPk.setPrmptId(promptId);
    	
    	 List<String>videoEntries = custBlbDao.getCurrentVideoEntriesForPrompt(promptId, tskId,customerId);
    	
    	
    		         if(videoEntries != null && videoEntries.size()>0)
    			{
    				   for(String videoEntry:videoEntries)
    				{
    						KalturaMediaEntry entry = new KalturaMediaEntry();
    						entry.name = dto.getEntryName();
    						entry.mediaType = KalturaMediaType.VIDEO;
    						entry.referenceId =  dto.getReferenceID();
    						//entry.categories = "Portfolio Pilot";
    						entry.categoriesIds = "12683521";
    						
    						entry.id=videoEntry;
    						dto.setMediaEntry(entry);
    			
    		     	 }//end for
    				  
    				   return true;
    			}//end if
    	 else return false;
				
	}

	
	
	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	  public void saveVideoEntry(String entryID,long customerId,long tskId, long promptId) {		
		
    	CustCrPK custCrPk = new CustCrPK();
    	custCrPk.setCustomerId(customerId);
    	custCrPk.setTskId(tskId);
    	custCrPk.setPrmptId(promptId);
    	
    	CustCr custCr = custCrDao.get(CustCr.class, custCrPk);
    	if(custCr!=null)
    	{
    		Set<CrBlb> crBlbs = custCr.getCrBlbs();
    		
    		if(crBlbs.size()>0)
    		{
    		   for(CrBlb crBlb:crBlbs)
    		 {
    			   //if(entryID.equals(crBlb.getMediaId()))
    			//{
    				 crBlb.setMediaId(entryID);
    			     crBlb.setMdaTypCde("video");
    		    	 crBlb.setMdaFrmtTypCde("video");
    			//}
    		 }
    		   custCr.setCrBlbs(crBlbs);
    		}
          else
       {
    	  CrBlb crBlb = new CrBlb();
    	  crBlb.setCrBlb(null);
    	  crBlb.setMediaId(entryID);
    	 //crBlb.setRspBlbLctnAdr(entryID);
    	    	
    	 //TODO: Fill up not null values with default values. we will need to change these values to more appropriate values.
    	 crBlb.setCrPrtId(new BigDecimal(1));
    	 crBlb.setCrSeq(new BigDecimal(1));
    	 crBlb.setPgmCde(custCr.getTsk().getFrm().getTst().getPgm().getPgmCde());
    	 crBlb.setMdaTypCde("video");
    	 crBlb.setMdaFrmtTypCde("video");
    	 custCr.setCrBlbs(new HashSet<CrBlb>());
    	 custCr.addCrBlb(crBlb);
      }
    	    	
    	 custCrDao.update(custCr);
    	}//end if custCr!=null
    	
    	else if(custCr == null)
    	{	
			EtsCust customer = customerDaoImpl.get(EtsCust.class, customerId);
			
			AsgndTskPK asgndTskPK = new AsgndTskPK();
			asgndTskPK.setCustomerId(customerId);
			asgndTskPK.setTskId(tskId);
			
			AsgndTsk task = asgndTskDao.get(AsgndTsk.class,asgndTskPK);
			
			Prompt taskPrompt = null;
			
			if(task != null && task.getTsk() != null && task.getTsk().getFrm() != null && task.getTsk().getFrm().getPrmpts() != null && task.getTsk().getFrm().getPrmpts().size() > 0)
			{
				for(Prompt prompt:task.getTsk().getFrm().getPrmpts())
				{
					if(prompt.getPromptId() == promptId)
					{
						taskPrompt = prompt;
					}
				}
			}
	
	    	
	    	CrBlb crBlb = new CrBlb();
	    	crBlb.setCrBlb(null);
	    	crBlb.setMediaId(entryID);
	    	
	    	//TODO: Fill up not null values with default values. we will need to change these values to more appropriate values.
	    	crBlb.setCrPrtId(new BigDecimal(1));
	    	crBlb.setCrSeq(new BigDecimal(1));
	    	crBlb.setPgmCde(taskPrompt.getProgramCode());
	    	crBlb.setMdaTypCde("video");
	    	crBlb.setMdaFrmtTypCde("video");

	    	custCr = new CustCr();
	    	custCr.setId(custCrPk);	  
	    	
	    	custCr.setCrBlbs(new HashSet<CrBlb>());
	    	custCr.addCrBlb(crBlb);
	    	
	    	custCr.setEtsCust(customer);
	    	custCr.setTsk(task.getTsk());   
	    	
	    	
	    	custCr.setPrmpt(taskPrompt);
	    	
	    	custCrDao.create(custCr);
    	
    	}
    		
    	   
    	
    	
	}
    
	

	/* (non-Javadoc)
	 * @see org.ets.pss.service.TaskService#saveEssay(java.lang.String)
	 */
	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public void saveEssay(String editorText,long customerId,long tskId, long promptId) {		
		
    	CustCrPK custCrPk = new CustCrPK();
    	custCrPk.setCustomerId(customerId);
    	custCrPk.setTskId(tskId);
    	custCrPk.setPrmptId(promptId);
    	
    	CustCr custCr = custCrDao.get(CustCr.class, custCrPk);
    	if(custCr != null)
   	  {
   		Set<CrBlb> crBlbs = custCr.getCrBlbs();
   		//Iterator<CrBlb> it = custCr.getCrBlbs()
   		if(crBlbs.size()>0)
   		{
   		   for(CrBlb crBlb:crBlbs)
   		 {
   			crBlb.setCrBlb(editorText.getBytes());
   		 }
   		   custCr.setCrBlbs(crBlbs);
   		}
   		else{
   			byte[] editorBytes = editorText.getBytes();
   	    	CrBlb crBlb = new CrBlb();
   	    	crBlb.setCrBlb(editorBytes);
   	    	
   	    	//TODO: Fill up not null values with default values. we will need to change these values to more appropriate values.
   	    	crBlb.setCrPrtId(new BigDecimal(1));
   	    	crBlb.setCrSeq(new BigDecimal(1));
   	    	crBlb.setPgmCde(custCr.getTsk().getFrm().getTst().getPgm().getPgmCde());
   	    	crBlb.setMdaTypCde("text");
   	    	crBlb.setMdaFrmtTypCde("html");
   	    	custCr.setCrBlbs(new HashSet<CrBlb>());
   	    	custCr.addCrBlb(crBlb);
   	    	
   		   }
   		
   		custCrDao.update(custCr);
   		
   	}//end if(custCr != null)
    	if(custCr == null)
    	{	
			EtsCust customer = customerDaoImpl.get(EtsCust.class, customerId);
			
			AsgndTskPK asgndTskPK = new AsgndTskPK();
			asgndTskPK.setCustomerId(customerId);
			asgndTskPK.setTskId(tskId);
			
			AsgndTsk task = asgndTskDao.get(AsgndTsk.class,asgndTskPK);
			
			Prompt taskPrompt = null;
			
			if(task != null && task.getTsk() != null && task.getTsk().getFrm() != null && task.getTsk().getFrm().getPrmpts() != null && task.getTsk().getFrm().getPrmpts().size() > 0)
			{
				for(Prompt prompt:task.getTsk().getFrm().getPrmpts())
				{
					if(prompt.getPromptId() == promptId)
					{
						taskPrompt = prompt;
					}
				}
			}
	
	    	byte[] editorBytes = editorText.getBytes();
	    	CrBlb crBlb = new CrBlb();
	    	crBlb.setCrBlb(editorBytes);
	    	
	    	//TODO: Fill up not null values with default values. we will need to change these values to more appropriate values.
	    	crBlb.setCrPrtId(new BigDecimal(1));
	    	crBlb.setCrSeq(new BigDecimal(1));
	    	crBlb.setPgmCde(taskPrompt.getProgramCode());
	    	crBlb.setMdaTypCde("text");
	    	crBlb.setMdaFrmtTypCde("html");

	    	custCr = new CustCr();
	    	custCr.setId(custCrPk);	  
	    	
	    	custCr.setCrBlbs(new HashSet<CrBlb>());
	    	custCr.addCrBlb(crBlb);
	    	
	    	custCr.setEtsCust(customer);
	    	custCr.setTsk(task.getTsk());   
	    	
	    	
	    	custCr.setPrmpt(taskPrompt);
	    	
	    	custCrDao.create(custCr);
    	
    	}
    	   
    	
	}

	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public String saveDraft(TaskDraft taskDraft,long custId) {				
		
		long taskIdL = Long.parseLong(taskDraft.getTaskId());
		String hasLinks=taskDraft.getHasLinks();
		Map<String,String[]> remvoeDocsMap = taskDraft.getRemoveDocs();
		
		/*save essay Contents*/
		Map<String,String> essayMap = taskDraft.getEssayMap();
		if(essayMap != null && essayMap.size()>0)
		{
			for(String promptId:essayMap.keySet())
			{
				long promptIdL = Long.parseLong(promptId);
				String editorText = essayMap.get(promptId);
				saveEssay(editorText,custId,taskIdL,promptIdL);
			}
		}
		
		if(remvoeDocsMap != null && remvoeDocsMap.size()>0)
		{
		 	for(String promptId:remvoeDocsMap.keySet())
			{
				long promptIdL = Long.parseLong(promptId);
				String []removeDocs = remvoeDocsMap.get(promptId);
		              if(removeDocs!=null && removeDocs.length>0)
		              {
		            	  for(int j=0;j<removeDocs.length;j++)
		            	  {
		            	  String docid=(String)removeDocs[j];
		            	  long docIdL=Long.parseLong( docid);
		            	  System.out.println("the doc to be removed are " +docid);
		            	  custCrAsgndTskDocDao.removeCurrentDocsForPrompt(promptIdL, taskIdL, custId, docIdL);
		            	  }
		            	  
		              }
				
			}
		}
		
		AsgndTskPK asgndTskPK = new AsgndTskPK();
		asgndTskPK.setCustomerId(custId);
		asgndTskPK.setTskId(taskIdL);
		
		AsgndTsk asgndTsk = asgndTskDao.get(AsgndTsk.class, asgndTskPK);
		
		Set<CustCrAsgndTskDoc> custCrAsgndTskDocSet =new HashSet<CustCrAsgndTskDoc>();
		String[] docList=null;
		for(String promptId:taskDraft.getPromptDocs().keySet())
		{			
			long promptIdL = Long.parseLong(promptId);

			CustCrPK custCrPK = new CustCrPK();
			custCrPK.setPrmptId(promptIdL);
			custCrPK.setCustomerId(custId);
			custCrPK.setTskId(taskIdL);
			
			CustCr custCr = custCrDao.get(CustCr.class,custCrPK);
			
			docList  =  taskDraft.getPromptDocs().get(promptId);
			
			for(String docId:docList)
			{
				long docIdL = Long.parseLong(docId);
				Doc doc = docDao.get(Doc.class, docIdL);
				
				CustCrAsgndTskDoc custCrAsgndTskDoc = new CustCrAsgndTskDoc();
				
				CustCrAsgndTskDocPK custCrAsgndTskDocPK = new CustCrAsgndTskDocPK();
				custCrAsgndTskDocPK.setDocId(docIdL);
				custCrAsgndTskDocPK.setCustomerId(custId);
				custCrAsgndTskDocPK.setPrmptId(promptIdL);
				custCrAsgndTskDocPK.setTskId(taskIdL);
				
				custCrAsgndTskDoc.setId(custCrAsgndTskDocPK);
				custCrAsgndTskDoc.setCustCr(custCr);
				custCrAsgndTskDoc.setDoc(doc);
			
				//add it to list of asgnd task
				
				custCrAsgndTskDocSet.add(custCrAsgndTskDoc);
/*				asgndTsk.addCustCrAsgndTskDoc(custCrAsgndTskDoc);				
				custCrAsgndTskDocDao.create(custCrAsgndTskDoc);*/
				
			}

		}
		
		if(hasLinks.equals("false"))
	{
		asgndTsk.getCustCrAsgndTskDocs().clear();
	}
		/*if(hasLinks.equals("true")&&(!taskDraft.getPromptDocs().isEmpty()))
		{
			asgndTsk.getCustCrAsgndTskDocs().clear();
		}*/
		
	
		
		asgndTsk.getCustCrAsgndTskDocs().addAll(custCrAsgndTskDocSet);
		
		
		
		TaskStatusType draftTskStsTyp = taskStatusTypeDAO.get(TaskStatusType.class,"DRAFT");
		asgndTsk.setDocStsTyp(draftTskStsTyp);	
		
		asgndTsk.setDraftDte(new Date());
		
		
		asgndTskDao.update(asgndTsk);
		
		return "{\"success\":\"true\"}";
		
		
	}
	
	
	

	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public String submitTask(long taskId,long custId) {
				
		AsgndTskPK asgndTskPK = new AsgndTskPK();
		asgndTskPK.setCustomerId(custId);
		asgndTskPK.setTskId(taskId);
		
		AsgndTsk asgndTsk = asgndTskDao.get(AsgndTsk.class, asgndTskPK);
		
		TaskStatusType completedTskStsTyp = taskStatusTypeDAO.get(TaskStatusType.class,"CMPLD");
		asgndTsk.setDocStsTyp(completedTskStsTyp);
		asgndTsk.setSbmtDte(new Date());//ashwin fix for Admin Tasks Screen
		
		asgndTskDao.update(asgndTsk);
		
		return "success";

	}

	@SuppressWarnings("unchecked")
	public Set<Doc> getCustomerArtifacts(long customerId) {		
		return  artifactDaoImpl.getCustomerArtifacts(customerId);
	}

	public EtsCust getCustomer(long customerId) {		
		return customerDaoImpl.get(EtsCust.class, customerId);
	}

	public MdaMimeTyp lookupMime(String mime) {	
		return mdaMimeTypeDao.getByMimeTypeName(mime);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<AsgndTsk> generateTasksForAccessCode(ERegUser loggedInUser,Long customerId, String accessCode) {
		
		logger.debug("Customer ID  : " + customerId);
		EtsCust customer1 = customerDaoImpl.get(EtsCust.class, customerId);
		
		if( null == customer1){
			
			BlcCustomer bc = new BlcCustomer();
			bc.setCustomerId(customerId);
			bc.setFirstName(loggedInUser.getFirstName());//ashwin fix for blccustomer for admin page
			bc.setLastName(loggedInUser.getLastName());//ashwin fix for blccustomer for admin page
			
			
			blcCustomerDao.create(bc);
			
			EtsCust etscust = new EtsCust();			
			etscust.setCustomerId(customerId);
			etscust.setCustTypCde("TSTTK");
			etscust.setTaxExmptFlg("N");
			etscust.setIntrnlUsrFlg("N");
			etscust.setBlcCustomer(bc);
			
			
			customerDaoImpl.create(etscust);
			
		}
		
		
		List<Test> tests = (List<Test>) taskDaoImpl.findAll(Test.class);
		
		Set<Task> tasks = null;
		for( Test tst : tests){
			if( accessCode.equals(tst.getCandidateAccessCode())) {
				tasks = tst.getTasks();
			}
		}
		
		if( null != tasks){
			List<AsgndTsk> ret = new ArrayList<AsgndTsk>();
			for( Task tsk : tasks){
				AsgndTsk asgtsk = new AsgndTsk();
				asgtsk.setBkngId(new BigDecimal(1));
				asgtsk.setTstTkrTstId(new BigDecimal(1));
				asgtsk.setApntmtDt(new Date());
				asgtsk.setEtsApntmtId("122");
				asgtsk.setApptTmEntFlg("Y");
				
				asgtsk.setDocStsTyp(taskStatusTypeDAO.get(TaskStatusType.class, "NEW"));
				AsgndTskPK pk = new AsgndTskPK();
				pk.setCustomerId(customerId);
				pk.setTskId(tsk.getTaskId());
				asgtsk.setId(pk);
				asgndTskDao.create(asgtsk);
				ret.add(asgtsk);
			}
			return ret; 
		}
		return null;
	}
	
	public String getAdminTasks()
	{
		
		List<AsgndTsk>assignedTaskListstemp=asgndTskDao.getCurrentTasks();
		List<AsgndTsk>assignedTaskList  = new ArrayList<AsgndTsk>();
		
		for(AsgndTsk vAsgndTsk:assignedTaskListstemp)
    	{
			
			
    		if(vAsgndTsk.getDocStsTyp().getDocStsTypCde().equalsIgnoreCase("CMPLD"))
    		
    		{
    						
    			assignedTaskList.add(vAsgndTsk);
    			
    		}
    	}
		
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	Gson gson = gsonBuilder.registerTypeAdapter(AsgndTsk.class, new AsgndTskAdapter()).create();
    	String json= gson.toJson(assignedTaskList);
    	System.out.println("the json is "+json);
    	return json;
	}
	
	public AsgndTsk getAssignedTask(long customerId,long lTaskId)
	{
		EtsCust customer1 = customerDaoImpl.get(EtsCust.class, customerId);
		
		Set<AsgndTsk> assignedTaskList = customer1.getAsgndTsks();
		
    	for(AsgndTsk vAsgndTsk:assignedTaskList)
    	{
    		if(vAsgndTsk.getId().getTskId() == lTaskId)
    		{
    			vAsgndTsk.getDocStsTyp().getDocStsTypCde();
    			return vAsgndTsk;
    		}
    	}
		return null;
	}
		
	public Map<Long, String> getPromptForTask(long customerId,long lTaskId)
	{
		Prompt prompt = null;
    	Set<Prompt> prompts = new HashSet<Prompt>();
    	Map<Long, String> customerPromptResponses = new HashMap<Long, String>();
    	
    	
    	EtsCust customer = customerDaoImpl.get(EtsCust.class, customerId);
    	//Task task = new Task();
    	Set<AsgndTsk> assignedTaskList = customer.getAsgndTsks();
    	
    	for(AsgndTsk vAsgndTsk:assignedTaskList)
    	{
    		if(vAsgndTsk.getId().getTskId() == lTaskId)
    		{
    			Set<Prompt> prmts = vAsgndTsk.getTsk().getFrm().getPrmpts();
    			
    			for(Prompt prmpt:prmts)
    			{
    				prompt = new Prompt();
    				//long promptid=prmpt.getPromptId();
    				//long stepid=prmpt.getStepId();
    				//System.out.println("the prompt id is "+promptid+" and the step id is "+stepid);
   				    //prompt.setPromptId(promptid); 	
    				prompt.setTaskId(lTaskId);
    				
    				CustCr custCrForPrompt = null;
    				 java.util.Iterator<CustCr> it =  ((Set<CustCr>) customer.getCustCrs()).iterator();
    				 while(it.hasNext())
    				 {
    					 CustCr custCr = it.next();
    					 if(prmpt.getPromptId() == custCr.getPrmpt().getPromptId())
    					 {
    						 custCrForPrompt = custCr;
    					 }
    				 }
    				 
    				 if(custCrForPrompt == null)
    				 {
    					 
    				 }
    				 else
    				 {
    					
    				
	    				try {
	    					Set<CrBlb> crBlbs = custCrForPrompt.getCrBlbs();
	    					for(CrBlb crBlb:crBlbs)
	    					{		    					
	    						if( null == crBlb.getCrBlb()) {
	    							logger.error("No response " + prmpt.getPromptId() ) ; 
	    							customerPromptResponses.put(prmpt.getPromptId(), "No Response" );
	    							continue;
	    						}
//		    						prompt.setEssayContent(new String(crBlb.getCrBlb(),"UTF-8"));
	    						customerPromptResponses.put(prmpt.getPromptId(), new String(crBlb.getCrBlb(),"UTF-8"));
	    						
	    					}
	    					
						} catch (UnsupportedEncodingException e) {
							
							e.printStackTrace();
							logger.error("Could not decode essay encoding.");
						}
    				 }
    				
    				
    				prompts.add(prompt);
    				prompt = null;
    				
    			}
    			
    			return customerPromptResponses;
    		}
    	}
		return customerPromptResponses;
	}
	
	public Map<String, String> getVideosForTask(long customerId,long lTaskId)
	{
		Prompt prompt = null;
    	Set<Prompt> prompts = new HashSet<Prompt>();
    	
    	Map<String, String> customerVideoResponses = new HashMap<String, String>();
    	
    	EtsCust customer = customerDaoImpl.get(EtsCust.class, customerId);
    
    	Set<AsgndTsk> assignedTaskList = customer.getAsgndTsks();
    	
    	for(AsgndTsk vAsgndTsk:assignedTaskList)
    	{
    		if(vAsgndTsk.getId().getTskId() == lTaskId)
    		{
    			Set<Prompt> prmts = vAsgndTsk.getTsk().getFrm().getPrmpts();
    			
    			for(Prompt prmpt:prmts)
    			{
    				prompt = new Prompt();
    				
    				prompt.setTaskId(lTaskId);
    				
    				CustCr custCrForPrompt = null;
    				 java.util.Iterator<CustCr> it =  ((Set<CustCr>) customer.getCustCrs()).iterator();
    				 while(it.hasNext())
    				 {
    					 CustCr custCr = it.next();
    					 if(prmpt.getPromptId() == custCr.getPrmpt().getPromptId())
    					 {
    						 custCrForPrompt = custCr;
    					 }
    				 }
    				 
    				 if(custCrForPrompt == null)
    				 {
    					 
    				 }
    				 else
    				 {
    					
    				
	    				try {
	    					Set<CrBlb> crBlbs = custCrForPrompt.getCrBlbs();
	    					for(CrBlb crBlb:crBlbs)
	    					{		    					
	    						if(crBlb!=null && crBlb.getMdaTypCde().equals("video"))
	    						{
	    						 customerVideoResponses.put("video",crBlb.getMediaId());
	    						
	    						}
	    						
	    					}
	    					
						} catch (Exception e) {
							
							e.printStackTrace();
							logger.error("Could not load videos for Customer.");
						}
    				 }
    				
    				
    				prompts.add(prompt);
    				prompt = null;
    				
    			}
    			
    			return customerVideoResponses;
    		}
    	}
		return customerVideoResponses;
	}


	@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
	public List<UserTask> getUserTasks(long customerId) {
			
			EtsCust customer1 = customerDaoImpl.get(EtsCust.class, customerId);		
			if(customer1==null){return null;}
			Set<AsgndTsk> taskList = customer1.getAsgndTsks();
			if (taskList==null) {return null;}
			TaskStatusType activeTskStsTyp = taskStatusTypeDAO.get(TaskStatusType.class,"ACTIV");
			for(AsgndTsk asgndTsk:taskList)
			{
				
				if("NEW".equals(asgndTsk.getDocStsTyp().getDocStsTypCde()))
				{
					Date todayDate = new Date();				
					if (DateUtils.isSameDay(asgndTsk.getTsk().getTaskOpenDate(), todayDate) || asgndTsk.getTsk().getTaskOpenDate().before(todayDate)) {
						asgndTsk.setDocStsTyp(activeTskStsTyp);
						asgndTskDao.update(asgndTsk);
					}
				}
			}
			
			List<UserTask> userTasks = new ArrayList<UserTask>();
			
			UserTask userTask;
			
			for(AsgndTsk asgndTask:taskList)
			{
				Task task=asgndTask.getTsk();
				userTask = new UserTask();
				userTask.setTaskId(task.getTaskId());
				userTask.setDisplaySequenceId(task.getDisplaySequence());
				//userTask.setTaskId(""+asgndTask.getId().getTskId());
				userTask.setName(task.getTitle());
				//userTask.setName("Task "+asgndTask.getId().getTskId());
				userTask.setOpenDate(asgndTask.getTsk().getTaskOpenDate());
				userTask.setDeadline(asgndTask.getTsk().getTaskCloseDate());
				userTask.setLastSaved(asgndTask.getDraftDte());
				String status = asgndTask.getDocStsTyp().getDocStsTypCde();
				
				if(status.equalsIgnoreCase("NEW"))
				{
					userTask.setStatus("NEW");
				}
				else if(status.equalsIgnoreCase("ACTIV"))
				{
					userTask.setStatus("READY");
					userTask.setShowAsLink(true);
				}
				else if(status.equalsIgnoreCase("DRAFT"))
				{
					userTask.setStatus("IN PROGRESS");
					userTask.setShowAsLink(true);
				}
				else if(status.equalsIgnoreCase("CMPLD"))
				{
					userTask.setStatus("COMPLETED");
					userTask.setShowAsLink(true);
				}						
				
				userTasks.add(userTask);
			}
			Collections.sort(userTasks);
			 
			
			return userTasks;
		}
	
	public TaskDTO getTask(Long taskId){
        
        Task task = taskDaoImpl.get(Task.class, taskId);
        
        if( null == task ) return null;
        
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setInstructions(task.getInstructions());
        Test test = task.getTest();
        taskDTO.setTestName(test.getTstNam());
        
        List<TaskStep> steps = taskDaoImpl.findAll(TaskStep.class);
        List<Prompt> prompts = taskDaoImpl.findAll(Prompt.class);
        List<PromptGuide> promptGuides = taskDaoImpl.findAll(PromptGuide.class);
        
        Set<TaskStepDTO> stepDTOs = new TreeSet<TaskStepDTO>();
        taskDTO.setStepDTOs(stepDTOs);
        for(TaskStep step : steps ){
               
               if( taskDTO.getTaskId().equals(step.getTaskId())){
                      TaskStepDTO tsDTO = new TaskStepDTO();
                      
                      tsDTO.setStepID(step.getStepId());
                      tsDTO.setTitle(step.getTitle());
                      tsDTO.setInstructions(step.getInstructions());
                      
                      stepDTOs.add(tsDTO);
                      
                      
                      
                      Set<PromptDTO> promptDTOs = new TreeSet<PromptDTO>();
                      tsDTO.setPromptDTOs(promptDTOs);
                      for(Prompt prompt : prompts){
                            if( step.getTaskId().equals(prompt.getTaskId()) && step.getStepId().equals(prompt.getStepId())){
                                   PromptDTO promptDTO = new PromptDTO();
                                   
                                   promptDTO.setPromptId(prompt.getPromptId());                                   
                                   promptDTO.setMedia(prompt.getMedia()) ;                          
                                   promptDTO.setTitle(prompt.getTitle());
                                   promptDTO.setInstructions(prompt.getInstructions());
                                   promptDTO.setActivity(prompt.getActivity());
                                   Map<Integer, String> guides = promptDTO.getGuides();
                                   if( null == guides) {
                                          guides = new HashMap<Integer, String>();
                                          promptDTO.setGuides(guides);
                                   }
                                   for(PromptGuide pg : promptGuides){
                                          if( pg.getPromptId().equals(promptDTO.getPromptId())){
                                                 guides.put(pg.getDisplaySequence(), pg.getPromptText());
                                          }
                                   }
                                   promptDTOs.add(promptDTO);
                                   
                                   
                            }
                      }
               }
        }
        
        
        
        return taskDTO;
  }

/*
	public TaskDTO getTask(Long taskId,long customerId){
		
		//Task task = taskDaoImpl.get(Task.class, taskId);
		
    	//EtsCust customer = customerDaoImpl.get(EtsCust.class, customerId);
    	//Task task = new Task();
    	AsgndTsk assignedTask = getAssignedTask(taskId,customerId);
    	TaskDTO taskDTO = new TaskDTO();
    	
/*		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setTaskId(task.getTaskId());
		taskDTO.setTitle(task.getTitle());
		taskDTO.setInstructions(task.getInstructions());
		Test test = task.getTest();
		taskDTO.setTestName(test.getTstNam());
		
		List<TaskStep> steps = taskDaoImpl.findAll(TaskStep.class);
		List<Prompt> prompts = taskDaoImpl.findAll(Prompt.class);
		List<PromptGuide> promptGuides = taskDaoImpl.findAll(PromptGuide.class);
		
		Set<TaskStepDTO> stepDTOs = new TreeSet<TaskStepDTO>();
		taskDTO.setStepDTOs(stepDTOs);
		for(TaskStep step : steps ){
			
			if( taskDTO.getTaskId().equals(step.getTaskId())){
				TaskStepDTO tsDTO = new TaskStepDTO();
				
				tsDTO.setStepID(step.getStepId());
				tsDTO.setTitle(step.getTitle());
				tsDTO.setInstructions(step.getInstructions());
				
				stepDTOs.add(tsDTO);
				
				
				
				Set<PromptDTO> promptDTOs = new TreeSet<PromptDTO>();
				tsDTO.setPromptDTOs(promptDTOs);
				for(Prompt prompt : prompts){
					if( step.getTaskId().equals(prompt.getTaskId()) && step.getStepId().equals(prompt.getStepId())){
						PromptDTO promptDTO = new PromptDTO();
						
						promptDTO.setPromptId(prompt.getPromptId());
						promptDTO.setTitle(prompt.getTitle());
						promptDTO.setInstructions(prompt.getInstructions());
						promptDTO.setActivity(prompt.getActivity());
						Map<Integer, String> guides = promptDTO.getGuides();
						if( null == guides) {
							guides = new HashMap<Integer, String>();
							promptDTO.setGuides(guides);
						}
						for(PromptGuide pg : promptGuides){
							if( pg.getPromptId().equals(promptDTO.getPromptId())){
								guides.put(pg.getDisplaySequence(), pg.getPromptText());
							}
						}
						promptDTOs.add(promptDTO);
						
						
					}
				}
			}
		}*/
    	
			/*	taskDTO.setTaskId(taskId);
				taskDTO.setTitle(assignedTask.getTsk().getTitle());
				taskDTO.setInstructions(assignedTask.getTsk().getInstructions());
				Test test = assignedTask.getTsk().getTest();
				taskDTO.setTestName(test.getTstNam());
				
    			String statusCode = assignedTask.getDocStsTyp().getDocStsTypCde();
    			if(statusCode != null)
    			{
    				if(statusCode.equalsIgnoreCase("NEW"))
    				{
    					//task.setStatus(TaskStatus.NEW);
    					taskDTO.setStatus(1);
    				}
    				else if(statusCode.equalsIgnoreCase("ACTIV"))
    				{
    					//task.setStatus(TaskStatus.ACTIVE);
    					taskDTO.setStatus(2);
    				}
    				else if(statusCode.equalsIgnoreCase("DRAFT"))
    				{
    					//task.setStatus(TaskStatus.DRAFT);
    					taskDTO.setStatus(3);
    				}
    				else if(statusCode.equalsIgnoreCase("CMPLD"))
    				{
    					//task.setStatus(TaskStatus.COMPLETED);
    					taskDTO.setStatus(4);
    				}
    			}
    			
    			boolean taskReadOnly = (assignedTask.getDocStsTyp().getDocStsTypCde().equalsIgnoreCase("CMPLD"));
    			taskDTO.setReadOnly(taskReadOnly);
    			
				List<TaskStep> steps = taskDaoImpl.findAll(TaskStep.class);
				List<Prompt> prompts = taskDaoImpl.findAll(Prompt.class);
				List<PromptGuide> promptGuides = taskDaoImpl.findAll(PromptGuide.class);
				
				Set<TaskStepDTO> stepDTOs = new TreeSet<TaskStepDTO>();
				taskDTO.setStepDTOs(stepDTOs);
				for(TaskStep step : steps ){
					
					if( taskDTO.getTaskId().equals(step.getTaskId())){
						TaskStepDTO tsDTO = new TaskStepDTO();
						
						tsDTO.setStepID(step.getStepId());
						tsDTO.setTitle(step.getTitle());
						tsDTO.setInstructions(step.getInstructions());
						
						stepDTOs.add(tsDTO);
						
						
						
						Set<PromptDTO> promptDTOs = new TreeSet<PromptDTO>();
						tsDTO.setPromptDTOs(promptDTOs);
						for(Prompt prompt : prompts){
							if( step.getTaskId().equals(prompt.getTaskId()) && step.getStepId().equals(prompt.getStepId())){
								PromptDTO promptDTO = new PromptDTO();
								
								promptDTO.setPromptId(prompt.getPromptId());
								promptDTO.setTitle(prompt.getTitle());
								promptDTO.setInstructions(prompt.getInstructions());
								promptDTO.setActivity(prompt.getActivity());
								Map<Integer, String> guides = promptDTO.getGuides();
								if( null == guides) {
									guides = new HashMap<Integer, String>();
									promptDTO.setGuides(guides);
								}
								for(PromptGuide pg : promptGuides){
									if( pg.getPromptId().equals(promptDTO.getPromptId())){
										guides.put(pg.getDisplaySequence(), pg.getPromptText());
									}
								}
								promptDTOs.add(promptDTO);
								
								
							}
						}
					}
				}


    	return taskDTO;
	}*/
	public Map<Long, String> getCustomerPromptsForTask(Long customerId, Long taskId){
		return customerPromptDAO.getCustomerPromptsForTask(customerId, taskId);
	}



}
