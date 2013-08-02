package org.ets.ereg.common.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.ets.ereg.common.exception.ERegRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseDebugController {
	private static Logger log = LoggerFactory.getLogger(BaseDebugController.class);
	private static final String LOGS_DIR_NAME = "logs";

	public String getTomcatBase() {
		return System.getProperty("catalina.base");
	}

	public String getTomcatLogsDirectoryPath() {
		if (StringUtils.isEmpty(getTomcatBase())) {
			log.error("Could not retrive tomcat base");
			return StringUtils.EMPTY;
		}
		return getTomcatBase() + File.separatorChar + LOGS_DIR_NAME;
	}

	public Collection<File> getLogsFiles() {
		if (StringUtils.isEmpty(getTomcatLogsDirectoryPath())) {
			log.error("Could not construct tomcat log directory path");
			return Collections.emptyList();
		}
		return FileUtils.listFiles(new File(getTomcatLogsDirectoryPath()), null, false);
	}

	public String getFileContents(String fileNameToView) {
		if (StringUtils.isEmpty(getTomcatLogsDirectoryPath())) {
			log.error("Invalid filename");
			return StringUtils.EMPTY;
		}
		String logFile = getTomcatLogsDirectoryPath() + File.separatorChar + fileNameToView;
		String fileContent = "";
		log.info("logFile: {}",logFile);
		int SIZE_TO_SEE = 100000;
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(logFile, "r");
			long position = raf.length() > SIZE_TO_SEE ? raf.length() - SIZE_TO_SEE : 0;
			raf.seek(position);
			StringBuffer fileContentBuffer = new StringBuffer(SIZE_TO_SEE);
			String line = null;
			raf.readLine();
			while ((line = raf.readLine()) != null) {
				fileContentBuffer.append(line).append(SystemUtils.LINE_SEPARATOR);
			}
			fileContent = fileContentBuffer.toString();
		} catch (FileNotFoundException e) {
			log.error("Unable to find logfile : {}",logFile);
			throw new ERegRuntimeException("Unable to find logfile :"+logFile,e);
		} catch (IOException ioe) {
			log.error("Unable to read logfile : {}",logFile);
			throw new ERegRuntimeException("Unable to red logfile :"+logFile,ioe);
		}
		return fileContent;
	}

}
