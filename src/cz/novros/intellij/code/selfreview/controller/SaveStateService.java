package cz.novros.intellij.code.selfreview.controller;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;

import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.settings.SaveState;
import cz.novros.intellij.code.selfreview.model.state.FirstStep;

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
	private Context context = new Context(new FirstStep());

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
		for (int i = context.getCurrentStep(); i <= Context.STEPS_COUNT && i < saveState.step; i++) {
			context.goNextState();
		}
	}
}
