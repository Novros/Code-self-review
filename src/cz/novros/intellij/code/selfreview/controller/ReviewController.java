package cz.novros.intellij.code.selfreview.controller;

import cz.novros.intellij.code.selfreview.model.Context;

/**
 * Controller over plugin state. Provides methods to go next state, previous state and so on.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class ReviewController {

	/**
	 * Context for state of plugin.
	 */
	private Context context;

	/**
	 * Go to the next step of plugin.
	 *
	 * @return Data of next step to display.
	 */
	public ViewData nextStep() {
		context.goNextState();
		return getViewData();
	}

	/**
	 * Go to the previous step of plugin.
	 *
	 * @return Data of previous step to display.
	 */
	public ViewData prevStep() {
		context.goPrevState();
		return getViewData();
	}

	/**
	 * Return data to display for current step of plugin.
	 *
	 * @return Data to display.
	 */
	public ViewData getCurrentStep() {
		return getViewData();
	}

	/**
	 * Return all steps of plugin.
	 *
	 * @return Number of steps.
	 */
	public int getStepsCount() {
		return context.getStepCount();
	}

	/**
	 * Set new context of for state of plugin.
	 *
	 * @param context New context.
	 */
	public void setContext(final Context context) {
		this.context = context;
	}

	/**
	 * Return view data from current step.
	 *
	 * @return View data to display.
	 */
	private ViewData getViewData() {
		final ViewData dto = new ViewData();

		dto.name = context.getCurrentName();
		dto.step = context.getCurrentStep();
		dto.content = context.getCurrentContent();

		return dto;
	}
}
