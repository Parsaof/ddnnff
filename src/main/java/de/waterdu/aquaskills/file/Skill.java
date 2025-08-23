//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import de.waterdu.aquaskills.skill.elements.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import de.waterdu.aquaskills.hooks.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.leaderboard.*;
import de.waterdu.aquaskills.ui.*;
import net.minecraft.util.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import de.waterdu.aquaapi.ui.api.*;

public class Skill implements IData
{
    private UUID uuid;
    private String name;
    private String title;
    private String displayName;
    private String displayItem;
    private String[] lore;
    private int displayIndex;
    private int exitButtonIndex;
    private int displayButtonIndex;
    private int chatButtonIndex;
    private int abilityButtonIndex;
    private long maxFunctionalLevel;
    private double xpGrowth;
    private long linearCutoffLevel;
    private double exponentialBase;
    private String[] bindableItems;
    private XPSource[] xpSources;
    private Ability[] abilities;
    private Reward[] rewards;
    private transient HashMap<String, ArrayList<IHookable>> mappedHooks;
    private transient HashMap<Long, Reward> mappedRewards;
    private transient ArrayList<Item> parsedBindableItems;
    
    public Skill(final String name, final String[] lore, final String title, final String item, final int displayIndex, final long maxFunctionalLevel, final String[] bindableItems, final XPSource[] xpSources, final Ability[] abilities, final Reward[] rewards) {
        this.uuid = UUID.randomUUID();
        this.exitButtonIndex = 49;
        this.displayButtonIndex = 11;
        this.chatButtonIndex = 15;
        this.abilityButtonIndex = 13;
        this.xpGrowth = 100.0;
        this.linearCutoffLevel = 100L;
        this.exponentialBase = 1.01;
        this.bindableItems = new String[0];
        this.mappedHooks = null;
        this.mappedRewards = null;
        this.parsedBindableItems = null;
        this.name = name;
        this.displayName = name;
        this.lore = lore;
        this.title = title;
        this.displayItem = item;
        this.displayIndex = displayIndex;
        this.maxFunctionalLevel = maxFunctionalLevel;
        this.bindableItems = bindableItems;
        this.xpSources = xpSources;
        this.abilities = abilities;
        this.rewards = rewards;
        this.getMappedHooks();
    }
    
    public Ability[] getAbilities() {
        return this.abilities;
    }
    
    public XPSource[] getXPSources() {
        return this.xpSources;
    }
    
    public Optional<Ability> getAbility(final String hook) {
        for (final Ability ability : this.abilities) {
            if (ability.getHook().equalsIgnoreCase(hook)) {
                return Optional.of(ability);
            }
        }
        return Optional.empty();
    }
    
    public HashMap<String, ArrayList<IHookable>> getMappedHooks() {
        if (this.mappedHooks == null) {
            this.mappedHooks = new HashMap<String, ArrayList<IHookable>>();
            for (final XPSource hook : this.xpSources) {
                this.mapHook(hook);
            }
            for (final Ability hook2 : this.abilities) {
                this.mapHook(hook2);
            }
        }
        return this.mappedHooks;
    }
    
    private void mapHook(final IHookable hook) {
        if (!this.mappedHooks.containsKey(hook.getHook())) {
            this.mappedHooks.put(hook.getHook(), new ArrayList<IHookable>());
        }
        this.mappedHooks.get(hook.getHook()).add(hook);
    }
    
    public HashMap<Long, Reward> getMappedRewards() {
        if (this.mappedRewards == null) {
            this.mappedRewards = new HashMap<Long, Reward>();
            for (final Reward reward : this.rewards) {
                this.mappedRewards.put(reward.getTrueLevel(), reward);
            }
        }
        return this.mappedRewards;
    }
    
    public Optional<Reward> getReward(final long trueLevel) {
        return Optional.ofNullable(this.getMappedRewards().get(trueLevel));
    }
    
