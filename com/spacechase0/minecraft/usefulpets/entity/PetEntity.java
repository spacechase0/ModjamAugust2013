package com.spacechase0.minecraft.usefulpets.entity;

import java.util.ArrayList;
import java.util.List;

import com.spacechase0.minecraft.usefulpets.UsefulPets;
import com.spacechase0.minecraft.usefulpets.ai.FollowOwnerAI;
import com.spacechase0.minecraft.usefulpets.ai.OwnerHurtTargetAI;
import com.spacechase0.minecraft.usefulpets.ai.SitAI;
import com.spacechase0.minecraft.usefulpets.ai.TargetHurtOwnerAI;
import com.spacechase0.minecraft.usefulpets.inventory.PetInventory;
import com.spacechase0.minecraft.usefulpets.item.ClawItem;
import com.spacechase0.minecraft.usefulpets.pet.*;
import com.spacechase0.minecraft.usefulpets.pet.skill.AttackSkill;
import com.spacechase0.minecraft.usefulpets.pet.skill.DefenseSkill;
import com.spacechase0.minecraft.usefulpets.pet.skill.FoodSkill;
import com.spacechase0.minecraft.usefulpets.pet.skill.Skill;
import com.spacechase0.minecraft.usefulpets.pet.skill.SpeedSkill;

