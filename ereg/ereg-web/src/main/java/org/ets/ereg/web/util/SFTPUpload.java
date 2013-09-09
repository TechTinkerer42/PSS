package org.ets.ereg.web.util;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SFTPUpload implements Runnable {
	private static Logger log = LoggerFactory.getLogger(SFTPUpload.class);

	private static String host = "ftp.kaltura.com";
	private static String user = "sdrpftp958691";
	private static String password = "eidiePh5";

	public static void main(String args[]) {
		SFTPUpload sftpUpload = new SFTPUpload();
		Thread thread = new Thread(sftpUpload);
		thread.start();
	}

	/**
	 * Upload file to SFTP server
	 * 
	 * @param host
	 * @param user
	 * @param password
	 * @param port
	 * @param mediaFilePath
	 * @param remoteFilename
	 * @param xmlFilePath
	 * @param xmlRemoteFilename
	 * @return status
	 */
	private int uploadFile(String host, String user, String password, int port, String mediaFilePath, String remoteFilename, String xmlFilePath, String xmlRemoteFilename) {
		int uploadStatus = -1;

		StandardFileSystemManager manager = new StandardFileSystemManager();

		String connectionString = "sftp://" + user + ":" + password + "@" + host;

		try {
			manager.init();

			// Create SFTP options
			FileSystemOptions fileSystemOptions = new FileSystemOptions();

			// SSH Key checking
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fileSystemOptions, "no");

			// upload media
			uploadFile(manager, fileSystemOptions, connectionString + "/" + remoteFilename, mediaFilePath);

			// upload xml
			//uploadFile(manager, fileSystemOptions, connectionString + "/" + xmlRemoteFilename, xmlFilePath);

			uploadStatus = 0;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			uploadStatus = -1;
		} finally {
			manager.close();
		}

		log.info("Exit uploadFile");

		return uploadStatus;
	}

	/**
	 * 
	 * @param manager
	 * @param fileSystemOptions
	 * @param connectionString
	 * @param mediaFilePath
	 * @param remoteFilename
	 * @throws FileSystemException
	 */
	private void uploadFile(StandardFileSystemManager manager, FileSystemOptions fileSystemOptions, String connectionString, String mediaFilePath) throws FileSystemException {
		// Create local file object
		FileObject localFile = manager.resolveFile(mediaFilePath);
		System.out.println(connectionString);
		// Create remote object
		FileObject remoteFile = manager.resolveFile(connectionString, fileSystemOptions);

		// Copy local file to sftp server
		remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);

		log.info("File upload success");

		// close the local file object
		localFile.close();

		// close the remote file object
		remoteFile.close();
	}

	public static String createConnectionString(String hostName, String username, String password, String remoteFilename) {
		return "sftp://" + username + ":" + password + "@" + hostName + "/" + remoteFilename;
	}

	@Override
	public void run() {
		String mediaFilePath = "C:\\ash\\DELTA.MPG";
		String remoteFilename = "DELTA.MPG";
		String xmlFilePath = "C:\\Temp\\Test.xml";
		String xmlRemoteFilename = "Test.xml";

		uploadFile(host, user, password, 22, mediaFilePath, remoteFilename, xmlFilePath, xmlRemoteFilename);
	}
}