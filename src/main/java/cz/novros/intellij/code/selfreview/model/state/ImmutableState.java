package cz.novros.intellij.code.selfreview.model.state;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.State;

/**
 * Immutable implementation of {@link State}. To fancy creation use {@link ImmutableStateBuilder}.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImmutableState implements State {

	/**
	 * Name of state.
	 */
	String name;
	/**
	 * Order number of state.
	 */
	int step;
	/**
	 * Content of state.
	 */
	List<Pair<String, String>> content;
	/**
	 * Pointer to next state.
	 */
	@NonFinal
	State nextState;
	/**
	 * Pointer to previous state.
	 */
	@NonFinal
	State previousState;

	/**
	 * Set next state to context.
	 *
	 * @param context Context which will be edited.
	 */
	@Override
	public void goNextState(@NotNull final Context context) {
		context.setCurrentState(nextState);
	}

	/**
	 * Set previous state to context.
	 *
	 * @param context Context which will be edited.
	 */
	@Override
	public void goPrevState(@NotNull final Context context) {
		context.setCurrentState(previousState);
	}

	/**
	 * Set next state for this state. This method should be called only after creation.
	 *
	 * @param nextState Next state.
	 */
	void setNextState(@NotNull final State nextState) {
		this.nextState = nextState;
	}

	/**
	 * Set previous state for this state. This method should be called only after creation.
	 *
	 * @param previousState Previous state.
	 */
	void setPreviousState(@NonFinal final State previousState) {
		this.previousState = previousState;
	}
}
