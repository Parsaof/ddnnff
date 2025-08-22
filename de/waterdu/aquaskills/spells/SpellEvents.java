//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\thoma\OneDrive\Documents\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package de.waterdu.aquaskills.spells;

import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.player.*;
import de.waterdu.aquaskills.helper.*;
import de.waterdu.aquaskills.skill.elements.*;
import de.waterdu.aquaskills.event.internal.*;
import de.waterdu.aquaskills.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.init.*;
import net.minecraft.util.text.*;
import de.waterdu.aquaskills.file.*;
import net.minecraft.util.*;
import de.waterdu.aquaskills.player.*;

public class SpellEvents
{
    public SpellEvents() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (event.getEntity().getEntityData().hasKey(NbtKeys.TRANSIENT)) {
            event.setCanceled(true);
            event.getEntity().setDead();
        }
    }
    
    @SubscribeEvent
    public void onServerTick(final TickEvent.ServerTickEvent event) {
        if (event.side == Side.SERVER && event.phase == TickEvent.Phase.START) {
            SpellEngine.get().onTick();
        }
    }
    
    @SubscribeEvent
    public void onRightClickItem(final PlayerInteractEvent.RightClickItem event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP && event.getHand() == EnumHand.MAIN_HAND) {
            final EntityPlayerMP player;
            final Player p;
            final Skill skill;
            final Ability ability;
            final Ability ability2;
            boolean locked;
            final Player player2;
            final EntityPlayerMP entityPlayerMP;
            final Player player3;
            final Skill skill2;
            final Ability ability3;
            BoundItemEvent e;
            final ITextComponent textComponent;
            final EntityPlayerMP entityPlayerMP2;
            ItemHelper.getBoundAbility(event.getItemStack()).ifPresent(tuple -> {
                player = (EntityPlayerMP)event.getEntityPlayer();
                p = Config.player(player);
                skill = (Skill)tuple.getFirst();
                ability = (Ability)tuple.getSecond();
                if (ability.isBindable()) {
                    p.getXP(skill).ifPresent(xp -> {
                        locked = (xp.getTrueLevel() < ability2.getLevelRequirement());
                        if (!locked) {
                            player2.getCooldown(ability2).ifPresent(cd -> {
                                if (!cd.isOnCooldown()) {
                                    e = new BoundItemEvent(entityPlayerMP, player3, event.getItemStack(), skill2, ability3);
                                    if (!AquaSkills.EVENT_BUS.post((Event)e)) {
                                        skill2.getAndExecute((Event)e, player3, FMLCommonHandler.instance().getMinecraftServerInstance(), new String[] { ability3.getHook() });
                                    }
                                }
                                else {
                                    new TextComponentString(Config.neutral("boundOnCooldown", new Object[] { ability3.getDisplayInfo().getName(), cd.getCooldownString() }));
                                    entityPlayerMP.sendMessage(textComponent);
                                    player3.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.MASTER, 1.0f, 1.0f);
                                }
                            });
                        }
                        else {
                            entityPlayerMP2.sendMessage((ITextComponent)new TextComponentString(Config.neutral("tooHighLevel", new Object[0])));
                        }
                    });
                }
                else {
                    player.sendMessage((ITextComponent)new TextComponentString(Config.neutral("noLongerBound", new Object[0])));
                }
            });
        }
    }
}
