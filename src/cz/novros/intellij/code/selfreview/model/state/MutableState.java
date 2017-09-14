package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.State;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MutableState implements State {

	String name;
	int step;
	List<Pair<String, String>> content = new ArrayList<>();
	State nextState;
	State previousState;

	public MutableState() {
	}

	@Override
	public void goNextState(@NotNull final Context context) {
		context.setCurrentState(nextState);
	}

	@Override
	public void goPrevState(@NotNull final Context context) {
		context.setCurrentState(previousState);
	}

	public void addContent(final Pair<String, String> stringStringPair) {
		content.add(stringStringPair);
	}
}
