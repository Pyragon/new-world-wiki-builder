package com.cryo;

import com.cryo.decompilers.DDSConverter;
import com.cryo.decompilers.DatasheetDecompiler;
import com.cryo.decompilers.Decompiler;
import com.cryo.decompilers.XMLDecompiler;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.tongfei.progressbar.ProgressBar;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class NewWorldWikiBuilder {

	public static String UNPACKED_BASE_PATH = "D:\\workspace\\github\\NewWorldUnpacker\\unpacked_out\\";
	public static String DECOMPILED_BASE_PATH = "D:\\workspace\\github\\NewWorldUnpacker\\decompiled\\";

	public static Gson gson;
	private static List<Decompiler> decompilers;

	public static void main(String[] args) {
		try {

			buildGson();

			File unpackedBase = new File(UNPACKED_BASE_PATH);
			if (!unpackedBase.exists()) return;
			File[] files = unpackedBase.listFiles();
			if (files == null) return;

			decompilers = new ArrayList<>() {{
//				add(new com.cryo.decompilers.LuaDecompiler("Decompiling lua scripts..."));
//				add(new DatasheetDecompiler("Decompiling datasheets..."));
//				add(new XMLDecompiler("Moving XML files..."));
				add(new DDSConverter("Converting DDS images to PNG... (this will take a long time)"));
			}};

			for (Decompiler decompiler : decompilers) {
				Path[] paths = Files.find(Paths.get(UNPACKED_BASE_PATH), Integer.MAX_VALUE, decompiler.getFilter()).toArray(Path[]::new);
				ProgressBar bar = new ProgressBar(decompiler.getProgressBarName(), paths.length);
				decompiler.setProgressBar(bar);
				bar.start();
				decompiler.decompile(paths);
				bar.stop();
			}

//			WikiBuilder.buildWiki();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Gson getGson() {
		return gson;
	}

	public static void buildGson() {
		gson = new GsonBuilder()
				.serializeNulls()
				.setVersion(1.0)
				.disableHtmlEscaping()
				.setPrettyPrinting()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.create();
	}


}
