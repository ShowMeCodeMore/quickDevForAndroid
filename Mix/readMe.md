## BaseRecyclerViewAdapterHelper上拉加载更多碰到的问题 ##  
### 问题加载过程描述###
>第一次加载 1次 第二次加载 3次 第三次加载 4 第四次加载 10
第五次加载 2 第六次加载 4 第七次 加载一个后崩溃掉 提示解析错误

<em>BaseRecyclerViewAdapterHelper 加载更多使用addData加载数据，加载前两页的时候正常而
加载第三页的时候出现多次加载，然后清除之前所有的数据。
还有一个比较奇怪的问题是源数据为20条，但是addData后在调用loadMoreComplete方法，源数据的
条数会变成40条多余的条数还是之前一样的数据</em>，是什么原因造成的还未找到原因，但是通过setNewData
可以正常实现上拉加载的数据。
