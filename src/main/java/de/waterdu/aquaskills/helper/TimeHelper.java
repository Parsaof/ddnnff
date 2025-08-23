//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

public class TimeHelper
{
    private static int ticker;
    
    public static void tick() {
        ++TimeHelper.ticker;
        if (TimeHelper.ticker >= 20) {
            TimeHelper.ticker = 0;
        }
    }
    
    public static boolean isSecond() {
        return TimeHelper.ticker == 0;
    }
    
    static {
        TimeHelper.ticker = 0;
    }
}
