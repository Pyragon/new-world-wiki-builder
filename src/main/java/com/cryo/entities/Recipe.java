package com.cryo.entities;

import com.cryo.builders.ItemDefinitionsBuilder;
import com.cryo.loaders.GameEvents;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Recipe {

	private final String recipeId;

	private final double bonusItemChance;
	private final String achievementId;
	private final int requiredLevel;
	private final String additionalFilterText;
	private final String bonusItemChanceIncrease;
	private final String bonusItemChanceDecrease;
	private final String recipeNameOverride;
	private final String HWMProgressionID;
	private final double craftingTime;
	private final double gemSlotCost;
	private final double useCraftingTax; //boolean?
	private final String gameEventID;
	private final double cooldownQuantity;
	private final String itemId; //why would this be empty?
	private final double craftingFee;
	private final String recipeTags;
	private final String tradeSkill;
	private final String craftingGroup;

	private final String[] types;
	private final int[] quantities;
	private final String[] ingredients;

	private final double perkCost;
	private final int[] gearScoreBonus;
	private final int[] gearScoreReduction;
	private final String[] stationTypes;
	private final double cooldownSeconds;
	private final double baseTier;
	private final boolean craftAll;
	private final boolean isTemporary;

	private final String[] proceduralTierIDs;
	private final double attributeCost;
	private final boolean bKnownByDefault;
	private final String craftingCategory;
	private final String displayIngredients;
	private final boolean isProcedural;
	private final boolean gameEventValidation;
	private final int baseGearScore;
	private final int outputQuantity;
	private final boolean isRefining;
	private final String requiredAchievementID;
	private final boolean bListedByDefault;


	public Recipe(LinkedTreeMap<String, Object> map) {
		bonusItemChance = (double) map.get("BonusItemChance");
		achievementId = (String) map.get("AchievementID");
		requiredLevel = ((Double) map.get("RecipeLevel")).intValue();
		additionalFilterText = (String) map.get("AdditionalFilterText");
		bonusItemChanceDecrease = (String) map.get("BonusItemChanceDecrease");
		recipeNameOverride = (String) map.get("RecipeNameOverride");
		HWMProgressionID = (String) map.get("HWMProgressionID");
		craftingTime = (double) map.get("CraftingTime");
		gemSlotCost = (double) map.get("GemSlotCost");
		recipeId = (String) map.get("RecipeID"); //item id is empty sometimes, so maybe get the item from whichever has this recipe id?
		useCraftingTax = (double) map.get("UseCraftingTax"); //name sounds like a boolean, value is 1.0 for what the ones I'm looking at
		gameEventID = (String) map.get("GameEventID");
		cooldownQuantity = (double) map.get("CooldownQuantity");
		itemId = (String) map.get("ItemID");
		craftingFee = (double) map.get("CraftingFee"); //can be int?
		recipeTags = (String) map.get("RecipeTags");
		bonusItemChanceIncrease = (String) map.get("BonusItemChanceIncrease");
		tradeSkill = (String) map.get("Tradeskill");
		craftingGroup = (String) map.get("CraftingGroup");
		types = new String[7];
		quantities = new int[7];
		ingredients = new String[7];
		for(int i = 1; i <= 7; i++) {
			types[i-1] = (String) map.get("Type"+i);
			quantities[i-1] = ((Double) map.get("Qty"+i)).intValue();
			ingredients[i-1] = (String) map.get("Ingredient"+i);
		}
		perkCost = (double) map.get("PerkCost");
		String bonus = (String) map.get("GearScoreBonus");
		if(!bonus.equals(""))
			gearScoreBonus = Arrays.stream(bonus.split(",")).mapToInt(Integer::parseInt).toArray();
		else
			gearScoreBonus = null;
		stationTypes = new String[4];
		for(int i = 1; i <= 4; i++) //heh?
			stationTypes[i-1] = (String) map.get("StationType"+i);
		cooldownSeconds = (double) map.get("CooldownSeconds");
		baseTier = (double) map.get("BaseTier");
		craftAll = (boolean) map.get("CraftAll");
		isTemporary = (boolean) map.get("IsTemporary");
		proceduralTierIDs = new String[5];
		for(int i = 1; i <= 5; i++)
			proceduralTierIDs[i-1] = (String) map.get("ProceduralTierID"+i);
		bKnownByDefault = (boolean) map.get("bKnownByDefault");
		craftingCategory = (String) map.get("CraftingCategory");
		displayIngredients = (String) map.get("DisplayIngredients");
		isProcedural = (boolean) map.get("IsProcedural");
		gameEventValidation = (boolean) map.get("Game Event Validation");
		baseGearScore = ((Double) map.get("BaseGearScore")).intValue();
		bonus = (String) map.get("GearScoreReduction");
		if(!bonus.equals(""))
			gearScoreReduction = Arrays.stream(bonus.split(",")).mapToInt(Integer::parseInt).toArray();
		else
			gearScoreReduction = null;
		outputQuantity = ((Double) map.get("OutputQty")).intValue();
		isRefining = (boolean) map.get("IsRefining");
		requiredAchievementID = (String) map.get("RequiredAchievementID");
		bListedByDefault = (boolean) map.get("bListedByDefault");
		attributeCost = (double) map.get("AttributeCost");
	}

	public ItemDefinitions getItemDefinitions() {
		for(ItemDefinitions defs : ItemDefinitionsBuilder.getItemDefinitions().values()) {
			if(defs.getItemID().equals(itemId)) return defs;
		}
		return null;
	}

	public ItemDefinitions getRecipeItemDefinitions() {
		for(ItemDefinitions defs : ItemDefinitionsBuilder.getItemDefinitions().values()) {
			if(defs.getItemID().equals(recipeId)) return defs;
		}
		return null;
	}

	public String getStationImage(int index) {
		if(index < 0 || index >= stationTypes.length) return "";
		String type = stationTypes[index];
		if(type == null || type.equals("")) return "";
		return "https://new-world.wiki/worldmap/worldmap_"+type.substring(0, type.length()-1)+".png";
	}

	public String getStationText(int index) {
		if(index < 0 || index >= stationTypes.length) return "";
		String type = stationTypes[index];
		if(type == null || type.equals("")) return "";
		return type.substring(0, 1).toUpperCase() + type.substring(1, type.length() - 1) + " Station Tier " + type.substring(type.length() - 1);
	}

	public String getTradeSkillImage() {
		return "https://new-world.wiki/tradeskills/"+tradeSkill.toLowerCase()+".png";
	}

	public String getTradeSkillText() {
		return tradeSkill+" Skill Lv. "+requiredLevel;
	}

	public GameEvent getGameEvent() {
		return GameEvents.getEvent(gameEventID);
	}

	public int getXp() {
		GameEvent event = getGameEvent();
		if(event == null) return 0;
		double progressionXp = event.getCategoricalProgressionReward();
		int total = Arrays.stream(quantities).sum();
		return (int) progressionXp * total;
	}

	public List<RecipeItem> getRecipeItems() {
		List<RecipeItem> items = new ArrayList<>();
		for(int i = 0; i < ingredients.length; i++) {
			String ingredient = ingredients[i];
			if(ingredient == null || ingredient.equals(""))
				continue;
			ItemDefinitions defs = ItemDefinitionsBuilder.getItemDefinitionsById().get(ingredient);
			if(defs == null) continue;
			RecipeItem item = new RecipeItem(defs, types[i], quantities[i]);
			items.add(item);
		}
		return items;
	}
}
