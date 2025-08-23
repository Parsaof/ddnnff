//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells;

import java.util.*;
import de.waterdu.aquaskills.hooks.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.file.*;

public class SpellEngine
{
    private static final SpellEngine INSTANCE;
    private final HashSet<EntitySpell> spells;
    
    private SpellEngine() {
        this.spells = new HashSet<EntitySpell>();
    }
    
    public static SpellEngine get() {
        return SpellEngine.INSTANCE;
    }
    
    public void onTick() {
        this.spells.removeIf(EntitySpell::onTick);
    }
    
    public void castSpell(final MethodData data, final ISpell spell) {
        this.castSpell(data.player, data.skill, (Ability)data.hookable, spell);
    }
    
    public void castSpell(final Player caster, final Skill skill, final Ability ability, final ISpell spell) {
        this.spells.add(new EntitySpell(caster, skill, ability, spell));
    }
    
    static {
        INSTANCE = new SpellEngine();
    }
}
