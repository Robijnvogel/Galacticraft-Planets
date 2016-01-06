package micdoodle8.mods.galacticraft.mars.wgen.dungeon;

import java.util.Random;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityDungeonSpawner;
import micdoodle8.mods.galacticraft.core.wgen.dungeon.GCCoreDungeonBoundingBox;
import micdoodle8.mods.galacticraft.core.wgen.dungeon.GCCoreDungeonRoom;
import micdoodle8.mods.galacticraft.core.wgen.dungeon.GCCoreMapGenDungeon;
import micdoodle8.mods.galacticraft.mars.blocks.GCMarsBlocks;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityCreeperBoss;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.vector.Vector3;

public class GCMarsRoomBoss extends GCCoreDungeonRoom
{

    public int sizeX;
    public int sizeY;
    public int sizeZ;
    Random rand;
    ChunkCoordinates spawnerCoords;

    public GCMarsRoomBoss(GCCoreMapGenDungeon dungeon, int posX, int posY, int posZ, ForgeDirection entranceDir)
    {
        super(dungeon, posX, posY, posZ, entranceDir);
        if (this.worldObj != null)
        {
            this.rand = new Random(this.worldObj.getSeed() * posX * posY * 57 * posZ);
            this.sizeX = this.rand.nextInt(6) + 20;
            this.sizeY = this.rand.nextInt(2) + 11;
            this.sizeZ = this.rand.nextInt(6) + 20;
        }
    }

    @Override
    public void generate(short[] chunk, byte[] meta, int cx, int cz)
    {
        for (int i = this.posX - 1; i <= this.posX + this.sizeX; i++)
        {
            for (int k = this.posZ - 1; k <= this.posZ + this.sizeZ; k++)
            {
                for (int j = this.posY - 1; j <= this.posY + this.sizeY; j++)
                {
                    if (i == this.posX - 1 || i == this.posX + this.sizeX || j == this.posY - 1 || j == this.posY + this.sizeY || k == this.posZ - 1 || k == this.posZ + this.sizeZ)
                    {
                        this.placeBlock(chunk, meta, i, j, k, cx, cz, this.dungeonInstance.DUNGEON_WALL_ID, this.dungeonInstance.DUNGEON_WALL_META);
                    }
                    else if (i == this.posX && k == this.posZ || i == this.posX + this.sizeX - 1 && k == this.posZ || i == this.posX && k == this.posZ + this.sizeZ - 1 || i == this.posX + this.sizeX - 1 && k == this.posZ + this.sizeZ - 1)
                    {
                        this.placeBlock(chunk, meta, i, j, k, cx, cz, Block.lavaMoving.blockID, 0);
                    }
                    else if (j % 3 == 0 && j >= this.posY + 2 && (i == this.posX || i == this.posX + this.sizeX - 1 || k == this.posZ || k == this.posZ + this.sizeZ - 1) || i == this.posX + 1 && k == this.posZ || i == this.posX && k == this.posZ + 1 || i == this.posX + this.sizeX - 2 && k == this.posZ || i == this.posX + this.sizeX - 1 && k == this.posZ + 1 || i == this.posX + 1 && k == this.posZ + this.sizeZ - 1 || i == this.posX && k == this.posZ + this.sizeZ - 2 || i == this.posX + this.sizeX - 2 && k == this.posZ + this.sizeZ - 1 || i == this.posX + this.sizeX - 1 && k == this.posZ + this.sizeZ - 2)
                    {
                        this.placeBlock(chunk, meta, i, j, k, cx, cz, Block.fenceIron.blockID, 0);
                    }
                    else if ((i == this.posX + 1 && k == this.posZ + 1 || i == this.posX + this.sizeX - 2 && k == this.posZ + 1 || i == this.posX + 1 && k == this.posZ + this.sizeZ - 2 || i == this.posX + this.sizeX - 2 && k == this.posZ + this.sizeZ - 2) && j % 3 == 0)
                    {
                        this.placeBlock(chunk, meta, i, j, k, cx, cz, Block.fenceIron.blockID, 0);
                    }
                    else
                    {
                        this.placeBlock(chunk, meta, i, j, k, cx, cz, 0, 0);
                    }
                }
            }
        }

        final int hx = (this.posX + this.posX + this.sizeX) / 2;
        final int hz = (this.posZ + this.posZ + this.sizeZ) / 2;
        this.spawnerCoords = new ChunkCoordinates(hx, this.posY + 2, hz);
    }

    @Override
    public GCCoreDungeonBoundingBox getBoundingBox()
    {
        return new GCCoreDungeonBoundingBox(this.posX, this.posZ, this.posX + this.sizeX, this.posZ + this.sizeZ);
    }

    @Override
    protected GCCoreDungeonRoom makeRoom(GCCoreMapGenDungeon dungeon, int x, int y, int z, ForgeDirection dir)
    {
        return new GCMarsRoomBoss(dungeon, x, y, z, dir);
    }

    @Override
    protected void handleTileEntities(Random rand)
    {
        if (this.spawnerCoords == null)
        {
            return;
        }

        this.worldObj.setBlock(this.spawnerCoords.posX, this.spawnerCoords.posY, this.spawnerCoords.posZ, GCMarsBlocks.marsBlock.blockID, 10, 3);

        final TileEntity tile = this.worldObj.getBlockTileEntity(this.spawnerCoords.posX, this.spawnerCoords.posY, this.spawnerCoords.posZ);

        if (tile == null || !(tile instanceof GCCoreTileEntityDungeonSpawner))
        {
            GCCoreTileEntityDungeonSpawner spawner = new GCCoreTileEntityDungeonSpawner(GCMarsEntityCreeperBoss.class);
            spawner.setRoom(new Vector3(this.posX, this.posY, this.posZ), new Vector3(this.sizeX, this.sizeY, this.sizeZ));
            this.worldObj.setBlockTileEntity(this.spawnerCoords.posX, this.spawnerCoords.posY, this.spawnerCoords.posZ, spawner);
        }
        else if (tile instanceof GCCoreTileEntityDungeonSpawner)
        {
            ((GCCoreTileEntityDungeonSpawner) tile).setRoom(new Vector3(this.posX, this.posY, this.posZ), new Vector3(this.sizeX, this.sizeY, this.sizeZ));
        }
    }

}
