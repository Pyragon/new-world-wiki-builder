package com.cryo.entities;

import com.cryo.WikiBuilder;
import com.cryo.builders.ItemDefinitionsBuilder;
import com.cryo.builders.RecipeBuilder;
import com.cryo.loaders.ObjectiveTasks;
import com.cryo.utils.AssetUtils;
import com.cryo.loaders.ItemStatsList;
import com.cryo.loaders.Tooltips;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Locale;

@Data
public class ItemDefinitions {

	private final String repairTypes;
	private final String itemTypeDisplayName;
	private final double gearScoreOverride;
	private final String uiItemClass;
	private final String iconPath;
	private final int maxGearScore;
	private final String itemType;
	private final String iconCaptureGroup;
	private final boolean nonRemovable;
	private final boolean rDyeSlotDisabled;
	private final double ingredientGearScoreMaxBonus;
	private final String name;
	private final int minGearScore;
	private final String tradingCategory;
	private final double repairDustModifier;
	private final boolean isMissionItem;
	private final String extraBonusItemChance;
	private final double containerLevel;
	private final String warboardDepositStat;
	private final boolean isRepairable;
	private final String audioDestroyed; //TODO - get audio
	private final String itemStatsRef;
	private final String armourAppearanceF;
	private final boolean useMagicAffix;
	private final String soundTableID;
	private final boolean ignoreNameChanges;
	private final String tradingGroup;
	private final int tier;
	private final String repairRecipe;
	private final String audioPlace;
	private final String mannequinTag;
	private final String tradingFamily;
	private final String armorAppearanceM;
	private final String[] perkBuckets;
	private final boolean confirmBeforeUse;
	private final int durability;
	private final String craftingRecipe;
	private final String itemID;
	private final boolean salvageResources;
	private final double weight;
	private final boolean useMaterialAffix;
	private final String weaponAppearanceOverride;
	private final int maxStackSize;
	private final boolean canHavePerks;
	private final String ingredientCategories;
	private final String[] itemClasses;
	private final boolean bindOnEquip;
	private final String description;
	private final double containerGS;
	private final int requiredLevel;
	private final String repairGameEventID;
	private final String salvageGameEventID;
	private final double ingredientBonusPrimary;
	private final boolean bDyeSlotDisabled;
	private final boolean gDyeSlotDisabled;
	private final String audioPickup;
	private final boolean destroyOnBreak;
	private final double ingredientGearScoreBaseBonus;
	private final double durabilityDamageOnDeath;
	private final String audioCreated;
	private final boolean hideInLootTicker;
	private final boolean isSalvageable;
	private final String notes;
	private final boolean useTypeAffix;
	private final boolean consumeOnUse;
	private final String warboardGatherStat;
	private final String salvageAchievement;
	private final int forceRarity;
	private final boolean isUniqueItem;
	private final boolean aDyeSlotDisabled;
	private final String confirmDestroy;
	private final boolean bindOnPickup;
	private final String[] perks;
	private final String acquisitionNotificationId;
	private final double ingredientBonusSecondary;
	private final String audioUse;
	private final String grantsHWMBump;
	private final String housingTags;
	private final String prefabPath;
	private final boolean canReplaceGem;
	private final double deathDropPercentage;

