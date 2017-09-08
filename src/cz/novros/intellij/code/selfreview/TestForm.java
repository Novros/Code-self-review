package cz.novros.intellij.code.selfreview;

import javax.swing.*;

import cz.novros.intellij.code.selfreview.controller.SaveStateService;

public class TestForm {

	public static void main(final String args[]) {
		final JFrame frame = new JFrame();

		frame.setSize(480, 800);
		frame.setLocationRelativeTo(null);

		frame.add(new SelfReviewComponent().getView(new SaveStateService()).getBase());
		frame.setVisible(true);
	}

}
