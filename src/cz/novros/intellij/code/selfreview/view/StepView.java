package cz.novros.intellij.code.selfreview.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

import com.intellij.ui.JBColor;

import org.apache.commons.lang3.tuple.Pair;
import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.controller.ViewData;
import cz.novros.intellij.code.selfreview.view.utils.GuiUtils;

/**
 * This class represents view on {@link cz.novros.intellij.code.selfreview.controller.ViewData}.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class StepView extends JPanel {

	/**
	 * Shows all steps count.
	 */
	private final JLabel lblStepsCount;
	/**
	 * Shows actual step count.
	 */
	private final JLabel lblActualStep;
	/**
	 * Show step name.
	 */
	private final JLabel lblStepName;
	/**
	 * Panel for showing step's items.
	 */
	private final JPanel pnlContent;
	/**
	 * Button for switch to next step.
	 */
	private final JButton btnNextStep;
	/**
	 * Button for switch to previous step.
	 */
	private final JButton btnPrevStep;

	/**
	 * Create step view for given data.
	 *
	 * @param data       Data which will be showed in this view.
	 * @param stepsCount Count of all steps.
	 */
	public StepView(@NotNull final ViewData data, final SwitchStep switchStep, final int stepsCount) {
		this.setLayout(new VerticalLayout(GuiUtils.LAYOUT_GAP));

		lblStepsCount = new JLabel();
		lblActualStep = new JLabel();
		lblStepName = new JLabel();
		pnlContent = new JPanel(new VerticalLayout());

		btnNextStep = new JButton("Next >");
		btnPrevStep = new JButton("< Prev");

		designUi();
		assignListeners(switchStep);
		setStepsCount(stepsCount);
		showStep(data);
	}

	/**
	 * Initialize this view with given {@link ViewData}.
	 *
	 * @param data Data which will be showed in this view.
	 */
	private void showStep(@NotNull final ViewData data) {
		lblActualStep.setText(Integer.toString(data.step));
		lblStepName.setText(data.name);

		data.content.forEach(item -> {
			if (pnlContent.getComponents().length > 0) {
				pnlContent.add(createStepItemSeparator());
			}

			pnlContent.add(new StepItem(item));
		});
	}

	/**
	 * Create separator to delimit step items.
	 *
	 * @return Simple separator.
	 */
	private JComponent createStepItemSeparator() {
		final JSeparator separator = new JSeparator();

		separator.setForeground(JBColor.isBright() ? JBColor.LIGHT_GRAY : new Color(85, 85, 85));
		separator.setSize(new Dimension(separator.getWidth() - 20, separator.getHeight()));

		return separator;
	}

	/**
	 * Set text to label showing steps count.
	 *
	 * @param steps Number of steps.
	 */
	private void setStepsCount(final int steps) {
		lblStepsCount.setText("/ " + steps);
	}

	/**
	 * Assign listeners to buttons.
	 *
	 * @param switchStep Interface to switch step.
	 */
	private void assignListeners(final SwitchStep switchStep) {
		btnNextStep.addActionListener(e -> switchStep.switchStep(true));
		btnPrevStep.addActionListener(e -> switchStep.switchStep(false));
	}

	/**
	 * Design panel.
	 */
	private void designUi() {
		pnlContent.setLayout(new VerticalLayout(GuiUtils.LAYOUT_GAP));

		add(createHeader());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(pnlContent);
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(createFooter());
	}

	/**
	 * Create header in design: "{step} / {steps} {StepName}
	 *
	 * @return Panel with header labels.
	 */
	private JPanel createHeader() {
		final JPanel header = new JPanel(new HorizontalLayout(GuiUtils.LAYOUT_GAP));

		lblStepName.setFont(lblStepName.getFont().deriveFont(Font.BOLD, 18f));

//		header.add(lblActualStep);
//		header.add(lblStepsCount);
		header.add(lblStepName);

		return header;
	}

	/**
	 * Create footer in design: [< Prev] {spacer} [Next >].
	 *
	 * @return Panel with buttons in footer.
	 */
	private JPanel createFooter() {
		final JPanel footer = new JPanel(new GridLayout(1, 2, GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP));

		footer.add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {{
			add(btnPrevStep);
		}});
		footer.add(new JPanel(new FlowLayout(FlowLayout.RIGHT)) {{
			add(btnNextStep);
		}});

		return footer;
	}
}
