# x-http-wrapper

[中文](README_ch.md) | [English](README.md)

1. 这是一个http相关代码的创建工具。
2. 现在能创建的http相关的文件类型有：http请求分类，http请求，请求方法参数，响应实体，响应实体中状态码列表和基础响应实体类。
3. http的数据来源，现阶段只有apidocjs这一个
    * 若有其他数据来源，可以配置api_data.api_data_source属性，然后添加对应的解析器，解析为xhw的model。


# 工具环境与依赖
* 命令行运行jar文件: 需要java8及以上的版本
* 开发环境:
    * Java的版本: java8及以上的版本
    * 开发平台: intellij idea
    * 依赖的jar: gson:2.8.0, fastjson:1.2.17, rxjava:1.2.2, junit:4.12


# 快速使用入门
1. 下载项目的Zip包，解压缩，从xhwt文件夹下，选取其中的一个包装器模板文件夹，作为目标包装器的配置，该文件夹在下面都叫做**target dir**；
    * 例如：xhwt/asynchttp/non_version(这是android-async-http库的一个模板与配置)；
2. 获取接口数据文件(api_data.json:存储apidocjs生成的API文档的数据)的路径；
    * 例如：guide文件夹中的api_data.json的绝对路径
3. 修改target dir下配置文件(**x-http-wrapper.json**)中api_data.file_path_infos的配置信息，将api_data.json的绝对路径添加上去；
    ```json
      "api_data": {
        "api_data_source": "apidocjs",
        "api_data_file_address_type": "file",
        "file_path_infos": [
          {
            "OSName": "Mac OS X",
            "address": "api_data.json的绝对路径"
          },
          {
            "OSName": "Windows",
            "address": "api_data.json的绝对路径"
          }
        ],
        "file_charset": "UTF-8"
      }
    ```
4. 修改target dir中，API的模板文件中\<t:header\>\</t:header\>标签内，生成文件的目标路径；
    * API的模板文件是以.xhwt为后缀的文件，是生成各个http相关文件的模板；
    * \<t:header\>\</t:header\>标签内，保存的是模板文件生成文件的文件名称与文件地址；
        * 例如：
        ```json
        {
            "fileName":"HttpApi.swift",
            "fileDirs":[
                {
                    "osName":"Windows",
                    "path":"生成文件的目标路径(绝对路径)"
                },
                {
                    "osName":"Mac OS X",
                    "path":"生成文件的目标路径(绝对路径)"
                }
            ]
        }
        ```
5. 修改target dir下配置文件(**x-http-wrapper.json**)中template_file_infos中的need_generate属性，用于开启、关闭生成文件的功能；
    * 例如：若你想生成ttpApi类型的文件，就需要将template_file_infos.HttpApi.need_generate设置为true，并要修改了xxx-httpapi.xhwt文件中header标签内的地址；
    ```json
      "template_file_infos": {
        "HttpApi": {
          "need_generate": true,
          "path": "ncm_ios_n-httpapi.xhwt"
        },
        ...
      }
    ```
6. 命令行生成相关http文件
    * 命令行运行：java -jar (jar文件的路径) (配置文件的绝对路径)
    * jar文件的路径：在guide文件夹下有最新的jar(x-http-wrapper.jar)
    * 配置文件的绝对路径：配置文件(**x-http-wrapper.json**)的绝对路径
    ```
    java -jar x-http-wrapper.jar xxxx/x-http-wrapper.json
    ```


# api的数据源：[apidocjs](guide/apidocjs.md)
* api_data.json就是使用apidocjs工具生成的数据文件；


# [工作流程](guide/global-process.png)
1. 解析x-http-wrapper.json这个配置文件；
2. 在配置文件中，有API数据文件(在api_data中)，再根据配置数据，将API数据解析为x-http-wrapper中的model数据；
3. 在配置文件中，有所有的x-http-wrapper的template文件(在template_file_infos中)，根据template文件中的内容与model datas和配置一起，生成目标文件；


# [最新的jar](guide/x-http-wrapper.jar)
1. 使用方式：
```
java -jar x-http-wrapper.jar xxx/x-http-wrapper.json
```
2. x-http-wrapper.json文件，必须是绝对路径，该文件是整个wrapper的配置文件；
3. 若有多个json文件，也可以(如：有多个程序(ios,android)需要生成代码)；

# wrapper的配置文件：
   * [x-http-wrapper.json (详细的配置文件介绍)](guide/config-structure.md)
   * 该文件保存有所有的配置信息， 共有8个分类：
   api_data, template_file_infos, base_config, filter, request, response, status_code, param_types


# [wrapper内部api数据模型](guide/xhw-model.md)
1. BaseModel:
    * 所有的model都需要继承BaseModel
    * BaseModel中有一个泛型用于存储更高一级的BaseModel
    * 在template engine中，反射只认BaseModel，不是BaseModel的model不能反射
    * template engine在反射调用时，若没有在反射的对象中找到方法，会从higherLevel中去找，直到没有higherLevel为止；
2. model的结构:
    * VersionModel-->StatusCodeGroup, RequestGroup
    * StatusCodeGroup-->StatusCode
    * RequestGroup-->Request-->Url,Header,Input,Response
    * Response-->Response File,Response Message


# [wrapper模板文件的类型](guide/template-type.md)
1. 所有的类别都在XHWTFileType枚举中，现阶段共有6个类别;
   * HttpApi, Request, RequestParam, Response, StatusCode, BaseResponse
2. 且在该枚举中也有该模板类别所需数据的获取过滤功能(getReflectiveDatas方法);


# [wrapper模板标签](guide/template-file_and_tags.md)
1. 生成的文件内容由该文件类型获取到的API数据与标签两者来驱动
2. 头部标签\<t:header\>\</t:header\>： 用于标示该模板文件，生成的目标文件路径和名称；
    * fileDirs:目标文件路径
    * headerfileName:目标文件名称
3. 现阶段只有7个标签类型：使用反射来进行数据的加工
    * text, foreach, retain, list_single_line, if_else, list_replace, list_attach
    * 标签内部的匹配都为反射的方法名称；
        * 例如：在foreach标签中
        ```xhtml
        <t:foreach each="request_groups">
        </t:foreach>
        ```
        匹配的request_groups即为反射后去request_groups方法的数据，然后利用该数据去遍历；


