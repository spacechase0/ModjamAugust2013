package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.client.ClientProxy;
import com.spacechase0.minecraft.usefulpets.client.gui.PetGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement( int id, EntityPlayer player, World world, int x, int y, int z )
	{
		// n/a
		
		return null;
	}

	@Override
	public Object getClientGuiElement( int id, EntityPlayer player, World world, int x, int y, int z )
	{
		if ( id == UsefulPets.PET_GUI_ID )
		{
			int entityId = x;
			return new PetGui( player, ClientProxy.pendingPetForGui );
		}
		
		return null;
	}
}
