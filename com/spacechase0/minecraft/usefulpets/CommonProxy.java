package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.entity.PetEntity;

public class CommonProxy
{
	public void registerRenderers()
	{
	}
	
	public void setPendingPetForGui( PetEntity entity )
	{
		pendingPetForGui = entity;
	}
	
	public static PetEntity pendingPetForGui;
}
