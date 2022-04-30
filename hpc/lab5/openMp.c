#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/time.h>

#define Maxn 1005

FILE *f=NULL;
int minSize,maxSize,size,step, threadNum;
struct timeval startThr, endThr, startNor, endNor, startOmp, endOmp;
double A[Maxn][Maxn];
double B[Maxn][Maxn];
double CThr[Maxn][Maxn];    //存放多线程计算结果
double CNor[Maxn][Maxn];    //存放普通算法结果
double COmp[Maxn][Maxn];    //存放openMP算法结果

//计算单行结果
void ThrCal(int *row) {
    for (int k = 0; k < size; ++k) {
        double sum = 0;
        for (int i = 0; i < size; ++i)
            sum += A[*row][i] * B[i][k];
        CThr[*row][k] = sum;
    }

    return;
}

void gemm(){
    //矩阵初始化
    for (int i = 0; i < size; ++i)
        for (int j = 0; j < size; ++j) {
            A[i][j] = size * 100 - j;
            B[i][j] = size * 100 - i;
        }

    //常规算法
    gettimeofday(&startNor, NULL);
    for (int k = 0; k < size; ++k)
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                CNor[i][j] += A[i][k] * B[k][j];
    gettimeofday(&endNor, NULL);

    //多线程
    pthread_t thrd[size];
    int row[size];
    for (int i = 0; i < size; ++i)    //为防止引起线程间冲突建立row数组存放行号
        row[i] = i;
    gettimeofday(&startThr, NULL);
    for (int i = 0; i < size; ++i) {
        pthread_create(&thrd[i], NULL, (void *) ThrCal, &row[i]);
    }
    for (int i = 0; i < size; ++i)
        pthread_join(thrd[i], NULL);    //挂起等待全部线程计算完毕
    gettimeofday(&endThr, NULL);

    //openMP
    gettimeofday(&startOmp, NULL);
    #pragma omp num_thread(4) parallel shared(A,B,COmp)
    {
        #pragma omp for
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                for (int k = 0; k < size; ++k) {
                    COmp[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }
    gettimeofday(&endOmp, NULL);

    //输出结果
    long ThrTime = 1000000 * (endThr.tv_sec - startThr.tv_sec) + endThr.tv_usec - startThr.tv_usec;
    long NorTime = 1000000 * (endNor.tv_sec - startNor.tv_sec) + endNor.tv_usec - startNor.tv_usec;
    long OmpTime = 1000000 * (endOmp.tv_sec - startOmp.tv_sec) + endOmp.tv_usec - startOmp.tv_usec;
    fprintf(f,"[%f,%f,%f]", OmpTime / 1000000.0, ThrTime / 1000000.0, NorTime / 1000000.0);
}

int main() {
    scanf("%d %d %d %d", &minSize,&maxSize,&step, &threadNum);    //读入最小测试规模、最大测试规模、步长、线程数量
    f= fopen("test.json","w");
    fprintf(f,"[");
    for (int k=minSize;k<=maxSize;k+=step) {
        size=k;
        gemm();
        if (k+step<=maxSize) fprintf(f,",");
        printf("%d",k);
    }
    fprintf(f,"]");
    fclose(f);

    return 0;
}
