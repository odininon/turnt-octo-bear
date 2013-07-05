package com.freyja.defense.registry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.freyja.defense.Defense;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Registry
{
    private static Map<String, Block> blocks;
    private static Map<String, Item> items;

    public static Block getBlock(String blockName)
    {
        if (blocks.containsKey(blockName))
        {
            return blocks.get(blockName);
        }

        return null;
    }

    public static Integer getBlockId(String blockName)
    {
        final Block block = getBlock(blockName);

        return block != null ? block.blockID : null;
    }

    public static void init()
    {
        blocks = new HashMap<String, Block>();
        items = new HashMap<String, Item>();
    }

    public static void register(String name, Object... object)
    {
        for (Object obj : object)
        {
            if (obj instanceof Item)
            {
                registerItem(name, (Item) obj);
            }
            else if (obj instanceof Block)
            {
                registerBlock(name, (Block) obj);
            }
        }
    }

    public static void registerBlock(String blockName, Block block)
    {
        if (blocks.containsKey(blockName))
        {
            Defense.logger.severe("Tried to register a block already register with name: " + blockName);
        }

        blocks.put(blockName, block);
        GameRegistry.registerBlock(block, blockName);
        LanguageRegistry.addName(block, blockName);
    }

    public static void registerItem(String blockName, Item item)
    {
        if (items.containsKey(blockName))
        {
            Defense.logger.severe("Tried to register a items already register with name: " + blockName);
        }

        items.put(blockName, item);
        LanguageRegistry.addName(item, blockName);
    }
}
