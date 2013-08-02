package com.spacechase0.minecraft.usefulpets.pet;

import java.util.HashMap;
import java.util.Map;

public enum PetType
{
	CAT( "cat" ),
	DOG( "dog" );
	
	PetType( String theName )
	{
		name = theName;
	}
	
	public final String name;
	
	private static Map< String, PetType > types = new HashMap< String, PetType >();
	
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
