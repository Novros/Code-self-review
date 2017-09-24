package cz.novros.intellij.code.selfreview.util;

import java.io.InputStream;
import java.net.URL;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.view.utils.GuiUtils;

/**
 * Contains helper methods for resources.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class ResourceUtils {

	/**
	 * Block creation of utility class.
	 */
	private ResourceUtils() {
	}

	/**
	 * Get url of resource file.
	 *
	 * @param fileName Name of file in resource folder.
	 *
	 * @return Url of resource of file or null if not found.
	 */
	@Nullable
	public static URL getResourcePath(@NotNull final String fileName) {
		return GuiUtils.class.getClassLoader().getResource(fileName);
	}

	/**
	 * Get input stream of resource file.
	 *
	 * @param fileName Name of file in resource folder.
	 *
	 * @return Input stream of resource of file or null if not found.
	 */
	@Nullable
	public static InputStream getResourceFileStream(@NotNull final String fileName) {
		return GuiUtils.class.getClassLoader().getResourceAsStream(fileName);
	}
}