import cpw.mods.fml.relauncher.Side;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
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
        tasks.addTask( 3, new EntityAILeapAtTarget( this, 0.4F ) );
        tasks.addTask( 4, new EntityAIAttackOnCollide( this, 1.0D, true ) );
        tasks.addTask( 5, new FollowOwnerAI( this, 1.0D, 10.0F, 3.5F ) );
        tasks.addTask( 7, new EntityAIWander(this, 1.0D));
        tasks.addTask( 9, new EntityAIWatchClosest( this, EntityPlayer.class, 9.0F ) );
        targetTasks.addTask(1, new TargetHurtOwnerAI( this ) );
        targetTasks.addTask(2, new OwnerHurtTargetAI( this ) );
        targetTasks.addTask(3, new EntityAIHurtByTarget( this, true ) );
	}
	
	public int getLevel()
	{
		return dataWatcher.getWatchableObjectInt( DATA_LEVEL );
	}
	
	public void setLevel( int level )
	{
		dataWatcher.updateObject( DATA_LEVEL, level );
	}
	
	public void levelUp()
	{
		if ( getLevel() >= Level.MAX_LEVEL )
		{
			return;
		}
		
		setLevel( getLevel() + 1 );
		setFreeSkillPoints( getFreeSkillPoints() + 1 );
	}
	
	public int getFreeSkillPoints()
	{
		return dataWatcher.getWatchableObjectInt( DATA_FREE_POINTS );
	}
	
	public void setFreeSkillPoints( int points )
	{
		dataWatcher.updateObject( DATA_FREE_POINTS, points );
	}
	
	public boolean hasSkill( int id )
	{
		//System.out.println("has: "+id+" "+skills.contains(id));
		return skills.contains( id );
	}
	
	public boolean hasSkillRequirements( int skillId )
	{
		Skill skill = Skill.forId( skillId );
		if ( skill.levelReq > getLevel() )
		{
			return false;
		}
		
		if ( skill.skillReqs == null )
		{
			return true;
		}
		
		for ( int is = 0; is < skill.skillReqs.length; ++is )
		{
			Skill parent = Skill.forId( skill.skillReqs[ is ] );
			if ( !hasSkill( parent.id ) )
			{
				return false;
			}
		}
		
		return true;
	}
	
	public void addSkill( int id )
	{
		if ( hasSkill( id ) || getFreeSkillPoints() < 1 || !hasSkillRequirements( id ) )
		{
			return;
		}
		
		skills.add( id );
		setFreeSkillPoints( getFreeSkillPoints() - 1 );
		dataWatcher.updateObject( DATA_SKILLS, getSkillsStack() );
	}
	
	public void removeSkill( int id )
	{
		if ( hasSkill( id ) && !type.defaultSkills.contains( id ) )
		{
			for ( Skill skill : Skill.skills.values() )
			{
				for ( int i = 0; skill.skillReqs != null && i < skill.skillReqs.length; ++i )
				{
					int reqId = skill.skillReqs[ i ];
					if ( reqId == id )
					{
						removeSkill( skill.id );
						break;
					}
				}
			}
			
			skills.remove( new Integer( id ) );
			setFreeSkillPoints( getFreeSkillPoints() + 1 );
		}
		dataWatcher.updateObject( DATA_SKILLS, getSkillsStack() );
	}
	
	private ItemStack getSkillsStack()
	{
		if ( skills == null || skills.size() == 0 )
		{
			return new ItemStack( Item.arrow );
		}
		
		int[] theSkills = new int[ skills.size() ];
		for ( int i = 0; i < skills.size(); ++i )
		{
			theSkills[ i ] = skills.get( i );
		}
		
		ItemStack stack = new ItemStack( Item.stick );
		stack.setTagCompound( new NBTTagCompound() );
		stack.getTagCompound().setIntArray( "Skills", theSkills );
		
		return stack;
	}
	
	private void updateSpeed()
	{
		AttributeInstance attr = func_110148_a( SharedMonsterAttributes.field_111263_d );
		
		float percent = 1.f;
		for ( Skill skill : Skill.skills.values() )
		{
			if ( !hasSkill( skill.id ) || !( skill instanceof SpeedSkill ) )
			{
				continue;
			}
			SpeedSkill speed = ( SpeedSkill ) skill;
			percent += speed.percent;
		}
		
		attr.func_111128_a( 0.3 * percent );
	}
	
	// TODO: Test me
	public void resetSkills()
	{
		for ( int id : type.defaultSkills )
		{
			skills.remove( id );
		}
		
		setFreeSkillPoints( getFreeSkillPoints() + skills.size() );
		setPetType( type ); // Resets skills to default
	}
	
	public PetType getPetType()
	{
		return type;
	}
	
	public void setPetType( PetType theType )
	{
		type = theType;
		setSize( type.sizeX, type.sizeY );
		skills.clear();
		skills.addAll( type.defaultSkills );
		dataWatcher.updateObject( DATA_TYPE, type.name );
		dataWatcher.updateObject( DATA_SKILLS, getSkillsStack() );
	}
	
	public boolean isSitting()
	{
		return ( dataWatcher.getWatchableObjectByte( DATA_SITTING ) != 0 );
	}
	
	public void setSitting( boolean sitting )
	{
		dataWatcher.updateObject( DATA_SITTING, ( byte )( sitting ? 1 : 0 ) );
	}
	
	public float getHunger()
	{
		return dataWatcher.func_111145_d( DATA_HUNGER );
	}
	
	public void useHunger( float amount )
	{
		float satDiff = Math.min( amount, saturation );
		saturation -= satDiff;
		if ( satDiff != amount )
		{
			setHunger( getHunger() - ( amount - satDiff ) );
		}
	}
	
	public void setHunger( float hunger )
	{
		if ( hunger < 0.f )
		{
			hunger = 0.f;
		}
		else if ( hunger > MAX_HUNGER )
		{
			hunger = MAX_HUNGER;
		}
		
		dataWatcher.updateObject( DATA_HUNGER, hunger );
	}
	
	public float getSaturation()
	{
		return saturation;
	}
	
	public void setSaturation( float theSaturation )
	{
		saturation = theSaturation;
	}
	
	public boolean isValidTarget( EntityLivingBase target )
	{
		return !( target instanceof EntityCreeper );
	}
	
	public IInventory getInventory()
	{
		return inv;
	}
	
	public boolean hasSaddle()
	{
		return ( inv.getStackInSlot( 0 ) != null && inv.getStackInSlot( 0 ).itemID == Item.saddle.itemID );
	}
	
	// Entity
    @Override
    public void writeEntityToNBT( NBTTagCompound tag)
    {
        super.writeEntityToNBT( tag );
        
        tag.setString( "Owner", getOwnerName() );
        tag.setString( "Type", getPetType().name );
        tag.setInteger( "Level", getLevel() );
        tag.setInteger( "FreeSkillPoints", getFreeSkillPoints() );
        int[] theSkills = new int[ skills.size() ];
        for ( int i = 0; i < skills.size(); ++i )
        {
        	theSkills[ i ] = skills.get( i );
        }
        tag.setTag( "Skills", new NBTTagIntArray( "Skills", theSkills ) );
        {
        	NBTTagList list = new NBTTagList();
        	for ( int i = 0; i < 12; ++i )
        	{
        		ItemStack stack = inv.getStackInSlot( i );
        		
        		NBTTagCompound compound = new NBTTagCompound();
        		if ( stack != null )
        		{
        			stack.writeToNBT( compound );
        		}
        		
        		list.appendTag( compound );
        	}
        	tag.setTag( "Inventory", list );
        }
        
        tag.setBoolean( "Sitting", isSitting() );
        tag.setFloat( "Hunger", getHunger() );
        tag.setFloat( "Saturation", getSaturation() );
    }

    @Override
    public void readEntityFromNBT( NBTTagCompound tag )
    {
        super.readEntityFromNBT( tag );
        
        setOwnerName( tag.getString( "Owner" ) );
        setPetType( PetType.forName( tag.getString( "Type" ) ) );
        setLevel( tag.getInteger( "Level" ) );
        setFreeSkillPoints( tag.getInteger( "FreeSkillPoints" ) );
        skills.clear();
        int[] theSkills = ( ( NBTTagIntArray ) tag.getTag( "Skills" ) ).intArray;
        for ( int id : theSkills )
        {
        	skills.add( id );
        }
        dataWatcher.updateObject( DATA_SKILLS, getSkillsStack() );
        if ( tag.getTag( "Inventory" ) != null )
        {
        	NBTTagList list = ( NBTTagList ) tag.getTag( "Inventory" );
        	for ( int i = 0; i < Math.min( 12, list.tagCount() ); ++i )
        	{
        		NBTTagCompound compound = ( NBTTagCompound ) list.tagAt( i );
        		if ( compound.hasNoTags() )
        		{
        			continue;
        		}
        		
        		ItemStack stack = ItemStack.loadItemStackFromNBT( compound );
        		inv.setInventorySlotContents( i, stack );
        	}
        }
        
        setSitting( tag.getBoolean( "Sitting" ) );
        setHunger( tag.getFloat( "Hunger" ) );
        setSaturation( tag.getFloat( "Saturation" ) );
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
        
        dataWatcher.addObject( DATA_OWNER, "Player" );
        dataWatcher.addObject( DATA_TYPE, "cat" );
        dataWatcher.addObject( DATA_SITTING, ( byte ) 0 );
        dataWatcher.addObject( DATA_HUNGER, 20.f );
        dataWatcher.addObject( DATA_LEVEL, 1 );
        dataWatcher.addObject( DATA_FREE_POINTS, 1 );
        dataWatcher.addObject( DATA_SKILLS, getSkillsStack() );
    }
    
    @Override
    public void onUpdate()
    {
    	if ( !hasSkill( Skill.COMBAT.id ) )
    	{
    		setTarget( null );
    	}
    	
    	updateSpeed();
    	
    	super.onUpdate();
    	
    	isDead = false;
    	deathTime = 0;
    	
    	if ( posY < -4.f )
    	{
    		setPosition( posX, -4.f, posZ );
    	}
    	
    	if ( worldObj.isRemote && ++syncTimer >= 20 )
    	{
    		ownerName = dataWatcher.getWatchableObjectString( DATA_OWNER );
        	type = PetType.forName( dataWatcher.getWatchableObjectString( DATA_TYPE ) );

        	skills.clear();
        	ItemStack skillStack = dataWatcher.getWatchableObjectItemStack( DATA_SKILLS );
        	if ( skillStack.itemID == Item.stick.itemID )
        	{
            	int[] newSkills = skillStack.getTagCompound().getIntArray( "Skills" );
            	for ( int id : newSkills )
            	{
            		skills.add( id );
            	}
        	}
        	
        	syncTimer = 0;
    	}
    	else
    	{
    		setHunger( getHunger() - 0.0002f );
    		
    		if ( getHunger() >= MAX_HUNGER / 2 && func_110143_aJ() < func_110138_aP() )
    		{
    			if ( ++regenTicks >= 35 )
    			{
    				setEntityHealth( func_110143_aJ() + 1 );
    				useHunger( 0.2f );
    				regenTicks = 0;
    			}
    		}
    		
    		if ( getHunger() < MAX_HUNGER && hasSkill( Skill.INVENTORY_FEEDING.id ) )
    		{
    			for ( int i = 3; i < inv.getSizeInventory(); ++i )
    			{
    				ItemStack stack = inv.getStackInSlot( i );
	    	    	if ( stack != null && stack.getItem() instanceof ItemFood )
	    	    	{
	    	    		ItemFood food = ( ItemFood ) stack.getItem();
	    	    		
	    	    		boolean canEat = false;
	    	    		for ( int id : skills )
	    	    		{
	    	    			Skill skill = Skill.forId( id );
	    	    			if ( !( skill instanceof FoodSkill ) )
	    	    			{
	    	    				continue;
	    	    			}
	    	    			FoodSkill foodSkill = ( FoodSkill ) skill;
	    	    			
	    	    			if ( foodSkill.type.doesMatch( type, stack ) )
	    	    			{
	    	    				canEat = true;
	    	    				break;
	    	    			}
	    	    		}

	    	    		/*
	    	    		System.out.println(getHunger());
	    	    		System.out.println(food.getHealAmount());
	    	    		System.out.println("");
	    	    		//*/
	    	    		//System.out.println( getHunger() + food.getHealAmount() );
	    	    		if ( canEat && getHunger() + food.getHealAmount() <= MAX_HUNGER )
	    	    		{
	    	    			setHunger( getHunger() + food.getHealAmount() );
	    	    			setSaturation( getSaturation() + food.getSaturationModifier() );
	    	    			inv.decrStackSize( i, 1 );
	    	    		}
	    	    	}
    			}
    		}
    	}
    }
    
    @Override
    public void setDead()
    {
    }
	
	@Override
    public boolean attackEntityFrom( DamageSource source, float damage )
    {
		if ( source.getEntity() == getOwner() )
		{
			return false;
		}
		
        Entity entity = source.getEntity();

        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
        {
        	damage = (damage + 1.0F) / 2.0F;
        }
        
        float percent = 1.f;
        for ( Skill skill : Skill.skills.values() )
        {
        	if ( !hasSkill( skill.id ) || !( skill instanceof DefenseSkill ) )
        	{
        		continue;
        	}
        	DefenseSkill defense = ( DefenseSkill ) skill;
        	
        	if ( defense.types == null )
        	{
        		percent -= defense.percent;
        	}
        	else
        	{
        		boolean found = false;
        		for ( String str : defense.types )
        		{
        			if ( str.equals( source.damageType ) )
        			{
        				found = true;
        				break;
        			}
        		}
        		
        		percent -= defense.percent;
        	}
        }
        
        damage *= Math.max( percent, 0.f );
        if ( damage <= 0.f )
        {
        	return false;
        }
        
        aiSit.setSitting( false );
        setSitting( false );

        return super.attackEntityFrom(source, damage);
    }
    
    // EntityLivingBase, EntityLiving
    @Override
    protected boolean canDespawn()
    {
    	return false;
    }

    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected String getLivingSound()
    {
        return type.getLivingSound();
    }
    
    @Override
    protected String getHurtSound()
    {
        return type.getHurtSound();
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    @Override
    public String getEntityName()
    {
        return hasCustomNameTag() ? getCustomNameTag() : ( "entity.pet." + type.name );
    }
    
    @Override
    protected void func_110147_ax()
    {
        super.func_110147_ax();

        func_110148_a( SharedMonsterAttributes.field_111267_a ).func_111128_a( 20 );
        func_110148_a( SharedMonsterAttributes.field_111263_d ).func_111128_a( 0.3 );
    }
    
    @Override
    public void onDeath( DamageSource source )
    {
    }
    
    @Override
    public boolean isEntityAlive()
    {
    	return true;
    }
    
    @Override
    protected boolean isMovementBlocked()
    {
        return isSitting();
    }
    
    @Override
    public boolean interact( EntityPlayer player )
    {
    	if ( player.isSneaking() )
    	{
    		UsefulPets.proxy.setPendingPetForGui( ( worldObj.isRemote ? Side.CLIENT : Side.SERVER ), this );
    		player.openGui( UsefulPets.instance, UsefulPets.PET_INVENTORY_GUI_ID, worldObj, 0, 0, 0 );
    		return false;
    	}
    	
    	if ( worldObj.isRemote )
    	{
    		return false;
    	}
    	
    	ItemStack held = player.getHeldItem();
    	if ( held != null && held.getItem() instanceof ItemFood )
    	{
    		ItemFood food = ( ItemFood ) held.getItem();
    		
    		boolean canEat = false;
    		for ( int id : skills )
    		{
    			Skill skill = Skill.forId( id );
    			if ( !( skill instanceof FoodSkill ) )
    			{
    				continue;
    			}
    			FoodSkill foodSkill = ( FoodSkill ) skill;
    			
    			if ( foodSkill.type.doesMatch( type, held ) )
    			{
    				canEat = true;
    				break;
    			}
    		}
    		
    		if ( canEat )
    		{
    			setHunger( getHunger() + food.getHealAmount() );
    			setSaturation( getSaturation() + food.getSaturationModifier() );
        		return true;
    		}
    	}
    	else if ( held == null && hasSkill( Skill.TRAVEL_MOUNTABLE.id ) && hasSaddle() )
    	{
    		player.mountEntity( this );
    	}
    	else
    	{
    		setSitting( !isSitting() );
    	}
    	
    	return false;
    }

    @Override
    public boolean attackEntityAsMob( Entity entity )
    {
    	int damage = 0;
    	for ( int id : skills )
    	{
    		Skill skill = Skill.forId( id );
    		if ( !( skill instanceof AttackSkill ) )
    		{
    			continue;
    		}
    		AttackSkill attack = ( AttackSkill ) skill;
    		
    		damage += attack.damage;
    	}
    	
    	if ( hasSkill( Skill.INVENTORY_WEAPON.id ) && inv.getStackInSlot( 2 ) != null )
    	{
    		ItemStack stack = inv.getStackInSlot( 2 );
    		if ( stack.getItem() instanceof ClawItem )
    		{
    			ClawItem claw = ( ClawItem ) stack.getItem();
    			damage += claw.damage;
    		}
    	}
    	
        return entity.attackEntityFrom( DamageSource.causeMobDamage( this ), (float) damage );
    }
    
    @Override
    public int getTotalArmorValue()
    {
    	if ( !hasSkill( Skill.INVENTORY_ARMOR.id ) || inv.getStackInSlot( 1 ) == null )
    	{
    		return 0;
    	}
    	
    	ItemStack stack = inv.getStackInSlot( 1 );
    	if ( stack.itemID == Item.field_111215_ce.itemID )
    	{
    		return 5;
    	}
    	else if ( stack.itemID == Item.field_111216_cf.itemID )
    	{
    		return 7;
    	}
    	else if ( stack.itemID == Item.field_111213_cg.itemID )
    	{
    		return 11;
    	}
    	
    	return 0;
    }
    
    // Copied from horses
    @Override
    public void moveEntityWithHeading(float par1, float par2)
    {
        if (this.riddenByEntity != null && hasSkill( Skill.TRAVEL_MOUNTABLE.id ) && hasSaddle() && !isSitting()/* && this.func_110257_ck()*/)
        {
            this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
            par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;

            if (par2 <= 0.0F)
            {
                par2 *= 0.25F;
                //this.field_110285_bP = 0;
            }

            // Jumping code?
            /*
            if (this.onGround && this.field_110277_bt == 0.0F &&false/*&& this.func_110209_cd() && !this.field_110294_bI)
            {
                par1 = 0.0F;
                par2 = 0.0F;
            }

            if (this.field_110277_bt > 0.0F && !wasInAir && this.onGround)
            {
            	// 3.f = jump height
                this.motionY = 3.f * (double)this.field_110277_bt;

                if (this.isPotionActive(Potion.jump))
                {
                    this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                }

                wasInAir=true;
                //this.func_110255_k(true);
                this.isAirBorne = true;

                if (par2 > 0.0F)
                {
                    float f2 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
                    float f3 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
                    this.motionX += (double)(-0.4F * f2 * this.field_110277_bt);
                    this.motionZ += (double)(0.4F * f3 * this.field_110277_bt);
                    this.playSound("mob.horse.jump", 0.4F, 1.0F);
                }

                this.field_110277_bt = 0.0F;
            }
            //*/

            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if (!this.worldObj.isRemote)
            {
                this.setAIMoveSpeed((float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() / 2);
                super.moveEntityWithHeading(par1, par2);
                this.setAIMoveSpeed((float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            }

            if (this.onGround)
            {
                this.field_110277_bt = 0.0F;
                //this.func_110255_k(false);
                wasInAir=false;
            }

            this.prevLimbYaw = this.limbYaw;
            double d0 = this.posX - this.prevPosX;
            double d1 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

            if (f4 > 1.0F)
            {
                f4 = 1.0F;
            }

            this.limbYaw += (f4 - this.limbYaw) * 0.4F;
            this.limbSwing += this.limbYaw;
        }
        else
        {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.moveEntityWithHeading(par1, par2);
        }
    }
    private float field_110277_bt = 0;
    private boolean wasInAir=false;

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
		dataWatcher.updateObject( DATA_OWNER, ownerName );
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
	private List< Integer > skills = new ArrayList< Integer >();
	private PetInventory inv = new PetInventory( this );
	
	// State stuff
	private float saturation;
	private int regenTicks = 0;
	private int syncTimer = 0;
	
	// AI stuff
	private SitAI aiSit = new SitAI( this );
	
	public static final int DATA_OWNER = 20;
	public static final int DATA_TYPE = 21;
	public static final int DATA_SITTING = 22;
	public static final int DATA_HUNGER = 23;
	public static final int DATA_LEVEL = 24;
	public static final int DATA_FREE_POINTS = 25;
	public static final int DATA_SKILLS = 26;
	
	public static final float MAX_HUNGER = 20;
}
