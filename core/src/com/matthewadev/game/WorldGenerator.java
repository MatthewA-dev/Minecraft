package com.matthewadev.game;

import com.matthewadev.render.Block;
import com.matthewadev.render.BlockType;
import com.matthewadev.render.Chunk;

public class WorldGenerator {
    public static Chunk generateChunk(int chunkx, int chunkz){
        Chunk c = new Chunk(chunkx, chunkz);
        for(int x = 0; x < 16; x++){
            for(int z = 0; z < 16; z++) {
                System.out.println((chunkx * 16 + x) + " " + (chunkz * 16 + z));
                if(x == 8 || z == 8){
                    continue;
                } else if(x == 0 || z == 0 || x == 15 || z == 15) {
                    c.addBlock(new Block(chunkx * 16 + x, 0, chunkz * 16 + z, BlockType.GRASS_BLOCK));
                }else {
                    c.addBlock(new Block(chunkx * 16 + x, 0, chunkz * 16 + z, BlockType.STONE));
                }
            }
        }
        return c;
    }
}
