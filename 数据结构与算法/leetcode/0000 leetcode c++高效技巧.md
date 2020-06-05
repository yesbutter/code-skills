## 记录在刷leetcode中编写不规范导致的内存和时间的多余消耗

### 1.对于复杂类型，如果不牵扯到修改值，传递引用可以避免在栈上复制浪费时间。

```c++
//Wrong，leetcode 0005 最长回文字串
    pair<int,int> expandAroundCenter(int l,int r,string s){
        while(l>=0 && r < s.size() && (s[l] == s[r])){
            l--,r++;
        }
        return make_pair(l + 1,r - 1);
    }

```

```c++
//right
    pair<int,int> expandAroundCenter(int l,int r,const string& s){
        while(l>=0 && r < s.size() && (s[l] == s[r])){
            l--,r++;
        }
        return make_pair(l + 1,r - 1);
    }
```

- 保证不会修改原字符串s。
- 函数调用

在保证上面的情况下，可以传递原字串的引用，来读取字符，从而减少在函数调用的时候，在栈上复制字符串而消耗的资源。

### 2.情况类似1

```c++
//leetcode 0006 Z字型变换
//wrong
string ans = "";
for(int i = 0;i< 1000;i++){
	ans = ans + "a";
}
```

```c++
//right
string ans = "";
for(int i = 0;i< 1000;i++){
	ans += "a";
}
```

在错误情况，会进行拷贝一次原串。正确的情况会在源串后面添加。