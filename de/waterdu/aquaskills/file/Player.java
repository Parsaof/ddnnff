//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import de.waterdu.aquaskills.player.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaapi.file.api.*;

public class Player implements IPlayerData
{
    private UUID uuid;
    private String name;
    private String hotbar;
    private boolean autoswitchHotbar;
    private boolean playSounds;
    private boolean fadeOutXP;
    private double rangeModifier;
    private HashSet<Experience> xp;
    private HashSet<Cooldown> cooldowns;
    private HashMap<String, AbilityInfo> persistentInfo;
    private transient HashMap<String, Experience> mappedXP;
    private transient HashMap<String, Cooldown> mappedCooldowns;
    private transient long lastXPSound;
    private transient EntityPlayerMP playerEntity;
    private transient int saveBuffer;
    private transient int saveCalls;
    
    public Player(final EntityPlayerMP player) {
        this(player.getUniqueID(), player.getName());
    }
    
    public Player(final UUID player, final String name) {
        this.hotbar = null;
        this.autoswitchHotbar = Config.settings().isAutoswitchHotbarDefault();
        this.playSounds = Config.settings().isPlaySoundsDefault();
        this.fadeOutXP = Config.settings().isFadeOutXPDefault();
        this.rangeModifier = 1.0;
        this.xp = new HashSet<Experience>();
        this.cooldowns = new HashSet<Cooldown>();
        this.persistentInfo = new HashMap<String, AbilityInfo>();
        this.mappedXP = new HashMap<String, Experience>();
        this.mappedCooldowns = new HashMap<String, Cooldown>();
        this.lastXPSound = -1L;
        this.playerEntity = null;
        this.saveBuffer = -1;
        this.saveCalls = 0;
        this.uuid = player;
        this.name = name;
        this.init();
    }
    
    public HashSet<Experience> getXP() {
        return this.xp;
    }
    
    public double getLeaderboardXP(final Skill skill) {
        return this.getXP(skill).map(xp -> -(xp.getExperience() + xp.getLevel() * 500000.0)).orElse(0.0);
    }
    
    public void init() {
        final Collection<Skill> ALL_SKILLS = (Collection<Skill>)AquaConfig.getAll("aquaskills", (Class)Skill.class);
        this.xp.removeIf(xp -> !xp.getSkill().isPresent());
        this.mappedXP.clear();
        final Experience experience;
        this.xp.forEach(xp -> xp.getSkill().ifPresent(skill -> experience = this.mappedXP.put(skill.getName(), xp)));
        this.cooldowns.removeIf(cooldown -> !cooldown.getAbility().isPresent());
        this.mappedCooldowns.clear();
        final Cooldown cooldown3;
        this.cooldowns.forEach(cooldown -> cooldown.getAbility().ifPresent(ability -> cooldown3 = this.mappedCooldowns.put(ability.getName(), cooldown)));
        Experience xp2;
        final Ability[] array;
        int length;
        int i = 0;
        Ability ability2;
        Cooldown cooldown2;
        ALL_SKILLS.forEach(skill -> {
            if (!this.mappedXP.containsKey(skill.getName())) {
                xp2 = new Experience(this.uuid, skill);
                this.xp.add(xp2);
                this.mappedXP.put(skill.getName(), xp2);
            }
            skill.getAbilities();
            for (length = array.length; i < length; ++i) {
                ability2 = array[i];
                if (!this.mappedCooldowns.containsKey(ability2.getName())) {
                    cooldown2 = new Cooldown(this.uuid, skill, ability2);
                    this.cooldowns.add(cooldown2);
                    this.mappedCooldowns.put(ability2.getName(), cooldown2);
                }
            }
        });
    }
    
    public double getRangeModifier() {
        return this.rangeModifier;
    }
    
    public Optional<Experience> getXP(final Skill skill) {
        if (skill == null) {
            return Optional.empty();
        }
        return this.getXP(skill.getName());
    }
    
