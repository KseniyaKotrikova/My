#include "task3.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#define OUT 0
#define IN 1


char *mixChars(char *in,char *out)
{
    printf("mixChars");
    int j=1,len=0,index=1;
    char temp=0;
    char *Pin=in;
 
    if(strlen(in)<4)
       out=in;
    
    len=strlen(in)-2;//кол-во перемешиваемых символов

    for(j=1;j<len;j++)
    {
        index = j;
        while(index==j)
           index=rand() % len+1;
        
        //swap
        temp = in[j];
        in[j] = in[index];
        in[index] = temp;
    }

    out=Pin;
    
      printf("in: %s out: %s\n",in,out);
    
    return out;
}

char *mixLine(char *instr,char *outstr)
{
    printf("mixLine");
  //  char word_in[MAXSIZE]={'\0'};
    char word_out[MAXSIZE]={'\0'};
    char *p_out=NULL;
   // outstr=NULL;//segmentation fault
    p_out=outstr;
  //  char *p_wordOut=NULL;
    char add_space=' ';
    
    
    int i=0,j=0;
    int len=0;
    len=strlen(instr);
    
    
  //  if(instr[len-1]=='\0')
    //  instr[len-1]='\n';
    
   for(int x=0;x<MAXSIZE;x++)//инициализируем word_out
        word_out[x]=0;
    
    for(int x=0;x<MAXSIZE;x++)//инициализируем outstr
        outstr[x]=0;
    
    
    int num_words;
    
    int index=0;
    //подсчет слов
    for(int i=1 ;i<len;i++)
    {
        if( instr[i-1]==' ')
            continue;
        else if(instr[i]==' ')
            num_words++;
        else index=i;
    }
    if(index+1<len)
        num_words--;
    
    char *words[MAXSIZE]={'\0'};//массив указателей на лексемы
    
    char *sep=" ;:.,!?";
    words[j]=strtok(instr,sep);//все лексемы - в word
   
    i++;                       // при первом вызове strtok(str,sep), параметр str указывает на начало строки,
    
    int y=0;
    while(words[y-1]!=NULL)
    {
        words[y]=strtok(NULL,sep);//...при последующих - используется нулевой указатель
        y++;
    }
    
    char *p_words=NULL;//указатель на word[]
    
    
    for(i=0;i<num_words;i++)//
    {
        p_words=words[i];//передаем слова через указатели
        word_out[i]=*p_words;//-//-
    mixChars(p_words,word_out);//p_words - массив с преобразованными словами
        
       
    //    printf("p_words:%s",p_words);
        *p_out='\0';
        for(j=0;j<num_words;j++)
        p_out=&word_out[j];
        *p_out='\0';
        *p_out='\n';
        
        
    
    //   if(*p_words==p_words[strlen(p_words)])
   //     *p_words++=' '; //??
        
        
        
        strcat(p_out, &add_space);
        strcat(p_out,p_words);
    }
        
    outstr=p_out;
        
      printf("outstr: %s\n",outstr);
   
        return outstr;
    
        
    }
