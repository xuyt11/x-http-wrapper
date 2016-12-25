# x-http-wrapper
a auto creation tool for http request, request param, response entity and status code of response body


# 快速使用入门
1. 从xhwt文件夹下，获取其中的一个包装器的模板文件夹的所有文件，例如：xhwt/asynchttp/non_version文件夹的所有文件；
2. 获取api_data.json文件，并修改x-http-wrapper.json中api_data.file_path_infos的配置信息，将api_data.json的绝对路径添加上去；
3. 修改模板文件夹中，xhwt后缀模板文件中<t:header>标签内，生成文件的目标路径；
4. 命令行：java -jar (jar path) (config absolute path)
```
java -jar x-http-wrapper.jar x-http-wrapper.json
```
5. 在x-http-wrapper.json中，template_file_infos.need_generate属性：是否需要生成该类文件的开关；[详细的配置文件介绍](guide/config-structure.md)


# [工作流程](guide/global-process.png)
1. 解析x-http-wrapper.json这个配置文件；
2. 在配置文件中，有API数据文件(在api_data中)，再根据配置数据，将API数据解析为x-http-wrapper中的model datas；
3. 在配置文件中，有所有的x-http-wrapper的template文件(在template_file_infos中)，根据template文件中的内容与model datas和配置一起，生成目标文件；


# [最新的jar](guide/x-http-wrapper.jar)
1. 使用方式：
```
java -jar x-http-wrapper.jar x-http-wrapper.json
```
2. x-http-wrapper.json文件，必须是绝对路径，该文件是整个wrapper的配置文件；
3. 若有多个json文件，也可以(如：有多个程序(ios,android)需要生成代码)；


# wrapper的配置文件：[x-http-wrapper.json](guide/config-structure.md)
   * 该文件保存有所有的配置信息， 共有8个分类：
   api_data,template_file_infos,base_config,filter,request,response,status_code,param_types


# [wrapper内部api数据模型](guide/xhw-model.md)
1. BaseModel:
    * 所有的model都需要继承BaseModel
    * BaseModel中有一个泛型用于存储更高一级的BaseModel
    * 在template engine中，反射只认BaseModel，不是BaseModel的model不能反射
    * template engine在反射调用时，若没有在反射的对象中找到方法，会从higherLevel中去找，直到没有higherLevel为止；
2. model的结构:
    * VersionModel-->StatusCode, Request
    * StatusCode
    * Request-->Url,Header,Input,Response
    * Response-->Response File,Response Message


# [wrapper模板文件的类型](guide/template-type.md)
1. 所有的类别都在XHWTFileType枚举中，现阶段共有6个类别;
    HttpApi, Request, RequestParam, Response, StatusCode, BaseResponse
2. 且在该枚举中也有该模板类别所需数据的获取过滤功能(getReflectiveDatas);


# [wrapper模板标签](guide/template-file_and_tags.md)
1. 生成的文件内容由该文件类型获取到的API数据与标签两者来驱动
2. 头部标签<t:header>： 用于标示该模板文件，生成的目标文件路径和名称；
    * fileDirs:目标文件路径
    * headerfileName:目标文件名称
3. 现阶段只有6个标签类型：使用反射来进行数据的加工
    * text, foreach, retain, list_single_line, if_else, list_replace
    * 标签内部的匹配都为反射的方法名称；
        * 例如：在foreach标签中，<t:foreach each="request_groups">，
        匹配的request_groups即为反射后去request_groups方法的数据，然后利用该数据去遍历；


# api的数据源：[apidocjs](guide/apidocjs.md)
* api_data.json就是使用apidocjs工具生成的数据文件；

