//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills;

import de.waterdu.aquaskills.spells.*;
import de.waterdu.aquaskills.event.*;
import de.waterdu.aquaskills.hooks.listeners.*;
import net.minecraftforge.fml.common.eventhandler.*;
import de.waterdu.aquaapi.file.api.*;
import de.waterdu.aquaskills.file.boosts.*;
import de.waterdu.aquaskills.battlepass.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.hooks.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.async.*;
import de.waterdu.aquaskills.skill.*;
import de.waterdu.aquaskills.leaderboard.*;
import de.waterdu.aquaapi.ui.api.*;
import net.minecraft.command.*;
import de.waterdu.aquaskills.command.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.*;
import org.apache.logging.log4j.*;

@Mod(name = "AquaSkills", version = "1.12.2-3.1.1-universal", acceptableRemoteVersions = "*", modid = "aquaskills", dependencies = "after:pixelmon")
public class AquaSkills
{
    public static final String MODVERSION = "1.12.2-3.1.1-universal";
    public static final String MODID = "aquaskills";
    public static final String MODNAME = "AquaSkills";
    @Mod.Instance("aquaskills")
    public static AquaSkills instance;
    public static Events events;
    public static SpellEvents spellEvents;
    public static LogEvents logEvents;
    public static ASBPEvents asbpEvents;
    public static HookListeners listeners;
    public static BlockLog blockLog;
    public static Logger log;
    public static final EventBus EVENT_BUS;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) throws Exception {
        AquaSkills.log.info("AquaSkills starting initialization.");
        this.addMetadata(event);
        AquaConfig.preRegistration(event, "aquaskills");
        AquaConfig.register("aquaskills", "config.json", (Class)Settings.class, (IConfiguration)new Settings());
        AquaConfig.register("aquaskills", "asbp.json", (Class)ASBPSettings.class, (IConfiguration)new ASBPSettings());
        AquaConfig.register("aquaskills", "lang.json", (Class)Messages.class, (IConfiguration)new Messages());
        AquaConfig.register("aquaskills", "abilitylang.json", (Class)SkillMessages.class, (IConfiguration)new SkillMessages());
        AquaConfig.register("aquaskills", "boosts.json", (Class)XPBoosts.class, (IConfiguration)new XPBoosts());
        AquaConfig.register("aquaskills", "players", (Class)Player.class, (IConfiguration)new Player());
        AquaConfig.register("aquaskills", "asbpplayers", (Class)ASBPPlayer.class, (IConfiguration)new ASBPPlayer());
        AquaConfig.register("aquaskills", "battlepass.json", (Class)ASBPRewards.class, (IConfiguration)new ASBPRewards());
        AquaConfig.register("aquaskills", "skills", (Class)Skill.class, (IConfiguration)new Skill());
        AquaConfig.register("aquaskills", "current.log", (Class)Log.class, (IConfiguration)new Log());
        AquaConfig.register("aquaskills", "ui.json", (Class)UI.class, (IConfiguration)new UI());
        AquaConfig.postRegistration("aquaskills");
        AquaConfig.whenNotBusy("aquaskills", () -> {
            Defaults.addDefaults();
            ASLogger.get().init(event);
            HookRegistry.registerHooks();
            ThreadPool.setSize(Math.max(1, Config.settings().getThreadPoolSize()));
            return;
        });
        try {
            final Class<?> clazz = Class.forName("de.waterdu.aquaskills.pixelmon.AquaSkillsPixelmon");
            clazz.getMethod("init", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception e) {
            AquaSkills.log.info("Couldn't find Pixelmon addon, skipping.");
        }
        AquaConfig.whenNotBusy("aquaskills", () -> {
            SkillMap.init();
            Leaderboard.get().init();
            return;
        });
        AquaUI.register();
        AquaSkills.events = new Events();
        AquaSkills.spellEvents = new SpellEvents();
        AquaSkills.logEvents = new LogEvents();
        AquaSkills.asbpEvents = new ASBPEvents();
        AquaSkills.listeners = new HookListeners();
        AquaSkills.blockLog = new BlockLog();
    }
    
    @Mod.EventHandler
    public void serverLoad(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new Command());
        if (Config.settingsASBP().isUse()) {
            event.registerServerCommand((ICommand)new BattlePassCommand());
        }
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        AquaSkills.log.info("AquaSkills initialized successfully.");
    }
    
    @Mod.EventHandler
    public void onShutdown(final FMLServerStoppingEvent event) {
        ASLogger.get().export();
    }
    
    private void addMetadata(final FMLPreInitializationEvent event) {
        final ModMetadata m = event.getModMetadata();
        m.autogenerated = false;
        m.modId = "aquaskills";
        m.version = "1.12.2-3.1.1-universal";
        m.name = "AquaSkills";
        m.url = "waterdu.de";
        m.description = "";
        m.credits = "Waterdude";
    }
    
    static {
        AquaSkills.log = LogManager.getLogger("AquaSkills");
        EVENT_BUS = new EventBus();
    }
}
