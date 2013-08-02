package com.spacechase0.minecraft.usefulpets.entity;

import com.spacechase0.minecraft.usefulpets.ai.FollowOwnerAI;
import com.spacechase0.minecraft.usefulpets.ai.SitAI;
import com.spacechase0.minecraft.usefulpets.pet.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityOwnable;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class PetEntity extends EntityAnimal implements EntityOwnable
{
	// MINE! :P
	public PetEntity( World world )
	{
		super( world );
		
		setSize( type.sizeX, type.sizeY );
		
		getNavigator().setAvoidsWater( true );
        tasks.addTask( 1, new EntityAISwimming( this ) );
        tasks.addTask( 2, aiSit );
        this.tasks.addTask( 5, new FollowOwnerAI( this, 1.0D, 10.0F, 3.5F ) );
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
		setSize( type.sizeX, type.sizeY );
	}
	
	public boolean isSitting()
	{
		return sitting;
	}
	
	public void setSitting( boolean theSitting )
	{
		sitting = theSitting;
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
        return worldObj.getPlayerEntityByName( getOwnerName() );
	}
	
	// Variables
	// Pet info
	private String ownerName = "Player";
	private PetType type = PetType.CAT;
	private int level = 1;
	
	// Pet status
	private boolean sitting = false;
	
	// AI stuff
	private SitAI aiSit = new SitAI( this );
}
