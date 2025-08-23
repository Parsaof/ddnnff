//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.player;

import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.skill.*;
import java.util.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.api.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaskills.helper.*;

public class Cooldown
{
    public static final Cooldown FAKE_COOLDOWN;
    private UUID parent;
    private UUID link;
    private String skillLink;
    private String abilityLink;
    private long cooldownUntil;
    private long activeUntil;
    private long lastUsed;
    private boolean enabled;
    private boolean messages;
    private transient Player player;
    private transient Skill skill;
    private transient Ability ability;
    private transient boolean fake;
    
    public Cooldown(final UUID player, final Skill skill, final Ability ability) {
        this.cooldownUntil = -1L;
        this.activeUntil = -1L;
        this.lastUsed = -1L;
        this.enabled = true;
        this.messages = true;
        this.player = null;
        this.skill = null;
        this.ability = null;
        this.fake = false;
        this.parent = player;
        this.skillLink = skill.getName();
        this.abilityLink = ability.getDisplayInfo().getName();
    }
    
    public boolean remap() {
        if (this.link != null) {
            for (final Skill skill : SkillMap.getSkills()) {
                if (this.link.equals(skill.getUUID())) {
                    this.skillLink = skill.getName();
                    this.link = null;
                    return true;
                }
            }
        }
        return false;
    }
    
    public void toggleEnabled() {
        this.enabled = !this.enabled;
    }
    
    public void toggleMessages() {
        this.messages = !this.messages;
    }
    
    public Optional<Player> getPlayer() {
        if (!this.fake && this.player == null) {
            this.player = (Player)AquaConfig.get("aquaskills", (Class)Player.class, this.parent);
        }
        return Optional.ofNullable(this.player);
    }
    
    public Optional<Skill> getSkill() {
        if (!this.fake && this.skill == null) {
            this.skill = SkillMap.get(this.skillLink).orElse(null);
        }
        return Optional.ofNullable(this.skill);
    }
    
    public Optional<Ability> getAbility() {
        if (!this.fake && this.ability == null) {
            final Optional<Skill> skill = this.getSkill();
            if (skill.isPresent()) {
                for (final Ability ability : skill.get().getAbilities()) {
                    if (ability.getDisplayInfo().getName().equalsIgnoreCase(this.abilityLink)) {
                        this.ability = ability;
                        break;
                    }
                }
            }
        }
        return Optional.ofNullable(this.ability);
    }
    
    public void use() {
        this.use(0L);
    }
    
    public void use(final long cooldown) {
        this.use(cooldown, 0L);
    }
    
    public void use(final long cooldown, final long duration) {
        if (this.fake) {
            return;
        }
        final long time = System.currentTimeMillis();
        this.cooldownUntil = time + cooldown;
        this.activeUntil = time + duration;
        this.lastUsed = time;
        this.sendUseMessageAndEvent();
    }
    
    public void useOverride(final long cooldown, final long duration) {
        if (this.fake) {
            return;
        }
        final long time = System.currentTimeMillis();
        this.cooldownUntil = time + cooldown;
        this.activeUntil = duration;
        this.lastUsed = time;
        this.sendUseMessageAndEvent();
    }
    
    public void useCooldownOnly(final long cooldown) {
        if (this.fake) {
            return;
        }
        final long time = System.currentTimeMillis();
        this.cooldownUntil = time + cooldown;
        this.activeUntil = 0L;
        this.lastUsed = 0L;
        this.sendUseMessageAndEvent();
    }
    
    public void sendUseMessageAndEvent() {
        final Optional<Player> player = this.getPlayer();
        final Optional<Skill> skill = this.getSkill();
        final Optional<Ability> ability = this.getAbility();
        if (player.isPresent() && skill.isPresent() && ability.isPresent()) {
            if (this.isMessages()) {
                player.get().sendFormattedMessage(skill.get().isASBP(), "ability", new Object[] { ability.get().getName() });
            }
            this.sendUseEventInternal(player.get(), skill.get(), ability.get());
        }
    }
    