    public void getAndExecute(final Event event, final Player player, final MinecraftServer server, final String... hookNames) {
        final EntityPlayerMP playerMP = player.getPlayerEntity();
        if (playerMP != null) {
            for (final String hookName : hookNames) {
                final ArrayList<IHookable> hooks = this.getMappedHooks().get(hookName);
                if (hooks != null) {
                    for (final IHookable hook : hooks) {
                        if ((hook.getPermission() == null || hook.getPermission().isEmpty() || PermHelper.canUse(hook.getPermission(), (ICommandSender)playerMP)) && hook.getHook().equalsIgnoreCase(hookName)) {
                            HookRegistry.get().execute(server, hookName, event, hook.prepare(player, this));
                        }
                    }
                }
            }
        }
    }
    
    public int isItemBindable(final ItemStack stack) {
        if (stack.getCount() != 1) {
            return -2;
        }
        if (this.parsedBindableItems == null) {
            this.parsedBindableItems = new ArrayList<Item>();
            for (final String str : this.bindableItems) {
                final Item i = Item.getByNameOrId(str);
                if (i != null) {
                    this.parsedBindableItems.add(i);
                }
            }
        }
        if (!Config.settings().isCanRebindItems() && ItemHelper.isItemBound(stack)) {
            return -1;
        }
        return this.parsedBindableItems.contains(stack.getItem()) ? 1 : 0;
    }
    
