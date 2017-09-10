package cz.novros.intellij.code.selfreview.model.settings;

import com.intellij.util.xmlb.annotations.Tag;

/**
 * Entity which represents data to save..
 */
public class SaveState {

	/**
	 * Last opened state.
	 */
	@Tag("step")
	public int step = 1;
}
