# 为什么使用RabbitMQ
    原因在于，使用线程池技术可以将用户任务发送到线程池中排队进行处理，但是当程序挂掉以后，
    用户提交的任务
# RabbitMQ使用方法
    MqInitMain文件用于声明交换机Exchanger以及消息队列Queue
    MessageConsumer用于从消息队列中接收请求
    MessageProducter用于向消息队列中发送请求

