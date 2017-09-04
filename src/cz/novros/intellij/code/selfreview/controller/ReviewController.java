package cz.novros.intellij.code.selfreview.controller;

import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.model.Context;

public class ReviewController {

	private Context context;

	public ReviewController(@NotNull final Context context) {
		this.context = context;
	}

	public ReviewDto nextStep() {
		context.goNextState();
		return getDtoFromContext();
	}

	public ReviewDto prevStep() {
		context.goPrevState();
		return getDtoFromContext();
	}
	
	public ReviewDto getCurrentStep() {
		return getDtoFromContext();
	}

	public int getStepsCount() {
		return Context.STEPS_COUNT;
	}

	private ReviewDto getDtoFromContext() {
		final ReviewDto dto = new ReviewDto();

		dto.name = context.getCurrentName();
		dto.step = context.getCurrentStep();
		dto.content = context.getCurrentContent();

		return dto;
	}
}
