package com.cryo.decompilers;

import me.tongfei.progressbar.ProgressBar;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Decompiler {

	protected String progressBarName;
	protected ProgressBar bar;

	public Decompiler(String progressBarName) {
		this.progressBarName = progressBarName;
	}

	public abstract BiPredicate<Path, BasicFileAttributes> getFilter();

	public abstract void decompile(Path[] paths);

	public void setProgressBar(ProgressBar bar) {
		this.bar = bar;
	}

	public String getProgressBarName() {
		return progressBarName;
	}
}
