# Change log

version 0.6
===========
* 重构xtemp解析器，foreach标签能够多层嵌套，去除掉list等标签
* 添加了 api_data.api_data_file_address_type属性(API数据文件的地址类型)
    * 都定义在ApiDataFileAddressType枚举中，现在有file与url两种；
    * 默认的文件地址是file类型的；
* 添加了list_attach标签：用于将转换后的字符串与当前的BaseModel绑定，之后的TextExpression即可使用attach的值作为转换符；

version 0.5
===========
1、add prefix(t) to template tags;  
2、add volley and asynchttp lib x-http-wrapper template file;  
3、refactor input and output type enum;-->add map type  
4、add is_polymerization fun;  
5、add guide;

version 0.4
===========
add request param template type;  
添加了请求参数组模板类型，用于简化请求参数的个数过多的情况；

version 0.3
===========
不再解析apidocjs的和HTML页面中的数据了，直接解析api_data.json；

version 0.2
===========
实现了转换文件的模板化；
>xha
>>http api
>>请求的对外文件，所有的请求都通过该文件进行访问；

>xreq
>>http request
>>请求的类别文件，包含同一分类中的所有的请求；

>xres
>>http response entity
>>每一个请求中的响应实体类；

>xbres
>>http base response
>>请求响应的base文件，包含了各个response中所有的error；

>xsc
>>http response status code
>>请求响应的内部的状态码文件；

version 0.1
===========
实现了最基本的，将request转换成请求方法与响应实体类，与响应状态码的功能；
