package com.cryo.loaders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.GameEvent;
import com.cryo.entities.Tooltip;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class GameEvents {

	private static final String GAME_EVENTS_FILE_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables/";

	private static HashMap<String, GameEvent> events;

	public static void loadEvents(File[] files) {
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					loadEvents(file.listFiles());
					continue;
				}
				if(!file.getName().matches("javelindata_(\\d+_)?gameevents.json")) continue;
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				StringBuilder builder = new StringBuilder();
				while ((line = reader.readLine()) != null) builder.append(line);
				ArrayList<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);

				for (LinkedTreeMap<String, Object> map : list) {

					GameEvent event = new GameEvent(map);
					events.put(event.getEventID(), event);

				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadEvents() {
		File file = new File(GAME_EVENTS_FILE_PATH);
		events = new HashMap<>();
		try {
			loadEvents(file.listFiles());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static GameEvent getEvent(String name) {
		return events.get(name);
	}
}
