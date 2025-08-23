//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon;

import de.waterdu.aquaskills.pixelmon.hooks.listeners.*;
import com.pixelmonmod.pixelmon.quests.objectives.*;
import de.waterdu.aquaskills.pixelmon.api.*;
import com.pixelmonmod.pixelmon.quests.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.pixelmon.hooks.*;
import de.waterdu.aquaapi.file.api.*;

public class AquaSkillsPixelmon
{
    private static HookListenersPixelmon listeners;
    
    public static void init() {
        try {
            QuestRegistry.registerObjectives(new IObjective[] { (IObjective)new GainLevelObjective(), (IObjective)new GainExperienceObjective() });
        }
        catch (Exception e) {
            AquaSkills.log.error("Failed to register quest objectives!");
        }
        AquaConfig.whenNotBusy("aquaskills", () -> {
            DefaultsPixelmon.addDefaults();
            HookRegistryPixelmon.registerHooks();
            return;
        });
        AquaSkillsPixelmon.listeners = new HookListenersPixelmon();
    }
}
