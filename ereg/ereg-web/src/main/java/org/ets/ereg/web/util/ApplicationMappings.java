/*
 * ApplicationMappings.java
 *
 * Copyright (c) Educational Testing Service
 *
 * This software is the confidential and proprietary information of 
 * Educational Testing Service. ("Confidential Information").
 * 
 */ 
package org.ets.ereg.web.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ApplicationMappings
 *
 * @version	1.0 May 8, 2013
 * @author 	Venkat Shanmugam
 * 
 * @history
 * May 8, 2013 VS Initial Creation
 *
 **/
public class ApplicationMappings {
	
		public static final String DEFAULT_SITE_CDE = "mo";
		
		public static class UriProgramMappings{
			public static Map<String, String> uriToPrograms = new HashMap<String, String>();
			
			static {
				uriToPrograms.put("hse", "HSE");
				uriToPrograms.put("efs", "EFS");
				uriToPrograms.put("mo", "MPA");
			}
		}
		public static class ProgramToUriMappings{
			public static Map<String, String> programsToUri = new HashMap<String, String>();
			
			static {
				programsToUri.put("HSE", "hse");
				programsToUri.put("EFS", "efs");
				programsToUri.put("MPA", "mo");
			}
		}		
		
		public static class SiteDescriptionMappings{
			public static Map<String, String> siteDescriptions = new HashMap<String, String>();
			
			static {
				siteDescriptions.put("hse", "HiSET");
				siteDescriptions.put("efs", "EFS");
				siteDescriptions.put("mo", "MPA");
			}
		}
		
		public static class ProgramDescriptionMappings{
			public static Map<String, String> programDescriptions = new HashMap<String, String>();
			
			static {
				programDescriptions.put("HSE", "HiSET");
				programDescriptions.put("EFS", "ETS Fundamental Science");
				programDescriptions.put("MPA", "Missouri Performance Assessments");
			}
			
			public static Map<String, String> programShortDescriptions = new HashMap<String, String>();
			
			static {
				programShortDescriptions.put("HSE", "HiSET");
				programShortDescriptions.put("EFS", "EFS");
				programShortDescriptions.put("MPA", "MPA");
			}
		}		
		
}
