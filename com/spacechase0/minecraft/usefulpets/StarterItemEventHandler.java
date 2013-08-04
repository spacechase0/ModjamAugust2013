package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.ai.AvoidPetEntityAI;
import cpw.mods.fml.common.Mod.EventHandler;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class StarterItemEventHandler
{
	@ForgeSubscribe
	public void entityJoinedWorld( EntityJoinWorldEvent event )
	{
		if ( !( event.entity instanceof EntityPlayer ) )
		{
			return;
		}
		EntityPlayer player = ( EntityPlayer ) event.entity;
		
		NBTTagCompound data = player.getEntityData();
		if ( !data.hasKey( EntityPlayer.PERSISTED_NBT_TAG ) )
		{
			data.setTag( EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound() );
		}
		NBTTagCompound persist = data.getCompoundTag( EntityPlayer.PERSISTED_NBT_TAG );
		
		if ( !persist.hasKey( CHECK ) )
		{
			player.inventory.addItemStackToInventory( new ItemStack( UsefulPets.instance.wand ) );
			persist.setBoolean( CHECK, true );
		}
	}
	
	private static final String CHECK = "ReceivedPetWand";
}
