//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import de.waterdu.aquaskills.skill.elements.*;
import java.util.function.*;
import org.apache.commons.lang3.math.*;
import java.util.*;

public class AbilityHelper
{
    private static void mapArgs(final Ability ability) {
        mapArgs(ability, null);
    }
    
    private static void mapArgs(final Ability ability, final Function<String, Object> parser) {
        if (!ability.areArgsParsed()) {
            final ArrayList<String> toParse = new ArrayList<String>();
            final boolean hasParser = parser != null;
            final String[] args = ability.getArgs().split(" ");
            final HashMap<String, Double> map = new HashMap<String, Double>();
            for (final String arg : args) {
                final String[] split = arg.split("=");
                if (NumberUtils.isParsable(split[1])) {
                    map.put(split[0], Double.parseDouble(split[1]));
                }
                else if (hasParser) {
                    toParse.add(split[1]);
                }
            }
            ability.getParsedArgs().add(map);
            if (hasParser) {
                for (final String arg2 : toParse) {
                    ability.getParsedArgs().add(parser.apply(arg2));
                }
            }
        }
    }
    
    public static HashMap<String, Double> getMap(final Ability ability) {
        return getMap(ability, null);
    }
    
    public static HashMap<String, Double> getMap(final Ability ability, final Function<String, Object> parser) {
        mapArgs(ability, parser);
        return ability.getParsedArgs().get(0);
    }
}
