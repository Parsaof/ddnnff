//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.hooks.defaults.*;
import de.waterdu.aquaskills.battlepass.*;

public class Defaults
{
    public static boolean addedDefaults;
    
    public static void addDefaults() {
        if (AquaConfig.getAll("aquaskills", (Class)Skill.class).isEmpty()) {
            Acrobatics.create();
            Archery.create();
            Axemanship.create();
            Crafting.create();
            Excavation.create();
            Fishing.create();
            Herbalism.create();
            MartialArts.create();
            Mining.create();
            Salvaging.create();
            Smelting.create();
            Swordsmanship.create();
            Woodcutting.create();
            Wizard.create();
            Cleric.create();
            Necromancy.create();
            Paladin.create();
            Sorcerer.create();
            Warlock.create();
            AnimalHusbandry.create();
            Leveller.create();
            AquaConfig.save("aquaskills", (Class)Skill.class);
            Defaults.addedDefaults = true;
        }
        final ASBPRewards rewards = (ASBPRewards)AquaConfig.get("aquaskills", (Class)ASBPRewards.class);
        if (rewards.getInternal().isEmpty()) {
            for (int i = 1; i <= 100; ++i) {
                final ASBPReward reward = new ASBPReward(i, new String[i % 2 != 0], new RewardDisplay("diamond", false, false, "Level " + i, new String[] { "", "1x Diamond" }), "You have claimed your Level " + i + " rewards!", new String[] { "give @p diamond_block" }, new RewardDisplay("diamond_block", false, true, "Level " + i + " Premium", new String[] { "", "1x Diamond Block" }), "You have claimed your Level " + i + " Premium rewards!");
                if (i % 2 != 0) {
                    reward.getRewards().get("free").getRewards()[0] = "give @p diamond";
                }
                rewards.getInternal().add(reward);
            }
            AquaConfig.save("aquaskills", (Class)ASBPRewards.class);
        }
    }
    
    static {
        Defaults.addedDefaults = false;
    }
}
