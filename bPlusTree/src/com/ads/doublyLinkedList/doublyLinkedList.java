 package com.ads.doublyLinkedList;

public class doublyLinkedList<valueType>{
    public node<valueType> start;
    public node<valueType> end ;
    public int size;
   
    public doublyLinkedList(){
        start = null;
        end = null;
        size = 0;
        }
    
    public boolean isEmpty()
    {
    	return start == null;
    	}
    
    public int getSize()
    {
    	return size;
    	}
    
    public void insertAtStart(valueType val)
    {
    	node<valueType> newNode = new node<valueType>(val, null, null);        
        if(start == null)
        {
        	start = newNode;
            end = start;
            }
        else
        {
            start.setPrev(newNode);
            newNode.setNext(start);
            start = newNode;
        }
        size++;
    }

    public void insertAtEnd(valueType val)
    {
    	node<valueType> newNode = new node<valueType>(val, null, null);
    	if(start == null)
        {
            start = newNode;
            end = start;
            }
    	else
        {
    		newNode.setPrev(end);
    		end.setNext(newNode);
    		end = newNode;
    		}
        size++;
    }
    
    public void insertAtPos(valueType val , int pos)
    {
    	node<valueType> newNode = new node<valueType>(val, null, null);    
        if (pos == 1)
        {
        	insertAtStart(val);
        	return;
        	}
        
        node<valueType> tempNode = start;
        for (int i = 2; i <= size; i++)
        {
            if (i == pos)
            {
            	node<valueType> tmp = tempNode.getNext();
            	tempNode.setNext(newNode);
            	newNode.setPrev(tempNode);
            	newNode.setNext(tmp);
                tmp.setPrev(newNode);
                }
            tempNode = tempNode.getNext();            
            }
        size++ ;
        }
   
    public void deleteAtPos(int pos)
    {
    	if (pos == 1) 
        {
            if (size == 1)
            {
                start = null;
                end = null;
                size = 0;
                return;
                }
            
            start = start.getNext();
            start.setPrev(null);
            size--; 
            return;
        }
    	
        if (pos == size)
        {
            end = end.getPrev();
            end.setNext(null);
            size-- ;
        }
        
        node<valueType> tempNode = start.getNext();
        for (int i = 2; i <= size; i++)
        {
            if (i == pos)
            {
            	node<valueType> prev = tempNode.getPrev();
            	node<valueType> next = tempNode.getNext();
                prev.setNext(next);
                next.setPrev(prev);
                size-- ;
                return;

            }
            tempNode = tempNode.getNext();
        } 
}
    }
