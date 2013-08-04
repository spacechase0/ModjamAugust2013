package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.client.ClientProxy;
import com.spacechase0.minecraft.usefulpets.client.gui.PetInventoryGui;
import com.spacechase0.minecraft.usefulpets.client.gui.PetSkillGui;
import com.spacechase0.minecraft.usefulpets.entity.PetEntity;
import com.spacechase0.minecraft.usefulpets.inventory.PetInventoryContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement( int id, EntityPlayer player, World world, int x, int y, int z )
	{
		if ( id == UsefulPets.PET_INVENTORY_GUI_ID )
		{
			PetEntity pet = ( PetEntity ) CommonProxy.pendingPetForGui;
			return new PetInventoryContainer( player.inventory, pet.getInventory(), pet );
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement( int id, EntityPlayer player, World world, int x, int y, int z )
	{
		if ( id == UsefulPets.PET_INVENTORY_GUI_ID )
		{
			PetEntity pet = ( PetEntity ) CommonProxy.pendingPetForGui;
			return new PetInventoryGui( player.inventory, pet.getInventory(), pet );
		}
		else if ( id == UsefulPets.PET_SKILLS_GUI_ID )
		{
			return new PetSkillGui( player, CommonProxy.pendingPetForGui );
		}
		
		return null;
	}
}
