//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon;

import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.pixelmon.hooks.defaults.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaapi.file.api.*;

public class DefaultsPixelmon
{
    public static void addDefaults() {
        if (Defaults.addedDefaults) {
            Battling.create();
            Breeding.create();
            Catching.create();
            Training.create();
            Forging.create();
            AquaConfig.save("aquaskills", (Class)Skill.class);
        }
    }
}
