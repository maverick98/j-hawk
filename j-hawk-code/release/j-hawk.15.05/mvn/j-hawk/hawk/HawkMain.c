/* 
 * File:   HawkMain.c
 * Author: manoranjan
 *
 * Created on 12 March, 2011, 12:06 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "JavaPropertyReader.h"




int main(int argc, char** argv) {

    int result = EXIT_FAILURE;
    result = executeCommandLineOptions(argc,argv);
    return result;
     
}
/**
 * Fix me ... Use gcc compile time option
 * to guard against different OS specific code...
 * @param command
 * @return
 */
int appendHawkJarPath(char *command){
    if( UNIX  == 1){
        strcat(command,"$HOME/.hawk/j-hawk.jar ");
    }
    if( WIN  == 1){
        strcat(command,"\"%PROGRAMFILES%/hawk/j-hawk.jar\" ");
    }
    return 0;
}

int executeCommandLineOptions(int argc, char** argv){
    int result = EXIT_FAILURE;
    char command[4096];
    char *prefix = "java ";
    strcpy(command,prefix);
    if(argc == 2){
        strcat(command," -cp ");
        if(strcmp("-version",argv[1]) == 0){
            appendHawkJarPath(command);
            strcat(command,HAWK_JAVA_SCRIPT_MAIN_CLASS);
            strcat(command," -version ");
            result = system(command);
        }else if(strcmp("-training",argv[1]) == 0){
            appendHawkJarPath(command);
            strcat(command,HAWK_JAVA_SCRIPT_MAIN_CLASS);
            strcat(command," -training ");
            result = system(command);
        }else{
            helpUser();
            result = EXIT_FAILURE;
        }
    }else if(argc == 3){
        if(strcmp("-f",argv[1]) == 0){
            strcat(command," -cp ");
            appendHawkJarPath(command);
            strcat(command,HAWK_JAVA_SCRIPT_MAIN_CLASS);
            strcat(command," -scripting -f ");
            strcat(command,argv[2]);
            result = system(command);
        }
    }else if(argc == 4 || argc ==6 ){
        char *hawkJavaPropnClasspathCommand  = calloc(2048,sizeof(char));
        char *hawkOutputCommand = calloc(256,sizeof(char));
        FILE *settingFile = fopen(argv[2],"r");
        scanSettingFile(settingFile,&hawkJavaPropnClasspathCommand,&hawkOutputCommand);
        strcat(hawkJavaPropnClasspathCommand,":");
        appendHawkJarPath(hawkJavaPropnClasspathCommand);
        if(argc ==4){
            if(strcmp("-s",argv[1]) == 0 &&  strcmp("-training",argv[3]) == 0){
                    strcat(hawkJavaPropnClasspathCommand,HAWK_JAVA_SCRIPT_MAIN_CLASS);
                    strcat(command,hawkJavaPropnClasspathCommand);
                    strcat(command," ");
                    strcat(command,argv[3]);
                    printf("%s",command);
                    result = system(command);
            }else if(strcmp("-s",argv[1]) == 0 &&  strcmp("-prop",argv[3]) == 0){
                    strcat(hawkJavaPropnClasspathCommand,HAWK_JAVA_PROPERTY_MAIN_CLASS);
                    strcat(command,hawkJavaPropnClasspathCommand);
                    strcat(command," ");
                    result = system(command);
            }else if(strcmp("-s",argv[1]) == 0 &&  strcmp("-plot",argv[3]) == 0){
                    strcat(hawkJavaPropnClasspathCommand,HAWK_JAVA_PLOTTER_MAIN_CLASS);
                    strcat(command,hawkJavaPropnClasspathCommand);
                    strcat(command," ");
                    result = system(command);
            }else{
                    helpUser();
                    result = EXIT_FAILURE;
            }
        }
       if(argc == 6){
            if(strcmp("-s",argv[1]) == 0 &&  strcmp("-f",argv[4]) == 0){
                if(strcmp("-compile",argv[3]) == 0 || strcmp("-debug",argv[3]) == 0
                        || strcmp("-perf",argv[3]) == 0 ){
                    strcat(hawkJavaPropnClasspathCommand,HAWK_JAVA_SCRIPT_MAIN_CLASS);
                    strcat(command,hawkJavaPropnClasspathCommand);
                    strcat(command," ");
                    strcat(command,argv[3]);
                    strcat(command," -f ");
                    strcat(command,argv[5]);
                    strcat(command,hawkOutputCommand);
                    result = system(command);
                    result = EXIT_SUCCESS;
                }else{
                    helpUser();
                    result = EXIT_FAILURE;
                }
            }else{
                helpUser();
                result = EXIT_FAILURE;
            }
        }
        
    }else{
        helpUser();
        result = EXIT_FAILURE;
    }
    return result;
}
int helpUser(){
    printf("---------------------------------------------------------------\n");
    printf("|              http://j-hawk.sourceforge.net                  |\n");
    printf("---------------------------------------------------------------\n");
    printf("Available options\n");
    printf("-version\n");
    printf("-training\n");
    printf("-f {path of hawk script file}\n");
    printf("-s {path of target app setting file} -training\n");
    printf("-s {path of target app setting file} -prop\n");
    printf("-s {path of target app setting file} {-debug|-perf|-compile} -f {path of hawk script file}\n");
    printf("-s {path of target app setting file} -plot\n");
    return EXIT_SUCCESS;
}




