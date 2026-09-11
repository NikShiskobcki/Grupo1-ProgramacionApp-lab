Para levantar en linux:
1- cd a la carpeta
2- xhost +local:docker
  Si xhost no esta instalado:
  sudo apt update && sudo apt install -y x11-xserver-utils
3- docker compose up

Para levantar en mac:
1- cd a la carpeta 
2- export DISPLAY=0 
3- /opt/X11/bin/xhost +
4- docker compose up



