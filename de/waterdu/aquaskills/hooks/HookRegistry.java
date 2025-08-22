//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaapi.file.api.*;
import net.minecraftforge.fml.common.*;
import de.waterdu.aquaskills.skill.*;
import de.waterdu.aquaskills.file.*;
import de.waterdu.aquaskills.async.*;
import net.minecraft.server.*;
import java.util.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.hooks.defaults.*;
import de.waterdu.aquaskills.api.events.*;

public class HookRegistry
{
    private static final HookRegistry instance;
    private final HashMap<String, ArrayList<MethodHook<? extends Event>>> methodHooks;
    private final HashMap<String, ArrayList<String>> eventMapping;
    
    private HookRegistry() {
        this.methodHooks = new HashMap<String, ArrayList<MethodHook<? extends Event>>>();
        this.eventMapping = new HashMap<String, ArrayList<String>>();
    }
    
    public static HookRegistry get() {
        return HookRegistry.instance;
    }
    
    public void registerMethodHook(final String name, final MethodHook<? extends Event> hook) {
        final String eventName = hook.getType().getName();
        if (!this.methodHooks.containsKey(name)) {
            this.methodHooks.put(name, new ArrayList<MethodHook<? extends Event>>());
        }
        if (!this.eventMapping.containsKey(eventName)) {
            this.eventMapping.put(eventName, new ArrayList<String>());
        }
        final ArrayList<MethodHook<? extends Event>> methodHooks = this.methodHooks.get(name);
        final ArrayList<String> eventMapping = this.eventMapping.get(eventName);
        if (methodHooks != null && eventMapping != null) {
            methodHooks.add(hook);
            eventMapping.add(name);
        }
    }
    
    public void consume(final Event event, final Entity entity) {
        this.consume(event, entity, true);
    }
    
    public void consume(final Event event, final EntityPlayer player) {
        this.consume(event, player, true);
    }
    
    public void consume(final Event event, final UUID uuid) {
        this.consume(event, uuid, true);
    }
    
    public void consume(final Event event, final Player player) {
        this.consume(event, player, true);
    }
    
    public void consume(final Event event, final Entity entity, final boolean async) {
        if (entity instanceof EntityPlayer) {
            this.consume(event, entity.getUniqueID(), async);
        }
    }
    
    public void consume(final Event event, final EntityPlayer player, final boolean async) {
        if (player instanceof EntityPlayerMP) {
            this.consume(event, player.getUniqueID(), async);
        }
    }
    
    public void consume(final Event event, final UUID uuid, final boolean async) {
        this.consume(event, (Player)AquaConfig.get("aquaskills", (Class)Player.class, uuid), async);
    }
    
    public void consume(final Event event, final Player player, final boolean async) {
        if (player != null) {
            final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            final ArrayList<String> hooks;
            final Iterator<Skill> iterator;
            Skill skill;
            final Iterator<String> iterator2;
            String hook;
            final MinecraftServer minecraftServer;
            final Runnable runnable = () -> {
                hooks = this.eventMapping.get(event.getClass().getName());
                if (hooks != null) {
                    SkillMap.getSkills().iterator();
                    while (iterator.hasNext()) {
                        skill = iterator.next();
                        hooks.iterator();
                        while (iterator2.hasNext()) {
                            hook = iterator2.next();
                            skill.getAndExecute(event, player, minecraftServer, new String[] { hook });
                        }
                    }
                }
                return;
            };
            if (async) {
                ThreadPool.submit(runnable);
            }
            else {
                runnable.run();
            }
        }
    }
    
    public void execute(final MinecraftServer server, final String method, final Event event, final MethodData data) {
        if (data.player.getPlayerEntity() == null) {
            return;
        }
        final ArrayList<MethodHook<? extends Event>> hooks = this.methodHooks.get(method);
        if (hooks != null) {
            for (final MethodHook<? extends Event> hook : hooks) {
                if (hook != null && (data.hookable instanceof XPSource || data.getCooldownSafely().isEnabled()) && !AquaSkills.EVENT_BUS.post((Event)new HookEvent.Pre(data))) {
                    server.addScheduledTask(() -> {
                        hook.consume(event, data);
                        AquaSkills.EVENT_BUS.post((Event)new HookEvent.Post(data));
                    });
                }
            }
        }
    }
    
    public static void registerHooks() {
        if (Config.settings().isUseDefaultSkillHooks()) {
            XPSources.init();
            Mining.init();
            Excavation.init();
            Herbalism.init();
            Woodcutting.init();
            Fishing.init();
            MartialArts.init();
            Swordsmanship.init();
            Axemanship.init();
            Archery.init();
            Acrobatics.init();
            Salvaging.init();
            Crafting.init();
            Smelting.init();
            Wizard.init();
            Cleric.init();
            Necromancy.init();
            Paladin.init();
            Sorcerer.init();
            Warlock.init();
            AnimalHusbandry.init();
        }
    }
    
    public static void reset() {
        get().methodHooks.clear();
        get().eventMapping.clear();
        registerHooks();
        try {
            final Class<?> clazz = Class.forName("de.waterdu.aquaskills.pixelmon.hooks.HookRegistryPixelmon");
            clazz.getMethod("registerHooks", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception e) {
            AquaSkills.log.info("Couldn't find Pixelmon addon, skipping.");
        }
        AquaSkills.EVENT_BUS.post((Event)new HookRegistryResetEvent());
    }
    
    static {
        instance = new HookRegistry();
    }
}
