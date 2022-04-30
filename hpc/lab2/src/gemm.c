#include <stdio.h>
#include <stdlib.h>
#include <cblas.h>
#define size 100
double A[size][size];
double B[size][size];
double C[size][size];
int temp = size * size;
    
int main()
{
    //init A,B,C
    for(int i = 0;i<size;i++)
    {
        for(int j = 0;j<size;j++)
        {
            A[i][j] = i*j+1;
            B[i][j] = temp - i*j;
            C[i][j] = 0;
        }
    }
    //cal&print
    for(int i = 0;i<size;i++)
        for(int j = 0;j<size;j++)
        {
            for(int k = 0;k<size;k++)
            {
                C[i][j] = C[i][j] + A[i][k] * B[k][j];
                printf("%.2f ",C[i][j]);
    	    }
    	    printf("\n");
    	}
    return 0;
}
