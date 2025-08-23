//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.player;

import de.waterdu.aquaskills.skill.*;
import java.util.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.file.boosts.*;
import de.waterdu.aquaskills.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.api.events.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.skill.elements.*;

public class Experience
{
    private UUID parent;
    private UUID link;
    private String skillLink;
    private long level;
    private double experience;
    private boolean unread;
    private boolean chat;
    private transient Player player;
    private transient Skill skill;
    
    public Experience(final UUID player, final Skill skill) {
        this.link = null;
        this.unread = false;
        this.chat = false;
        this.player = null;
        this.skill = null;
        this.parent = player;
        this.skillLink = skill.getName();
        this.level = 0L;
        this.experience = 0.0;
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
    
    public Optional<Player> getPlayer() {
        if (this.player == null) {
            this.player = (Player)AquaConfig.get("aquaskills", (Class)Player.class, this.parent);
        }
        return Optional.ofNullable(this.player);
    }
    
    public Optional<Skill> getSkill() {
        if (this.skill == null) {
            this.skill = SkillMap.get(this.skillLink).orElse(null);
        }
        return Optional.ofNullable(this.skill);
    }
    
    public void toggleMessages() {
        this.chat = !this.chat;
    }
    
    public long getTrueLevel() {
        return this.level + 1L;
    }
    
    public String getDisplayLevel() {
        return Long.toString(this.getTrueLevel());
    }
    
    public void gainExperience(double experience) {
        final Optional<Player> playerOptional = this.getPlayer();
        final Optional<Skill> skillOptional = this.getSkill();
        if (playerOptional.isPresent() && skillOptional.isPresent()) {
            final Player player = playerOptional.get();
            final Skill skill = skillOptional.get();
            final EntityPlayerMP entity = player.getPlayerEntity();
            experience = XPBoosts.apply(experience, player, skill);
            final GainExperienceEvent gainExperienceEvent = new GainExperienceEvent(player, skill, experience);
            if (AquaSkills.EVENT_BUS.post((Event)gainExperienceEvent)) {
                return;
            }
            experience = gainExperienceEvent.experience;
            if (experience <= 0.0) {
                return;
            }
            final boolean asbp = skill.isASBP();
            final boolean sounds = player.shouldPlaySounds();
            double goal = this.getExperienceRequiredForLevelUp();
            this.experience += experience;
            if (this.chat) {
                player.sendFormattedMessage(asbp, asbp ? "gainXPASBP" : "gainXP", new Object[] { skill.getDisplayName(), experience });
            }
            if (sounds) {
                final long time = entity.world.getTotalWorldTime();
                if (player.getLastXPSound() < time) {
                    player.setLastXPSound(time);
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.05f, (RandomHelper.nextFloat() - RandomHelper.nextFloat()) * 0.35f + 0.9f);
                }
            }
            while (this.experience >= goal) {
                final LevelUpEvent levelUpEvent = new LevelUpEvent(player, skill, this.level + 1L);
                if (AquaSkills.EVENT_BUS.post((Event)levelUpEvent)) {
                    break;
                }
                this.experience -= goal;
                ++this.level;
                final AtomicBoolean special = new AtomicBoolean(false);
                player.sendFormattedMessage(asbp, asbp ? "levelUpASBP" : "levelUp", new Object[] { this.getTrueLevel(), skill.getDisplayName(), skill.getTitle() });
                for (final Ability ability : skill.getAbilities()) {
                    if (ability.getLevelRequirement() == this.getTrueLevel()) {
                        player.sendFormattedMessage(asbp, "newAbility", new Object[] { ability.getName() });
                        special.set(this.unread = true);
                    }
                }
                final Player player2;
                final AtomicBoolean atomicBoolean;
                skill.getReward(this.getTrueLevel()).ifPresent(reward -> {
                    reward.execute(player2.getPlayerEntity());
                    atomicBoolean.set(true);
                    return;
                });
                if (sounds) {
                    player.playSound(special.get() ? SoundEvents.UI_TOAST_CHALLENGE_COMPLETE : SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, special.get() ? 0.8f : 0.6f, 1.0f);
                }
                goal = this.getExperienceRequiredForLevelUp();
            }
            if (player.isAutoswitchHotbar() && !skill.isASBP()) {
                player.setHotbar(this.skillLink, player.getPlayerEntity());
            }
            this.setHotbar();
            player.save(false);
        }
        else if (playerOptional.isPresent()) {
            AquaSkills.log.warn("Missing skill in gainExperience call - player " + this.parent + ", skill " + this.skillLink);
        }
        else {
            AquaSkills.log.warn("Missing player in gainExperience call - player " + this.parent + ", skill " + this.skillLink);
        }
    }
    
