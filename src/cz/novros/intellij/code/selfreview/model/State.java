package cz.novros.intellij.code.selfreview.model;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public interface State {

	void goNextState(@NotNull final Context context);

	void goPrevState(@NotNull final Context context);

	int getStep();

	String getName();

	List<Pair<String, String>> getContent();
}
