package com.spacechase0.minecraft.usefulpets.pet;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;

public enum PetType
{
	CAT( "cat", EntityOcelot.class ),
	DOG( "dog", EntityWolf.class );
	
	PetType( String theName, Class toConvertFrom )
	{
		name = theName;
		convertFrom = toConvertFrom;
	}
	
	public final String name;
	public final Class convertFrom;
	public final float sizeX = 0.6f;
	public final float sizeY = 0.8f;
	
	public static final Map< String, PetType > types = new HashMap< String, PetType >();
	
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
