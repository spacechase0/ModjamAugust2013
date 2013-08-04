package com.spacechase0.minecraft.usefulpets.pet.skill;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
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
		return skills.get( id  );
	}
	
	public static Skill HUNGER = new FoodSkill( 0, "", FoodType.SPECIES, new ItemStack( Item.porkRaw ) );
	public static Skill HUNGER_OTHER = new FoodSkill( 1, "eatOther", FoodType.OTHER_SPECIES, new ItemStack( Item.fishRaw ) );
	public static Skill HUNGER_PLANTS = new FoodSkill( 2, "eatPlants", FoodType.PLANTS, new ItemStack( Item.carrot ) );
	public static Skill HUNGER_PROCESSED = new FoodSkill( 3, "eatProcessed", 5, FoodType.PROCESSED, new ItemStack( Item.cookie ) );
	public static Skill HUNGER_GROSS = new FoodSkill( 4, "eatGross", 10, FoodType.GROSS, new ItemStack( Item.rottenFlesh ) );

	public static Skill COMBAT = new AttackSkill( 5, "", 4, new ItemStack( Item.swordWood ) );
	public static Skill COMBAT_UPGRADE1 = new AttackSkill( 6, "upgrade1", 1, new ItemStack( Item.swordStone ) );
	public static Skill COMBAT_UPGRADE2 = new AttackSkill( 7, "upgrade2", 5, 1, new ItemStack( Item.swordIron ) );
	public static Skill COMBAT_UPGRADE3 = new AttackSkill( 8, "upgrade3", 10, 1, new ItemStack( Item.swordDiamond ) );

	public static Skill REPELLANT = new RepellantSkill( 9, "", new ItemStack( Item.skull, 1, 4 ) );
	public static Skill REPELLANT_RADIUS = new RepellantSkill( 10, "larger", 10, 4.f, new ItemStack( Item.stick ) );
	public static Skill REPELLANT_UNDEAD = new RepellantSkill( 11, "undead", 6, EnumCreatureAttribute.UNDEAD, new ItemStack( Item.skull, 1, 0 ) );
	public static Skill REPELLANT_SPIDERS = new RepellantSkill( 12, "spiders", 3, EnumCreatureAttribute.ARTHROPOD, new ItemStack( Item.spiderEye ) );

	public static Skill INVENTORY = new Skill( 13, "inventory", 4.5f, 0, new ItemStack( Block.chest ), 1, null );
	public static Skill INVENTORY_UPGRADE1 = new Skill( 14, "inventory.upgrade1", 5.5f, 1.5f, new ItemStack( Block.chest ), 7, new int[] { 13 } );
	public static Skill INVENTORY_UPGRADE2 = new Skill( 15, "inventory.upgrade2", 5.5f, 2.5f, new ItemStack( Block.chest ), 12, new int[] { 14 } );
	public static Skill INVENTORY_FEEDING = new Skill( 16, "inventory.selfSufficient", 4.5f, -1.5f, new ItemStack( Item.pumpkinPie ), 7, new int[] { 0, 13 } );
	public static Skill INVENTORY_ARMOR = new Skill( 17, "inventory.armor", 3.5f, 1.5f, new ItemStack( Item.field_111215_ce ), 5, new int[] { 5, 13 } );
	public static Skill INVENTORY_WEAPON = new Skill( 18, "inventory.weapon", 3.5f, 2.5f, new ItemStack( Item.swordIron ), 5, new int[] { 5, 13 } ); // TODO: My item

	public static Skill DEFENSE = new DefenseSkill( 19, "", 3, 0.1f, new ItemStack( Item.plateLeather ) );
	public static Skill DEFENSE_UPGRADE1 = new DefenseSkill( 20, "upgrade1", 5, 0.15f, new ItemStack( Item.plateIron ) );
	public static Skill DEFENSE_UPGRADE2 = new DefenseSkill( 21, "upgrade2", 10, 0.25f, new ItemStack( Item.plateDiamond ) );
	public static Skill DEFENSE_FIRE = new DefenseSkill( 22, "fire", 7, new String[] { "inFire", "onFire", "lava" }, new ItemStack( Block.fire ) );
	public static Skill DEFENSE_BREATHLESS = new DefenseSkill( 23, "breathless", 5, new String[] { "inWall", "drown" }, new ItemStack( Block.waterStill ) );
	public static Skill DEFENSE_FEATHERFALL = new DefenseSkill( 24, "featherFall", 3, new String[] { "fall" }, new ItemStack( Item.feather ) );
}
