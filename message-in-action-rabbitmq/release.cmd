@echo off

echo message-in-action-rabbitmq compile and deploy...

call mvnd clean -DskipTests source:jar deploy -pl ^
message-in-action-rabbitmq-core,^
message-in-action-rabbitmq-handler,^
message-in-action-rabbitmq-autoconfigure,^
message-in-action-rabbitmq-starter ^
 -am %*

@REM exit