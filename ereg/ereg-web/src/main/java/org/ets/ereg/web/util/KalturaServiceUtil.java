package org.ets.ereg.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;


import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.ets.pss.persistence.dto.ContentManagementDTO;


import com.kaltura.client.KalturaApiException;
import com.kaltura.client.KalturaClient;
import com.kaltura.client.KalturaConfiguration;
import com.kaltura.client.enums.KalturaMediaType;
import com.kaltura.client.enums.KalturaSessionType;

import com.kaltura.client.types.KalturaMediaEntry;
/**
 * @author asampath
 *
 */
public class KalturaServiceUtil {
	
	
	private static final String FLASHVAR_HTML5 = "&flashvars[Kaltura.LeadWithHTML5]=true";
	private static final String FLASHVAR_AUTOPLAY = "&flashvars[autoPlay]=true";
	private static final String Kaltura_Admin_Secret ="0e3182750202169071f0e06ee13aaab7";
	private static final String Kaltura_Partner_ID ="958691";	
	private static final String Kaltura_TTL_Expiry ="18000";
	private static final String Kaltura_User_ID="asampath@ets.org";
	private static final String Kaltura_Url="https://cdnapisec.kaltura.com/index.php/kwidget/cache_st/1361827954/wid/_{0}/uiconf_id/{1}/entry_id/{2}";	
	private static final String Kaltura_Player_ID  ="13758581";
	private static final String Kaltura_EndPoint ="http://www.kaltura.com";
	
	public  final String  CM_MAX_UPLOAD_RETRY_COUNT="3";

	public  final String CM_SFTP_PORT="22";

	public  final String CM_SFTP_PWD ="eidiePh5";
	

	//public  final String CM_SFTP_REMOTE_LOCATION ="/home/sdrpftp958691";
	public  final String CM_SFTP_REMOTE_LOCATION ="/";

	public  final String CM_SFTP_USER="sdrpftp958691";

	public  final String CM_SFT_HOST="ftp.kaltura.com";

	public final String CM_UPLOAD_FAILURE_MAIL_LIST="asamapth@ets.org";

	public  final String CM_UPLOAD_RETRY_WAIT_TIME ="30";

	public  final String CM_UPLOAD_THREAD_RETRY_SLEEP_TIME="2";



	
	Logger log = Logger.getLogger(KalturaServiceUtil.class);
	
	
	public String getKalturaSession(ContentManagementDTO contentManagementVO) throws RuntimeException{	
		//TODO: include contentManagementVO attributes in log 
		log.info("KalturaServiceUtil.getToken called for "+contentManagementVO.getEntryIDToSearch());
		String ks = null;		
		String kalturaPartnerID =Kaltura_Partner_ID;	

		String kalturaAdminSecret =Kaltura_Admin_Secret;
		String ttlExpiry =Kaltura_TTL_Expiry;
		String userID =Kaltura_User_ID;
		
		if(kalturaPartnerID == null || kalturaPartnerID.isEmpty()
				|| kalturaAdminSecret == null || kalturaAdminSecret.isEmpty()
				|| ttlExpiry == null || ttlExpiry.isEmpty()
				|| userID == null || userID.isEmpty()
				){
			//TODO: include contentManagementVO attributes in exception
			new RuntimeException("Kaltura configuration details are missing");
		}
		
		try{
			ks = getKalturaSession(kalturaPartnerID, kalturaAdminSecret, true, userID,new Integer(ttlExpiry),contentManagementVO.getEntryIDToSearch());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			new RuntimeException(e);
		}
		return ks;
	}

