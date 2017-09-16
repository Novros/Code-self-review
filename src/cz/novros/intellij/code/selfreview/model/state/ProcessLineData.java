package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessLineData {
	private final List<ImmutableState> states = new ArrayList<>();
	private ImmutableState.ImmutableStateBuilder builder;

	private List<Pair<String, String>> items = new ArrayList<>();
	private String shortDescription;
	private StringBuilder content = new StringBuilder();

	public void buildAndAddState() {
		builder.content(items);
		states.add(connectState());
	}
	
	public void resetBuilder(@NotNull final String name) {
		builder = ImmutableState.builder();
		items = new ArrayList<>();
		builder.name(name);
		shortDescription = null;
	}
	
	public void tryAddContentItem() {
		if (canAddContentItem()) {
			addContentItem();
		}

		content.setLength(0);
	}

	private boolean canAddContentItem() {
		return shortDescription != null && !shortDescription.isEmpty() && builder != null;
	}

	private void addContentItem() {
		items.add(Pair.of(shortDescription, content.toString()));
	}

	private ImmutableState connectState() {
		ImmutableState state;

		if (!states.isEmpty()) {
			final ImmutableState firsState = states.get(0);
			final ImmutableState lastState = states.get(states.size() - 1);

			builder.nextState(firsState);
			builder.previousState(lastState);

			state = builder.build();

			firsState.setPreviousState(state);
			lastState.setNextState(state);
		} else {
			state = builder.build();
		}

		return state;
	}
}
