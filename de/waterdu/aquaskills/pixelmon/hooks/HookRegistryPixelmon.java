//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

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
