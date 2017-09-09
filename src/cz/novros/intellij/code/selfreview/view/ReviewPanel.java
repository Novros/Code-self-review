package cz.novros.intellij.code.selfreview.view;

import java.awt.CardLayout;

import javax.swing.*;

import com.intellij.ui.components.JBScrollPane;

import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.controller.ReviewController;
import cz.novros.intellij.code.selfreview.view.animation.Animation;
import cz.novros.intellij.code.selfreview.view.animation.ScrollTask;
import cz.novros.intellij.code.selfreview.view.utils.GuiUtils;

/**
 * Main view panel for this plugin.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class ReviewPanel implements SwitchStep {

	/**
	 * Main plugin controller.
	 */
	private final ReviewController controller;
	/**
	 * Main panel, where all views will be displayed.
	 */
	private final JPanel pnlBase;
	/**
	 * Actual displayed view for {@link Animation}.
	 */
	private StepView actualView;

	/**
	 * Create main view panel.
	 *
	 * @param controller Controller to change steps.
	 */
	public ReviewPanel(@NotNull final ReviewController controller) {
		this.controller = controller;
		this.pnlBase = new JPanel(new CardLayout(GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP));

		showActualStep();
	}

	/**
	 * Show first step or last saved step.
	 */
	private void showActualStep() {
		final StepView view = new StepView(controller.getCurrentStep(), this, controller.getStepsCount());
		actualView = view;

		pnlBase.add(view);
	}

	/**
	 * Return component, which represents this view panel.
	 *
	 * @return Component representing this main view panel.
	 */
	public JComponent getBase() {
		final JScrollPane pane = new JBScrollPane(pnlBase, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pane.getVerticalScrollBar().setBlockIncrement(200);

		return pane;
	}

	/**
	 * Switch step to next or previous step.
	 *
	 * @param next True to switch next step. False to switch previous step.
	 */
	@Override
	public void switchStep(final boolean next) {
		final StepView nextView = new StepView(
				next ? controller.nextStep() : controller.prevStep(),
				this, controller.getStepsCount());
		final ScrollTask.Direction direction = next ? ScrollTask.Direction.RIGHT : ScrollTask.Direction.LEFT;

		nextView.setSize(actualView.getSize());
		Animation.transitionAnimation(pnlBase, actualView, nextView, direction);

		actualView = nextView;
	}
}
