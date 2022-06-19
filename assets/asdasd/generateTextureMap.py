from PIL import Image
import os

from numpy import block

blockType = """EMPTY,
STONE,
DIRT,
GRASS_BLOCK,
ACACIA_LOG,
BIRCH_LOG,
DARK_OAK_LOG,
JUNGLE_LOG,
OAK_LOG,
SPRUCE_LOG,
SPRUCE_PLANKS,
OAK_PLANKS,
JUNGLE_PLANKS,
ACACIA_PLANKS,
BIRCH_PLANKS,
DARK_OAK_PLANKS,
BLACK_WOOL,
BLUE_WOOL,
BROWN_WOOL,
CYAN_WOOL,
GRAY_WOOL,
LIGHT_BLUE_WOOL,
LIGHT_GRAY_WOOL,
LIME_WOOL,
MAGENTA_WOOL,
GREEN_WOOL,
ORANGE_WOOL,
PINK_WOOL,
PURPLE_WOOL,
RED_WOOL,
WHITE_WOOL,
YELLOW_WOOL,
CHISELED_STONE_BRICKS,
CRACKED_STONE_BRICKS,
CRAFTING_TABLE,
END_STONE,
GLASS,
LEAVES,
SMOOTH_STONE,
STONE_BRICKS""".split(",\n")

width = len(blockType) * 16
height = 3 * 16

img = Image.new("RGBA", (width, height), (0,0,0,0))

for file in os.listdir():
  if(file.split(".")[-1] != "png"):
    continue
  name = file.split(".")[0]
  side = name.split("-")[-1]
  
  h = 0
  if(side == "SIDE"):
    h = 1
  elif(side == "BOTTOM"):
    h = 2
  blockname = name.split("-")[0]
  w = blockType.index(blockname)
  blockimage = Image.open(file)
  img.paste(blockimage, (w * 16, h * 16))

img.save("textures.png")
