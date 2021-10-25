package com.cryo;

import com.cryo.builders.Builder;
import com.cryo.builders.ItemDefinitionsBuilder;
import com.cryo.builders.RecipeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WikiBuilder {

	private static String LOCALIZATION_BASE_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/localization/en-us/";
	public static HashMap<String, String> localizationStrings;

	public static void buildWiki() {

		loadLocalizationFiles();
		ItemDefinitionsBuilder.loadItemDefinitions();
		ItemDefinitionsBuilder.loadCSSTemplate();
		RecipeBuilder.loadRecipes();

		List<Builder> builders = new ArrayList<>() {{
			add(new ItemDefinitionsBuilder());
		}};

		builders.forEach(Builder::build);

	}

	public static void loadLocalizationFiles() {
		//Eventually load all of them so we can have pages in all languages
		//Just english for now, and only item name/definitions

		localizationStrings = new HashMap<>();

		File[] files = new File(LOCALIZATION_BASE_PATH).listFiles();
		if(files == null) return;

		for(File file : files) {

			try {

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

				// parse XML file
				DocumentBuilder db = dbf.newDocumentBuilder();

				Document doc = db.parse(file);

				doc.getDocumentElement().normalize();

				NodeList list = doc.getElementsByTagName("string");

				for(int i = 0; i < list.getLength(); i++) {

					Node node = list.item(i);

					if(node.getNodeType() == Node.ELEMENT_NODE) {

						Element element = (Element) node;

						String key = element.getAttribute("key");
						localizationStrings.put(key, node.getTextContent());
						if(key.equals("1hSwordT2_FTUE_Description"))
							System.out.println("Found it: "+node.getTextContent());
					}

				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		}
	}

}
