package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.State;

public class MutableState implements State {

	private String name;
	private int step;
	private List<Pair<String, String>> content = new ArrayList<>();
	private State nextState;
	private State previousState;

	public MutableState() {
	}

	@Override
	public void goNextState(@NotNull final Context context) {
		context.setCurrentState(nextState);
	}

	@Override
	public void goPrevState(@NotNull final Context context) {
		context.setCurrentState(previousState);
	}

	@Override
	public int getStep() {
		return step;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Pair<String, String>> getContent() {
		return content;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStep(final int step) {
		this.step = step;
	}

	public void setContent(final List<Pair<String, String>> content) {
		this.content = content;
	}

	public boolean addContent(final Pair<String, String> stringStringPair) {
		return content.add(stringStringPair);
	}

	public void setNextState(final State nextState) {
		this.nextState = nextState;
	}

	public void setPreviousState(final State previousState) {
		this.previousState = previousState;
	}
}
