package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.model.Context;
import cz.novros.intellij.code.selfreview.model.State;

public class SecondStep implements State {
	@Override
	public void goNextState(@NotNull final Context context) {
		context.setCurrentState(new ThirdStep());
	}

	@Override
	public void goPrevState(@NotNull final Context context) {
		context.setCurrentState(new FirstStep());
	}

	@Override
	public int getStep() {
		return 2;
	}

	@Override
	public String getName() {
		return "Create modifications";
	}

	@Override
	public List<Pair<String, String>> getContent() {
		final List<Pair<String, String>> content = new ArrayList<>();

		content.add(Pair.of(
				"Commit early and often.",
				"See commits as checkpoints. Coming up with a solution usually requires some experimentation. If you happen to break anything along the way, you can simply revert to a previous version of your code."
		));
		content.add(Pair.of(
				"Recover context from logs and diffs.",
				"- observe recently opened files and hit undo/redo a couple of times in my editor\n" +
						"- check git status, git diff and maybe even git log\n" +
						"- git diff shows everything since my last commit"
		));
		content.add(Pair.of(
				"Mind your commit message.",
				"- write descriptive commit messages\n" +
						"- add each logical change to a separate commit"
		));
		content.add(Pair.of(
				"Always leave the campground cleaner than you found it.",
				""
		));

		return content;
	}
}
