package cz.novros.intellij.code.selfreview.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.controller.ReviewController;
import cz.novros.intellij.code.selfreview.controller.ViewData;

public class ReviewPanel {

	private final JPanel pnlBase;
	private final JLabel lblStepsCount;
	private final JLabel lblActualStep;
	private final JLabel lblStepName;
	private final JPanel pnlSteps;
	private final JButton btnNextStep;
	private final JButton btnPrevStep;

	private final ReviewController controller;

	public ReviewPanel(@NotNull final ReviewController controller) {
		this.controller = controller;

		pnlBase = new JPanel(new BorderLayout());

		lblStepsCount = new JLabel();
		lblActualStep = new JLabel();
		lblStepName = new JLabel();
		pnlSteps = new JPanel();

		btnNextStep = new JButton("Next >");
		btnPrevStep = new JButton("< Prev");

		designUi();
		assignListeners();
		setStepsCount(controller.getStepsCount());
		showStep(controller.getCurrentStep());
	}

	public JComponent getBase() {
		final JScrollPane pane = new JBScrollPane(pnlBase, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pane.getVerticalScrollBar().setBlockIncrement(200);

		return pane;
	}

	private void setStepsCount(final int steps) {
		lblStepsCount.setText("/ " + steps);
	}

	private void showStep(@NotNull final ViewData dto) {
		lblActualStep.setText(Integer.toString(dto.step));
		lblStepName.setText(dto.name);

		pnlSteps.removeAll();
		for (final Pair<String, String> item : dto.content) {
			pnlSteps.add(new StepItem(item));
		}
	}


	private void designUi() {
		pnlBase.setLayout(new VerticalLayout(GuiUtils.LAYOUT_GAP));
		pnlBase.setBorder(new EmptyBorder(GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP));

		JPanel tempPanel = new JPanel(new HorizontalLayout(GuiUtils.LAYOUT_GAP, SwingConstants.CENTER));
		tempPanel.add(lblActualStep);
		tempPanel.add(lblStepsCount);
		tempPanel.add(lblStepName);
		pnlBase.add(tempPanel);
		pnlBase.add(new JSeparator(SwingConstants.HORIZONTAL));

		lblStepName.setFont(lblStepName.getFont().deriveFont(Font.BOLD, 18f));

		pnlBase.add(pnlSteps);

		pnlBase.add(new JSeparator(SwingConstants.HORIZONTAL));

		tempPanel = new JPanel(new GridLayout(1, 2, GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP));
		tempPanel.add(new JPanel(new FlowLayout(FlowLayout.LEFT)) {{
			add(btnPrevStep);
		}});
		tempPanel.add(new JPanel(new FlowLayout(FlowLayout.RIGHT)) {{
			add(btnNextStep);
		}});
		pnlBase.add(tempPanel);

		pnlSteps.setLayout(new VerticalLayout(GuiUtils.LAYOUT_GAP));
	}

	private void assignListeners() {
		btnNextStep.addActionListener(e -> showStep(controller.nextStep()));
		btnPrevStep.addActionListener(e -> showStep(controller.prevStep()));
	}
}
