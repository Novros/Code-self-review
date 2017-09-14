package cz.novros.intellij.code.selfreview.model;

import org.jetbrains.annotations.Nullable;

/**
 * State model for {@link State}.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public interface StateModel {

	/**
	 * Return first state in model.
	 *
	 * @return First state or null if model is empty.
	 */
	@Nullable
	State getFirstState();

	/**
	 * Return last state in model
	 *
	 * @return Last state or null if model is empty.
	 */
	@Nullable
	State getLastState();

	/**
	 * Return size model.
	 *
	 * @return Number of state in model.
	 */
	int getSize();

	/**
	 * Check if model is empty.
	 *
	 * @return True if model is empty, otherwise false.
	 */
	boolean isEmpty();
}
