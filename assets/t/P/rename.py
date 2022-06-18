import os

for path in os.listdir():
  if(path.split(".")[-1] != "png"):
    continue
  os.rename(path, path.upper().split(".")[0] + "-TOP.png")