	/**
	 * Use this new method for embedding the Kaltura video. The URL returned is
	 * the location of the Kaltura script which will auto embed an HTML5 or
	 * Flash player
	 * 
	 * @param contentManagementVO
	 * @return
	 * @throws CRSRuntimeException
	 */
	public String getScriptURL(ContentManagementDTO contentManagementVO) throws RuntimeException {
		log.info("getScriptURL called for " + contentManagementVO.getEntryIDToSearch());

		String kalturaPartnerID = Kaltura_Partner_ID;
		String playerID = Kaltura_Player_ID;
		String kalturaUrl = Kaltura_Url;


		if (kalturaPartnerID == null || kalturaPartnerID.isEmpty() || playerID == null || playerID.isEmpty() || kalturaUrl == null || kalturaUrl.isEmpty()) {
			new RuntimeException("Kaltura configuration details are missing.");
		}

		String url = MessageFormat.format(kalturaUrl, kalturaPartnerID, contentManagementVO.getPlayerID() == null ? playerID : contentManagementVO.getPlayerID(), contentManagementVO
				.getEntryIDToSearch(), contentManagementVO.getPlayerName(), contentManagementVO.getPlayerWidth(), contentManagementVO.getPlayerHeight());

		// add flashvars
		if (contentManagementVO.isLeadWithHTML5())
			url = url + FLASHVAR_HTML5;
		if (contentManagementVO.isAutoPlay())
			url = url + FLASHVAR_AUTOPLAY;

		return url;
	}

	public String getURL(ContentManagementDTO contentManagementVO) throws RuntimeException {
		log.info("getURL called for " + contentManagementVO.getEntryIDToSearch());
		String kalturaPartnerID = Kaltura_Partner_ID;
		String playerID = Kaltura_Player_ID;	

		String endPoint =Kaltura_EndPoint;

		if(kalturaPartnerID == null || kalturaPartnerID.isEmpty()				
				|| playerID == null || playerID.isEmpty()
				|| endPoint == null || endPoint.isEmpty()
				){
			new RuntimeException("Kaltura configuration details are missing");
		}

		String url = MessageFormat.format(endPoint, kalturaPartnerID, contentManagementVO.getPlayerID() == null ? playerID : contentManagementVO.getPlayerID(), contentManagementVO.getEntryIDToSearch());

		return url;
	}
	
	public void setKalturaClient(boolean isAdmin,ContentManagementDTO contentManagementVO) throws KalturaApiException
	{
			log.info("Entering setKalturaClient = ");
			
			String kalturaPartnerID =Kaltura_Partner_ID;

			String kalturaAdminSecret =Kaltura_Admin_Secret;
			String userID =  Kaltura_User_ID;
			String endPoint = Kaltura_EndPoint;
			
			if(kalturaPartnerID == null || kalturaPartnerID.isEmpty()
					|| kalturaAdminSecret == null || kalturaAdminSecret.isEmpty()
					|| userID == null || userID.isEmpty()
					|| endPoint == null || endPoint.isEmpty()					
					){
				log.info("Kaltura configuration details are missing");
				throw new KalturaApiException("Kaltura configuration details are missing");
			}
		
	        // set a new configuration object
	        KalturaConfiguration config = new KalturaConfiguration();
	        config.setPartnerId(new Integer(kalturaPartnerID));
	        config.setEndpoint(endPoint);
	        // get a new client object using our configuration
	        KalturaClient client = new KalturaClient(config);	        
	        // start a new session (admin/user) and recieve the ks (kaltura session) string	       
	        KalturaSessionType sessionType = (isAdmin ? KalturaSessionType.ADMIN : KalturaSessionType.USER);
	        String ks = client.getSessionService().start(kalturaAdminSecret, userID, sessionType);
	        log.info("Kaltura Session generated : "+ks);
	        // set the kaltura client to use the received ks as default for all future operations
	        client.setSessionId(ks);	        
	        // return the configured client
	        contentManagementVO.setClient(client);
	        log.info("Exiting getKalturaClient = ");
	}
	
	/** 
	 * creates an empty media entry and assigns basic metadata to it.
	 * @return the generated <code>KalturaMediaEntry</code>
	 * @throws KalturaApiException 
	 */
	
