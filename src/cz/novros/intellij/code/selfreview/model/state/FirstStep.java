package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.State;

public class FirstStep implements State {

	@Override
	public void goNextState(@NotNull final Context context) {
		context.setCurrentState(new SecondStep());
	}

	@Override
	public void goPrevState(@NotNull final Context context) {
		context.setCurrentState(new ThirdStep());
	}

	@Override
	public int getStep() {
		return 1;
	}

	@Override
	public String getName() {
		return "Prepare a branch";
	}

	@Override
	public List<Pair<String, String>> getContent() {
		final List<Pair<String, String>> content = new ArrayList<>();

		content.add(Pair.of(
				"Refresh source code and create new branch with name: \"<branch-type>-<issue-id>\"",
				"\n" +
						"# Switch to the integration branch\n" +
						"git checkout develop\n" +
						"\n" +
						"# Get the latest code\n" +
						"git pull\n" +
						"\n" +
						"# Create a new topic branch with the name \"feature-135\"\n" +
						"git checkout -b feature-135"
		));

		return content;
	}
}
