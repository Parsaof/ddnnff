//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.api.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.spells.*;

@Cancelable
public class SpellImpactEvent extends Event
{
    public final Player source;
    public final EntityLivingBase target;
    public final Skill skill;
    public final Ability ability;
    public final ISpell spell;
    
    public SpellImpactEvent(final Player source, final EntityLivingBase target, final Skill skill, final Ability ability, final ISpell spell) {
        this.source = source;
        this.target = target;
        this.skill = skill;
        this.ability = ability;
        this.spell = spell;
    }
}
