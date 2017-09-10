package cz.novros.intellij.code.selfreview.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * Context for {@link State} of this plugin.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class Context {

	/**
	 * All steps of this plugin.
	 */
	public static final int STEPS_COUNT = 3;

	/**
	 * Current state of context.
	 */
	private State currentState;

	/**
	 * Create context with given state.
	 *
	 * @param currentState State which will be set.
	 */
	public Context(final State currentState) {
		this.currentState = currentState;
	}

	/**
	 * Switch context to next state.
	 */
	public void goNextState() {
		currentState.goNextState(this);
	}

	/**
	 * Switch context to previous state.
	 */
	public void goPrevState() {
		currentState.goPrevState(this);
	}

	/**
	 * Set current state to context.
	 *
	 * @param state New state for context.
	 */
	public void setCurrentState(@NotNull final State state) {
		currentState = state;
	}

	/**
	 * Return current step of this context.
	 *
	 * @return Number of current step.
	 */
	public int getCurrentStep() {
		return currentState.getStep();
	}

	/**
	 * Return current name of this context.
	 *
	 * @return Name of current step.
	 */
	public String getCurrentName() {
		return currentState.getName();
	}

	/**
	 * Return current content of this context.
	 *
	 * @return Content of current step.
	 */
	public List<Pair<String, String>> getCurrentContent() {
		return currentState.getContent();
	}
}
