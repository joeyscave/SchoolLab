# <center>gcc\gdb学习总结

>元凯文 200110213

## 一：gcc

1. 预处理

   预处理是将包含（ include ）的文件插入源文件中，将宏定义展开、根据条件编译命令选择要使用的代码，最后将代码输出到一个“.i” 文件中等待进一步处理。

2. 编译

   编译就是把 c代码（比如上面的“.i”文件）翻译成汇编代码

3. 汇编

   汇编是将第二步输出的汇编代码翻译成符合一定格式的机器代码，在 Linux系统上一般表现为 ELF目标文件（ OBJ 文件）

4. 链接

   链接是将汇编生成的 OBJ 文件、系统库的 OBJ 文件、库文件链接起来，最终生成可以在特定平台运行的可执行程序。

### 常见指令

-v 查看gcc编译器版本

-o 指定输出文件名

-E 只执行到预处理结束

-S 只执行到编译结束

-c 只执行到编译和汇编结束

./xxx.out 执行可执行文件

## 二：gdb

### 常见指令

2. 单步执行和跟踪函数调用
a) 通过 gdb file （可执行文件）进入 gdb 调试
b)help 查看命令类别 可通过 help files 的形式查看某一类别下的命令
c)list （ l ）从第一行开始列出源代码，一次十行 可以通过回车复制上一次命令进行连续查看 也可以 list （函数名）列出函数源代码行号 从第几行开始列出源代码
e)quit 
f)start 开始执行程序，停留在第一行代码处
g)next 
h)step （ s ）进入函数
i)backtrace (bt) 查看函数调用的栈帧
j)info (i)local 查看局部变量
k)frame (f) 选择栈帧
l) print （ p ）变量名 打印指定变量的值
m) finish 运行到当前函数返回为止
n)set var 修改变量的值
2. 断点
  a)break （ b ）行号 设置断点 函数名 在某个函数开头设置断点
  b)breakif 设置条件断点
  c) continue （ c ）从当前位置连续的运行程序
  d) info breakpoints 查看设置的的断点
  e)delete reakpoints 断点号 删除断点
  f)enable 断点号 禁用此大断点
  g)run(r) 从头开始连续运行程序
  h)display 变量名 跟踪查看某个变量，每次停止时显示它的值i)undisplay 跟踪显示号 取消跟踪显示
3. 观察点
  a)x [ 指定打印格式 ] 打印指定的存储单元的内容
  b)watch 变量名 设置观察点
  c)info watchpoints 查看设置了哪些观察点