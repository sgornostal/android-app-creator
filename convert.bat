@echo off
set input_image=%1
set output_image=%2

magick %input_image% ( +clone  -threshold -1 -draw "fill black polygon 0,0 0,30 30,0 fill white circle 30,30 30,0" ( +clone -flip ) -compose Multiply -composite  ( +clone -flop ) -compose Multiply -composite ) +matte -compose CopyOpacity -composite %output_image%