	public ItemDefinitions(LinkedTreeMap<String, Object> map) {
		repairTypes = (String) map.get("RepairTypes");
		itemTypeDisplayName = (String) map.get("ItemTypeDisplayName");
		gearScoreOverride = (double) map.get("GearScoreOverride");
		uiItemClass = (String) map.get("UiItemClass");
		iconPath = (String) map.get("IconPath");
		maxGearScore = ((Double) map.get("MaxGearScore")).intValue();
		itemType = (String) map.get("ItemType");
		iconCaptureGroup = (String) map.get("IconCaptureGroup");
		Object o = map.get("Nonremovable");
		nonRemovable = o instanceof Boolean && (boolean) o;
		o = map.get("RDyeSlotDisabled");
		rDyeSlotDisabled = o instanceof Boolean && (boolean) o;
		o = map.get("IngredientGearScoreMaxBonus");
		ingredientGearScoreMaxBonus = o instanceof Double ? (double) o : 0.0;
		name = (String) map.get("Name");
		minGearScore = ((Double) map.get("MinGearScore")).intValue();
		tradingCategory = (String) map.get("TradingCategory");
		repairDustModifier = (double) map.get("RepairDustModifier");
		o = map.get("IsMissionItem");
		isMissionItem = o instanceof Boolean && (boolean) o;
		extraBonusItemChance = (String) map.get("ExtraBonusItemChance");
		o = map.get("ContainerLevel");
		containerLevel = o instanceof String ? 0.0 : (double) o;
		warboardDepositStat = (String) map.get("WarboardDepositStat");
		isRepairable = (boolean) map.get("IsRepairable");
		audioDestroyed = (String) map.get("AudioDestroyed");
		itemStatsRef = (String) map.get("ItemStatsRef");
		armourAppearanceF = (String) map.get("ArmourAppearanceF");
		useMagicAffix = (boolean) map.get("UseMagicAffix");
		soundTableID = (String) map.get("SoundTableID");
		o = map.get("IgnoreNameChanges");
		ignoreNameChanges = o instanceof Boolean && (boolean) o;
		tradingGroup = (String) map.get("TradingGroup");
		tier = ((Double) map.get("Tier")).intValue();
		repairRecipe = (String) map.get("RepairRecipe");
		audioPlace = (String) map.get("AudioPlace");
		mannequinTag = (String) map.get("MannequinTag");
		tradingFamily = (String) map.get("TradingFamily");
		armorAppearanceM = (String) map.get("ArmorAppearanceM");
		perks = new String[5];
		perkBuckets = new String[5];
		for(int i = 1; i <= 5; i++) {
			perks[i-1] = (String) map.get("Perk"+i);
			perkBuckets[i-1] = (String) map.get("PerkBucket"+i);
		}
		confirmBeforeUse = (boolean) map.get("ConfirmBeforeUse");
		durability = ((Double) map.get("Durability")).intValue();
		craftingRecipe = (String) map.get("CraftingRecipe");
		itemID = (String) map.get("ItemID");
		o = map.get("SavageResources");
		salvageResources = o instanceof Boolean && (boolean) o;
		weight = ((double) map.get("Weight")) / 10;
		useMaterialAffix = (boolean) map.get("UseMaterialAffix");
		weaponAppearanceOverride = (String) map.get("WeaponAppearanceOverride");
		maxStackSize = ((Double) map.get("MaxStackSize")).intValue();
		canHavePerks = (boolean) map.get("CanHavePerks");
		ingredientCategories = (String) map.get("IngredientCategories");
		itemClasses = ((String) map.get("ItemClass")).split("\\+");
		description = (String) map.get("Description");
		o = map.get("ContainerGS");
		containerGS = o instanceof Double ? (double) o : 0.0;
		o = map.get("RequiredLevel");
		requiredLevel = o instanceof Double ? ((Double) o).intValue() : 0;
		repairGameEventID = (String) map.get("RepairGameEventID");
		salvageGameEventID = (String) map.get("SalvageGameEventID");
		o = map.get("IngredientBonusPrimary");
		ingredientBonusPrimary = o instanceof Double ? (double) o : 0.0;
		bDyeSlotDisabled = (boolean) map.get("BDyeSlotDisabled");
		gDyeSlotDisabled = (boolean) map.get("GDyeSlotDisabled");
		audioPickup = (String) map.get("AudioPickup");
		o = map.get("DestroyOnBreak");
		destroyOnBreak = o instanceof Boolean && (boolean) o;
		o = map.get("IngredientGearScoreBaseBonus");
		ingredientGearScoreBaseBonus = o instanceof Double ? (double) o : 0.0;
		o = map.get("DurabilityDmgOnDeath");
		durabilityDamageOnDeath = o instanceof Double ? (double) o : 0.0;
		audioCreated = (String) map.get("AudioCreated");
		o = map.get("HideInLootTicker");
		hideInLootTicker = o instanceof Boolean && (boolean) o;
		isSalvageable = (boolean) map.get("IsSalvageable");
		notes = (String) map.get("Notes");
		useTypeAffix = (boolean) map.get("UseTypeAffix");
		consumeOnUse = (boolean) map.get("ConsumeOnUse");
		warboardGatherStat = (String) map.get("WarboardGatherStat");
		salvageAchievement = (String) map.get("SalvageAchievement");
		o = map.get("ForceRarity");
		forceRarity = o instanceof Double ? ((Double) o).intValue() : 0;
		o = map.get("IsUniqueItem");
		isUniqueItem = o instanceof Boolean && (boolean) o;
		aDyeSlotDisabled = (boolean) map.get("ADyeSlotDisabled");
		confirmDestroy = (String) map.get("ConfirmDestroy");
		o = map.get("BindOnEquip");
		bindOnEquip = o instanceof Boolean && (boolean) o;
		o = map.get("BindOnPickup");
		bindOnPickup = o instanceof Boolean && (boolean) o;
		acquisitionNotificationId = (String) map.get("AcquisitionNotificationId");
		o = map.get("IngredientBonusSecondary");
		ingredientBonusSecondary = o instanceof Double ? (double) o : 0.0;
		audioUse = (String) map.get("AudioUse");
		grantsHWMBump = (String) map.get("GrantsHWMBump");
		housingTags = (String) map.get("HousingTags");
		prefabPath = (String) map.get("PrefabPath");
		canReplaceGem = (boolean) map.get("CanReplaceGem");
		deathDropPercentage = (double) map.get("DeathDropPercentage");
	}

