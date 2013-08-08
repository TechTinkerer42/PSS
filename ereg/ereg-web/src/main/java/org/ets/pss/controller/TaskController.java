package org.ets.pss.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.ets.ereg.common.enums.RoleEnum;
import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.ets.pss.persistence.dto.ContentManagementDTO;
import org.ets.pss.persistence.dto.TaskDTO;
import org.ets.pss.persistence.dto.TaskDraft;
import org.ets.pss.persistence.model.AsgndTsk;
import org.ets.pss.persistence.model.Doc;
import org.ets.pss.persistence.model.EtsCust;

import org.ets.pss.persistence.model.UserTask;
import org.ets.pss.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang.StringUtils;

/**
 * @author ashwin sampath->asampath@ets.org
 *
 */

@Controller("task-controller")
@RequestMapping(value="/pss/task/")
@SessionAttributes({"customer","task"})
public class TaskController {
	private static final Logger logger = LoggerFactory
			.getLogger(TaskController.class);
	@Autowired
	@Qualifier("taskService")
	TaskService taskServiceImpl;
	
    @RequestMapping(value = "/goto", method = RequestMethod.GET)
    public String index(String taskId, @LoggedInUser ERegUser loggedInUser, Model model) {
    	
    	System.out.println("taskId "+ taskId);
    	long lTaskId = Long.parseLong(taskId);
    
    	TaskDTO task = taskServiceImpl.getTask(lTaskId);
    	
    	if( null == task ) throw new RuntimeException("Invalid Task Id");
    	
    	model.addAttribute("task",task);
    	 AsgndTsk  asgndTsk=taskServiceImpl.getAssignedTask(loggedInUser.getId(), lTaskId);
    	
    	model.addAttribute("customerTask", asgndTsk);
    	
  	
    	Map<Long, String> prompts = taskServiceImpl.getPromptForTask(loggedInUser.getId(), lTaskId);
    	Map<String, String> videomap = taskServiceImpl.getVideosForTask(loggedInUser.getId(), lTaskId);
    	model.addAttribute("promptResponses", prompts);
    	
    	if(videomap!=null)
    	{
    	model.addAttribute("videoResponses", videomap);
    	}
//    	
//    	model.addAttribute("prompts",prompts);    	 			
//    	
//    	model.addAttribute("uploadItem",new UploadItem());
//      	
//    	Set<Doc> cusotmerArtifacts = taskServiceImpl.getCustomerArtifacts(loggedInUser.getId());
//    	
//    	model.addAttribute("artifacts",cusotmerArtifacts);
    	    	
        return "task4";
        
    }
    
    
	
    @RequestMapping(value = "/gotoAdmin", method = RequestMethod.GET)
    public String getReadOnlyTasks(String taskId,String userId, @LoggedInUser ERegUser loggedInUser, Model model) {
    	
    	System.out.println("taskId "+ taskId);
    	long lTaskId = Long.parseLong(taskId);
    	long lUserId = Long.parseLong(userId);
    
    	TaskDTO task = taskServiceImpl.getTask(lTaskId);
    	
    	if( null == task ) throw new RuntimeException("Invalid Task Id");
    	
    	model.addAttribute("task",task);
    	 AsgndTsk  asgndTsk=taskServiceImpl.getAssignedTask(lUserId, lTaskId);
    	
    	model.addAttribute("customerTask", asgndTsk);
    	
  	
    	Map<Long, String> prompts = taskServiceImpl.getPromptForTask(lUserId, lTaskId);
    	Map<String, String> videomap = taskServiceImpl.getVideosForTask(lUserId, lTaskId);
    	model.addAttribute("promptResponses", prompts);
    	
    	if(videomap!=null)
    	{
    	model.addAttribute("videoResponses", videomap);
    	}
//    	
//    	model.addAttribute("prompts",prompts);    	 			
//    	
//    	model.addAttribute("uploadItem",new UploadItem());
//      	
//    	Set<Doc> cusotmerArtifacts = taskServiceImpl.getCustomerArtifacts(loggedInUser.getId());
//    	
//    	model.addAttribute("artifacts",cusotmerArtifacts);
    	    	
        return "task4";
        
    }
    
    
    
    @RequestMapping(value = "/review/video", method = RequestMethod.GET)
    public String reviewVideo(@LoggedInUser ERegUser loggedInUser) {
    	System.out.println("coming here for reviewing video");
	
    	return "review";
		 
        
    }
  
    @RequestMapping(value = "/draft/admin", method = RequestMethod.GET)
    public @ResponseBody String getAdminTasks(@LoggedInUser ERegUser loggedInUser,Model model) {
    	System.out.println("coming here for getAdminTasks");
    	long customerId = loggedInUser.getId();
    	System.out.println("**** Customer Id : " + customerId);
    	String json=taskServiceImpl.getAdminTasks();
    	return json;
    }
        
