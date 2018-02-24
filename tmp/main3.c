#include "task3.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <ctype.h>


    
int main()
{
    srand((unsigned)time(NULL));
    
    char strIn[MAXSIZE]={"mad max"};
    char strOut[MAXSIZE]={'\0'};
/*
    FILE *input_F = fopen ("in.txt", "r");
   FILE *output_F = fopen("out.txt", "w");
 //   FILE *output_F=stdin;
    
    if (input_F == NULL ||output_F==NULL)
    {
        perror("fopen");
        return 1;
    }
    
    
    while(fgets(strIn,MAXSIZE,input_F)!=NULL)
    {
        fprintf(output_F, "%s\n", mixLine(strIn,strOut));
 
    }
    
    fclose(input_F);
    fclose(output_F);
*/
   printf("%s", mixLine(strIn,strOut));
    
    return 0;
}
                 
