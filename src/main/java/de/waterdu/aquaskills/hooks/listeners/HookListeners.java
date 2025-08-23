//r

//Decompiled by Procyon!

package de.waterdu.aquaskills.hooks.listeners;

import net.minecraftforge.common.*;
import de.waterdu.aquaskills.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.hooks.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.brewing.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.*;
import de.waterdu.aquaskills.api.events.*;
import net.minecraft.util.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.*;

public class HookListeners
{
    public HookListeners() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        AquaSkills.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EldritchBlastEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player.getPlayerEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingSetAttackTargetEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (Entity)event.getTarget());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final SpellImpactEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.source.getPlayerEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AbilityUseEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player.getPlayerEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BlockEvent.BreakEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BlockEvent.PlaceEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerEvent.ItemCraftedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player, false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerEvent.ItemPickupEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingEvent.LivingJumpEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingHurtEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
        if (event.getSource().getTrueSource() != null) {
            HookRegistry.get().consume((Event)event, event.getSource().getTrueSource(), false);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingHealEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ProjectileImpactEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getRayTraceResult().entityHit, false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ProjectileImpactEvent.Arrow event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getRayTraceResult().entityHit, false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ProjectileImpactEvent.Fireball event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getRayTraceResult().entityHit, false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ProjectileImpactEvent.Throwable event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getRayTraceResult().entityHit, false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingDamageEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerEvent.ItemSmeltedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player, false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AnvilRepairEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AttackEntityEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ArrowNockEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ArrowLooseEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BonemealEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final CriticalHitEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final FillBucketEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ItemFishedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerBrewedPotionEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerPickupXpEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerSleepInBedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerWakeUpEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final UseHoeEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AnimalTameEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getTamer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BabyEntitySpawnEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getCausedByPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EnderTeleportEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingAttackEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
        if (event.getSource().getTrueSource() != null) {
            HookRegistry.get().consume((Event)event, event.getSource().getTrueSource());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PotionEvent.PotionAddedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PotionEvent.PotionApplicableEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PotionEvent.PotionExpiryEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingDeathEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EntityStruckByLightningEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingFallEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingKnockBackEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EntityTravelToDimensionEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PressAbilityEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player.getPlayerEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerInteractEvent.RightClickItem event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        if (event.getHand() == EnumHand.MAIN_HAND) {
            HookRegistry.get().consume((Event)event, event.getEntityPlayer());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerInteractEvent.LeftClickBlock event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        if (event.getHand() == EnumHand.MAIN_HAND) {
            HookRegistry.get().consume((Event)event, event.getEntityPlayer());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        if (event.getHand() == EnumHand.MAIN_HAND) {
            HookRegistry.get().consume((Event)event, event.getEntityPlayer());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerInteractEvent.EntityInteract event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        if (event.getHand() == EnumHand.MAIN_HAND) {
            HookRegistry.get().consume((Event)event, event.getEntityPlayer());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LivingEvent.LivingUpdateEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerInteractEvent.RightClickBlock event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        if (event.getHand() == EnumHand.MAIN_HAND) {
            HookRegistry.get().consume((Event)event, event.getEntityPlayer());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BlockEvent.HarvestDropsEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getHarvester(), false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BlockEvent.FarmlandTrampleEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntity());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ExplosionEvent.Start event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (Entity)event.getExplosion().getExplosivePlacedBy());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ExplosionEvent.Detonate event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (Entity)event.getExplosion().getExplosivePlacedBy());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final TickEvent.PlayerTickEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        if (event.side == Side.SERVER && event.phase == TickEvent.Phase.START) {
            HookRegistry.get().consume((Event)event, event.player);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ServerChatEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerFlyableFallEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getEntityPlayer());
    }
}
