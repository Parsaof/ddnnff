//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.file;

import de.waterdu.aquaapi.file.api.*;
import java.util.*;

public class Log implements IConfiguration
{
    private ArrayList<String> log;
    
    public ArrayList<String> getLog() {
        return this.log;
    }
    
    public void setLog(final ArrayList<String> log) {
        this.log = log;
    }
    
    public Log() {
        this.log = new ArrayList<String>();
    }
}