    public boolean gainExperience(final Skill skill, final double amount) {
        return this.getXP(skill).map(xp -> {
            xp.gainExperience(amount);
            return true;
        }).orElseGet(() -> {
            AquaSkills.log.warn("Failed to gain experience! player = " + this.getName());
            return false;
        });
    }
    
    public Optional<Experience> getXP(final String skill) {
        return Optional.ofNullable(this.mappedXP.get(skill));
    }
    
    public Optional<Cooldown> getCooldown(final Ability ability) {
        return this.getCooldown(ability.getName());
    }
    
    public Optional<Cooldown> getCooldown(final String ability) {
        final Cooldown cooldown = this.mappedCooldowns.get(ability);
        return Optional.ofNullable(cooldown);
    }
    
    public void toggleHotbar(final Skill skill, final EntityPlayerMP player) {
        if (this.hotbar == null || !this.hotbar.equals(skill.getName())) {
            this.hotbar = skill.getName();
            this.getXP(skill).ifPresent(Experience::setHotbar);
        }
        else {
            this.hotbar = null;
            PlayerHelper.clearBottomTitle(player);
        }
    }
    
    public void setHotbar(final String skill, final EntityPlayerMP player) {
        this.hotbar = skill;
        if (this.hotbar == null) {
            PlayerHelper.clearBottomTitle(player);
        }
    }
    
    public void toggleFadeOutXP() {
        this.fadeOutXP = !this.fadeOutXP;
    }
    
    public boolean shouldFadeOutXP() {
        return this.fadeOutXP;
    }
    
    public void toggleSounds() {
        this.playSounds = !this.playSounds;
    }
    
    public boolean shouldPlaySounds() {
        return this.playSounds;
    }
    
    public void playSound(final EntityPlayerMP player, final BlockPos pos, final SoundEvent sound, final SoundCategory category, final float volume, final float pitch) {
        player.getServerWorld().playSound((EntityPlayer)null, pos, sound, category, volume, pitch);
    }
    
    public void playSound(final EntityPlayerMP player, final SoundEvent sound, final SoundCategory category, final float volume, final float pitch) {
        this.playSound(player, player.getPosition(), sound, category, volume, pitch);
    }
    
    public void playSound(final BlockPos pos, final SoundEvent sound, final SoundCategory category, final float volume, final float pitch) {
        final EntityPlayerMP player = this.getPlayerEntity();
        if (player != null) {
            this.playSound(player, pos, sound, category, volume, pitch);
        }
    }
    
    public void playSound(final SoundEvent sound, final SoundCategory category, final float volume, final float pitch) {
        final EntityPlayerMP player = this.getPlayerEntity();
        if (player != null) {
            this.playSound(player, player.getPosition(), sound, category, volume, pitch);
        }
    }
    
    public int getMessageState() {
        int state = 0;
        for (final Experience experience : this.xp) {
            if (experience.isChat()) {
                state = 2;
            }
            else {
                if (state == 2) {
                    state = 1;
                    break;
                }
                continue;
            }
        }
        return state;
    }
    