	public void addEmptyEntry(ContentManagementDTO contentManagementDTO) throws KalturaApiException {
		log.info("Entering addEmptyEntry");
		setKalturaClient(true,contentManagementDTO);
		log.info("Creating an empty Kaltura Entry for Reference "+contentManagementDTO.getReferenceID());
		KalturaMediaEntry entry = new KalturaMediaEntry();
		entry.name = contentManagementDTO.getEntryName();
		entry.mediaType = KalturaMediaType.VIDEO;
		entry.referenceId =  contentManagementDTO.getReferenceID();
		//entry.categories = "Portfolio Pilot";
		entry.categoriesIds = "12683521";
		KalturaMediaEntry newEntry = contentManagementDTO.getClient().getMediaService().add(entry);
		System.out.println("Category: " + newEntry.categoriesIds);
		System.out.println("Entry ID is: " + newEntry.id);		
		contentManagementDTO.setMediaEntry(newEntry);
		log.info("Exit addEmptyEntry");
	}
  
	
	public void deleteEntry(ContentManagementDTO contentManagementDTO) throws KalturaApiException {
	
		log.info("Entering approveEntry");
		setKalturaClient(true,contentManagementDTO);
		log.info("Creating an approveEntry for Reference "+contentManagementDTO.getReferenceID());
		
		
		 contentManagementDTO.getClient().getMediaService().delete(contentManagementDTO.getMediaEntry().id);
		 addEmptyEntry(contentManagementDTO);
		
		log.info("Exit deleteEntry");
	}
	
  
	

	public void getEntryForID(ContentManagementDTO contentManagementDTO) throws KalturaApiException {
		log.info("Enter getEntryForID");
		setKalturaClient(true,contentManagementDTO);
		KalturaMediaEntry newEntry =contentManagementDTO.getClient().getMediaService().get(contentManagementDTO.getEntryIDToSearch());
		contentManagementDTO.setMediaEntry(newEntry);
		log.info("Exit getEntryForID");
	}
	
	
	public String getKalturaSession(String partnerId, String secret, boolean isAdmin,String userId,int expiry,String assetID) throws NumberFormatException, Exception 
	{
		log.info("Enter getKalturaSession");
		String ks = null;	

			if(partnerId == null || partnerId == null || userId == null){
				return null;				
			}   
	        ks = generateSession(secret,userId, KalturaSessionType.USER,Integer.parseInt(partnerId), expiry,assetID != null?"sview:"+assetID:"");
	        ks = ks.replace("\n", "");
	        return ks;
	}
	 
	
	public String generateSession(String adminSecretForSigning, String userId, KalturaSessionType type, int partnerId, int expiry, String privileges) throws Exception
	{
		try
		{
			// initialize required values
			int rand = (int)(Math.random() * 32000);
			expiry += (int)(System.currentTimeMillis() / 1000);
			 
			// build info string
			StringBuilder sbInfo = new StringBuilder();
			sbInfo.append(partnerId).append(";"); // index 0 - partner ID
			sbInfo.append(partnerId).append(";"); // index 1 - partner pattern - using partner ID
			sbInfo.append(expiry).append(";"); // index 2 - expiration timestamp
			sbInfo.append(type.getHashCode()).append(";"); // index 3 - session type
			sbInfo.append(rand).append(";"); // index 4 - random number
			sbInfo.append(userId).append(";"); // index 5 - user ID
			sbInfo.append(privileges); // index 6 - privileges
			
			// sign info with SHA1 algorithm
			MessageDigest algorithm = MessageDigest.getInstance("SHA1");
			algorithm.reset();
			algorithm.update(adminSecretForSigning.getBytes());
			algorithm.update(sbInfo.toString().getBytes());
			byte infoSignature[] = algorithm.digest();
			
			// convert signature to hex:
			String signature = this.convertToHex(infoSignature);
			
			// build final string to base64 encode
			StringBuilder sbToEncode = new StringBuilder();
			sbToEncode.append(signature.toString()).append("|").append(sbInfo.toString());
			Base64 encoder = new Base64();
			
			// encode the signature and info with base64
			String hashedString = encoder.encodeToString(sbToEncode.toString().getBytes());
			
			// remove line breaks in the session string
			String ks = hashedString.replace("\n", "");
			ks = hashedString.replace("\r", "");
			
			// return the generated session key (KS)
			return ks;
		} catch (NoSuchAlgorithmException ex)
		{
			throw new Exception(ex);
		}
	}	
	
	private String convertToHex(byte[] data) { 
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) { 
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do { 
				if ((0 <= halfbyte) && (halfbyte <= 9)) 
					buf.append((char) ('0' + halfbyte));
				else 
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		} 
		return buf.toString();
	}
		
	

	
}