//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.command;

import net.minecraft.command.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.file.boosts.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.hooks.*;
import de.waterdu.aquaskills.skill.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaapi.ui.api.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.player.*;
import de.waterdu.aquaskills.async.*;
import org.apache.commons.lang3.math.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.ui.*;
import de.waterdu.aquaskills.battlepass.*;
import de.waterdu.aquaapi.playerdata.*;
import net.minecraft.util.math.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.skill.elements.*;

public class Command extends CommandBase
{
    public String getName() {
        return Config.settings().getCommandName();
    }
    
    public String getUsage(final ICommandSender sender) {
        return "/" + Config.settings().getCommandName();
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return true;
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        final String negative = AquaConfig.format("aquaskills", (Class)Messages.class, "prefixNegative", new Object[0]);
        final String neutral = AquaConfig.format("aquaskills", (Class)Messages.class, "prefixNeutral", new Object[0]);
        final String positive = AquaConfig.format("aquaskills", (Class)Messages.class, "prefixPositive", new Object[0]);
        final String noPerms = negative + AquaConfig.format("aquaskills", (Class)Messages.class, "noPerms", new Object[0]);
        final String invalid = negative + AquaConfig.format("aquaskills", (Class)Messages.class, "invalidArgs", new Object[0]);
        boolean b = false;
        Label_0153: {
            Label_0148: {
                if (args.length > 0) {
                    if (PermHelper.canUseCommand(sender, args[0], true)) {
                        break Label_0148;
                    }
                }
                else if (PermHelper.canUse("aquaskills.admin", sender)) {
                    break Label_0148;
                }
                if (!PermHelper.canUse("aquaskills.admin", sender)) {
                    b = false;
                    break Label_0153;
                }
            }
            b = true;
        }
        final boolean hasAdminPerm = b;
        final boolean hasUserPerm = PermHelper.canUse("aquaskills.user", sender);
        final boolean isPlayer = sender instanceof EntityPlayerMP;
        final EntityPlayerMP player = isPlayer ? ((EntityPlayerMP)sender) : null;
        final Player p = isPlayer ? ((Player)AquaConfig.get("aquaskills", (Class)Player.class, player.getPersistentID())) : null;
        if (args.length == 1) {
            if (this.match(args[0], "save")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                AquaConfig.save("aquaskills", (Class)Log.class);
                this.send(sender, positive + "Saved logs.");
            }
            else if (this.match(args[0], "reload")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                AquaConfig.load("aquaskills", (Class)Settings.class);
                AquaConfig.load("aquaskills", (Class)ASBPSettings.class);
                AquaConfig.load("aquaskills", (Class)ASBPRewards.class);
                AquaConfig.load("aquaskills", (Class)Skill.class);
                AquaConfig.load("aquaskills", (Class)Messages.class);
                AquaConfig.load("aquaskills", (Class)SkillMessages.class);
                AquaConfig.load("aquaskills", (Class)XPBoosts.class);
                AquaConfig.load("aquaskills", (Class)UI.class);
                AquaConfig.whenNotBusy("aquaskills", () -> {
                    HookRegistry.reset();
                    SkillMap.init();
                    ASLogger.get().reload();
                    return;
                });
                TransientData.gc();
                this.send(sender, positive + "Reloaded configuration.");
            }
            else if (this.match(args[0], "leaders") || this.match(args[0], "leaderboard")) {
                if (!hasUserPerm || !isPlayer) {
                    this.send(sender, noPerms);
                    return;
                }
                AquaUI.openUI(player, (IPage)new LeaderboardsPage(p));
            }
            else if (this.match(args[0], "options")) {
                if (!hasUserPerm || !isPlayer) {
                    this.send(sender, noPerms);
                    return;
                }
                AquaUI.openUI(player, (IPage)new OptionsPage(p));
            }
            else if (this.match(args[0], "godmode")) {
                if (!hasAdminPerm || !isPlayer) {
                    this.send(sender, noPerms);
                    return;
                }
                for (final Experience experience : p.getXP()) {
                    experience.setLevel(9000L);
                }
            }
            else if (this.match(args[0], "resetasbp")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                if (Config.settingsASBP().isUse()) {
                    this.send(sender, Config.neutralASBP("resetasbpWarning", new Object[0]));
                }
                else {
                    this.send(sender, Config.negativeASBP("noasbp", new Object[0]));
                }
            }
            else {
                if (!hasUserPerm || !isPlayer) {
                    this.send(sender, noPerms);
                    return;
                }
                final Optional<Skill> skill = SkillMap.get(args[0]);
                if (skill.isPresent() && !skill.get().isASBP()) {
                    p.getXP(skill.get()).ifPresent(xp -> AquaUI.openUI(player, (IPage)new SkillPage(p, skill.get(), xp)));
                }
                else {
                    this.send(sender, negative + args[0] + " isn't a valid skill!");
                }
            }
        }
        else if (args.length == 2) {
            if (this.match(args[0], "resetcooldowns")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                final Player target = PlayerHelper.getPlayer(args[1]);
                if (target != null) {
                    for (final Cooldown cooldown : target.getCooldowns()) {
                        final Cooldown cooldown2;
                        cooldown.getAbility().ifPresent(ability -> {
                            if (ability.isResettable()) {
                                cooldown2.setCooldownUntil(0L);
                                cooldown2.setActiveUntil(0L);
                            }
                            return;
                        });
                    }
                    this.send(sender, positive + "Reset the cooldowns of " + args[1] + "!");
                }
                else {
                    this.send(sender, negative + "Can't find a player called " + args[1] + "!");
                }
            }
            else if (this.match(args[0], "inspect")) {
                if (!hasUserPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                final Player target = PlayerHelper.getPlayer(args[1]);
                if (target != null) {
                    AquaUI.openUI(player, (IPage)new SkillsPage(target));
                }
                else {
                    this.send(sender, negative + "Can't find a player called " + args[1] + "!");
                }
            }
            else if (this.match(args[0], "reset")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                final Player target = PlayerHelper.getPlayer(args[1]);
                if (target != null) {
                    for (final Experience experience2 : target.getXP()) {
                        experience2.setExperience(0.0);
                        experience2.setLevel(1L);
                    }
                    target.save(true);
                    this.send(sender, positive + "Reset all skills of " + args[1] + "!");
                }
                else {
                    this.send(sender, negative + "Can't find a player called " + args[1] + "!");
                }
            }
            else if (this.match(args[0], "resetasbp") && this.match(args[1], "confirm")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                if (Config.settingsASBP().isUse()) {
                    final Skill skill2 = Config.settingsASBP().getASBPSkill();
                    if (skill2 == null) {
                        this.send(sender, Config.negativeASBP("resetasbpFail", new Object[0]));
                        return;
                    }
                    this.send(sender, Config.neutralASBP("resetasbpStart", new Object[0]));
                    int count;
                    final Iterator<Player> iterator4;
                    Player target2;
                    ASBPPlayer asbp;
                    final Skill skill5;
                    Optional<Experience> xp2;
                    ThreadPool.submit(() -> {
                        count = 0;
                        Config.players().iterator();
                        while (iterator4.hasNext()) {
                            target2 = iterator4.next();
                            asbp = Config.playerASBP(target2.getUUID());
                            if (asbp != null) {
                                asbp.reset();
                            }
                            xp2 = target2.getXP(skill5);
                            if (xp2.isPresent()) {
                                xp2.get().setExperience(0.0);
                                xp2.get().setLevel(0L);
                                target2.save(true);
                                ++count;
                            }
                        }
                        this.send(sender, Config.positiveASBP("resetasbpEnd", count));
                    });
                }
                else {
                    this.send(sender, Config.negativeASBP("noasbp", new Object[0]));
                }
            }
            else if (this.match(args[0], "pruneasbp")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                if (Config.settingsASBP().isUse()) {
                    if (NumberUtils.isParsable(args[1])) {
                        final int days = Integer.parseInt(args[1]);
                        if (days >= 1) {
                            this.send(sender, Config.neutralASBP("pruneasbpWarning", days, (days == 1) ? "" : "s"));
                        }
                        else {
                            this.send(sender, negative + args[1] + " isn't a valid value!");
                        }
                    }
                    else {
                        this.send(sender, negative + args[1] + " isn't a valid value!");
                    }
                }
                else {
                    this.send(sender, Config.negativeASBP("noasbp", new Object[0]));
                }
            }
            else {
                this.send(sender, invalid);
            }
        }
        else if (args.length == 3) {
            if (this.match(args[0], "pruneasbp") && this.match(args[2], "confirm")) {
                if (!hasAdminPerm) {
                    this.send(sender, noPerms);
                    return;
                }
                if (Config.settingsASBP().isUse()) {
                    if (NumberUtils.isParsable(args[1])) {
                        final Skill skill2 = Config.settingsASBP().getASBPSkill();
                        if (skill2 == null) {
                            this.send(sender, Config.negativeASBP("pruneasbpFail", new Object[0]));
                            return;
                        }
                        final int days2 = Integer.parseInt(args[1]);
                        if (days2 >= 1) {
                            final long time = System.currentTimeMillis();
                            final long prune = days2 * 86400000L;
                            this.send(sender, Config.neutralASBP("pruneasbpStart", new Object[0]));
                            int count2;
                            final Iterator<Player> iterator5;
                            Player target3;
                            PlayerData data;
                            ASBPPlayer asbp2;
                            final long n;
                            long timeDif;
                            final long n2;
                            final Skill skill6;
                            Optional<Experience> xp3;
                            ThreadPool.submit(() -> {
                                count2 = 0;
                                Config.players().iterator();
                                while (iterator5.hasNext()) {
                                    target3 = iterator5.next();
                                    data = AquaAPIData.getPlayer(target3.getUUID());
                                    asbp2 = Config.playerASBP(target3.getUUID());
                                    timeDif = n - data.getLastLogin();
                                    if (timeDif > n2) {
                                        if (asbp2 != null) {
                                            asbp2.reset();
                                        }
                                        xp3 = target3.getXP(skill6);
                                        if (xp3.isPresent()) {
                                            ++count2;
                                            xp3.get().setExperience(0.0);
                                            xp3.get().setLevel(0L);
                                            target3.save(true);
                                        }
                                        else {
                                            continue;
                                        }
                                    }
                                }
                                this.send(sender, Config.positiveASBP("pruneasbpEnd", count2));
                            });
                        }
                        else {
                            this.send(sender, negative + args[1] + " isn't a valid value!");
                        }
                    }
                    else {
                        this.send(sender, negative + args[1] + " isn't a valid value!");
                    }
                }
                else {
                    this.send(sender, Config.negativeASBP("noasbp", new Object[0]));
                }
            }
            else {
                this.send(sender, invalid);
            }
        }
        else if (args.length == 4) {
            if (hasAdminPerm) {
                if (this.match(args[0], "setlevel") || this.match(args[0], "setexperience") || this.match(args[0], "addlevel") || this.match(args[0], "addexperience")) {
                    final Player target = PlayerHelper.getPlayer(args[1]);
                    if (target != null) {
                        final Optional<Skill> skill3 = SkillMap.get(args[2]);
                        if (skill3.isPresent()) {
                            long value;
                            final String s;
                            final Optional<Skill> optional;
                            final String str;
                            target.getXP(skill3.get()).ifPresent(xp -> {
                                if (NumberUtils.isParsable(args[3])) {
                                    value = Long.parseLong(args[3]);
                                    if (this.match(args[0], "setlevel")) {
                                        xp.setLevel(value - 1L);
                                        xp.setExperience(0.0);
                                        this.send(sender, s + "Set " + args[1] + "'s " + optional.get().getDisplayName() + " level to " + value + ".");
                                    }
                                    else if (this.match(args[0], "setexperience")) {
                                        xp.setExperience((double)value);
                                        this.send(sender, s + "Set " + args[1] + "'s " + optional.get().getDisplayName() + " experience to " + value + ".");
                                    }
                                    else if (this.match(args[0], "addlevel")) {
                                        xp.setLevel(xp.getLevel() + value);
                                        xp.setExperience(0.0);
                                        this.send(sender, s + "Added " + value + " level" + ((value == 1L) ? "" : "s") + " to " + args[1] + "'s " + optional.get().getDisplayName() + ".");
                                    }
                                    else if (this.match(args[0], "addexperience")) {
                                        xp.gainExperience((double)value);
                                        this.send(sender, s + "Added " + value + " experience to " + args[1] + "'s " + optional.get().getDisplayName() + ".");
                                    }
                                }
                                else {
                                    this.send(sender, str + args[3] + " isn't a valid value!");
                                }
                            });
                        }
                        else {
                            this.send(sender, negative + args[2] + " isn't a valid skill!");
                        }
                    }
                    else {
                        this.send(sender, negative + "Can't find a player called " + args[1] + "!");
                    }
                }
                else {
                    this.send(sender, invalid);
                }
            }
            else {
                this.send(sender, noPerms);
            }
        }
        else if (args.length == 6) {
            if (this.match(args[0], "boostxp")) {
                final String[] split;
                final String[] playerArgs = split = args[1].split(",");
                for (final String playerArg : split) {
                    final Player target4 = PlayerHelper.getPlayer(playerArg);
                    final boolean allPlayers = this.match(playerArg, "all");
                    if (target4 != null || allPlayers) {
                        final String[] split2;
                        final String[] skillArgs = split2 = args[2].split(",");
                        for (final String skillArg : split2) {
                            final Optional<Skill> skill4 = SkillMap.get(skillArg);
                            final boolean allSkills = this.match(skillArg, "all");
                            if (skill4.isPresent() || allSkills) {
                                if (NumberUtils.isDigits(args[3])) {
                                    if (NumberUtils.isParsable(args[4])) {
                                        if (NumberUtils.isParsable(args[5])) {
                                            final int duration = Integer.parseInt(args[3]);
                                            final double rate = Double.parseDouble(args[4]);
                                            final double boost = Double.parseDouble(args[5]);
                                            XPBoosts.addBoost(target4, skill4.orElse(null), duration, rate, boost);
                                            this.send(sender, positive + "Created XP boost: player " + ((player == null) ? "all" : player.getName()) + ", skill " + (skill4.isPresent() ? skill4.get().getName() : "all") + ", duration " + duration + "s, multiplier " + rate + "x, flat boost " + boost);
                                        }
                                        else {
                                            this.send(sender, negative + args[5] + " isn't a valid value!");
                                        }
                                    }
                                    else {
                                        this.send(sender, negative + args[4] + " isn't a valid value!");
                                    }
                                }
                                else {
                                    this.send(sender, negative + args[3] + " isn't a valid value!");
                                }
                            }
                            else {
                                this.send(sender, negative + skillArg + " isn't a valid skill!");
                            }
                        }
                    }
                    else {
                        this.send(sender, negative + "Can't find a player called " + playerArg + "!");
                    }
                }
            }
            else {
                this.send(sender, invalid);
            }
        }
        else if (args.length == 0) {
            if (!hasUserPerm || !isPlayer) {
                this.send(sender, noPerms);
                return;
            }
            AquaUI.openUI(player, (IPage)new MainMenuPage(p));
        }
        else {
            this.send(sender, neutral + "1.12.2-3.1.1-universal");
        }
    }
    
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender, final String[] args, @Nullable final BlockPos pos) {
        final boolean isAdmin = PermHelper.canUse("aquaskills.admin", sender);
        final boolean isUser = PermHelper.canUse("aquaskills.user", sender);
        final List<String> possibleArgs = new ArrayList<String>();
        if (args.length == 1) {
            if (isAdmin) {
                possibleArgs.add("save");
                possibleArgs.add("reload");
                possibleArgs.add("setlevel");
                possibleArgs.add("setexperience");
                possibleArgs.add("addlevel");
                possibleArgs.add("addexperience");
                possibleArgs.add("resetcooldowns");
                possibleArgs.add("boostxp");
                possibleArgs.add("resetasbp");
                possibleArgs.add("pruneasbp");
                possibleArgs.add("reset");
            }
            if (isUser) {
                possibleArgs.add("leaders");
                possibleArgs.add("leaderboard");
                possibleArgs.add("options");
                possibleArgs.add("inspect");
                for (final Skill skill : SkillMap.getSkills()) {
                    if (!skill.isASBP()) {
                        possibleArgs.add(skill.getName().replace(" ", ""));
                        possibleArgs.add(skill.getTitle().replace(" ", ""));
                    }
                }
            }
        }
        else if (args.length == 2) {
            if (isAdmin && (this.match(args[0], "setlevel") || this.match(args[0], "setexperience") || this.match(args[0], "addlevel") || this.match(args[0], "addexperience") || this.match(args[0], "resetcooldowns") || this.match(args[0], "boostxp") || this.match(args[0], "reset"))) {
                possibleArgs.addAll(Arrays.asList(server.getOnlinePlayerNames()));
                if (this.match(args[0], "boostxp")) {
                    possibleArgs.add("all");
                }
            }
            if (isUser && this.match(args[0], "inspect")) {
                possibleArgs.addAll(Arrays.asList(server.getOnlinePlayerNames()));
            }
        }
        else if (args.length == 3) {
            if (isAdmin && (this.match(args[0], "setlevel") || this.match(args[0], "setexperience") || this.match(args[0], "addlevel") || this.match(args[0], "addexperience") || this.match(args[0], "boostxp"))) {
                possibleArgs.addAll(SkillMap.getSkillNames());
                if (this.match(args[0], "boostxp")) {
                    possibleArgs.add("all");
                }
            }
        }
        else if (args.length == 4) {
            if (isAdmin && this.match(args[0], "boostxp")) {
                possibleArgs.add("3600");
            }
        }
        else if (args.length == 5) {
            if (isAdmin && this.match(args[0], "boostxp")) {
                possibleArgs.add("1");
            }
        }
        else if (args.length == 6 && isAdmin && this.match(args[0], "boostxp")) {
            possibleArgs.add("0");
        }
        return (List<String>)getListOfStringsMatchingLastWord(args, (Collection)possibleArgs);
    }
    
    private boolean match(final String a, final String b) {
        return a.equalsIgnoreCase(b);
    }
    
    private void send(final ICommandSender recipient, final String message) {
        recipient.sendMessage((ITextComponent)new TextComponentString(message));
    }
    
    private void send(final ICommandSender recipient, final ITextComponent message) {
        recipient.sendMessage(message);
    }
    
    public List<String> getAliases() {
        return new ArrayList<String>(Arrays.asList(Config.settings().getCommandAliases()));
    }
}
