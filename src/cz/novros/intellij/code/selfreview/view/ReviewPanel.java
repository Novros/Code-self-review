package cz.novros.intellij.code.selfreview.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jetbrains.annotations.NotNull;

import cz.novros.intellij.code.selfreview.controller.ReviewController;
import cz.novros.intellij.code.selfreview.controller.ReviewDto;

public class ReviewPanel {

	private static final int LAYOUT_GAP = 8;

	private final JPanel pnlBase;
	private final JLabel lblStepsCount;
	private final JLabel lblActualStep;
	private final JLabel lblStepName;
	private final JPanel pnlSteps;
	private final JButton btnNextStep;
	private final JButton btnPrevStep;
	private final JButton btnRepaint;

	private final ReviewController controller;

	public ReviewPanel(@NotNull final ReviewController controller) {
		this.controller = controller;

		pnlBase = new JPanel(new BorderLayout());

		lblStepsCount = new JLabel();
		lblActualStep = new JLabel();
		lblStepName = new JLabel();
		pnlSteps = new JPanel();

		btnNextStep = new JButton("Next");
		btnPrevStep = new JButton("Previous");
		btnRepaint = new JButton("Repaint");

		designUi();
		assignListeners();
		setStepsCount(controller.getStepsCount());
		showStep(controller.getCurrentStep());
	}

	public JComponent getBase() {
		final JScrollPane pane = new JScrollPane(pnlBase, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pane.getVerticalScrollBar().setBlockIncrement(200);

		return pane;
	}

	private void setStepsCount(final int steps) {
		lblStepsCount.setText("/ " + steps);
	}

	private void showStep(@NotNull final ReviewDto dto) {
		lblActualStep.setText(Integer.toString(dto.step));
		lblStepName.setText(dto.name);

		pnlSteps.removeAll();
		for (final Pair<String, String> item : dto.content) {
			pnlSteps.add(getContentItem(item));
		}
	}

	private JPanel getContentItem(@NotNull final Pair<String, String> data) {
		final JPanel tempPanel = new JPanel(new BorderLayout());

		final JTextArea lblTitle = getWrapLabel("- " + data.getLeft());
		lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD));
		lblTitle.setOpaque(false);
		tempPanel.add(lblTitle, BorderLayout.NORTH);

		if (!data.getRight().isEmpty()) {
			tempPanel.add(getDescriptionPane(data.getRight(), lblTitle), BorderLayout.CENTER);
		}

		return tempPanel;
	}

	private JXCollapsiblePane getDescriptionPane(final String text, final JComponent lblTitle) {
		final JXCollapsiblePane pane = new JXCollapsiblePane(JXCollapsiblePane.Direction.UP, new BorderLayout());

		final JTextArea content = getWrapLabel(text);

		pane.add(content);
		pane.setAnimated(false);
		pane.setCollapsed(true);

		content.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 2) {
					pane.setCollapsed(!pane.isCollapsed());
				}
			}
		});
		lblTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				pane.setCollapsed(!pane.isCollapsed());
			}
		});


		return pane;
	}

	private JTextArea getWrapLabel(final String text) {
		final JTextArea area = new JTextArea();
		area.setBorder(new EmptyBorder(5, 5, 5, 5));
		area.setText(text);
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		return area;
	}

	private void designUi() {
		pnlBase.setLayout(new VerticalLayout(LAYOUT_GAP));
		pnlBase.setBorder(new EmptyBorder(LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP));

		JPanel tempPanel = new JPanel(new HorizontalLayout(LAYOUT_GAP, SwingConstants.CENTER));
		tempPanel.add(lblActualStep);
		tempPanel.add(lblStepsCount);
		tempPanel.add(lblStepName);
		pnlBase.add(tempPanel);
		pnlBase.add(new JSeparator(SwingConstants.HORIZONTAL));

		lblStepName.setFont(lblStepName.getFont().deriveFont(Font.BOLD, 18f));

		pnlBase.add(pnlSteps);

		tempPanel = new JPanel(new HorizontalLayout(LAYOUT_GAP, SwingConstants.CENTER));
		tempPanel.add(btnPrevStep);
		tempPanel.add(btnRepaint);
		tempPanel.add(btnNextStep);
		pnlBase.add(tempPanel);

		pnlSteps.setLayout(new VerticalLayout(LAYOUT_GAP));
	}

	private void assignListeners() {
		btnNextStep.addActionListener(e -> showStep(controller.nextStep()));
		btnPrevStep.addActionListener(e -> showStep(controller.prevStep()));
		btnRepaint.addActionListener(e -> showStep(controller.getCurrentStep()));
	}
}
