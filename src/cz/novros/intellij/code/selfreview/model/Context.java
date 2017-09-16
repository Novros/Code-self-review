package cz.novros.intellij.code.selfreview.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Context for {@link State} of this plugin.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class Context {

	/**
	 * All steps of this context.
	 */
	private final int stepsCount;

	/**
	 * Current state of context.
	 */
	@Nullable
	private State currentState;

	/**
	 * Create empty context.
	 */
	public Context() {
		this.currentState = null;
		this.stepsCount = 0;
	}

	/**
	 * Create context with given state.
	 *
	 * @param currentState State which will be set.
	 */
	public Context(@NotNull final State currentState, final int stepsCount) {
		this.currentState = currentState;
		this.stepsCount = stepsCount;
	}

	/**
	 * Switch context to next state.
	 */
	public void goNextState() {
		if (currentState != null) {
			currentState.goNextState(this);
		}
	}

	/**
	 * Switch context to previous state.
	 */
	public void goPrevState() {
		if (currentState != null) {
			currentState.goPrevState(this);
		}
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
		return currentState != null ? currentState.getStep() : 0;
	}

	/**
	 * Return current name of this context.
	 *
	 * @return Name of current step.
	 */
	@Nullable
	public String getCurrentName() {
		return currentState != null ? currentState.getName() : null;
	}

	/**
	 * Return current content of this context.
	 *
	 * @return Content of current step.
	 */
	@NotNull
	public List<Pair<String, String>> getCurrentContent() {
		return currentState != null ? currentState.getContent() : ImmutableList.of();
	}

	/**
	 * Method getStepCount returns the stepCount of this Context object.
	 *
	 * @return the stepCount (type int) of this Context object.
	 */
	public int getStepCount() {
		return stepsCount;
	}
}
