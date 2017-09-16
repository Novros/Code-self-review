package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import cz.novros.intellij.code.selfreview.model.State;
import cz.novros.intellij.code.selfreview.model.StateModel;

/**
 * Model for states of plugin.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImmutableStateModel implements StateModel {

	/**
	 * List of states ordered by adding into model.
	 */
	List<ImmutableState> states = new ArrayList<>();

	/**
	 * Add state into model.
	 *
	 * @param state State which will be added and connected.
	 */
	public void addState(@NotNull final ImmutableState state) {
		states.add(state);
	}

	/**
	 * Return first state in model.
	 *
	 * @return First state or null if model is empty.
	 */
	@Nullable
	@Override
	public State getFirstState() {
		return states.isEmpty() ? null : states.get(0);
	}

	/**
	 * Return last state in model
	 *
	 * @return Last state or null if model is empty.
	 */
	@Nullable
	@Override
	public State getLastState() {
		return states.isEmpty() ? null : states.get(states.size() - 1);
	}

	/**
	 * Return size model.
	 *
	 * @return Number of state in model.
	 */
	@Override
	public int getSize() {
		return states.size();
	}

	/**
	 * Check if model is empty.
	 *
	 * @return True if model is empty, otherwise false.
	 */
	@Override
	public boolean isEmpty() {
		return states.isEmpty();
	}
}
