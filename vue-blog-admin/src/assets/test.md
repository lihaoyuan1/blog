## 题目
In computer science, a **heap** is a specialized tree-based data structure that satisfies the heap property: if P is a parent node of C, then the key (the value) of P is either greater than or equal to (in a max heap) or less than or equal to (in a min heap) the key of C. A common implementation of a heap is the binary heap, in which the tree is a complete binary tree. (Quoted from Wikipedia at [https://en.wikipedia.org/wiki/Heap_(data_structure)](https://en.wikipedia.org/wiki/Heap_(data_structure)))

One thing for sure is that all the keys along any path from the root to a leaf in a max/min heap must be in non-increasing/non-decreasing order.

Your job is to check every path in a given complete binary tree, in order to tell if it is a heap or not.

## Input Specification:
Each input file contains one test case. For each case, the first line gives a positive integer $N(1\lt N\le1,000)$, the number of keys in the tree. Then the next line contains $N$ distinct integer keys (all in the range of **int**), which gives the level order traversal sequence of a complete binary tree.

## Output Specification:
For each given tree, first print all the paths from the root to the leaves. Each path occupies a line, with all the numbers separated by a space, and no extra space at the beginning or the end of the line. The paths must be printed in the following order: for each node in the tree, all the paths in its right subtree must be printed before those in its left subtree.

Finally print in a line `Max Heap` if it is a max heap, or `Min Heap` for a min heap, or `Not Heap` if it is not a heap at all.

## Sample Input 1:
>8
98 72 86 60 65 12 23 50

## Sample Output 1:
>98 86 23
98 86 12
98 72 65
98 72 60 50
Max Heap

## Sample Input 2:
>8
8 38 25 58 52 82 70 60

## Sample Output 2:
>8 25 70
8 25 82
8 38 52
8 38 58 60
Min Heap

## Sample Input 3:
>8
10 28 15 12 34 9 8 56

## Sample Output 3:
>10 15 8
10 15 9
10 28 34
10 28 12 56
Not Heap

## 代码
```cpp
#include <iostream>
#include <vector>
#include <cstdio>
using namespace std;

int n;
vector<int> arr;
vector<int> path;

void order(int r){
    if(r > n)
        return;
    path.push_back(r);
    if(r*2 > n){
        for(int i=0; i<path.size(); i++){
            if(i)
                printf(" ");
            printf("%d", arr[path[i]]);
        }
        printf("\n");
    }
    order(r*2+1);
    order(r*2);
    path.pop_back();
}

bool isMaxHeap(){
    for(int i=1; i<=n/2; i++){
        if(arr[i] < arr[i*2] || (i*2+1 <= n && arr[i] < arr[i*2+1]))
            return false;
    }
    return true;
}

bool isMinHeap(){
    for(int i=1; i<=n/2; i++){
        if(arr[i] > arr[i*2] || (i*2+1 <= n && arr[i] > arr[i*2+1]))
            return false;
    }
    return true;
}

int main() {
    scanf("%d", &n);
    arr.resize(n+1);
    for(int i=1; i<=n; i++)
        scanf("%d", &arr[i]);
    order(1);
    if(isMaxHeap())
        printf("Max Heap\n");
    else if(isMinHeap())
        printf("Min Heap\n");
    else
        printf("Not Heap\n");
    return 0;
}
```