    @RequestMapping(value = "/myPage", method = RequestMethod.GET)
    public String home(Model model, @LoggedInUser ERegUser loggedInUser) {
    	System.out.println("**********entering here*****************");
    	long customerId = loggedInUser.getId();
    	String code="";
    	System.out.println("**** Customer Id ************** : " + customerId);
    	System.out.println("before getting roles");
		List<UserTask> userTasks = taskServiceImpl.getUserTasks(customerId);
		if( null == userTasks || userTasks.size() == 0 ){
			model.addAttribute("newtasks", new EtsCust());
			return "enterCode";
		}else {
			model.addAttribute("userTasks", userTasks);
		}
		
		Set<RoleEnum> roles=loggedInUser.getEregUserRoles();
		
		for(RoleEnum role:roles)
    	{
    	   code=role.getCode();
    	   System.out.println("the role code is *****************"+code);
    	   
    	   
    	}
		if(code.equals("ROLE_CUSTOMER"))       
        {return "my_tasks";}
		else if(code.equals("ROLE_ADMIN"))
		{return "admintasks";}
		else return "my_tasks";
    }
    
    @RequestMapping(value = "/addNewTasks", method = RequestMethod.POST)
    @Transactional
	public String addNewTask( @ModelAttribute("newtasks") EtsCust customer, BindingResult result, @LoggedInUser ERegUser loggedInUser) {                                           
			 logger.debug("Adding a new tasks for : " + customer.getMilStsCde());  
			 
			 List<AsgndTsk> tasks = taskServiceImpl.generateTasksForAccessCode(loggedInUser,loggedInUser.getId(), customer.getMilStsCde());
		     
			 
			 return "redirect:/pss/task/myPage";
			 
	}  	
    
    @RequestMapping(value = "/upload/save", method = RequestMethod.POST)
    public @ResponseBody String save(@RequestParam MultipartFile file, @LoggedInUser ERegUser loggedInUser, Model model) {
    	System.out.println("Upload / Save ");    	    	
    	Doc doc = new Doc();
    	EtsCust etsCust = taskServiceImpl.getCustomer(loggedInUser.getId());
    	doc.setEtsCust(etsCust);
    	
    	String fileName = file.getOriginalFilename();    	
    	String fileExt = getFileExt(fileName);
    	String mime = null;
    	if(fileExt != null)
    	{
	    	if(fileExt.equalsIgnoreCase("txt"))
	    	{
	    		mime = "text/plain";
	    	}
	    	else if(fileExt.equalsIgnoreCase("pdf"))
	    	{
	    		mime = "application/pdf";
	    	}
	    	else if(fileExt.equalsIgnoreCase("doc") || fileExt.equalsIgnoreCase("dot"))
	    	{
	    		mime = "application/msword";
	    	}
	    	else if(fileExt.equalsIgnoreCase("xls"))
	    	{
	    		mime = "application/vnd.ms-excel";
	    	}
	    	else if(fileExt.equalsIgnoreCase("ppt") || fileExt.equalsIgnoreCase("pot") ||
	    			fileExt.equalsIgnoreCase("pps") || fileExt.equalsIgnoreCase("ppa"))
	    	{
	    		mime = "application/vnd.ms-powerpoint";
	    	}
	    	else
	    	{
	    		mime = "unknown";
	    	}
    	}
    	
    	doc.setRspSrcLctnNam(fileName);    	
    	doc.setMdaMimeTyp(taskServiceImpl.lookupMime(mime));
    	
    	try {
    		doc.setDocBlb(file.getBytes());    		
		} catch (IOException e) {			
			logger.error("Could not read contents of file " + fileName);
			e.printStackTrace();
			return "{\"success\":false}";
		}
    	
    	taskServiceImpl.saveDoc(doc);
    	
    	Set<Doc> cusotmerArtifacts = taskServiceImpl.getCustomerArtifacts(etsCust.getCustomerId());
    	
    	if(cusotmerArtifacts != null && cusotmerArtifacts.size() > 0)
    	{
    		model.addAttribute("artifacts",cusotmerArtifacts);
    	}
    	
    	
        return "{\"success\":true}";
    }
    
