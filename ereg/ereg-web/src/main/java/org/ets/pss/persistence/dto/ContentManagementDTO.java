package org.ets.pss.persistence.dto;



import java.io.File;
import java.io.InputStream;

import com.kaltura.client.KalturaClient;
import com.kaltura.client.types.KalturaMediaEntry;
/**
 * @author asampath
 *
 */
public class ContentManagementDTO {
	private File fileToUpload;
	
	private String fileName;
	private String entryName;
	private String uploadStatus;
	private KalturaClient client;
	private KalturaMediaEntry mediaEntry;
	private String referenceID;
	private String entryIDToSearch;
	private String urlString;
	private String ttlToken;
	private String playerID;
	private Long contentUploadTrackingID;

	// player attributes
	private String playerName = "kalturaPlayer";
	private int playerWidth = 930; //4:3 aspect ratio; addt'l pixels added for the toolbar at the bottom of the player
	private int playerHeight = 729; //4:3 aspect ratio
	private boolean leadWithHTML5 = false;
	private boolean autoPlay = false;
	

	private InputStream fileStream;


	/**
	 * @return the fileStream
	 */
	public InputStream getFileStream() {
		return fileStream;
	}

	/**
	 * @param fileStream the fileStream to set
	 */
	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	
	/**
	 * @return the fileToUpload
	 */
	public File getFileToUpload() {
		return fileToUpload;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the entryName
	 */
	public String getEntryName() {
		return entryName;
	}

	/**
	 * @return the client
	 */
	public KalturaClient getClient() {
		return client;
	}

	/**
	 * @return the mediaEntry
	 */
	public KalturaMediaEntry getMediaEntry() {
		return mediaEntry;
	}

	/**
	 * @return the referenceID
	 */
	public String getReferenceID() {
		return referenceID;
	}

	/**
	 * @return the entryIDToSearch
	 */
	public String getEntryIDToSearch() {
		return entryIDToSearch;
	}

	/**
	 * @return the urlString
	 */
	public String getUrlString() {
		return urlString;
	}

	/**
	 * @param fileToUpload
	 *            the fileToUpload to set
	 */
	public void setFileToUpload(File fileToUpload) {
		this.fileToUpload = fileToUpload;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param entryName
	 *            the entryName to set
	 */
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(KalturaClient client) {
		this.client = client;
	}

	/**
	 * @param mediaEntry
	 *            the mediaEntry to set
	 */
	public void setMediaEntry(KalturaMediaEntry mediaEntry) {
		this.mediaEntry = mediaEntry;
	}

	/**
	 * @param referenceID
	 *            the referenceID to set
	 */
	public void setReferenceID(String referenceID) {
		this.referenceID = referenceID;
	}

	/**
	 * @param entryIDToSearch
	 *            the entryIDToSearch to set
	 */
	public void setEntryIDToSearch(String entryIDToSearch) {
		this.entryIDToSearch = entryIDToSearch;
	}

	/**
	 * @param urlString
	 *            the urlString to set
	 */
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	/**
	 * @return the ttlToken
	 */
	public String getTtlToken() {
		return ttlToken;
	}

	/**
	 * @param ttlToken
	 *            the ttlToken to set
	 */
	public void setTtlToken(String ttlToken) {
		this.ttlToken = ttlToken;
	}

	/**
	 * @return the playerID
	 */
	public String getPlayerID() {
		return playerID;
	}

	/**
	 * @param playerID
	 *            the playerID to set
	 */
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	/**
	 * @return the contentUploadTrackingID
	 */
	public Long getContentUploadTrackingID() {
		return contentUploadTrackingID;
	}

	/**
	 * @param uploadStatus
	 *            the uploadStatus to set
	 */
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	/**
	 * @param contentUploadTrackingID
	 *            the contentUploadTrackingID to set
	 */
	public void setContentUploadTrackingID(Long contentUploadTrackingID) {
		this.contentUploadTrackingID = contentUploadTrackingID;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the playerWidth
	 */
	public int getPlayerWidth() {
		return playerWidth;
	}

	/**
	 * @param playerWidth
	 *            the playerWidth to set
	 */
	public void setPlayerWidth(int playerWidth) {
		this.playerWidth = playerWidth;
	}

	/**
	 * @return the playerHeight
	 */
	public int getPlayerHeight() {
		return playerHeight;
	}

	/**
	 * @param playerHeight
	 *            the playerHeight to set
	 */
	public void setPlayerHeight(int playerHeight) {
		this.playerHeight = playerHeight;
	}

	/**
	 * @return the leadWithHTML5
	 */
	public boolean isLeadWithHTML5() {
		return leadWithHTML5;
	}

	/**
	 * @param leadWithHTML5 the leadWithHTML5 to set
	 */
	public void setLeadWithHTML5(boolean leadWithHTML5) {
		this.leadWithHTML5 = leadWithHTML5;
	}

	/**
	 * @return the autoPlay
	 */
	public boolean isAutoPlay() {
		return autoPlay;
	}

	/**
	 * @param autoPlay the autoPlay to set
	 */
	public void setAutoPlay(boolean autoPlay) {
		this.autoPlay = autoPlay;
	}
}