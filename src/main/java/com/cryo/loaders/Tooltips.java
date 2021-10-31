package com.cryo.loaders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.Tooltip;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Tooltips {

	private static final String TOOLTIP_FILE_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables/javelindata_itemdefinitions_tooltiplayout.json";

	private static HashMap<String, Tooltip> tooltips;

	public static void loadTooltips() {
		File file = new File(TOOLTIP_FILE_PATH);
		tooltips = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			StringBuilder builder = new StringBuilder();
			while((line = reader.readLine()) != null)
				builder.append(line);
			ArrayList<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);

			for(LinkedTreeMap<String, Object> map : list) {

				Tooltip tip = new Tooltip(map);
				tooltips.put(tip.getItemId(), tip);

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Tooltip getTooltip(String name) {
		return tooltips.get(name);
	}
}
