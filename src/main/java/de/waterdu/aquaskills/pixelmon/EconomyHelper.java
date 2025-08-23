//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon;

import net.minecraft.entity.player.*;
import com.pixelmonmod.pixelmon.api.economy.*;
import com.pixelmonmod.pixelmon.*;

public class EconomyHelper
{
    private static IPixelmonBankAccount getAccount(final EntityPlayerMP player) {
        return Pixelmon.moneyManager.getBankAccount(player).orElse(null);
    }
    
    public static int getBalance(final EntityPlayerMP player) {
        final IPixelmonBankAccount account = getAccount(player);
        return (account != null) ? account.getMoney() : 0;
    }
    
    public static boolean hasBalance(final EntityPlayerMP player, final int amount) {
        return getBalance(player) >= amount;
    }
    
    public static void addBalance(final EntityPlayerMP player, final int amount) {
        final IPixelmonBankAccount account = getAccount(player);
        if (account != null) {
            account.changeMoney(amount);
        }
    }
    
    public static boolean deductBalance(final EntityPlayerMP player, final int amount) {
        final IPixelmonBankAccount account = getAccount(player);
        if (account != null && account.getMoney() >= amount) {
            account.changeMoney(-amount);
            return true;
        }
        return false;
    }
}
