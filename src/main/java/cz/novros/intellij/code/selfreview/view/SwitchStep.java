package cz.novros.intellij.code.selfreview.view;

/**
 * Interface to define to switch step.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public interface SwitchStep {

	/**
	 * Switch step to next or previous step.
	 *
	 * @param next True to switch next step. False to switch previous step.
	 */
	void switchStep(final boolean next);
}
