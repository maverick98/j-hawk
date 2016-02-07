/* 
 * File:   JavaPropertyReader.h
 * Author: manoranjan
 *
 * Created on 20 March, 2011, 12:35 PM
 */

#ifndef JAVAPROPERTYREADER_H
#define	JAVAPROPERTYREADER_H

#ifdef	__cplusplus
extern "C" {
#endif


/* We define these the same for all machines.
   Changes from this to the outside world should be done in `_exit'.  */
#define HAWK_JAVA_PROP_PREFIX "hawk.java.prop."
#define HAWK_JAVA_CLASSPATH_PREFIX "hawk.java.classpath"
#define HAWK_OUTPUT_FILE "hawk.output.file"
#define HAWK_BG_RUN "hawk.bg.run"
#define HAWK_JAVA_SCRIPT_MAIN_CLASS "org.hawk.module.script.ScriptInterpreter"
#define HAWK_JAVA_PROPERTY_MAIN_CLASS "org.hawk.module.ModuleExecutor"
#define HAWK_JAVA_PLOTTER_MAIN_CLASS    "org.hawk.data.perf.plotter.HawkPlotter"

#define TRUE "true"


#ifdef	__cplusplus
}
#endif

#endif	/* JAVAPROPERTYREADER_H */

#include "DoublyLinkedList.h"
int prepareJavaOptions(Node *firstNode , char **hawkJavaPropnClasspathCommand , char **hawkOutputCommand);
int scanSettingFile(FILE *settingFile, char **hawkJavaPropnClasspathCommand , char **hawkOutputCommand);


int prepareJavaOptions(Node *firstNode , char **hawkJavaPropnClasspathCommand , char **hawkOutputCommand){
    Node *currentNode = NULL;
   
    char *data = NULL;
    if(firstNode ==NULL || *hawkJavaPropnClasspathCommand == NULL || hawkOutputCommand == NULL){
        return -1;
    }


    for(currentNode = firstNode;currentNode != NULL;currentNode =  currentNode->next){
        
        
        if(strstr(currentNode->key,HAWK_JAVA_PROP_PREFIX) != NULL){
            strcat(*hawkJavaPropnClasspathCommand,"-D");

            int hawkKeyLen = strlen(currentNode->key);
            int hawkPrefixKeyLen = strlen(HAWK_JAVA_PROP_PREFIX);
            int javaKeyLen = hawkKeyLen-hawkPrefixKeyLen;
            data = (char *) calloc( javaKeyLen ,sizeof(char));
            strncpy(data,currentNode->key+hawkPrefixKeyLen,javaKeyLen);
            strcat(*hawkJavaPropnClasspathCommand,data);
            strcat(*hawkJavaPropnClasspathCommand,"=");
            strcat(*hawkJavaPropnClasspathCommand,currentNode->value);
            strcat(*hawkJavaPropnClasspathCommand," ");
        }
        if(strstr(currentNode->key,HAWK_JAVA_CLASSPATH_PREFIX) != NULL){
            strcat(*hawkJavaPropnClasspathCommand," -cp ");
            strcat(*hawkJavaPropnClasspathCommand,currentNode->value);
        }

    }
    
    for(currentNode = firstNode;currentNode != NULL;currentNode =  currentNode->next){
         if(strstr(currentNode->key,HAWK_OUTPUT_FILE) != NULL){
             strcat(*hawkOutputCommand,">");
             strcat(*hawkOutputCommand,currentNode->value);
         }
    }
    for(currentNode = firstNode;currentNode != NULL;currentNode =  currentNode->next){
         if(strstr(currentNode->key,HAWK_BG_RUN) != NULL){
             if(strcmp(currentNode->value,TRUE) == 0){
                strcat(*hawkOutputCommand," ");
                strcat(*hawkOutputCommand," 2>&1 &");
             }
         }
    }
    
    return 0;
}

int scanSettingFile(FILE *settingFile, char **hawkJavaPropnClasspathCommand , char **hawkOutputCommand){
    Node *node=NULL;
    Node *firstNode = NULL;
    int result = -1;
    int lineNo =0;
    if ( settingFile != NULL ){
        char line [ LINE_LENGTH ];
        char *key,*value;
        int i=0;
        while ( fgets ( line, sizeof line, settingFile ) != NULL ){
            //fputs ( line, stdout );
            key = calloc(128,sizeof(char));
            value = calloc(256,sizeof(char));

            splitKeyValuePair(line,&key,&value);
            if(key != NULL){
                node = addNode(node,key,value);
                if(firstNode == NULL){
                    firstNode =node;
                }

            }
            i++;
        }
        fclose ( settingFile );
        result = 0;
    }else{
        result =-1;
    }

    prepareJavaOptions(firstNode,hawkJavaPropnClasspathCommand,hawkOutputCommand);

    return result;
}

int splitKeyValuePair(char line[] , char **key,char **value){
    int i=0;
    int j=0;
    if(line[0] == '#'){
        *key = NULL;
        *value =NULL;
        return EXIT_FAILURE;
    }
    if(strcmp(line,"\n") ==0){
        *key = NULL;
        *value =NULL;
        return EXIT_FAILURE;
    }
    while(line[i] != '='){
          if(line[i] != ' '){
            (*key)[j] = line[i];
            j++;
          }
        i++;
    }
    (*key)[i]= '\0';
    j=0;
    while(line[i] != '\0'){
        if(line[i] != '=' && line[i] != ' '){
            (*value)[j] = line[i];
            j++;
        }
        i++;
    }
    (*value)[j-1]= '\0';
    return EXIT_SUCCESS;
}
