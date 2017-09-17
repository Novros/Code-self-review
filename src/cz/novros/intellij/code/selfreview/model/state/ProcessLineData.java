package cz.novros.intellij.code.selfreview.model.state;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Helper class for processing lines in {@link cz.novros.intellij.code.selfreview.model.DataParser}.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessLineData {

	/**
	 * Regex for finding:
	 * <ul>
	 * <li>empty/whitespace lines at beginning</li>
	 * <li>empty/whitespace lines in middle</li>
	 * <li>trailing whitespace on any non-empty line</li>
	 * </ul>
	 */
	private static final String TRAILING_EMPTY_WHITESPACES_LINE_REGEX = "([\\n\\r]+\\s*)*$";

	/**
	 * Actual created {@link cz.novros.intellij.code.selfreview.model.State}.
	 */
	final List<ImmutableState> states = new ArrayList<>();
	/**
	 * Builder for actually processed {@link cz.novros.intellij.code.selfreview.model.State}.
	 */
	ImmutableState.ImmutableStateBuilder builder;
	/**
	 * Items for actual {@link #builder}.
	 */
	List<Pair<String, String>> items = new ArrayList<>();
	/**
	 * Short description for actual {@link #content}
	 */
	@Setter
	String shortDescription;

	/**
	 * Content for actual {@link #shortDescription}.
	 */
	StringBuilder content = new StringBuilder();

	/**
	 * Build state from {@link #builder} and add it to {@link #states}.
	 */
	public void buildAndAddState() {
		builder.content(items);
		states.add(buildAndConnectState());
	}

	/**
	 * Reset builder for creating next state.
	 *
	 * @param name Name of next state.
	 */
	public void resetBuilder(@NotNull final String name) {
		builder = ImmutableState.builder();
		items = new ArrayList<>();
		builder.name(name);
		shortDescription = null;
	}

	/**
	 * Will try to add content item to actual {@link cz.novros.intellij.code.selfreview.model.State} in {@link #builder}.
	 */
	public void tryAddContentItem() {
		if (canAddContentItem()) {
			addContentItem();
		}

		content.setLength(0);
	}

	/**
	 * If content item can be added.
	 *
	 * @return True if item is ready to be added. False otherwise.
	 */
	private boolean canAddContentItem() {
		return shortDescription != null && !shortDescription.isEmpty() && builder != null;
	}

	/**
	 * Add content item to {@link #items}.
	 */
	private void addContentItem() {
		items.add(Pair.of(shortDescription, getContentItem()));
	}

	/**
	 * Return content for step item.
	 *
	 * @return Fined string.
	 */
	private String getContentItem() {
		content.trimToSize();

		final String itemContent = content.toString();
		return itemContent.replaceAll(TRAILING_EMPTY_WHITESPACES_LINE_REGEX, "");
	}

	/**
	 * Build {@link ImmutableState} from {@link #builder} and connect it to states in {@link #states}.
	 *
	 * @return Return builded {@link ImmutableState} from {@link #builder}.
	 */
	@NotNull
	private ImmutableState buildAndConnectState() {
		ImmutableState state;

		builder.step(states.size() + 1);

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