    public ItemStack bindItem(final ItemStack stack, final Ability ability) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.setStackDisplayName(Config.format("boundItemName", stack.getDisplayName(), ability.getDisplayInfo().getName()));
        ItemHelper.prefixLore(ItemHelper.isItemBound(stack), stack, Config.format("spellLoreA", ability.getDisplayInfo().getName()), Config.format("spellLoreB", new Object[0]));
        stack.getTagCompound().setBoolean(NbtKeys.BOUND_ITEM, true);
        stack.getTagCompound().setString(NbtKeys.BOUND_SKILL, this.getName());
        stack.getTagCompound().setString(NbtKeys.BOUND_ABILITY, ability.getHook());
        stack.getTagCompound().setUniqueId(NbtKeys.DIFFERENTIATION, UUID.randomUUID());
        return stack;
    }
    
    public String getDisplayName() {
        if (this.displayName == null || this.displayName.isEmpty()) {
            this.displayName = this.name;
        }
        return this.displayName;
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public boolean hasUUID() {
        return this.uuid != null;
    }
    
    public void setUUID(final UUID uuid) {
    }
    
    public String getFilename() {
        return this.name;
    }
    
    public ItemStack getItem() {
        Item item = Item.getByNameOrId(this.displayItem);
        if (item == null) {
            item = Items.EXPERIENCE_BOTTLE;
        }
        return new ItemStack(item);
    }
    
    public Button getButton(final Experience experience, final int index, final boolean moreInfo) {
        final StringBuilder lore = new StringBuilder("\n" + TextFormatting.RESET + "" + Config.format("level", experience.getDisplayLevel()) + "\n" + TextFormatting.RESET + "" + Config.format("xp", experience.getDisplayXP(), experience.getExperienceRequiredForLevelUp()) + TextFormatting.RESET + "" + TextFormatting.GRAY + "\n" + (moreInfo ? ("\n" + TextFormatting.RESET + "" + Config.format("more", new Object[0])) : ""));
        if (!moreInfo) {
            for (final String loreLine : this.lore) {
                lore.append("\n").append(TextFormatting.RESET).append(TextFormatting.GRAY).append(loreLine);
            }
            lore.append("\n\n");
            switch (this.abilitiesEnabled(experience.getPlayer().orElse(null))) {
                case NONE: {
                    lore.append(Config.format("abilitiesDisabledAll", new Object[0]));
                    break;
                }
                case SOME: {
                    lore.append(Config.format("abilitiesEnabledSome", new Object[0]));
                    break;
                }
                case ALL: {
                    lore.append(Config.format("abilitiesEnabledAll", new Object[0]));
                    break;
                }
            }
            lore.append("\n");
            switch (this.messagesEnabled(experience.getPlayer().orElse(null))) {
                case NONE: {
                    lore.append(Config.format("messagesDisabledAll", new Object[0]));
                    break;
                }
                case SOME: {
                    lore.append(Config.format("messagesEnabledSome", new Object[0]));
                    break;
                }
                case ALL: {
                    lore.append(Config.format("messagesEnabledAll", new Object[0]));
                    break;
                }
            }
        }
        return new Button(TextFormatting.AQUA + this.getDisplayName(), lore.toString(), this.getItem(), index);
    }
    
    public Button getLeaderboardButton(final EntityPlayerMP player, final IPage parent, final Player me, final int index) {
        final Tuple<Player, Experience> leader = Leaderboard.get().getLeader(this);
        final int myPos = Leaderboard.get().getPos(this, me);
        final StringBuilder lore = new StringBuilder("\n");
        if (leader == null) {
            lore.append(TextFormatting.RESET).append(TextFormatting.DARK_RED).append("Leaderboard not found, try again in a little bit!");
        }
        else {
            lore.append(TextFormatting.RESET).append(TextFormatting.YELLOW).append("Leader: ").append(TextFormatting.WHITE).append(((Player)leader.getFirst()).getName()).append("\n").append(TextFormatting.RESET).append(Config.format("level", ((Experience)leader.getSecond()).getTrueLevel())).append(TextFormatting.GRAY).append(", ").append(Config.format("xp", ((Experience)leader.getSecond()).getDisplayXP(), ((Experience)leader.getSecond()).getExperienceRequiredForLevelUp()));
            if (myPos != -1) {
                me.getXP(this).ifPresent(xp -> lore.append("\n\n").append(TextFormatting.RESET).append(TextFormatting.YELLOW).append("Position #").append(myPos + 1).append(": ").append(TextFormatting.WHITE).append("You").append("\n").append(TextFormatting.RESET).append(Config.format("level", xp.getTrueLevel())).append(TextFormatting.GRAY).append(" - ").append(Config.format("xp", xp.getDisplayXP(), xp.getExperienceRequiredForLevelUp())));
            }
            lore.append("\n\n").append(TextFormatting.RESET).append(TextFormatting.DARK_GRAY).append("Click for full leaderboard");
        }
        final Button button = new Button(TextFormatting.AQUA + this.getDisplayName(), lore.toString(), this.getItem(), index);
        button.setClickAction(clickData -> AquaUI.openUI(player, (IPage)new LeaderboardPage(this, parent)));
        return button;
    }
    
    public void addDetailsToButton(final Button.Builder builder, final Player player, final boolean me, final int ordinal) {
        builder.setItem(AquaAPIData.getPlayer(player.getUUID()).getSkullItemStack()).setName(TextFormatting.YELLOW + ((ordinal == 0) ? "Leader: " : ("Position #" + (ordinal + 1) + ": ")) + TextFormatting.WHITE + (me ? "You" : player.getName()));
        player.getXP(this).ifPresent(xp -> builder.addLoreLine(TextFormatting.GRAY + Config.format("level", xp.getTrueLevel()) + TextFormatting.GRAY + ", " + Config.format("xp", xp.getDisplayXP(), xp.getExperienceRequiredForLevelUp())));
    }
    
    public Tristate abilitiesEnabled(final Player p) {
        if (p == null) {
            return Tristate.NONE;
        }
        int count = 0;
        for (final Ability ability : this.abilities) {
            final Optional<Cooldown> cooldown = (Optional<Cooldown>)p.getCooldown(ability);
            if (cooldown.isPresent() && cooldown.get().isEnabled()) {
                ++count;
            }
        }
        return Tristate.get(count, this.abilities.length);
    }
    
    public Tristate messagesEnabled(final Player p) {
        if (p == null) {
            return Tristate.NONE;
        }
        int count = 0;
        for (final Ability ability : this.abilities) {
            final Optional<Cooldown> cooldown = (Optional<Cooldown>)p.getCooldown(ability);
            if (cooldown.isPresent() && cooldown.get().isMessages()) {
                ++count;
            }
        }
        return Tristate.get(count, this.abilities.length);
    }
    
    public void toggleAllAbilities(final Player p) {
        if (p == null) {
            return;
        }
        final Tristate tristate = this.abilitiesEnabled(p);
        for (final Ability ability : this.abilities) {
            p.getCooldown(ability).ifPresent(cooldown -> cooldown.setEnabled(!tristate.areEnabled()));
        }
    }
    
    public void toggleAllMessages(final Player p) {
        if (p == null) {
            return;
        }
        final Tristate tristate = this.messagesEnabled(p);
        for (final Ability ability : this.abilities) {
            p.getCooldown(ability).ifPresent(cooldown -> cooldown.setMessages(!tristate.areEnabled()));
        }
    }
    
    public boolean isASBP() {
        return Config.settingsASBP().getASBPSkillName().equalsIgnoreCase(this.name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDisplayItem() {
        return this.displayItem;
    }
    
    public String[] getLore() {
        return this.lore;
    }
    
    public int getDisplayIndex() {
        return this.displayIndex;
    }
    
    public int getExitButtonIndex() {
        return this.exitButtonIndex;
    }
    
    public int getDisplayButtonIndex() {
        return this.displayButtonIndex;
    }
    
    public int getChatButtonIndex() {
        return this.chatButtonIndex;
    }
    
    public int getAbilityButtonIndex() {
        return this.abilityButtonIndex;
    }
    
    public long getMaxFunctionalLevel() {
        return this.maxFunctionalLevel;
    }
    
    public double getXpGrowth() {
        return this.xpGrowth;
    }
    
    public long getLinearCutoffLevel() {
        return this.linearCutoffLevel;
    }
    
    public double getExponentialBase() {
        return this.exponentialBase;
    }
    
    public String[] getBindableItems() {
        return this.bindableItems;
    }
    
    public Reward[] getRewards() {
        return this.rewards;
    }
    
    public ArrayList<Item> getParsedBindableItems() {
        return this.parsedBindableItems;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public void setDisplayItem(final String displayItem) {
        this.displayItem = displayItem;
    }
    
    public void setLore(final String[] lore) {
        this.lore = lore;
    }
    
    public void setDisplayIndex(final int displayIndex) {
        this.displayIndex = displayIndex;
    }
    
    public void setExitButtonIndex(final int exitButtonIndex) {
        this.exitButtonIndex = exitButtonIndex;
    }
    
    public void setDisplayButtonIndex(final int displayButtonIndex) {
        this.displayButtonIndex = displayButtonIndex;
    }
    
    public void setChatButtonIndex(final int chatButtonIndex) {
        this.chatButtonIndex = chatButtonIndex;
    }
    
    public void setAbilityButtonIndex(final int abilityButtonIndex) {
        this.abilityButtonIndex = abilityButtonIndex;
    }
    
    public void setMaxFunctionalLevel(final long maxFunctionalLevel) {
        this.maxFunctionalLevel = maxFunctionalLevel;
    }
    
    public void setXpGrowth(final double xpGrowth) {
        this.xpGrowth = xpGrowth;
    }
    
    public void setLinearCutoffLevel(final long linearCutoffLevel) {
        this.linearCutoffLevel = linearCutoffLevel;
    }
    
    public void setExponentialBase(final double exponentialBase) {
        this.exponentialBase = exponentialBase;
    }
    
    public void setBindableItems(final String[] bindableItems) {
        this.bindableItems = bindableItems;
    }
    
    public void setXpSources(final XPSource[] xpSources) {
        this.xpSources = xpSources;
    }
    
    public void setAbilities(final Ability[] abilities) {
        this.abilities = abilities;
    }
    
    public void setRewards(final Reward[] rewards) {
        this.rewards = rewards;
    }
    
    public void setMappedHooks(final HashMap<String, ArrayList<IHookable>> mappedHooks) {
        this.mappedHooks = mappedHooks;
    }
    
    public void setMappedRewards(final HashMap<Long, Reward> mappedRewards) {
        this.mappedRewards = mappedRewards;
    }
    
    public void setParsedBindableItems(final ArrayList<Item> parsedBindableItems) {
        this.parsedBindableItems = parsedBindableItems;
    }
    
    public Skill() {
        this.uuid = UUID.randomUUID();
        this.exitButtonIndex = 49;
        this.displayButtonIndex = 11;
        this.chatButtonIndex = 15;
        this.abilityButtonIndex = 13;
        this.xpGrowth = 100.0;
        this.linearCutoffLevel = 100L;
        this.exponentialBase = 1.01;
        this.bindableItems = new String[0];
        this.mappedHooks = null;
        this.mappedRewards = null;
        this.parsedBindableItems = null;
    }
}
