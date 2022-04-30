# openBLAS\gemm对比

> 元凯文 200110213

## 一 、运行环境搭建

1. git clone https://github.com/xianyi/OpenBLAS.git
2. cd OpenBLAS
3. make FC=gfortran 
4. sudo make PREFIX=/usr/local install

## 二 、结果对比

![gemm](pic\openBLAS_gemm.png)

## 三 、结果分析

在数据规模较小时（n<500) 两算法运行表现相近，随着数据规模增大，普通gemm算法呈现出$n^{3}$的渐进复杂度，而openBLAS算法复杂度表现几乎为线性。

