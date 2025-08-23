//r

//Decompiled by Procyon!

package de.waterdu.aquaskills;

import java.text.*;
import net.minecraftforge.fml.common.event.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaapi.file.api.*;
import java.io.*;
import java.util.zip.*;
import java.util.*;

public class ASLogger
{
    private static final ASLogger INSTANCE;
    private static final SimpleDateFormat sdfWide;
    private static final SimpleDateFormat sdfNarrow;
    private File zipFolder;
    private boolean logToConsole;
    
    private ASLogger() {
    }
    
    public static ASLogger get() {
        return ASLogger.INSTANCE;
    }
    
    public void init(final FMLPreInitializationEvent event) {
        final File root = new File(event.getModConfigurationDirectory().getParent());
        (this.zipFolder = new File(root + "/logs/aquaskills")).mkdir();
        this.logToConsole = Config.settings().isLogToConsole();
        this.export();
    }
    
    public void reload() {
        this.logToConsole = Config.settings().isLogToConsole();
        this.export();
    }
    
    public static void log(final Severity severity, final String message) {
        final Log log = (Log)AquaConfig.get("aquaskills", (Class)Log.class);
        final String compiledMessage = "[" + severity.name() + "] " + ASLogger.sdfWide.format(new Date()) + " " + message;
        log.getLog().add(compiledMessage);
        if (get().logToConsole) {
            severity.log(message);
        }
    }
    
    public void export() {
        final Log log = (Log)AquaConfig.get("aquaskills", (Class)Log.class);
        if (!log.getLog().isEmpty()) {
            try {
                final String datetime = ASLogger.sdfNarrow.format(new Date());
                final String name = datetime + ".zip";
                final File newFile = new File(this.zipFolder, name);
                final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newFile));
                final ZipEntry ze = new ZipEntry(datetime + ".txt");
                zos.putNextEntry(ze);
                final StringBuilder builder = new StringBuilder();
                for (final String line : log.getLog()) {
                    if (builder.length() > 0) {
                        builder.append("\r\n");
                    }
                    builder.append(line);
                }
                final byte[] data = builder.toString().getBytes();
                zos.write(data, 0, data.length);
                zos.closeEntry();
                zos.close();
                log.getLog().clear();
                AquaConfig.save("aquaskills", (Class)Log.class);
            }
            catch (Exception e) {
                AquaSkills.log.error("Failed to write log!");
                e.printStackTrace();
            }
        }
    }
    
    static {
        INSTANCE = new ASLogger();
        sdfWide = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdfNarrow = new SimpleDateFormat("yyyy-MM-dd HHmmss");
    }
    
    public enum Severity
    {
        INFO, 
        WARN, 
        ERROR;
        
        public void log(final String message) {
            switch (this) {
                case INFO: {
                    AquaSkills.log.info(message);
                    break;
                }
                case WARN: {
                    AquaSkills.log.warn(message);
                    break;
                }
                case ERROR: {
                    AquaSkills.log.error(message);
                    break;
                }
            }
        }
    }
}
