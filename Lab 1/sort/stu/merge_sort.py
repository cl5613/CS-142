"""
Merge Sort
Author: RIT CS

Perform an out of place merge sort on a native array of integers.

merge_sort (not in place):
    best=O(nlogn)
    worst=O(nlogn)

To run:
    $ python3 merge_sort.py
"""

from typing import List     # List
from typing import Tuple    # Tuple

def _split(data: List[int]) -> Tuple[List[int], List[int]]:
    """
    Split the data into halves and return the two halves
    :param data: The list to split in half
    :return: Two lists, cut in half
    """
    return data[:len(data)//2], data[len(data)//2:]


def _merge(left: List[int], right: List[int]) -> List[int]:
    """
    Merges two sorted lists, left and right, into a combined sorted result
    :param left: A sorted list
    :param right: A sorted list
    :return: One combined sorted list
    """
    # the sorted left + right will be stored in result
    result = []
    left_index, right_index = 0, 0

    # loop through until either the left or right list is exhausted
    while left_index < len(left) and right_index < len(right):
        if left[left_index] <= right[right_index]:
            result.append(left[left_index])
            left_index += 1
        else:
            result.append(right[right_index])
            right_index += 1

    # take the un-exhausted list and extend the remainder onto the result
    if left_index < len(left):
        result.extend(left[left_index:])
    elif right_index < len(right):
        result.extend(right[right_index:])

    return result


def merge_sort(data: List[int]) -> List[int]:
    """
    Performs a merge sort and returns a newly sorted list
    :param data: The data to be sorted (a list)
    :return: A sorted list
    """
    # an empty list, or list of 1 element is already sorted
    if len(data) < 2:
        return data
    else:
        # split the data into left and right halves
        left, right = _split(data)
        # return the merged recursive merge_sort of the halves
        return _merge(merge_sort(left), merge_sort(right))


def main() -> None:
    """
    Test function for merge_sort
    """
    DATA = (
        (),
        (0,),
        (0, 1),
        (1, 0),
        (0, 1, 2),
        (0, 2, 1),
        (1, 0, 2),
        (1, 2, 0),
        (2, 0, 1),
        (2, 1, 0),
        (9, 5, 2, 6, 3, 8, 1, 4, 0, 7)
    )

    for data in DATA:
        # create two lists of the data
        sort_data, sorted = list(data), list(data)
        # merge sort is not in place and returns a new sorted list
        sort_data = merge_sort(sort_data)
        # use built in sort to compare against
        sorted.sort()
        # show the results of the comparison
        print('merge_sort:', list(data), 'result:', sort_data,
              'OK' if sort_data == sorted else 'FAIL!')

if __name__ == '__main__':
    main()