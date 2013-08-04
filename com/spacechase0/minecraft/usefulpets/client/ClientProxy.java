package com.spacechase0.minecraft.usefulpets.client;

import com.spacechase0.minecraft.usefulpets.CommonProxy;
import com.spacechase0.minecraft.usefulpets.entity.PetEntity;
import com.spacechase0.minecraft.usefulpets.client.render.*;
import com.spacechase0.minecraft.usefulpets.entity.*;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler( PetEntity.class, new PetEntityRenderer() );
	}
}
