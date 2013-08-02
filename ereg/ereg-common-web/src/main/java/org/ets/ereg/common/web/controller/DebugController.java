package org.ets.ereg.common.web.controller;

import org.ets.ereg.common.web.util.CommonWebMappingConstants;
import org.ets.ereg.common.web.util.CommonJSPMappingConstants;
import org.ets.ereg.common.web.controller.BaseDebugController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("debugcontroller")
@RequestMapping(CommonWebMappingConstants.BASE_DEBUG_MAPPING)
public class DebugController extends BaseDebugController {
	private static Logger log = LoggerFactory.getLogger(DebugController.class);

	@RequestMapping(value = CommonWebMappingConstants.BASE_DEBUG_MAPPING, method = RequestMethod.GET)
	public String goToDebugPage() {
		return CommonJSPMappingConstants.DEBUG_HOME;
	}

	@RequestMapping(value = CommonWebMappingConstants.DEBUG_VIEW_LOGS_MAPPING, method = RequestMethod.GET)
	public String goToViewLogsPage(Model model) {
		log.info("getting logs information");
		model.addAttribute("filesList", getLogsFiles());
		model.addAttribute("logsDirectoryPath", getTomcatLogsDirectoryPath());
		return CommonJSPMappingConstants.DEBUG_VIEW_LOGS;
	}

	@RequestMapping(value = CommonWebMappingConstants.DEBUG_BUILD_INFO, method = RequestMethod.GET)
	public String goToBuildInfoPage() {
		return CommonJSPMappingConstants.DEBUG_BUILD_INFO;
	}

	@RequestMapping(value = CommonWebMappingConstants.DEBUG_VIEW_LOG_FILE, method = RequestMethod.POST)
	public @ResponseBody String viewLogFileContent(@RequestParam("selectedfile") String fileToView) {
		return getFileContents(fileToView);
	}

}
