-makelib ies_lib/xil_defaultlib -sv \
  "D:/utility/vivado/Vivado/2018.2/data/ip/xpm/xpm_cdc/hdl/xpm_cdc.sv" \
-endlib
-makelib ies_lib/xpm \
  "D:/utility/vivado/Vivado/2018.2/data/ip/xpm/xpm_VCOMP.vhd" \
-endlib
-makelib ies_lib/xil_defaultlib \
  "../../../../memory_w_r.srcs/sources_1/ip/clk_div/clk_div_clk_wiz.v" \
  "../../../../memory_w_r.srcs/sources_1/ip/clk_div/clk_div.v" \
-endlib
-makelib ies_lib/xil_defaultlib \
  glbl.v
-endlib

