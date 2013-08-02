package com.spacechase0.minecraft.usefulpets.pet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;

public enum PetType
{
	CAT( "cat", EntityOcelot.class, "hitt" ),
	DOG( "dog", EntityWolf.class, "hurt" );
	
	PetType( String theName, Class toConvertFrom, String theHurtSound )
	{
		name = theName;
		convertFrom = toConvertFrom;
		hurtSound = theHurtSound;
	}
	
	public String getLivingSound()
	{
		if ( this.equals( CAT ) )
		{
			return ( ( rand.nextInt( 4 ) == 0 ) ? "mob.cat.purreow" : "mob.cat.meow" );
		}
		else if ( this.equals( DOG ) )
		{
			return "mob.wolf.bark";
		}
		
		return null;
	}
	
	public String getHurtSound()
	{
		return "mob." + name + "." + hurtSound;
	}
	
	public final String name;
	public final Class convertFrom;
	public final float sizeX = 0.6f;
	public final float sizeY = 0.8f;
	
	private final String hurtSound;
	
	public static final Map< String, PetType > types = new HashMap< String, PetType >();
	private static final Random rand = new Random();
	
	public static PetType forName( String name )
	{
		return types.get( name );
	}
	
	static
	{
		types.put( CAT.name, CAT );
		types.put( DOG.name, DOG );
	}
}
