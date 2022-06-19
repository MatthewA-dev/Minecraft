package com.matthewadev.game;

import com.matthewadev.render.Block;
import com.matthewadev.render.BlockType;
import com.matthewadev.render.Chunk;

public class WorldGenerator {
    public static Chunk generateChunk(int chunkx, int chunkz){
        Chunk c = new Chunk(chunkx, chunkz);
        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (x == 8 || z == 8) {
                    continue;
                }
                BlockType t = BlockType.STONE;
                if (x == 0) {
                    t = BlockType.CRAFTING_TABLE;
                } else if (z == 0) {
                    t = BlockType.DIRT;
                } else if (x == 15) {
                    t = BlockType.GRASS_BLOCK;
                } else if (z == 15) {
                    t = BlockType.OAK_PLANKS;
                }
                c.addBlockWithoutCalculation(new Block(chunkx * 16 + x, 0, chunkz * 16 + z, t));
            }
        }
        return c;
    }
}
