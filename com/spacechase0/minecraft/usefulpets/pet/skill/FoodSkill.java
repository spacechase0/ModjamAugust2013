package com.spacechase0.minecraft.usefulpets.pet.skill;

import com.spacechase0.minecraft.usefulpets.pet.food.FoodType;

public class FoodSkill extends Skill
{
	public FoodSkill( int theId, String theName, FoodType theType )
	{
		super( theId, ( theName.equals( "" ) ? "hunger" : ( "hunger." + theName ) ) );
		type = theType;
		
		if ( name.equals( "" ) )
		{
			mainId = id;
		}
		else
		{
			skillReqs = new int[] { mainId };
		}
	}
	
	public FoodSkill( int theId, String theName, int theLevelReq, FoodType theType )
	{
		this( theId, theName, theType );
		levelReq = theLevelReq;
	}
	
	public final FoodType type;
	private static int mainId;
}
