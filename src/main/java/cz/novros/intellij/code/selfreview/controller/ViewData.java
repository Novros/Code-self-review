package cz.novros.intellij.code.selfreview.controller;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Data;

/**
 * Data for view of this plugin.
 *
 * @author Rostislav Novák
 * @version 1.0
 * @since 1.0
 */
@Data
public class ViewData {

	/**
	 * Actual step.
	 */
	private int step;

	/**
	 * Name of actual step.
	 */
	private String name;

	/**
	 * Content of actual step in form of <Short-description, description>.
	 */
	private List<Pair<String, String>> content;
}
