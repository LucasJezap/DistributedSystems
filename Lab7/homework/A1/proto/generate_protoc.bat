cmd /k "python -m grpc_tools.protoc -I. --python_out=../client_python --grpc_python_out=../client_python crypto.proto & exit"
cmd /k "protoc -I. --java_out=../server_java/gen --plugin=protoc-gen-grpc-java-1.37.0-windows-x86_64.exe --grpc-java_out=../server_java/gen crypto.proto & exit"