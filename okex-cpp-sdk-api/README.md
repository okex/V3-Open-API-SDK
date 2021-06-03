# okapi
This is a sample that shows how to use okapi on C++ using the C++ REST SDK

## How to build

1. Install git, CMake, boost, openssl on your system, if you are using macOS this can be acomplished easily with the following command: 

          $ brew install cmake git openssl boost zlib

   if you are using Ubuntu this can be acomplished easily with the following command:

          $ sudo apt-get install cmake git openssl libssl-dev libboost-all-dev zlib1g zlib1g.dev
          
2. Clone the repository.
3. Go to the directory ./libs and execute the script: ```./build_dependencies.sh``` that'll clone the [C++ REST SDK](https://github.com/Microsoft/cpprestsdk) repository and will build the static version of the library, if you want to build the dynamic link version of the library just on the **build_dependencies.sh** script remove the flag: ```-DBUILD_SHARED_LIBS=OFF```.
4. Go to the directory cppsdk and type the following commands:

          $ mkdir build
          $ cd build
          $ cmake -G "Unix Makefiles" -DCMAKE_BUILD_TYPE=Debug ..

5. Set config.SecretKey, config.ApiKey and config.Passphrase in the main function in main.cpp
6. Finally type the command:

          $ make -j 8
          
7. On ```./build``` directory type and you should see the following output:

          $ ./okapi
        
