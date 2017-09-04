package cz.novros.intellij.code.selfreview.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public class Context {

	public static final int STEPS_COUNT = 3;

	private State currentState;

	public Context(@NotNull final State beginState) {
		this.currentState = beginState;
	}

	public void goNextState() {
		currentState.goNextState(this);
	}

	public void goPrevState() {
		currentState.goPrevState(this);
	}

	public void setCurrentState(@NotNull final State state) {
		currentState = state;
	}

	public int getCurrentStep() {
		return currentState.getStep();
	}

	public String getCurrentName() {
		return currentState.getName();
	}

	public List<Pair<String, String>> getCurrentContent() {
		return currentState.getContent();
	}
}
