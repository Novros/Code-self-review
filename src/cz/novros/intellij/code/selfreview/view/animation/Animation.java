package cz.novros.intellij.code.selfreview.view.animation;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.util.Timer;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.view.utils.ImageUtils;

/**
 * This class provides simple animations over containers.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class Animation {

	/**
	 * Block creation of utility class.
	 */
	private Animation() {
	}

	/**
	 * Do transition animation over container with card layout.
	 *
	 * @param container On which container animation should be.
	 * @param from      On which component animation should start.
	 * @param to        To which component animation should end.
	 * @param direction Which direction should animation be.
	 */
	public static void transitionAnimation(@NotNull final Container container, @NotNull final Component from, @NotNull final Component to, ScrollTask.Direction direction) {
		if (!(container.getLayout() instanceof CardLayout)) {
			throw new IllegalArgumentException("Could not run animation on another layout then CardLayout");
		}

		final JScrollPane temporaryScrollPane = createAnimationComponent(getTransitionComponent(from, to, direction));

		final CardLayout cardLayout = (CardLayout) container.getLayout();
		container.add(temporaryScrollPane, "transition");
		cardLayout.show(container, "transition");
		container.add(to, "newcard");

		final Runnable callback = () -> {
			cardLayout.show(container, "newcard");
			container.remove(temporaryScrollPane);
		};

		final ScrollTask scrollTask = new ScrollTask(callback, temporaryScrollPane, 30, direction);
		new Timer().scheduleAtFixedRate(scrollTask, 0, 15);
	}

	/**
	 * Create component which should be used in transition animation.
	 *
	 * @param content Content in component.
	 *
	 * @return Scroll pane to use in animation.
	 */
	private static JScrollPane createAnimationComponent(@NotNull final Component content) {
		final JScrollPane temporaryScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		temporaryScrollPane.setViewportView(content);
		temporaryScrollPane.setBorder(null);

		return temporaryScrollPane;
	}

	/**
	 * Create transition component. (two images side-by-side)
	 *
	 * @param from      From which component should animation start.
	 * @param to        To which component should animation end.
	 * @param direction Which direction should animation go.
	 *
	 * @return Component which should be used to transition animation.
	 */
	private static JComponent getTransitionComponent(@NotNull final Component from, @NotNull final Component to, @NotNull final ScrollTask.Direction direction) {
		final BufferedImage fromImage = ImageUtils.captureComponent(from);
		final BufferedImage toImage = ImageUtils.captureComponent(to);

		final BufferedImage combined = direction == ScrollTask.Direction.RIGHT ?
				ImageUtils.combineImages(fromImage, toImage)
				: ImageUtils.combineImages(toImage, fromImage);

		return new JLabel(new ImageIcon(combined));
	}
}