    @RequestMapping(value = "/upload/video", method = RequestMethod.POST)
    public @ResponseBody String uploadVideo(HttpServletRequest request,@RequestParam String promptId,@RequestParam String taskId,@RequestParam MultipartFile file, @LoggedInUser ERegUser loggedInUser, Model model) {
    	System.out.println("Upload / Save ");    	    	
    	Doc doc = new Doc();
    	EtsCust etsCust = taskServiceImpl.getCustomer(loggedInUser.getId());
    	doc.setEtsCust(etsCust);
    	
    	System.out.println("the prompt id at controller lvl is"+promptId);
    	System.out.println("the task id at controller lvl is"+taskId);
    	if(promptId!=null && taskId!=null)
    	{
    	
    	String fileName = file.getOriginalFilename();    
    	
    	//String fileExt = getFileExt(fileName);
    	String mime = null;
    	
    	doc.setRspSrcLctnNam(fileName);    	
    	doc.setMdaMimeTyp(taskServiceImpl.lookupMime(mime));
    	ContentManagementDTO contentManagementVO = new ContentManagementDTO();
    	try {
    		  file.getBytes();
    		  //MM/dd/yyyy h:mm:ss a
    		  //yyyyMMddhhmm
    		  String directoryName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    		  System.out.println("directory name is: " + directoryName);
    		 //File theDir=new File("c:\\ash\\temp\\"+directoryName);
    		 File theDir=new File("/export/Apps/tomcat/upload/"+directoryName);
    		  if (!theDir.exists())
    		  {
    		    System.out.println("creating directory: " + directoryName);
    		    boolean result = theDir.mkdir();  
    		    if(result){    
    		       System.out.println("DIR created");  
    		     
    		  //Unix file format
    		 // File tempfile=new File("c:\\ash\\temp\\"+directoryName+"\\"+fileName);
    		 File tempfile=new File("/export/Apps/tomcat/upload/"+directoryName+"/"+fileName);
    		  
    		  file.transferTo(tempfile);
    		  
    		  if (StringUtils.isNotBlank(fileName)) {
    				
    					contentManagementVO.setEntryName(fileName);
    					contentManagementVO.setReferenceID(fileName);
    					contentManagementVO.setFileToUpload(tempfile);
    					//contentManagementVO.setFileName(fileName);
    					contentManagementVO.setFileName(tempfile.getAbsolutePath());
    					//contentManagementVO.setFileStream(file.getInputStream());
    					boolean exists=taskServiceImpl.getVideoEntries(contentManagementVO,loggedInUser.getId(), Long.parseLong(taskId),Long.parseLong(promptId));
    					contentManagementVO= taskServiceImpl.uploadContent(exists,contentManagementVO);
    				} 
    			
    		  
		}
    		    else{
    		    	throw new RuntimeException("Not able to Create directory with specified name "+directoryName);	
    		    }
    		  }
    	}catch (IOException e) {			
			logger.error("Could not read contents of file " + fileName);
			e.printStackTrace();
			return "{\"success\":false}";
		}
    	
    	catch (Exception e) {			
			logger.error("Could not upload contents of file " + fileName);
			e.printStackTrace();
			return "{\"success\":false}";
		}
    	if(contentManagementVO != null && contentManagementVO.getEntryName()!=null)
    	{
    		model.addAttribute("entryName",contentManagementVO.getMediaEntry().id);
    		taskServiceImpl.saveVideoEntry(contentManagementVO.getMediaEntry().id,loggedInUser.getId(), Long.parseLong(taskId),Long.parseLong(promptId));
    		return "{\"success\":true}";
    		
    	}
    	else{
    		return "{\"success\":false}";
    	    }
        
    	}
    	else
    	{
    	return "{\"success\":false}";}
    }
   /* 
    @RequestMapping(value = "/editor/save", method = RequestMethod.POST)
    public @ResponseBody String saveEditor(
    		@RequestParam String editorText,
    		@RequestParam String taskId,
    		@RequestParam String promptId
    		, @LoggedInUser ERegUser loggedInUser    		
    		) {
    		
    		taskServiceImpl.saveEssay(editorText, loggedInUser.getId(), Long.parseLong(taskId),Long.parseLong(promptId));
    		return "";
    }*/
    

    @RequestMapping(value = "/editor/save", method = RequestMethod.POST,consumes={"application/json"})
    public @ResponseBody void saveEditor(    		    		
    		@RequestBody TaskDraft taskDraft,
    		@LoggedInUser ERegUser loggedInUser
    		   		
    		) {
    		
    		//taskServiceImpl.saveEssay(editorText, loggedInUser.getId(), Long.parseLong(taskId),Long.parseLong(promptId));
    		
    		taskServiceImpl.saveDraft(taskDraft, loggedInUser.getId());
    		
    }
    
    @RequestMapping(value = "/draft/save", method = RequestMethod.POST, consumes={"application/json"})
    public @ResponseBody void saveDraft(
    		@RequestBody TaskDraft taskDraft
    		, @LoggedInUser ERegUser loggedInUser
    		) {
    		
    	taskServiceImpl.saveDraft(taskDraft, loggedInUser.getId());
    	
    			
    }
    
    @RequestMapping(value = "/submit/save", method = RequestMethod.POST)
    public @ResponseBody String submitTask(
    		@RequestParam String taskId,
    		 @LoggedInUser ERegUser loggedInUser   		
    		) {    		
    				return taskServiceImpl.submitTask(Long.parseLong(taskId),loggedInUser.getId());    			
    }
    
	private String getFileExt(String filename)
	{
		int periodIndex = filename.lastIndexOf(".");
		int filenamelength = filename.length();
		if(periodIndex != -1 && filenamelength>0)
		{
			return filename.substring(periodIndex+1,filenamelength);
		}
		
		return null;
	}
	

}