//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.skill.elements;

import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import de.waterdu.aquaskills.helper.*;
import net.minecraft.util.text.*;
import net.minecraft.command.*;
import net.minecraft.server.*;

public class Reward
{
    private long trueLevel;
    private String message;
    private String[] commands;
    
    public Reward(final long trueLevel, final String message, final String... commands) {
        this.trueLevel = trueLevel;
        this.message = message;
        this.commands = commands;
    }
    
    public void execute(final EntityPlayerMP player) {
        final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (this.message != null) {
            player.sendMessage((ITextComponent)new TextComponentString(Config.neutral("reward", new Object[] { this.message })));
        }
        for (final String command : this.commands) {
            server.getCommandManager().executeCommand((ICommandSender)server, command.replace("@p", player.getName()));
        }
    }
    
    public long getTrueLevel() {
        return this.trueLevel;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public String[] getCommands() {
        return this.commands;
    }
    
    public void setTrueLevel(final long trueLevel) {
        this.trueLevel = trueLevel;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public void setCommands(final String[] commands) {
        this.commands = commands;
    }
    
    public Reward() {
    }
}
