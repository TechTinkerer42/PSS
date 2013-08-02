package org.ets.pss.controller;

import java.io.IOException;

import org.ets.pss.persistence.dao.DocDao;
import org.ets.pss.persistence.dao.TskDao;
import org.ets.pss.persistence.dto.Artifact;
import org.ets.pss.persistence.model.Doc;
import org.ets.pss.persistence.model.EtsCust;
import org.ets.pss.persistence.model.UploadItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller("upload-async-controller")
@RequestMapping(value="/web/upload/")
public class AsyncController {
		
	private static final Logger logger = LoggerFactory
			.getLogger(AsyncController.class);
	
	@Autowired
	@Qualifier("artifactDao")
	DocDao artifactDaoImpl; 
	
	@Autowired
	@Qualifier("taskDao")
	TskDao taskDaoImpl; 
    
    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public String index() {
        return "home";
    }
    
    @RequestMapping(value = "/async/save", method = RequestMethod.POST)
    public @ResponseBody String save(@RequestParam MultipartFile file,@ModelAttribute("customer") EtsCust etsCust, Model model) {
    	
    	Doc doc = new Doc();
    	//doc.setEtsCust();
    	
    	Artifact a;
        // Save the files
    	a = new Artifact();
    	a.setUserId("123");
    	a.setTaskId(2);
    	
    	String fileName = file.getOriginalFilename();    	
    	String fileExt = getFileExt(fileName);
    	String type;
    	if(fileExt != null && fileExt.equalsIgnoreCase("txt"))
    	{
    		type = fileExt;
    	}
    	else
    	{
    		type = "unk";
    	}

    	a.setType(type);
    	
    	a.setFilename(fileName);
    	
    	try {
    		//doc.setDocBlb(file.getBytes());
    		a.setContents(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Could not read contents of file " + fileName);
			e.printStackTrace();
			return "false";
		}
    	
    	//artifactDaoImpl.addArtifact(a);
        
        // Return to same view
/*    	List<Artifact> userTaskArtifacts = (List<Artifact>) artifactDaoImpl.getUserArtifactsForTask(123,2);
    	
    	if(userTaskArtifacts != null && userTaskArtifacts.size() > 0)
    	{
    		model.addAttribute("artifacts",userTaskArtifacts);
    	}*/
    	
        return "{\"success\":true}";
    }
    
/*    @RequestMapping(value = "/async/save", method = RequestMethod.POST)
    public @ResponseBody String save(@RequestParam MultipartFile file, Model model) {
    	
    	Artifact a;
        // Save the files
    	a = new Artifact();
    	a.setUserId("123");
    	a.setTaskId(2);
    	
    	String fileName = file.getOriginalFilename();    	
    	String fileExt = getFileExt(fileName);
    	String type;
    	if(fileExt != null && fileExt.equalsIgnoreCase("txt"))
    	{
    		type = fileExt;
    	}
    	else
    	{
    		type = "unk";
    	}

    	a.setType(type);
    	
    	a.setFilename(fileName);
    	
    	try {
			a.setContents(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Could not read contents of file " + fileName);
			e.printStackTrace();
			return "false";
		}
    	
    	artifactDaoImpl.addArtifact(a);
        
        // Return to same view
    	List<Artifact> userTaskArtifacts = (List<Artifact>) artifactDaoImpl.getUserArtifactsForTask("123",2);
    	
    	if(userTaskArtifacts != null && userTaskArtifacts.size() > 0)
    	{
    		model.addAttribute("artifacts",userTaskArtifacts);
    	}
    	
        return "{\"success\":true}";
    }*/
/*    public String save(UploadItem uploadItem, BindingResult result, Model model) {
    	Artifact a;
        // Save the files
    	a = new Artifact();
    	a.setUserId("123");
    	a.setTaskId(2);
    	
    	String fileName = uploadItem.getFileData().getOriginalFilename();
    	String fileExt = getFileExt(fileName);
    	String type;
    	if(fileExt != null && fileExt.equalsIgnoreCase("txt"))
    	{
    		type = fileExt;
    	}
    	else
    	{
    		type = "unk";
    	}

    	a.setType(type);
    	
    	a.setFilename(fileName);
    	a.setContents(uploadItem.getFileData().getBytes());
    	
    	artifactDaoImpl.addArtifact(a);
        
        // Return to same view
    	List<Artifact> userTaskArtifacts = (List<Artifact>) artifactDaoImpl.getUserArtifactsForTask("123",2);
    	
    	if(userTaskArtifacts != null && userTaskArtifacts.size() > 0)
    	{
    		model.addAttribute("artifacts",userTaskArtifacts);
    	}
        return "task";
    }*/

	private String getFileExt(String filename)
	{
		int periodIndex = filename.lastIndexOf(".");
		int filenamelength = filename.length();
		if(periodIndex != -1 && filenamelength>0)
		{
			return filename.substring(filenamelength-periodIndex,filenamelength);
		}
		
		return null;
	}
/*    @RequestMapping(value = "/async/saveeditor", method = RequestMethod.POST)
    public @ResponseBody String saveEditor(@RequestBody String editorText) {
    	Doc a = new Doc();
    	a.setDocBlb(editorText.getBytes());    	
    	artifactDaoImpl.addArtifact(a);
        return "";
    }*/
    
/*    @RequestMapping(value = "/async/remove", method = RequestMethod.GET)
    public String remove(@RequestParam(value="taskId") String taskId, Model model) {
    //public String remove(@PathVariable("uuid") int uuid, Model model) {
        // Return to same view
    	try {
			artifactDaoImpl.removeArtifact(Long.parseLong(taskId));
		} catch (Exception e) {			
			e.printStackTrace();
		}	    	    
    	
    	model.addAttribute("artifacts",(List<Artifact>) artifactDaoImpl.getUserArtifactsForTask(123,2));
        return "task";
    }*/
    
    @ModelAttribute("uploadItem")
    public UploadItem getUploadItem() {
     return new UploadItem();
    }
    
    
/*    @ModelAttribute("task")
    public Task getTask()
    {    	
    	return (Task)taskDaoImpl.getTask(2);    	
    }*/
    
    
/*    @ModelAttribute("artifacts")
    public List<Artifact> getArtifacts()
    {    	
    	return (List<Artifact>) artifactDaoImpl.getUserArtifactsForTask(123,2);
    }*/
}