package com.matthewadev.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;

public class TextureManager {
    public static HashMap<BlockType, HashMap<BlockSide,Texture>> textures = new HashMap<>();
    public static Texture all;
    public static void loadTextures() {
        //System.setProperty("user.dir","..");
        // by default, load top texture
        for (BlockType t : BlockType.values()){
            textures.put(t, new HashMap<BlockSide, Texture>());
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("./textures"))) { // i am not proud of this
            for (Path file: stream) {
                String[] fileName = file.getFileName().toString().split("-"); // remove the beginning component and try and see if there is a block type
                try{
                    BlockType type = BlockType.valueOf(fileName[0]);
                    BlockSide side = BlockSide.valueOf(fileName[1].split("\\.")[0]); // this is terrible
                    textures.get(type).put(side, new Texture(file.toString()));
                }catch(IllegalArgumentException ignored){}
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
    }
}
