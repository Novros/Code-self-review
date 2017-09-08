package cz.novros.intellij.code.selfreview.controller;

import cz.novros.intellij.code.selfreview.model.Context;

public class ReviewController {

	private Context context;

	public ViewData nextStep() {
		context.goNextState();
		return getViewData();
	}

	public ViewData prevStep() {
		context.goPrevState();
		return getViewData();
	}

	public ViewData getCurrentStep() {
		return getViewData();
	}

	public int getStepsCount() {
		return Context.STEPS_COUNT;
	}

	public void setContext(final Context context) {
		this.context = context;
	}

	private ViewData getViewData() {
		final ViewData dto = new ViewData();

		dto.name = context.getCurrentName();
		dto.step = context.getCurrentStep();
		dto.content = context.getCurrentContent();

		return dto;
	}
}
