cmake_minimum_required(VERSION 3.0)

project(okapi)

# Configure required libraries ...
if(UNIX) # Darwing or Linux

    find_package(Boost REQUIRED COMPONENTS system thread log program_options chrono)
    find_package(Threads REQUIRED)

    if(APPLE)
        # Prefer a homebrew version of OpenSSL over the one in /usr/lib
	    file(GLOB OPENSSL_ROOT_DIR /usr/local/Cellar/openssl/*)
        # Prefer the latest (make the latest one first)
	    list(REVERSE OPENSSL_ROOT_DIR)

        find_package(OpenSSL 1.0.2 REQUIRED)
        set(OPENSSL_VERSION "1.0.2f")
    else()
        find_package(OpenSSL 1.0.1 REQUIRED)
        set(OPENSSL_VERSION "1.0.1")
    endif()

elseif(WIN32) # Windows systems including Win64.
    message(FATAL_ERROR "-- Windows is not supported for now.")
else()
    message(FATAL_ERROR "-- Unsupported platform sorry! :( ")
endif()

# Configure compiler options ...
if(CMAKE_CXX_COMPILER_ID MATCHES "Clang")

    message("-- configuring clang options")
    set(CMAKE_CXX_FLAGS "-arch x86_64 -std=c++11 -stdlib=libc++ -DBOOST_LOG_DYN_LINK -Wno-deprecated-declarations")

elseif(CMAKE_CXX_COMPILER_ID MATCHES "GNU")

    message("-- configuring gcc options")

    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11 -DBOOST_LOG_DYN_LINK")

endif()

# Project construction ...

# source files ...
add_executable(${PROJECT_NAME} source/main.cpp source/swap.cpp source/okapi_ws.h source/okapi_ws.cpp source/base64.hpp
                               source/okapi.cpp source/okapi.h source/algo_hmac.h source/algo_hmac.cpp source/constants.h
        source/futures_api.cpp source/account_api.cpp source/utils.h source/utils.cpp source/spot/margin_account_api.cpp
        source/spot/margin_order_api.cpp source/spot/spot_account_api.cpp source/spot/spot_order_api.cpp source/spot/spot_product_api.cpp
        source/ett_api.cpp)

# headers search paths ...
set(CPPRESTSDK_INCLUDE_DIR "./libs/cpprestsdk/Release/include")
set(MICROSERVICE_INCLUDE_DIR "./source/foundation/include")
message(MICROSERVICE " ${MICROSERVICE_INCLUDE_DIR}")
set(HEADER_SEARCH_PATHS ${CPPRESTSDK_INCLUDE_DIR} ${MICROSERVICE_INCLUDE_DIR} ${Boost_INCLUDE_DIR} ${OPENSSL_INCLUDE_DIR})

# library search paths ...
if(APPLE)
    set(OPENSSL_LIBS "/usr/local/Cellar/openssl/${OPENSSL_VERSION}/lib/libssl.1.0.0.dylib;/usr/local/Cellar/openssl/${OPENSSL_VERSION}/lib/libcrypto.1.0.0.dylib")
    set(CPPRESTSDK_LIBRARY "${PROJECT_SOURCE_DIR}/libs/cpprestsdk/build.debug/Binaries/libcpprest.a")
    set(ZIP_LIBRARY "/usr/local/Cellar/zlib/1.2.11/lib/libz.dylib")

    set(LIBRARIES_SEARCH_PATHS ${OPENSSL_LIBS} ${Boost_LIBRARIES} ${CPPRESTSDK_LIBRARY} ${ZIP_LIBRARY})
else()
    set(OPENSSL_LIBS "${OPENSSL_LIBRARIES}")
    set(CPPRESTSDK_LIBRARY "${PROJECT_SOURCE_DIR}/libs/cpprestsdk/build.debug/Binaries/libcpprest.a")

    set(LIBRARIES_SEARCH_PATHS ${CPPRESTSDK_LIBRARY} ${OPENSSL_LIBS} ${Boost_LIBRARIES})
endif()

message(BOOST_LIBS " ${Boost_LIBRARIES}")
message(OPENSSL_LIBS " ${OPENSSL_LIBRARIES}")
message(CPPRESTSDK_LIBRARY " ${CPPRESTSDK_LIBRARY}")
message(ZLIB_LIBRARY " ${ZLIB_LIBRARY}")
message(LIBRARIES_SEARCH_PATHS " ${LIBRARIES_SEARCH_PATHS}")

include_directories(${HEADER_SEARCH_PATHS})
if (APPLE)
    target_link_libraries(${PROJECT_NAME} "-framework CoreFoundation")
    target_link_libraries(${PROJECT_NAME} "-framework Security")
    target_link_libraries(${PROJECT_NAME} ${LIBRARIES_SEARCH_PATHS})
    set_target_properties(${PROJECT_NAME} PROPERTIES LINK_FLAGS "-W1, -F/Library/Frameworks")
else()
    target_link_libraries(${PROJECT_NAME} ${LIBRARIES_SEARCH_PATHS})
    target_link_libraries(${PROJECT_NAME} "-lz")
endif()
