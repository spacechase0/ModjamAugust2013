package com.spacechase0.minecraft.usefulpets.client.render;

import org.lwjgl.opengl.GL11;

import com.spacechase0.minecraft.usefulpets.client.model.CatModel;
import com.spacechase0.minecraft.usefulpets.client.model.DogModel;
import com.spacechase0.minecraft.usefulpets.entity.PetEntity;
import com.spacechase0.minecraft.usefulpets.pet.PetType;
import com.spacechase0.minecraft.usefulpets.pet.skill.Skill;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PetEntityRenderer extends RenderLiving
{
	public PetEntityRenderer()
	{
		super( new CatModel(), 0.45f );
	}

	@Override
    public void doRender( Entity entity, double par2, double par4, double par6, float par8, float par9 )
    {
		PetEntity pet = ( PetEntity ) entity;
		if ( pet.getPetType().equals( PetType.CAT ) )
		{
			mainModel = renderPassModel = catModel;
		}
		else if ( pet.getPetType().equals( PetType.DOG ) )
		{
			mainModel = renderPassModel = dogModel;
		}
		
		Minecraft.getMinecraft().renderEngine.func_110577_a( func_110775_a( entity ) );
        doRenderLiving( ( EntityLiving ) entity, par2, par4, par6, par8, par9 );
        
        // TODO: Render armor
        
        if ( hasSaddle( pet ) )
        {
            Minecraft.getMinecraft().renderEngine.func_110577_a( new ResourceLocation( "usefulpets:textures/entity/" + pet.getPetType().name + "/saddle.png" ) );
            doRenderLiving( ( EntityLiving ) entity, par2, par4, par6, par8, par9 );
        }
        
        /*
        Minecraft.getMinecraft().renderEngine.func_110577_a( new ResourceLocation( "usefulpets:textures/entity/" + pet.getPetType().name + "/armorDiamond.png" ) );
        doRenderLiving( ( EntityLiving ) entity, par2, par4, par6, par8, par9 );
        */
        
    }
	
	@Override
	protected ResourceLocation func_110775_a( Entity entity )
	{
		PetEntity pet = ( PetEntity ) entity;
		if ( pet.getPetType().equals( PetType.CAT ) )
		{
			return catTex;
		}
		else if ( pet.getPetType().equals( PetType.DOG ) )
		{
			return dogTex;
		}
		
		return null;
	}
	
	@Override
    protected void func_110777_b(Entity par1Entity)
    {
        //this.func_110776_a(this.func_110775_a(par1Entity));
    }
	
	@Override
    protected void passSpecialRender(EntityLivingBase entity, double x, double y, double z)
    {
		
		PetEntity pet = ( PetEntity ) entity;
		int health = ( int ) pet.func_110143_aJ();
		int maxHealth = ( int ) pet.func_110138_aP();
		
		if ( hasSaddle( pet ) )
		{
			justRendered = !justRendered;
			if ( !justRendered )
			{
				return;
			}
		}
		
		String hp = "HP: " + health + "/" + maxHealth;
		String food = "Food: " + ( ( int ) pet.getHunger() ) + "/" + ( ( int ) PetEntity.MAX_HUNGER );
		
		renderLivingLabel( entity, hp, x, y, z, 4 );
		renderLivingLabel( entity, food, x, y - 0.3f, z, 4 );
    }
	
	private boolean hasSaddle( PetEntity pet )
	{
        ItemStack saddleStack = pet.getInventory().getStackInSlot( 0 );
        if ( pet.hasSkill( Skill.TRAVEL_MOUNTABLE.id ) && saddleStack != null && saddleStack.itemID == Item.saddle.itemID )
        {
        	return true;
        }
        
        return false;
	}
	
	private final ModelBase catModel = new CatModel();
	private final ModelBase dogModel = new DogModel();
	private final ResourceLocation catTex = new ResourceLocation( "textures/entity/cat/red.png" );
	private final ResourceLocation dogTex = new ResourceLocation( "textures/entity/wolf/wolf_tame.png" );
	
	private boolean justRendered = false;
}
