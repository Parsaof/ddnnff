//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.skill;

import de.waterdu.aquaskills.file.*;
import java.util.function.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.skill.elements.*;
import java.util.*;

public class SkillMap
{
    private static final SkillMap instance;
    private final HashMap<String, Skill> skills;
    
    private SkillMap() {
        this.skills = new HashMap<String, Skill>();
    }
    
    public static boolean isASBP(final String skill) {
        return get(skill).map((Function<? super Skill, ? extends Boolean>)Skill::isASBP).orElse(false);
    }
    
    public static Optional<Skill> get(final String skill) {
        final String name = (skill == null) ? null : skill.replace(" ", "").toLowerCase(Locale.ROOT);
        final Optional<Skill> optional = (name == null) ? Optional.empty() : Optional.ofNullable(SkillMap.instance.skills.get(name));
        if (!optional.isPresent()) {
            for (final Skill s : SkillMap.instance.skills.values()) {
                if (s.getTitle().replace(" ", "").equalsIgnoreCase(name)) {
                    return Optional.of(s);
                }
            }
        }
        return optional;
    }
    
    public static void init() {
        final Collection<Skill> skills = (Collection<Skill>)AquaConfig.getAll("aquaskills", (Class)Skill.class);
        SkillMap.instance.skills.clear();
        for (final Skill skill : skills) {
            for (final Ability ability : skill.getAbilities()) {
                ability.reset();
            }
            for (final XPSource source : skill.getXPSources()) {
                source.reset();
            }
            skill.setMappedRewards((HashMap)null);
            skill.setMappedHooks((HashMap)null);
            skill.setParsedBindableItems((ArrayList)null);
            skill.getMappedRewards();
            skill.getMappedHooks();
            SkillMap.instance.skills.put(skill.getName().replace(" ", "").toLowerCase(Locale.ROOT), skill);
        }
    }
    
    public static Set<String> getSkillNames() {
        return SkillMap.instance.skills.keySet();
    }
    
    public static Collection<Skill> getSkills() {
        return SkillMap.instance.skills.values();
    }
    
    static {
        instance = new SkillMap();
    }
}
