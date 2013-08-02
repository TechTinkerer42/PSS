package org.ets.ereg.domain.interfaces.model.common;

public interface ProgramType {
	/**
	 * Method to get Code
	 *
	 * @return
	 */
	public abstract String getCode();

	/**
	 * Method to set Code
	 *
	 * @param code
	 */
	public abstract void setCode(String code);

	/**
	 * Method to get Description
	 *
	 * @return
	 */
	public abstract String getDescription();

	/**
	 * Method to set Description
	 *
	 * @param description
	 */
	public abstract void setDescription(String description);

	/**
     * Method to get Display Sequence
     *
     * @return
     */
    public abstract Long getDisplaySequence();

    /**
     * Method to set Display Sequence
     *
     * @param displaySequence
     */
    public abstract void setDisplaySequence(Long displaySequence);

    /**
     * Method to set Displayable
     *
     * @param isDisplayable
     */
    public abstract Boolean isDisplayable();

    /**
     * Method to set Displayable
     *
     * @param isDisplayable
     */
    public abstract void setDisplayable(Boolean isDisplayable);

}
