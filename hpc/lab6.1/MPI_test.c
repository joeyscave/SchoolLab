#include <stdio.h>
#include <mpich/mpi.h>
#include <string.h>
int size;
int A[101][101];
int B[101][101];
int C[101][101];

int main(int argc, char *argv[])
{
    int numprocs, myid, source;
    MPI_Status status;
    size=100;
    //矩阵初始化
    for (int i = 0; i < size; ++i)
        for (int j = 0; j < size; ++j)
        {
            A[i][j] = i + j ;
            B[i][j] = i + j ;
            C[i][j] = 0;
        }

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &myid);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    int block = (size * size) / (numprocs - 1); //每个进程需要计算的个数
    int temp[block];

    if (myid != 0)
    {
        int start = (myid - 1) * block; //当前进程需要计算的首个位置
        int end = start + block;    //当前进程需要计算的末尾位置
        int px = 0; //游标表明当前已计算个数

        while (start < end)
        {
            int i = start % size;
            int j = start / size;
            temp[px] = 0;
            for (int k = 0; k < size; k++)
            {
                temp[px] += A[i][k] * B[k][j];
            }
            //printf("In %d:temp[%d]=%d\n",myid,px,temp[px]);
            px++;
            start++;
        }

        MPI_Send(temp, px, MPI_INT, 0, 99, MPI_COMM_WORLD); //通信发送
    }
    else
    {
        double start=MPI_Wtime();
        for (source = 1; source < numprocs; source++)
        {
            MPI_Recv(temp, block, MPI_INT, source, 99, MPI_COMM_WORLD, &status); //通信接收
            int start = (source - 1) * block;
            int end = start + block;
            int px = 0;

            //拼合各进程计算结果
            while (start < end)
            {
                int i = start % size;
                int j = start / size;
                C[i][j] = temp[px];
                start++;
                px++;
            }
        }
        double end=MPI_Wtime();
        printf("time:%.3lf\n",end-start);
        // for (int i = 0; i < size; ++i)
        // {
        //     for (int j = 0; j < size; ++j)
        //     {
        //         printf("%d ", C[i][j]);
        //     }
        //     printf("\n");
        // }
    }
    MPI_Finalize();
    return 0;
}