# cloud-survey

1.cloud-survey 云数据调查分析平台

2.打包命令

    mvn clean install -Dmaven.test.skip=true
    clean assembly:assembly -Dmaven.test.skip=true -Pdevelopment

3.启动命令

    cloud-survey-server: clean compile exec:java