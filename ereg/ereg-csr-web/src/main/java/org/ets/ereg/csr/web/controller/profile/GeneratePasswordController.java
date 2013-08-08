package org.ets.ereg.csr.web.controller.profile;
import javax.annotation.Resource;


@Controller
@RequestMapping("/generatePassword")
public class GeneratePasswordController {

    @Resource(name = "generatePasswordBusinessService")
    private GeneratePasswordBusinessService generatePasswordBusinessService;
	/**
	 * Dummy method to display the generate password page. To be deleted after
	 * integrating with csr profile page.
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @param profileForm
	 * @param errors
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String start() {
		return generatePasswordpage();
	}

	/**
	 * Return GeneratePasswordDemo page
	 * @return
	 */
	public String generatePasswordpage() {
		return "profile/GeneratePasswordDemo";
	}


	/**
	 * Method to generate new password and adding the same to the model
	 * @param generateNewPassword
	 * @param model
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/generateNewPassword", method = RequestMethod.GET)
	public String generateNewPassword(
			@ModelAttribute("generatedPasswordString") String generateNewPassword,
			Model model) throws ServiceException {
		generateNewPassword = generatePasswordBusinessService
				.generateNewPassword();
		model.addAttribute("generatedPasswordString", generateNewPassword);
	}

}