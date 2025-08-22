//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.pixelmon.hooks.listeners;

import com.pixelmonmod.pixelmon.*;
import de.waterdu.aquaskills.*;
import com.pixelmonmod.pixelmon.storage.*;
import de.waterdu.aquaskills.api.events.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.hooks.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.pixelmonmod.pixelmon.api.events.*;
import com.pixelmonmod.pixelmon.api.events.spawning.*;
import com.pixelmonmod.pixelmon.api.events.pokemon.*;
import com.pixelmonmod.pixelmon.api.events.legendary.*;
import java.util.*;
import com.pixelmonmod.pixelmon.api.events.battles.*;
import net.minecraft.entity.*;

public class HookListenersPixelmon
{
    public HookListenersPixelmon() {
        Pixelmon.EVENT_BUS.register((Object)this);
        AquaSkills.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onGainXP(final GainExperienceEvent event) {
        final PlayerPartyStorage pps = Pixelmon.storageManager.getParty(event.player.getPlayerEntity());
        try {
            pps.getQuestData(false).receive("GAIN_SKILL_XP", new Object[] { event.skill, event.experience });
        }
        catch (Exception e) {
            AquaSkills.log.error("Failed to send data to AquaSkills quest objective (A)");
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void onGainLevel(final LevelUpEvent event) {
        final PlayerPartyStorage pps = Pixelmon.storageManager.getParty(event.player.getPlayerEntity());
        try {
            pps.getQuestData().receive("GAIN_SKILL_LEVEL", new Object[] { event.skill });
        }
        catch (Exception e) {
            AquaSkills.log.error("Failed to send data to AquaSkills quest objective (B)");
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final CaptureEvent.SuccessfulCapture event) {
        if (!TransientData.CAPTURED_POKEMON.contains(event.getPokemon().getUniqueID())) {
            if (event.isCancelable() && event.isCanceled()) {
                return;
            }
            HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
            TransientData.CAPTURED_POKEMON.add(event.getPokemon().getUniqueID());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final CaptureEvent.StartCapture event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final CaptureEvent.FailedCapture event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final SpawnEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.action.spawnLocation.cause);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ApricornEvent.PickApricorn event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ApricornEvent.ApricornPlanted event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ApricornEvent.ApricornWatered event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BerryEvent.BerryPlanted event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BerryEvent.PickBerry event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AggressionEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AnvilEvent.HammerDamage event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AnvilEvent.MaterialChanged event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AnvilEvent.BeatAnvil event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final AnvilEvent.FinishedSmith event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BeatTrainerEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BeatWildPixelmonEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BreedEvent.AddPokemon event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BreedEvent.CollectEgg event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.owner);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BreedEvent.BreedingTicks event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.owner);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BreedEvent.MakeEgg event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.owner);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BreedEvent.EnvironmentStrength event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.owner);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final DropEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EggHatchEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.pokemon.getOwnerPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EvolveEvent.PreEvolve event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EvolveEvent.PostEvolve event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ExperienceGainEvent event) {
        if (event.pokemon.getBattleController() == null || event.pokemon.getBattleController().playerNumber == 1) {
            HookRegistry.get().consume((Event)event, (EntityPlayer)event.pokemon.getPlayerOwner());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final FishingEvent.Cast event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final FishingEvent.Catch event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final FishingEvent.Reel event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final HealerEvent.Pre event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final HealerEvent.Post event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LevelUpEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player, false);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LostToTrainerEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final MegaEvolutionEvent.PostEvolve event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PassiveHealEvent.Pre event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PassiveHealEvent.Post event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PassivePoisonEvent.Pre event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PassivePoisonEvent.Post event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PixelmonFaintEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PixelmonDeletedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PixelmonKnockoutEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.pokemon.getPlayerOwner());
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.source.getPlayerOwner());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PixelmonReceivedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PixelmonSendOutEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PixelmonTradeEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player1);
        HookRegistry.get().consume((Event)event, event.player2);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PlayerActivateShrineEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PokerusEvent.Cured event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PokerusEvent.Spread.Pre event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.party.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PokerusEvent.Spread.Post event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.party.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final PokeLootClaimedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ThrowPokeballEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LegendarySpawnEvent.DoSpawn event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.action.spawnLocation.cause);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final LegendarySpawnEvent.ChoosePlayer event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.player);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final EVsGainedEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.pokemon.getOwnerPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ArceusEvent.AddPlate event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ArceusEvent.CreateFlute event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final ArceusEvent.PlayFlute event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final Gen2BellEvent.RollSuccessEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final Gen2BellEvent.SummonLegendary event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (EntityPlayer)event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final TimespaceEvent.PlaceChain event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final TimespaceEvent.PlaceOrb event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final TimespaceEvent.TakeChain event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final TimespaceEvent.TakeOrb event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final TimespaceEvent.Summon.Pre event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final TimespaceEvent.Summon.Post event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, event.getPlayer());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final BattleEndEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        if (event.getPlayers().size() == 1) {
            for (final EntityPlayer player : event.getPlayers()) {
                HookRegistry.get().consume((Event)event, player);
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void eventListener(final UseBattleItemEvent event) {
        if (event.isCancelable() && event.isCanceled()) {
            return;
        }
        HookRegistry.get().consume((Event)event, (Entity)event.participant.getEntity());
    }
}
