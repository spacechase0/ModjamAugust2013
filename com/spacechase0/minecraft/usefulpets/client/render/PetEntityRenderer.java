package com.spacechase0.minecraft.usefulpets.client.render;

import org.lwjgl.opengl.GL11;

import com.spacechase0.minecraft.usefulpets.client.model.CatModel;
import com.spacechase0.minecraft.usefulpets.client.model.DogModel;
import com.spacechase0.minecraft.usefulpets.entity.PetEntity;
import com.spacechase0.minecraft.usefulpets.pet.PetType;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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
		
        doRenderLiving( ( EntityLiving ) entity, par2, par4, par6, par8, par9 );
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
    protected void passSpecialRender(EntityLivingBase entity, double x, double y, double z)
    {
		PetEntity pet = ( PetEntity ) entity;
		int health = ( int ) pet.func_110143_aJ();
		int maxHealth = ( int ) pet.func_110138_aP();
		
		renderLivingLabel( entity, health + "/" + maxHealth, x, y, z, 16 );
    }
	
	private final ModelBase catModel = new CatModel();
	private final ModelBase dogModel = new DogModel();
	private final ResourceLocation catTex = new ResourceLocation( "textures/entity/cat/red.png" );
	private final ResourceLocation dogTex = new ResourceLocation( "textures/entity/wolf/wolf_tame.png" );
}
