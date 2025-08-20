package com.andersmmg.cityessentials.tags;

import com.andersmmg.cityessentials.CityEssentials;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Block> MAIL_DROPPABLE = TagKey.of(RegistryKeys.BLOCK, Identifier.of(CityEssentials.MOD_ID, "mail_droppable"));
}
