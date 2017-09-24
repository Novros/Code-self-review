package cz.novros.intellij.code.selfreview.controller;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;

import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.DataParser;
import cz.novros.intellij.code.selfreview.model.StateModel;
import cz.novros.intellij.code.selfreview.model.settings.SaveState;

/**
 * Service for saving settings ({@link SaveState}).
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
//    storages = {@Storage("$WORKSPACE_FILE$")} // For plugin settings
@State(name = "SaveStateService", storages = {@Storage("code-self-review-state.xml")})
public class SaveStateService implements PersistentStateComponent<SaveState> {

	/**
	 * Actual context from saved settings.
	 */
	private final Context context;

	/**
	 * Constructor SaveStateService creates a new SaveStateService instance.
	 */
	public SaveStateService() {
		final StateModel model = DataParser.createStateModel();
		final cz.novros.intellij.code.selfreview.model.State firstState = model.getFirstState();

		if (firstState == null) {
			context = new Context();
		} else {
			context = new Context(firstState, model.getSize());
		}
	}

	/**
	 * Return set context in this service.
	 *
	 * @return Loaded context from settings.
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Return state, from {@link #context}, to save.
	 *
	 * @return State to save.
	 */
	@Nullable
	@Override
	public SaveState getState() {
		final SaveState state = new SaveState();
		state.step = context.getCurrentStep();
		return state;
	}

	/**
	 * Load state from save settings.
	 *
	 * @param saveState Saved state which should be loaded.
	 */
	@Override
	public void loadState(final SaveState saveState) {
		for (int i = context.getCurrentStep(); i <= context.getStepCount() && i < saveState.step; i++) {
			context.goNextState();
		}
	}
}
