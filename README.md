# x-http-wrapper
a auto creation tool for http request, request param, response entity and status code of response body


# [工作流程](guide/global-process.png)

1. 解析x-http-wrapper.json这个配置文件；
2. 在配置文件中，有API数据文件(在api_data中)，再根据配置数据，将API数据解析为x-http-wrapper中的model datas；
3. 在配置文件中，有所有的x-http-wrapper的template文件(在template_file_infos中)，根据template文件中的内容与model datas和配置一起，生成目标文件；
