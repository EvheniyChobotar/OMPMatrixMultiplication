#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include <omp.h>
#include "HeaderForLabThree.h"

double  **A = NULL,
        **B = NULL,
        **C = NULL,
        t_start,
        t_end;
int     N,M,
        i,j,k;

void    Initialization();
void    TestInitialization();
void    FreeMemory();
double  ParallelCalculation();
double  SequentialCalculation();


JNIEXPORT jdouble JNICALL Java_mainPackage_Main_nativeParallelMatrixMultiplikation(JNIEnv *env, jobject o, jint n, jint m)
{
    N = n;
    M = m;
    Initialization();
    TestInitialization();
    return ParallelCalculation();
    FreeMemory();

}

JNIEXPORT jdouble JNICALL Java_mainPackage_Main_nativeSequentialMatrixMultiplikation(JNIEnv *env, jobject o, jint n, jint m)
{
    N = n;
    M = m;
    Initialization();
    TestInitialization();
    return SequentialCalculation();
    FreeMemory();
}

void Initialization()
{
    A = (double**)malloc(N*sizeof(double));
    B = (double**)malloc(M*sizeof(double));
    C = (double**)malloc(N*sizeof(double));
    
    for(i=0; i<N; i++)
    {
        A[i]=(double*)malloc(M*sizeof(double));
        C[i]=(double*)malloc(N*sizeof(double));
    }
    
    for(i=0; i<M; i++) 
        B[i]=(double*)malloc(N*sizeof(double));

    for(i=0; i<N; i++)
        for(j=0; j<M; j++)
        {
            A[i][j]=(double)i;
            B[j][i]=(double)i;
        }
}

void TestInitialization()
{
    if(!A || !B || !C)
        exit(1);
}

double SequentialCalculation()
{
    t_start = omp_get_wtime();
    
    for (i=0; i < N; i++)
        for (j=0; j < N; j++) 
        {
            C[i][j] = 0;
            for (k=0; k < M; k++)
                C[i][j] += A[i][k] * B[k][j];
        }
    
    t_end = omp_get_wtime();
    
    return t_end - t_start;
}
double ParallelCalculation()
{
    t_start = omp_get_wtime();
#pragma omp parallel for num_threads(2) shared(A,B,N,M) private(i,j,k) 
    for (i=0; i < N; i++)
        for (j=0; j < N; j++) 
        {
            C[i][j] = 0;
            for (k=0; k < M; k++)
                C[i][j] += A[i][k] * B[k][j];
        }
    
    t_end = omp_get_wtime();
    
    return t_end - t_start;
}

void FreeMemory()
{
    free(A);
    free(B);
    free(C);
    A=NULL;
    B=NULL;
    C=NULL;
}