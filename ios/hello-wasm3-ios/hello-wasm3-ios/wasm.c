//
//  wasm.c
//  hello-wasm3-ios
//
//  Created by 小島和範 on 2022/12/31.
//

#include "wasm.h"
#include <sys/mman.h>
#include <pthread/pthread.h>

#include "wasm3.h"
#include "m3_config.h"
#include "m3_api_libc.h"

#define FATAL(msg, ...) { printf("Fatal: " msg "\n", ##__VA_ARGS__); return 0; }

IM3Function _wasm_fibonacci;

struct wasm_bin
{
    unsigned char* body;
    uint32_t length;
};


void* loadWasm(void* ctx) {
    struct wasm_bin* arg = (struct wasm_bin*)ctx;
    
    M3Result result = m3Err_none;
    printf("Loading WebAssembly...\n");

    IM3Environment env = m3_NewEnvironment();
    if (!env) FATAL("m3_NewEnvironment failed");

    IM3Runtime runtime = m3_NewRuntime(env, 8192, NULL);
    if (!runtime) FATAL("m3_NewRuntime failed");

    IM3Module module;
    result = m3_ParseModule(env, &module, arg->body, arg->length);
    if (result) FATAL("m3_ParseModule: %s", result);

    result = m3_LoadModule(runtime, module);
    if (result) FATAL("m3_LoadModule: %s", result);

    result = m3_LinkLibC(module);
    if (result) FATAL("m3_LinkLibC: %s", result);

    result = m3_FindFunction(&_wasm_fibonacci, runtime, "fibonacci");
    if (result) FATAL("m3_FindFunction: %s", result);
    
    return NULL;
}

uint wasm_fibonacci(uint num) {
    M3Result result = m3Err_none;
    
    result = m3_CallVL(_wasm_fibonacci, (char*)&num);
    if (result) FATAL("m3_CallV: %s", result);
    
    int value = 0;
    result = m3_GetResultsV(_wasm_fibonacci, &value);
    if (result) FATAL("m3_GetResultsV: %s", result);
    
    return value;
}

void launchWasm(unsigned char* wasm, uint32_t len) {
    struct wasm_bin arg = { wasm, len };
    loadWasm(&arg);
}
