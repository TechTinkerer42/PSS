package org.ets.pss.service;

import java.io.File;

import org.apache.log4j.Logger;
import org.ets.ereg.web.util.CRSContentUploadThread;

import org.ets.ereg.web.util.KalturaServiceUtil;
import org.ets.pss.persistence.dto.ContentManagementDTO;

import com.kaltura.client.KalturaApiException;
/**
 * @author asampath
 *
 */
public class CRSContentUploadUtilServiceImpl {
	Logger log = Logger.getLogger(CRSContentUploadUtilServiceImpl.class);
	private KalturaServiceUtil kalturaServiceUtil;	
	/**
	 * @return the kalturaServiceUtil
	 */
	public KalturaServiceUtil getKalturaServiceUtil() {
		return kalturaServiceUtil;
	}

	/**
	 * @param kalturaServiceUtil the kalturaServiceUtil to set
	 */
	public void setKalturaServiceUtil(KalturaServiceUtil kalturaServiceUtil) {
		this.kalturaServiceUtil = kalturaServiceUtil;
	}

	public CRSContentUploadUtilServiceImpl() {

	}
	
	public void initUpload(boolean exists,ContentManagementDTO contentManagementVO) throws RuntimeException{
		kalturaServiceUtil=new KalturaServiceUtil();
		log.info("Entering initUpload for Reference ID"+contentManagementVO.getReferenceID());
		try{
			if(!exists)
			//Create empty(no content) media entry in kaltura
			{kalturaServiceUtil.addEmptyEntry(contentManagementVO);}
			else
			{
				//kalturaServiceUtil.setKalturaClient(true,contentManagementVO);
				kalturaServiceUtil.deleteEntry(contentManagementVO);
				
			}
			
		}
		
		
		catch(KalturaApiException kalEx){
			//log.error("KalturaServiceUtil.addEmptyEntry failed", kalEx);
			throw new RuntimeException();	
		}
		
		File up = new File(contentManagementVO.getFileName());
        if(up.exists() == false){
        	//saveUploadTrackingStatus(contentManagementVO,"Upload Content "+contentManagementVO.getFileName()+" is not found",1,ContentUploadTracking.STS_UPLOAD_FAILED);
        	throw new RuntimeException();		        	
        }
        contentManagementVO.setFileToUpload(up);
		
		//upload tracing started
		//saveUploadTrackingStatus(contentManagementVO,"Upload started",1,ContentUploadTracking.STS_UPLOAD_PROCESSING);
		//fire the upload event / call the upload thread, this will upload the video
		startUploaderThread(contentManagementVO);		
	}
	
	//this method will start the new thread to upload the media
		public void startUploaderThread(ContentManagementDTO contentManagementVO){		
			CRSContentUploadThread upload = new CRSContentUploadThread(contentManagementVO,getKalturaServiceUtil());
			Thread t = new Thread(upload);
			t.setName(contentManagementVO.getEntryIDToSearch() != null?contentManagementVO.getEntryIDToSearch():contentManagementVO.getMediaEntry().id );
			log.info("Thread "+t.getName() +" started , Reference Id - "+contentManagementVO.getReferenceID());
			t.start();
		}
		
	
	
}
