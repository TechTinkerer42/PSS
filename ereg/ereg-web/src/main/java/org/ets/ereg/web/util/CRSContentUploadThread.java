package org.ets.ereg.web.util;





import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.ets.pss.persistence.dto.ContentManagementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.kaltura.ObjectFactory;
import com.kaltura.TChannel;
import com.kaltura.TContent;
import com.kaltura.TContentAssets;
import com.kaltura.TDropFolderFileContentResource;
import com.kaltura.TItem;
import com.kaltura.TMedia;
import com.kaltura.TMrss;
import com.kaltura.client.KalturaApiException;
import com.kaltura.client.enums.KalturaEntryStatus;
import com.kaltura.client.types.KalturaMediaEntry;

/**
 * @author asampath
 *
 */
public class CRSContentUploadThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(CRSContentUploadThread.class);

	public static final String ENCODING = "UTF-8";
	public static final String FILE_SEPARATOR = "/";
	private final static String FILE_EXT_XML = ".xml";

	private final static String KALTURA_UPDATE_ACTION = "update";

	private ContentManagementDTO contentManagementVO;
	
	private KalturaServiceUtil kalturaServiceUtil;

	public CRSContentUploadThread() {
	}

	/**
	 * 
	 * @param contentManagementVO
	 * @param kalturaServiceUtil
	 * @param emailService
	 * @param crsContentManagementPersistenceService
	 */
	public CRSContentUploadThread(ContentManagementDTO contentManagementVO, KalturaServiceUtil kalturaServiceUtil
			) {
		log.info("CRSContentUploadThread created for entry Reference ID - {}", contentManagementVO.getReferenceID());
		this.contentManagementVO = contentManagementVO;
		
		this.kalturaServiceUtil = kalturaServiceUtil;
	
	}

	public void run() {
		try {
			uploadMediaFile();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			//updateUploadTrackingStatus(contentManagementVO, e.getMessage(), 1, ContentUploadTracking.STS_UPLOAD_FAILED);
		}
	}

	/**
	 * Uploads a video file to Kaltura and assigns it to a given Media Entry
	 * object
	 */
	private void uploadMediaFile() throws KalturaApiException {
		// Check for the given entity ID if the content already uploaded
		// successfully. If not, proceed further.
		KalturaMediaEntry retrievedEntry = contentManagementVO.getClient().getMediaService().get(contentManagementVO.getMediaEntry().id);
		log.info("Entry {} , Status {}", contentManagementVO.getMediaEntry().id, retrievedEntry.status);

		/*if (retrievedEntry.status != KalturaEntryStatus.NO_CONTENT) {
			log.info("Entry {} is already uploaded , Status {}", contentManagementVO.getMediaEntry().id, retrievedEntry.status);
			// Set the upload status to success
			//updateUploadTrackingStatus(contentManagementVO, "", 1, ContentUploadTracking.STS_UPLOAD_SUCCESSFUL);
			return;
		}*/

		// Generate the XML file for ingestion
		JAXBElement<TMrss> jaxbElement = createXML(contentManagementVO);
		String xmlFilename =contentManagementVO.getFileToUpload().getParent() +FILE_SEPARATOR + contentManagementVO.getReferenceID() + FILE_EXT_XML;
		
		File xmlFile = new File(xmlFilename);
		marshall(jaxbElement, xmlFile);

		// SFTP to dropFolder location in Kaltura cloud
		String sftpHost = kalturaServiceUtil.CM_SFT_HOST;
		String sftpUser = kalturaServiceUtil.CM_SFTP_USER;
		String sftpPassword =kalturaServiceUtil.CM_SFTP_PWD;
		String sftpPort = kalturaServiceUtil.CM_SFTP_PORT;
		String sftpRemoteLocation =kalturaServiceUtil.CM_SFTP_REMOTE_LOCATION;
		String toAddress = kalturaServiceUtil.CM_UPLOAD_FAILURE_MAIL_LIST;

		int maxRetryCount = new Integer(kalturaServiceUtil.CM_MAX_UPLOAD_RETRY_COUNT);
		int threadSleepTimeinMins = new Integer(kalturaServiceUtil.CM_UPLOAD_THREAD_RETRY_SLEEP_TIME);

		int retryCounter = 0;

		log.info("sftpHost = " + sftpHost);
		log.info("sftpUser = " + sftpUser);
		log.info("sftpPassword = " + sftpPassword);
		log.info("sftpRemoteLocation = " + sftpRemoteLocation);
		log.info("toAddress = " + toAddress);
		log.info("maxRetryCount = " + maxRetryCount);

		if (StringUtils.isEmpty(sftpHost) || StringUtils.isEmpty(sftpUser) || StringUtils.isEmpty(sftpPassword) || StringUtils.isEmpty(sftpPort) || StringUtils.isEmpty(sftpRemoteLocation)
				|| StringUtils.isEmpty(toAddress)) {
			log.error("One of the SFTP related parameter value is missing.");
			throw new KalturaApiException("One of the SFTP related parameter value is missing.");
		}

		//String[] mailToList = StringUtils.split(toAddress, ',');
		int port = Integer.parseInt(sftpPort);
		int uploadStatus = -1;
		log.info("File to upload {}", contentManagementVO.getFileToUpload());

		while (retryCounter < maxRetryCount) {
			try {
				// SFTP the file to Kaltura dropfolder location
				uploadStatus = uploadFile(sftpHost, sftpUser, sftpPassword, port, getInputStreamFromFile(contentManagementVO.getFileToUpload()), sftpRemoteLocation + FILE_SEPARATOR
						+ contentManagementVO.getFileToUpload().getName(), getInputStreamFromFile(xmlFile), xmlFile.getName());
				log.info("Uploading {} , Status {}", contentManagementVO.getFileToUpload().getName(), uploadStatus);

				if (uploadStatus != 0) {
					retryCounter++;
					//updateUploadTrackingStatus(contentManagementVO, "One of the SFTP related parameter value is missing.", retryCounter, ContentUploadTracking.STS_UPLOAD_RETRY);
					Thread.sleep(threadSleepTimeinMins * 60 * 1000);

					// In case failure happened because of wrong config value,
					// this re-read helps to load the content in the next try.
					sftpHost =kalturaServiceUtil.CM_SFT_HOST;
					sftpUser = kalturaServiceUtil.CM_SFTP_USER;
					sftpPassword = kalturaServiceUtil.CM_SFTP_PWD;
					sftpPort = kalturaServiceUtil.CM_SFTP_PORT;
					sftpRemoteLocation = kalturaServiceUtil.CM_SFTP_REMOTE_LOCATION;
					continue;
				} else {
					// if upload is successful, break the loop
					break;
				}
			} catch (Exception e) {
				retryCounter++;
				e.printStackTrace();
				//updateUploadTrackingStatus(contentManagementVO, e.getMessage(), retryCounter, ContentUploadTracking.STS_UPLOAD_RETRY);
			}
		}

		log.info("Upload Status {}", uploadStatus);

		// Handling failure related to SFTP connection
		if (uploadStatus == 1 || uploadStatus == 2) {
			log.error("Uploading {} failed. Sending email.", contentManagementVO.getFileToUpload().getName());
			// Send failure email related to SFTP connection
			// Asset location information and the meta data xml should be
			// available in email
			//sendEmail(mailToList, null, contentManagementVO.getFileToUpload().getName(), "Content Upload Failure - SFTP connection issue", contentManagementVO.getMediaEntry().id);
			//updateUploadTrackingStatus(contentManagementVO, "Content Upload failure - SFTP connection issue", retryCounter, ContentUploadTracking.STS_UPLOAD_FAILED);

			return;
		}

		// Handling failure not related to SFTP connection
		if (uploadStatus == -1) {
			log.error("Uploading {} failed. Sending email.", contentManagementVO.getFileToUpload().getName());
			// Send email related to video upload failure. Manual upload should
			// be done. Asset location information and the meta data xml should
			// be available in email
			//sendEmail(mailToList, null, contentManagementVO.getFileToUpload().getName(), "Content Upload Failure", contentManagementVO.getMediaEntry().id);
			//updateUploadTrackingStatus(contentManagementVO, "Content Upload Failure", retryCounter, ContentUploadTracking.STS_UPLOAD_FAILED);

			return;
		}

		log.info("Uploaded a new video file to entry {}", contentManagementVO.getMediaEntry().id);
		// TODO need to add more status for error conditions
		retrievedEntry = contentManagementVO.getClient().getMediaService().get(contentManagementVO.getMediaEntry().id);
		log.info("Entry Status {}", retrievedEntry.status);

		// TODO : Following code is checking the status of the content, We may
		// need to remove this check based on the drop folder option set up
		// if (retrievedEntry.status == KalturaEntryStatus.NO_CONTENT ||
		// retrievedEntry.status == KalturaEntryStatus.ERROR_CONVERTING ||
		// retrievedEntry.status == KalturaEntryStatus.ERROR_IMPORTING) {
		if (retrievedEntry.status == KalturaEntryStatus.ERROR_CONVERTING || retrievedEntry.status == KalturaEntryStatus.ERROR_IMPORTING) {
			log.info("Uploading {} had some issue. Status {}. Sending email.", contentManagementVO.getFileToUpload().getName(), retrievedEntry.status);
			//sendEmail(mailToList, null, contentManagementVO.getFileToUpload(), "Content Upload issue (status : " + retrievedEntry.status + ")", contentManagementVO.getMediaEntry().id);
			//updateUploadTrackingStatus(contentManagementVO, "Kalture : Content Upload issue (status : " + retrievedEntry.status + ")", retryCounter, ContentUploadTracking.STS_UPLOAD_FAILED);
			return;
		}

		// if SFTP upload is done, upload is successful
		// Delete if there is any entry found in failure log
		//updateUploadTrackingStatus(contentManagementVO, "Content Uploaded Successfully", retryCounter, ContentUploadTracking.STS_UPLOAD_SUCCESSFUL);
	}

	/**
	 * Upload file to SFTP server
	 * 
	 * @param host
	 * @param user
	 * @param password
	 * @param port
	 * @param inStream
	 * @param remoteFile
	 * 
	 * @return status
	 */
	private int uploadFile(String host, String user, String password, int port, InputStream inStream, String remoteFile, InputStream xmlInStream, String xmlRemoteFile) {
		Channel channel = null;
		ChannelSftp sftpChannel = null;
		JSch jsch = null;
		Session session = null;

		int uploadStatus = -1;

		log.info("Entering uploadFile");

		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");

			jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig(config);

			session.connect();

			// Session not established
			if (!session.isConnected()) {
				log.warn("SFTP session not connected. Return 1");
				return 1;
			}

			channel = session.openChannel("sftp");
			channel.connect();

			// SFTP Channel not established
			if (!channel.isConnected()) {
				log.warn("SFTP channel not connected. Return 2");
				return 2;
			}

			sftpChannel = (ChannelSftp) channel;
			sftpChannel.put(inStream, remoteFile);
			sftpChannel.put(xmlInStream, xmlRemoteFile);

			uploadStatus = 0;

			log.info("File uploaded successfully to FTP server. Status {}", uploadStatus);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			uploadStatus = -1;
		} finally {
			// disconnect the sftpChannel and session
			try {
				if (sftpChannel != null)
					sftpChannel.disconnect();
				if (session != null)
					session.disconnect();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		log.info("Exit uploadFile");

		return uploadStatus;
	}

	

	


	
	/**
	 * @return the contentManagementVO
	 */
	public ContentManagementDTO getContentManagementVO() {
		return contentManagementVO;
	}

	/**
	 * @param contentManagementVO
	 *            the contentManagementVO to set
	 */
	public void setContentManagementDTO(ContentManagementDTO contentManagementVO) {
		this.contentManagementVO = contentManagementVO;
	}

	

	/**
	 * @return the kalturaServiceUtil
	 */
	public KalturaServiceUtil getKalturaServiceUtil() {
		return kalturaServiceUtil;
	}

	/**
	 * @param kalturaServiceUtil
	 *            the kalturaServiceUtil to set
	 */
	public void setKalturaServiceUtil(KalturaServiceUtil kalturaServiceUtil) {
		this.kalturaServiceUtil = kalturaServiceUtil;
	}
	
	/**
	 * Marshalls from object to XML input stream
	 */
	private void marshall(JAXBElement<TMrss> jaxbElement, File xmlFile) {
		try {
			Marshaller marshaller = JAXBContext.newInstance(TMrss.class).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "");

			marshaller.marshal(jaxbElement, xmlFile);
		} catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param mediaFileName
	 * @return
	 */
	private JAXBElement<TMrss> createXML(ContentManagementDTO contentManagementVO) {
		log.info("contentManagementVO.getFileToUpload().getName(): " + contentManagementVO.getFileName());
		log.info("id: " + contentManagementVO.getMediaEntry().id);

		TDropFolderFileContentResource dropFolderContent = new TDropFolderFileContentResource();
		dropFolderContent.setFilePath(contentManagementVO.getFileToUpload().getName());
		//dropFolderContent.setFilePath(contentManagementVO.getEntryName());

		TContent content = new TContent();
		content.setContentResourceExtension(new ObjectFactory().createDropFolderFileContentResource(dropFolderContent));

		TContentAssets contentAssets = new TContentAssets();
		contentAssets.getContent().add(content);

		TMedia media = new TMedia();
		media.setMediaType(1);

		TItem item = new TItem();
		item.setAction(KALTURA_UPDATE_ACTION);
		item.getTypeAndLicenseTypeOrEntryId().add(new ObjectFactory().createTItemEntryId(contentManagementVO.getMediaEntry().id));
		item.getTypeAndLicenseTypeOrEntryId().add(new ObjectFactory().createTItemReferenceId(contentManagementVO.getReferenceID()));
		item.setName(contentManagementVO.getReferenceID());
		item.setMedia(media);
		item.setContentAssets(contentAssets);

		TChannel channel = new TChannel();
		channel.getItem().add(item);

		TMrss mrss = new TMrss();
		mrss.getChannel().add(channel);

		JAXBElement<TMrss> jaxbElement = new ObjectFactory().createMrss(mrss);
		return jaxbElement;
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	private InputStream getInputStreamFromFile(File file) {
		try {
			return new FileInputStream(file);
		} catch (Exception e) {
			return null;
		}
	}

	

	/**
	 * Class used for jcraft debugging
	 */
	class MyLogger implements com.jcraft.jsch.Logger {
		java.util.Hashtable name = new java.util.Hashtable();

		public MyLogger() {
			name.put(new Integer(DEBUG), "DEBUG: ");
			name.put(new Integer(INFO), "INFO: ");
			name.put(new Integer(WARN), "WARN: ");
			name.put(new Integer(ERROR), "ERROR: ");
			name.put(new Integer(FATAL), "FATAL: ");
		}

		public boolean isEnabled(int level) {
			return true;
		}

		public void log(int level, String message) {
			log.info(message);
		}
	}
}