    public void sendCantUseMessage() {
        final Optional<Player> player = this.getPlayer();
        final Optional<Skill> skill = this.getSkill();
        final Optional<Ability> ability = this.getAbility();
        if (player.isPresent() && skill.isPresent() && ability.isPresent() && this.isMessages()) {
            player.get().sendFormattedMessage(skill.get().isASBP(), "cooldown", new Object[] { ability.get().getName() });
        }
    }
    
    public void sendUseEvent() {
        final Optional<Player> player = this.getPlayer();
        final Optional<Skill> skill = this.getSkill();
        final Optional<Ability> ability = this.getAbility();
        if (player.isPresent() && skill.isPresent() && ability.isPresent()) {
            this.sendUseEventInternal(player.get(), skill.get(), ability.get());
        }
    }
    
    private void sendUseEventInternal(final Player player, final Skill skill, final Ability ability) {
        AquaSkills.EVENT_BUS.post((Event)new AbilityUseEvent(player, skill, ability));
    }
    
    public boolean isActive() {
        return !this.fake && this.activeUntil > System.currentTimeMillis();
    }
    
    public boolean isOnCooldown() {
        return this.fake || this.cooldownUntil > System.currentTimeMillis();
    }
    
    public String getCooldownString() {
        long cooldown = this.cooldownUntil - System.currentTimeMillis();
        final long hours = cooldown / 3600000L;
        cooldown -= 3600000L * hours;
        final long minutes = cooldown / 60000L;
        cooldown -= 60000L * minutes;
        final long seconds = cooldown / 1000L;
        String str = "";
        if (hours > 0L) {
            str = str + Config.format((hours == 1L) ? "hour" : "hours", new Object[] { hours }) + ", ";
        }
        if (minutes > 0L) {
            str = str + Config.format((minutes == 1L) ? "minute" : "minutes", new Object[] { minutes }) + ", ";
        }
        str += Config.format((seconds == 1L) ? "second" : "seconds", new Object[] { seconds });
        return str;
    }
    
    public UUID getParent() {
        return this.parent;
    }
    
    public UUID getLink() {
        return this.link;
    }
    
    public String getSkillLink() {
        return this.skillLink;
    }
    
    public String getAbilityLink() {
        return this.abilityLink;
    }
    
    public long getCooldownUntil() {
        return this.cooldownUntil;
    }
    
    public long getActiveUntil() {
        return this.activeUntil;
    }
    
    public long getLastUsed() {
        return this.lastUsed;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public boolean isMessages() {
        return this.messages;
    }
    
    public boolean isFake() {
        return this.fake;
    }
    
    public void setParent(final UUID parent) {
        this.parent = parent;
    }
    
    public void setLink(final UUID link) {
        this.link = link;
    }
    
    public void setSkillLink(final String skillLink) {
        this.skillLink = skillLink;
    }
    
    public void setAbilityLink(final String abilityLink) {
        this.abilityLink = abilityLink;
    }
    
    public void setCooldownUntil(final long cooldownUntil) {
        this.cooldownUntil = cooldownUntil;
    }
    
    public void setActiveUntil(final long activeUntil) {
        this.activeUntil = activeUntil;
    }
    
    public void setLastUsed(final long lastUsed) {
        this.lastUsed = lastUsed;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setMessages(final boolean messages) {
        this.messages = messages;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public void setSkill(final Skill skill) {
        this.skill = skill;
    }
    
    public void setAbility(final Ability ability) {
        this.ability = ability;
    }
    
    public void setFake(final boolean fake) {
        this.fake = fake;
    }
    
    public Cooldown() {
        this.cooldownUntil = -1L;
        this.activeUntil = -1L;
        this.lastUsed = -1L;
        this.enabled = true;
        this.messages = true;
        this.player = null;
        this.skill = null;
        this.ability = null;
        this.fake = false;
    }
    
    static {
        FAKE_COOLDOWN = new Cooldown();
        Cooldown.FAKE_COOLDOWN.fake = true;
    }
}
