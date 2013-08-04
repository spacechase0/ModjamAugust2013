package com.spacechase0.minecraft.usefulpets;

import com.spacechase0.minecraft.usefulpets.entity.PetEntity;

import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{
	public void registerRenderers()
	{
	}
	
	public PetEntity getPendingPetForGui( Side side )
	{
		return pendingGui[ side.ordinal() ];
	}
	
	public void setPendingPetForGui( Side side, PetEntity entity )
	{
		pendingGui[ side.ordinal() ] = entity;
	}
	
	private PetEntity[] pendingGui = new PetEntity[ 2 ];
}
