#include <stdio.h>
#include <string.h>
#include <ctype.h>


#define N 256

 struct SYM{
    unsigned int c;// код символа
    float freq;//частота встречаемости
     int count;
    
} ;
struct SYM DATA[N]={};// массив структур


void sortByFreq(struct SYM *DATA, int n)
{
    int i, j;
    struct SYM temp;
    
    for (i = 0; i < n-1; i++)
    {
        for (j = 0; j < (n - 1-i); j++)
        {
            if (DATA[j].freq < DATA[j + 1].freq)
            {
                temp = DATA[j];
                DATA[j] = DATA[j + 1];
                DATA[j + 1] = temp;
            }
        }
    }}

void printSort(struct SYM DATA[N], int n, int count)
{
    int i=0;

    printf("Sorted:\n");
    for (i = 0; i < n; i++)
    {
        printf("%0.f  (%0.2f% %)  '%c'  \n",DATA[i].freq, 100*DATA[i].freq/count ,  DATA[i].c);
    }
}
int main ()
{
    int i=0;
    struct SYM DATA[N];
    FILE *fin=fopen("in.txt","r");
    FILE *fout=fopen("out.txt","w");
    int n=256;
    
    if (fin == NULL ||fout==NULL)
    {
        perror("fopen");
        return 1;
    }
    
 

    typedef unsigned long ul;
   int count=0;// счетчик букв
   float freq[256]={}; //массив счетчиков для каждого кода символа; для каждого прочитанного инкрементируем счетчик
    unsigned int c;//код символа от 0 до 256
    while((c=fgetc(fin)) !=EOF)
    {
      ++freq[c];
      ++count;
    }
    
    for(c=0;c<256;c++)
        printf("sym:'%c' %d - %0.2f (%.2lf% %)\n",
               ' ', c,  freq[c], 100.0 *freq[c]/count); //если печатаемый символ-выводим егоб иначе -?
    
    
    for(i=0;i<256;i++)
    {
        DATA[i].c=c++;
        DATA[i].freq=freq[i];
    }
    
 

    sortByFreq(DATA,n);
    printSort(DATA,n,count);
    
    fclose(fin);
    return 0;
}
