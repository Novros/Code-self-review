package cz.novros.intellij.code.selfreview.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.model.exception.DataParserException;
import cz.novros.intellij.code.selfreview.model.state.MutableState;
import cz.novros.intellij.code.selfreview.util.ResourceUtils;

/**
 * Parser of data in file.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class DataParser {

	/**
	 * Regex for matching state.
	 */
	private static final Pattern STATE_REGEX = Pattern.compile("^([0-9]+\\. ).*");

	/**
	 * Regex for matching step.
	 */
	private static final Pattern STEP_REGEX = Pattern.compile("^(\\* ).+");

	public static State createStateModel() {
		List<MutableState> loadedStates = new ArrayList<>();

		try {
			loadedStates = loadStates();
		} catch (final DataParserException exception) {
			JOptionPane.showMessageDialog(null, "Could not load plugin data!", "Error", JOptionPane.ERROR_MESSAGE);
			// TODO Log exception and show error dialog.
		}

		if (loadedStates.isEmpty()) {
			return new MutableState();
		}

		State previousState = loadedStates.get(loadedStates.size() - 1);
		for (int i = 0; i < loadedStates.size(); i++) {
			final MutableState state = loadedStates.get(i);

			if (previousState != null) {
				state.setPreviousState(previousState);
			}

			if (i < (loadedStates.size() - 1)) {
				state.setNextState(loadedStates.get(i + 1));
			} else {
				state.setNextState(loadedStates.get(0));
			}

			previousState = state;
		}

		return loadedStates.get(0); // TODO Return steps count and remove number
	}

	/**
	 * Load all states from file "resources/data.txt".
	 *
	 * @return All loaded states in list.
	 *
	 * @throws IllegalArgumentException If file with data was not found.
	 * @throws DataParserException      When was problem with file data.
	 */
	private static List<MutableState> loadStates() throws DataParserException {
		final InputStream fileStream = ResourceUtils.getResourceFileStream("data.txt");

		if (fileStream == null) {
			throw new IllegalStateException("Data not found!");
		}

		final List<MutableState> states = new ArrayList<>();

		MutableState state = null;
		String shortDescription = null;
		final StringBuilder content = new StringBuilder("");

		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream))) {
			String line = reader.readLine();
			while (line != null) {
				if (STATE_REGEX.matcher(line).find()) {
					if (state != null) {
						addContentItem(state, shortDescription, content);
						states.add(state);
					}

					shortDescription = null;

					state = new MutableState();
					state.setName(line);

				} else if (STEP_REGEX.matcher(line).find()) {
					addContentItem(state, shortDescription, content);
					shortDescription = line;

				} else {
					if (content.length() > 0 || !line.isEmpty()) {
						content.append(line).append("\n"); // FIXME
					}
				}

				line = reader.readLine();
			}

			if (state != null) {
				states.add(state);
			}
		} catch (
				final IOException exception)

		{
			throw new DataParserException("There was problem with reading data file!", exception);
		}

		return states;
	}

	private static void addContentItem(@Nullable final MutableState state, @Nullable final String shortDescription, @NotNull final StringBuilder description) {
		if (shortDescription != null && !shortDescription.isEmpty() && state != null) {
			state.addContent(org.apache.commons.lang3.tuple.Pair.of(shortDescription, description.toString()));
		}

		description.setLength(0);
	}
}
