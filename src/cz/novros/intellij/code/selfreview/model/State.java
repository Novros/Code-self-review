package cz.novros.intellij.code.selfreview.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for plugin state.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public interface State {

	/**
	 * Set next state to context.
	 *
	 * @param context Context which will be edited.
	 */
	void goNextState(@NotNull final Context context);

	/**
	 * Set previous state to context.
	 *
	 * @param context Context which will be edited.
	 */
	void goPrevState(@NotNull final Context context);

	/**
	 * Return number of this step.
	 *
	 * @return Number of step.
	 */
	int getStep();

	/**
	 * Return name of this step.
	 *
	 * @return Name of step.
	 */
	String getName();

	/**
	 * Return content of this step in format <Short-description,description>.
	 *
	 * @return Content of step.
	 */
	List<Pair<String, String>> getContent();
}
