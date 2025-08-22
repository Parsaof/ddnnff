//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks;

import de.waterdu.aquaskills.file.*;
import java.util.*;
import de.waterdu.aquaskills.player.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.*;
import java.util.function.*;
import net.minecraft.entity.player.*;

public class MethodData
{
    public Player player;
    public Skill skill;
    public IHookable hookable;
    
    public Optional<Experience> getExperience() {
        return (Optional<Experience>)this.player.getXP(this.skill);
    }
    
    public long getLevel() {
        return this.getExperience().map((Function<? super Experience, ? extends Long>)Experience::getLevel).orElse(0L);
    }
    
    public long getTrueFunctionalLevel() {
        return this.getExperience().map(xp -> Math.min(this.skill.getMaxFunctionalLevel(), xp.getLevel())).orElse(0L);
    }
    
    public long getTrueLevel() {
        return this.getExperience().map((Function<? super Experience, ? extends Long>)Experience::getTrueLevel).orElse(1L);
    }
    
    public double getXP() {
        return this.getExperience().map((Function<? super Experience, ? extends Double>)Experience::getExperience).orElse(0.0);
    }
    
    public Optional<Cooldown> getCooldown() {
        return (Optional<Cooldown>)this.player.getCooldown((Ability)this.hookable);
    }
    
    public Cooldown getCooldownSafely() {
        return this.player.getCooldown((Ability)this.hookable).orElseGet(() -> {
            AquaSkills.log.warn("Could not find cooldown! Using fallback fake cooldown for now. player = " + this.player.getName() + ", skill = " + this.skill.getName());
            return Cooldown.FAKE_COOLDOWN;
        });
    }
    
    public void ifPlayerPresent(final Consumer<EntityPlayerMP> consumer) {
        final EntityPlayerMP player = this.player.getPlayerEntity();
        if (player != null) {
            consumer.accept(player);
        }
    }
    
    public MethodData(final Player player, final Skill skill, final IHookable hookable) {
        this.player = player;
        this.skill = skill;
        this.hookable = hookable;
    }
}
