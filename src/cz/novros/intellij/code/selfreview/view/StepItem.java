package cz.novros.intellij.code.selfreview.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.tuple.Pair;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.model.State;

/**
 * Form for one step item in {@link State#getContent()}.
 */
public class StepItem extends JPanel {

	/**
	 * Create step item by given data.
	 *
	 * @param data Pair of simple text and description text.
	 */
	public StepItem(@NotNull final Pair<String, String> data) {
		setLayout(new BorderLayout(GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP));

		final JTextArea lblTitle = getSimpleWrapLabel("- " + data.getLeft());
		lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD));
		lblTitle.setOpaque(false);

		add(lblTitle, BorderLayout.NORTH);

		if (!data.getRight().isEmpty()) {
			add(getDescriptionPane(data.getRight(), lblTitle), BorderLayout.CENTER);
		}
	}

	/**
	 * Return description pane, which is collapsible.
	 *
	 * @param text     Text which will be content of description pane.
	 * @param lblTitle Component to which will be assigned opening of this pane.
	 *
	 * @return Instance of description pane.
	 */
	private JXCollapsiblePane getDescriptionPane(@Nullable final String text, @NotNull final JComponent lblTitle) {
		final JXCollapsiblePane pane = new JXCollapsiblePane(JXCollapsiblePane.Direction.UP, new BorderLayout());

		final JTextArea content = getSimpleWrapLabel(text);

		pane.add(content);
		changeStateOfPane(pane);
		pane.setVisible(false); // Hack for not see collaps animation at begin.

		content.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 2) {
					changeStateOfPane(pane);
				}
			}
		});
		lblTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				changeStateOfPane(pane);
			}
		});

		return pane;
	}
	
	private void changeStateOfPane(@NotNull final JXCollapsiblePane pane) {
		if (!pane.isVisible()) {
			pane.setVisible(true);
		}
		
		pane.setCollapsed(!pane.isCollapsed());
	}

	/**
	 * Create label which wraps text.
	 *
	 * @param text Text which will be set as content.
	 *
	 * @return Instance of wrap label.
	 */
	private JTextArea getSimpleWrapLabel(@Nullable final String text) {
		final JTextArea area = new JTextArea();
		area.setBorder(new EmptyBorder(5, 5, 5, 5));
		area.setText(text);
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		return area;
	}
}
