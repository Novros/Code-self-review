package cz.novros.intellij.code.selfreview.controller;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Data for view of this plugin.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
public class ViewData {

	/**
	 * Actual step.
	 */
	public int step;

	/**
	 * Name of actual step.
	 */
	public String name;

	/**
	 * Content of actual step in form of <Short-description, description>.
	 */
	public List<Pair<String, String>> content;
}
