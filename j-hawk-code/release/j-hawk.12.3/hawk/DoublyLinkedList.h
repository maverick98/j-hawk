/* 
 * File:   DoublyLinkedList.h
 * Author: manoranjan
 *
 * Created on 22 October, 2011, 10:19 PM
 */

#ifndef DOUBLYLINKEDLIST_H
#define	DOUBLYLINKEDLIST_H

#ifdef	__cplusplus
extern "C" {
#endif




#ifdef	__cplusplus
}
#endif

#endif	/* DOUBLYLINKEDLIST_H */
#include "HawkConstant.h"
typedef struct NodeStruct {
    char key[128];
    char value[256];
    struct NodeStruct *prev;
    struct NodeStruct *next;

}Node;
Node* addNode(struct NodeStruct *currentNode , char key[],char value[]);
int  displayList(Node *firstNode );
char* searchKey(Node *firstNode, char key[]);


int displayList(Node *firstNode ){
    Node *currentNode = NULL;
    if(firstNode ==NULL){
        return EXIT_FAILURE;
    }
    for(currentNode = firstNode;currentNode != NULL;currentNode =  currentNode->next){
        printf("%s->%s",currentNode->key,currentNode->value);

    }
    printf("\n");
    return EXIT_SUCCESS;
}

char* searchKey(Node *firstNode, char key[]){
    Node *currentNode = NULL;
    char* result = NULL;
    if(firstNode ==NULL){
        return NULL;
    }
    for(currentNode = firstNode;currentNode != NULL;currentNode =  currentNode->next){
        if(strcmp(currentNode->key,key) == 0){
            result =  currentNode->value;
            break;
        }
    }
    return result;

}

Node* addNode(Node *currentNode , char key[],char value[]){
    Node *nextNode = NULL;
    if(currentNode == NULL){
        currentNode = (Node *)malloc(sizeof(Node));
        nextNode = currentNode;
    }else{
        nextNode = (Node *)malloc(sizeof(Node));
        currentNode->next = nextNode;
        nextNode->prev = currentNode;

    }

    strcpy(nextNode->key,key);
    strcpy(nextNode->value,value);

    return nextNode;
}