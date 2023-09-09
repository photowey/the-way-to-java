@echo off

echo message-in-action compile and deploy...

call mvnd clean -DskipTests source:jar deploy -pl ^
message-in-action-core,^
message-in-action-handler,^
message-in-action-autoconfigure,^
message-in-action-starter ^
 -am %*

@REM exit