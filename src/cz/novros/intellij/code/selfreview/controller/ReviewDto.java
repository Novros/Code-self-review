package cz.novros.intellij.code.selfreview.controller;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class ReviewDto {
	public int step;
	public String name;
	public List<Pair<String, String>> content;
}
