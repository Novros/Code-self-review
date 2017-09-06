package cz.novros.intellij.code.selfreview;

import javax.swing.*;

public class TestForm {

	public static void main(final String args[]) {
		final JFrame frame = new JFrame();

		frame.setSize(480, 800);
		frame.setLocationRelativeTo(null);

		frame.add(SelfReviewComponent.getView().getBase());
		frame.setVisible(true);
	}

}
