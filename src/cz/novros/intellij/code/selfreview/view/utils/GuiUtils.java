package cz.novros.intellij.code.selfreview.view.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.util.ResourceUtils;

/**
 * Contains constants for GUI and some helper methods.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class GuiUtils {

	/**
	 * Cache for loaded icons.
	 */
	private static final Map<String, Icon> iconCache = new HashMap<>();

	/**
	 * Constant for gap in all layouts.
	 */
	public static final int LAYOUT_GAP = 8;

	/**
	 * Block creation of utility class.
	 */
	private GuiUtils() {
	}

	/**
	 * Load icon from resource folder.
	 *
	 * @return Loaded icon or null if icon was not found.
	 */
	@Nullable
	public static Icon getIconFromResource(@NotNull final String fileName) {
		Icon icon = iconCache.get(fileName);

		if (icon == null) {
			final URL path = ResourceUtils.getResourcePath(fileName);

			if (path == null) {
				return null;
			}

			icon = new ImageIcon(path);
			iconCache.put(fileName, icon);
		}

		return icon;
	}
}
