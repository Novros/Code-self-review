package cz.novros.intellij.code.selfreview.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.intellij.ui.JBColor;

import org.apache.commons.lang3.tuple.Pair;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cz.novros.intellij.code.selfreview.model.State;
import cz.novros.intellij.code.selfreview.view.utils.GuiUtils;

/**
 * Form for one step item in {@link State#getContent()}.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class StepItem extends JPanel {

	/**
	 * Create step item by given data.
	 *
	 * @param data Pair of simple text and description text.
	 */
	public StepItem(@NotNull final Pair<String, String> data) {
		setLayout(new BorderLayout(GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP));

		final JComponent header = createHeader(data);
		add(header, BorderLayout.NORTH);

		if (!data.getRight().isEmpty()) {
			add(getDescriptionPane(data.getRight(), header), BorderLayout.CENTER);
		}
	}

	/**
	 * Create header panel for step item.
	 *
	 * @param data Text which will be visible in header.
	 *
	 * @return Panel with all necessary components.
	 */
	private static JComponent createHeader(@NotNull final Pair<String, String> data) {
		final JPanel panel = new JPanel(new BorderLayout(GuiUtils.LAYOUT_GAP, GuiUtils.LAYOUT_GAP));
		panel.setOpaque(false);

		final JTextArea lblTitle = getSimpleWrapLabel(data.getLeft());
		lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD));
		lblTitle.setOpaque(false);

		panel.add(lblTitle);

		if (!data.getRight().isEmpty()) {
			panel.add(new JLabel(
					GuiUtils.getIconFromResource(JBColor.isBright() ? "note.png" : "note-white.png")),
					BorderLayout.EAST);
		}

		return panel;
	}

	/**
	 * Return description pane, which is collapsible.
	 *
	 * @param text            Text which will be content of description pane.
	 * @param headerComponent Component to which will be assigned opening of this pane.
	 *
	 * @return Instance of description pane.
	 */
	private static JXCollapsiblePane getDescriptionPane(@Nullable final String text, @NotNull final JComponent headerComponent) {
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

		final MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				changeStateOfPane(pane);
			}
		};

		headerComponent.addMouseListener(adapter);
		Arrays.stream(headerComponent.getComponents())
				.forEach(component -> component.addMouseListener(adapter));

		return pane;
	}

	/**
	 * Change stat of collapsible pane.
	 *
	 * @param pane Collapsible pane of which will be state changed.
	 */
	private static void changeStateOfPane(@NotNull final JXCollapsiblePane pane) {
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
	private static JTextArea getSimpleWrapLabel(@Nullable final String text) {
		final JTextArea area = new JTextArea();
		area.setBorder(new EmptyBorder(5, 5, 5, 5));
		area.setText(text);
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);

		return area;
	}
}
