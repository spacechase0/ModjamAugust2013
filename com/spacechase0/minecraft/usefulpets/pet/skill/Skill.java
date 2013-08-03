package com.spacechase0.minecraft.usefulpets.pet.skill;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.spacechase0.minecraft.usefulpets.pet.food.FoodType;

public class Skill
{
	public Skill( int theId, String theName, float px, float py, ItemStack theIcon )
	{
		id = theId;
		name = theName;
		levelReq = 1;
		x = px;
		y = py;
		icon = theIcon;
		
		if ( skills.containsKey( id ) )
		{
			throw new IllegalArgumentException( "Skill " + id + " already exists." );
		}
		skills.put( id, this );
	}
	
	public Skill( int theId, String theName, float px, float py, ItemStack theIcon, int theLevelReq, int[] theSkillReqs )
	{
		this( theId, theName, px, py, theIcon );
		levelReq = theLevelReq;
		skillReqs = theSkillReqs;
	}
	
	public final int id;
	public final String name;
	public final float x;
	public final float y;
	public int levelReq;
	public int[] skillReqs;
	public final ItemStack icon;
	
	public static final Map< Integer, Skill > skills = new HashMap< Integer, Skill >();
	
	public static Skill forId( int id )
	{
		return skills.get( id );
	}
	
	public static Skill HUNGER = new FoodSkill( 0, "", FoodType.SPECIES, new ItemStack( Item.porkRaw ) );
	public static Skill HUNGER_OTHER = new FoodSkill( 1, "eatOther", FoodType.OTHER_SPECIES, new ItemStack( Item.fishRaw ) );
	public static Skill HUNGER_PLANTS = new FoodSkill( 2, "eatPlants", FoodType.PLANTS, new ItemStack( Item.carrot ) );
	public static Skill HUNGER_PROCESSED = new FoodSkill( 3, "eatProcessed", FoodType.PROCESSED, new ItemStack( Item.cookie ) );
	public static Skill HUNGER_GROSS = new FoodSkill( 4, "eatGross", FoodType.GROSS, new ItemStack( Item.rottenFlesh ) );
}
