package cz.novros.intellij.code.selfreview.view.utils;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.intellij.util.ui.UIUtil;

import org.jetbrains.annotations.NotNull;

/**
 * Helper class to create/capture and manipulate images.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class ImageUtils {

	/**
	 * Block creation of utility class.
	 */
	private ImageUtils() {
	}

	/**
	 * Capture screenshot from component.
	 *
	 * @param component Component from which will be image created.
	 *
	 * @return Screenshot of component.
	 */
	@NotNull
	public static BufferedImage captureComponent(@NotNull final Component component) {
		final BufferedImage image = UIUtil.createImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);

		if (!component.isShowing()) {
			component.addNotify();
		}

		component.paintAll(image.getGraphics());
		return image;
	}

	/**
	 * Combine two images together, side-by-side.
	 *
	 * @param left  Image on the left.
	 * @param right Image on the right.
	 *
	 * @return Combined two images.
	 */
	public static BufferedImage combineImages(@NotNull final BufferedImage left, @NotNull final BufferedImage right) {
		final BufferedImage combinedImage = UIUtil.createImage(
				left.getWidth() + right.getWidth(),
				Math.max(left.getHeight(), right.getHeight()),
				BufferedImage.TYPE_INT_ARGB);

		final Graphics graphics = combinedImage.getGraphics();
		graphics.drawImage(left, 0, 0, null);
		graphics.drawImage(right, left.getWidth(), 0, null);

		return combinedImage;
	}
}
