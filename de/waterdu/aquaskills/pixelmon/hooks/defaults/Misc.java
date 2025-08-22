//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.defaults;

import com.pixelmonmod.pixelmon.api.events.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.helper.*;
import java.util.*;
import de.waterdu.aquaskills.hooks.*;

public class Misc
{
    public static void init() {
        final Ability ability;
        HashMap map;
        long level;
        double multiplier;
        HookRegistry.get().registerMethodHook("LUCK_OF_THE_SEA", new MethodHook((Class)FishingEvent.Cast.class, (event, data) -> {
            ability = (Ability)data.hookable;
            if (data.getTrueLevel() >= ability.getLevelRequirement()) {
                map = AbilityHelper.getMap(ability);
                level = data.getTrueFunctionalLevel();
                multiplier = map.get("Mult") + map.get("MultPerLvl") * level;
                event.setChanceOfNothing(event.getChanceOfNothing() / (float)multiplier);
                event.setTicksUntilCatch((int)(event.getTicksUntilCatch() / multiplier));
            }
        }));
    }
}
