"""
Queue implementation built on top of nodes.
file: cs_queue.py
author: CS @ rit.edu

This is Queue datatype implemented by a mutable linked node sequence.
"""

from node_types import MutableNode as Node
from dataclasses import dataclass
from typing import Union

@dataclass(frozen = False)
class Queue:
    size: int
    front: Union[None, Node]
    back: Union[None, Node]

def make_empty_queue():
    """
    Returns a new queue with size initialized to zero and
    the front and back fields initialized to the empty sequence.
    """
    return Queue(0, None, None)

def enqueue(queue, element):
    """
    Insert an element into the back of the queue. (Returns None)
    """
    newnode = Node(element, None)
    if is_empty(queue):
        queue.front = newnode
    else:
        queue.back.next = newnode
    queue.back = newnode
    queue.size = queue.size + 1
    
def dequeue(queue):
    """
    Remove the front element from the queue. (returns removed value)
    precondition: queue is not empty.
    """
    if is_empty(queue):
        raise IndexError("dequeue on empty queue") 
    removed = queue.front.value
    queue.front = queue.front.next
    if is_empty(queue):
        queue.back = None
    queue.size = queue.size - 1
    return removed
    
def front(queue):
    """
    Access and return the first element in the queue without removing it.
    precondition: queue is not empty.
    """
    if is_empty(queue):
        raise IndexError("front on empty queue") 
    return queue.front.value
    
def back(queue):
    """
    Access and return the last element in the queue without removing it
    precondition: queue is not empty.
    """
    if is_empty(queue):
        raise IndexError("back on empty queue") 
    return queue.back.value
    
def is_empty(queue):
    """
    Is the queue empty?
    """
    return queue.front is None

