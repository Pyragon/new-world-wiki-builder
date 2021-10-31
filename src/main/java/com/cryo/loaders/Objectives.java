package com.cryo.loaders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.Objective;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Objectives {

	private static final String OBJECTIVES_FILE_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables";

	private static HashMap<String, Objective> objectives;

	public static void loadObjectives(File[] files) {
		for(File file : files) {
			if(file.isDirectory()) {
				loadObjectives(file.listFiles());
				continue;
			}
			if(!file.getName().matches("javelindata_(\\d+_)?objectives.json")) continue;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				StringBuilder builder = new StringBuilder();
				while((line = reader.readLine()) != null)
					builder.append(line);
				ArrayList<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);

				for(LinkedTreeMap<String, Object> map : list) {

					Objective task = new Objective(file.getName(), map);
					objectives.put(task.getObjectiveID(), task);

				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadObjectives() {
		File file = new File(OBJECTIVES_FILE_PATH);
		objectives = new HashMap<>();
		loadObjectives(file.listFiles());
	}

	public static Collection<Objective> getObjectives() {
		return objectives.values();
	}

	public static Objective getTask(String name) {
		return objectives.get(name);
	}
}
