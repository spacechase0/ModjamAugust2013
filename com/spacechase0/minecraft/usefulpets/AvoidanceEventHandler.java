package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.ai.AvoidPetEntityAI;
import cpw.mods.fml.common.Mod.EventHandler;
import net.minecraft.entity.EntityCreature;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class AvoidanceEventHandler
{
	@ForgeSubscribe
	public void entityJoinedWorld( EntityJoinWorldEvent event )
	{
		if ( !( event.entity instanceof EntityCreature ) )
		{
			return;
		}
		EntityCreature creature = ( EntityCreature ) event.entity;
		
		creature.tasks.addTask( 3, new AvoidPetEntityAI( creature ) );
	}
}
