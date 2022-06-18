import os

blocks = []

for path in os.listdir():
  if(path.split(".")[-1] != "png"):
    continue
  if(path.split("-")[0] in blocks):
    continue
  blocks.append(path.split("-")[0])

for block in blocks:
  print(block + ",")
