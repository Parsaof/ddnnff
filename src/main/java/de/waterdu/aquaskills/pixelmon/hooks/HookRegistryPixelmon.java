//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks;

import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.pixelmon.hooks.defaults.*;

public class HookRegistryPixelmon
{
    public static void registerHooks() {
        if (Config.settings().isUseDefaultSkillHooks()) {
            XPSourcesPixelmon.init();
            Training.init();
            Forging.init();
            Catching.init();
            Breeding.init();
            Battling.init();
        }
    }
}
