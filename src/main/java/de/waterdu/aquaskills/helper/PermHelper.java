//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.server.permission.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.server.*;
import net.minecraft.util.text.*;
import java.util.*;

public class PermHelper
{
    public static boolean isOp(final EntityPlayerMP player) {
        return FMLCommonHandler.instance().getMinecraftServerInstance() != null && FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList() != null && FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry((Object)player.getGameProfile()) != null;
    }
    
    public static boolean hasPermission(final String perm, final EntityPlayerMP player) {
        return PermissionAPI.hasPermission((EntityPlayer)player, perm) || player.canUseCommand(4, perm) || isOp(player);
    }
    
    public static boolean canUse(final String perm, final ICommandSender sender) {
        return sender instanceof MinecraftServer || (sender instanceof EntityPlayerMP && hasPermission(perm, (EntityPlayerMP)sender));
    }
    
    public static void sendToAdmins(final MinecraftServer server, final String message) {
        for (final EntityPlayerMP player : server.getPlayerList().getPlayers()) {
            if (canUseCommand((ICommandSender)player, "misc", true)) {
                player.sendMessage((ITextComponent)new TextComponentString(TextFormatting.GOLD + "ADMIN " + message));
            }
        }
    }
    
    public static boolean canUseCommand(final ICommandSender sender, final String command, final boolean isAdmin) {
        return canUse(getPermNode(command, isAdmin), sender);
    }
    
    public static boolean canUseCommand(final ICommandSender sender, final String command, final String[] qualifers, final boolean isAdmin) {
        return canUse(getPermNode(command, qualifers, isAdmin), sender);
    }
    
    public static String getPermNode(final String command, final boolean isAdmin) {
        return getPermNode(command, null, isAdmin);
    }
    
    public static String getPermNode(final String command, final String[] qualifers, final boolean isAdmin) {
        final StringBuilder builder = new StringBuilder();
        builder.append("aquaskills.");
        builder.append(isAdmin ? "admin." : "user.");
        builder.append(command);
        if (qualifers != null) {
            for (final String qualifer : qualifers) {
                builder.append(".");
                builder.append(qualifer);
            }
        }
        return builder.toString();
    }
}
