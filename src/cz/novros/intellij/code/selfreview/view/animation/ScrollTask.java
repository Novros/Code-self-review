package cz.novros.intellij.code.selfreview.view.animation;

import java.util.TimerTask;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

/**
 * Scroll task, which scroll {@link java.awt.ScrollPane} to given direction.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class ScrollTask extends TimerTask {

	/**
	 * Callback which will be run at the end of animation.
	 */
	private Runnable callback;

	/**
	 * Scroll bar, which will be changed.
	 */
	private JScrollBar scrollBar;

	/**
	 * Of which amount to scroll.
	 */
	private final int step;
	/**
	 * Maximal value, which must animation reach.
	 */
	private final int end;

	/**
	 * Actual position of {@link #scrollBar}. Must be as attribute, because {@link #scrollBar} is stuck after
	 * {@link JScrollPane#getWidth()} value.
	 */
	private int offset;

	/**
	 * Create timed scroll task to scroll pane to given direction.
	 *
	 * @param callback     Callback which will be run at the end of animation.
	 * @param paneToScroll Scroll pane, which will be scrolled.
	 * @param step         Step of scroll.
	 * @param direction    To which direction.
	 */
	public ScrollTask(@NotNull final Runnable callback, @NotNull final JScrollPane paneToScroll, final int step, final Direction direction) {
		this.callback = callback;
		this.scrollBar = paneToScroll.getHorizontalScrollBar();

		final int max = direction == Direction.RIGHT ? scrollBar.getMaximum() : scrollBar.getMinimum();
		this.end = max - step;

		this.step = direction == Direction.RIGHT ? step : -step;
		this.offset = direction == Direction.RIGHT ? scrollBar.getMinimum() : scrollBar.getMaximum();
		this.scrollBar.setValue(offset);
	}

	/**
	 * Scroll scroll pane by step to maximal value.
	 */
	@Override
	public void run() {
		if ((step > 0 && offset < end) || (step < 0 && offset > end)) {
			offset += step;
			scrollBar.setValue(offset);
		} else {
			scrollBar.setValue(end + step);
			cancel();
			callback.run();
		}
	}

	/**
	 * Direction of scroll animation.
	 */
	public enum Direction {
		LEFT,
		RIGHT;
	}
}
