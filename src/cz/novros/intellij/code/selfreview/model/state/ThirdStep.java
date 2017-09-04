package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.State;

public class ThirdStep implements State {

	@Override
	public void goNextState(@NotNull final Context context) {
		context.setCurrentState(new FirstStep());
	}

	@Override
	public void goPrevState(@NotNull final Context context) {
		context.setCurrentState(new SecondStep());
	}

	@Override
	public int getStep() {
		return 3;
	}

	@Override
	public String getName() {
		return "Review and share";
	}

	@Override
	public List<Pair<String, String>> getContent() {
		final List<Pair<String, String>> content = new ArrayList<>();

		content.add(Pair.of(
				"",
				""
		));

		return content;
	}
}
