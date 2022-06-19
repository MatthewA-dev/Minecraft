package com.matthewadev.game;

import com.matthewadev.render.Block;
import com.matthewadev.render.BlockType;
import com.matthewadev.render.Chunk;

public class WorldGenerator {
    private static int level = 3;
    private static int variance = 4;
    public static Chunk generateChunk(int chunkx, int chunkz){
        Chunk c = new Chunk(chunkx, chunkz);
        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                //double val = PerlinNoise.noise(chunkx * 16 + x, 30, chunkz * 16 + z);

                double val = (PerlinNoise.noise((double)x / 16 + chunkx, (double)z / 16 + chunkz));
                c.addBlockWithoutCalculation(new Block(chunkx * 16 + x, (int)(val * variance) + level, chunkz * 16 + z, BlockType.GRASS_BLOCK));
                for (int i = 0; i < (int)(val * variance) + level; i++) {
                    if(i > (int)(val * variance) + level - 4){

                        c.addBlockWithoutCalculation(new Block(chunkx * 16 + x, i, chunkz * 16 + z, BlockType.DIRT));
                    }else {
                        c.addBlockWithoutCalculation(new Block(chunkx * 16 + x, i, chunkz * 16 + z, BlockType.STONE));
                    }
                }
            }
        }
        c.recalculateMesh();
        return c;
    }
}
