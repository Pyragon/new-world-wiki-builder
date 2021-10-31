package com.cryo.entities;

import com.google.gson.internal.LinkedTreeMap;
import lombok.Data;

import java.text.DecimalFormat;

@Data
public class GameEvent {

	private final String creatureType;
	private final String itemReward;
	private final String entitlementId;
	private final double factionReputation;
	private final String factionInfluenceSrc;
	private final double territoryStanding;
	private final double azothRewardChance;
	private final int universalExpAmount;
	private final double coinRewardChance;
	private final String categoricalProgressionId;
	private final double gearScoreRange;
	private final double categoricalProgressionReward;
	private final String gameEventType;
	private final String useRestedExp;
	private final double factionTokens;
	private final boolean emitTelemetry;
	private final String eventID;
	private final double level;
	private final int azothReward;
	private final double currencyReward;
	private final double factionInfluenceAmount;

	public GameEvent(LinkedTreeMap<String, Object> map) {
		creatureType = (String) map.get("CreatureType");
		itemReward = (String) map.get("ItemReward");
		entitlementId = (String) map.get("EntitlementId");
		Object o = map.get("FactionReputation");
		factionReputation = o instanceof String ? 0.0 : (double) o;
		factionInfluenceSrc = (String) map.get("FactionInfluenceSrc");
		o = map.get("TerritoryStanding");
		territoryStanding = o instanceof String ? 0.0 : (double) o;
		azothRewardChance = (double) map.get("AzothRewardChance");
		universalExpAmount = ((Double) map.get("UniversalExpAmount")).intValue();
		o = map.get("CoinRewardChance");
		coinRewardChance = o == null || o instanceof String ? 0.0 : (double) o;
		categoricalProgressionId = (String) map.get("CategoricalProgressionId");
		o = map.get("GearScoreRange");
		gearScoreRange = o == null || o instanceof String ? 0.0 : (double) o;
		o = map.get("CategoricalProgressionReward");
		categoricalProgressionReward = o == null || o instanceof String ? 0.0 : (double) o;
		gameEventType = (String) map.get("GameEventType");
		useRestedExp = (String) map.get("UseRestedExp");
		o = map.get("FactionTokens");
		factionTokens = o == null || o instanceof String ? 0.0 : (double) o;
		emitTelemetry = (boolean) map.get("EmitTelemetry");
		eventID = (String) map.get("EventID");
		level = (double) map.get("Level");
		o = map.get("AzothReward");
		azothReward = o == null || o instanceof String ? 0 : ((Double) o).intValue();
		o = map.get("CurrencyReward");
		currencyReward = o == null || o instanceof String ? 0.0 : (double) o;
		o = map.get("FactionInfluenceAmount");
		factionInfluenceAmount = o == null || o instanceof String ? 0.0 : (double) o;
	}

	public String getXPFormatted() {
		DecimalFormat format = new DecimalFormat("###,###,###");
		return format.format(universalExpAmount);
	}

	public String getCoinRewardFormatted() {
		return String.format("%.2f", currencyReward / 100);
	}

}
