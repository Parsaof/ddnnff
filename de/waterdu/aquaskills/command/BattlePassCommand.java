//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.command;

import de.waterdu.aquaskills.helper.*;
import net.minecraft.command.*;
import net.minecraft.server.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.battlepass.ui.*;
import de.waterdu.aquaapi.ui.api.*;
import net.minecraft.util.text.*;
import java.util.*;

public class BattlePassCommand extends CommandBase
{
    public String getName() {
        return Config.settingsASBP().getCommandName();
    }
    
    public String getUsage(final ICommandSender sender) {
        return "/" + Config.settingsASBP().getCommandName();
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return true;
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) {
        final String neutral = AquaConfig.format("aquaskills", (Class)Messages.class, "prefixNeutral", new Object[0]);
        final boolean isPlayer = sender instanceof EntityPlayerMP;
        final EntityPlayerMP player = isPlayer ? ((EntityPlayerMP)sender) : null;
        if (args.length == 0 && player != null) {
            AquaUI.openUI(player, (IPage)new ASBPPage(player));
        }
        else {
            this.send(sender, neutral + "1.12.2-3.1.1-universal");
        }
    }
    
    private void send(final ICommandSender recipient, final String message) {
        recipient.sendMessage((ITextComponent)new TextComponentString(message));
    }
    
    public List<String> getAliases() {
        return new ArrayList<String>(Arrays.asList(Config.settingsASBP().getCommandAliases()));
    }
}
