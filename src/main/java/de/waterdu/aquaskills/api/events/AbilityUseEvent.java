//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.api.events;

import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;

public class AbilityUseEvent extends Event
{
    public final Player player;
    public final Skill skill;
    public final Ability ability;
    
    public AbilityUseEvent(final Player player, final Skill skill, final Ability ability) {
        this.player = player;
        this.skill = skill;
        this.ability = ability;
    }
}
