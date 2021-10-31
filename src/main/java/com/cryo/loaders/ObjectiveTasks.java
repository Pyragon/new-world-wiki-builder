package com.cryo.loaders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.Objective;
import com.cryo.entities.ObjectiveTask;
import com.cryo.entities.Tooltip;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ObjectiveTasks {

	private static final String TASKS_FILE_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables";

	private static HashMap<String, ObjectiveTask> tasks;

	public static void loadObjectives(File[] files) {
		for(File file : files) {
			if(file.isDirectory()) {
				loadObjectives(file.listFiles());
				continue;
			}
			if(!file.getName().matches("javelindata_(\\d+_)?objectivetasks.json")) continue;
				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String line;
					StringBuilder builder = new StringBuilder();
					while((line = reader.readLine()) != null)
						builder.append(line);
					ArrayList<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);

					for(LinkedTreeMap<String, Object> map : list) {

						ObjectiveTask task = new ObjectiveTask(map);
						tasks.put(task.getTaskID(), task);

					}
				} catch(Exception e) {
					e.printStackTrace();
				}
		}
	}

	public static void loadObjectiveTasks() {
		File file = new File(TASKS_FILE_PATH);
		tasks = new HashMap<>();
		loadObjectives(file.listFiles());
	}

	public static ObjectiveTask getTask(String name) {
		return tasks.get(name);
	}

	public static ObjectiveTask getTaskForItem(String itemId) {
		for(ObjectiveTask task : tasks.values()) {
			if(task.getItemName().equals(itemId)) return task;
		}
		return null;
	}
}
