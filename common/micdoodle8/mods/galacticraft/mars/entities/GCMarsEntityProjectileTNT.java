package micdoodle8.mods.galacticraft.mars.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GCMarsEntityProjectileTNT extends EntityFireball
{
    public GCMarsEntityProjectileTNT(World par1World)
    {
        super(par1World);
        this.setSize(1.0F, 1.0F);
    }

    public GCMarsEntityProjectileTNT(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
    {
        super(par1World, par2EntityLivingBase, par3, par5, par7);
        this.setSize(1.0F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public GCMarsEntityProjectileTNT(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.setSize(0.3125F, 0.3125F);
    }

    public boolean isBurning()
    {
        return false;
    }

    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            if (par1MovingObjectPosition.entityHit != null)
            {
                par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0F);
            }

            this.worldObj.newExplosion((Entity)null, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }
    }

    public boolean canBeCollidedWith()
    {
        return true;
    }
}
