package cz.novros.intellij.code.selfreview.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import cz.novros.intellij.code.selfreview.model.exception.DataParserException;
import cz.novros.intellij.code.selfreview.model.state.ImmutableState;
import cz.novros.intellij.code.selfreview.model.state.ImmutableStateModel;
import cz.novros.intellij.code.selfreview.model.state.ProcessLineData;
import cz.novros.intellij.code.selfreview.util.ResourceUtils;

/**
 * Parser of data in file.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class DataParser {

	/**
	 * Regex for matching state.
	 */
	private static final Pattern STATE_REGEX = Pattern.compile("^([0-9]+\\. ).*");

	/**
	 * Regex for matching step.
	 */
	private static final Pattern STEP_REGEX = Pattern.compile("^(\\* ).+");

	/**
	 * Path to default data in resource folder.
	 */
	private static final String DEFAULT_DATA_PATH = "data.txt";

	/**
	 * Create state model from default data file.
	 *
	 * @return First state in model state. If data was not found, it will return empty state.
	 */
	public static StateModel createStateModel() {
		return createStateModel(DEFAULT_DATA_PATH, true);
	}

	/**
	 * Create state model from given file path.
	 *
	 * @param filePath Path to file from which should be model created.
	 *
	 * @return First state in model state. If data was not found, it will return empty state.
	 */
	public static StateModel createStateModel(@NotNull final String filePath) {
		return createStateModel(filePath, false);
	}

	/**
	 * Create state model from given file path.
	 *
	 * @param filePath       Path to file from which should be model created.
	 * @param isResourceFile True if filePath is in resource folder, otherwise false.
	 *
	 * @return First state in model state. If data was not found, it will return empty state.
	 */
	private static StateModel createStateModel(@NotNull final String filePath, final boolean isResourceFile) {
		final ImmutableStateModel stateModel = new ImmutableStateModel();

		try (final BufferedReader reader = createReaderFromFile(filePath, isResourceFile)) {
			loadStates(reader).forEach(stateModel::addState);
		} catch (final DataParserException | IOException exception) {
			log.error("Could not load plugin data!", exception);
		}

		return stateModel;
	}

	/**
	 * Load all states from file "resources/data.txt".
	 *
	 * @param reader BufferedReader for reading lines of data.
	 *
	 * @return All loaded states in list.
	 *
	 * @throws IllegalArgumentException If file with data was not found.
	 * @throws DataParserException      When was problem with file data.
	 */
	private static List<ImmutableState> loadStates(@NotNull final BufferedReader reader) throws DataParserException {
		final ProcessLineData data = new ProcessLineData();

		try {
			// Process each line
			String line = reader.readLine();
			while (line != null) {
				processLine(data, line);
				line = reader.readLine();
			}

			// Add last worked state
			if (data.getBuilder() != null) {
				data.buildAndAddState();
			}
		} catch (final IOException exception) {
			throw new DataParserException("There was problem with reading data file!", exception);
		}

		return data.getStates();
	}

	/**
	 * Process one line from data file.
	 *
	 * @param data Data dto which holds actual processed data for building {@link State}.
	 * @param line Line from data file, which should be processed.
	 */
	private static void processLine(@NotNull final ProcessLineData data, @NotNull final String line) {
		if (STATE_REGEX.matcher(line).find()) {
			if (data.getBuilder() != null) {
				data.tryAddContentItem();
				data.buildAndAddState();
			}

			data.resetBuilder(line);

		} else if (STEP_REGEX.matcher(line).find()) {
			data.tryAddContentItem();
			data.setShortDescription(line);

		} else {
			if (data.getContent().length() > 0 || !line.isEmpty()) {
				data.getContent().append(line).append("\n"); // FIXME - Add bad end line.
			}
		}
	}

	/**
	 * Create buffered reader from file.
	 *
	 * @param file           Path to file.
	 * @param isResourceFIle True if file is from resource folder and false from somewhere else.
	 *
	 * @return Buffered reader for file.
	 *
	 * @throws FileNotFoundException If file was not found.
	 */
	private static BufferedReader createReaderFromFile(@NotNull final String file, final boolean isResourceFIle) throws FileNotFoundException {
		final InputStream fileStream = isResourceFIle ?
				ResourceUtils.getResourceFileStream("data.txt") :
				new FileInputStream(file);

		if (fileStream == null) {
			throw new FileNotFoundException("Data file was not found!");
		}

		return new BufferedReader(new InputStreamReader(fileStream));
	}
}