	public ItemStats getStats() {
		return ItemStatsList.getStats(itemStatsRef);
	}

	public String getTierImage() {
		return "https://new-world.wiki/itemlayout/crafting_tier_"+tier+".png";
	}

	public String getBigImagePath() {
		String name = "big_"+this.iconPath.toLowerCase();
		String path = AssetUtils.getImagePath(name);
		if(path != null) return path;

		File file = new File("D:/workspace/github/NewWorldUnpacker/decompiled/lyshineui/images/icons/items_hires/" + iconPath.toLowerCase() + ".png");
		if(!file.exists()) {
			System.out.println(iconPath.toLowerCase());
			return "";
		}

		boolean result = AssetUtils.uploadImage(name, null, file);
		if(!result) {
			System.out.println("No result");
			return "";
		}
		path = AssetUtils.getImagePath(name);
		return path;
	}

	public String getImagePath() {
		String name = this.iconPath.toLowerCase();
		String path = AssetUtils.getImagePath(name);
		if(path != null) return path;

		boolean result = AssetUtils.uploadImage(name, null, new File(ItemDefinitionsBuilder.ITEM_ICONS_PATH + "/" + itemType.toLowerCase() + "/" + iconPath.toLowerCase() + ".png"));
		if(!result) {
			System.out.println("No result");
			return "";
		}
		path = AssetUtils.getImagePath(name);
		return path;
	}

	public ObjectiveTask getTask() {
		return ObjectiveTasks.getTask(itemID);
	}

	public Tooltip getTooltips() {
		return Tooltips.getTooltip(itemID);
	}

	public String getLocalType() {
		return WikiBuilder.localizationStrings.get(itemTypeDisplayName.replace("@", ""));
	}

	public String getLocalName() {
		return WikiBuilder.localizationStrings.get(name.replace("@", ""));
	}

	public String getLocalDescription() {
		return WikiBuilder.localizationStrings.get(description.replace("@", ""));
	}

	public List<Recipe> getUsedIn() {
		return RecipeBuilder.getUsedIn(itemID);
	}

	public List<Recipe> getObtainedFrom() {
		return RecipeBuilder.getObtainedFrom(itemID);
	}

	public String getRarityBackground() {
		return switch(forceRarity) {
			case 1 -> "background: linear-gradient(#211810, #0B3B0B);";
			default -> "background: linear-gradient(#211810, #8A4B08);";
		};
	}

	public String getRarityItemBackground() {
		return switch(forceRarity) {
			case 1 -> "border: 1px solid #60CD4F;";
			default -> "border: 1px solid orange;";
		};
	}

	public String getRarityColour() {
		return switch(forceRarity) {
			case 1 -> "#22E752";
			case 2 -> "#0CC9C9";
			default -> "orange";
		};
	}

	public String getRarityStringColour() {
		return switch(forceRarity) {
			case 1 -> "#22E752";
			case 2 -> "#0CC9C9";
			default -> "orange";
		};
	}

	public String getRarityString() {
		return switch(forceRarity) {
			case 1 -> "Uncommon";
			case 2 -> "Rare";
			case 3 -> "Epic";
			case 4 -> "Legendary";
			default -> "Common";
		};
	}

	public String getTierString() {
		return switch(tier) {
			case 2 -> "II";
			case 3 -> "III";
			case 4 -> "IV";
			case 5 -> "V";
			default -> "I";
		};
	}
}
