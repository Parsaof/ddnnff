//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.event.internal;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;

@Cancelable
public class BoundItemEvent extends Event
{
    public final EntityPlayerMP entity;
    public final Player player;
    public final ItemStack stack;
    public final Skill skill;
    public final Ability ability;
    
    public BoundItemEvent(final EntityPlayerMP entity, final Player player, final ItemStack stack, final Skill skill, final Ability ability) {
        this.entity = entity;
        this.player = player;
        this.stack = stack;
        this.skill = skill;
        this.ability = ability;
    }
}
