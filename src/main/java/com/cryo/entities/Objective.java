package com.cryo.entities;

import com.cryo.WikiBuilder;
import com.cryo.builders.ItemDefinitionsBuilder;
import com.cryo.loaders.GameEvents;
import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;

@Data
public class Objective {

	private final boolean canFastTravel;
	private final double exclusiveTerritory;
	private final String destinationCompletionAvailablePrompt;
	private final String objectiveCompletionResponse;
	private final String task;
	private final String description;
	private final String playerPrompt;
	private final String difficulty;
	private final double requiredLevel;
	private final double entitlementId;
	private final double itemRewardQty;
	private final int difficultyLevel;
	private final String releaseEventTags;
	private final String npcDestinationId;
	private final String experienceReward;
	private final String inProgressResponse;
	private final boolean canBeShared;
	private final String requiredAchievementId;
	private final String skipReqAchievmentWhenInvalid;
	private final String itemRewardName;
	private final boolean repeatable;
	private final String objectiveAcceptanceResponse;
	private final String objectiveID;
	private final String successGameEventId;
	private final String failureGameEventId;
	private final String achievementId;
	private final String title;
	private final String maxInfluenceLevel;
	private final String objectiveProposalResponse;
	private final boolean canBeAbandoned;
	private final String type;
	private final boolean hideIncompleteTurninMarker;
	private final String destinationCompletionAvailableResponse;
	private final String requiredFaction;
	private final String releaseActiveOverride;
	private final boolean flagPvp;
	private final String objectiveReward;

	public Objective(String file, LinkedTreeMap<String, Object> map) {
		Object o = map.get("CanFastTravel");
		canFastTravel = o instanceof Boolean && (boolean) o;
		o = map.get("ExclusiveTerritory");
		exclusiveTerritory = o instanceof Double ? (double) o : 0.0;
		destinationCompletionAvailablePrompt = (String) map.get("DestinationCompletionAvailablePrompt");
		objectiveCompletionResponse = (String) map.get("ObjectiveCompletionResponse");
		task = (String) map.get("Task");
		description = (String) map.get("Description");
		playerPrompt = (String) map.get("PlayerPrompt");
		difficulty = (String) map.get("Difficulty");
		o = map.get("RequiredLevel");
		requiredLevel = o instanceof Double ? (double) o : 0.0;
		o = map.get("EntitlementId");
		if(o instanceof Double) {
			System.out.println("Is double: ("+file+") "+o);
			entitlementId = (double) o;
		} else
			entitlementId = 0.0;
		o = map.get("ItemRewardQty");
		itemRewardQty = o instanceof Double ? (double) o : 0.0;
		o = map.get("DifficultyLevel");
		difficultyLevel = o instanceof Double ? ((Double) o).intValue() : 0;
		releaseEventTags = (String) map.get("ReleaseEventTags");
		npcDestinationId = (String) map.get("NpcDestinationId");
		experienceReward = (String) map.get("ExperienceReward");
		inProgressResponse = (String) map.get("InProgressResponse");
		o = map.get("CanBeShared");
		canBeShared = o instanceof Boolean && (boolean) o;
		requiredAchievementId = (String) map.get("RequiredAchievementId");
		skipReqAchievmentWhenInvalid = (String) map.get("SkipReqAchievmentWhenInvalid");
		itemRewardName = (String) map.get("ItemRewardName");
		o = map.get("Repeatable");
		repeatable = o instanceof Double && (double) o == 1.0;
		objectiveAcceptanceResponse = (String) map.get("ObjectiveAcceptanceResponse");
		objectiveID = (String) map.get("ObjectiveID");
		successGameEventId = (String) map.get("SuccessGameEventId");
		failureGameEventId = (String) map.get("FailureGameEventId");
		achievementId = (String) map.get("AchievementId");
		title = (String) map.get("Title");
		maxInfluenceLevel = (String) map.get("MaxInfluenceLevel");
		objectiveProposalResponse = (String) map.get("ObjectiveProposalResponse");
		o = map.get("CanBeAbandoned");
		canBeAbandoned = o instanceof Boolean && (boolean) o;
		type = (String) map.get("Type");
		o = map.get("HideIncompleteTurninMarker");
		hideIncompleteTurninMarker = o instanceof Boolean && (boolean) o;
		destinationCompletionAvailableResponse = (String) map.get("DestinationCompletionAvailableResponse");
		requiredFaction = (String) map.get("RequiredFaction");
		releaseActiveOverride = (String) map.get("ReleaseActiveOverride");
		o = map.get("FlagPvp");
		flagPvp = o instanceof Boolean && (boolean) o;
		objectiveReward = (String) map.get("ObjectiveReward");
	}

	public String getLocalTitle() {
		String local = WikiBuilder.localizationStrings.get(title.replace("@", ""));
		if(local == null) {
			System.out.println("Cannot find title: "+title);
			return "N/A";
		}
		return local;
	}

	public String getLocalDescription() {
		return WikiBuilder.localizationStrings.get(description.replace("@", ""));
	}

	public GameEvent getSuccessGameEvent() {
		return GameEvents.getEvent(successGameEventId);
	}

	public ItemDefinitions getItemReward() {
		return ItemDefinitionsBuilder.getItemDefinitionsById().get(getSuccessGameEvent().getItemReward());
	}

	public String getRewardItemBackgroundImage() {
		return "background-image: url(/itemlayout/crafting_itemraritybg"+getItemReward().getForceRarity()+".png)";
	}
}