    public void setMessageState(final boolean enabled) {
        for (final Experience experience : this.xp) {
            experience.setChat(enabled);
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public EntityPlayerMP getPlayerEntity() {
        if (this.playerEntity == null || this.playerEntity.hasDisconnected()) {
            this.playerEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.uuid);
        }
        return this.playerEntity;
    }
    
    public void sendMessage(final String message) {
        final EntityPlayerMP player = this.getPlayerEntity();
        if (player != null) {
            player.sendMessage((ITextComponent)new TextComponentString(message));
        }
    }
    
    public void sendAbilityMessage(final String ability, final Object... args) {
        final String text = Config.ability(ability, args);
        if (text.isEmpty()) {
            this.sendMessage(text);
        }
    }
    
    public void sendFormattedMessage(final String key, final Object... args) {
        this.sendFormattedMessage(false, key, args);
    }
    
    public void sendFormattedMessage(final boolean asbp, final String key, final Object... args) {
        this.sendMessage(asbp ? Config.neutralASBP(key, args) : Config.neutral(key, args));
    }
    
    public AbilityInfo getPersistentInfo(final String id) {
        if (this.persistentInfo.containsKey(id)) {
            this.persistentInfo.put(id, new AbilityInfo());
        }
        return this.persistentInfo.get(id);
    }
    
    public void save(final boolean force) {
        if (this.saveBuffer == -1) {
            this.saveBuffer = Config.settings().getXpGainSaveBuffer();
        }
        if (force || ++this.saveCalls >= this.saveBuffer) {
            AquaConfig.save("aquaskills", (IData)this);
            this.saveCalls = 0;
        }
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public void setUUID(final UUID uuid) {
        this.uuid = uuid;
    }
    
    public String getFilename() {
        return this.uuid.toString();
    }
    
    public String getHotbar() {
        return this.hotbar;
    }
    
    public boolean isAutoswitchHotbar() {
        return this.autoswitchHotbar;
    }
    
    public boolean isPlaySounds() {
        return this.playSounds;
    }
    
    public boolean isFadeOutXP() {
        return this.fadeOutXP;
    }
    
    public HashSet<Cooldown> getCooldowns() {
        return this.cooldowns;
    }
    
    public HashMap<String, AbilityInfo> getPersistentInfo() {
        return this.persistentInfo;
    }
    
    public HashMap<String, Experience> getMappedXP() {
        return this.mappedXP;
    }
    
    public HashMap<String, Cooldown> getMappedCooldowns() {
        return this.mappedCooldowns;
    }
    
    public long getLastXPSound() {
        return this.lastXPSound;
    }
    
    public int getSaveBuffer() {
        return this.saveBuffer;
    }
    
    public int getSaveCalls() {
        return this.saveCalls;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setHotbar(final String hotbar) {
        this.hotbar = hotbar;
    }
    
    public void setAutoswitchHotbar(final boolean autoswitchHotbar) {
        this.autoswitchHotbar = autoswitchHotbar;
    }
    
    public void setPlaySounds(final boolean playSounds) {
        this.playSounds = playSounds;
    }
    
    public void setFadeOutXP(final boolean fadeOutXP) {
        this.fadeOutXP = fadeOutXP;
    }
    
    public void setRangeModifier(final double rangeModifier) {
        this.rangeModifier = rangeModifier;
    }
    
    public void setXp(final HashSet<Experience> xp) {
        this.xp = xp;
    }
    
    public void setCooldowns(final HashSet<Cooldown> cooldowns) {
        this.cooldowns = cooldowns;
    }
    
    public void setPersistentInfo(final HashMap<String, AbilityInfo> persistentInfo) {
        this.persistentInfo = persistentInfo;
    }
    
    public void setMappedXP(final HashMap<String, Experience> mappedXP) {
        this.mappedXP = mappedXP;
    }
    
    public void setMappedCooldowns(final HashMap<String, Cooldown> mappedCooldowns) {
        this.mappedCooldowns = mappedCooldowns;
    }
    
    public void setLastXPSound(final long lastXPSound) {
        this.lastXPSound = lastXPSound;
    }
    
    public void setPlayerEntity(final EntityPlayerMP playerEntity) {
        this.playerEntity = playerEntity;
    }
    
    public void setSaveBuffer(final int saveBuffer) {
        this.saveBuffer = saveBuffer;
    }
    
    public void setSaveCalls(final int saveCalls) {
        this.saveCalls = saveCalls;
    }
    
    public Player() {
        this.hotbar = null;
        this.autoswitchHotbar = Config.settings().isAutoswitchHotbarDefault();
        this.playSounds = Config.settings().isPlaySoundsDefault();
        this.fadeOutXP = Config.settings().isFadeOutXPDefault();
        this.rangeModifier = 1.0;
        this.xp = new HashSet<Experience>();
        this.cooldowns = new HashSet<Cooldown>();
        this.persistentInfo = new HashMap<String, AbilityInfo>();
        this.mappedXP = new HashMap<String, Experience>();
        this.mappedCooldowns = new HashMap<String, Cooldown>();
        this.lastXPSound = -1L;
        this.playerEntity = null;
        this.saveBuffer = -1;
        this.saveCalls = 0;
    }
}
