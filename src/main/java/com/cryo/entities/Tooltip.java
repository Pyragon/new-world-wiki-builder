package com.cryo.entities;

import com.cryo.WikiBuilder;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;

@Data
public class Tooltip {

	private final String canBeCraftedTextsLarge;
	private final String resourcesIcons;
	private final String commonUsesItemsLarge;
	private final String refineryInputAmount;
	private final String refinedAtLabelText;
	private final String refinedAtIcon;
	private final String canBeCraftedIconsLarge;
	private final String usedAtTexts;
	private final String commonUsesIconsLarge;
	private final String resourcesAmounts;
	private final String resourcesTexts;
	private final String commonUsesTextsLarge;
	private final String locNote;
	private final String commonUsesTextsSmall;
	private final String refineryInputText;
	private final String refineryInputIcon;
	private final String commonUsesIconsSmall;
	private final String refineryOutputAmount;
	private final String derivedFromText;
	private final String specialInstructions;
	private final String derivedFromIcon;
	private final String refineryOutputIcon;
	private final String canBeCraftedTextsSmall;
	private final String commonUsesItemsSmall;
	private final String itemId;
	private final String resourcesHeaderIcon;
	private final String refineryOutputText;
	private final String canBeCraftedIconsSmall;
	private final String usedAtIcons;
	private final String mainHeaderIcon;
	private final String refinedAtText;
	private final String commonUsesLabelText;
	private final String resourcesLabelText;

	public Tooltip(LinkedTreeMap<String, Object> map) {
		canBeCraftedTextsLarge = (String) map.get("CanBeCraftedTextsLarge");
		resourcesIcons = (String) map.get("ResourcesIcons");
		commonUsesItemsLarge = (String) map.get("CommonUsesItemsLarge");
		refineryInputAmount = (String) map.get("RefineryInputAmount");
		refinedAtLabelText = (String) map.get("RefinedAtLabelText");
		refinedAtIcon = (String) map.get("RefinedAtIcon");
		canBeCraftedIconsLarge = (String) map.get("CanBeCraftedIconsLarge");
		usedAtTexts = (String) map.get("UsedAtTexts");
		commonUsesIconsLarge = (String) map.get("CommonUsesItemsLarge");
		resourcesAmounts = (String) map.get("ResourcesAmounts");
		resourcesTexts = (String) map.get("ResourcesTexts");
		commonUsesTextsLarge = (String) map.get("CommonUsesTextsLarge");
		locNote = (String) map.get("Loc Note");
		commonUsesTextsSmall = (String) map.get("CommonUsesTextsSmall");
		refineryInputText = (String) map.get("RefineryInputText");
		refineryInputIcon = (String) map.get("RefineryInputIcon");
		commonUsesIconsSmall = (String) map.get("CommonUsesIconsSmall");
		refineryOutputAmount = (String) map.get("RefineryOutputAmount");
		derivedFromText = (String) map.get("DerivedFromText");
		specialInstructions = (String) map.get("SpecialInstructions");
		derivedFromIcon = (String) map.get("DerivedFromIcon");
		refineryOutputIcon = (String) map.get("RefineryOutputIcon");
		canBeCraftedTextsSmall = (String) map.get("CanBeCraftedTextsSmall");
		commonUsesItemsSmall = (String) map.get("CommonUsesItemsSmall");
		itemId = (String) map.get("ItemID");
		resourcesHeaderIcon = (String) map.get("ResourcesHeaderIcon");
		refineryOutputText = (String) map.get("RefineryOutputText");
		canBeCraftedIconsSmall = (String) map.get("CanBeCraftedIconsSmall");
		usedAtIcons = (String) map.get("UsedAtIcons");
		mainHeaderIcon = (String) map.get("MainHeaderIcon");
		refinedAtText = (String) map.get("RefinedAtText");
		commonUsesLabelText = (String) map.get("CommonUsesLabelText");
		resourcesLabelText = (String) map.get("ResourcesLabelText");
	}

	public String getLocalDerivedFromText() {
		return WikiBuilder.localizationStrings.get(derivedFromText.replace("@", ""));
	}
}
