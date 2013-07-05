package com.freyja.defense.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.freyja.defense.registry.Registry;

public class ItemDefenseDoor extends Item
{
    private final String blockName;

    public ItemDefenseDoor(int id, String block)
    {
        super(id);
        blockName = block;
        this.maxStackSize = 1;
    }

    /**
     * Callback for item usage. If the item does something special on right
     * clicking, he will have one of those. Return True if something happen and
     * false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else
        {
            ++y;
            final Block block = Registry.getBlock(blockName);

            if (block == null)
                return false;

            if (player.canPlayerEdit(x, y, z, par7, itemStack) && player.canPlayerEdit(x, y + 1, z, par7, itemStack))
            {
                if (!block.canPlaceBlockAt(world, x, y, z))
                {
                    return false;
                }
                else
                {
                    int meta = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(world, x, y, z, meta, block);
                    --itemStack.stackSize;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public static void placeDoorBlock(World world, int x, int y, int z, int meta, Block block)
    {
        byte b0 = 0;
        byte b1 = 0;

        if (meta == 0)
        {
            b1 = 1;
        }

        if (meta == 1)
        {
            b0 = -1;
        }

        if (meta == 2)
        {
            b1 = -1;
        }

        if (meta == 3)
        {
            b0 = 1;
        }

        int i1 = (world.isBlockNormalCube(x - b0, y, z - b1) ? 1 : 0) + (world.isBlockNormalCube(x - b0, y + 1, z - b1) ? 1 : 0);
        int j1 = (world.isBlockNormalCube(x + b0, y, z + b1) ? 1 : 0) + (world.isBlockNormalCube(x + b0, y + 1, z + b1) ? 1 : 0);
        boolean flag = world.getBlockId(x - b0, y, z - b1) == block.blockID || world.getBlockId(x - b0, y + 1, z - b1) == block.blockID;
        boolean flag1 = world.getBlockId(x + b0, y, z + b1) == block.blockID || world.getBlockId(x + b0, y + 1, z + b1) == block.blockID;
        boolean flag2 = false;

        if (flag && !flag1)
        {
            flag2 = true;
        }
        else if (j1 > i1)
        {
            flag2 = true;
        }

        world.setBlock(x, y, z, block.blockID, meta, 2);
        world.setBlock(x, y + 1, z, block.blockID, 8 | (flag2 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, block.blockID);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block.blockID);
    }
}
