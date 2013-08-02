package org.ets.pss.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.ets.ereg.security.user.ERegUser;
import org.ets.ereg.security.user.LoggedInUser;
import org.ets.pss.persistence.dto.Artifact;
import org.ets.pss.persistence.dto.ArtifactResponse;
import org.ets.pss.persistence.model.Doc;
import org.ets.pss.persistence.model.EtsCust;
import org.ets.pss.service.ArtifactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller("artifact-controller")
@RequestMapping(value="/pss/artifact/")
@SessionAttributes("customer")
public class ArtifactController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ArtifactController.class);
       
	
	@Autowired
	@Qualifier("artifactService")
	ArtifactService artifactServiceImpl;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(@LoggedInUser ERegUser loggedInUser, Model model) {    	
    	Set<Doc> cusotmerArtifacts = artifactServiceImpl.getCustomerArtifacts(loggedInUser.getId());  	
    	model.addAttribute("artifacts",cusotmerArtifacts);
    	return "artifacts2";
    }
    
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody ArtifactResponse remove( @RequestParam long docId, @LoggedInUser ERegUser loggedInUser, Model model) {    	
    	String message = artifactServiceImpl.removeArtifact(docId);
    	logger.info("Remove message="+message);
    	ArtifactResponse response = new ArtifactResponse();    	
    	response.setMessage(message);
    	
    	response.setArtifacts(new ArrayList<Artifact>());
    	
    	Set<Doc> cusotmerArtifacts = artifactServiceImpl.getCustomerArtifacts(loggedInUser.getId());
    	for(Doc doc:cusotmerArtifacts)
    	{
    		Artifact a = new Artifact();
    		a.setId(doc.getDocId());
    		a.setFilename(doc.getRspSrcLctnNam());  
    		a.setDateCreated(doc.getDateCreated());
    		logger.info("adding "+doc.getRspSrcLctnNam());
    		
    		response.getArtifacts().add(a);
    	}
    	//model.addAttribute("artifacts",cusotmerArtifacts);
    	
    	
    	return response;
    }
    
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public @ResponseBody List<Artifact> getList(@LoggedInUser ERegUser loggedInUser, Model model) {    	  	
    	Set<Doc> cusotmerArtifacts = artifactServiceImpl.getCustomerArtifacts(loggedInUser.getId());  
    	
    	List<Artifact> artifactList = new ArrayList<Artifact>();
    	
    	Artifact artifact = null;
    	
    	for(Doc doc: cusotmerArtifacts)
    	{
    		artifact = new Artifact();
    		artifact.setId(doc.getDocId());
    		artifact.setFilename(doc.getRspSrcLctnNam());
    		artifactList.add(artifact);
    	}
    	
		return artifactList;
    	
    }
    
    @RequestMapping(value = "/view/{docId}", method = RequestMethod.GET)
    public @ResponseBody HttpEntity<byte[]> viewDoc(@PathVariable long docId, HttpServletResponse response) {    	  	
    	Doc doc = artifactServiceImpl.getDoc(docId);
    	String[] mimeParts;
    	if(doc.getMdaMimeTyp().getMimeTypNam() != null && !doc.getMdaMimeTyp().getMimeTypNam().equalsIgnoreCase("unknown"))
    	{
    		mimeParts = doc.getMdaMimeTyp().getMimeTypNam().split("/");
    	}
    	else
    	{
    		mimeParts = "text/plain".split("/");
    	}
    	
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(mimeParts[0], mimeParts[1]));
        header.set("Content-Disposition",
                       "attachment; filename=" + doc.getRspSrcLctnNam().replace(" ", "_"));
        header.setContentLength(doc.getDocBlb().length);

        return new HttpEntity<byte[]>(doc.getDocBlb(), header);
    }
 
}