    public void setHotbar() {
        final TextComponentString textComponentString;
        this.getPlayer().ifPresent(player -> {
            if (player.getHotbar() != null && player.getHotbar().equals(this.skillLink)) {
                this.getSkill().ifPresent(skill -> {
                    new TextComponentString(Config.format(skill.isASBP() ? "barTextASBP" : "barText", new Object[] { skill.getDisplayName(), this.getTrueLevel(), this.getDisplayXP(), this.getExperienceRequiredForLevelUp() }));
                    PlayerHelper.setBottomTitle((ITextComponent)textComponentString, player.getPlayerEntity());
                });
            }
        });
    }
    
    public void setLevel(final long level) {
        final Optional<Player> playerOptional = this.getPlayer();
        final Optional<Skill> skillOptional = this.getSkill();
        if (playerOptional.isPresent() && skillOptional.isPresent()) {
            final Player player = playerOptional.get();
            final Skill skill = skillOptional.get();
            final long oldLevel = this.level;
            this.level = level;
            if (this.level > oldLevel) {
                final AtomicBoolean special = new AtomicBoolean(false);
                final boolean sounds = player.shouldPlaySounds();
                final boolean asbp = skill.isASBP();
                player.sendFormattedMessage(asbp, asbp ? "levelUpASBP" : "levelUp", new Object[] { this.level + 1L, skill.getDisplayName(), skill.getTitle() });
                for (long i = oldLevel + 1L; i <= this.level; ++i) {
                    for (final Ability ability : skill.getAbilities()) {
                        if (ability.getLevelRequirement() == i + 1L) {
                            player.sendFormattedMessage(skill.isASBP(), "newAbility", new Object[] { ability.getName() });
                            special.set(this.unread = true);
                        }
                    }
                    final Player player2;
                    final AtomicBoolean atomicBoolean;
                    skill.getReward(i + 1L).ifPresent(reward -> {
                        reward.execute(player2.getPlayerEntity());
                        atomicBoolean.set(true);
                        return;
                    });
                }
                if (sounds) {
                    player.playSound(special.get() ? SoundEvents.UI_TOAST_CHALLENGE_COMPLETE : SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, special.get() ? 0.8f : 0.6f, 1.0f);
                }
            }
            player.save(false);
        }
        else if (playerOptional.isPresent()) {
            AquaSkills.log.warn("Missing skill in setLevel call - player " + this.parent + ", skill " + this.skillLink);
        }
        else {
            AquaSkills.log.warn("Missing player in setLevel call - player " + this.parent + ", skill " + this.skillLink);
        }
    }
    
    public void setExperience(final double experience) {
        if (experience > this.experience) {
            this.gainExperience(experience - this.experience);
        }
        else {
            this.experience = experience;
            this.getPlayer().ifPresent(p -> p.save(true));
        }
    }
    
    public double getExperienceRequiredForLevelUp() {
        final long linearUntil;
        final double growth;
        final double base;
        final long level;
        return this.getSkill().map(skill -> {
            linearUntil = skill.getLinearCutoffLevel();
            growth = skill.getXpGrowth();
            base = skill.getExponentialBase();
            level = Math.min(this.getTrueLevel(), skill.getMaxFunctionalLevel());
            if (linearUntil >= level) {
                return growth * level;
            }
            else {
                return growth * level + growth * (long)Math.pow(base, (double)(level - linearUntil));
            }
        }).orElseGet(() -> {
            AquaSkills.log.warn("Missing skill in getExperienceRequiredForLevelUp call - player " + this.parent + ", skill " + this.skillLink);
            return Double.MAX_VALUE;
        });
    }
    
    public String getDisplayXP() {
        return Settings.getDecimalFormat().format(this.experience);
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
    
    public long getLevel() {
        return this.level;
    }
    
    public double getExperience() {
        return this.experience;
    }
    
    public boolean isUnread() {
        return this.unread;
    }
    
    public boolean isChat() {
        return this.chat;
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
    
    public void setUnread(final boolean unread) {
        this.unread = unread;
    }
    
    public void setChat(final boolean chat) {
        this.chat = chat;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public void setSkill(final Skill skill) {
        this.skill = skill;
    }
    
    public Experience() {
        this.link = null;
        this.unread = false;
        this.chat = false;
        this.player = null;
        this.skill = null;
    }
}
