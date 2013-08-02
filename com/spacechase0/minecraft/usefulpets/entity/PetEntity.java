package com.spacechase0.minecraft.usefulpets.entity;

import com.spacechase0.minecraft.usefulpets.pet.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityOwnable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class PetEntity extends EntityAnimal implements EntityOwnable
{
	// MINE! :P
	public PetEntity( World world )
	{
		super( world );
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setLevel( int theLevel )
	{
		level = theLevel;
	}
	
	public PetType getPetType()
	{
		return type;
	}
	
	public void setPetType( PetType theType )
	{
		type = theType;
	}

	// EntityAnimal
	@Override
	public EntityAgeable createChild( EntityAgeable entity )
	{
		// TODO
		return null;
	}
	
	// EntityOwnable (+some)
	@Override
	public String getOwnerName()
	{
		return ownerName;
	}
	
	public void setOwnerName( String theOwnerName )
	{
		ownerName = theOwnerName;
	}

	@Override
	public Entity getOwner()
	{
		return null;
	}
	
	// Variables
	private String ownerName = "Player";
	private PetType type = PetType.CAT;
	private int level = 1;
}
