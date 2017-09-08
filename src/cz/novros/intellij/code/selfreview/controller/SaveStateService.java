package cz.novros.intellij.code.selfreview.controller;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;

import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.state.FirstStep;
import cz.novros.intellij.code.selfreview.settings.SaveState;

//    storages = {@Storage("$WORKSPACE_FILE$")} // For plugin settings
@State(name = "SaveStateService", storages = {@Storage("code-self-review-state.xml")})
public class SaveStateService implements PersistentStateComponent<SaveState> {

	private Context context = new Context(new FirstStep());

	public Context getContext() {
		return context;
	}

	@Nullable
	@Override
	public SaveState getState() {
		final SaveState state = new SaveState();
		state.step = context.getCurrentStep();
		return state;
	}

	@Override
	public void loadState(final SaveState saveState) {
		for (int i = context.getCurrentStep(); i <= Context.STEPS_COUNT && i < saveState.step; i++) {
			context.goNextState();
		}
	}
}
