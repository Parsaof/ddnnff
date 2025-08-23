//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.helper;

import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;

public class TextHelper
{
    public static ITextComponent createCommandExecuteComponent(final String... args) {
        ITextComponent tcs = null;
        for (int i = 0; i < args.length; i += 2) {
            final TextComponentString newTcs = new TextComponentString(args[i]);
            if (!args[i + 1].isEmpty()) {
                final Style s = new Style();
                final ClickEvent ce = new ClickEvent(ClickEvent.Action.RUN_COMMAND, args[i + 1]);
                s.setClickEvent(ce);
                newTcs.setStyle(s);
            }
            if (tcs == null) {
                tcs = (ITextComponent)newTcs;
            }
            else {
                tcs = tcs.appendSibling((ITextComponent)newTcs);
            }
        }
        return tcs;
    }
    
    public static String format(final String in) {
        return in.replace("&", "�");
    }
}
